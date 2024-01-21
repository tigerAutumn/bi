package com.pinting.gateway.hessian.message.dafy;

import com.pinting.core.hessian.msg.ReqMsg;

public class B2GReqMsg_Customer_QueryUserById extends ReqMsg {

	private static final long serialVersionUID = 1083329941312887514L;

	private String lUserId;

	public String getlUserId() {
		return lUserId;
	}

	public void setlUserId(String lUserId) {
		this.lUserId = lUserId;
	}
}
