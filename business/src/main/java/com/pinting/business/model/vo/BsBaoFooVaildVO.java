package com.pinting.business.model.vo;

/**
 * Created by 剑钊 on 2016/8/5.
 * 预充值
 */
public class BsBaoFooVaildVO {

    /**
     * 短信验证码
     */
    private String smsCode;

    private String mobile;

    private String accNo;

    public String getSmsCode() {
        return smsCode;
    }

    public void setSmsCode(String smsCode) {
        this.smsCode = smsCode;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getAccNo() {
        return accNo;
    }

    public void setAccNo(String accNo) {
        this.accNo = accNo;
    }
}
