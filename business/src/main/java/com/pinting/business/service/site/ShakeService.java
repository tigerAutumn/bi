/**
 * Wenzi.com Inc.
 * Copyright (c) 2015-2025 All Rights Reserved.
 */
package com.pinting.business.service.site;

import com.pinting.business.hessian.site.message.ReqMsg_Shake_DrawRedPacket;
import com.pinting.business.hessian.site.message.ReqMsg_Shake_GetWinUserNumber;
import com.pinting.business.hessian.site.message.ResMsg_Shake_DrawRedPacket;
import com.pinting.business.hessian.site.message.ResMsg_Shake_GetWinUserNumber;

/**
 * 
 * @author HuanXiong
 * @version $Id: ShakeService.java, v 0.1 2016-3-10 下午8:12:18 HuanXiong Exp $
 */
public interface ShakeService {

    /**
     * @param req
     * @param res
     */
    public void drawRedPacket(ReqMsg_Shake_DrawRedPacket req, ResMsg_Shake_DrawRedPacket res);
    
    /**
     * 获得摇到红包的人数
     * @param req
     * @param res
     */
    public void getWinUserNumber(ReqMsg_Shake_GetWinUserNumber req, ResMsg_Shake_GetWinUserNumber res);
    
}
