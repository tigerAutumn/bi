package com.pinting.business.model;

import java.util.Date;

import com.pinting.business.model.vo.PageInfoObject;

public class BsProfitLoss extends PageInfoObject {
    private Integer id;

    private Date clearDate;

    private Double principal;

    private Double interest;

    private Double sysInterest;

    private Double pintingProfit;

    private Double shouldBonus;

    private Double surplusBonus;

    private Double bonus;

    private Double dafy2Percent;

    private Double dafyShouldProfit;

    private Double dafySendProfit;

    private Date createTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getClearDate() {
        return clearDate;
    }

    public void setClearDate(Date clearDate) {
        this.clearDate = clearDate;
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

    public Double getSysInterest() {
        return sysInterest;
    }

    public void setSysInterest(Double sysInterest) {
        this.sysInterest = sysInterest;
    }

    public Double getPintingProfit() {
        return pintingProfit;
    }

    public void setPintingProfit(Double pintingProfit) {
        this.pintingProfit = pintingProfit;
    }

    public Double getShouldBonus() {
        return shouldBonus;
    }

    public void setShouldBonus(Double shouldBonus) {
        this.shouldBonus = shouldBonus;
    }

    public Double getSurplusBonus() {
        return surplusBonus;
    }

    public void setSurplusBonus(Double surplusBonus) {
        this.surplusBonus = surplusBonus;
    }

    public Double getBonus() {
        return bonus;
    }

    public void setBonus(Double bonus) {
        this.bonus = bonus;
    }

    public Double getDafy2Percent() {
        return dafy2Percent;
    }

    public void setDafy2Percent(Double dafy2Percent) {
        this.dafy2Percent = dafy2Percent;
    }

    public Double getDafyShouldProfit() {
        return dafyShouldProfit;
    }

    public void setDafyShouldProfit(Double dafyShouldProfit) {
        this.dafyShouldProfit = dafyShouldProfit;
    }

    public Double getDafySendProfit() {
        return dafySendProfit;
    }

    public void setDafySendProfit(Double dafySendProfit) {
        this.dafySendProfit = dafySendProfit;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}