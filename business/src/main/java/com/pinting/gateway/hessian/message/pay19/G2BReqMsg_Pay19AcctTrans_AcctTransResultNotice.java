package com.pinting.gateway.hessian.message.pay19;

import java.util.Date;

import com.pinting.core.hessian.msg.ReqMsg;
import com.pinting.gateway.hessian.message.pay19.enums.AcctTransTradeResult;
import com.pinting.gateway.hessian.message.pay19.enums.CardType;

public class G2BReqMsg_Pay19AcctTrans_AcctTransResultNotice extends ReqMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = -917336162025026019L;
	private String versionId;
	private String merchantId;
	private String orderId;
	private String mpOrderId;
	private AcctTransTradeResult tradeResult;
	private Double orderAmount;
	private Double fee;
	private Date finTime;
	private String retCode;
	private String errorMsg;
	private String verifyString;
	
	public String getErrorMsg() {
		return errorMsg;
	}
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
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
	public String getVerifyString() {
		return verifyString;
	}
	public void setVerifyString(String verifyString) {
		this.verifyString = verifyString;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	@Override
	public String toString() {
		return "收到19付打款结果通知 [versionId="
				+ versionId + ", merchantId=" + merchantId + ", orderId="
				+ orderId + ", mpOrderId=" + mpOrderId + ", tradeResult="
				+ tradeResult + ", orderAmount=" + orderAmount + ", fee=" + fee
				+ ", finTime=" + finTime + ", retCode=" + retCode
				+ ", verifyString=" + verifyString + "]";
	}

}
