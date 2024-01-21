package com.pinting.business.model.vo;

import java.util.Date;

/**
 * Author:      cyb
 * Date:        2017/2/23
 * Description: 获奖名单||获奖金额
 */
public class AnniversaryAwardInfoVO {

    /* 日期 */
    private Date time;

    /* 排名 */
    private Integer rank;

    /* 用户名 */
    private String userName;

    /* 用户年化投资金额 */
    private Double amount;

    /* 奖金 */
    private Double award;

    private String timeStr;

    /* 当日总的年化投资额 */
    private Double anniAmountThatDay;

    /* 当日总奖金 */
    private Double todayAwardAmount;

    /* 用户ID */
    private Integer userId;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Double getAward() {
        return award;
    }

    public void setAward(Double award) {
        this.award = award;
    }

    public String getTimeStr() {
        return timeStr;
    }

    public void setTimeStr(String timeStr) {
        this.timeStr = timeStr;
    }

    public Double getAnniAmountThatDay() {
        return anniAmountThatDay;
    }

    public void setAnniAmountThatDay(Double anniAmountThatDay) {
        this.anniAmountThatDay = anniAmountThatDay;
    }

    public Double getTodayAwardAmount() {
        return todayAwardAmount;
    }

    public void setTodayAwardAmount(Double todayAwardAmount) {
        this.todayAwardAmount = todayAwardAmount;
    }
}
