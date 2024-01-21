package com.pinting.business.hessian.manage.message;

import com.pinting.core.hessian.msg.ReqMsg;

public class ReqMsg_BsSysMessage_BsSysMessageDelete extends ReqMsg {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1208433639075876296L;
	private Integer id;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
}
