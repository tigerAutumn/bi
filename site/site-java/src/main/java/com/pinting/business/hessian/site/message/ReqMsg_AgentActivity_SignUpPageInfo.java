package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ReqMsg;

/**
 * Author:      cyb
 * Date:        2017/3/23
 * Description: 报名页信息
 */
public class ReqMsg_AgentActivity_SignUpPageInfo extends ReqMsg {

    private static final long serialVersionUID = 5705670337402462782L;

    private Integer userId;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
