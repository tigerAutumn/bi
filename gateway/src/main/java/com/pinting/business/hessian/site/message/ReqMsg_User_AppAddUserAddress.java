package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ReqMsg;

public class ReqMsg_User_AppAddUserAddress extends ReqMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7231643363281427369L;

	private String address;
	
	private Integer userId;

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}
}
