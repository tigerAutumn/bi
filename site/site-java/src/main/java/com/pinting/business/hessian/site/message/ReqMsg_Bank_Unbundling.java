/**
 * Wenzi.com Inc.
 * Copyright (c) 2015-2025 All Rights Reserved.
 */
package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ReqMsg;

/**
 * 解绑银行卡 入参
 * @author HuanXiong
 * @version $Id: ReqMsg_Bank_Unbundling.java, v 0.1 2015-11-14 下午3:43:02 HuanXiong Exp $
 */
public class ReqMsg_Bank_Unbundling extends ReqMsg {

    /**  */
    private static final long serialVersionUID = -817927404926847061L;
    /*银行卡号*/
    private String            cardNo;
    /*银行卡id*/
    private Integer           cardId;
    /*银行名称*/
    private String            bankName;
    /*用户id*/
    private Integer userId;
    
    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public Integer getCardId() {
        return cardId;
    }

    public void setCardId(Integer cardId) {
        this.cardId = cardId;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

}
