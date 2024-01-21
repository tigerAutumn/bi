package com.pinting.gateway.dafy.out.model;


/**
 * 品听提现model
 * @Project: gateway
 * @Title: WXWithdrawReqModel.java
 * @author Huang MengJian
 * @date 2015-4-14 下午4:48:16
 * @Copyright: 2015 BiGangWan.com Inc. All rights reserved.
 */
public class SysWithdrawReqModel extends BaseReqModel{
	
	private String applyNo;
	
	private Double amount;
	
	private String transType;

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

	public String getTransType() {
		return transType;
	}

	public void setTransType(String transType) {
		this.transType = transType;
	}
	
	
	
}
