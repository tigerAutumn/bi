package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ReqMsg;

/**
 * Author:      cyb
 * Date:        2017/4/14
 * Description:
 */
public class ReqMsg_Bonus_Withdraw extends ReqMsg {

    private static final long serialVersionUID = 2540083613458220883L;

    private int userId;//用户编号

    private Double amount;//提现金额

    private String password;//用户支付密码

    private Integer terminalType; //终端类型@1手机端,2PC端

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
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

