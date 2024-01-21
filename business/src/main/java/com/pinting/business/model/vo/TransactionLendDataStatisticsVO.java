package com.pinting.business.model.vo;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * 2. 成交及出借数据统计
 * Created by cyb on 2018/3/12.
 */
public class TransactionLendDataStatisticsVO implements Serializable {

    private static final long serialVersionUID = -7442417302636229993L;
    // 每月平台累计成交额 保存12条数据，按1月到12月的顺序存储对应累计成交额
    private List<String> monthPlatformBuyAmount;

    // 各个期限计划成交概况 按照期限正序存储对应成交额
    private List<HashMap<String, Object>> eachTermProductBuyOverview;

    // 收益概况
    private InterestOverview interestOverview;

    // 出借数据
    private LoanData loanData;

    // 时间
    private Date createTime;

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public List<String> getMonthPlatformBuyAmount() {
        return monthPlatformBuyAmount;
    }

    public void setMonthPlatformBuyAmount(List<String> monthPlatformBuyAmount) {
        this.monthPlatformBuyAmount = monthPlatformBuyAmount;
    }

    public List<HashMap<String, Object>> getEachTermProductBuyOverview() {
        return eachTermProductBuyOverview;
    }

    public void setEachTermProductBuyOverview(List<HashMap<String, Object>> eachTermProductBuyOverview) {
        this.eachTermProductBuyOverview = eachTermProductBuyOverview;
    }

    public InterestOverview getInterestOverview() {
        return interestOverview;
    }

    public void setInterestOverview(InterestOverview interestOverview) {
        this.interestOverview = interestOverview;
    }

    public LoanData getLoanData() {
        return loanData;
    }

    public void setLoanData(LoanData loanData) {
        this.loanData = loanData;
    }

}
