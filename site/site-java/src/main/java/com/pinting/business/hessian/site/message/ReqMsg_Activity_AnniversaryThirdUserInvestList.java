package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ReqMsg;

/**
 * Author:      cyb
 * Date:        2017/2/28
 * Description:
 */
public class ReqMsg_Activity_AnniversaryThirdUserInvestList extends ReqMsg {

    private static final long serialVersionUID = -965512004016304225L;

    private Integer userId;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
