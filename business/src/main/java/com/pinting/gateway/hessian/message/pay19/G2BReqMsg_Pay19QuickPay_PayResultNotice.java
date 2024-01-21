package com.pinting.gateway.hessian.message.pay19;

import java.util.Date;

import com.pinting.core.hessian.msg.ReqMsg;

public class G2BReqMsg_Pay19QuickPay_PayResultNotice extends ReqMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8551072817120279531L;
	private String verifyString;
	private String status;
	private String errorCode;
	private String errorMsg;
	private String userId;
	private String orderId;
	private Date orderDate;
	private Date orderSubTime;
	private Date orderFinTime;
	private Double amount;
	private String currency;
	private String mpOrderId;
	private String tradeType;
	private String orderPName;
	private String orderRemarkDesc;
	private String commonRetrieveParam;

	public String getVerifyString() {
		return verifyString;
	}

	public void setVerifyString(String verifyString) {
		this.verifyString = verifyString;
	}

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

	public Date getOrderSubTime() {
		return orderSubTime;
	}

	public void setOrderSubTime(Date orderSubTime) {
		this.orderSubTime = orderSubTime;
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

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getMpOrderId() {
		return mpOrderId;
	}

	public void setMpOrderId(String mpOrderId) {
		this.mpOrderId = mpOrderId;
	}

	public String getTradeType() {
		return tradeType;
	}

	public void setTradeType(String tradeType) {
		this.tradeType = tradeType;
	}

	public String getOrderPName() {
		return orderPName;
	}

	public void setOrderPName(String orderPName) {
		this.orderPName = orderPName;
	}

	public String getOrderRemarkDesc() {
		return orderRemarkDesc;
	}

	public void setOrderRemarkDesc(String orderRemarkDesc) {
		this.orderRemarkDesc = orderRemarkDesc;
	}

	public String getCommonRetrieveParam() {
		return commonRetrieveParam;
	}

	public void setCommonRetrieveParam(String commonRetrieveParam) {
		this.commonRetrieveParam = commonRetrieveParam;
	}

}
