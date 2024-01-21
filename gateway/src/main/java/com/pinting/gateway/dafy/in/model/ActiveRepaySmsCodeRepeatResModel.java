package com.pinting.gateway.dafy.in.model;
/**
 * 还款预下单重发验证码短信
 * @project gateway
 * @title ActiveRepaySmsCodeRepeatResModel.java
 * @author Dragon & cat
 * @date 2017-6-9
 * @Copyright 2015 BiGangWan.com Inc. All rights reserved.
 * @Description
 */
public class ActiveRepaySmsCodeRepeatResModel extends BaseResModel {
	/**
	 * 支付单号
	 */
	private		String		bgwOrderNo;

	public String getBgwOrderNo() {
		return bgwOrderNo;
	}

	public void setBgwOrderNo(String bgwOrderNo) {
		this.bgwOrderNo = bgwOrderNo;
	}
	
	
	
}
