package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ReqMsg;

public class ReqMsg_User_AddApplication extends ReqMsg {

	/**
	 * @author zhangpeng
	 * 2018/07/08
	 */
	private static final long serialVersionUID = 7905309748511845664L;

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
