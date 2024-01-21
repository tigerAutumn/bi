package com.pinting.gateway.hfbank.out.model;

import java.util.List;

import net.sf.json.JSONObject;

/**
 * 
 * @project gateway
 * @title EstablishOrAbandonReqModel.java
 * @author Dragon & cat
 * @date 2017-4-1
 * @Copyright 2015 BiGangWan.com Inc. All rights reserved.
 * @Description 恒丰银行存管 ，标的成废请求
 */
public class EstablishOrAbandonReqModel extends BaseReqModel {
	/*产品编号*/
	private		String 		prod_id;
	/*成标、废标标记    2 成标 3废标*/
	private		String 		flag;
	/*成标时候需要进行的分佣说明*/
	private		JSONObject		funddata;
	
	/*还款计划表，标的成立时必填，该计划为借款人还款计划*/
	private		List<EstablishOrAbandonReqRepayPlan> 		repay_plan_list;
	/*备注*/
	private		String 		remark;
	
	public String getProd_id() {
		return prod_id;
	}
	public void setProd_id(String prod_id) {
		this.prod_id = prod_id;
	}
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	
	public JSONObject getFunddata() {
		return funddata;
	}
	public void setFunddata(JSONObject funddata) {
		this.funddata = funddata;
	}
	public List<EstablishOrAbandonReqRepayPlan> getRepay_plan_list() {
		return repay_plan_list;
	}
	public void setRepay_plan_list(
			List<EstablishOrAbandonReqRepayPlan> repay_plan_list) {
		this.repay_plan_list = repay_plan_list;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
}
