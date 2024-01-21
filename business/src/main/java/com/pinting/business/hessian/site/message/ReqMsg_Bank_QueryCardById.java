/**
 * Wenzi.com Inc.
 * Copyright (c) 2015-2025 All Rights Reserved.
 */
package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ReqMsg;

/**
 * 根据银行卡ID查询银行卡入参
 * @author HuanXiong
 * @version $Id: ReqMsg_Bank_QueryCardByUserId.java, v 0.1 2015-11-21 下午3:06:02 HuanXiong Exp $
 */
public class ReqMsg_Bank_QueryCardById extends ReqMsg {

    /**  */
    private static final long serialVersionUID = -5860837653327304390L;
    /*用户id*/
    private Integer userId;
    /*银行卡ID*/
    private Integer cardId;
    
    public Integer getCardId() {
        return cardId;
    }

    public void setCardId(Integer cardId) {
        this.cardId = cardId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
