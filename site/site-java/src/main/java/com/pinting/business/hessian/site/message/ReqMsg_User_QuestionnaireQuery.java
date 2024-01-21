package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ReqMsg;

public class ReqMsg_User_QuestionnaireQuery extends ReqMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4961644389338866497L;
	
	/**
	 * 用户ID
	 */
	private Integer userId;

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	
}
