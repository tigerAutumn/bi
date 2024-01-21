package com.pinting.business.model.vo;

/**
 * Created by 剑钊 on 2016/8/5.
 */
public class BsBaoFooPreBindCardVO {

    /**
     * 请求绑定的银行卡号
     *
     */
    private String accNo;

    /**
     * 银行卡绑定手机号
     *
     */
    private String mobile;

    /**
     * 身份证号
     */
    private String idCard;

    /**
     * 持卡人姓名
     */
    private String userName;

    /**
     * 银行编码
     */
    private String payCode;


    public String getAccNo() {
        return accNo;
    }

    public void setAccNo(String accNo) {
        this.accNo = accNo;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPayCode() {
        return payCode;
    }

    public void setPayCode(String payCode) {
        this.payCode = payCode;
    }
}
