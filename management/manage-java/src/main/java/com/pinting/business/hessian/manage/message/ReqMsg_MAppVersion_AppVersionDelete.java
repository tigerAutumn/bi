package com.pinting.business.hessian.manage.message;

import com.pinting.core.hessian.msg.ReqMsg;

public class ReqMsg_MAppVersion_AppVersionDelete extends ReqMsg {
	/**
	 * 序列化编号
	 */
	private static final long serialVersionUID = -3944473771131305243L;
	
	private Integer id;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
}
