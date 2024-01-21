package com.pinting.business.accounting.finance.model;

import com.pinting.gateway.hessian.message.pay19.enums.AcctTransTradeResult;

import java.util.Date;

/**
 * Created by babyshark on 2016/9/6.
 */
public class SysActTransResultInfo {

	private String orderId;
	private String mpOrderId;
	private AcctTransTradeResult tradeResult;
	private Double orderAmount;
	private Double fee;
	private Date finTime;
	private String retCode;
	private String errorMsg;

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
	public AcctTransTradeResult getTradeResult() {
		return tradeResult;
	}
	public void setTradeResult(AcctTransTradeResult tradeResult) {
		this.tradeResult = tradeResult;
	}
	public Double getOrderAmount() {
		return orderAmount;
	}
	public void setOrderAmount(Double orderAmount) {
		this.orderAmount = orderAmount;
	}
	public Double getFee() {
		return fee;
	}
	public void setFee(Double fee) {
		this.fee = fee;
	}
	public Date getFinTime() {
		return finTime;
	}
	public void setFinTime(Date finTime) {
		this.finTime = finTime;
	}
	public String getRetCode() {
		return retCode;
	}
	public void setRetCode(String retCode) {
		this.retCode = retCode;
	}

}
