package com.pinting.gateway.zsd.in.model;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * Created by zhangbao on 2017/8/30.
 */
public class ZsdRepayReq extends BaseReqModel {
    /**
     * 本次支付的交易号
     */
    @NotEmpty(message = "订单号不能为空")
    private String bgwOrderNo;

    /**
     * 短信验证码
     */
    @NotEmpty(message = "短信验证码不能为空")
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
