package com.pinting.gateway.hessian.message.dafy;

import com.pinting.core.hessian.msg.ReqMsg;

public class B2GReqMsg_Customer_QueryCurrDeptUserInfo extends ReqMsg {

	private static final long serialVersionUID = 8309856717419967017L;

	private String deptCode;

	public String getDeptCode() {
		return deptCode;
	}

	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}
}
