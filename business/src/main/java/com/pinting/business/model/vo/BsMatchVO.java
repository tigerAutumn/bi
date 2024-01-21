package com.pinting.business.model.vo;

import com.pinting.business.model.BsMatch;

import java.util.Date;

public class BsMatchVO extends BsMatch{
	
	private String borrowerName; //借款人
	
	private Double toOtherAmount;//转给他人的匹配金额

	private String transMark; //债转标记

	private Date loanTime; //借款成功的时间

	private Date transferTime; //债转发生的时间

	private String idCard; //借款人身份证号
	
	private String repayFlag;//迁移前的还款标记
	
	private String stockGeneratedClaims;
	
	private Date openTime;
	
	private String tableFlag; //迁移前后表格标志

	// 7贷随借随还产品新增业务标识
    private String partnerBusinessFlag;

	private String partnerCode; //借款人来源云贷或7贷
    
	public String getBorrowerName() {
		return borrowerName;
	}

	public void setBorrowerName(String borrowerName) {
		this.borrowerName = borrowerName;
	}

	public Double getToOtherAmount() {
		return toOtherAmount;
	}

	public void setToOtherAmount(Double toOtherAmount) {
		this.toOtherAmount = toOtherAmount;
	}

	public String getTransMark() {
		return transMark;
	}

	public void setTransMark(String transMark) {
		this.transMark = transMark;
	}

	public Date getLoanTime() {
		return loanTime;
	}

	public void setLoanTime(Date loanTime) {
		this.loanTime = loanTime;
	}

	public Date getTransferTime() {
		return transferTime;
	}

	public void setTransferTime(Date transferTime) {
		this.transferTime = transferTime;
	}

	public String getIdCard() {
		return idCard;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}

	public String getPartnerBusinessFlag() {
		return partnerBusinessFlag;
	}

	public void setPartnerBusinessFlag(String partnerBusinessFlag) {
		this.partnerBusinessFlag = partnerBusinessFlag;
	}

	public String getRepayFlag() {
		return repayFlag;
	}

	public void setRepayFlag(String repayFlag) {
		this.repayFlag = repayFlag;
	}

	public Date getOpenTime() {
		return openTime;
	}

	public void setOpenTime(Date openTime) {
		this.openTime = openTime;
	}

	public String getStockGeneratedClaims() {
		return stockGeneratedClaims;
	}

	public void setStockGeneratedClaims(String stockGeneratedClaims) {
		this.stockGeneratedClaims = stockGeneratedClaims;
	}

	public String getTableFlag() {
		return tableFlag;
	}

	public void setTableFlag(String tableFlag) {
		this.tableFlag = tableFlag;
	}

	public String getPartnerCode() {
		return partnerCode;
	}

	public void setPartnerCode(String partnerCode) {
		this.partnerCode = partnerCode;
	}
}
