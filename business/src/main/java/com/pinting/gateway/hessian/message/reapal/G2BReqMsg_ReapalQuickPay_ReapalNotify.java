package com.pinting.gateway.hessian.message.reapal;

import com.pinting.core.hessian.msg.ReqMsg;

public class G2BReqMsg_ReapalQuickPay_ReapalNotify extends ReqMsg{

	private static final long serialVersionUID = 2314623122854608767L;

	private String merchantId;
	
	private String tradeNo; //融宝交易流水号
	
	private String orderNo; //币港湾订单号
	
	private Double amount; //金额
	
	private String status;

	private String resultCode;
	
	private String resultMsg;
	
	private String notifyId; //异步通知ID

	public String getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}

	public String getTradeNo() {
		return tradeNo;
	}

	public void setTradeNo(String tradeNo) {
		this.tradeNo = tradeNo;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getResultCode() {
		return resultCode;
	}

	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}

	public String getResultMsg() {
		return resultMsg;
	}

	public void setResultMsg(String resultMsg) {
		this.resultMsg = resultMsg;
	}

	public String getNotifyId() {
		return notifyId;
	}

	public void setNotifyId(String notifyId) {
		this.notifyId = notifyId;
	}

}
