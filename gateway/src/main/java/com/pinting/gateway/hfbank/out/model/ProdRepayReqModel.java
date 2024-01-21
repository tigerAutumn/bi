package com.pinting.gateway.hfbank.out.model;

import java.util.List;

import net.sf.json.JSONObject;

/**
 * 
 * @project gateway
 * @title ProdRepayReqModel.java
 * @author Dragon & cat
 * @date 2017-4-1
 * @Copyright 2015 BiGangWan.com Inc. All rights reserved.
 * @Description 恒丰银行存管，标的还款请求
 */
public class ProdRepayReqModel extends BaseReqModel {
	/**标的编号*/
	private 	String 		prod_id;
	/**还款期数，如果一次性还款，repay_num为1*/
	private 	Integer 		repay_num;
	/**是否整个标的还清：0-是，1-否； */
	private 	String 		is_payoff;
	/**交易金额（所有实际还款金额之和）*/
	private 	String 		trans_amt;
	/**资金数据，json格式记录还款金额*/
	private 	JSONObject	funddata;
//	private 	ProdRepayReqFundData 		funddata;
	/**本期已还清 0-是，1-否*/
	private		String 		repay_flag;
	/**备注 */
	private 	String 		remark;
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
	public String getIs_payoff() {
		return is_payoff;
	}
	public void setIs_payoff(String is_payoff) {
		this.is_payoff = is_payoff;
	}
	public String getRepay_flag() {
		return repay_flag;
	}
	public void setRepay_flag(String repay_flag) {
		this.repay_flag = repay_flag;
	}
	public String getTrans_amt() {
		return trans_amt;
	}
	public void setTrans_amt(String trans_amt) {
		this.trans_amt = trans_amt;
	}
	public JSONObject getFunddata() {
		return funddata;
	}
	public void setFunddata(JSONObject funddata) {
		this.funddata = funddata;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	
}
