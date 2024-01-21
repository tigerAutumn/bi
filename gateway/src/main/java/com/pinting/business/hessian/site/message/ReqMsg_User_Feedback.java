/**
 * Wenzi.com Inc.
 * Copyright (c) 2015-2025 All Rights Reserved.
 */
package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ReqMsg;

/**
 * 
 * @author HuanXiong
 * @version $Id: ReqMsg_User_Feedback.java, v 0.1 2015-12-22 下午3:41:49 HuanXiong Exp $
 */
public class ReqMsg_User_Feedback extends ReqMsg {
    
    /**  */
    private static final long serialVersionUID = -16002918006353081L;
    
    private Integer userId;
    
    private String info;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
