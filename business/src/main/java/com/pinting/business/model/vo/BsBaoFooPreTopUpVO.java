package com.pinting.business.model.vo;

/**
 * Created by 剑钊 on 2016/8/5.
 * 预充值
 */
public class BsBaoFooPreTopUpVO {

    /**
     * 请求绑定的银行卡号
     *
     */
    private String accNo;

    /**
     * 交易金额
     */
    private Double txnAccount;


    public String getAccNo() {
        return accNo;
    }

    public void setAccNo(String accNo) {
        this.accNo = accNo;
    }

    public Double getTxnAccount() {
        return txnAccount;
    }

    public void setTxnAccount(Double txnAccount) {
        this.txnAccount = txnAccount;
    }
}
