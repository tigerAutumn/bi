package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ResMsg;

public class ResMsg_Home_InfoQuery extends ResMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5239309042692792540L;
	
	//用户总收入（收益）
	private double totalIncome;

	/*目前未赋值 */
	private double limit;
	/*目前未赋值 */
	private double ceiling;

	/* 总投资数量 */
	private Integer investNum;

	/* 当前投资总金额 */
	private Double currTotalAmount;

	public Double getCurrTotalAmount() {
		return currTotalAmount;
	}

	public void setCurrTotalAmount(Double currTotalAmount) {
		this.currTotalAmount = currTotalAmount;
	}

	public Integer getInvestNum() {
		return investNum;
	}

	public void setInvestNum(Integer investNum) {
		this.investNum = investNum;
	}

	public double getLimit() {
		return limit;
	}

	public void setLimit(double limit) {
		this.limit = limit;
	}

	public double getCeiling() {
		return ceiling;
	}

	public void setCeiling(double ceiling) {
		this.ceiling = ceiling;
	}

	public double getTotalIncome() {
		return totalIncome;
	}

	public void setTotalIncome(double totalIncome) {
		this.totalIncome = totalIncome;
	}
}
