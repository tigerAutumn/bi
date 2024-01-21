package com.pinting.business.hessian.manage.message;

import com.pinting.core.hessian.msg.ReqMsg;

public class ReqMsg_MSystem_SysConfigUpdate extends ReqMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6898508108276313352L;
	
	private String userId;
	private String roleId;
	private String confKey;
	private String confValue;
	private String name;
	private String note;
	private String updateFlag;
	
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
	public String getConfKey() {
		return confKey;
	}
	public void setConfKey(String confKey) {
		this.confKey = confKey;
	}
	public String getConfValue() {
		return confValue;
	}
	public void setConfValue(String confValue) {
		this.confValue = confValue;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public String getUpdateFlag() {
		return updateFlag;
	}
	public void setUpdateFlag(String updateFlag) {
		this.updateFlag = updateFlag;
	}

}
