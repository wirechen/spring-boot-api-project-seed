package com.company.project.web;

import com.alibaba.fastjson.JSONObject;
import com.company.project.core.Result;
import com.company.project.core.ResultGenerator;
import com.company.project.model.Device;
import com.company.project.service.DeviceService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
* Created by CodeGenerator on 2017/11/27.
*/
@RestController
@RequestMapping("/api/v1/device")
public class DeviceController {
    @Resource
    private DeviceService deviceService;

    @PostMapping
    public Result add(@RequestBody JSONObject object) {
        int productId = object.getInteger("product_id");
        String deviceName = object.getString("device_name");
        int userId = (Integer)object.getOrDefault("user_id", 0);
        String result = deviceService.createNormalDevice(userId, productId, deviceName);
        if ("success".equals(result)) {
            return ResultGenerator.genSuccessResult();
        } else {
            return ResultGenerator.genFailResult(result);
        }
    }

//    @DeleteMapping("/{id}")
//    public Result delete(@PathVariable Integer id) {
//        deviceService.deleteById(id);
//        return ResultGenerator.genSuccessResult();
//    }

//    @PutMapping
//    public Result update(@RequestBody Device device) {
//        deviceService.update(device);
//        return ResultGenerator.genSuccessResult();
//    }

    @GetMapping("/{id}")
    public Result detail(@PathVariable Integer id) {
        Device device = deviceService.findById(id);
        return ResultGenerator.genSuccessResult(device);
    }

    @GetMapping
    public Result list(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "0") Integer size) {
        PageHelper.startPage(page, size);
        List<Device> list = deviceService.findAll();
        PageInfo pageInfo = new PageInfo(list);
        return ResultGenerator.genSuccessResult(pageInfo);
    }

    @PostMapping("/active")
    public Result activeTestDevice(@RequestBody JSONObject object) {
        int id = object.getInteger("id");
        Map<String, String> map = deviceService.activeDevice(id);
        return ResultGenerator.genSuccessResult(map);
    }

    @PostMapping("/publish")
    public Result publishTestSpots(@RequestBody JSONObject object) {
        JSONObject jsonObject = object.getJSONObject("data");
        Map<String, String> map = deviceService.publishSpots(jsonObject);
        return ResultGenerator.genSuccessResult(map);
    }
}
