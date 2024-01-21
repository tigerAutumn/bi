package com.pinting.business.model.vo;

import com.pinting.business.model.BsSubAccount;

public class BsSubAccountVO4DepFixedMatch extends BsSubAccount {

	private Integer redSubAccountId; //红包户subAccountid 
	
	private Double redAvailableBalance; //红包户可用金额

	private Integer userId;
	
	private Integer agentId;

	private String hfUserId; //恒丰用户id
	
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
	public Integer getRedSubAccountId() {
		return redSubAccountId;
	}

	public void setRedSubAccountId(Integer redSubAccountId) {
		this.redSubAccountId = redSubAccountId;
	}

	public Double getRedAvailableBalance() {
		return redAvailableBalance;
	}

	public void setRedAvailableBalance(Double redAvailableBalance) {
		this.redAvailableBalance = redAvailableBalance;
	}
	public String getHfUserId() {
		return hfUserId;
	}
	public void setHfUserId(String hfUserId) {
		this.hfUserId = hfUserId;
	}
}
