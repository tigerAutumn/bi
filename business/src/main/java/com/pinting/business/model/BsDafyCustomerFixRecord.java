package com.pinting.business.model;

import java.util.Date;

public class BsDafyCustomerFixRecord {
    private Integer id;

    private Integer opUserId;

    private Integer userId;

    private Integer beforeDafyCustomerManagerId;

    private Integer afterDafyCustomerManagerId;

    private String beforeSubAccountId;

    private String afterSubAccountId;

    private Date splitTime;

    private Date opTime;

    private Date createTime;

    private Date updateTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getOpUserId() {
        return opUserId;
    }

    public void setOpUserId(Integer opUserId) {
        this.opUserId = opUserId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getBeforeDafyCustomerManagerId() {
        return beforeDafyCustomerManagerId;
    }

    public void setBeforeDafyCustomerManagerId(Integer beforeDafyCustomerManagerId) {
        this.beforeDafyCustomerManagerId = beforeDafyCustomerManagerId;
    }

    public Integer getAfterDafyCustomerManagerId() {
        return afterDafyCustomerManagerId;
    }

    public void setAfterDafyCustomerManagerId(Integer afterDafyCustomerManagerId) {
        this.afterDafyCustomerManagerId = afterDafyCustomerManagerId;
    }

    public String getBeforeSubAccountId() {
        return beforeSubAccountId;
    }

    public void setBeforeSubAccountId(String beforeSubAccountId) {
        this.beforeSubAccountId = beforeSubAccountId;
    }

    public String getAfterSubAccountId() {
        return afterSubAccountId;
    }

    public void setAfterSubAccountId(String afterSubAccountId) {
        this.afterSubAccountId = afterSubAccountId;
    }

    public Date getSplitTime() {
        return splitTime;
    }

    public void setSplitTime(Date splitTime) {
        this.splitTime = splitTime;
    }

    public Date getOpTime() {
        return opTime;
    }

    public void setOpTime(Date opTime) {
        this.opTime = opTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}