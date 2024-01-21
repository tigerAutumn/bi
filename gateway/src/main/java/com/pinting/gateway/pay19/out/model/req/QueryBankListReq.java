/**
 * Wenzi.com Inc.
 * Copyright (c) 2015-2025 All Rights Reserved.
 */
package com.pinting.gateway.pay19.out.model.req;

/**
 * 
 * @author Baby shark love blowing wind
 * @version $Id: QueryBankListReq.java, v 0.1 2015-8-6 上午10:21:24 BabyShark Exp $
 */
public class QueryBankListReq extends QuickPayBaseReq {

    /**  */
    private static final long serialVersionUID = 1944926426076024640L;

    private String            ts;

    public String getTs() {
        return ts;
    }

    public void setTs(String ts) {
        this.ts = ts;
    }
}
