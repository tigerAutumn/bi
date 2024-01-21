package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ReqMsg;

/**
 * 
 * @author zhangpeng
 * 2018-7-8
 */
public class ReqMsg_User_AddApplication extends ReqMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = 907396676421379248L;

	private Integer userId;
	
	private String applications;
	
	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getApplications() {
		return applications;
	}

	public void setApplications(String applications) {
		this.applications = applications;
	}

}
