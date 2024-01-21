/**
 * Wenzi.com Inc.
 * Copyright (c) 2015-2025 All Rights Reserved.
 */
package com.pinting.gateway.pay19.out.model.resp;

import java.io.Serializable;

/**
 * 
 * @author Baby shark love blowing wind
 * @version $Id: Pay19BankCard.java, v 0.1 2015-8-7 上午10:20:43 BabyShark Exp $
 */
public class Pay19BankCard implements Serializable {
    /**  */
    private static final long serialVersionUID = -4320380345066257958L;
    private String            bindSno;
    private String            bankAccount;
    private String            pcId;
    private String            cardType;
    private String            accountType;
    private String            bankName;
    private String            tradeType;
    private String            chanIfUse;
    private String            cardHolder;
    private String            userMobile;
    private String            idCard;
    private String            lastTradeTime;

    public String getBindSno() {
        return bindSno;
    }

    public void setBindSno(String bindSno) {
        this.bindSno = bindSno;
    }

    public String getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(String bankAccount) {
        this.bankAccount = bankAccount;
    }

    public String getPcId() {
        return pcId;
    }

    public void setPcId(String pcId) {
        this.pcId = pcId;
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getTradeType() {
        return tradeType;
    }

    public void setTradeType(String tradeType) {
        this.tradeType = tradeType;
    }

    public String getChanIfUse() {
        return chanIfUse;
    }

    public void setChanIfUse(String chanIfUse) {
        this.chanIfUse = chanIfUse;
    }

    public String getCardHolder() {
        return cardHolder;
    }

    public void setCardHolder(String cardHolder) {
        this.cardHolder = cardHolder;
    }

    public String getUserMobile() {
        return userMobile;
    }

    public void setUserMobile(String userMobile) {
        this.userMobile = userMobile;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getLastTradeTime() {
        return lastTradeTime;
    }

    public void setLastTradeTime(String lastTradeTime) {
        this.lastTradeTime = lastTradeTime;
    }
}
