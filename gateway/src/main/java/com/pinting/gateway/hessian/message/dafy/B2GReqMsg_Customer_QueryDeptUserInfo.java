package com.pinting.gateway.hessian.message.dafy;

import com.pinting.core.hessian.msg.ReqMsg;

public class B2GReqMsg_Customer_QueryDeptUserInfo extends ReqMsg {

	private static final long serialVersionUID = -8300031892046654633L;

	private String deptCode;

	public String getDeptCode() {
		return deptCode;
	}

	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}
}
