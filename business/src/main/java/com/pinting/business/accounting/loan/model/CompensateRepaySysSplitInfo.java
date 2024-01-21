package com.pinting.business.accounting.loan.model;

public class CompensateRepaySysSplitInfo {

	private Integer loanId; //借款编号
	
	private String partnerRepayId; //合作方计划还款编号
	
	private String partnerLoanId; //合作方借款id
	
	private String orderNo; //代偿单号
	 
	private Double repayAmount; //还款总金额(还款本金+利息+逾期本利)
	
	private Integer lnCompensateDetailId; //代偿通知明细表主键id,主动

	public Integer getLoanId() {
		return loanId;
	}

	public void setLoanId(Integer loanId) {
		this.loanId = loanId;
	}

	public String getPartnerRepayId() {
		return partnerRepayId;
	}

	public void setPartnerRepayId(String partnerRepayId) {
		this.partnerRepayId = partnerRepayId;
	}

	public String getPartnerLoanId() {
		return partnerLoanId;
	}

	public void setPartnerLoanId(String partnerLoanId) {
		this.partnerLoanId = partnerLoanId;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public Double getRepayAmount() {
		return repayAmount;
	}

	public void setRepayAmount(Double repayAmount) {
		this.repayAmount = repayAmount;
	}

	public Integer getLnCompensateDetailId() {
		return lnCompensateDetailId;
	}

	public void setLnCompensateDetailId(Integer lnCompensateDetailId) {
		this.lnCompensateDetailId = lnCompensateDetailId;
	}
	 
	
}
