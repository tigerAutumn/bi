package com.pinting.business.accounting.loan.model;

/**
 * Author:      cyb
 * Date:        2017/8/30
 * Description:
 */
public class BindCardInfoReq {

    // 借款用户ID
    private Integer lnUserId;

    private String mobile;

    private String bankCode;

    private String bankCard;

    private String name;

    private String idCard;

    private String partnerCode;

    private String ucUserType;

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

    public Integer getLnUserId() {
        return lnUserId;
    }

    public void setLnUserId(Integer lnUserId) {
        this.lnUserId = lnUserId;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }

    public String getBankCard() {
        return bankCard;
    }

    public void setBankCard(String bankCard) {
        this.bankCard = bankCard;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }
}
