package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ReqMsg;

public class ReqMsg_User_AppVersion extends ReqMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8731186135503168289L;
	
	
	private Integer userId;  //用户ID
	
	private String  appVersion;  //App版本号

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getAppVersion() {
		return appVersion;
	}

	public void setAppVersion(String appVersion) {
		this.appVersion = appVersion;
	}
	
	

}
