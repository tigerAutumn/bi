package com.pinting.business.model.vo;

import java.util.Date;

/**
 * Created by cyb on 2017/11/5.
 */
public class ActivityLuckyNumber {

    /* 日期 */
    private Date investDate;

    /* 幸运号 */
    private String luckyNumber;

    /* 我的投资号 */
    private String investNumber;

    /* 幸运用户手机号 */
    private String luckyUser;

    public Date getInvestDate() {
        return investDate;
    }

    public void setInvestDate(Date investDate) {
        this.investDate = investDate;
    }

    public String getLuckyNumber() {
        return luckyNumber;
    }

    public void setLuckyNumber(String luckyNumber) {
        this.luckyNumber = luckyNumber;
    }

    public String getInvestNumber() {
        return investNumber;
    }

    public void setInvestNumber(String investNumber) {
        this.investNumber = investNumber;
    }

    public String getLuckyUser() {
        return luckyUser;
    }

    public void setLuckyUser(String luckyUser) {
        this.luckyUser = luckyUser;
    }
}
