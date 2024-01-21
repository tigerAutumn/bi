package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ReqMsg;

/**
 * Created by cyb on 2017/10/30.
 */
public class ReqMsg_News_IsRead extends ReqMsg {

    private static final long serialVersionUID = 918689362609220671L;

    private Integer userId;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
