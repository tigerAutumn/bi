package com.pinting.business.hessian.manage.message;

import com.pinting.core.hessian.msg.ReqMsg;

public class ReqMsg_BsErrorCode_BsErrorCodeDelete extends ReqMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6219991129056677910L;
	
	private Integer id;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

}
