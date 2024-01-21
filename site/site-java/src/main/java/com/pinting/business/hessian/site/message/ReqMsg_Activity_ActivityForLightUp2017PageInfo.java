package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ReqMsg;

/**
 * Author:      shh
 * Date:        2017/6/23
 * Description: 微信点亮存管图标瓜分百万红包首页信息 入参
 */
public class ReqMsg_Activity_ActivityForLightUp2017PageInfo extends ReqMsg {

    private static final long serialVersionUID = 3619500720669290475L;

    /*用户id*/
    private Integer userId;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
