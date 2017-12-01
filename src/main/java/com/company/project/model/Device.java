package com.company.project.model;

import java.util.Date;
import javax.persistence.*;

public class Device {
    @Id
    private Integer id;

    @Column(name = "device_id")
    private String deviceId;

    @Column(name = "device_secret")
    private String deviceSecret;

    @Column(name = "device_name")
    private String deviceName;

    private String status;

    @Column(name = "product_id")
    private Integer productId;

    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "last_online_time")
    private Date lastOnlineTime;

    @Column(name = "last_offline_time")
    private Date lastOfflineTime;

    /**
     * @return id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return device_id
     */
    public String getDeviceId() {
        return deviceId;
    }

    /**
     * @param deviceId
     */
    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    /**
     * @return device_secret
     */
    public String getDeviceSecret() {
        return deviceSecret;
    }

    /**
     * @param deviceSecret
     */
    public void setDeviceSecret(String deviceSecret) {
        this.deviceSecret = deviceSecret;
    }

    /**
     * @return device_name
     */
    public String getDeviceName() {
        return deviceName;
    }

    /**
     * @param deviceName
     */
    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    /**
     * @return status
     */
    public String getStatus() {
        return status;
    }

    /**
     * @param status
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * @return product_id
     */
    public Integer getProductId() {
        return productId;
    }

    /**
     * @param productId
     */
    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    /**
     * @return user_id
     */
    public Integer getUserId() {
        return userId;
    }

    /**
     * @param userId
     */
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    /**
     * @return create_time
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * @param createTime
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * @return last_online_time
     */
    public Date getLastOnlineTime() {
        return lastOnlineTime;
    }

    /**
     * @param lastOnlineTime
     */
    public void setLastOnlineTime(Date lastOnlineTime) {
        this.lastOnlineTime = lastOnlineTime;
    }

    /**
     * @return last_offline_time
     */
    public Date getLastOfflineTime() {
        return lastOfflineTime;
    }

    /**
     * @param lastOfflineTime
     */
    public void setLastOfflineTime(Date lastOfflineTime) {
        this.lastOfflineTime = lastOfflineTime;
    }
}