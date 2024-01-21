/**
 * Wenzi.com Inc.
 * Copyright (c) 2015-2025 All Rights Reserved.
 */
package com.pinting.gateway.pay19.out.model.req;

/**
 * 
 * @author Baby shark love blowing wind
 * @version $Id: RealNameAuthReq.java, v 0.1 2015-8-17 下午6:33:49 BabyShark Exp $
 */
public class RealNameVerifyReq extends RealNameBaseReq {

    /**  */
    private static final long serialVersionUID = 2266406818568700427L;
    private String            mxUserId;
    private String            mxReqSno;
    private String            mxReqDate;
    private String            cardHolder;
    private String            idType;
    private String            idNo;
    private String            mobile;
    private String            pcId;
    private String            bankCardNo;
    private String            cardType;
    private String            cardAttr;
    private String            cvv2;
    private String            validDate;
    private String            notifyUrl;
    private String            remark;
    private String            reserved;

    public String getMxUserId() {
        return mxUserId;
    }

    public void setMxUserId(String mxUserId) {
        this.mxUserId = mxUserId;
    }

    public String getMxReqSno() {
        return mxReqSno;
    }

    public void setMxReqSno(String mxReqSno) {
        this.mxReqSno = mxReqSno;
    }

    public String getMxReqDate() {
        return mxReqDate;
    }

    public void setMxReqDate(String mxReqDate) {
        this.mxReqDate = mxReqDate;
    }

    public String getCardHolder() {
        return cardHolder;
    }

    public void setCardHolder(String cardHolder) {
        this.cardHolder = cardHolder;
    }

    public String getIdType() {
        return idType;
    }

    public void setIdType(String idType) {
        this.idType = idType;
    }

    public String getIdNo() {
        return idNo;
    }

    public void setIdNo(String idNo) {
        this.idNo = idNo;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPcId() {
        return pcId;
    }

    public void setPcId(String pcId) {
        this.pcId = pcId;
    }

    public String getBankCardNo() {
        return bankCardNo;
    }

    public void setBankCardNo(String bankCardNo) {
        this.bankCardNo = bankCardNo;
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public String getCardAttr() {
        return cardAttr;
    }

    public void setCardAttr(String cardAttr) {
        this.cardAttr = cardAttr;
    }

    public String getCvv2() {
        return cvv2;
    }

    public void setCvv2(String cvv2) {
        this.cvv2 = cvv2;
    }

    public String getValidDate() {
        return validDate;
    }

    public void setValidDate(String validDate) {
        this.validDate = validDate;
    }

    public String getNotifyUrl() {
        return notifyUrl;
    }

    public void setNotifyUrl(String notifyUrl) {
        this.notifyUrl = notifyUrl;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getReserved() {
        return reserved;
    }

    public void setReserved(String reserved) {
        this.reserved = reserved;
    }

}
