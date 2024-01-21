package com.pinting.business.model;

import java.util.Date;

public class BsQuestionnaireQuota {
    private Integer id;

    private String evaluateType;

    private Double amountLimit;

    private Integer periodLimit;

    private Integer term;
    
    private Integer operateUserId;
    
    private Date createTime;

    private Date updateTime;

    private String note;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEvaluateType() {
        return evaluateType;
    }

    public void setEvaluateType(String evaluateType) {
        this.evaluateType = evaluateType == null ? null : evaluateType.trim();
    }

    public Double getAmountLimit() {
        return amountLimit;
    }

    public void setAmountLimit(Double amountLimit) {
        this.amountLimit = amountLimit;
    }

    public Integer getPeriodLimit() {
        return periodLimit;
    }

    public void setPeriodLimit(Integer periodLimit) {
        this.periodLimit = periodLimit;
    }

    public Integer getTerm() {
        return term;
    }

    public void setTerm(Integer term) {
        this.term = term;
    }

	public Integer getOperateUserId() {
		return operateUserId;
	}

	public void setOperateUserId(Integer operateUserId) {
		this.operateUserId = operateUserId;
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

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note == null ? null : note.trim();
    }
}