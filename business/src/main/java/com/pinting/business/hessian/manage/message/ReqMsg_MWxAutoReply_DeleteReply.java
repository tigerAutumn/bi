package com.pinting.business.hessian.manage.message;

import com.pinting.core.hessian.msg.ReqMsg;

public class ReqMsg_MWxAutoReply_DeleteReply extends ReqMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7674470565189616871L;

	private Integer id;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
}
