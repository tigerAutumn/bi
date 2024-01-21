package com.pinting.business.hessian.manage.message;

import java.util.ArrayList;
import java.util.HashMap;

import com.pinting.core.hessian.msg.ResMsg;

public class ResMsg_MUserOperate_AgentQuery extends ResMsg {

	private static final long serialVersionUID = -4623479475625277680L;
	
	private ArrayList<HashMap<String, Object>> agentList;

	public ArrayList<HashMap<String, Object>> getAgentList() {
		return agentList;
	}

	public void setAgentList(ArrayList<HashMap<String, Object>> agentList) {
		this.agentList = agentList;
	}
}
