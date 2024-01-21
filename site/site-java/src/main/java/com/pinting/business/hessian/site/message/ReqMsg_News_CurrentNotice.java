/**
 * Wenzi.com Inc.
 * Copyright (c) 2015-2025 All Rights Reserved.
 */
package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ReqMsg;

/**
 * 目前没有用到
 * @author HuanXiong
 * @version $Id: ReqMsg_News_CurrentNews.java, v 0.1 2016-2-23 上午10:18:23 HuanXiong Exp $
 */
public class ReqMsg_News_CurrentNotice extends ReqMsg {

    /**  */
    private static final long serialVersionUID = 5224161410247365614L;

    private String receiverType;    // 接收类型
    
    private String type;    // 公告；新闻

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getReceiverType() {
        return receiverType;
    }

    public void setReceiverType(String receiverType) {
        this.receiverType = receiverType;
    }
    
    
    
}
