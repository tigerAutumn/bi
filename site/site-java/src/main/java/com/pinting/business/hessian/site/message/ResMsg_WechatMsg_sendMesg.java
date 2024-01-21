package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ResMsg;


public class ResMsg_WechatMsg_sendMesg extends ResMsg{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6501581198595905592L;
	private String returnMsg;

	public String getReturnMsg() {
		return returnMsg;
	}

	public void setReturnMsg(String returnMsg) {
		this.returnMsg = returnMsg;
	}
}
