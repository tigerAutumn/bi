/**
 * Wenzi.com Inc.
 * Copyright (c) 2015-2025 All Rights Reserved.
 */
package com.pinting.business.hessian.manage.message;

import com.pinting.core.hessian.msg.ReqMsg;

/**
 * 
 * @author HuanXiong
 * @version $Id: ReqMsg_ChannelWithdraw_ConfirmTransfer.java, v 0.1 2016-1-8 下午2:01:54 HuanXiong Exp $
 */
public class ReqMsg_ChannelWithdraw_ConfirmTransfer extends ReqMsg {

    /**  */
    private static final long serialVersionUID = 6937014318903986282L;

    private Integer userId;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
