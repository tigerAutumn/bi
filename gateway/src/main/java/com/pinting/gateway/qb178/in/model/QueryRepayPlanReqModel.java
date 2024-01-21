package com.pinting.gateway.qb178.in.model;
/**
 * 查询还款计划req 
 * @author bianyatian
 * @2017-7-28 下午5:02:25
 */
public class QueryRepayPlanReqModel extends BaseReqModel {

	/* 用户账号 */
	private String user_account;
	/* 产品编号 */
	private String product_code;
	/* 创建时间开始 */
	private String create_time_begin;
	/* 创建时间结束 */
	private String create_time_end;
	
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
	public String getCreate_time_begin() {
		return create_time_begin;
	}
	public void setCreate_time_begin(String create_time_begin) {
		this.create_time_begin = create_time_begin;
	}
	public String getCreate_time_end() {
		return create_time_end;
	}
	public void setCreate_time_end(String create_time_end) {
		this.create_time_end = create_time_end;
	}
	
	
}
