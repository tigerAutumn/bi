package com.pinting.mall.hessian.site.message;

import com.pinting.core.hessian.msg.ReqMsg;

import javax.validation.constraints.NotNull;

public class ReqMsg_MallAddr_GetDetail extends ReqMsg {

    private static final long serialVersionUID = 1L;

    @NotNull(message="用户编码ID不能为空")
    private Integer userId; // 用户编码ID

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
