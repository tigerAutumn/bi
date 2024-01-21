package com.pinting.gateway.dafy.out.model;

/**
 * @Project: gateway
 * @Title: RegisterReqModel.java
 * @author Zhou Changzai
 * @date 2015-2-11 下午2:58:15
 * @Copyright: 2015 BiGangWan.com Inc. All rights reserved.
 */
public class RegisterCustomerReqModel extends BaseReqModel{
	private String name;
	private String cardNo;
	private String mobile;
	public String getCardNo() {
		return cardNo;
	}
	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
}
