package com.pinting.business.hessian.manage.message;

import com.pinting.core.hessian.msg.ResMsg;

public class ResMsg_AutoPacket_AddAutoPacket extends ResMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3763128383366330941L;
	
	private String errorMsg;

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

}
