package com.company.project.configurer;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "com.aliyun")
@Component
public class PropertiesConfigurer {

    //IotHub
    public static class Iot {
        private String accessKey;
        private String accessSecret;

        public String getAccessKey() {
            return accessKey;
        }

        public void setAccessKey(String accessKey) {
            this.accessKey = accessKey;
        }

        public String getAccessSecret() {
            return accessSecret;
        }

        public void setAccessSecret(String accessSecret) {
            this.accessSecret = accessSecret;
        }
    }

    //MNS
    public static class Mns {
        private String accessKey;
        private String accessSecret;
        private String endpoint;
        private String queueName;

        public String getAccessKey() {
            return accessKey;
        }

        public void setAccessKey(String accessKey) {
            this.accessKey = accessKey;
        }

        public String getAccessSecret() {
            return accessSecret;
        }

        public void setAccessSecret(String accessSecret) {
            this.accessSecret = accessSecret;
        }

        public String getEndpoint() {
            return endpoint;
        }

        public void setEndpoint(String endpoint) {
            this.endpoint = endpoint;
        }

        public String getQueueName() {
            return queueName;
        }

        public void setQueueName(String queueName) {
            this.queueName = queueName;
        }
    }


    private Iot iot;
    private Mns mns;

    public Iot getIot() {
        return iot;
    }

    public void setIot(Iot iot) {
        this.iot = iot;
    }

    public Mns getMns() {
        return mns;
    }

    public void setMns(Mns mns) {
        this.mns = mns;
    }
}
