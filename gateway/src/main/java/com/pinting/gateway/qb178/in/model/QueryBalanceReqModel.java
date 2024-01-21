package com.pinting.gateway.qb178.in.model;

/**
 * 查询会员资金余额req
 * @author bianyatian
 * @2017-7-28 下午4:34:46
 */
public class QueryBalanceReqModel extends BaseReqModel {
	/* 会员账号，逗号分割（20 个会员账号一次查询），查询会员资金余额 */
	private String user_account_ary;
	/* 创建时间开始 */
	private String create_time_begin;
	/* 创建时间结束 */
	private String create_time_end;
	
	public String getUser_account_ary() {
		return user_account_ary;
	}
	public void setUser_account_ary(String user_account_ary) {
		this.user_account_ary = user_account_ary;
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
