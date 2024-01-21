package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ReqMsg;

public class ReqMsg_Game_Check extends ReqMsg {

	


	/**
	 * 
	 */
	private static final long serialVersionUID = 8691034391861102897L;
	private String mobile;
	private String openId;
	
	
	public String getOpenId() {
		return openId;
	}
	public void setOpenId(String openId) {
		this.openId = openId;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	
}
