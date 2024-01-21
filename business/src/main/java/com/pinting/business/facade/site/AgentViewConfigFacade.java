package com.pinting.business.facade.site;

import com.pinting.business.hessian.site.message.ReqMsg_AgentViewConfig_GetAgentIds;
import com.pinting.business.hessian.site.message.ResMsg_AgentViewConfig_GetAgentIds;
import com.pinting.business.service.site.BsAgentViewConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Author:      cyb
 * Date:        2016/10/10
 * Description:
 */
@Component("AgentViewConfig")
public class AgentViewConfigFacade {

    @Autowired
    private BsAgentViewConfigService bsAgentViewConfigService;

    public void getAgentIds(ReqMsg_AgentViewConfig_GetAgentIds req, ResMsg_AgentViewConfig_GetAgentIds res) {
        res.setAgentIds(bsAgentViewConfigService.getAgentIds());
    }
}
