package com.pinting.open.pojo.response.asset;

import com.pinting.open.base.response.Response;
/**
 * 
 * @project open-base
 * @title MyBonusWithdrawResponse.java
 * @author Dragon & cat
 * @date 2017-4-14
 * @Copyright 2015 BiGangWan.com Inc. All rights reserved.
 * @Description 我的奖励金收益_奖励金转出
 */
public class MyBonusWithdrawResponse extends Response {
    
	private String  withdrawTime; //提现申请时间
	
	private String orderNo; //订单号

	public String getWithdrawTime() {
		return withdrawTime;
	}

	public void setWithdrawTime(String withdrawTime) {
		this.withdrawTime = withdrawTime;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	
	
}
