package com.pinting.business.hessian.manage.message;

import javax.validation.constraints.NotNull;

import com.pinting.core.hessian.msg.ReqMsg;

public class ReqMsg_MHome_MenuQuery extends ReqMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = -11245862719830779L;
	@NotNull
	private String userId;
	@NotNull
	private String roleId;
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getRoleId() {
		return roleId;
	}
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

}
