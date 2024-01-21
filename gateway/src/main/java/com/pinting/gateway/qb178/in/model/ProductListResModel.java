package com.pinting.gateway.qb178.in.model;

import java.util.List;

/**
 * 查询产品列表
 * @project gateway
 * @title ProductListResModel.java
 * @author Dragon & cat
 * @date 2017-7-28
 * @Copyright 2015 BiGangWan.com Inc. All rights reserved.
 * @Description
 */
public class ProductListResModel extends BaseResModel {
	/*产品列表集合对象*/
	private		List<ProductListDataResModel> 	data;

	public List<ProductListDataResModel> getData() {
		return data;
	}

	public void setData(List<ProductListDataResModel> data) {
		this.data = data;
	}
	
}
