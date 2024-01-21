package com.pinting.business.model.vo;

import java.io.Serializable;
import java.util.Date;

/**
 * 1. 平台运营概况
 * Created by cyb on 2018/3/12.
 */
public class PlatformOverviewVO implements Serializable {

    private static final long serialVersionUID = -5127034581956801751L;
    // 合规运营天数
    private int operatingDays;

    // 累计成交额
    private String totalBuyAmount;

    // 累计出借额
    private String totalLoanAmount;

    // 累计收益金额
    private String totalIncomeAmount;

    // 时间
    private Date createTime;

    public int getOperatingDays() {
        return operatingDays;
    }

    public void setOperatingDays(int operatingDays) {
        this.operatingDays = operatingDays;
    }

    public String getTotalBuyAmount() {
        return totalBuyAmount;
    }

    public void setTotalBuyAmount(String totalBuyAmount) {
        this.totalBuyAmount = totalBuyAmount;
    }

    public String getTotalLoanAmount() {
        return totalLoanAmount;
    }

    public void setTotalLoanAmount(String totalLoanAmount) {
        this.totalLoanAmount = totalLoanAmount;
    }

    public String getTotalIncomeAmount() {
        return totalIncomeAmount;
    }

    public void setTotalIncomeAmount(String totalIncomeAmount) {
        this.totalIncomeAmount = totalIncomeAmount;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
