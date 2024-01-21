/**
 * Wenzi.com Inc.
 * Copyright (c) 2015-2025 All Rights Reserved.
 */
package com.pinting.business.hessian.manage.message;

import com.pinting.core.hessian.msg.ReqMsg;

/**
 * 
 * @author HuanXiong
 * @version $Id: ReqMsg_ChannelWithdraw_AuditWithdrawCheck.java, v 0.1 2016-1-8 下午1:35:11 HuanXiong Exp $
 */
public class ReqMsg_ChannelWithdraw_AuditWithdrawCheck extends ReqMsg {

    /**  */
    private static final long serialVersionUID = -6419035396237560770L;

    private Integer id;
    
    private Integer mUserId;
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getmUserId() {
        return mUserId;
    }

    public void setmUserId(Integer mUserId) {
        this.mUserId = mUserId;
    }
    
}
