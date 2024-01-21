/**
 * Wenzi.com Inc.
 * Copyright (c) 2015-2025 All Rights Reserved.
 */
package com.pinting.gateway.pay19.out.model.resp;

import java.util.List;

/**
 * 
 * @author Baby shark love blowing wind
 * @version $Id: QueryBankCardListResp.java, v 0.1 2015-8-7 上午10:23:59 BabyShark Exp $
 */
public class QueryBankCardListResp extends QuickPayBaseResp {

    /**  */
    private static final long   serialVersionUID = 2183835653432456747L;
    private String              mx_userid;
    private List<Pay19BankCard> bank_card_list;

    public String getMx_userid() {
        return mx_userid;
    }

    public void setMx_userid(String mx_userid) {
        this.mx_userid = mx_userid;
    }

    public List<Pay19BankCard> getBank_card_list() {
        return bank_card_list;
    }

    public void setBank_card_list(List<Pay19BankCard> bank_card_list) {
        this.bank_card_list = bank_card_list;
    }
}
