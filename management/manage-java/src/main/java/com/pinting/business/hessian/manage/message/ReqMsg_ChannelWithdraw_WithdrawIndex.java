/**
 * Wenzi.com Inc.
 * Copyright (c) 2015-2025 All Rights Reserved.
 */
package com.pinting.business.hessian.manage.message;

import com.pinting.core.hessian.msg.ReqMsg;

/**
 * 
 * @author HuanXiong
 * @version $Id: ReqMsg_Finance_WithdrawIndex.java, v 0.1 2016-1-7 下午3:17:04 HuanXiong Exp $
 */
public class ReqMsg_ChannelWithdraw_WithdrawIndex extends ReqMsg {

    /**  */
    private static final long serialVersionUID = -90404859042576357L;

    private String channelType;
    
    private Integer channelBankCardId;
    
    public Integer getChannelBankCardId() {
        return channelBankCardId;
    }

    public void setChannelBankCardId(Integer channelBankCardId) {
        this.channelBankCardId = channelBankCardId;
    }

    public String getChannelType() {
        return channelType;
    }

    public void setChannelType(String channelType) {
        this.channelType = channelType;
    }
}
