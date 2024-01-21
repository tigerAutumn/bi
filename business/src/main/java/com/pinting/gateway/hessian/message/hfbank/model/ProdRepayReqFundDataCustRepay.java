package com.pinting.gateway.hessian.message.hfbank.model;

import java.io.Serializable;

/**
 * 
 * @project gateway
 * @title ProdRepayReqFundDataCustRepay.java
 * @author Dragon & cat
 * @date 2017-4-1
 * @Copyright 2015 BiGangWan.com Inc. All rights reserved.
 * @Description 恒丰银行存管，标的还款请求-资金数据，json格式记录还款金额-投资人还款明细
 */
public class ProdRepayReqFundDataCustRepay implements  Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8341808819062431588L;
	/**
	 * 实际还款金额（实际还款本金+体验金+加息金+利息+手续费）
	 */
	private 		String 		real_repay_amt;
	/**实际还款本金*/
	private 		String 		real_repay_amount;
	/**体验金*/
	private 		String 		experience_amt;
	/**加息金*/
	private 		String 		rates_amt;
	/**实际还款利息*/
	private 		String 		real_repay_val;
	/**手续费*/
	private 		String 		repay_fee;
	/**投资人平台客户号*/
	private 		String 		cust_no;
	/**还款期数*/
	private 		String 		repay_num;
	/**还款日期*/
	private 		String 		repay_date;
	/**实际还款日期*/
	private 		String 		real_repay_date;

	public String getReal_repay_amt() {
		return real_repay_amt;
	}

	public void setReal_repay_amt(String real_repay_amt) {
		this.real_repay_amt = real_repay_amt;
	}

	public String getReal_repay_amount() {
		return real_repay_amount;
	}
	public void setReal_repay_amount(String real_repay_amount) {
		this.real_repay_amount = real_repay_amount;
	}
	public String getExperience_amt() {
		return experience_amt;
	}
	public void setExperience_amt(String experience_amt) {
		this.experience_amt = experience_amt;
	}
	public String getRates_amt() {
		return rates_amt;
	}
	public void setRates_amt(String rates_amt) {
		this.rates_amt = rates_amt;
	}
	public String getReal_repay_val() {
		return real_repay_val;
	}
	public void setReal_repay_val(String real_repay_val) {
		this.real_repay_val = real_repay_val;
	}
	public String getRepay_fee() {
		return repay_fee;
	}
	public void setRepay_fee(String repay_fee) {
		this.repay_fee = repay_fee;
	}
	public String getCust_no() {
		return cust_no;
	}
	public void setCust_no(String cust_no) {
		this.cust_no = cust_no;
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
	public String getReal_repay_date() {
		return real_repay_date;
	}
	public void setReal_repay_date(String real_repay_date) {
		this.real_repay_date = real_repay_date;
	}
	
}
