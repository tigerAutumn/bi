package com.pinting.gateway.hessian.message.dafy;

import com.pinting.core.hessian.msg.ResMsg;

/**
 * @Project: business
 * @Title: B2GResMsg_Customer_Register.java
 * @author Zhou Changzai
 * @date 2015-2-11 下午7:34:25
 * @Copyright: 2015 BiGangWan.com Inc. All rights reserved.
 */
public class B2GResMsg_Customer_Register extends ResMsg{
	private static final long serialVersionUID = 1730011373465747839L;
	private String customerId;//达飞返回的客户编码

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

}
