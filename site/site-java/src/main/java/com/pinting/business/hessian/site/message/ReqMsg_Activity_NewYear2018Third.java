package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ReqMsg;

/**
 * Created by cyb on 2018/1/29.
 */
public class ReqMsg_Activity_NewYear2018Third extends ReqMsg {
    private static final long serialVersionUID = 6446102913307854070L;

    private Integer userId;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
