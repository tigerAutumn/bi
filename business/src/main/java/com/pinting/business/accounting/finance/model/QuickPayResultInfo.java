package com.pinting.business.accounting.finance.model;

import com.pinting.core.hessian.msg.ReqMsg;

import java.util.Date;

/**
 * Created by babyshark on 2016/9/11.
 */
public class QuickPayResultInfo extends ReqMsg {

    private String status;
    private String errorCode;
    private String errorMsg;
    private String orderId;
    private String mpOrderId;
    private Date orderFinTime;
    private Double amount;
    private String orderRemarkDesc;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getMpOrderId() {
        return mpOrderId;
    }

    public void setMpOrderId(String mpOrderId) {
        this.mpOrderId = mpOrderId;
    }

    public Date getOrderFinTime() {
        return orderFinTime;
    }

    public void setOrderFinTime(Date orderFinTime) {
        this.orderFinTime = orderFinTime;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getOrderRemarkDesc() {
        return orderRemarkDesc;
    }

    public void setOrderRemarkDesc(String orderRemarkDesc) {
        this.orderRemarkDesc = orderRemarkDesc;
    }
}
