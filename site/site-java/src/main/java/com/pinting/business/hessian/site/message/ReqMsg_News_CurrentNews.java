/**
 * Wenzi.com Inc.
 * Copyright (c) 2015-2025 All Rights Reserved.
 */
package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ReqMsg;

/**
 * 查询最新的新闻|公告|动态 入参
 * @author HuanXiong
 * @version $Id: ReqMsg_News_CurrentNews.java, v 0.1 2016-4-5 下午4:09:43 HuanXiong Exp $
 */
public class ReqMsg_News_CurrentNews extends ReqMsg {

    /**  */
    private static final long serialVersionUID = 5224161410247365614L;

    private String receiverType;    // 接收类型
    
    private String type;    // 新闻；公司动态
    
    private Integer showPage;   // 显示条数

    private Integer userId;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getReceiverType() {
        return receiverType;
    }

    public void setReceiverType(String receiverType) {
        this.receiverType = receiverType;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getShowPage() {
        return showPage;
    }

    public void setShowPage(Integer showPage) {
        this.showPage = showPage;
    }
    
}
