package com.company.project.model;

import com.company.project.core.JSONTypeHandler;
import org.apache.ibatis.type.JdbcType;
import tk.mybatis.mapper.annotation.ColumnType;

import java.util.Date;
import javax.persistence.*;

public class Spots {
    @Id
    private Integer id;

    @Column(name = "device_id")
    private Integer deviceId;

    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "inserted_at")
    private Date insertedAt;

    @ColumnType(jdbcType = JdbcType.OTHER, typeHandler = JSONTypeHandler.class)
    private Object data;

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
    public Integer getDeviceId() {
        return deviceId;
    }

    /**
     * @param deviceId
     */
    public void setDeviceId(Integer deviceId) {
        this.deviceId = deviceId;
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
     * @return inserted_at
     */
    public Date getInsertedAt() {
        return insertedAt;
    }

    /**
     * @param insertedAt
     */
    public void setInsertedAt(Date insertedAt) {
        this.insertedAt = insertedAt;
    }

    /**
     * @return data
     */
    public Object getData() {
        return data;
    }

    /**
     * @param data
     */
    public void setData(Object data) {
        this.data = data;
    }
}