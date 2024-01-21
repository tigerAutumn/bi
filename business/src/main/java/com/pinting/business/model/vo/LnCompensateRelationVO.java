package com.pinting.business.model.vo;

import com.pinting.business.model.LnCompensateRelation;

public class LnCompensateRelationVO extends LnCompensateRelation {
	
	private String bsUserName;
	
	private String bsIdCard;

	private Integer repaySerial;

	public String getBsUserName() {
		return bsUserName;
	}

	public void setBsUserName(String bsUserName) {
		this.bsUserName = bsUserName;
	}

	public String getBsIdCard() {
		return bsIdCard;
	}

	public void setBsIdCard(String bsIdCard) {
		this.bsIdCard = bsIdCard;
	}

	public Integer getRepaySerial() {
		return repaySerial;
	}

	public void setRepaySerial(Integer repaySerial) {
		this.repaySerial = repaySerial;
	}
}
