package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ReqMsg;

/**
 * Author:      cyb
 * Date:        2017/6/30
 * Description: 是否已读
 */
public class ReqMsg_AppActive_IsRead extends ReqMsg {

    private static final long serialVersionUID = 9162555364810427342L;

    private Integer userId;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
