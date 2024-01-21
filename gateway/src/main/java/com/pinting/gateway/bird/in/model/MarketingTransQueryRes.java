package com.pinting.gateway.bird.in.model;

public class MarketingTransQueryRes extends BaseResModel {

    /**
     * 营销订单号
     */
    private String orderNo;

    /**
     * 资金通道  目前为BAOFOO
     */
    private String channel;

    /**
     * 结果编码
     */
    private String resultCode;

    /**
     * 结果信息
     */
    private String resultMsg;

    /**
     * 代付成功时间
     */
    private String payTime;


    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    public String getResultMsg() {
        return resultMsg;
    }

    public void setResultMsg(String resultMsg) {
        this.resultMsg = resultMsg;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getPayTime() {
        return payTime;
    }

    public void setPayTime(String payTime) {
        this.payTime = payTime;
    }
}
