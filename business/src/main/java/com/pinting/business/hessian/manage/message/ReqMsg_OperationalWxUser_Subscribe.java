package com.pinting.business.hessian.manage.message;

import com.pinting.core.hessian.msg.ReqMsg;

/**
 *
 * @author SHENGUOPING
 * @date  2018年6月19日 下午5:15:38
 */
public class ReqMsg_OperationalWxUser_Subscribe extends ReqMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5695355815347633627L;
	private String openId;
	private String userId;
	private String accessToken;
	
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
	public String getAccessToken() {
		return accessToken;
	}
	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}
	
}
