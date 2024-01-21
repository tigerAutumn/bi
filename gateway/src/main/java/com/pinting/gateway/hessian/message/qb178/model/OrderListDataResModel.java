package com.pinting.gateway.hessian.message.qb178.model;

import java.io.Serializable;

/**
 * 查询订单列表
 * @project gateway
 * @title OrderListDataResModel.java
 * @author Dragon & cat
 * @date 2017-7-28
 * @Copyright 2015 BiGangWan.com Inc. All rights reserved.
 * @Description
 */
public class OrderListDataResModel implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5157237847339424825L;
	/**合作商订单编号*/
	private 	String 		delegation_code;
	/**产品编码*/
	private 	String 		product_code;
	/**产品名称*/
	private 	String 		product_name;
	/**会员账号*/
	private 	String 		user_account;
	/**订单金额（金额单位：分）*/
	private 	Long 		order_balance;
	/**订单类型 认购（applyTrade）*/
	private 	String 		delegate_type;
	/**订单状态：委托中(delegating)，成功(success)，撤单(closed)*/
	private 	String 		delegate_status;
	/**下单时间 yyyyMMddHHmmss*/
	private 	String 		order_time;
	/**子渠道标识*/
	private 	String 		channel;
	public String getDelegation_code() {
		return delegation_code;
	}
	public void setDelegation_code(String delegation_code) {
		this.delegation_code = delegation_code;
	}
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
	public String getUser_account() {
		return user_account;
	}
	public void setUser_account(String user_account) {
		this.user_account = user_account;
	}
	public Long getOrder_balance() {
		return order_balance;
	}
	public void setOrder_balance(Long order_balance) {
		this.order_balance = order_balance;
	}
	public String getDelegate_type() {
		return delegate_type;
	}
	public void setDelegate_type(String delegate_type) {
		this.delegate_type = delegate_type;
	}
	public String getDelegate_status() {
		return delegate_status;
	}
	public void setDelegate_status(String delegate_status) {
		this.delegate_status = delegate_status;
	}
	public String getOrder_time() {
		return order_time;
	}
	public void setOrder_time(String order_time) {
		this.order_time = order_time;
	}
	public String getChannel() {
		return channel;
	}
	public void setChannel(String channel) {
		this.channel = channel;
	}
	
	
}
