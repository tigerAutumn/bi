package com.pinting.mall.hessian.site.message;

import com.pinting.core.hessian.msg.ReqMsg;

/**
 * 商品兑换前校验
 */
public class ReqMsg_MallExchangeFlow_ExchangeCheck extends ReqMsg {

    private static final long serialVersionUID = 1L;

    private Integer id; // 商品信息表ID

    private Integer userId; // 用户编号ID

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
