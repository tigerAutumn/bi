package com.pinting.business.model;

import java.util.Date;

public class Bs19payCheckRecord {
    private Integer id;

    private String orderNo;

    private String batchSerialNo;

    private String relativeOrderNo;

    private String pay19OrderJnl;

    private Date tradeCompanyOrderTime;

    private Date orderSubmitTime;

    private Date orderFinishTime;

    private Date orderSettleTime;

    private String tranType;

    private String orderSrc;

    private String currencyType;

    private Double tranAmount;

    private Double commission;

    private Double settleAmount;

    private String orderDesc;

    private String tradeCompanyNote;

    private String refundOrderNo;

    private Date refundCreateTime;

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
        this.orderNo = orderNo;
    }

    public String getBatchSerialNo() {
        return batchSerialNo;
    }

    public void setBatchSerialNo(String batchSerialNo) {
        this.batchSerialNo = batchSerialNo;
    }

    public String getRelativeOrderNo() {
        return relativeOrderNo;
    }

    public void setRelativeOrderNo(String relativeOrderNo) {
        this.relativeOrderNo = relativeOrderNo;
    }

    public String getPay19OrderJnl() {
        return pay19OrderJnl;
    }

    public void setPay19OrderJnl(String pay19OrderJnl) {
        this.pay19OrderJnl = pay19OrderJnl;
    }

    public Date getTradeCompanyOrderTime() {
        return tradeCompanyOrderTime;
    }

    public void setTradeCompanyOrderTime(Date tradeCompanyOrderTime) {
        this.tradeCompanyOrderTime = tradeCompanyOrderTime;
    }

    public Date getOrderSubmitTime() {
        return orderSubmitTime;
    }

    public void setOrderSubmitTime(Date orderSubmitTime) {
        this.orderSubmitTime = orderSubmitTime;
    }

    public Date getOrderFinishTime() {
        return orderFinishTime;
    }

    public void setOrderFinishTime(Date orderFinishTime) {
        this.orderFinishTime = orderFinishTime;
    }

    public Date getOrderSettleTime() {
        return orderSettleTime;
    }

    public void setOrderSettleTime(Date orderSettleTime) {
        this.orderSettleTime = orderSettleTime;
    }

    public String getTranType() {
        return tranType;
    }

    public void setTranType(String tranType) {
        this.tranType = tranType;
    }

    public String getOrderSrc() {
        return orderSrc;
    }

    public void setOrderSrc(String orderSrc) {
        this.orderSrc = orderSrc;
    }

    public String getCurrencyType() {
        return currencyType;
    }

    public void setCurrencyType(String currencyType) {
        this.currencyType = currencyType;
    }

    public Double getTranAmount() {
        return tranAmount;
    }

    public void setTranAmount(Double tranAmount) {
        this.tranAmount = tranAmount;
    }

    public Double getCommission() {
        return commission;
    }

    public void setCommission(Double commission) {
        this.commission = commission;
    }

    public Double getSettleAmount() {
        return settleAmount;
    }

    public void setSettleAmount(Double settleAmount) {
        this.settleAmount = settleAmount;
    }

    public String getOrderDesc() {
        return orderDesc;
    }

    public void setOrderDesc(String orderDesc) {
        this.orderDesc = orderDesc;
    }

    public String getTradeCompanyNote() {
        return tradeCompanyNote;
    }

    public void setTradeCompanyNote(String tradeCompanyNote) {
        this.tradeCompanyNote = tradeCompanyNote;
    }

    public String getRefundOrderNo() {
        return refundOrderNo;
    }

    public void setRefundOrderNo(String refundOrderNo) {
        this.refundOrderNo = refundOrderNo;
    }

    public Date getRefundCreateTime() {
        return refundCreateTime;
    }

    public void setRefundCreateTime(Date refundCreateTime) {
        this.refundCreateTime = refundCreateTime;
    }
}