package com.company.project.service;
import com.alibaba.fastjson.JSONObject;
import com.company.project.model.Device;
import com.company.project.core.Service;

import java.util.Map;


/**
 * Created by CodeGenerator on 2017/11/27.
 */
public interface DeviceService extends Service<Device> {

    String createNormalDevice(int userId, int productId, String deviceName);

    Map<String, String> activeDevice(int id);

    Map<String, String> publishSpots(JSONObject jsonObject);

    void initDeviceService(String imei, int productId);

    Map<String,Object> activeDeviceService(String imei, String iccid);

    void registDeviceService(Device device);

    void switchMsn(String switchMsn);

    //2号产品设备激活
    Map<String,Object> P2DeviceActive(String imei, String iccid);
}
