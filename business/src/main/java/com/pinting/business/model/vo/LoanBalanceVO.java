package com.pinting.business.model.vo;

/**
 * 每日日终快照-借款人余额、可用余额、冻结余额vo
 *
 * @author shh
 * @date 2018/4/23 14:54
 * @Copyright: 2018 BiGangWan.com Inc. All rights reserved.
 */
public class LoanBalanceVO {

    /* 某天借款人余额合计 */
    private Double sumBalance;

    /* 某天借款人可用余额合计 */
    private Double sumAvailableBalance;

    /* 某天借款人冻结余额合计 */
    private Double sumFreezeBalance;

    public Double getSumBalance() {
        return sumBalance;
    }

    public void setSumBalance(Double sumBalance) {
        this.sumBalance = sumBalance;
    }

    public Double getSumAvailableBalance() {
        return sumAvailableBalance;
    }

    public void setSumAvailableBalance(Double sumAvailableBalance) {
        this.sumAvailableBalance = sumAvailableBalance;
    }

    public Double getSumFreezeBalance() {
        return sumFreezeBalance;
    }

    public void setSumFreezeBalance(Double sumFreezeBalance) {
        this.sumFreezeBalance = sumFreezeBalance;
    }
}
