/**
 * Wenzi.com Inc.
 * Copyright (c) 2015-2025 All Rights Reserved.
 */
package com.pinting.gateway.pay19.out.model.req;

/**
 * 
 * @author Baby shark love blowing wind
 * @version $Id: QueryMOrder.java, v 0.1 2015-8-7 上午10:13:46 BabyShark Exp $
 */
public class QueryMOrderReq extends QuickPayBaseReq {

    /**  */
    private static final long serialVersionUID = -4330562767927807042L;
    private String            mx_userid;
    private String            order_id;
    private String            order_date;
    private String            ts;

    public String getMx_userid() {
        return mx_userid;
    }

    public void setMx_userid(String mx_userid) {
        this.mx_userid = mx_userid;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getOrder_date() {
        return order_date;
    }

    public void setOrder_date(String order_date) {
        this.order_date = order_date;
    }

    public String getTs() {
        return ts;
    }

    public void setTs(String ts) {
        this.ts = ts;
    }

}
