package com.pinting.business.hessian.manage.message;

import com.pinting.core.hessian.msg.ReqMsg;

public class ReqMsg_BsUserChannel_SelectByPrimaryKey extends ReqMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7166125265406591454L;
	
	private Integer id;
	
	private Integer userId;
	
	private String userName;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	

}
