/**
 * Wenzi.com Inc.
 * Copyright (c) 2015-2025 All Rights Reserved.
 */
package com.pinting.gateway.pay19.out.model.resp;

/**
 * 
 * @author Baby shark love blowing wind
 * @version $Id: QueryAcctBalanceResp.java, v 0.1 2015-11-3 下午3:46:19 BabyShark Exp $
 */
public class QueryAcctBalanceResp extends AcctBalanceBaseResp {

    /**  */
    private static final long serialVersionUID = 6261132952087898583L;

    private String            reqSno;
    private String            merchantId;
    private String            account;
    private String            totalBalance;
    private String            availableBalance;
    private String            frozenBalance;
    private String            unSettleBalance;

    public String getReqSno() {
        return reqSno;
    }

    public void setReqSno(String reqSno) {
        this.reqSno = reqSno;
    }

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getTotalBalance() {
        return totalBalance;
    }

    public void setTotalBalance(String totalBalance) {
        this.totalBalance = totalBalance;
    }

    public String getAvailableBalance() {
        return availableBalance;
    }

    public void setAvailableBalance(String availableBalance) {
        this.availableBalance = availableBalance;
    }

    public String getFrozenBalance() {
        return frozenBalance;
    }

    public void setFrozenBalance(String frozenBalance) {
        this.frozenBalance = frozenBalance;
    }

    public String getUnSettleBalance() {
        return unSettleBalance;
    }

    public void setUnSettleBalance(String unSettleBalance) {
        this.unSettleBalance = unSettleBalance;
    }

}
