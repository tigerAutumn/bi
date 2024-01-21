package com.pinting.gateway.dafy.in.model;


/**
 * @Project: gateway
 * @Title: BuyProductReqModel.java
 * @author Zhou Changzai
 * @date 2015-2-11 下午3:41:50
 * @Copyright: 2015 BiGangWan.com Inc. All rights reserved.
 */
public class BuyProductResultReqModel extends BaseReqModel {
	private int result;
	private String applyNo;
	private String payChannel;
	private double actAmount;
	private int moneyType;
	
	public int getResult() {
		return result;
	}
	public void setResult(int result) {
		this.result = result;
	}
	public String getApplyNo() {
		return applyNo;
	}
	public void setApplyNo(String applyNo) {
		this.applyNo = applyNo;
	}
	public String getPayChannel() {
		return payChannel;
	}
	public void setPayChannel(String payChannel) {
		this.payChannel = payChannel;
	}
	public double getActAmount() {
		return actAmount;
	}
	public void setActAmount(double actAmount) {
		this.actAmount = actAmount;
	}
	public int getMoneyType() {
		return moneyType;
	}
	public void setMoneyType(int moneyType) {
		this.moneyType = moneyType;
	}
	@Override
	public String toString() {
		return "BuyProductResultReqModel [result=" + result
				+ ", applyNo=" + applyNo + ", payChannel="
				+ payChannel + ", actAmount=" + actAmount
				+ ", moneyType=" + moneyType + "]";
	}

}
