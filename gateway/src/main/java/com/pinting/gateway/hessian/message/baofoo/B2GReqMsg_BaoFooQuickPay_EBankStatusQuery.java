package com.pinting.gateway.hessian.message.baofoo;

import com.pinting.core.hessian.msg.ReqMsg;

/**
 * Created by 剑钊 on 2016/8/3.
 */
public class B2GReqMsg_BaoFooQuickPay_EBankStatusQuery extends ReqMsg {


    /**
     * 订单号
     * 唯一订单号，8-20 位字母和数字宝付将以此作为结算的唯一凭证
     */
    private String transId;


    public String getTransId() {
        return transId;
    }

    public void setTransId(String transId) {
        this.transId = transId;
    }
}
