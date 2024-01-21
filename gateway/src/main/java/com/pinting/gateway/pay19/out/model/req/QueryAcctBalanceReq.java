/**
 * Wenzi.com Inc.
 * Copyright (c) 2015-2025 All Rights Reserved.
 */
package com.pinting.gateway.pay19.out.model.req;

/**
 * 
 * @author Baby shark love blowing wind
 * @version $Id: QueryAcctBalanceReq.java, v 0.1 2015-11-3 下午3:44:31 BabyShark Exp $
 */
public class QueryAcctBalanceReq extends AcctBalanceBaseReq {

    /**  */
    private static final long serialVersionUID = -2256152491740613939L;
    private String            account;
    private String            reqSno;
    private String            reqDate;
    private String            reserved;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getReqSno() {
        return reqSno;
    }

    public void setReqSno(String reqSno) {
        this.reqSno = reqSno;
    }

    public String getReqDate() {
        return reqDate;
    }

    public void setReqDate(String reqDate) {
        this.reqDate = reqDate;
    }

    public String getReserved() {
        return reserved;
    }

    public void setReserved(String reserved) {
        this.reserved = reserved;
    }

}
