/**
 * Wenzi.com Inc.
 * Copyright (c) 2015-2025 All Rights Reserved.
 */
package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ReqMsg;

/**
 * 
 * @author HuanXiong
 * @version $Id: ReqMsg_UserBalance_Withdraw.java, v 0.1 2015-12-23 上午10:24:32 HuanXiong Exp $
 */
public class ReqMsg_UserBalance_Withdraw extends ReqMsg {

    /**  */
    private static final long serialVersionUID = -8803953012722372380L;
    
    private int userId;//用户编号
    
    private double amount;//提现金额
    
    private String password;//用户支付密码
    
    private Integer terminalType; //终端类型@1手机端,2PC端

    private String accountType;//账户类型：DEP-存管账户；SIMPLE-普通账户

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getTerminalType() {
        return terminalType;
    }

    public void setTerminalType(Integer terminalType) {
        this.terminalType = terminalType;
    }

}
