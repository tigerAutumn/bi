package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ReqMsg;

/**
 * Author:      shh
 * Date:        2017/6/26
 * Description: 微信点亮存管图标瓜分百万红包点亮操作 入参
 */
public class ReqMsg_Activity_ActivityLightUpOperate extends ReqMsg {

    private static final long serialVersionUID = -25473295086354684L;

    /*用户id*/
    private Integer userId;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
