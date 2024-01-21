package com.pinting.gateway.hessian.message.zsd.model;

/**
 * Author:      cyb
 * Date:        2017/8/30
 * Description:
 */
public class OpenAccountReq {

    private String userId;

    // 银行卡号，是否必填（是）
    private String bankCard;

    // 银行类型，是否必填（是）
    private String bankCode;

    // 身份证号，是否必填（是）
    private String idCard;

    // 姓名，是否必填（是）
    private String cardHolder;

    // 银行预留手机号，是否必填（是）
    private String mobile;

    // 年收入，是否必填（否）
    private String annualIncome;

    // 资产合作方CODE
    private String partnerCode;

    private String ucUserType;

    private String workUnit;

    private String education;

    private String marriage;

    /*借款人地址*/
    private String address;

    /*借款人邮箱*/
    private String email;

    public String getWorkUnit() {
        return workUnit;
    }

    public void setWorkUnit(String workUnit) {
        this.workUnit = workUnit;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getMarriage() {
        return marriage;
    }

    public void setMarriage(String marriage) {
        this.marriage = marriage;
    }

    public String getUcUserType() {
        return ucUserType;
    }

    public void setUcUserType(String ucUserType) {
        this.ucUserType = ucUserType;
    }

    public String getPartnerCode() {
        return partnerCode;
    }

    public void setPartnerCode(String partnerCode) {
        this.partnerCode = partnerCode;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getBankCard() {
        return bankCard;
    }

    public void setBankCard(String bankCard) {
        this.bankCard = bankCard;
    }

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getCardHolder() {
        return cardHolder;
    }

    public void setCardHolder(String cardHolder) {
        this.cardHolder = cardHolder;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getAnnualIncome() {
        return annualIncome;
    }

    public void setAnnualIncome(String annualIncome) {
        this.annualIncome = annualIncome;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
