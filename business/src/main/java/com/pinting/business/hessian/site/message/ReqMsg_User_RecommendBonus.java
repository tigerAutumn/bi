package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ReqMsg;

/**
 * 已获得推荐奖励
 * @author SHENGP
 * @date  2017年7月20日 下午5:31:56
 */
public class ReqMsg_User_RecommendBonus extends ReqMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7620404904703228056L;
	
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
