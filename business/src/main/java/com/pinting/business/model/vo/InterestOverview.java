package com.pinting.business.model.vo;

import java.io.Serializable;

/**
 * 收益概况
 * Created by cyb on 2018/3/12.
 */
public class InterestOverview implements Serializable {
    private static final long serialVersionUID = 8824805996775625911L;
    // 已赚取收益
    private String totalInterestAmount;
    // 待赚取收益
    private String investInterestWill;

    public String getTotalInterestAmount() {
        return totalInterestAmount;
    }

    public void setTotalInterestAmount(String totalInterestAmount) {
        this.totalInterestAmount = totalInterestAmount;
    }

    public String getInvestInterestWill() {
        return investInterestWill;
    }

    public void setInvestInterestWill(String investInterestWill) {
        this.investInterestWill = investInterestWill;
    }
}
