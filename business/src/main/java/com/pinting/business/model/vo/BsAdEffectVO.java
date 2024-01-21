package com.pinting.business.model.vo;

import java.util.Date;

import com.pinting.business.model.BsAdEffect;

public class BsAdEffectVO extends BsAdEffect {
	private Double userBuyAmount;
	private Integer userId;
	private Date registerTime;

	public Double getUserBuyAmount() {
		return userBuyAmount;
	}

	public void setUserBuyAmount(Double userBuyAmount) {
		this.userBuyAmount = userBuyAmount;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Date getRegisterTime() {
		return registerTime;
	}

	public void setRegisterTime(Date registerTime) {
		this.registerTime = registerTime;
	}
	
	
}
