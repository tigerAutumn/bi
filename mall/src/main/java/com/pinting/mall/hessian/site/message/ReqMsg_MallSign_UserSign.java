package com.pinting.mall.hessian.site.message;

import com.pinting.core.hessian.msg.ReqMsg;

public class ReqMsg_MallSign_UserSign extends ReqMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7672690769552598516L;

	private Integer userId;

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}
}
