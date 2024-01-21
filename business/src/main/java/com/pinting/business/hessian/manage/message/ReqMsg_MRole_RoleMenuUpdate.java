package com.pinting.business.hessian.manage.message;

import javax.validation.constraints.NotNull;

import com.pinting.core.hessian.msg.ReqMsg;

public class ReqMsg_MRole_RoleMenuUpdate extends ReqMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1319446521128231057L;
	
	
	private String menuIds;//多个，以逗号隔开的 字符串
	@NotNull(message="角色编号不能空")
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
