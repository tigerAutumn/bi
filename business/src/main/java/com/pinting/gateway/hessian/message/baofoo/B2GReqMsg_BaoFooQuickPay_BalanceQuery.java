package com.pinting.gateway.hessian.message.baofoo;

import com.pinting.core.hessian.msg.ReqMsg;

/**
 * Created by 剑钊 on 2016/8/3.
 */
public class B2GReqMsg_BaoFooQuickPay_BalanceQuery extends ReqMsg {


    /**
     * 合作方编码
     */
    private String partner;


    public String getPartner() {
        return partner;
    }

    public void setPartner(String partner) {
        this.partner = partner;
    }
}
