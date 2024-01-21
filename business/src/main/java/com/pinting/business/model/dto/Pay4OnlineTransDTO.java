package com.pinting.business.model.dto;

/**
 * Created by 剑钊 on 2016/11/16.
 */
public class Pay4OnlineTransDTO {

    /**
     * 转账方编码
     * YUN_DAI 云贷
     * 7_DAI 7贷
     */
    private String partner;

    /**
     * 转账金额
     * 单位 元
     */
    private String amount;


    private String token;

    public String getPartner() {
        return partner;
    }

    public void setPartner(String partner) {
        this.partner = partner;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
