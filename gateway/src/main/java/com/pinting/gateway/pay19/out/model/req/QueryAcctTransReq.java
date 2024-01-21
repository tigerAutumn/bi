/**
 * Wenzi.com Inc.
 * Copyright (c) 2015-2025 All Rights Reserved.
 */
package com.pinting.gateway.pay19.out.model.req;

/**
 * 
 * @author Baby shark love blowing wind
 * @version $Id: QueryAcctTransReq.java, v 0.1 2015-11-4 下午2:11:22 BabyShark Exp $
 */
public class QueryAcctTransReq extends AcctTransBaseReq {

    /**  */
    private static final long serialVersionUID = -5955897979314003270L;

    private String            orderId;
    private String            orderDate;
    private String            tradeType;
    private String            ts;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public String getTradeType() {
        return tradeType;
    }

    public void setTradeType(String tradeType) {
        this.tradeType = tradeType;
    }

    public String getTs() {
        return ts;
    }

    public void setTs(String ts) {
        this.ts = ts;
    }

}
