package com.pinting.business.model;

import java.util.Date;

public class BsServiceFee {
    private Integer id;

    private String feeType;

    private String orderNo;

    private Integer subAccountId;

    private Double transAmount;

    private Double planFee;

    private Double doneFee;

    private Double paymentPlatformFee;

    private String status;

    private String note;

    private Date createTime;

    private Date updateTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFeeType() {
        return feeType;
    }

    public void setFeeType(String feeType) {
        this.feeType = feeType;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public Integer getSubAccountId() {
        return subAccountId;
    }

    public void setSubAccountId(Integer subAccountId) {
        this.subAccountId = subAccountId;
    }

    public Double getTransAmount() {
        return transAmount;
    }

    public void setTransAmount(Double transAmount) {
        this.transAmount = transAmount;
    }

    public Double getPlanFee() {
        return planFee;
    }

    public void setPlanFee(Double planFee) {
        this.planFee = planFee;
    }

    public Double getDoneFee() {
        return doneFee;
    }

    public void setDoneFee(Double doneFee) {
        this.doneFee = doneFee;
    }

    public Double getPaymentPlatformFee() {
        return paymentPlatformFee;
    }

    public void setPaymentPlatformFee(Double paymentPlatformFee) {
        this.paymentPlatformFee = paymentPlatformFee;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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