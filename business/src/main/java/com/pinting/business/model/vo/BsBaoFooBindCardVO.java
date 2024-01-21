package com.pinting.business.model.vo;

/**
 * Created by 剑钊 on 2016/8/5.
 */
public class BsBaoFooBindCardVO {

    /**
     * 短信验证码
     */
    private String  smsCode;

    /**
     * 银行卡号
     */
    private String accNo;


    public String getAccNo() {
        return accNo;
    }

    public void setAccNo(String accNo) {
        this.accNo = accNo;
    }

    public String getSmsCode() {
        return smsCode;
    }

    public void setSmsCode(String smsCode) {
        this.smsCode = smsCode;
    }
}
