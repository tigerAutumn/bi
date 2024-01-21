package com.pinting.business.hessian.manage.message;

import com.pinting.core.hessian.msg.ResMsg;

public class ResMsg_Banner_BannerAddOrUpdate extends ResMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7931351989560015778L;

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
