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

}
