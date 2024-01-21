package com.pinting.business.model;

import java.util.Date;

public class LnMarketGrantRecord {
    private Integer id;

    private String partnerCode;

    private String partnerOrderNo;

    private String grantItem;

    private String partnerUserId;

    private String bgwBindId;

    private Double amount;

    private String orderNo;

    private Date finishTime;

    private String status;

    private String informStatus;

    private Date createTime;

    private Date updateTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPartnerCode() {
        return partnerCode;
    }

    public void setPartnerCode(String partnerCode) {
        this.partnerCode = partnerCode;
    }

    public String getPartnerOrderNo() {
        return partnerOrderNo;
    }

    public void setPartnerOrderNo(String partnerOrderNo) {
        this.partnerOrderNo = partnerOrderNo;
    }

    public String getGrantItem() {
        return grantItem;
    }

    public void setGrantItem(String grantItem) {
        this.grantItem = grantItem;
    }

    public String getPartnerUserId() {
        return partnerUserId;
    }

    public void setPartnerUserId(String partnerUserId) {
        this.partnerUserId = partnerUserId;
    }

    public String getBgwBindId() {
        return bgwBindId;
    }

    public void setBgwBindId(String bgwBindId) {
        this.bgwBindId = bgwBindId;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public Date getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(Date finishTime) {
        this.finishTime = finishTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getInformStatus() {
        return informStatus;
    }

    public void setInformStatus(String informStatus) {
        this.informStatus = informStatus;
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