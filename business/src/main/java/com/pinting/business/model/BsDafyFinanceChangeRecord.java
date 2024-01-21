package com.pinting.business.model;

import java.util.Date;

public class BsDafyFinanceChangeRecord {
    private Integer id;

    private Integer userId;

    private String oldCustomerManagerName;

    private String newCustomerManagerName;

    private Integer oldCustomerManagerDafyId;

    private Integer newCustomerManagerDafyId;

    private Integer oldDafyDeptId;

    private Integer newDafyDeptId;

    private String oldDafyDeptCode;

    private String newDafyDeptCode;

    private String oldDafyDeptName;

    private String newDafyDeptName;

    private String note;

    private Integer opUserId;

    private Date createTime;

    private Date updateTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getOldCustomerManagerName() {
        return oldCustomerManagerName;
    }

    public void setOldCustomerManagerName(String oldCustomerManagerName) {
        this.oldCustomerManagerName = oldCustomerManagerName;
    }

    public String getNewCustomerManagerName() {
        return newCustomerManagerName;
    }

    public void setNewCustomerManagerName(String newCustomerManagerName) {
        this.newCustomerManagerName = newCustomerManagerName;
    }

    public Integer getOldCustomerManagerDafyId() {
        return oldCustomerManagerDafyId;
    }

    public void setOldCustomerManagerDafyId(Integer oldCustomerManagerDafyId) {
        this.oldCustomerManagerDafyId = oldCustomerManagerDafyId;
    }

    public Integer getNewCustomerManagerDafyId() {
        return newCustomerManagerDafyId;
    }

    public void setNewCustomerManagerDafyId(Integer newCustomerManagerDafyId) {
        this.newCustomerManagerDafyId = newCustomerManagerDafyId;
    }

    public Integer getOldDafyDeptId() {
        return oldDafyDeptId;
    }

    public void setOldDafyDeptId(Integer oldDafyDeptId) {
        this.oldDafyDeptId = oldDafyDeptId;
    }

    public Integer getNewDafyDeptId() {
        return newDafyDeptId;
    }

    public void setNewDafyDeptId(Integer newDafyDeptId) {
        this.newDafyDeptId = newDafyDeptId;
    }

    public String getOldDafyDeptCode() {
        return oldDafyDeptCode;
    }

    public void setOldDafyDeptCode(String oldDafyDeptCode) {
        this.oldDafyDeptCode = oldDafyDeptCode;
    }

    public String getNewDafyDeptCode() {
        return newDafyDeptCode;
    }

    public void setNewDafyDeptCode(String newDafyDeptCode) {
        this.newDafyDeptCode = newDafyDeptCode;
    }

    public String getOldDafyDeptName() {
        return oldDafyDeptName;
    }

    public void setOldDafyDeptName(String oldDafyDeptName) {
        this.oldDafyDeptName = oldDafyDeptName;
    }

    public String getNewDafyDeptName() {
        return newDafyDeptName;
    }

    public void setNewDafyDeptName(String newDafyDeptName) {
        this.newDafyDeptName = newDafyDeptName;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Integer getOpUserId() {
        return opUserId;
    }

    public void setOpUserId(Integer opUserId) {
        this.opUserId = opUserId;
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