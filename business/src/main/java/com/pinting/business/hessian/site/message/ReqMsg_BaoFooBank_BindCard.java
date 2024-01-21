package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ReqMsg;

/**
 * Author:      cyb
 * Date:        2016/8/23
 * Description: 宝付正式下单请求信息
 */
public class ReqMsg_BaoFooBank_BindCard extends ReqMsg {

    private static final long serialVersionUID = 2526488189373173354L;

    /* 绑卡订单号 */
    private String orderNo;

    /* 验证码 */
    private String smsCode;

    /* 用户ID */
    private String userId;

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getSmsCode() {
        return smsCode;
    }

    public void setSmsCode(String smsCode) {
        this.smsCode = smsCode;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
