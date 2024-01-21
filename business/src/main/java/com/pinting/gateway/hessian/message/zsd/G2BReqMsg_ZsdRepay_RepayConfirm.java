package com.pinting.gateway.hessian.message.zsd;

import com.pinting.core.hessian.msg.ReqMsg;

/**
 * Created by zhangbao on 2017/8/30.
 */
public class G2BReqMsg_ZsdRepay_RepayConfirm extends ReqMsg{
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
