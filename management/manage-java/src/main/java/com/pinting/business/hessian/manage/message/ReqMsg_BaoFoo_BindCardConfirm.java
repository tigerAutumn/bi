package com.pinting.business.hessian.manage.message;

import com.pinting.core.hessian.msg.ReqMsg;

/**
 * Created by 剑钊 on 2016/8/3.
 */
public class ReqMsg_BaoFoo_BindCardConfirm extends ReqMsg {


    /**
     * 短信验证码
     */
    private String  smsCode;

    /**
     * 银行卡号
     */
    private String accNo;


    public String getSmsCode() {
        return smsCode;
    }

    public void setSmsCode(String smsCode) {
        this.smsCode = smsCode;
    }

    public String getAccNo() {
        return accNo;
    }

    public void setAccNo(String accNo) {
        this.accNo = accNo;
    }
}
