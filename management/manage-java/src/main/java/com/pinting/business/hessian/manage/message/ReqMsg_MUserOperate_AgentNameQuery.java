package com.pinting.business.hessian.manage.message;

import java.util.List;

import com.pinting.core.hessian.msg.ReqMsg;

public class ReqMsg_MUserOperate_AgentNameQuery extends ReqMsg {

	private static final long serialVersionUID = -4623479475625277680L;
	
	private List<Integer> agentIds;

	public List<Integer> getAgentIds() {
		return agentIds;
	}

	public void setAgentIds(List<Integer> agentIds) {
		this.agentIds = agentIds;
	}
}
