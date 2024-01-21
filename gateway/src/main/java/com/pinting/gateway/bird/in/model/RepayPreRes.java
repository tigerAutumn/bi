package com.pinting.gateway.bird.in.model;

/**
 * Created by 剑钊 on 2016/8/10.
 * 还款预下单
 */
public class RepayPreRes extends BaseResModel {

    /**
     * 本次支付的交易号
     */
    private String bgwOrderNo;


    public String getBgwOrderNo() {
        return bgwOrderNo;
    }

    public void setBgwOrderNo(String bgwOrderNo) {
        this.bgwOrderNo = bgwOrderNo;
    }
}
