/**
 * Wenzi.com Inc.
 * Copyright (c) 2015-2025 All Rights Reserved.
 */
package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ReqMsg;

/**
 * 根据id和类型查询某一个新闻的内容 入参
 * @author HuanXiong
 * @version $Id: ReqMsg_News_Details.java, v 0.1 2016-2-23 上午10:30:31 HuanXiong Exp $
 */
public class ReqMsg_News_Details extends ReqMsg {

    /**  */
    private static final long serialVersionUID = -8721058430508581326L;

    private Integer id; // newsId
    /*类型*/
    private String type;

    private Integer userId;

    private String receiverType;

    public String getReceiverType() {
        return receiverType;
    }

    public void setReceiverType(String receiverType) {
        this.receiverType = receiverType;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    
}
