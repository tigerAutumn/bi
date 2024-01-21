package com.pinting.gateway.bird.in.model;

/**
 * Created by 剑钊 on 2016/8/10.
 * 还款状态查询
 */
public class RepayQueryReq extends BaseReqModel {

    /**
     * 还款订单编号
     */
    private String orderNo;

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }
}
