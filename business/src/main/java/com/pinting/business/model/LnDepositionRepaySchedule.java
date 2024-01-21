package com.pinting.business.model;

import java.util.Date;

public class LnDepositionRepaySchedule {
    private Integer id;

	private Integer lnUserId;

	private Integer loanId;

	private String partnerRepayId;

	private Integer serialId;

	private Date planDate;

	private Date finishTime;

	private Double planTotal;

	private String status;

	private String returnFlag;

	private String repayType;

	private String dfOrderNo;

	private String dkOrderNo;

	private String repayOrderNo;

	private String returnOrderNo;

	private String note;

	private Date createTime;

	private Date updateTime;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getLnUserId() {
		return lnUserId;
	}

	public void setLnUserId(Integer lnUserId) {
		this.lnUserId = lnUserId;
	}

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

	public Date getFinishTime() {
		return finishTime;
	}

	public void setFinishTime(Date finishTime) {
		this.finishTime = finishTime;
	}

	public Double getPlanTotal() {
		return planTotal;
	}

	public void setPlanTotal(Double planTotal) {
		this.planTotal = planTotal;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getReturnFlag() {
		return returnFlag;
	}

	public void setReturnFlag(String returnFlag) {
		this.returnFlag = returnFlag;
	}

	public String getRepayType() {
		return repayType;
	}

	public void setRepayType(String repayType) {
		this.repayType = repayType;
	}

	public String getDfOrderNo() {
		return dfOrderNo;
	}

	public void setDfOrderNo(String dfOrderNo) {
		this.dfOrderNo = dfOrderNo;
	}

	public String getDkOrderNo() {
		return dkOrderNo;
	}

	public void setDkOrderNo(String dkOrderNo) {
		this.dkOrderNo = dkOrderNo;
	}

	public String getRepayOrderNo() {
		return repayOrderNo;
	}

	public void setRepayOrderNo(String repayOrderNo) {
		this.repayOrderNo = repayOrderNo;
	}

	public String getReturnOrderNo() {
		return returnOrderNo;
	}

	public void setReturnOrderNo(String returnOrderNo) {
		this.returnOrderNo = returnOrderNo;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
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