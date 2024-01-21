package com.pinting.gateway.hessian.message.pay19;

import java.util.Date;

import com.pinting.core.hessian.msg.ReqMsg;

public class B2GReqMsg_QuickPay_ConfirmOrder extends ReqMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8121578501467360372L;
	private String            userId;
    private String            orderId;
    private Date              orderDate;
    private Double            amount;
    private String            mpOrderId;
    private String            verifyCode;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getMpOrderId() {
        return mpOrderId;
    }

    public void setMpOrderId(String mpOrderId) {
        this.mpOrderId = mpOrderId;
    }

    public String getVerifyCode() {
        return verifyCode;
    }

    public void setVerifyCode(String verifyCode) {
        this.verifyCode = verifyCode;
    }
}
