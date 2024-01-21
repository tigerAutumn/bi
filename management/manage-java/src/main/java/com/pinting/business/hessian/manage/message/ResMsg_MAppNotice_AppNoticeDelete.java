package com.pinting.business.hessian.manage.message;

import com.pinting.core.hessian.msg.ResMsg;


public class ResMsg_MAppNotice_AppNoticeDelete extends ResMsg {
	/**
	 * 序列化编号
	 */
	private static final long serialVersionUID = -3944473771131305243L;
	
	private String json;

	public String getJson() {
		return json;
	}

	public void setJson(String json) {
		this.json = json;
	}
	
	
}
