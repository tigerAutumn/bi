/**
 * Wenzi.com Inc.
 * Copyright (c) 2015-2025 All Rights Reserved.
 */
package com.pinting.gateway.pay19.out.model.resp;

import java.util.List;

/**
 * 
 * @author Baby shark love blowing wind
 * @version $Id: QueryBankListResp.java, v 0.1 2015-8-6 上午10:22:33 BabyShark Exp $
 */
public class QueryBankListResp extends QuickPayBaseResp {

    /**  */
    private static final long serialVersionUID = 3849224139947046089L;
    private List<Pay19Bank>   bank_list;

    public List<Pay19Bank> getBank_list() {
        return bank_list;
    }

    public void setBank_list(List<Pay19Bank> bank_list) {
        this.bank_list = bank_list;
    }

}
