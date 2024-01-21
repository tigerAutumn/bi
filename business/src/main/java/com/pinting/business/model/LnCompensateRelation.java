package com.pinting.business.model;

import java.util.Date;

public class LnCompensateRelation {
    private Integer id;

    private Integer depPlanId;

    private Integer loanRelationId;

    private Integer compUserId;

    private String partnerCode;

    private String compHfUserId;

    private Double amount;

    private Double principal;

    private Double interest;

    private Integer interestDay;

    private Date createTime;

    private Date updateTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getDepPlanId() {
        return depPlanId;
    }

    public void setDepPlanId(Integer depPlanId) {
        this.depPlanId = depPlanId;
    }

    public Integer getLoanRelationId() {
        return loanRelationId;
    }

    public void setLoanRelationId(Integer loanRelationId) {
        this.loanRelationId = loanRelationId;
    }

    public Integer getCompUserId() {
        return compUserId;
    }

    public void setCompUserId(Integer compUserId) {
        this.compUserId = compUserId;
    }

    public String getPartnerCode() {
        return partnerCode;
    }

    public void setPartnerCode(String partnerCode) {
        this.partnerCode = partnerCode;
    }

    public String getCompHfUserId() {
        return compHfUserId;
    }

    public void setCompHfUserId(String compHfUserId) {
        this.compHfUserId = compHfUserId;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
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

    public Integer getInterestDay() {
        return interestDay;
    }

    public void setInterestDay(Integer interestDay) {
        this.interestDay = interestDay;
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