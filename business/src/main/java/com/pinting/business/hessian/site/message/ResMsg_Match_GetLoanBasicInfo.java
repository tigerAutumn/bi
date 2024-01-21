package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ResMsg;

import java.util.Date;

/**
 * Created by shh on 2016/10/13 20:35.
 */
public class ResMsg_Match_GetLoanBasicInfo extends ResMsg {

    private static final long serialVersionUID = -772663071774634881L;

    /* 借款年化利率 */
    private Double loanInterestRate;

    /* 总期数 */
    private Integer period;

    /* 出借受让日期 */
    private Date loanTime;

    /* regd户子账户id */
    private Integer regdId;

    /* 月偿还本息数额 */
    private Double needRepayMoney4Month;

    /* 第一期还款日 */
    private Date lnRepayStartDate;

    /* 最后一期还款日 */
    private Date lnRepayEndDate;

    /* 每月还款日 */
    private Integer day4Month;

    public Double getLoanInterestRate() {
        return loanInterestRate;
    }

    public void setLoanInterestRate(Double loanInterestRate) {
        this.loanInterestRate = loanInterestRate;
    }

    public Integer getPeriod() {
        return period;
    }

    public void setPeriod(Integer period) {
        this.period = period;
    }

    public Date getLoanTime() {
        return loanTime;
    }

    public void setLoanTime(Date loanTime) {
        this.loanTime = loanTime;
    }

    public Integer getRegdId() {
        return regdId;
    }

    public void setRegdId(Integer regdId) {
        this.regdId = regdId;
    }

    public Double getNeedRepayMoney4Month() {
        return needRepayMoney4Month;
    }

    public void setNeedRepayMoney4Month(Double needRepayMoney4Month) {
        this.needRepayMoney4Month = needRepayMoney4Month;
    }

    public Date getLnRepayStartDate() {
        return lnRepayStartDate;
    }

    public void setLnRepayStartDate(Date lnRepayStartDate) {
        this.lnRepayStartDate = lnRepayStartDate;
    }

    public Date getLnRepayEndDate() {
        return lnRepayEndDate;
    }

    public void setLnRepayEndDate(Date lnRepayEndDate) {
        this.lnRepayEndDate = lnRepayEndDate;
    }

    public Integer getDay4Month() {
        return day4Month;
    }

    public void setDay4Month(Integer day4Month) {
        this.day4Month = day4Month;
    }
}
