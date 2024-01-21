package com.pinting.mall.hessian.site.message;

import com.pinting.core.hessian.msg.ReqMsg;

public class ReqMsg_MallPoints_UserPoints extends ReqMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1651053571290486175L;

	private Integer userId;

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}
}
