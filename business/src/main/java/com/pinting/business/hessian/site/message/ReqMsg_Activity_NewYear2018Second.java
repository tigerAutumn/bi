package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ReqMsg;

/**
 * Created by cyb on 2018/1/29.
 */
public class ReqMsg_Activity_NewYear2018Second extends ReqMsg {
    private static final long serialVersionUID = 2377168432960663712L;

    private Integer userId;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
