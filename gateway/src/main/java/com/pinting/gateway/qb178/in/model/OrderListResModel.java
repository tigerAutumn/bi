package com.pinting.gateway.qb178.in.model;

import java.util.List;

/**
 * 查询订单列表
 * @project gateway
 * @title OrderListResModel.java
 * @author Dragon & cat
 * @date 2017-7-28
 * @Copyright 2015 BiGangWan.com Inc. All rights reserved.
 * @Description
 */
public class OrderListResModel extends BaseResModel {
	
	private		List<OrderListDataResModel> 	data;

	public List<OrderListDataResModel> getData() {
		return data;
	}

	public void setData(List<OrderListDataResModel> data) {
		this.data = data;
	}
	
	
}
