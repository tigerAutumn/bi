package com.pinting.gateway.hessian.message.baofoo;

import com.pinting.core.hessian.msg.ResMsg;

/**
 * Created by 剑钊 on 2016/8/3.
 */
public class B2GResMsg_BaoFooQuickPay_BalanceQuery extends ResMsg {

    /**
     * 余额
     * 单位为元
     */
    private String balance;

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }
}
