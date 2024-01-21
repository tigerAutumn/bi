package com.pinting.business.model.vo;

/**
 *
 * @author SHENGUOPING
 * @date  2018年4月11日 下午6:05:48
 */
public class BsPartnerServiceFeeVO {

	private Integer smsCount;
	
	private Integer authCount;
	
	private Integer loanCount;
	
	private Integer repayCount;
	
	private Double loanAmount;

	/* 协议支付手续费 */
	private Double agreementFeeAmount;

	public Integer getSmsCount() {
		return smsCount;
	}

	public void setSmsCount(Integer smsCount) {
		this.smsCount = smsCount;
	}

	public Integer getAuthCount() {
		return authCount;
	}

	public void setAuthCount(Integer authCount) {
		this.authCount = authCount;
	}

	public Integer getLoanCount() {
		return loanCount;
	}

	public void setLoanCount(Integer loanCount) {
		this.loanCount = loanCount;
	}

	public Integer getRepayCount() {
		return repayCount;
	}

	public void setRepayCount(Integer repayCount) {
		this.repayCount = repayCount;
	}

	public Double getLoanAmount() {
		return loanAmount;
	}

	public void setLoanAmount(Double loanAmount) {
		this.loanAmount = loanAmount;
	}

	public Double getAgreementFeeAmount() {
		return agreementFeeAmount;
	}

	public void setAgreementFeeAmount(Double agreementFeeAmount) {
		this.agreementFeeAmount = agreementFeeAmount;
	}
}
