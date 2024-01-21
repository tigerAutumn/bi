package com.pinting.business.model.vo;

import java.util.Date;

/**
 * Created by 剑钊 on 2016/8/30.
 */
public class LoanNoticeVO {

    private String payOrderNo;

    private String channel;

    /**
     * 渠道交易类型
     * 例如：认证支付/代付
     */
    private String channelTransType;

    /**
     * 交易类型
     * 例如 绑卡/借款/还款
     */
    private String transType;

    private Integer status;

    /**
     * 金额
     */
    private String amount;

    private String returnCode;

    private String returnMsg;

    /**
     * 交易结束时间
     */
    private Date finishTime;

    /**
     * ZAN
     */
    private String clientKey;

    public String getPayOrderNo() {
        return payOrderNo;
    }

    public void setPayOrderNo(String payOrderNo) {
        this.payOrderNo = payOrderNo;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getChannelTransType() {
        return channelTransType;
    }

    public void setChannelTransType(String channelTransType) {
        this.channelTransType = channelTransType;
    }

    public String getTransType() {
        return transType;
    }

    public void setTransType(String transType) {
        this.transType = transType;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getReturnCode() {
        return returnCode;
    }

    public void setReturnCode(String returnCode) {
        this.returnCode = returnCode;
    }

    public String getReturnMsg() {
        return returnMsg;
    }

    public void setReturnMsg(String returnMsg) {
        this.returnMsg = returnMsg;
    }

    public Date getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(Date finishTime) {
        this.finishTime = finishTime;
    }

    public String getClientKey() {
        return clientKey;
    }

    public void setClientKey(String clientKey) {
        this.clientKey = clientKey;
    }
}
