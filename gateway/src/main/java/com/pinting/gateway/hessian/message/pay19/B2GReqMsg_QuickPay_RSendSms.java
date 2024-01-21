package com.pinting.gateway.hessian.message.pay19;

import java.util.Date;

import com.pinting.core.hessian.msg.ReqMsg;
import com.pinting.gateway.hessian.message.pay19.enums.VerifyCodeSendFlag;

public class B2GReqMsg_QuickPay_RSendSms extends ReqMsg {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5540195158236726240L;
	private String userId;
	private String orderId;
	private Date orderDate;
	private String mpOrderId;
	private VerifyCodeSendFlag verifyCodeSendFlag;

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

	public String getMpOrderId() {
		return mpOrderId;
	}

	public void setMpOrderId(String mpOrderId) {
		this.mpOrderId = mpOrderId;
	}

	public VerifyCodeSendFlag getVerifyCodeSendFlag() {
		return verifyCodeSendFlag;
	}

	public void setVerifyCodeSendFlag(VerifyCodeSendFlag verifyCodeSendFlag) {
		this.verifyCodeSendFlag = verifyCodeSendFlag;
	}

}
