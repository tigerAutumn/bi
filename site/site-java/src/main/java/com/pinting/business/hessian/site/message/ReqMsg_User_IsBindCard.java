package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ReqMsg;

/**
 * Author:      cyb
 * Date:        2016/9/20
 * Description: 是否绑卡请求信息
 */
public class ReqMsg_User_IsBindCard extends ReqMsg {

    private static final long serialVersionUID = -4273140934343683742L;

    private String userId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
