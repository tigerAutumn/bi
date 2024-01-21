package com.pinting.gateway.hessian.message.pay19;

import com.pinting.core.hessian.msg.ResMsg;

public class B2GResMsg_AcctTrans_AcctTrans extends ResMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3258884043359859639L;
	private String merchantId;
	private String orderId;
	private String mpOrderId;
	private String tradeResult;
	private String orderAmount;
	private String fee;
	private String finTime;
	private String retCode;

	public String getRetCode() {
		return retCode;
	}

	public void setRetCode(String retCode) {
		this.retCode = retCode;
	}

	public String getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
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

	public String getTradeResult() {
		return tradeResult;
	}

	public void setTradeResult(String tradeResult) {
		this.tradeResult = tradeResult;
	}

	public String getOrderAmount() {
		return orderAmount;
	}

	public void setOrderAmount(String orderAmount) {
		this.orderAmount = orderAmount;
	}

	public String getFee() {
		return fee;
	}

	public void setFee(String fee) {
		this.fee = fee;
	}

	public String getFinTime() {
		return finTime;
	}

	public void setFinTime(String finTime) {
		this.finTime = finTime;
	}

}
