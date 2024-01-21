package com.pinting.gateway.hessian.message.reapal;

import java.util.Date;

import com.pinting.core.hessian.msg.ResMsg;

public class B2GResMsg_ReapalQuickPay_QueryOrderResult extends ResMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6699035184625824791L;

	private String orderNo;
	private String tradeNo;
	private String status;
	private Double amount;
	private Date tradeDate;
	private String resultCode;
	private String resultMsg;
	
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
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	public String getTradeNo() {
		return tradeNo;
	}
	public void setTradeNo(String tradeNo) {
		this.tradeNo = tradeNo;
	}
	public Date getTradeDate() {
		return tradeDate;
	}
	public void setTradeDate(Date tradeDate) {
		this.tradeDate = tradeDate;
	}

}
