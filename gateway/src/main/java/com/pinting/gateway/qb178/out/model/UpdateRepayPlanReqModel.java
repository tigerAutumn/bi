package com.pinting.gateway.qb178.out.model;

import com.pinting.gateway.qb178.out.model.BaseReqModel;

/**
 * 更新还款计划状态req
 * @author bianyatian
 * @2017-7-28 下午5:28:30
 */
public class UpdateRepayPlanReqModel extends BaseReqModel {
	/* 用户账号 */
	private String user_account;
	/* 产品编号 */
	private String product_code;
	/* 期数 */
	private Integer period;
	/* 计划状态未到期（WAITING）;已完成（FINISH） */
	private String jet_plan_status;
	/* 实际还款日期，计划状态更新为已完成时必填 */
	private String real_date;
	/* 计划还款日期  */
	private String plan_date;
	
	public String getUser_account() {
		return user_account;
	}
	public void setUser_account(String user_account) {
		this.user_account = user_account;
	}
	public String getProduct_code() {
		return product_code;
	}
	public void setProduct_code(String product_code) {
		this.product_code = product_code;
	}
	public Integer getPeriod() {
		return period;
	}
	public void setPeriod(Integer period) {
		this.period = period;
	}
	public String getJet_plan_status() {
		return jet_plan_status;
	}
	public void setJet_plan_status(String jet_plan_status) {
		this.jet_plan_status = jet_plan_status;
	}
	public String getReal_date() {
		return real_date;
	}
	public void setReal_date(String real_date) {
		this.real_date = real_date;
	}
	public String getPlan_date() {
		return plan_date;
	}
	public void setPlan_date(String plan_date) {
		this.plan_date = plan_date;
	}  

	
}
