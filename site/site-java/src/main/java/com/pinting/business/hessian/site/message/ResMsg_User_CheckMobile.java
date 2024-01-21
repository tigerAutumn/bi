/**
 * Wenzi.com Inc.
 * Copyright (c) 2015-2025 All Rights Reserved.
 */
package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ResMsg;

/**
 * 
 * @author HuanXiong
 * @version $Id: ResMsg_User_CheckMobile.java, v 0.1 2016-1-25 上午11:43:29 HuanXiong Exp $
 */
public class ResMsg_User_CheckMobile extends ResMsg {
    
    /**  */
    private static final long serialVersionUID = -7767532098048455301L;
    
    private Boolean isRegister;

    public Boolean getIsRegister() {
        return isRegister;
    }

    public void setIsRegister(Boolean isRegister) {
        this.isRegister = isRegister;
    }
    
}
