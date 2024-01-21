package com.pinting.business.model.vo;

/**
 * 系统余额快照记录表VO
 *
 * @author shh
 * @date 2018/6/14 14:53
 * @Copyright: 2018 BiGangWan.com Inc. All rights reserved.
 */
public class SysBalanceDailySnapVO {

    /* 总金额 */
    private Double balance;

    /* 可用余额 */
    private Double freezeBalance;

    /* 冻结余额 */
    private Double availableBalance;

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public Double getFreezeBalance() {
        return freezeBalance;
    }

    public void setFreezeBalance(Double freezeBalance) {
        this.freezeBalance = freezeBalance;
    }

    public Double getAvailableBalance() {
        return availableBalance;
    }

    public void setAvailableBalance(Double availableBalance) {
        this.availableBalance = availableBalance;
    }
}
