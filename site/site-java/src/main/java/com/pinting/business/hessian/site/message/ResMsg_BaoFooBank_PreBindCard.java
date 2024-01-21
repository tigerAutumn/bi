package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ResMsg;

/**
 * Author:      cyb
 * Date:        2016/8/23
 * Description: 宝付预下单响应信息
 */
public class ResMsg_BaoFooBank_PreBindCard extends ResMsg {

    private static final long serialVersionUID = -1874437609895640634L;

    /* 预绑卡订单号 */
    private String orderNo;

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

}
