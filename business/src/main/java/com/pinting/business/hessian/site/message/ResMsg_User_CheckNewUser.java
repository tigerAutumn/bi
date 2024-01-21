package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ResMsg;

public class ResMsg_User_CheckNewUser extends ResMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8950462056608142764L;
	
	private Boolean isNewUser;

	public Boolean getIsNewUser() {
		return isNewUser;
	}

	public void setIsNewUser(Boolean isNewUser) {
		this.isNewUser = isNewUser;
	}


}
