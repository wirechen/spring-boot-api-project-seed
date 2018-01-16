package com.company.project.aliyun;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.aliyun.mns.client.CloudAccount;
import com.aliyun.mns.client.CloudQueue;
import com.aliyun.mns.client.MNSClient;
import com.aliyun.mns.model.Message;
import com.company.project.configurer.PropertiesConfigurer;
import com.company.project.model.Device;
import com.company.project.model.Spots;
import com.company.project.service.DeviceService;
import com.company.project.service.SpotsService;
import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Component
public class MnsLocalServer {

    private Logger logger = LoggerFactory.getLogger(MnsLocalServer.class);

    @Autowired
    private PropertiesConfigurer config;
    @Autowired
    private DeviceService deviceService;
    @Autowired
    private SpotsService spotsService;

    //全局变量 控制是否监听mns消息服务
    private Boolean switchMsn = true;

    public void setSwitchMsn(Boolean switchMsn) {
        this.switchMsn = switchMsn;
    }

    /**
     * MNS 消息服务
     */
    public void messageMQ() {

        final String AccessKeyId = config.getMns().getAccessKey();
        final String AccessKeySecret = config.getMns().getAccessSecret();
        final String AccountEndpoint = config.getMns().getEndpoint();
        final String QueueName = config.getMns().getQueueName();

        CloudAccount account = new CloudAccount(AccessKeyId, AccessKeySecret,AccountEndpoint);

        MNSClient client = account.getMNSClient();
        CloudQueue queue = client.getQueueRef(QueueName); //参数请输入IoT自动创建的队列名称，例如上面截图中的aliyun-iot-3AbL0062osF
        Boolean flag = true;

        while (switchMsn) {
            // 获取消息
//            System.out.println(queue.popMessage().getMessageBody());
//            if (popMsgd != null) {
//                System.out.println("=====  " +popMsgd.size());
//            }
            Message popMsg = queue.popMessage(10);
            if (popMsg != null) {
                String originalData = popMsg.getMessageBodyAsRawString();//获取原始消息（Base64编码后的数据）
                /**
                 {
                 "messageid":"12345",
                 "messagetype":"status/upload",
                 "topic":"null/topic",
                 "payload": {data},  //数据为Base64 Encode的数据 eg:{"lastTime":"2017-11-27 17:02:15.225","time":"2017-11-27 17:02:30.576","productKey":"NiKJvQNpUcN","deviceName":"java_device_test","status":"offline"}
                 "timestamp": 1469564576
                 }
                 */
                String originalDataDe = new String(Base64.decodeBase64(originalData));
                JSONObject message = JSON.parseObject(originalDataDe);
                String messageType = message.getString("messagetype");

                String payload = message.get("payload").toString();
                String payloadDe = new String(Base64.decodeBase64(payload));
                JSONObject data = null;
                logger.info("originalDataDe:" + originalDataDe);
                logger.info("payload:" + payload);
                logger.info("payloadDe:" + payloadDe);

                try {
                    data = JSON.parseObject(payloadDe);
                    logger.info("PopMessage Body: " + data);
                } catch (Exception e) {
                    logger.error("PopMessage Body不是JSON格式");
                    flag = false;
                }

                if ("status".equals(messageType)) { // 状态 -> 更新设备状态

                    String deviceName = data.getString("deviceName");
                    Device device = deviceService.findBy("deviceName", deviceName);
                    if (device == null) {
                        logger.error("没有相应的deviceName");
                    } else {
                        String status = data.getString("status").toUpperCase();
                        device.setStatus(status);

                        String time = data.getString("time");
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        try {
                            if ("OFFLINE".equals(status)) {
                                device.setLastOfflineTime(sdf.parse(time));
                            } else {
                                device.setLastOnlineTime(sdf.parse(time));
                            }
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        deviceService.update(device);
                    }

                } else if ("upload".equals(messageType) && flag) { // 数据上传 -> 入库

                    try{
                        int deviceId = data.getInteger("device_id");
                        int userId = data.getInteger("user_id");
                        long collectedTime = 0;
                        collectedTime = Long.parseLong(data.getString("collected_at"));
                        JSONObject spotsData = data.getJSONObject("data");
                        Spots spots = new Spots();
                        spots.setDeviceId(deviceId);
                        spots.setUserId(userId);
                        spots.setInsertedAt(new Date(collectedTime));
                        spots.setData(spotsData);
                        spotsService.save(spots);
                    }catch (Exception e){

                    }

                }

                //从队列中删除消息
                queue.deleteMessage(popMsg.getReceiptHandle());
            } else {
                System.out.println("Continuing --- " + new Date());
            }
        }
    }
}
