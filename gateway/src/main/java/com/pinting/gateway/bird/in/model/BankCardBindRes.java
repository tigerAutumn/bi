package com.pinting.gateway.bird.in.model;

/**
 * Created by 剑钊 on 2016/8/10.
 * 正式绑卡
 */
public class BankCardBindRes extends BaseResModel {

    /**
     *  绑卡编号
     */
    private String bindId;

    public String getBindId() {
        return bindId;
    }

    public void setBindId(String bindId) {
        this.bindId = bindId;
    }
}
