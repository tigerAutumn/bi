package com.pinting.gateway.qb178.in.model;
/**
 * 查询订单列表
 * @project gateway
 * @title OrderListReqModel.java
 * @author Dragon & cat
 * @date 2017-7-28
 * @Copyright 2015 BiGangWan.com Inc. All rights reserved.
 * @Description
 */
public class OrderListReqModel extends BaseReqModel{

	/**产品编码*/
	private 	String 		product_code;
	/**会员账号*/
	private 	String 		user_account;
	/**创建时间开始*/
	private 	String 		create_time_begin;
	/**创建时间结束*/
	private 	String 		create_time_end;
	/**子标识*/
	private 	String 		channel;
	
	public String getProduct_code() {
		return product_code;
	}
	public void setProduct_code(String product_code) {
		this.product_code = product_code;
	}
	public String getUser_account() {
		return user_account;
	}
	public void setUser_account(String user_account) {
		this.user_account = user_account;
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
	public String getChannel() {
		return channel;
	}
	public void setChannel(String channel) {
		this.channel = channel;
	}
	
	
}
