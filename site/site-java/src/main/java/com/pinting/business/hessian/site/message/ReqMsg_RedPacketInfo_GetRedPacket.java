/**
 * Wenzi.com Inc.
 * Copyright (c) 2015-2025 All Rights Reserved.
 */
package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ReqMsg;

/**
 * 
 * @author HuanXiong
 * @version $Id: ReqMsg_RedPacketInfo_GetRedPacket.java, v 0.1 2016-3-3 上午10:50:42 HuanXiong Exp $
 */
public class ReqMsg_RedPacketInfo_GetRedPacket extends ReqMsg {

    /**  */
    private static final long serialVersionUID = 5251287360520113969L;

    private Integer userId;
    
    private String status;  // 状态
    
    private Double amount;  // 金额
    
    private Integer productId;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }
    
}
