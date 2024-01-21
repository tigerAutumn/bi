/**
 * Wenzi.com Inc.
 * Copyright (c) 2015-2025 All Rights Reserved.
 */
package com.pinting.gateway.pay19.out.model.resp;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @author Baby shark love blowing wind
 * @version $Id: AccountDetail.java, v 0.1 2015-8-31 下午8:23:27 BabyShark Exp $
 */
public class AccountDetail implements Serializable {

    /**  */
    private static final long serialVersionUID = -8497821913549821255L;
    private String            orderNo;
    private String            mpOrderNo;
    private Date              reqTime;
    private Date              submitTime;
    private Date              finishTime;
    private Date              settleTime;
    private String            tradeType;
    private String            orderSource;
    private String            currency;
    private Double            amount;
    private Double            fee;
    private Double            settleAmount;
    private String            orderRemark;

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getMpOrderNo() {
        return mpOrderNo;
    }

    public void setMpOrderNo(String mpOrderNo) {
        this.mpOrderNo = mpOrderNo;
    }

    public Date getReqTime() {
        return reqTime;
    }

    public void setReqTime(Date reqTime) {
        this.reqTime = reqTime;
    }

    public Date getSubmitTime() {
        return submitTime;
    }

    public void setSubmitTime(Date submitTime) {
        this.submitTime = submitTime;
    }

    public Date getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(Date finishTime) {
        this.finishTime = finishTime;
    }

    public Date getSettleTime() {
        return settleTime;
    }

    public void setSettleTime(Date settleTime) {
        this.settleTime = settleTime;
    }

    public String getTradeType() {
        return tradeType;
    }

    public void setTradeType(String tradeType) {
        this.tradeType = tradeType;
    }

    public String getOrderSource() {
        return orderSource;
    }

    public void setOrderSource(String orderSource) {
        this.orderSource = orderSource;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Double getFee() {
        return fee;
    }

    public void setFee(Double fee) {
        this.fee = fee;
    }

    public Double getSettleAmount() {
        return settleAmount;
    }

    public void setSettleAmount(Double settleAmount) {
        this.settleAmount = settleAmount;
    }

    public String getOrderRemark() {
        return orderRemark;
    }

    public void setOrderRemark(String orderRemark) {
        this.orderRemark = orderRemark;
    }

}
