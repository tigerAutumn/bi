/**
 * Wenzi.com Inc.
 * Copyright (c) 2015-2025 All Rights Reserved.
 */
package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ResMsg;

/**
 * 
 * @author HuanXiong
 * @version $Id: ResMsg_Shake_DrawRedPacket.java, v 0.1 2016-3-10 下午7:54:12 HuanXiong Exp $
 */
public class ResMsg_Shake_DrawRedPacket extends ResMsg {

    /**  */
    private static final long serialVersionUID = -8113019747805281592L;

    private Boolean isNewUser;

    public Boolean getIsNewUser() {
        return isNewUser;
    }

    public void setIsNewUser(Boolean isNewUser) {
        this.isNewUser = isNewUser;
    }
    
}
