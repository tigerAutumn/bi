package com.pinting.business.hessian.manage.message;

import com.pinting.core.hessian.msg.ReqMsg;

public class ReqMsg_MSystem_SysConfigQuery extends ReqMsg {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8573295746116787001L;
	
	private String userId;
	private String roleId;
	
	private String confKey;
	
	public String getConfKey() {
		return confKey;
	}
	public void setConfKey(String confKey) {
		this.confKey = confKey;
	}
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
