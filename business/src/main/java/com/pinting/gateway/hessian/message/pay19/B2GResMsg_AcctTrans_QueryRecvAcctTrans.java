package com.pinting.gateway.hessian.message.pay19;

import com.pinting.core.hessian.msg.ResMsg;

public class B2GResMsg_AcctTrans_QueryRecvAcctTrans extends ResMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4126996678419147352L;
	private String            merchantId;
    private String            oriOutMxId;
    private String            oriOutOrderId;
    private String            mpOrderId;
    private String            orderAmount;
    private String            tradeResult;
    private String            fee;
    private String            finTime;
	public String getMerchantId() {
		return merchantId;
	}
	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}
	public String getOriOutMxId() {
		return oriOutMxId;
	}
	public void setOriOutMxId(String oriOutMxId) {
		this.oriOutMxId = oriOutMxId;
	}
	public String getOriOutOrderId() {
		return oriOutOrderId;
	}
	public void setOriOutOrderId(String oriOutOrderId) {
		this.oriOutOrderId = oriOutOrderId;
	}
	public String getMpOrderId() {
		return mpOrderId;
	}
	public void setMpOrderId(String mpOrderId) {
		this.mpOrderId = mpOrderId;
	}
	public String getOrderAmount() {
		return orderAmount;
	}
	public void setOrderAmount(String orderAmount) {
		this.orderAmount = orderAmount;
	}
	public String getTradeResult() {
		return tradeResult;
	}
	public void setTradeResult(String tradeResult) {
		this.tradeResult = tradeResult;
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
