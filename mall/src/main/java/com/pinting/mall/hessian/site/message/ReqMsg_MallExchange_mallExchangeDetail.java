package com.pinting.mall.hessian.site.message;

import com.pinting.core.hessian.msg.ReqMsg;

/**
 * 兑换详情
 *
 * @author shh
 * @date 2018/5/16 15:25
 * @Copyright: 2018 BiGangWan.com Inc. All rights reserved.
 */
public class ReqMsg_MallExchange_mallExchangeDetail extends ReqMsg {

    private static final long serialVersionUID = 5825094846941948088L;

    private Integer userIdParam;

    /* 商品编号 */
    private Integer commId;

    /* 积分商城订单表id */
    private Integer orderId;

    /* 商品属性：虚拟、实物 */
    private String commProperty;

    public Integer getUserIdParam() {
        return userIdParam;
    }

    public void setUserIdParam(Integer userIdParam) {
        this.userIdParam = userIdParam;
    }

    public Integer getCommId() {
        return commId;
    }

    public void setCommId(Integer commId) {
        this.commId = commId;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public String getCommProperty() {
        return commProperty;
    }

    public void setCommProperty(String commProperty) {
        this.commProperty = commProperty;
    }
}
