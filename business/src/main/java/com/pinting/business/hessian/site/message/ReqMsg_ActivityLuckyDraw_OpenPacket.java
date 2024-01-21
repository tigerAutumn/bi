package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ReqMsg;

/**
 * Created by Administrator on 2016/10/31.
 */
public class ReqMsg_ActivityLuckyDraw_OpenPacket extends ReqMsg {
    private static final long serialVersionUID = -5507413774726511999L;

    private Integer userId;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
