package com.pinting.gateway.dafy.in.model;

import java.util.Date;

/**
 * 
 * @Project: gateway
 * @Title: SysWithdrawReqModel.java
 * @author Huang MengJian
 * @date 2015-4-15 上午11:13:28
 * @Copyright: 2015 BiGangWan.com Inc. All rights reserved.
 */
public class SysWithdrawResultReqModel extends BaseReqModel {
	
	private String applyNo;
	private String result;
	private Date successTime;
	private String moneyType;
	private String transType;
	private String failReason;
	private Double amount;
	
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	public String getFailReason() {
		return failReason;
	}
	public void setFailReason(String failReason) {
		this.failReason = failReason;
	}
	public String getApplyNo() {
		return applyNo;
	}
	public void setApplyNo(String applyNo) {
		this.applyNo = applyNo;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public Date getSuccessTime() {
		return successTime;
	}
	public void setSuccessTime(Date successTime) {
		this.successTime = successTime;
	}
	public String getMoneyType() {
		return moneyType;
	}
	public void setMoneyType(String moneyType) {
		this.moneyType = moneyType;
	}
	public String getTransType() {
		return transType;
	}
	public void setTransType(String transType) {
		this.transType = transType;
	}
	
	
	
	
}
