/**
 * Wenzi.com Inc.
 * Copyright (c) 2015-2025 All Rights Reserved.
 */
package com.pinting.business.hessian.manage.message;

import com.pinting.core.hessian.msg.ResMsg;

/**
 * 
 * @author HuanXiong
 * @version $Id: ResMsg_Finance_ChannelWithdraw.java, v 0.1 2016-1-7 下午12:33:13 HuanXiong Exp $
 */
public class ResMsg_ChannelWithdraw_ChannelWithdraw extends ResMsg {

    /**  */
    private static final long serialVersionUID = -2246227433930038895L;
    
    private String msg;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
    
}
