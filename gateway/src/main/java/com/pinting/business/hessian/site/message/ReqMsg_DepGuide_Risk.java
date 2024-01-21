package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ReqMsg;

/**
 * Created by cyb on 2018/2/23.
 */
public class ReqMsg_DepGuide_Risk extends ReqMsg {

    private static final long serialVersionUID = -7188414409959240973L;

    private Integer userId;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
