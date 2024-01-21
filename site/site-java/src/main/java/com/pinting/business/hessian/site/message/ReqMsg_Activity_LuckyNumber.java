package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ReqMsg;

/**
 * Created by cyb on 2017/11/6.
 */
public class ReqMsg_Activity_LuckyNumber extends ReqMsg {

    private static final long serialVersionUID = -5346809908851985310L;

    private Integer userId;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
