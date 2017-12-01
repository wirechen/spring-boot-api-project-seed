package com.company.project.model;

import java.util.Date;
import javax.persistence.*;

public class Product {
    @Id
    private Integer id;

    @Column(name = "product_name")
    private String productName;

    @Column(name = "product_key")
    private String productKey;

    @Column(name = "product_desc")
    private String productDesc;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "modify_time")
    private Date modifyTime;

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
     * @return product_name
     */
    public String getProductName() {
        return productName;
    }

    /**
     * @param productName
     */
    public void setProductName(String productName) {
        this.productName = productName;
    }

    /**
     * @return product_key
     */
    public String getProductKey() {
        return productKey;
    }

    /**
     * @param productKey
     */
    public void setProductKey(String productKey) {
        this.productKey = productKey;
    }

    /**
     * @return product_desc
     */
    public String getProductDesc() {
        return productDesc;
    }

    /**
     * @param productDesc
     */
    public void setProductDesc(String productDesc) {
        this.productDesc = productDesc;
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
     * @return modify_time
     */
    public Date getModifyTime() {
        return modifyTime;
    }

    /**
     * @param modifyTime
     */
    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }
}