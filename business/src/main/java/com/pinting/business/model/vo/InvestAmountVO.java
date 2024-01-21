package com.pinting.business.model.vo;

import java.util.Date;

/**
 * Created by cyb on 2017/12/6.
 */
public class InvestAmountVO {

    private Double investAmount;

    private Date date;

    public Double getInvestAmount() {
        return investAmount;
    }

    public void setInvestAmount(Double investAmount) {
        this.investAmount = investAmount;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
