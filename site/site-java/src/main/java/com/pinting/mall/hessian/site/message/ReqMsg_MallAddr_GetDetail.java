package com.pinting.mall.hessian.site.message;

import com.pinting.core.hessian.msg.ReqMsg;

public class ReqMsg_MallAddr_GetDetail extends ReqMsg {

    private static final long serialVersionUID = 1L;

    private Integer userId; // 用户编号ID

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
