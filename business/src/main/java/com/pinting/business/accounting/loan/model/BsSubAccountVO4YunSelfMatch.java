package com.pinting.business.accounting.loan.model;

import com.pinting.business.model.BsSubAccount;

public class BsSubAccountVO4YunSelfMatch extends BsSubAccount {

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
