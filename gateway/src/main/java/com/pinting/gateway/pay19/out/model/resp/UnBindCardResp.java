/**
 * Wenzi.com Inc.
 * Copyright (c) 2015-2025 All Rights Reserved.
 */
package com.pinting.gateway.pay19.out.model.resp;

/**
 * 
 * @author Baby shark love blowing wind
 * @version $Id: UnBindCardResp.java, v 0.1 2015-8-7 上午10:26:56 BabyShark Exp $
 */
public class UnBindCardResp extends QuickPayBaseResp {

    /**  */
    private static final long serialVersionUID = 6636595763122008035L;
    private String            mx_userid;

    public String getMx_userid() {
        return mx_userid;
    }

    public void setMx_userid(String mx_userid) {
        this.mx_userid = mx_userid;
    }

}
