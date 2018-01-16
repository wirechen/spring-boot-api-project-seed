package com.company.project.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.company.project.aliyun.IotHub;
import com.company.project.aliyun.IotTestClient;
import com.company.project.aliyun.MnsLocalServer;
import com.company.project.core.ServiceException;
import com.company.project.dao.DeviceMapper;
import com.company.project.model.Device;
import com.company.project.model.Product;
import com.company.project.service.DeviceService;
import com.company.project.core.AbstractService;
import com.company.project.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
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
    @Autowired
    private MnsLocalServer mnsLocalServer;

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
        JSONObject dataObject = jsonObject.getJSONObject("data");
        int id = dataObject.getInteger("device_id");
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

    @Override
    public void initDeviceService(String imei, int productId) {
        Device device = new Device();
        device.setImei(imei);
        device.setProductId(productId);
        device.setUserId(0);
        save(device);
    }

    @Override
    public Map<String, Object> activeDeviceService(String imei, String iccid) {
        Device device = findBy("imei", imei);
        // 如果device为null说明那些傻逼工人连IMEI条形码都还没扫就他妈开机了！！
        if (device == null) {
            throw new ServiceException("傻逼工人还没有扫条形码就TM开机了");
        } else {
            int productId = device.getProductId();
            Product product = productService.findById(productId);
            String productKey = product.getProductKey();
            // 判断device的userId是否为0， 0 -> 创建IOT设备并返回MQTT参数;  不为0 -> 直接返回MQTT参数
            Map<String, Object> data = new HashMap<>();
            if (device.getUserId() == 0 && (device.getDeviceName() == null || "".equals(device.getDeviceName()))) {
                Map<String, Object> result = iotHub.createDevice(productKey);
                String deviceName = result.get("device_name").toString();
                String deviceSecret = result.get("device_secret").toString();
                device.setDeviceName(deviceName);
                device.setDeviceSecret(deviceSecret);
                device.setIccid(iccid);
                device.setCreateTime(new Date());
                update(device);
                data.put("deviceName", deviceName);
                data.put("deviceSecret", deviceSecret);
            } else {
                String deviceName = device.getDeviceName();
                String deviceSecret = device.getDeviceSecret();
                data.put("deviceName", deviceName);
                data.put("deviceSecret", deviceSecret);
            }
//            String userId = String.format("%03d", device.getUserId()); //补全位数
//            String deviceId = String.format("%04d", device.getId()); //补全位数
            data.put("userId", device.getUserId());
            data.put("deviceId", device.getId());
            data.put("productKey", productKey);

            return data;
        }

    }

    @Override
    public void registDeviceService(Device device) {
        // 只取更新参数，防止其他参数被恶意修改
        String imei = device.getImei();
        Device registDevice = findBy("imei", imei);
        registDevice.setImei(device.getImei());
        registDevice.setUserId(device.getUserId());
        registDevice.setSiteName(device.getSiteName());
        registDevice.setCoordinates(device.getCoordinates());
        registDevice.setIsOutage(device.getIsOutage());
        registDevice.setIsWind(device.getIsWind());
        registDevice.setSiteHigh(device.getSiteHigh());
        registDevice.setSiteImg(device.getSiteImg());
        update(registDevice);

    }


    @Async
    @Override
    public void switchMsn(String switchMsn) {
        if ("on".equals(switchMsn)) {
            new Thread(()->{
                mnsLocalServer.messageMQ();
            }).run();
        } else if ("off".equals(switchMsn)) {
            mnsLocalServer.setSwitchMsn(false);
        } else {
            throw new ServiceException("开启MNS消息服务参数错误");
        }

    }

    @Override
    public Map<String, Object> P2DeviceActive(String imei, String iccid) {
        // 根据IMEI查找设备是否为空  否->直接返回MQTT参数  是->创建
        Device device = findBy("imei", imei);
        Product product = productService.findById(2);
        String productKey = product.getProductKey();
        Map<String, Object> data = new HashMap<>();
        if (device == null) {
            //模拟1号产品初始化过程
            Device createDevice = new Device();
            createDevice.setImei(imei);
            createDevice.setIccid(iccid);
            createDevice.setUserId(0);
            createDevice.setProductId(2);
            //IOT创建设备
            Map<String, Object> result = iotHub.createDevice(productKey);
            String deviceName = result.get("device_name").toString();
            String deviceSecret = result.get("device_secret").toString();
            createDevice.setDeviceName(deviceName);
            createDevice.setDeviceSecret(deviceSecret);
            createDevice.setIccid(iccid);
            createDevice.setCreateTime(new Date());
            createDevice.setStatus("激活成功-未注册");
            int isSuccess = saveDeviceReturnId(createDevice); //提交事务
            //插入成功后取deviceId
            if (isSuccess == 1) {
                Device deviceInserted = findBy("imei", imei);
                int deviceId = deviceInserted.getId();
                data.put("deviceName", deviceName);
                data.put("deviceSecret", deviceSecret);
                data.put("userId", 0);
                data.put("deviceId", deviceId);
                data.put("productKey", productKey);
            }
        } else {
            data.put("deviceName", device.getDeviceName());
            data.put("deviceSecret", device.getDeviceSecret());
            data.put("userId", device.getUserId());
            data.put("deviceId", device.getId());
            data.put("productKey", productKey);
        }
        return data;
    }

    private int saveDeviceReturnId(Device device) {
        return deviceMapper.insertSelective(device);
    }
}
