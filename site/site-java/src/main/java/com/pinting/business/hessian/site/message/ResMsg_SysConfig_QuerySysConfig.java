package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ResMsg;

public class ResMsg_SysConfig_QuerySysConfig extends ResMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3395850536789674194L;

	private String confKey;

	private String confValue;

	private String name;

	private String note;

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

}
