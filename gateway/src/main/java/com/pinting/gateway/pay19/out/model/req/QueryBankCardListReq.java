/**
 * Wenzi.com Inc.
 * Copyright (c) 2015-2025 All Rights Reserved.
 */
package com.pinting.gateway.pay19.out.model.req;

/**
 * 
 * @author Baby shark love blowing wind
 * @version $Id: QueryBankCardListReq.java, v 0.1 2015-8-7 上午10:19:16 BabyShark Exp $
 */
public class QueryBankCardListReq extends QuickPayBaseReq {

    /**  */
    private static final long serialVersionUID = -9179273305686442517L;
    private String            mx_userid;
    private String            trade_type;
    private String            ts;

    public String getMx_userid() {
        return mx_userid;
    }

    public void setMx_userid(String mx_userid) {
        this.mx_userid = mx_userid;
    }

    public String getTrade_type() {
        return trade_type;
    }

    public void setTrade_type(String trade_type) {
        this.trade_type = trade_type;
    }

    public String getTs() {
        return ts;
    }

    public void setTs(String ts) {
        this.ts = ts;
    }

}
