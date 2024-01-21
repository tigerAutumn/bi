/**
 * Wenzi.com Inc.
 * Copyright (c) 2015-2025 All Rights Reserved.
 */
package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ReqMsg;

/**
 * 
 * @author HuanXiong
 * @version $Id: ReqMsg_Activity_LuckyDrawResult.java, v 0.1 2016-1-21 下午6:09:30 HuanXiong Exp $
 */
public class ReqMsg_Activity_LuckyDrawResult extends ReqMsg {

    /**  */
    private static final long serialVersionUID = 1434420103008361038L;

    private Integer userId;
    
    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

}
