package com.pinting.gateway.hessian.message.dafy;

import java.util.Date;

import com.pinting.core.hessian.msg.ReqMsg;

public class B2GReqMsg_Withdraw_BonusWithdraw extends ReqMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5796602381599515095L;
	
	private Double amount;
	private Date applyTime;
	private String dafyUserId;
	private String userName;
	private String bankcard;
	private String transType;
	private String applyNo;
	
	public String getApplyNo() {
		return applyNo;
	}
	public void setApplyNo(String applyNo) {
		this.applyNo = applyNo;
	}
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	public String getDafyUserId() {
		return dafyUserId;
	}
	public void setDafyUserId(String dafyUserId) {
		this.dafyUserId = dafyUserId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getBankcard() {
		return bankcard;
	}
	public void setBankcard(String bankcard) {
		this.bankcard = bankcard;
	}
	public String getTransType() {
		return transType;
	}
	public void setTransType(String transType) {
		this.transType = transType;
	}
	public Date getApplyTime() {
		return applyTime;
	}
	public void setApplyTime(Date applyTime) {
		this.applyTime = applyTime;
	}
}
