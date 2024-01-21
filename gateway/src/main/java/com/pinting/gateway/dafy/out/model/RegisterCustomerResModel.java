package com.pinting.gateway.dafy.out.model;

/**
 * @Project: gateway
 * @Title: RegisterResModel.java
 * @author Zhou Changzai
 * @date 2015-2-11 下午2:59:22
 * @Copyright: 2015 BiGangWan.com Inc. All rights reserved.
 */
public class RegisterCustomerResModel extends BaseResModel{
	private String customerId;

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
}
