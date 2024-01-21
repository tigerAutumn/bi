/**
 * Wenzi.com Inc.
 * Copyright (c) 2015-2025 All Rights Reserved.
 */
package com.pinting.gateway.pay19.out.model.resp;

import java.util.List;

/**
 * 
 * @author Baby shark love blowing wind
 * @version $Id: CheckAccountFileResp.java, v 0.1 2015-8-31 下午8:12:53 BabyShark Exp $
 */
public class CheckAccountFileResp extends BaseResp {

    /**  */
    private static final long   serialVersionUID = 1182712172368149206L;
    private TotalAccount        totalAccount;
    private List<AccountDetail> accountDetails;

    public TotalAccount getTotalAccount() {
        return totalAccount;
    }

    public void setTotalAccount(TotalAccount totalAccount) {
        this.totalAccount = totalAccount;
    }

    public List<AccountDetail> getAccountDetails() {
        return accountDetails;
    }

    public void setAccountDetails(List<AccountDetail> accountDetails) {
        this.accountDetails = accountDetails;
    }

}
