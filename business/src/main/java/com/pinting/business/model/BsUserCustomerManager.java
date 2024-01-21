package com.pinting.business.model;

import java.util.Date;

public class BsUserCustomerManager {
    private Integer id;

    private Integer customerManagerDafyId;

    private Integer userId;

    private Integer grade;

    private Date createTime;

    private Date updateTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCustomerManagerDafyId() {
        return customerManagerDafyId;
    }

    public void setCustomerManagerDafyId(Integer customerManagerDafyId) {
        this.customerManagerDafyId = customerManagerDafyId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getGrade() {
        return grade;
    }

    public void setGrade(Integer grade) {
        this.grade = grade;
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