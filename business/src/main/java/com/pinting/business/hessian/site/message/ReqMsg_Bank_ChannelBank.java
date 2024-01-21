package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ReqMsg;

/**
 * Author:      cyb
 * Date:        2016/9/2
 * Description: 根据渠道和支付类型查询银行信息的请求信息
 */
public class ReqMsg_Bank_ChannelBank extends ReqMsg {

    private static final long serialVersionUID = -3439299708679319415L;

    /* 支付渠道 PAY19-19付；REAPAL-融宝；BAOFOO-宝付 */
    private String payChannel;

    /* 支付类型 1-快捷 2-代付 3-代扣 4-网银 */
    private int payType;

    public String getPayChannel() {
        return payChannel;
    }

    public void setPayChannel(String payChannel) {
        this.payChannel = payChannel;
    }

    public int getPayType() {
        return payType;
    }

    public void setPayType(int payType) {
        this.payType = payType;
    }
}
