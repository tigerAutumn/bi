/**
 * Wenzi.com Inc.
 * Copyright (c) 2015-2025 All Rights Reserved.
 */
package com.pinting.gateway.pay19.out.model.req;

/**
 * 
 * @author Baby shark love blowing wind
 * @version $Id: AcctTransReq.java, v 0.1 2015-11-3 下午4:48:24 BabyShark Exp $
 */
public class AcctTransReq extends AcctTransBaseReq {

    /**  */
    private static final long serialVersionUID = -6260262277123268423L;
    private String            orderId;
    private String            orderDate;
    private String            orderAmount;
    private String            accountFrom;
    private String            accountTo;
    private String            toAcctType;
    private String            toAcctName;
    private String            tradeType;
    private String            tradeDesc;
    private String            notifyUrl;
    private String            remarks;
    private String            reserved;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public String getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(String orderAmount) {
        this.orderAmount = orderAmount;
    }

    public String getAccountFrom() {
        return accountFrom;
    }

    public void setAccountFrom(String accountFrom) {
        this.accountFrom = accountFrom;
    }

    public String getAccountTo() {
        return accountTo;
    }

    public void setAccountTo(String accountTo) {
        this.accountTo = accountTo;
    }

    public String getToAcctType() {
        return toAcctType;
    }

    public void setToAcctType(String toAcctType) {
        this.toAcctType = toAcctType;
    }

    public String getToAcctName() {
        return toAcctName;
    }

    public void setToAcctName(String toAcctName) {
        this.toAcctName = toAcctName;
    }

    public String getTradeType() {
        return tradeType;
    }

    public void setTradeType(String tradeType) {
        this.tradeType = tradeType;
    }

    public String getTradeDesc() {
        return tradeDesc;
    }

    public void setTradeDesc(String tradeDesc) {
        this.tradeDesc = tradeDesc;
    }

    public String getNotifyUrl() {
        return notifyUrl;
    }

    public void setNotifyUrl(String notifyUrl) {
        this.notifyUrl = notifyUrl;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getReserved() {
        return reserved;
    }

    public void setReserved(String reserved) {
        this.reserved = reserved;
    }
}
