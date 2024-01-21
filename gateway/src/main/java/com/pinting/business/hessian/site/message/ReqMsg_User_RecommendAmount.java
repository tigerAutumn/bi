package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ReqMsg;

public class ReqMsg_User_RecommendAmount extends ReqMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8684259000613734841L;
	
	private Integer userId;

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	
}
