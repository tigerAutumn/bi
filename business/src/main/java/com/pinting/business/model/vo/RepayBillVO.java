package com.pinting.business.model.vo;

public class RepayBillVO {
	
	private Integer loanId;
	
	private Integer repayPlanId;
	
	private String partnerRepayId;
	
	private String partnerLoanId;
	
	private String partnerUserId;
	
	private String scheStatus;

	private Integer billId;

	private String partnerBusinessFlag; // 合作方业务标识

	public Integer getBillId() {
		return billId;
	}

	public void setBillId(Integer billId) {
		this.billId = billId;
	}

	public String getPartnerRepayId() {
		return partnerRepayId;
	}

	public void setPartnerRepayId(String partnerRepayId) {
		this.partnerRepayId = partnerRepayId;
	}

	public Integer getLoanId() {
		return loanId;
	}

	public void setLoanId(Integer loanId) {
		this.loanId = loanId;
	}

	public String getPartnerLoanId() {
		return partnerLoanId;
	}

	public void setPartnerLoanId(String partnerLoanId) {
		this.partnerLoanId = partnerLoanId;
	}

	public String getPartnerUserId() {
		return partnerUserId;
	}

	public void setPartnerUserId(String partnerUserId) {
		this.partnerUserId = partnerUserId;
	}

	public String getScheStatus() {
		return scheStatus;
	}

	public void setScheStatus(String scheStatus) {
		this.scheStatus = scheStatus;
	}

	public Integer getRepayPlanId() {
		return repayPlanId;
	}

	public void setRepayPlanId(Integer repayPlanId) {
		this.repayPlanId = repayPlanId;
	}

	public String getPartnerBusinessFlag() {
		return partnerBusinessFlag;
	}

	public void setPartnerBusinessFlag(String partnerBusinessFlag) {
		this.partnerBusinessFlag = partnerBusinessFlag;
	}
}
