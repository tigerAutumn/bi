package com.pinting.gateway.hfbank.in.model;

/**
 * 平台非存管账户出金异步通知请求信息
 * Created by shh on 2017/4/1.
 */
public class PlattransReqModel extends BaseReqModel {

    /* 金额 */
    private Double amt;
    /* 1-入账成功  2-入账失败 */
    private String code;

    public Double getAmt() {
        return amt;
    }

    public void setAmt(Double amt) {
        this.amt = amt;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
