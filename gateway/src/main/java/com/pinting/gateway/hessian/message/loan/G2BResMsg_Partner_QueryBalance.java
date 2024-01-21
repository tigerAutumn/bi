package com.pinting.gateway.hessian.message.loan;

import com.pinting.core.hessian.msg.ResMsg;

public class G2BResMsg_Partner_QueryBalance extends ResMsg {

    private String balance;

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }
}
