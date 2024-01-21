package com.pinting.business.model;

import java.util.Date;

public class BsPayLimit {
    private Integer id;

    private String payBusinessType;

    private String timeType;

    private String timeStart;

    private String timeEnd;

    private String limitType;

    private String limitEquleType;

    private Integer limitVaule;

    private String isDelete;

    private Integer mUserId;

    private String note;

    private Date createTime;

    private Date updateTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPayBusinessType() {
        return payBusinessType;
    }

    public void setPayBusinessType(String payBusinessType) {
        this.payBusinessType = payBusinessType;
    }

    public String getTimeType() {
        return timeType;
    }

    public void setTimeType(String timeType) {
        this.timeType = timeType;
    }

    public String getTimeStart() {
        return timeStart;
    }

    public void setTimeStart(String timeStart) {
        this.timeStart = timeStart;
    }

    public String getTimeEnd() {
        return timeEnd;
    }

    public void setTimeEnd(String timeEnd) {
        this.timeEnd = timeEnd;
    }

    public String getLimitType() {
        return limitType;
    }

    public void setLimitType(String limitType) {
        this.limitType = limitType;
    }

    public String getLimitEquleType() {
        return limitEquleType;
    }

    public void setLimitEquleType(String limitEquleType) {
        this.limitEquleType = limitEquleType;
    }

    public Integer getLimitVaule() {
        return limitVaule;
    }

    public void setLimitVaule(Integer limitVaule) {
        this.limitVaule = limitVaule;
    }

    public String getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(String isDelete) {
        this.isDelete = isDelete;
    }

    public Integer getmUserId() {
        return mUserId;
    }

    public void setmUserId(Integer mUserId) {
        this.mUserId = mUserId;
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