package com.pinting.business.hessian.manage.message;

import java.util.ArrayList;
import java.util.HashMap;

import com.pinting.core.hessian.msg.ResMsg;

public class ResMsg_MHome_MenuQuery extends ResMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2075115363549572888L;

	private Integer userId;
	private String userName;
	private Integer roleId;
	private String roleName;
	//用户角色菜单列表
	private ArrayList<HashMap<String, Object>> menus;
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
	public Integer getRoleId() {
		return roleId;
	}
	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public ArrayList<HashMap<String, Object>> getMenus() {
		return menus;
	}
	public void setMenus(ArrayList<HashMap<String, Object>> menus) {
		this.menus = menus;
	}

}
