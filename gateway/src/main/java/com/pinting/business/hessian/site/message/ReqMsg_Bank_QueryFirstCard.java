/**
 * Wenzi.com Inc.
 * Copyright (c) 2015-2025 All Rights Reserved.
 */
package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ReqMsg;

/**
 * 查询回款卡信息 入参
 * @author HuanXiong
 * @version $Id: ReqMsg_Bank_QueryFirstCard.java, v 0.1 2015-12-24 上午10:52:04 HuanXiong Exp $
 */
public class ReqMsg_Bank_QueryFirstCard extends ReqMsg {

    /**  */
    private static final long serialVersionUID = 4195959635200916236L;
    /*用户id*/
    private Integer userId;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
    
}
