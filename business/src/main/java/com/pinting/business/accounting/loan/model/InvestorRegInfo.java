package com.pinting.business.accounting.loan.model;

/**
 * Created by babyshark on 2016/9/2.
 */
public class InvestorRegInfo {

    private Integer investorRegActId;
    private Double regAmount;

    public Integer getInvestorRegActId() {
        return investorRegActId;
    }

    public void setInvestorRegActId(Integer investorRegActId) {
        this.investorRegActId = investorRegActId;
    }

    public Double getRegAmount() {
        return regAmount;
    }

    public void setRegAmount(Double regAmount) {
        this.regAmount = regAmount;
    }

    @Override
    public String toString() {
        return "InvestorRegInfo{" +
                "investorRegActId=" + investorRegActId +
                ", regAmount=" + regAmount +
                '}';
    }
}
