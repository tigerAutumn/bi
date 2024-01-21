package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ResMsg;

public class ResMsg_User_ValidUser extends ResMsg {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -898468013014239668L;
	private Boolean validUser;

	public Boolean getValidUser() {
		return validUser;
	}

	public void setValidUser(Boolean validUser) {
		this.validUser = validUser;
	} 
}
