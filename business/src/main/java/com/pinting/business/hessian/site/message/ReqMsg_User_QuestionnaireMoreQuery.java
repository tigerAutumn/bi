package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ReqMsg;

public class ReqMsg_User_QuestionnaireMoreQuery extends ReqMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = 183924242359914606L;
	
	private Integer userId;

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	
}
