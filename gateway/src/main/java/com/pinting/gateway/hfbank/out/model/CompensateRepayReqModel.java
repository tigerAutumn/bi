package com.pinting.gateway.hfbank.out.model;

import com.pinting.gateway.hfbank.out.model.BaseReqModel;
/**
 * 
 * @project gateway
 * @title CompensateRepayReqModel.java
 * @author Dragon & cat
 * @date 2017-4-15
 * @Copyright 2015 BiGangWan.com Inc. All rights reserved.
 * @Description  4.2.2.标的代偿（委托）还款
 */
public class CompensateRepayReqModel extends BaseReqModel {
	/**产品编号*/
	private  	String 		prod_id;
	/**还款期数，如果一次性还款，repay_num为1*/
	private  	Integer 	repay_num;
	/**计划还款日期(yyyyMMdd)*/
	private  	String 		repay_date;
	/**计划还款金额*/
	private  	String 		repay_amt;
	/**实际还款日期(yyyyMMdd)*/
	private  	String 		real_repay_date;
	/**实际还款金额*/
	private  	String 		real_repay_amt;
	/**平台客户编号（借款人）*/
	private  	String 		platcust;
	/**代偿人平台客户编号（或者代偿账户编号）*/
	private  	String 		compensation_platcust;
	/**交易金额（实际还款金额+手续费金额）*/
	private  	String 		trans_amt;
	/**手续费金额*/
	private  	String 		fee_amt;
	/**还款类型 0-代偿还款 1-委托还款*/
	private  	String 		repay_type;
	/**备注*/
	private  	String 		remark;
	
	public String getProd_id() {
		return prod_id;
	}
	public void setProd_id(String prod_id) {
		this.prod_id = prod_id;
	}
	public Integer getRepay_num() {
		return repay_num;
	}
	public void setRepay_num(Integer repay_num) {
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
	
	public String getPlatcust() {
		return platcust;
	}
	public void setPlatcust(String platcust) {
		this.platcust = platcust;
	}
	public String getCompensation_platcust() {
		return compensation_platcust;
	}
	public void setCompensation_platcust(String compensation_platcust) {
		this.compensation_platcust = compensation_platcust;
	}
	public String getRepay_type() {
		return repay_type;
	}
	public void setRepay_type(String repay_type) {
		this.repay_type = repay_type;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getRepay_amt() {
		return repay_amt;
	}
	public void setRepay_amt(String repay_amt) {
		this.repay_amt = repay_amt;
	}
	public String getReal_repay_amt() {
		return real_repay_amt;
	}
	public void setReal_repay_amt(String real_repay_amt) {
		this.real_repay_amt = real_repay_amt;
	}
	public String getTrans_amt() {
		return trans_amt;
	}
	public void setTrans_amt(String trans_amt) {
		this.trans_amt = trans_amt;
	}
	public String getFee_amt() {
		return fee_amt;
	}
	public void setFee_amt(String fee_amt) {
		this.fee_amt = fee_amt;
	}
}