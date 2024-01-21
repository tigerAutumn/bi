/**
 * Wenzi.com Inc.
 * Copyright (c) 2015-2025 All Rights Reserved.
 */
package com.pinting.gateway.pay19.out.model.req;

/**
 * 
 * @author Baby shark love blowing wind
 * @version $Id: UnBindCardReq.java, v 0.1 2015-8-7 上午10:25:47 BabyShark Exp $
 */
public class UnBindCardReq extends QuickPayBaseReq {

    /**  */
    private static final long serialVersionUID = -2098276648169234793L;

    private String            mx_userid;
    private String            bind_sno;
    private String            ts;

    public String getMx_userid() {
        return mx_userid;
    }

    public void setMx_userid(String mx_userid) {
        this.mx_userid = mx_userid;
    }

    public String getBind_sno() {
        return bind_sno;
    }

    public void setBind_sno(String bind_sno) {
        this.bind_sno = bind_sno;
    }

    public String getTs() {
        return ts;
    }

    public void setTs(String ts) {
        this.ts = ts;
    }

}
