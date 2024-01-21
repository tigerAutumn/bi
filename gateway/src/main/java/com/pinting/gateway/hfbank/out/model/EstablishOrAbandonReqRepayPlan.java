package com.pinting.gateway.hfbank.out.model;

import java.io.Serializable;

/**
 * 
 * @project gateway
 * @title EstablishOrAbandonReqRepayPlan.java
 * @author Dragon & cat
 * @date 2017-4-1
 * @Copyright 2015 BiGangWan.com Inc. All rights reserved.
 * @Description 恒丰银行存管，标的成废请求-还款计划表，标的成立时必填，
 * 该计划为借款人还款计划
 */
public class EstablishOrAbandonReqRepayPlan implements  Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2942328575932436585L;
	/*还款金额*/
	private		String		repay_amt;
	/*手续费*/
	private		String		repay_fee;
	/*还款期数*/
	private		String		repay_num;
	/*还款日期*/
	private		String		repay_date;
	public String getRepay_amt() {
		return repay_amt;
	}
	public void setRepay_amt(String repay_amt) {
		this.repay_amt = repay_amt;
	}
	public String getRepay_fee() {
		return repay_fee;
	}
	public void setRepay_fee(String repay_fee) {
		this.repay_fee = repay_fee;
	}
	public String getRepay_num() {
		return repay_num;
	}
	public void setRepay_num(String repay_num) {
		this.repay_num = repay_num;
	}
	public String getRepay_date() {
		return repay_date;
	}
	public void setRepay_date(String repay_date) {
		this.repay_date = repay_date;
	}
	
}
