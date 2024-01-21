/**
 * Wenzi.com Inc.
 * Copyright (c) 2015-2025 All Rights Reserved.
 */
package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ReqMsg;

/**
 * 
 * @author HuanXiong
 * @version $Id: ReqMsg_User_CheckPayPassword.java, v 0.1 2015-11-17 下午3:58:33 HuanXiong Exp $
 */
public class ReqMsg_User_CheckPayPassword extends ReqMsg {

    /**  */
    private static final long serialVersionUID = 5501585328263950060L;

    private Integer userId;
    
    private String payPassword;
    
    

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getPayPassword() {
        return payPassword;
    }

    public void setPayPassword(String payPassword) {
        this.payPassword = payPassword;
    }
    
    
}
