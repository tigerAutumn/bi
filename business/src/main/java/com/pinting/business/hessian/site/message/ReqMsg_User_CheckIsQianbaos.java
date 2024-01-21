package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ReqMsg;

/**
 * 根据用户id或agentId判断是否是钱报系用户
 * @author bianyatian
 * @2017-1-11 下午4:36:51
 */
public class ReqMsg_User_CheckIsQianbaos extends ReqMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6054435908200814463L;
	
	private Integer userId;
	
	private Integer agentId;

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getAgentId() {
		return agentId;
	}

	public void setAgentId(Integer agentId) {
		this.agentId = agentId;
	}
	
	

}
