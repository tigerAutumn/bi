/**
 * Wenzi.com Inc.
 * Copyright (c) 2015-2025 All Rights Reserved.
 */
package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ReqMsg;

/**
 * 
 * @author HuanXiong
 * @version $Id: ReqMsg_RedPacket_RedPacketList.java, v 0.1 2016-3-1 下午12:52:32 HuanXiong Exp $
 */
public class ReqMsg_RedPacketInfo_RedPacketList extends ReqMsg {

    /**  */
    private static final long serialVersionUID = -5011669355009172963L;
    
    private Integer userId;
    
    private String status;  // 状态
    
    private Double amount;  // 金额
    
    private Integer productId;   // 产品ID（便于获取期限，购买时用）

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
