package com.pinting.business.model;

import java.util.Date;

public class LnCreditTransfer {
    private Integer id;

    private Integer outLoanRelationId;

    private Integer inLoanRelationId;

    private Integer outUserId;

    private Integer inUserId;

    private Integer outSubAccountId;

    private Integer inSubAccountId;

    private Double amount;

    private Double inAmount;

    private Date createTime;

    private Date updateTime;

    /**
     * 折让金额
     */
    private Double discountAmount;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getOutLoanRelationId() {
        return outLoanRelationId;
    }

    public void setOutLoanRelationId(Integer outLoanRelationId) {
        this.outLoanRelationId = outLoanRelationId;
    }

    public Integer getInLoanRelationId() {
        return inLoanRelationId;
    }

    public void setInLoanRelationId(Integer inLoanRelationId) {
        this.inLoanRelationId = inLoanRelationId;
    }

    public Integer getOutUserId() {
        return outUserId;
    }

    public void setOutUserId(Integer outUserId) {
        this.outUserId = outUserId;
    }

    public Integer getInUserId() {
        return inUserId;
    }

    public void setInUserId(Integer inUserId) {
        this.inUserId = inUserId;
    }

    public Integer getOutSubAccountId() {
        return outSubAccountId;
    }

    public void setOutSubAccountId(Integer outSubAccountId) {
        this.outSubAccountId = outSubAccountId;
    }

    public Integer getInSubAccountId() {
        return inSubAccountId;
    }

    public void setInSubAccountId(Integer inSubAccountId) {
        this.inSubAccountId = inSubAccountId;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Double getInAmount() {
        return inAmount;
    }

    public void setInAmount(Double inAmount) {
        this.inAmount = inAmount;
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

    public Double getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(Double discountAmount) {
        this.discountAmount = discountAmount;
    }
}