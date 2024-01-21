package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ReqMsg;

/**
 * Created by cyb on 2017/10/17.
 */
public class ReqMsg_Activity_YouFuSelfInfo extends ReqMsg {

    private static final long serialVersionUID = -8614804040801046948L;

    private Integer userId;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
