package com.pinting.business.accounting.loan.model;

import com.pinting.gateway.hessian.message.pay19.enums.OrderStatus;

import java.util.Date;

/**
 * Created by babyshark on 2016/9/2.
 */
public class ReceiveMoneyNoticeInfo {
    private String mxOrderId;
    private OrderStatus orderStatus;
    private Date finishTime;
    private Double amount;
    private String retCode;
    private String errorMsg;

    public String getMxOrderId() {
        return mxOrderId;
    }

    public void setMxOrderId(String mxOrderId) {
        this.mxOrderId = mxOrderId;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Date getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(Date finishTime) {
        this.finishTime = finishTime;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getRetCode() {
        return retCode;
    }

    public void setRetCode(String retCode) {
        this.retCode = retCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }
}
