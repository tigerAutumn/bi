package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ReqMsg;

/**
 * Author:      cyb
 * Date:        2017/2/13
 * Description:
 */
public class ReqMsg_Activity_CheckForGirl2017Receive extends ReqMsg {

    private static final long serialVersionUID = 2566906532081130076L;

    private Integer userId;

    private Integer agentId;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getAgentId() {
        return agentId;
    }

    public void setAgentId(Integer agentId) {
        this.agentId = agentId;
    }
}
