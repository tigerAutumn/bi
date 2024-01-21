package com.pinting.gateway.hfbank.in.model;
/**
 * 
 * @project gateway
 * @title LiquidationNoticeResModel.java
 * @author Dragon & cat
 * @date 2017-5-15
 * @Copyright 2015 BiGangWan.com Inc. All rights reserved.
 * @Description
 */
public class LiquidationNoticeResModel extends BaseResModel {
	/* 订单号 */
	private String order_no;

	public String getOrder_no() {
		return order_no;
	}

	public void setOrder_no(String order_no) {
		this.order_no = order_no;
	}
}
