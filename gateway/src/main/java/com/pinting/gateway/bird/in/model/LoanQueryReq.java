package com.pinting.gateway.bird.in.model;

public class LoanQueryReq extends BaseReqModel {

    /**
     * 借款订单号
     */
    private String orderNo;

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }
}
