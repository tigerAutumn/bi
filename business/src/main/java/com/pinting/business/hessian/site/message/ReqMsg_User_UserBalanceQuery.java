package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ReqMsg;
/**
 * 
 * @author Dragon & cat
 * @date 2016-8-25
 */
public class ReqMsg_User_UserBalanceQuery extends ReqMsg {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7285036933820653152L;

	private String userId;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
}
