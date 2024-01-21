package com.pinting.business.hessian.manage.message;

import com.pinting.core.hessian.msg.ReqMsg;

public class ReqMsg_MAppNotice_AppNoticeQuery extends ReqMsg {
	/**
	 * 序列化编号
	 */
	private static final long serialVersionUID = -3944473771131305243L;
	
    private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
