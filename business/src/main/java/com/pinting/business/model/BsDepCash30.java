package com.pinting.business.model;

import java.util.Date;

public class BsDepCash30 {
    private Integer id;

    private String partnerCode;

    private Date cashDate;

    private Double quitPrincipal;

    private Double quitInterest;

    private Double repayBalance;

    private Double vipAmount;

    private Double vipInterest;

    private Double prepareBalance;

    private Date createTime;

    private Date updateTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPartnerCode() {
        return partnerCode;
    }

    public void setPartnerCode(String partnerCode) {
        this.partnerCode = partnerCode;
    }

    public Date getCashDate() {
        return cashDate;
    }

    public void setCashDate(Date cashDate) {
        this.cashDate = cashDate;
    }

    public Double getQuitPrincipal() {
        return quitPrincipal;
    }

    public void setQuitPrincipal(Double quitPrincipal) {
        this.quitPrincipal = quitPrincipal;
    }

    public Double getQuitInterest() {
        return quitInterest;
    }

    public void setQuitInterest(Double quitInterest) {
        this.quitInterest = quitInterest;
    }

    public Double getRepayBalance() {
        return repayBalance;
    }

    public void setRepayBalance(Double repayBalance) {
        this.repayBalance = repayBalance;
    }

    public Double getVipAmount() {
        return vipAmount;
    }

    public void setVipAmount(Double vipAmount) {
        this.vipAmount = vipAmount;
    }

    public Double getVipInterest() {
        return vipInterest;
    }

    public void setVipInterest(Double vipInterest) {
        this.vipInterest = vipInterest;
    }

    public Double getPrepareBalance() {
        return prepareBalance;
    }

    public void setPrepareBalance(Double prepareBalance) {
        this.prepareBalance = prepareBalance;
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