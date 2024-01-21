package com.pinting.business.model.vo;

import java.util.Date;

public class BsSubAc4BatchBuyVO {

	private String productCode;
	private Double productAmount;
	private Integer term;
	private Double sysReturnRate;
	private Date interestBeginDate;
	private Date lastFinishInterestDate;
	private Integer subAccountId;
	private Integer userId;
	private Integer batchBuyId;

	public Integer getBatchBuyId() {
		return batchBuyId;
	}

	public void setBatchBuyId(Integer batchBuyId) {
		this.batchBuyId = batchBuyId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getSubAccountId() {
		return subAccountId;
	}

	public void setSubAccountId(Integer subAccountId) {
		this.subAccountId = subAccountId;
	}

	public Date getInterestBeginDate() {
		return interestBeginDate;
	}

	public void setInterestBeginDate(Date interestBeginDate) {
		this.interestBeginDate = interestBeginDate;
	}

	public Date getLastFinishInterestDate() {
		return lastFinishInterestDate;
	}

	public void setLastFinishInterestDate(Date lastFinishInterestDate) {
		this.lastFinishInterestDate = lastFinishInterestDate;
	}

	public Double getSysReturnRate() {
		return sysReturnRate;
	}

	public void setSysReturnRate(Double sysReturnRate) {
		this.sysReturnRate = sysReturnRate;
	}

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public Double getProductAmount() {
		return productAmount;
	}

	public void setProductAmount(Double productAmount) {
		this.productAmount = productAmount;
	}

	public Integer getTerm() {
		return term;
	}

	public void setTerm(Integer term) {
		this.term = term;
	}

	@Override
	public String toString() {
		return "该天产品购买金额统计 [productCode=" + productCode
				+ ", productAmount=" + productAmount + ", term=" + term
				+ ", sysReturnRate=" + sysReturnRate + ", interestBeginDate="
				+ interestBeginDate + ", lastFinishInterestDate="
				+ lastFinishInterestDate + ", subAccountId=" + subAccountId
				+ ", batchBuyId=" + batchBuyId + ", userId=" + userId + "]";
	}

}
