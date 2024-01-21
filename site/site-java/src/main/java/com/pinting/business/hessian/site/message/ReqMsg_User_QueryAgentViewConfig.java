package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ReqMsg;

/**
 * Author:      cyb
 * Date:        2016/10/9
 * Description:
 */
public class ReqMsg_User_QueryAgentViewConfig extends ReqMsg {

    private static final long serialVersionUID = -7928490933335143715L;

    private Integer agentId;

    public Integer getAgentId() {
        return agentId;
    }

    public void setAgentId(Integer agentId) {
        this.agentId = agentId;
    }
}
