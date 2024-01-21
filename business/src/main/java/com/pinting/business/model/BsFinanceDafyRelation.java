package com.pinting.business.model;

import java.util.Date;

public class BsFinanceDafyRelation {
    private Integer id;

    private Integer subAccountId;

    private Integer customerManagerDafyId;

    private Integer dafyDeptId;

    private String dafyDeptCode;

    private String dafyDeptName;

    private String note;

    private Date createTime;

    private Date updateTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSubAccountId() {
        return subAccountId;
    }

    public void setSubAccountId(Integer subAccountId) {
        this.subAccountId = subAccountId;
    }

    public Integer getCustomerManagerDafyId() {
        return customerManagerDafyId;
    }

    public void setCustomerManagerDafyId(Integer customerManagerDafyId) {
        this.customerManagerDafyId = customerManagerDafyId;
    }

    public Integer getDafyDeptId() {
        return dafyDeptId;
    }

    public void setDafyDeptId(Integer dafyDeptId) {
        this.dafyDeptId = dafyDeptId;
    }

    public String getDafyDeptCode() {
        return dafyDeptCode;
    }

    public void setDafyDeptCode(String dafyDeptCode) {
        this.dafyDeptCode = dafyDeptCode;
    }

    public String getDafyDeptName() {
        return dafyDeptName;
    }

    public void setDafyDeptName(String dafyDeptName) {
        this.dafyDeptName = dafyDeptName;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
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