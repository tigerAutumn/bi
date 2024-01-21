package com.pinting.business.model;

import java.util.Date;

public class BsAdjustJnl {
    private Integer id;

    private Integer transJnlId;

    private Integer checkJnlId;

    private Integer sysStatus;

    private Integer userId;

    private Integer checkFileStatus;

    private Integer checkFileId;

    private Integer isAdjust;

    private Integer adjustUserId;

    private Date createTime;

    private Date adjustTime;

    private String note;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTransJnlId() {
        return transJnlId;
    }

    public void setTransJnlId(Integer transJnlId) {
        this.transJnlId = transJnlId;
    }

    public Integer getCheckJnlId() {
        return checkJnlId;
    }

    public void setCheckJnlId(Integer checkJnlId) {
        this.checkJnlId = checkJnlId;
    }

    public Integer getSysStatus() {
        return sysStatus;
    }

    public void setSysStatus(Integer sysStatus) {
        this.sysStatus = sysStatus;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getCheckFileStatus() {
        return checkFileStatus;
    }

    public void setCheckFileStatus(Integer checkFileStatus) {
        this.checkFileStatus = checkFileStatus;
    }

    public Integer getCheckFileId() {
        return checkFileId;
    }

    public void setCheckFileId(Integer checkFileId) {
        this.checkFileId = checkFileId;
    }

    public Integer getIsAdjust() {
        return isAdjust;
    }

    public void setIsAdjust(Integer isAdjust) {
        this.isAdjust = isAdjust;
    }

    public Integer getAdjustUserId() {
        return adjustUserId;
    }

    public void setAdjustUserId(Integer adjustUserId) {
        this.adjustUserId = adjustUserId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getAdjustTime() {
        return adjustTime;
    }

    public void setAdjustTime(Date adjustTime) {
        this.adjustTime = adjustTime;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}