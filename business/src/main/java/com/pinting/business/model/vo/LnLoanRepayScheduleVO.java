package com.pinting.business.model.vo;

import java.util.Date;

import com.pinting.business.model.LnLoan;

public class LnLoanRepayScheduleVO extends LnLoan{

	private Integer repayScheduleId;

    private String partnerRepayId;

    private Integer serialId;
    
    private Date planDate;

	public Integer getRepayScheduleId() {
		return repayScheduleId;
	}

	public void setRepayScheduleId(Integer repayScheduleId) {
		this.repayScheduleId = repayScheduleId;
	}

	public String getPartnerRepayId() {
		return partnerRepayId;
	}

	public void setPartnerRepayId(String partnerRepayId) {
		this.partnerRepayId = partnerRepayId;
	}

	public Integer getSerialId() {
		return serialId;
	}

	public void setSerialId(Integer serialId) {
		this.serialId = serialId;
	}

	public Date getPlanDate() {
		return planDate;
	}

	public void setPlanDate(Date planDate) {
		this.planDate = planDate;
	}
    
    
}
