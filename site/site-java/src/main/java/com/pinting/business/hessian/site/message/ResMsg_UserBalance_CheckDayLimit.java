package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ResMsg;

/**
 * Author:      cyb
 * Date:        2016/11/7
 * Description:
 */
public class ResMsg_UserBalance_CheckDayLimit extends ResMsg {

    private static final long serialVersionUID = -212441775530248830L;

    private boolean moreThanDayLimit;

    private Double leftAmount;

    public Double getLeftAmount() {
        return leftAmount;
    }

    public void setLeftAmount(Double leftAmount) {
        this.leftAmount = leftAmount;
    }

    public boolean isMoreThanDayLimit() {
        return moreThanDayLimit;
    }

    public void setMoreThanDayLimit(boolean moreThanDayLimit) {
        this.moreThanDayLimit = moreThanDayLimit;
    }
}
