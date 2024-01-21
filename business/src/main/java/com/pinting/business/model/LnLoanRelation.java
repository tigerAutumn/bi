package com.pinting.business.model;

import java.util.Date;

public class LnLoanRelation {
    private Integer id;

	private Integer loanId;

	private Integer bsUserId;

	private Integer bsSubAccountId;

	private Integer lnUserId;

	private Integer lnSubAccountId;

	private Double initAmount;

	private Double discountAmount;

	private Double totalAmount;

	private Double leftAmount;

	private Integer firstTerm;

	private String status;

	private String transMark;

	private String bidStatus;

	private Date createTime;

	private Date updateTime;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getLoanId() {
		return loanId;
	}

	public void setLoanId(Integer loanId) {
		this.loanId = loanId;
	}

	public Integer getBsUserId() {
		return bsUserId;
	}

	public void setBsUserId(Integer bsUserId) {
		this.bsUserId = bsUserId;
	}

	public Integer getBsSubAccountId() {
		return bsSubAccountId;
	}

	public void setBsSubAccountId(Integer bsSubAccountId) {
		this.bsSubAccountId = bsSubAccountId;
	}

	public Integer getLnUserId() {
		return lnUserId;
	}

	public void setLnUserId(Integer lnUserId) {
		this.lnUserId = lnUserId;
	}

	public Integer getLnSubAccountId() {
		return lnSubAccountId;
	}

	public void setLnSubAccountId(Integer lnSubAccountId) {
		this.lnSubAccountId = lnSubAccountId;
	}

	public Double getInitAmount() {
		return initAmount;
	}

	public void setInitAmount(Double initAmount) {
		this.initAmount = initAmount;
	}

	public Double getDiscountAmount() {
		return discountAmount;
	}

	public void setDiscountAmount(Double discountAmount) {
		this.discountAmount = discountAmount;
	}

	public Double getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(Double totalAmount) {
		this.totalAmount = totalAmount;
	}

	public Double getLeftAmount() {
		return leftAmount;
	}

	public void setLeftAmount(Double leftAmount) {
		this.leftAmount = leftAmount;
	}

	public Integer getFirstTerm() {
		return firstTerm;
	}

	public void setFirstTerm(Integer firstTerm) {
		this.firstTerm = firstTerm;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getTransMark() {
		return transMark;
	}

	public void setTransMark(String transMark) {
		this.transMark = transMark;
	}

	public String getBidStatus() {
		return bidStatus;
	}

	public void setBidStatus(String bidStatus) {
		this.bidStatus = bidStatus;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

}