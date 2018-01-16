package com.company.project.model;

import com.company.project.core.JSONTypeHandler;
import org.apache.ibatis.type.JdbcType;
import tk.mybatis.mapper.annotation.ColumnType;

import java.util.Date;
import javax.persistence.*;

public class Device {
    @Id
    private Integer id;

    @Column(name = "iccid")
    private String iccid;

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

    private String imei;

    @Column(name = "site_name")
    private String siteName;

    @ColumnType(jdbcType = JdbcType.OTHER, typeHandler = JSONTypeHandler.class)
    private Object coordinates;

    @Column(name = "is_outage")
    private Integer isOutage;

    @Column(name = "is_wind")
    private Integer isWind;

    @Column(name = "site_high")
    private Float siteHigh;

    @Column(name = "site_img")
    private String siteImg;

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

    public String getIccid() {
        return iccid;
    }

    public void setIccid(String iccid) {
        this.iccid = iccid;
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

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public String getSiteName() {
        return siteName;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }

    public Object getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Object coordinates) {
        this.coordinates = coordinates;
    }

    public Integer getIsOutage() {
        return isOutage;
    }

    public void setIsOutage(Integer isOutage) {
        this.isOutage = isOutage;
    }

    public Integer getIsWind() {
        return isWind;
    }

    public void setIsWind(Integer isWind) {
        this.isWind = isWind;
    }

    public Float getSiteHigh() {
        return siteHigh;
    }

    public void setSiteHigh(Float siteHigh) {
        this.siteHigh = siteHigh;
    }

    public String getSiteImg() {
        return siteImg;
    }

    public void setSiteImg(String siteImg) {
        this.siteImg = siteImg;
    }
}