package com.pinting.gateway.hessian.message.dafy;

import com.pinting.core.hessian.msg.ReqMsg;

public class B2GReqMsg_Customer_QueryCurrDeptInfo extends ReqMsg {

	private static final long serialVersionUID = -1134544913122176705L;
	
	private String strDeptCode;

	public String getStrDeptCode() {
		return strDeptCode;
	}

	public void setStrDeptCode(String strDeptCode) {
		this.strDeptCode = strDeptCode;
	}

}
