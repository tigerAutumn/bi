/**
 * Wenzi.com Inc.
 * Copyright (c) 2015-2025 All Rights Reserved.
 */
package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ReqMsg;

/**
 * 
 * @author HuanXiong
 * @version $Id: ReqMsg_RegularBuy_EBankBuy.java, v 0.1 2015-11-24 上午10:08:41 HuanXiong Exp $
 */
public class ReqMsg_RegularBuy_EBankBuy extends ReqMsg {

    /**  */
    private static final long serialVersionUID = -8784980526929131747L;

    private Integer userId;
    
    private Integer productId;
    
    private Double amount;
    
    private Integer transType; //交易类型1卡购买,2充值
    
    public Integer getTransType() {
        return transType;
    }

    public void setTransType(Integer transType) {
        this.transType = transType;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }
    
}
