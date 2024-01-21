package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ResMsg;

/**
 * Author:      cyb
 * Date:        2017/4/14
 * Description:
 */
public class ResMsg_Bonus_Withdraw extends ResMsg {

    private static final long serialVersionUID = 715264006114514040L;

    private String orderNo; //订单号

    private String time;// 预计到账时间

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }
}
