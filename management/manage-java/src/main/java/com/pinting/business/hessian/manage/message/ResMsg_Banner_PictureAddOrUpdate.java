package com.pinting.business.hessian.manage.message;

import com.pinting.core.hessian.msg.ResMsg;

public class ResMsg_Banner_PictureAddOrUpdate extends ResMsg {

	private static final long serialVersionUID = 463230189204622698L;
	
	private String retMsg;

	private String retCode;
	
	public String getRetMsg() {
		return retMsg;
	}

	public void setRetMsg(String retMsg) {
		this.retMsg = retMsg;
	}

	public String getRetCode() {
		return retCode;
	}

	public void setRetCode(String retCode) {
		this.retCode = retCode;
	}
	
}
