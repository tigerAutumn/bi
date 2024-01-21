package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ReqMsg;

public class ReqMsg_UserWithdraw_MyBonusWithdrawInfo extends ReqMsg {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2425339021859550099L;
	/*用户ID*/
	private		Integer		userId;

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	
	
}
