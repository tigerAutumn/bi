package com.pinting.gateway.hessian.message.pay19;

import java.util.Date;

import com.pinting.core.hessian.msg.ReqMsg;

public class B2GReqMsg_AcctTrans_AcctTrans extends ReqMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3583915807326776008L;
	private String accountFrom;
	private String accountTo;
	private String notifyUrl;
	private Double orderAmount;
	private Date orderDate;
	private String orderId;
	private String remarks;
	private String toAcctName;
	private String toAcctType;
	private String tradeDesc;
	private String tradeType;
	public String getAccountFrom() {
		return accountFrom;
	}
	public void setAccountFrom(String accountFrom) {
		this.accountFrom = accountFrom;
	}
	public String getAccountTo() {
		return accountTo;
	}
	public void setAccountTo(String accountTo) {
		this.accountTo = accountTo;
	}
	public String getNotifyUrl() {
		return notifyUrl;
	}
	public void setNotifyUrl(String notifyUrl) {
		this.notifyUrl = notifyUrl;
	}
	public Double getOrderAmount() {
		return orderAmount;
	}
	public void setOrderAmount(Double orderAmount) {
		this.orderAmount = orderAmount;
	}
	public Date getOrderDate() {
		return orderDate;
	}
	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getToAcctName() {
		return toAcctName;
	}
	public void setToAcctName(String toAcctName) {
		this.toAcctName = toAcctName;
	}
	public String getToAcctType() {
		return toAcctType;
	}
	public void setToAcctType(String toAcctType) {
		this.toAcctType = toAcctType;
	}
	public String getTradeDesc() {
		return tradeDesc;
	}
	public void setTradeDesc(String tradeDesc) {
		this.tradeDesc = tradeDesc;
	}
	public String getTradeType() {
		return tradeType;
	}
	public void setTradeType(String tradeType) {
		this.tradeType = tradeType;
	}

}
