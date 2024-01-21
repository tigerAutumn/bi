package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ReqMsg;

public class ReqMsg_User_CsaiRegister extends ReqMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3483123272860316049L;
	private String mobile;
	private Integer agentId;
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public Integer getAgentId() {
		return agentId;
	}
	public void setAgentId(Integer agentId) {
		this.agentId = agentId;
	}

	
}
