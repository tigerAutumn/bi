/**
 * 
 */
package com.pinting.business.model;

/**
 * 自动红包参数类
 * @Project: business
 * @author yanwl
 * @Title: AutoRedPacketParams.java
 * @date 2016-3-11 上午10:51:34
 * @Copyright: 2016 bigangwan.com Inc. All rights reserved.
 */
public class AutoRedPacketParams {

	/**
	 * 用户Id
	 */
	private Integer userId;
	
	/**
	 * 触发条件类型
	 */
	private String triggerType;
	
	/**
	 * 购买金额
	 */
	private double amount;

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getTriggerType() {
		return triggerType;
	}

	public void setTriggerType(String triggerType) {
		this.triggerType = triggerType;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}
}
