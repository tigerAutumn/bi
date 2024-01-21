package com.pinting.gateway.hessian.message.loan;

import com.pinting.core.hessian.msg.ReqMsg;

public class G2BReqMsg_Repay_RepayConfirm extends ReqMsg {

    /**
     * 绑卡订单号
     */
    private String bgwOrderNo;

    /**
     * 短信验证码
     */
    private String smsCode;

    public String getBgwOrderNo() {
        return bgwOrderNo;
    }

    public void setBgwOrderNo(String bgwOrderNo) {
        this.bgwOrderNo = bgwOrderNo;
    }

    public String getSmsCode() {
        return smsCode;
    }

    public void setSmsCode(String smsCode) {
        this.smsCode = smsCode;
    }
}
