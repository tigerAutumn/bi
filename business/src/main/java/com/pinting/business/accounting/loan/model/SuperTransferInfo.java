package com.pinting.business.accounting.loan.model;

public class SuperTransferInfo extends BaseAccount {
	
	private Integer sInvestorRegActId; //超级理财人REG户
	
	private Integer investorAuthActId; //普通理财人AUTH户
	
	private Integer superUserId; //超级理财人id
	
	private Integer normalUserId; //普通理财人id
	
	private Double matchAmount; //匹配金额，即普通AUTH需减少的金额

	public Integer getsInvestorRegActId() {
		return sInvestorRegActId;
	}

	public void setsInvestorRegActId(Integer sInvestorRegActId) {
		this.sInvestorRegActId = sInvestorRegActId;
	}

	public Integer getInvestorAuthActId() {
		return investorAuthActId;
	}

	public void setInvestorAuthActId(Integer investorAuthActId) {
		this.investorAuthActId = investorAuthActId;
	}

	public Integer getSuperUserId() {
		return superUserId;
	}

	public void setSuperUserId(Integer superUserId) {
		this.superUserId = superUserId;
	}

	public Integer getNormalUserId() {
		return normalUserId;
	}

	public void setNormalUserId(Integer normalUserId) {
		this.normalUserId = normalUserId;
	}

	public Double getMatchAmount() {
		return matchAmount;
	}

	public void setMatchAmount(Double matchAmount) {
		this.matchAmount = matchAmount;
	}

	
}
