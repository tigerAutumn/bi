/**
 * Wenzi.com Inc.
 * Copyright (c) 2015-2025 All Rights Reserved.
 */
package com.pinting.business.hessian.manage.message;

import com.pinting.core.hessian.msg.ReqMsg;

/**
 * 
 * @author HuanXiong
 * @version $Id: ReqMsg_Finance_ChannelWithdraw.java, v 0.1 2016-1-7 下午3:10:42 HuanXiong Exp $
 */
public class ReqMsg_ChannelWithdraw_ChannelWithdraw extends ReqMsg {

    /**  */
    private static final long serialVersionUID = -7854435387934660895L;

    private Integer userId;
    
    private String channelType;
    
    private Integer channelBankCardId;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getChannelType() {
        return channelType;
    }

    public void setChannelType(String channelType) {
        this.channelType = channelType;
    }

    public Integer getChannelBankCardId() {
        return channelBankCardId;
    }

    public void setChannelBankCardId(Integer channelBankCardId) {
        this.channelBankCardId = channelBankCardId;
    }
}
