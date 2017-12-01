package com.conpany.project.service;

import com.company.project.service.DeviceService;
import com.conpany.project.Tester;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class TestDeviceService extends Tester {

    private Logger logger = LoggerFactory.getLogger(TestDeviceService.class);

    @Autowired
    private DeviceService deviceService;

    /**
     * 1、productId数据库必须存在
     * 2、deviceName不能重复
     */
    @Test
    public void createNormalDevice() {
        String info = deviceService.createNormalDevice(0,11, "aaaaaaa");
        logger.info("=======createNormalDevice====" + info);
        Assert.assertTrue("success".equals(info));
    }
}
