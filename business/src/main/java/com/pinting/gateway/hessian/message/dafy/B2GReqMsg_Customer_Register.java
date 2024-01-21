package com.pinting.gateway.hessian.message.dafy;

import com.pinting.core.hessian.msg.ReqMsg;

/**
 * @Project: business
 * @Title: B2GReqMsg_RegisterCustomer_.java
 * @author Zhou Changzai
 * @date 2015-2-11 下午7:14:35
 * @Copyright: 2015 BiGangWan.com Inc. All rights reserved.
 */
public class B2GReqMsg_Customer_Register extends ReqMsg{
	private static final long serialVersionUID = 1785816042464209494L;
	
	private String name;//用户姓名
	private String idCard;//用户身份证号码
	private String mobile;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getIdCard() {
		return idCard;
	}
	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
}
