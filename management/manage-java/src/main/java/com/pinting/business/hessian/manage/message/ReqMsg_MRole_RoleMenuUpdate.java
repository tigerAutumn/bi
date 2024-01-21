package com.pinting.business.hessian.manage.message;

import com.pinting.core.hessian.msg.ReqMsg;

public class ReqMsg_MRole_RoleMenuUpdate extends ReqMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1319446521128231057L;
	
	private String menuIds;//多个，以逗号隔开的 字符串
	private String roleId;
	
	public String getMenuIds() {
		return menuIds;
	}
	public void setMenuIds(String menuIds) {
		this.menuIds = menuIds;
	}
	public String getRoleId() {
		return roleId;
	}
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
}
