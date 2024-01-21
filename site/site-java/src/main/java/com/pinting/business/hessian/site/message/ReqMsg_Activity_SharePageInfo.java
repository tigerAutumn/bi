package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ReqMsg;

/**
 * Created by cyb on 2017/11/7.
 */
public class ReqMsg_Activity_SharePageInfo extends ReqMsg {

    private static final long serialVersionUID = -2606877491158293189L;

    private Integer userId;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
