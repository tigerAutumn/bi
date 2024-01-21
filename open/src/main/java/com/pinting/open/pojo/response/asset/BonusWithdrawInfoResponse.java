package com.pinting.open.pojo.response.asset;

import com.pinting.open.base.response.Response;

/**
 * Created by cyb on 2018/2/27.
 */
public class BonusWithdrawInfoResponse extends Response {

    private Integer cardId;

    private String cardNo;

    private String bankName;

    private Integer withdrawTimes;  // 用户当月可提现次数

    private String  smallLogo;   //小图标

    private String  largeLogo;   //大图标

    private String canWithdraw; // 最多可提现金额

    private String limitWithdraw; // 最少起提金额

    private String totalAmount; // 奖励金总金额

    /* 单笔提现上限 */
    private String singleWithdrawUpperLimit;

    /* 单日提现上限 */
    private String dayWithdrawUpperLimit;

    /* 用户已提现总金额*/
    private String userWithdrawAmount;

    public Integer getCardId() {
        return cardId;
    }

    public void setCardId(Integer cardId) {
        this.cardId = cardId;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public Integer getWithdrawTimes() {
        return withdrawTimes;
    }

    public void setWithdrawTimes(Integer withdrawTimes) {
        this.withdrawTimes = withdrawTimes;
    }

    public String getSmallLogo() {
        return smallLogo;
    }

    public void setSmallLogo(String smallLogo) {
        this.smallLogo = smallLogo;
    }

    public String getLargeLogo() {
        return largeLogo;
    }

    public void setLargeLogo(String largeLogo) {
        this.largeLogo = largeLogo;
    }

    public String getCanWithdraw() {
        return canWithdraw;
    }

    public void setCanWithdraw(String canWithdraw) {
        this.canWithdraw = canWithdraw;
    }

    public String getLimitWithdraw() {
        return limitWithdraw;
    }

    public void setLimitWithdraw(String limitWithdraw) {
        this.limitWithdraw = limitWithdraw;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getSingleWithdrawUpperLimit() {
        return singleWithdrawUpperLimit;
    }

    public void setSingleWithdrawUpperLimit(String singleWithdrawUpperLimit) {
        this.singleWithdrawUpperLimit = singleWithdrawUpperLimit;
    }

    public String getDayWithdrawUpperLimit() {
        return dayWithdrawUpperLimit;
    }

    public void setDayWithdrawUpperLimit(String dayWithdrawUpperLimit) {
        this.dayWithdrawUpperLimit = dayWithdrawUpperLimit;
    }

    public String getUserWithdrawAmount() {
        return userWithdrawAmount;
    }

    public void setUserWithdrawAmount(String userWithdrawAmount) {
        this.userWithdrawAmount = userWithdrawAmount;
    }
}
