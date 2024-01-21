package com.pinting.gateway.qidian.out.model;

import java.util.List;

/**
 * 订单信息同步
 * @project gateway
 * @title OrderInfoSyncReqModel.java
 * @author Dragon & cat
 * @date 2018-3-21
 * @Copyright 2015 BiGangWan.com Inc. All rights reserved.
 * @Description
 */
public class OrderInfoSyncReqModel extends BaseReqModel {
	/*1.理财购买 2.到期回款*/
	private		Integer		type;
	/*订单信息列表*/
	private 	List<OrderInfos>   orderInfos;

	
	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public List<OrderInfos> getOrderInfos() {
		return orderInfos;
	}

	public void setOrderInfos(List<OrderInfos> orderInfos) {
		this.orderInfos = orderInfos;
	}
}
