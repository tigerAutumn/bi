package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ReqMsg;

/**
 * Author:      cyb
 * Date:        2017/4/13
 * Description:
 */
public class ReqMsg_DepGuide_WaiteActivateInfo extends ReqMsg {

    private static final long serialVersionUID = -3885422883761528154L;

    private Integer userId;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
