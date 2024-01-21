/**
 * Wenzi.com Inc.
 * Copyright (c) 2015-2025 All Rights Reserved.
 */
package com.pinting.gateway.pay19.out.model.req;

/**
 * 
 * @author Baby shark love blowing wind
 * @version $Id: NewMerchantDf.java, v 0.1 2015-8-11 下午3:48:23 BabyShark Exp $
 */
public class NewMerchantDfReq extends Pay4AnotherBaseReq {

    /**  */
    private static final long serialVersionUID = -8625544634820070606L;
    private String            mxLoginName;
    private String            mxOrderId;
    private String            amount;
    private String            cardHolder;
    private String            bankCardId;
    private String            accountType;
    private String            cardType;
    private String            bankCode;
    private String            subBankName;
    private String            provinceId;
    private String            cityId;
    private String            alliedBankCode;
    private String            payType;
    private String            tradeDesc;
    private String            notifyUrl;
    private String            currency;
    private String            payerId;
    private String            tradeType;
    private String            mxReserved;
    private String            commonExtend;
    private String            persistHandling;
    private String            persistTimeOut;
    private String            securityInfo;
    private String            extend1;
    private String            extend2;
    private String            extend3;

    public String getMxLoginName() {
        return mxLoginName;
    }

    public void setMxLoginName(String mxLoginName) {
        this.mxLoginName = mxLoginName;
    }

    public String getMxOrderId() {
        return mxOrderId;
    }

    public void setMxOrderId(String mxOrderId) {
        this.mxOrderId = mxOrderId;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getCardHolder() {
        return cardHolder;
    }

    public void setCardHolder(String cardHolder) {
        this.cardHolder = cardHolder;
    }

    public String getBankCardId() {
        return bankCardId;
    }

    public void setBankCardId(String bankCardId) {
        this.bankCardId = bankCardId;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }

    public String getSubBankName() {
        return subBankName;
    }

    public void setSubBankName(String subBankName) {
        this.subBankName = subBankName;
    }

    public String getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(String provinceId) {
        this.provinceId = provinceId;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getAlliedBankCode() {
        return alliedBankCode;
    }

    public void setAlliedBankCode(String alliedBankCode) {
        this.alliedBankCode = alliedBankCode;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public String getTradeDesc() {
        return tradeDesc;
    }

    public void setTradeDesc(String tradeDesc) {
        this.tradeDesc = tradeDesc;
    }

    public String getNotifyUrl() {
        return notifyUrl;
    }

    public void setNotifyUrl(String notifyUrl) {
        this.notifyUrl = notifyUrl;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getPayerId() {
        return payerId;
    }

    public void setPayerId(String payerId) {
        this.payerId = payerId;
    }

    public String getTradeType() {
        return tradeType;
    }

    public void setTradeType(String tradeType) {
        this.tradeType = tradeType;
    }

    public String getMxReserved() {
        return mxReserved;
    }

    public void setMxReserved(String mxReserved) {
        this.mxReserved = mxReserved;
    }

    public String getCommonExtend() {
        return commonExtend;
    }

    public void setCommonExtend(String commonExtend) {
        this.commonExtend = commonExtend;
    }

    public String getPersistHandling() {
        return persistHandling;
    }

    public void setPersistHandling(String persistHandling) {
        this.persistHandling = persistHandling;
    }

    public String getPersistTimeOut() {
        return persistTimeOut;
    }

    public void setPersistTimeOut(String persistTimeOut) {
        this.persistTimeOut = persistTimeOut;
    }

    public String getSecurityInfo() {
        return securityInfo;
    }

    public void setSecurityInfo(String securityInfo) {
        this.securityInfo = securityInfo;
    }

    public String getExtend1() {
        return extend1;
    }

    public void setExtend1(String extend1) {
        this.extend1 = extend1;
    }

    public String getExtend2() {
        return extend2;
    }

    public void setExtend2(String extend2) {
        this.extend2 = extend2;
    }

    public String getExtend3() {
        return extend3;
    }

    public void setExtend3(String extend3) {
        this.extend3 = extend3;
    }

}
