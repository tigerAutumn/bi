package com.pinting.business.model.vo;

import java.text.SimpleDateFormat;

public class RepayPlan {
    private int  period; // 期数
    private long days;   // 本期计息天数
    private long date;   // 还款日
    private long interest;
    private long principal;

    public RepayPlan(int period, long date, long days) {
        this.period = period;
        this.date = date;
        this.days = days;
    }

    public int getPeriod() {
        return period;
    }

    public void setPeriod(int period) {
        this.period = period;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public long getDays() {
        return days;
    }

    public void setDays(long days) {
        this.days = days;
    }

    public long getInterest() {
        return interest;
    }

    public void setInterest(long interest) {
        this.interest = interest;
    }

    public long getPrincipal() {
        return principal;
    }

    public void setPrincipal(long principal) {
        this.principal = principal;
    }

    @Override
    public String toString() {
        return "RepayPlan[period=" + period + ",days=" + days + ",interest=" + interest
                + ",principal=" + principal + ",date="
                + new SimpleDateFormat("yyyy-MM-dd").format(this.date) + "]";
    }

}
