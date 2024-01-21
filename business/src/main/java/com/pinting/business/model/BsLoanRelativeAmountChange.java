package com.pinting.business.model;

import java.util.Date;

public class BsLoanRelativeAmountChange {
    private Integer id;

    private String propertySymbol;

    private Integer loanRelativeId;

    private Double beforeAmount;

    private Double afterAmount;

    private Double repayAmount;

    private String dealStatus;

    private String isPullDetail;

    private Date createTime;

    private Date updateTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPropertySymbol() {
        return propertySymbol;
    }

    public void setPropertySymbol(String propertySymbol) {
        this.propertySymbol = propertySymbol;
    }

    public Integer getLoanRelativeId() {
        return loanRelativeId;
    }

    public void setLoanRelativeId(Integer loanRelativeId) {
        this.loanRelativeId = loanRelativeId;
    }

    public Double getBeforeAmount() {
        return beforeAmount;
    }

    public void setBeforeAmount(Double beforeAmount) {
        this.beforeAmount = beforeAmount;
    }

    public Double getAfterAmount() {
        return afterAmount;
    }

    public void setAfterAmount(Double afterAmount) {
        this.afterAmount = afterAmount;
    }

    public Double getRepayAmount() {
        return repayAmount;
    }

    public void setRepayAmount(Double repayAmount) {
        this.repayAmount = repayAmount;
    }

    public String getDealStatus() {
        return dealStatus;
    }

    public void setDealStatus(String dealStatus) {
        this.dealStatus = dealStatus;
    }

    public String getIsPullDetail() {
        return isPullDetail;
    }

    public void setIsPullDetail(String isPullDetail) {
        this.isPullDetail = isPullDetail;
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