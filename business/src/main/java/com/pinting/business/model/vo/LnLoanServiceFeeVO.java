package com.pinting.business.model.vo;

import java.util.Date;

/**
 * 存管产品统计-借款服务费
 * @author SHENGP
 * @date  2018年3月30日 下午1:31:36
 */
public class LnLoanServiceFeeVO extends PageInfoObject {
	
	private static final long serialVersionUID = 2469545811290813964L;

	// 序号
	private Integer serialNo;
	
	// 单据编号
	private String billNo;
	
	// 融资客户名称
	private String userName;
	
	// 融资客户代码
	private String lnCustomerCode;
	
	// 手机号
	private String mobile;
	
	// 资产方
	private String partnerCode;
	
	// 结算本金
	private Double principal; 
	
	// 融资客户应算利息
	private Double interest;
	
	// 借款服务费
	private Double loanServiceFee;
	
	// 结算日期
	private Date finishTime;
	
	// 备注
	private String note;

	// 融资客户应付利息总计 
	private Double loanInterestTotal;
	
	// 借款服务费总计
	private Double loanServiceFeeTotal;

	// 借款产品标识
	private String partnerBusinessFlag;
	
	public Integer getSerialNo() {
		return serialNo;
	}

	public void setSerialNo(Integer serialNo) {
		this.serialNo = serialNo;
	}

	public String getBillNo() {
		return billNo;
	}

	public void setBillNo(String billNo) {
		this.billNo = billNo;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getPartnerCode() {
		return partnerCode;
	}

	public void setPartnerCode(String partnerCode) {
		this.partnerCode = partnerCode;
	}

	public Double getPrincipal() {
		return principal;
	}

	public void setPrincipal(Double principal) {
		this.principal = principal;
	}

	public Double getInterest() {
		return interest;
	}

	public void setInterest(Double interest) {
		this.interest = interest;
	}

	public Double getLoanServiceFee() {
		return loanServiceFee;
	}

	public void setLoanServiceFee(Double loanServiceFee) {
		this.loanServiceFee = loanServiceFee;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getLnCustomerCode() {
		return lnCustomerCode;
	}

	public void setLnCustomerCode(String lnCustomerCode) {
		this.lnCustomerCode = lnCustomerCode;
	}

	public Date getFinishTime() {
		return finishTime;
	}

	public void setFinishTime(Date finishTime) {
		this.finishTime = finishTime;
	}

	public Double getLoanInterestTotal() {
		return loanInterestTotal;
	}

	public void setLoanInterestTotal(Double loanInterestTotal) {
		this.loanInterestTotal = loanInterestTotal;
	}

	public Double getLoanServiceFeeTotal() {
		return loanServiceFeeTotal;
	}

	public void setLoanServiceFeeTotal(Double loanServiceFeeTotal) {
		this.loanServiceFeeTotal = loanServiceFeeTotal;
	}

	public String getPartnerBusinessFlag() {
		return partnerBusinessFlag;
	}

	public void setPartnerBusinessFlag(String partnerBusinessFlag) {
		this.partnerBusinessFlag = partnerBusinessFlag;
	}
}
