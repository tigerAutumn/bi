package com.pinting.gateway.baofoo.out.model.req;

/**
 * Created by 剑钊 on 2016/7/23.
 */
public class PcStatusQueryReq extends BaoFooOutBaseReq {

    /**
     * 商户订单号
     */
    private String transID;

    public String getTransID() {
        return transID;
    }

    public void setTransID(String transID) {
        this.transID = transID;
    }
}
