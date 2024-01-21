package com.pinting.gateway.qidian.out.model;

import java.util.List;

/**
 * 客户信息同步
 * @project gateway
 * @title CustomerInfoSyncReqModel.java
 * @author Dragon & cat
 * @date 2018-3-21
 * @Copyright 2015 BiGangWan.com Inc. All rights reserved.
 * @Description 
 */
public class CustomerInfoSyncReqModel extends BaseReqModel {
	/*1.客户注册；2.用户绑卡；3.用户登录*/
	private		Integer		type;
	/*客户信息列表*/
	private 	List<Customers>  customers;

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public List<Customers> getCustomers() {
		return customers;
	}

	public void setCustomers(List<Customers> customers) {
		this.customers = customers;
	}
	
}
