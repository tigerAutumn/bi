package com.pinting.business.model.vo;

import com.pinting.business.model.BsSubAccount;

public class BsSubAc4InterestVO extends BsSubAccount{
	
	private String accountCode;
	private Integer userId;
	private Double totalInterest;
	private String nick;
	private String mobile;
	private Double productAccuInterest;
	private String entrustStatus;
	private Double redBalance;
	private Integer redAccountId;

	public Double getRedBalance() {
		return redBalance;
	}

	public void setRedBalance(Double redBalance) {
		this.redBalance = redBalance;
	}

	public Integer getRedAccountId() {
		return redAccountId;
	}

	public void setRedAccountId(Integer redAccountId) {
		this.redAccountId = redAccountId;
	}

	public String getAccountCode() {
		return accountCode;
	}
	public void setAccountCode(String accountCode) {
		this.accountCode = accountCode;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public Double getTotalInterest() {
		return totalInterest;
	}
	public void setTotalInterest(Double totalInterest) {
		this.totalInterest = totalInterest;
	}
	public String getNick() {
		return nick;
	}
	public void setNick(String nick) {
		this.nick = nick;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public Double getProductAccuInterest() {
		return productAccuInterest;
	}
	public void setProductAccuInterest(Double productAccuInterest) {
		this.productAccuInterest = productAccuInterest;
	}
	public String getEntrustStatus() {
		return entrustStatus;
	}
	public void setEntrustStatus(String entrustStatus) {
		this.entrustStatus = entrustStatus;
	}

	
}
