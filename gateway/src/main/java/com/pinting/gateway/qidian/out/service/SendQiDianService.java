package com.pinting.gateway.qidian.out.service;

import com.pinting.gateway.qidian.out.model.CustomerInfoSyncReqModel;
import com.pinting.gateway.qidian.out.model.CustomerInfoSyncResModel;
import com.pinting.gateway.qidian.out.model.OrderInfoSyncReqModel;
import com.pinting.gateway.qidian.out.model.OrderInfoSyncResModel;
/**
 * 七店数据同步
 * @project gateway
 * @title SendQiDian7Service.java
 * @author Dragon & cat
 * @date 2018-3-21
 * @Copyright 2015 BiGangWan.com Inc. All rights reserved.
 * @Description
 */
public interface SendQiDianService {
	/**
	 * 客户信息同步
	 * @param req 请求数据 CustomerInfoSyncReqModel
	 * @return CustomerInfoSyncResModel
	 */
	public CustomerInfoSyncResModel customerInfoSync(CustomerInfoSyncReqModel req);
	
	/**
	 * 订单信息同步
	 * @param req 请求数据 OrderInfoSyncReqModel
	 * @return OrderInfoSyncResModel
	 */
	public OrderInfoSyncResModel orderInfoSync(OrderInfoSyncReqModel req);
	
}
