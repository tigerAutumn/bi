package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ReqMsg;

public class ReqMsg_RedPacketInfo_GetUserRegistRedPakt extends ReqMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = 239336484123623500L;
	private Integer userId;
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}

}
