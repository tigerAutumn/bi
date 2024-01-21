package com.pinting.business.hessian.manage.message;

import com.pinting.core.hessian.msg.ResMsg;

public class ResMsg_MUserOperate_AgentNameQuery extends ResMsg {

	private static final long serialVersionUID = -4623479475625277680L;
	
	private String agentNames;

	public String getAgentNames() {
		return agentNames;
	}

	public void setAgentNames(String agentNames) {
		this.agentNames = agentNames;
	}
}
