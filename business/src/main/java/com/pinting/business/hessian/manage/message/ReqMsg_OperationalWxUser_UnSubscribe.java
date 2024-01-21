package com.pinting.business.hessian.manage.message;

import com.pinting.core.hessian.msg.ReqMsg;

/**
 *
 * @author SHENGUOPING
 * @date  2018年6月19日 下午5:28:24
 */
public class ReqMsg_OperationalWxUser_UnSubscribe extends ReqMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2383503631526023359L;

	private String openId;
	private String userId;
	
	public String getOpenId() {
		return openId;
	}
	public void setOpenId(String openId) {
		this.openId = openId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
}
