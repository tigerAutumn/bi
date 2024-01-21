package com.pinting.gateway.bird.in.model;

/**
 * @title 还款代扣请求
 * Created by 剑钊 on 2016/12/29.
 */
public class WithholdingRepayRes extends BaseResModel{


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
