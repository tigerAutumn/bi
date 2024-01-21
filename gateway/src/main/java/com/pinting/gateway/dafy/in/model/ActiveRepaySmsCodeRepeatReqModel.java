package com.pinting.gateway.dafy.in.model;
/**
 * 还款预下单重发验证码短信
 * @project gateway
 * @title ActiveRepayPreRepeatReqModel.java
 * @author Dragon & cat
 * @date 2017-6-9
 * @Copyright 2015 BiGangWan.com Inc. All rights reserved.
 * @Description
 */
public class ActiveRepaySmsCodeRepeatReqModel extends BaseReqModel {
	/*客户端标识*/
	private			String 		clientKey;
	/*还款单号	即原预下单还款单号*/
	private			String		orderNo;
	public String getClientKey() {
		return clientKey;
	}
	public void setClientKey(String clientKey) {
		this.clientKey = clientKey;
	}
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	
	
}
