package com.company.project.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.company.project.aliyun.IotHub;
import com.company.project.aliyun.IotTestClient;
import com.company.project.dao.DeviceMapper;
import com.company.project.model.Device;
import com.company.project.model.Product;
import com.company.project.service.DeviceService;
import com.company.project.core.AbstractService;
import com.company.project.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


/**
 * Created by CodeGenerator on 2017/11/27.
 */
@Service
@Transactional
public class DeviceServiceImpl extends AbstractService<Device> implements DeviceService {

    @Resource
    private DeviceMapper deviceMapper;
    @Resource
    private ProductService productService;
    @Autowired
    private IotHub iotHub;
    @Autowired
    private IotTestClient iotTestClient;

    @Override
    public String createNormalDevice(int userId, int productId, String deviceName) {
        //查询product
        Product product = productService.findById(productId);
        //iot-创建设备
        Map<String, Object> map = iotHub.createDevice(product.getProductKey(), deviceName);
        //同步本地数据库
        String info = map.get("info").toString();
        if ("success".equals(info)) {
            Device device = new Device();
            device.setDeviceId(map.get("device_id").toString());
            device.setDeviceName(map.get("device_name").toString());
            device.setDeviceSecret(map.get("device_secret").toString());
            device.setProductId(productId);
            device.setUserId(userId);
            device.setStatus("UNACTIVE");
            device.setCreateTime(new Date());
            save(device);
        }
        return info;
    }

    @Override
    public Map<String, String> activeDevice(int id) {
        Device device = findById(id);
        int productId = device.getProductId();
        Product product = productService.findById(productId);
        String productKey = product.getProductKey();
        String deviceName = device.getDeviceName();
        String deviceSecret = device.getDeviceSecret();
        Map<String, String> res = new HashMap<>();
        try {
            iotTestClient.runTestDevice(productKey, deviceName, deviceSecret, null);
            res.put("result", "设备激活成功");
        } catch (Exception e) {
            e.printStackTrace();
            res.put("result", "设备激活失败，原因:" + e.getMessage());
        }

        return res;
    }

    @Override
    public Map<String, String> publishSpots(JSONObject jsonObject) {
        int id = jsonObject.getInteger("device_id");
        Device device = findById(id);
        int productId = device.getProductId();
        Product product = productService.findById(productId);
        String productKey = product.getProductKey();
        String deviceName = device.getDeviceName();
        String deviceSecret = device.getDeviceSecret();
        Map<String, String> res = new HashMap<>();
        try {
            iotTestClient.runTestDevice(productKey, deviceName, deviceSecret, jsonObject);
            res.put("result", "数据发送成功");
        } catch (Exception e) {
            e.printStackTrace();
            res.put("result", "数据发送失败，原因:" + e.getMessage());
        }

        return res;
    }



}
