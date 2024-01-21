package com.pinting.gateway.hessian.message.hfbank.model;

import java.io.Serializable;

/**
 * Author:      cyb
 * Date:        2017/4/1
 * Description: 资金余额查询响应信息业务数据
 */
public class GetAccountInfoData implements Serializable {

    private static final long serialVersionUID = 1459909922311493792L;
    /* 余额信息 */
    private String balance;

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }
}
