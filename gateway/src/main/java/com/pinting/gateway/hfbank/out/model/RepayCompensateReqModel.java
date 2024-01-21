package com.pinting.gateway.hfbank.out.model;
/**
 * 
 * @project gateway
 * @title RepayCompensate.java
 * @author Dragon & cat
 * @date 2017-4-15
 * @Copyright 2015 BiGangWan.com Inc. All rights reserved.
 * @Description 4.2.3.借款人还款代偿（委托）
 */
public class RepayCompensateReqModel extends BaseReqModel {
	/**产品编号*/
	private 	String 		prod_id;
	/**还款金额*/
	private 	String 		repay_amt;
	/**平台客户编号（借款人）*/
	private 	String 		platcust;
	/**代偿人平台客户编号（或者代偿账户编号）*/
	private 	String 		compensation_platcust;
	/**备注*/
	private 	String 		remark;
	
	public String getProd_id() {
		return prod_id;
	}
	public void setProd_id(String prod_id) {
		this.prod_id = prod_id;
	}
	public String getRepay_amt() {
		return repay_amt;
	}
	public void setRepay_amt(String repay_amt) {
		this.repay_amt = repay_amt;
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
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	
}
