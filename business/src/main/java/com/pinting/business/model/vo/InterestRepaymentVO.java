package com.pinting.business.model.vo;

import com.pinting.business.model.BsUser;

import java.util.Date;

/**
 * 用户利息回款查询 VO
 * Created by shh on 2016/10/11 20:41.
 */
public class InterestRepaymentVO extends BsUser {

    private static final long serialVersionUID = -4556811641418071960L;

    /* 购买日期 */
    private Date openTime;

    /* 产品名称 */
    private  String productName;

    /* 购买金额 */
    private Double balance;

    /* 利率 */
    private Double productRate;

    /* 期限 */
    private Integer term;

    /* 到期提现日期 */
    private Date lastfinishInterestDate;

    /* 账户状态 */
    private Integer accountStatus;

    /* 资产合作方 */
    private String propertySymbol;

    /* 利息 */
    private Double interest;

    /* 购买开始时间 */
    private Date openTimeStart;

    /* 购买结束时间 */
    private Date openTimeEnd;

    public Date getOpenTime() {
        return openTime;
    }

    public void setOpenTime(Date openTime) {
        this.openTime = openTime;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public Double getProductRate() {
        return productRate;
    }

    public void setProductRate(Double productRate) {
        this.productRate = productRate;
    }

    public Integer getTerm() {
        return term;
    }

    public void setTerm(Integer term) {
        this.term = term;
    }

    public Date getLastfinishInterestDate() {
        return lastfinishInterestDate;
    }

    public void setLastfinishInterestDate(Date lastfinishInterestDate) {
        this.lastfinishInterestDate = lastfinishInterestDate;
    }

    public Integer getAccountStatus() {
        return accountStatus;
    }

    public void setAccountStatus(Integer accountStatus) {
        this.accountStatus = accountStatus;
    }

    public String getPropertySymbol() {
        return propertySymbol;
    }

    public void setPropertySymbol(String propertySymbol) {
        this.propertySymbol = propertySymbol;
    }

    public Double getInterest() {
        return interest;
    }

    public void setInterest(Double interest) {
        this.interest = interest;
    }

    public Date getOpenTimeStart() {
        return openTimeStart;
    }

    public void setOpenTimeStart(Date openTimeStart) {
        this.openTimeStart = openTimeStart;
    }

    public Date getOpenTimeEnd() {
        return openTimeEnd;
    }

    public void setOpenTimeEnd(Date openTimeEnd) {
        this.openTimeEnd = openTimeEnd;
    }
}
