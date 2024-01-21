package com.pinting.business.hessian.manage.message;

import com.pinting.core.hessian.msg.ReqMsg;

public class ReqMsg_MRole_RoleMenuQuery extends ReqMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5125073059048641500L;
	private String userId;
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
