package com.pinting.gateway.hessian.message.loan;

import com.pinting.core.hessian.msg.ReqMsg;

/**
 * Created by 剑钊
 *
 * @2016/10/17 11:38.
 */
public class G2BReqMsg_Partner_MarketingTrans extends ReqMsg {

    /**
     * 订单号
     */
    private String orderNo;

    /**
     * 用户id
     */
    private String userId;

    /**
     * 金额
     */
    private String amount;

    /**
     * 绑卡编号
     */
    private String bindId;

    /**
     * 营销内容备注
     */
    private String purpose;

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getBindId() {
        return bindId;
    }

    public void setBindId(String bindId) {
        this.bindId = bindId;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }
}
