/**
 * Wenzi.com Inc.
 * Copyright (c) 2015-2025 All Rights Reserved.
 */
package com.pinting.business.facade.site;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pinting.business.hessian.site.message.ReqMsg_AutoRedPacket_RedPacketSend;
import com.pinting.business.hessian.site.message.ResMsg_AutoRedPacket_RedPacketSend;
import com.pinting.business.model.AutoRedPacketParams;
import com.pinting.business.service.site.RedPacketService;

/**
 * 
 * @author yanwl
 * @version $Id: AutoRedPacketFacade.java, v 0.1 2016-3-3 上午9:40:22 yanwl Exp $
 */
@Component("AutoRedPacket")
public class AutoRedPacketFacade {
    
    @Autowired
    private RedPacketService redPacketService;
   
    /**
     * 自动发放红包准则解析，发放红包
     * @param req
     * @param res
     */
    public void redPacketSend(ReqMsg_AutoRedPacket_RedPacketSend req,ResMsg_AutoRedPacket_RedPacketSend res) {
    	AutoRedPacketParams params = new AutoRedPacketParams();
    	params.setUserId(req.getUserId());
    	params.setTriggerType(req.getTriggerType());
    	res.setRedPacketInfoIds(redPacketService.autoRedPacketSend(params));
    }
}
