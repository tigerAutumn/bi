package com.pinting.gateway.bird.in.model;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * Created by 剑钊 on 2016/8/10.
 * 解绑卡
 */
public class BankCardUnBindReq extends BaseReqModel {

    /**
     * 绑卡编号
     */
    @NotEmpty(message = "绑卡编号不能为空")
    private String bindId;

    public String getBindId() {
        return bindId;
    }

    public void setBindId(String bindId) {
        this.bindId = bindId;
    }
}
