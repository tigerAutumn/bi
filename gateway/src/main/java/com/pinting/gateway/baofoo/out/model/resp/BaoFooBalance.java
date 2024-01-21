package com.pinting.gateway.baofoo.out.model.resp;

/**
 * Created by 剑钊
 *
 * @2016/10/20 11:09.
 */
public class BaoFooBalance {

    /**
     * 账户类型
     * 0:全部；
     * 1:基本账户;
     * 2:未结算账户;
     * 3:冻结账户;
     * 4:保证金账户;
     * 5:资金托管账户
     */
    private String account_type;

    /**
     * 余额
     */
    private String balance;

    public String getAccount_type() {
        return account_type;
    }

    public void setAccount_type(String account_type) {
        this.account_type = account_type;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }
}
