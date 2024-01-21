package com.pinting.gateway.bird.in.model;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * Created by 剑钊
 * @title 营销代付请求
 * @2016/10/17 11:09.
 */
public class MarketingTransReq extends BaseReqModel {

    /**
     * 订单号
     */
    @NotEmpty(message = "营销订单号不能为空")
    private String orderNo;

    /**
     * 用户id
     */
    @NotEmpty(message = "用户编号不能为空")
    private String userId;

    /**
     * 金额
     */
    @NotEmpty(message = "打款金额不能为空")
    private String amount;

    /**
     * 绑卡编号
     */
    @NotEmpty(message = "绑卡编号不能为空")
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
