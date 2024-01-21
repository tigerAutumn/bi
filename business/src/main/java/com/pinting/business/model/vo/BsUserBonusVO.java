package com.pinting.business.model.vo;

import com.pinting.business.model.BsUser;

public class BsUserBonusVO extends BsUser {
	private Double validProductAmount;
	private Double bonusAmount;
	public Double getValidProductAmount() {
		return validProductAmount;
	}
	public void setValidProductAmount(Double validProductAmount) {
		this.validProductAmount = validProductAmount;
	}
	public Double getBonusAmount() {
		return bonusAmount;
	}
	public void setBonusAmount(Double bonusAmount) {
		this.bonusAmount = bonusAmount;
	}
}
