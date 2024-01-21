/**
 * Wenzi.com Inc.
 * Copyright (c) 2015-2025 All Rights Reserved.
 */
package com.pinting.business.facade.site;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pinting.business.hessian.site.message.ReqMsg_Shake_DrawRedPacket;
import com.pinting.business.hessian.site.message.ReqMsg_Shake_GetWinUserNumber;
import com.pinting.business.hessian.site.message.ResMsg_Shake_DrawRedPacket;
import com.pinting.business.hessian.site.message.ResMsg_Shake_GetWinUserNumber;
import com.pinting.business.service.site.ShakeService;

/**
 * 
 * @author HuanXiong
 * @version $Id: ShakeFacade.java, v 0.1 2016-3-10 下午8:11:05 HuanXiong Exp $
 */
@Component("Shake")
public class ShakeFacade {
    
    @Autowired
    private ShakeService shakeService;
    
    public void drawRedPacket(ReqMsg_Shake_DrawRedPacket req, ResMsg_Shake_DrawRedPacket res) {
        shakeService.drawRedPacket(req, res);
    }
    
    public void getWinUserNumber(ReqMsg_Shake_GetWinUserNumber req,
                                 ResMsg_Shake_GetWinUserNumber res) {
        shakeService.getWinUserNumber(req, res);
    }
}
