package com.pinting.business.model;

import java.util.Date;

public class BsOperationReport {
    private Integer id;

    private String reportName;

    private String displayTime;

    private String imgUrl;

    private String storageAddress;

    private String isSugguest;

    private String showTerminal;

    private Integer opUserId;

    private Date createTime;

    private Date updateTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getReportName() {
        return reportName;
    }

    public void setReportName(String reportName) {
        this.reportName = reportName;
    }

    public String getDisplayTime() {
        return displayTime;
    }

    public void setDisplayTime(String displayTime) {
        this.displayTime = displayTime;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getStorageAddress() {
        return storageAddress;
    }

    public void setStorageAddress(String storageAddress) {
        this.storageAddress = storageAddress;
    }

    public String getIsSugguest() {
        return isSugguest;
    }

    public void setIsSugguest(String isSugguest) {
        this.isSugguest = isSugguest;
    }

    public String getShowTerminal() {
        return showTerminal;
    }

    public void setShowTerminal(String showTerminal) {
        this.showTerminal = showTerminal;
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