package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ReqMsg;

/**
 *
 * @author SHENGUOPING
 * @date  2017年11月22日 下午5:26:30
 */
public class ReqMsg_User_BankCardInfo extends ReqMsg {

	private static final long serialVersionUID = -5653942431156499682L;
	
	private Integer userId;

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	
}
