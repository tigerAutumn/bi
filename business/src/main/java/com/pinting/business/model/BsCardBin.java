package com.pinting.business.model;

import java.util.Date;

public class BsCardBin {
    private Integer id;

    private Integer bankId;

    private String cardBin;

    private Integer cardBinLen;

    private Integer bankCardLen;

    private String bankNameDesc;

    private String bankCardTypeDesc;

    private String bankCardFuncType;

    private Date createTime;

    private Date updateTime;

    private String note;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getBankId() {
        return bankId;
    }

    public void setBankId(Integer bankId) {
        this.bankId = bankId;
    }

    public String getCardBin() {
        return cardBin;
    }

    public void setCardBin(String cardBin) {
        this.cardBin = cardBin;
    }

    public Integer getCardBinLen() {
        return cardBinLen;
    }

    public void setCardBinLen(Integer cardBinLen) {
        this.cardBinLen = cardBinLen;
    }

    public Integer getBankCardLen() {
        return bankCardLen;
    }

    public void setBankCardLen(Integer bankCardLen) {
        this.bankCardLen = bankCardLen;
    }

    public String getBankNameDesc() {
        return bankNameDesc;
    }

    public void setBankNameDesc(String bankNameDesc) {
        this.bankNameDesc = bankNameDesc;
    }

    public String getBankCardTypeDesc() {
        return bankCardTypeDesc;
    }

    public void setBankCardTypeDesc(String bankCardTypeDesc) {
        this.bankCardTypeDesc = bankCardTypeDesc;
    }

    public String getBankCardFuncType() {
        return bankCardFuncType;
    }

    public void setBankCardFuncType(String bankCardFuncType) {
        this.bankCardFuncType = bankCardFuncType;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}