package com.pinting.business.hessian.manage.message;

import java.util.ArrayList;
import java.util.HashMap;

import com.pinting.core.hessian.msg.ResMsg;

public class ResMsg_MRole_RoleMenuQuery extends ResMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6202410963143732615L;
	private Integer userId;
	private Integer roleId;
	//用户角色菜单列表
	private ArrayList<HashMap<String, Object>> menus;
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public Integer getRoleId() {
		return roleId;
	}
	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}
	public ArrayList<HashMap<String, Object>> getMenus() {
		return menus;
	}
	public void setMenus(ArrayList<HashMap<String, Object>> menus) {
		this.menus = menus;
	}
}
