package com.pinting.business.model;

import java.util.Date;

import com.pinting.core.hessian.msg.ResMsg;

public class LnFinanceRepaySchedule extends ResMsg {
    /**
	 * 
	 */
	private static final long serialVersionUID = -1523845556774814312L;

	private Integer id;

	private Integer relationId;

	private Integer repaySerial;

	private Date planDate;

	private Double planTotal;

	private Double planPrincipal;

	private Double planInterest;

	private Double planTransInterest;

	private Double planFee;

	private Double diffAmount;

	private Double leftPlanInterest;

	private Date doneTime;

	private String status;

	private Date createTime;

	private Date updateTime;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getRelationId() {
		return relationId;
	}

	public void setRelationId(Integer relationId) {
		this.relationId = relationId;
	}

	public Integer getRepaySerial() {
		return repaySerial;
	}

	public void setRepaySerial(Integer repaySerial) {
		this.repaySerial = repaySerial;
	}

	public Date getPlanDate() {
		return planDate;
	}

	public void setPlanDate(Date planDate) {
		this.planDate = planDate;
	}

	public Double getPlanTotal() {
		return planTotal;
	}

	public void setPlanTotal(Double planTotal) {
		this.planTotal = planTotal;
	}

	public Double getPlanPrincipal() {
		return planPrincipal;
	}

	public void setPlanPrincipal(Double planPrincipal) {
		this.planPrincipal = planPrincipal;
	}

	public Double getPlanInterest() {
		return planInterest;
	}

	public void setPlanInterest(Double planInterest) {
		this.planInterest = planInterest;
	}

	public Double getPlanTransInterest() {
		return planTransInterest;
	}

	public void setPlanTransInterest(Double planTransInterest) {
		this.planTransInterest = planTransInterest;
	}

	public Double getPlanFee() {
		return planFee;
	}

	public void setPlanFee(Double planFee) {
		this.planFee = planFee;
	}

	public Double getDiffAmount() {
		return diffAmount;
	}

	public void setDiffAmount(Double diffAmount) {
		this.diffAmount = diffAmount;
	}

	public Double getLeftPlanInterest() {
		return leftPlanInterest;
	}

	public void setLeftPlanInterest(Double leftPlanInterest) {
		this.leftPlanInterest = leftPlanInterest;
	}

	public Date getDoneTime() {
		return doneTime;
	}

	public void setDoneTime(Date doneTime) {
		this.doneTime = doneTime;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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