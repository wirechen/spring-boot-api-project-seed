package com.company.project.aliyun;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.iot.model.v20170620.*;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.company.project.configurer.PropertiesConfigurer;
import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class IotHub {

    @Autowired
    private PropertiesConfigurer config;

    private Logger logger = LoggerFactory.getLogger(IotHub.class);

    /**
     * 创建IOT-SDK客户端
     * @return
     * @throws ClientException
     */
    private DefaultAcsClient getClient() throws ClientException {
        String accessKey = config.getIot().getAccessKey();
        String accessSecret = config.getIot().getAccessSecret();
        DefaultProfile.addEndpoint("cn-shanghai", "cn-shanghai", "Iot", "iot.cn-shanghai.aliyuncs.com");
        IClientProfile profile = DefaultProfile.getProfile("cn-shanghai", accessKey, accessSecret);
        DefaultAcsClient client = new DefaultAcsClient(profile);
        return client;
    }

    /**
     * 创建产品
     * @param productName
     * @param desc
     * @return
     */
    public Map<String, Object> createNewProduct(String productName, String desc) {
        CreateProductRequest request = new CreateProductRequest();
        request.setDesc(desc);
        request.setName(productName);
        CreateProductResponse response = null;
        try {
            response = getClient().getAcsResponse(request);
        } catch (ClientException e) {
            e.printStackTrace();
        }
        Map<String, Object> map = new HashMap<String, Object>();
        if (response != null) {
            map.put("key", response.getProductInfo().getProductKey());
            map.put("info", response.getErrorMessage() == null ? "success" : response.getErrorMessage());
        } else {
            map.put("key", null);
            map.put("info", "some wrong with iot client");
        }
        return map;
    }

    /**
     * 修改产品信息
     * @param productName
     * @param desc
     * @return
     */
    public String updateProduct(String productKey, String productName, String desc) {
        UpdateProductRequest request = new UpdateProductRequest();
        request.setProductKey(productKey);
        request.setProductName(productName);
        request.setProductDesc(desc);
        UpdateProductResponse response = null;
        try {
            response = getClient().getAcsResponse(request);
        } catch (ClientException e) {
            e.printStackTrace();
        }
        if (response != null) {
            return response.getErrorMessage();
        } else {
            return "some wrong with iot client";
        }
    }

    /**
     * 创建单个设备
     * @param productKey
     * @param deviceName
     * @return
     */
    public Map<String, Object> createDevice(String productKey, String deviceName) {
        RegistDeviceRequest registDeviceRequest = new RegistDeviceRequest();
        registDeviceRequest.setProductKey(productKey);
        registDeviceRequest.setDeviceName(deviceName);// length: 4-32可以设空，如果名称为空则由阿里云生成设备名称默认与设备id一致,设备名称在产品内唯一
        RegistDeviceResponse registDeviceResponse = null;
        try {
            registDeviceResponse = getClient().getAcsResponse(registDeviceRequest);
        } catch (ClientException e) {
            e.printStackTrace();
        }
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("device_id", registDeviceResponse.getDeviceId());
        map.put("device_secret", registDeviceResponse.getDeviceSecret());
        map.put("device_name", registDeviceResponse.getDeviceName());
        map.put("info", registDeviceResponse.getErrorMessage() == null ? "success" : registDeviceResponse.getErrorMessage());
        return map;
    }

    public Map<String, Object> createDevice(String productKey) {
        RegistDeviceRequest registDeviceRequest = new RegistDeviceRequest();
        registDeviceRequest.setProductKey(productKey);
        RegistDeviceResponse registDeviceResponse = null;
        try {
            registDeviceResponse = getClient().getAcsResponse(registDeviceRequest);
        } catch (ClientException e) {
            e.printStackTrace();
        }
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("device_secret", registDeviceResponse.getDeviceSecret());
        map.put("device_name", registDeviceResponse.getDeviceName());
        return map;
    }


    public void testPublish(String productKey, String deviceName, String content) {

        String accessKey = "LTAI6ZFkpwi8LRYn";
        String accessSecret = "ppQnf2lp0KSFkUckfEHhI4DMfuq6TR";
        try {
            DefaultProfile.addEndpoint("cn-shanghai", "cn-shanghai", "Iot", "iot.cn-shanghai.aliyuncs.com");
        } catch (ClientException e) {
            e.printStackTrace();
        }
        IClientProfile profile = DefaultProfile.getProfile("cn-shanghai", accessKey, accessSecret);
        DefaultAcsClient client = new DefaultAcsClient(profile);

        PubRequest request = new PubRequest();
        request.setProductKey(productKey);
        request.setMessageContent(Base64.encodeBase64String(content.getBytes()));
        request.setTopicFullName("/"+productKey+"/"+deviceName+"/get");
        request.setQos(1); //目前支持QoS0和QoS1
        PubResponse response = null;
        try {
            response = client.getAcsResponse(request);
        } catch (ClientException e) {
            e.printStackTrace();
        }
        System.out.println(response.getSuccess());
        System.out.println(response.getErrorMessage());
    }

}
