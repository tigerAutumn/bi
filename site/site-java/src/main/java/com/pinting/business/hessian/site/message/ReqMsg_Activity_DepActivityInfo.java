package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ReqMsg;


/**
 * Author:      cyb
 * Date:        2017/6/26
 * Description:
 */
public class ReqMsg_Activity_DepActivityInfo extends ReqMsg {

    private static final long serialVersionUID = -912496136046451280L;

    private Integer userId;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
