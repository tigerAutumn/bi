package com.pinting.gateway.hessian.message.loan;

import com.pinting.core.hessian.msg.ReqMsg;

public class G2BReqMsg_Partner_QueryMarketingTrans extends ReqMsg {

    /**
     * 营销订单号
     */
    private String orderNo;


    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }
}
