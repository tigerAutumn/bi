package com.pinting.business.hessian.manage.message;

import com.pinting.core.hessian.msg.ReqMsg;

public class ReqMsg_MWxAutoReply_GetReplyById extends ReqMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = 42424964332760034L;

	private Integer id;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
}
