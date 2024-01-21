package com.pinting.gateway.hessian.message.zsd;

import com.pinting.core.hessian.msg.ReqMsg;

/**
 * Created by zhangbao on 2017/8/30.
 */
public class G2BReqMsg_ZsdRepay_QueryRepayResult extends ReqMsg {

    private String orderNo; // 还款订单编号

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }
}
