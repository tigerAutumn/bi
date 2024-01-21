/**
 * Wenzi.com Inc.
 * Copyright (c) 2015-2025 All Rights Reserved.
 */
package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ReqMsg;

/**
 * 设置回款卡 入参
 * @author HuanXiong
 * @version $Id: ReqMsg_Bank_SetIsFirstBankCard.java, v 0.1 2015-11-17 上午9:51:50 HuanXiong Exp $
 */
public class ReqMsg_Bank_SetIsFirstBankCard extends ReqMsg {

    /**  */
    private static final long serialVersionUID = -297026148765415238L;
    /*用户id*/
    private Integer userId;
    /*如果是账户余额，则为null*/
    private Integer cardId; 

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getCardId() {
        return cardId;
    }

    public void setCardId(Integer cardId) {
        this.cardId = cardId;
    }
    
    
}
