package com.pinting.business.hessian.manage.message;

/**
 * Created by 剑钊 on 2016/8/3.
 */
public class ReqMsg_BaoFoo_BindCard {

    /**
     * 请求绑定的银行卡号
     */
    private String accNo;

    /**
     * 身份证号
     */
    private String idCard;

    /**
     * 持卡人姓名
     */
    private String idHolder;

    /**
     * 银行卡绑定手机号
     */
    private String mobile;

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

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getIdHolder() {
        return idHolder;
    }

    public void setIdHolder(String idHolder) {
        this.idHolder = idHolder;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }


    public String getPayCode() {
        return payCode;
    }

    public void setPayCode(String payCode) {
        this.payCode = payCode;
    }
}
