package com.pinting.business.model.vo;

import java.util.Date;

public class LnDepositionRepayScheduleVO extends PageInfoObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1276392666097838925L;

	private String partnerLoanId;  //资产端借款编号
	private Double approveAmount;  //借款金额
	private String dkOrderNo;  //批量代付订单号
	private Double planTotal;  //代付金额
	private Date finishTime;  //代付日期
	
	public String getPartnerLoanId() {
		return partnerLoanId;
	}
	public void setPartnerLoanId(String partnerLoanId) {
		this.partnerLoanId = partnerLoanId;
	}
	public Double getApproveAmount() {
		return approveAmount;
	}
	public void setApproveAmount(Double approveAmount) {
		this.approveAmount = approveAmount;
	}
	public String getDkOrderNo() {
		return dkOrderNo;
	}
	public void setDkOrderNo(String dkOrderNo) {
		this.dkOrderNo = dkOrderNo;
	}
	public Double getPlanTotal() {
		return planTotal;
	}
	public void setPlanTotal(Double planTotal) {
		this.planTotal = planTotal;
	}
	public Date getFinishTime() {
		return finishTime;
	}
	public void setFinishTime(Date finishTime) {
		this.finishTime = finishTime;
	}
	
	
}
