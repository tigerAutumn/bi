package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ResMsg;

/**
 * Author:      cyb
 * Date:        2017/4/14
 * Description: 奖励金提现页面信息响应参数
 */
public class ResMsg_Bonus_WithdrawPageInfo extends ResMsg {

    private static final long serialVersionUID = -460724178865233479L;

    /*银行id*/
    private Integer bankId;
    /*卡号*/
    private String cardNo;
    /*银行名称*/
    private String bankName;
    /*小图标*/
    private String smallLogo;
    /*大图标*/
    private String largeLogo;
    /*银行卡ID*/
    private Integer cardId;
    /*最小提现金额*/
    private String withdrawLimit;
    /* 单笔提现上限 */
    private Double singleWithdrawUpperLimit;
    /* 单日提现上限 */
    private Double dayWithdrawUpperLimit;
    /* 可提现余额 */
    private Double can_withdraw;

    public Integer getBankId() {
        return bankId;
    }

    public void setBankId(Integer bankId) {
        this.bankId = bankId;
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

    public Integer getCardId() {
        return cardId;
    }

    public void setCardId(Integer cardId) {
        this.cardId = cardId;
    }

    public String getWithdrawLimit() {
        return withdrawLimit;
    }

    public void setWithdrawLimit(String withdrawLimit) {
        this.withdrawLimit = withdrawLimit;
    }

    public Double getSingleWithdrawUpperLimit() {
        return singleWithdrawUpperLimit;
    }

    public void setSingleWithdrawUpperLimit(Double singleWithdrawUpperLimit) {
        this.singleWithdrawUpperLimit = singleWithdrawUpperLimit;
    }

    public Double getDayWithdrawUpperLimit() {
        return dayWithdrawUpperLimit;
    }

    public void setDayWithdrawUpperLimit(Double dayWithdrawUpperLimit) {
        this.dayWithdrawUpperLimit = dayWithdrawUpperLimit;
    }

    public Double getCan_withdraw() {
        return can_withdraw;
    }

    public void setCan_withdraw(Double can_withdraw) {
        this.can_withdraw = can_withdraw;
    }
}
