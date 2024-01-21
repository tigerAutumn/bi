package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ReqMsg;

/**
 *
 * @author SHENGUOPING
 * @date  2018年8月3日 下午1:24:50
 */
public class ReqMsg_User_ConfirmTransaction extends ReqMsg {

	private static final long serialVersionUID = -181200509756056113L;

	private int userId;

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

}
