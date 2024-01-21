package com.pinting.business.model;

import java.util.Date;

public class LnBadDetail {
    private Integer id;

    private Integer badId;

    private String subjectCode;

    private Double doneAmount;

    private Date createTime;

    private Date updateTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getBadId() {
        return badId;
    }

    public void setBadId(Integer badId) {
        this.badId = badId;
    }

    public String getSubjectCode() {
        return subjectCode;
    }

    public void setSubjectCode(String subjectCode) {
        this.subjectCode = subjectCode;
    }

    public Double getDoneAmount() {
        return doneAmount;
    }

    public void setDoneAmount(Double doneAmount) {
        this.doneAmount = doneAmount;
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