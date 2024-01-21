package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ResMsg;

public class ResMsg_SMS_SendMobiles extends ResMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7993980438112927304L;
	//发送失败的手机号，多个手机号时以逗号隔开
	private String failedMobiles;
	public String getFailedMobiles() {
		return failedMobiles;
	}
	public void setFailedMobiles(String failedMobiles) {
		this.failedMobiles = failedMobiles;
	}

}
