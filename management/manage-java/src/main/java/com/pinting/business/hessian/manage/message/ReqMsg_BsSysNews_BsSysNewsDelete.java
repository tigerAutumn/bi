package com.pinting.business.hessian.manage.message;

import com.pinting.core.hessian.msg.ReqMsg;

public class ReqMsg_BsSysNews_BsSysNewsDelete extends ReqMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = -223388280724899068L;
	private Integer id;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
}
