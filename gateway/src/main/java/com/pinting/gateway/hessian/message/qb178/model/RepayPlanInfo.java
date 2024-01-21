package com.pinting.gateway.hessian.message.qb178.model;

import java.io.Serializable;

/**
 * 还款计划列表
 * @author bianyatian
 * @2017-7-28 下午5:03:59
 */
public class RepayPlanInfo implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -1518044988042520998L;
	/* 用户账号 */
	private String user_account;
	/* 产品编码  */
	private String product_code;
	/* 产品名称*/
	private String product_name;
	/* 总期数 */
	private Integer total_period;
	/* 还款开始日期 */
	private String profit_begin_time;
	/* 当前期数 */
	private Integer period;
	/* 计划还款日期 */
	private String plan_date;
	/* 实际还款日期 */
	private String real_date;
	/* 当期本金（金额单位：分） */
	private Long principal_amount;
	/* 当期利息（金额单位：分） */
	private Long profit_amount;
	/* 计划状态未到期（WAITING）;已完成（FINISH） */
	private String jet_plan_status;
	
	public String getProduct_code() {
		return product_code;
	}
	public void setProduct_code(String product_code) {
		this.product_code = product_code;
	}
	public String getProduct_name() {
		return product_name;
	}
	public void setProduct_name(String product_name) {
		this.product_name = product_name;
	}
	public Integer getTotal_period() {
		return total_period;
	}
	public void setTotal_period(Integer total_period) {
		this.total_period = total_period;
	}
	public String getProfit_begin_time() {
		return profit_begin_time;
	}
	public void setProfit_begin_time(String profit_begin_time) {
		this.profit_begin_time = profit_begin_time;
	}
	public String getReal_date() {
		return real_date;
	}
	public void setReal_date(String real_date) {
		this.real_date = real_date;
	}
	public Long getPrincipal_amount() {
		return principal_amount;
	}
	public void setPrincipal_amount(Long principal_amount) {
		this.principal_amount = principal_amount;
	}
	public Long getProfit_amount() {
		return profit_amount;
	}
	public void setProfit_amount(Long profit_amount) {
		this.profit_amount = profit_amount;
	}
	public String getJet_plan_status() {
		return jet_plan_status;
	}
	public void setJet_plan_status(String jet_plan_status) {
		this.jet_plan_status = jet_plan_status;
	}
	public String getUser_account() {
		return user_account;
	}
	public void setUser_account(String user_account) {
		this.user_account = user_account;
	}
	public Integer getPeriod() {
		return period;
	}
	public void setPeriod(Integer period) {
		this.period = period;
	}
	public String getPlan_date() {
		return plan_date;
	}
	public void setPlan_date(String plan_date) {
		this.plan_date = plan_date;
	}
	
	
}
