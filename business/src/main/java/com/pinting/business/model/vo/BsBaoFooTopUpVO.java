package com.pinting.business.model.vo;

/**
 * Created by 剑钊 on 2016/8/5.
 * 预充值
 */
public class BsBaoFooTopUpVO {

    /**
     * 短信验证码
     */
    private String smsCode;

    private String transId;

    public String getSmsCode() {
        return smsCode;
    }

    public void setSmsCode(String smsCode) {
        this.smsCode = smsCode;
    }

    public String getTransId() {
        return transId;
    }

    public void setTransId(String transId) {
        this.transId = transId;
    }
}
