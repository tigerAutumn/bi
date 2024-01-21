package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ReqMsg;

public class ReqMsg_User_AgentQuery extends ReqMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4606159033165451433L;
	
	private Integer agentId;

	public Integer getAgentId() {
		return agentId;
	}

	public void setAgentId(Integer agentId) {
		this.agentId = agentId;
	}

	
	
}
