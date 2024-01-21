package com.pinting.business.model;

import java.util.Date;

import com.pinting.business.model.vo.PageInfoObject;

public class BsCheckErrorJnl  extends PageInfoObject{
    private Integer id;

    private Integer transJnlId;

    private Integer checkJnlId;

    private Integer userId;

    private Integer sysStatus;

    private Integer checkFileStatus;

    private Integer isDeal;

    private Integer dealUserId;

    private Integer checkFileId;

    private Date createTime;

    private Date dealTime;

    private String info;

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

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getSysStatus() {
        return sysStatus;
    }

    public void setSysStatus(Integer sysStatus) {
        this.sysStatus = sysStatus;
    }

    public Integer getCheckFileStatus() {
        return checkFileStatus;
    }

    public void setCheckFileStatus(Integer checkFileStatus) {
        this.checkFileStatus = checkFileStatus;
    }

    public Integer getIsDeal() {
        return isDeal;
    }

    public void setIsDeal(Integer isDeal) {
        this.isDeal = isDeal;
    }

    public Integer getDealUserId() {
        return dealUserId;
    }

    public void setDealUserId(Integer dealUserId) {
        this.dealUserId = dealUserId;
    }

    public Integer getCheckFileId() {
        return checkFileId;
    }

    public void setCheckFileId(Integer checkFileId) {
        this.checkFileId = checkFileId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getDealTime() {
        return dealTime;
    }

    public void setDealTime(Date dealTime) {
        this.dealTime = dealTime;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}