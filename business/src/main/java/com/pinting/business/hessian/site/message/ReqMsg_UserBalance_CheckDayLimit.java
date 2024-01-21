package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ReqMsg;

/**
 * Author:      cyb
 * Date:        2016/11/07
 * Description:
 */

public class ReqMsg_UserBalance_CheckDayLimit extends ReqMsg {

    private static final long serialVersionUID = 2000780133841335376L;

    private Integer userId;

    private Double amount;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }
}
