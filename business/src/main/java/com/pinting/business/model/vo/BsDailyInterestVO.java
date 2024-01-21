package com.pinting.business.model.vo;

import com.pinting.business.model.BsDailyInterest;

public class BsDailyInterestVO extends BsDailyInterest{

	private Double totalInterest;
	private Double currentInterest;
	private String nick;
	private String mobile;
	
	public Double getTotalInterest() {
		return totalInterest;
	}
	public void setTotalInterest(Double totalInterest) {
		this.totalInterest = totalInterest;
	}
	public Double getCurrentInterest() {
		return currentInterest;
	}
	public void setCurrentInterest(Double currentInterest) {
		this.currentInterest = currentInterest;
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
}
