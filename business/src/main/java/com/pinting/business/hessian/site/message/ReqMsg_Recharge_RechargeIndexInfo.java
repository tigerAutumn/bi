/**
 * Wenzi.com Inc.
 * Copyright (c) 2015-2025 All Rights Reserved.
 */
package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ReqMsg;

/**
 * 充值首页信息
 * @author HuanXiong
 * @version $Id: ReqMsg_Recharge_RechargeIndexInfo.java, v 0.1 2015-11-19 上午10:27:31 HuanXiong Exp $
 */
public class ReqMsg_Recharge_RechargeIndexInfo extends ReqMsg {

    /**  */
    private static final long serialVersionUID = 7506639148220412995L;
    
    private Integer userId;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
    
}
