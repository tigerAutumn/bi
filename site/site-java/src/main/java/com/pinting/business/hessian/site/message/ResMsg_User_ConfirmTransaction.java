package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ResMsg;

/**
 *
 * @author SHENGUOPING
 * @date  2018年8月3日 下午1:27:12
 */
public class ResMsg_User_ConfirmTransaction extends ResMsg {

	private static final long serialVersionUID = 2948112760745400889L;

	private Boolean isConfirmTransaction;

	private String userStatus;
	
	public Boolean getIsConfirmTransaction() {
		return isConfirmTransaction;
	}

	public void setIsConfirmTransaction(Boolean isConfirmTransaction) {
		this.isConfirmTransaction = isConfirmTransaction;
	}

	public String getUserStatus() {
		return userStatus;
	}

	public void setUserStatus(String userStatus) {
		this.userStatus = userStatus;
	}
	
}
