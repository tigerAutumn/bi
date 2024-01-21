package com.pinting.gateway.hessian.message.pay19;

import java.util.Date;

import com.pinting.core.hessian.msg.ResMsg;

public class B2GResMsg_QuickPay_PreOrder extends ResMsg {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5396678701197232244L;
	private String errorCode;
	private String userId;
	private String orderId;
	private Date orderDate;
	private String mpOrderId;

	private String signUrl;
	private String signParam;

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getSignParam() {
		return signParam;
	}

	public void setSignParam(String signParam) {
		this.signParam = signParam;
	}

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

	public String getSignUrl() {
		return signUrl;
	}

	public void setSignUrl(String signUrl) {
		this.signUrl = signUrl;
	}

}
