package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ReqMsg;

public class ReqMsg_WechatMsg_sendMesg extends ReqMsg {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8240298844447468267L;
	private String openId;

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

}
