package com.pinting.business.model;

import java.util.Date;

public class Bs19payCheckError {
    private Integer id;

    private String orderNo;

    private String sysStatus;

    private String checkFileStatus;

    private Integer isDeal;

    private Integer dealUserId;

    private String checkFileName;

    private Double sysAmount;

    private Double doneAmount;

    private Date createTime;

    private Date dealTime;

    private String merchantNo;

    private String channel;

    private String partnerCode;

    private String businessType;

    private String bfOrderNo;

    private String hostSysStatus;

    private String info;

    private String note;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo == null ? null : orderNo.trim();
    }

    public String getSysStatus() {
        return sysStatus;
    }

    public void setSysStatus(String sysStatus) {
        this.sysStatus = sysStatus == null ? null : sysStatus.trim();
    }

    public String getCheckFileStatus() {
        return checkFileStatus;
    }

    public void setCheckFileStatus(String checkFileStatus) {
        this.checkFileStatus = checkFileStatus == null ? null : checkFileStatus.trim();
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

    public String getCheckFileName() {
        return checkFileName;
    }

    public void setCheckFileName(String checkFileName) {
        this.checkFileName = checkFileName == null ? null : checkFileName.trim();
    }

    public Double getSysAmount() {
        return sysAmount;
    }

    public void setSysAmount(Double sysAmount) {
        this.sysAmount = sysAmount;
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

    public Date getDealTime() {
        return dealTime;
    }

    public void setDealTime(Date dealTime) {
        this.dealTime = dealTime;
    }

    public String getMerchantNo() {
        return merchantNo;
    }

    public void setMerchantNo(String merchantNo) {
        this.merchantNo = merchantNo == null ? null : merchantNo.trim();
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel == null ? null : channel.trim();
    }

    public String getPartnerCode() {
        return partnerCode;
    }

    public void setPartnerCode(String partnerCode) {
        this.partnerCode = partnerCode == null ? null : partnerCode.trim();
    }

    public String getBusinessType() {
        return businessType;
    }

    public void setBusinessType(String businessType) {
        this.businessType = businessType == null ? null : businessType.trim();
    }

    public String getBfOrderNo() {
        return bfOrderNo;
    }

    public void setBfOrderNo(String bfOrderNo) {
        this.bfOrderNo = bfOrderNo == null ? null : bfOrderNo.trim();
    }

    public String getHostSysStatus() {
        return hostSysStatus;
    }

    public void setHostSysStatus(String hostSysStatus) {
        this.hostSysStatus = hostSysStatus == null ? null : hostSysStatus.trim();
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info == null ? null : info.trim();
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note == null ? null : note.trim();
    }
}