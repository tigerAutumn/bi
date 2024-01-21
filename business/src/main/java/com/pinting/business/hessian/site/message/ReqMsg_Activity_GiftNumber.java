package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ReqMsg;

/**
 * Created by cyb on 2017/11/6.
 */
public class ReqMsg_Activity_GiftNumber extends ReqMsg {

    private static final long serialVersionUID = -8152115311705632640L;

    private Integer userId;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
