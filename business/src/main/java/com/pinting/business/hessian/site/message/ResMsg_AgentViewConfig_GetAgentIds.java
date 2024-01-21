package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ResMsg;

import java.util.List;

/**
 * Author:      cyb
 * Date:        2016/10/10
 * Description:
 */
public class ResMsg_AgentViewConfig_GetAgentIds extends ResMsg {

    private static final long serialVersionUID = 3803358014689695385L;

    private List<Integer> agentIds;

    public List<Integer> getAgentIds() {
        return agentIds;
    }

    public void setAgentIds(List<Integer> agentIds) {
        this.agentIds = agentIds;
    }
}
