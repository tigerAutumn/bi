package com.pinting.business.model;

import java.util.Date;

public class BsMatch {
    private Integer id;

	private Integer userId;

	private Integer subAccountId;

	private Integer loanRelativeId;

	private String propertySymbol;

	private Double amount;

	private Double leftPrincipal;

	private Double repayAmount;

	private Date lastRepayDate;

	private String repayStatus;

	private String note;

	private Date matchDate;

	private Date createTime;

	private Date updateTime;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public Integer getLoanRelativeId() {
		return loanRelativeId;
	}

	public void setLoanRelativeId(Integer loanRelativeId) {
		this.loanRelativeId = loanRelativeId;
	}

	public String getPropertySymbol() {
		return propertySymbol;
	}

	public void setPropertySymbol(String propertySymbol) {
		this.propertySymbol = propertySymbol;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public Double getLeftPrincipal() {
		return leftPrincipal;
	}

	public void setLeftPrincipal(Double leftPrincipal) {
		this.leftPrincipal = leftPrincipal;
	}

	public Double getRepayAmount() {
		return repayAmount;
	}

	public void setRepayAmount(Double repayAmount) {
		this.repayAmount = repayAmount;
	}

	public Date getLastRepayDate() {
		return lastRepayDate;
	}

	public void setLastRepayDate(Date lastRepayDate) {
		this.lastRepayDate = lastRepayDate;
	}

	public String getRepayStatus() {
		return repayStatus;
	}

	public void setRepayStatus(String repayStatus) {
		this.repayStatus = repayStatus;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public Date getMatchDate() {
		return matchDate;
	}

	public void setMatchDate(Date matchDate) {
		this.matchDate = matchDate;
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