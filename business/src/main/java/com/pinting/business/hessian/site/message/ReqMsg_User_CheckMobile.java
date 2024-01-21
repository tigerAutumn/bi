/**
 * Wenzi.com Inc.
 * Copyright (c) 2015-2025 All Rights Reserved.
 */
package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ReqMsg;

/**
 * 
 * @author HuanXiong
 * @version $Id: ReqMsg_User_CheckMobile.java, v 0.1 2016-1-25 上午11:42:39 HuanXiong Exp $
 */
public class ReqMsg_User_CheckMobile extends ReqMsg {

    /**  */
    private static final long serialVersionUID = -4524260494590122431L;

    private String mobile;

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
    
}
