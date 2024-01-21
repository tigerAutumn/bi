/**
 * Wenzi.com Inc.
 * Copyright (c) 2015-2025 All Rights Reserved.
 */
package com.pinting.business.hessian.site.message;

import java.util.Date;

import com.pinting.core.hessian.msg.ResMsg;

/**
 * 
 * @author HuanXiong
 * @version $Id: ResMsg_RegularBuy_EBankBuy.java, v 0.1 2015-11-24 上午10:09:13 HuanXiong Exp $
 */
public class ResMsg_RegularBuy_EBankBuy extends ResMsg {

    /**  */
    private static final long serialVersionUID = 3115900743132666176L;

    private String orderNo;
    
    private Date orderDate;

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }
}
