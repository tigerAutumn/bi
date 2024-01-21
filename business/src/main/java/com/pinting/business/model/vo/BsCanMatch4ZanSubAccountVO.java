package com.pinting.business.model.vo;

import com.pinting.business.model.BsSubAccount;

public class BsCanMatch4ZanSubAccountVO extends BsSubAccount{
	
	private Integer userId;
	
	private Integer term;
	
	private Integer agentId;

	private String hfUserId; //恒丰用户id
	
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public Integer getTerm() {
		return term;
	}
	public void setTerm(Integer term) {
		this.term = term;
	}
	public Integer getAgentId() {
		return agentId;
	}
	public void setAgentId(Integer agentId) {
		this.agentId = agentId;
	}
	public String getHfUserId() {
		return hfUserId;
	}
	public void setHfUserId(String hfUserId) {
		this.hfUserId = hfUserId;
	}
	

}
