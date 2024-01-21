package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ReqMsg;

/**
 *
 * @author SHENGP
 * @date  2017年7月5日 下午3:58:19
 */
public class ReqMsg_User_QuestionnaireMoreQuery extends ReqMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7022900884708522851L;

	/**
	 * 用户ID
	 */
	private Integer userId;
	
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	
}
