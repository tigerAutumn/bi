package com.pinting.gateway.pay19.in.model.req;


public class AcctTransNoticeReq extends BaseReq {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4034904806804293792L;
	private String versionId;
	private String merchantId;
	private String orderId;
	private String mpOrderId;
	private String tradeResult;
	private String orderAmount;
	private String fee;
	private String finTime;
	private String retCode;
	private String verifyString;
	public String getVersionId() {
		return versionId;
	}
	public void setVersionId(String versionId) {
		this.versionId = versionId;
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
	public String getRetCode() {
		return retCode;
	}
	public void setRetCode(String retCode) {
		this.retCode = retCode;
	}
	public String getVerifyString() {
		return verifyString;
	}
	public void setVerifyString(String verifyString) {
		this.verifyString = verifyString;
	}
	@Override
	public String toString() {
		return "19付转账通知请求数据：  【versionId=" + versionId + ", merchantId="
				+ merchantId + ", orderId=" + orderId + ", mpOrderId="
				+ mpOrderId + ", tradeResult=" + tradeResult + ", orderAmount="
				+ orderAmount + ", fee=" + fee + ", finTime=" + finTime
				+ ", retCode=" + retCode + ", verifyString=" + verifyString
				+ "】";
	}

}
