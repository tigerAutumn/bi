package com.pinting.business.hessian.manage.message;

import com.pinting.core.hessian.msg.ReqMsg;

public class ReqMsg_MUser_changePwd extends ReqMsg {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 9187017215205801399L;

	private Integer id;//	用户编号	必填		
	
	private String password;
	
	private String oldPassword;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getOldPassword() {
		return oldPassword;
	}

	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}
	
}
