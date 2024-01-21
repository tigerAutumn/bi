package com.pinting.business.hessian.manage.message;

import com.pinting.core.hessian.msg.ResMsg;

public class ResMsg_MWxAutoReply_AddOrUpdateReply extends ResMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4853319403389174658L;

	private String errorMsg;

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
}
