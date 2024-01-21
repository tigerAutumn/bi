package com.pinting.business.model.vo;

import com.pinting.business.model.BsUserTransDetail;

public class BsUserTransDetailVO extends BsUserTransDetail{
	
	/**
	 * 序列化编号
	 */
	private static final long serialVersionUID = -1612062483258915056L;

	private String mobile;
	
	private String userName;
	
	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	
}
