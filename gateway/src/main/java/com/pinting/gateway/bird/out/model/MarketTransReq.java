package com.pinting.gateway.bird.out.model;

/**
 * Created by 剑钊
 *
 * @2016/10/18 23:36.
 */
public class MarketTransReq extends BaseReqModel{

    /**
     * 借款订单号
     */
    private String orderNo;

    /**
     * 资金通道  目前为BAOFOO
     */
    private String channel;

    /**
     * 借款结果编码
     */
    private String resultCode;

    /**
     * 借款结果信息
     */
    private String resultMsg;

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

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
}
