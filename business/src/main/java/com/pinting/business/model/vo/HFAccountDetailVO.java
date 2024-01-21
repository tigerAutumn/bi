package com.pinting.business.model.vo;

/**
 * Author:      cyb
 * Date:        2017/5/11
 * Description: 恒丰出入金对账文件明细
 */
public class HFAccountDetailVO {

    /* 平台编号 */
    private String platNo;

    /* 对账日期 */
    private String checkDate;

    /* 时间 */
    private String checkTime;

    /* 请求订单号 */
    private String orderNo;

    /* 交易金额 */
    private Double amount;

    /* 类型 C-充值；T-提现 */
    private String type;

    /* 渠道编号 */
    private String channelNo;

    /* 渠道流水号 */
    private String channelJnlNo;

    /* 行内支付通道号 */
    private String payPathNo;

    public String getPayPathNo() {
        return payPathNo;
    }

    public void setPayPathNo(String payPathNo) {
        this.payPathNo = payPathNo;
    }

    public String getPlatNo() {
        return platNo;
    }

    public void setPlatNo(String platNo) {
        this.platNo = platNo;
    }

    public String getCheckDate() {
        return checkDate;
    }

    public void setCheckDate(String checkDate) {
        this.checkDate = checkDate;
    }

    public String getCheckTime() {
        return checkTime;
    }

    public void setCheckTime(String checkTime) {
        this.checkTime = checkTime;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getChannelNo() {
        return channelNo;
    }

    public void setChannelNo(String channelNo) {
        this.channelNo = channelNo;
    }

    public String getChannelJnlNo() {
        return channelJnlNo;
    }

    public void setChannelJnlNo(String channelJnlNo) {
        this.channelJnlNo = channelJnlNo;
    }
}
