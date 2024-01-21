package com.pinting.business.model.vo;

import com.pinting.business.model.LnLoan;

import java.util.Date;

public class RevenueSettleDetailVO extends LnLoan {

	private String partnerRepayId;
	
	private String partnerUserId;
	
	private Date finishTime;

	/* 合作方业务标识 */
	private String partnerBusinessFlag;
	
	public String getPartnerRepayId() {
		return partnerRepayId;
	}

	public void setPartnerRepayId(String partnerRepayId) {
		this.partnerRepayId = partnerRepayId;
	}

	public Date getFinishTime() {
		return finishTime;
	}

	public void setFinishTime(Date finishTime) {
		this.finishTime = finishTime;
	}

	public String getPartnerUserId() {
		return partnerUserId;
	}

	public void setPartnerUserId(String partnerUserId) {
		this.partnerUserId = partnerUserId;
	}

	@Override
	public String getPartnerBusinessFlag() {
		return partnerBusinessFlag;
	}

	@Override
	public void setPartnerBusinessFlag(String partnerBusinessFlag) {
		this.partnerBusinessFlag = partnerBusinessFlag;
	}
}
