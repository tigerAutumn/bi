package com.pinting.business.model.vo;

import java.util.Date;

/**
 * Author:      shh
 * Date:        2017/10/23
 * Description: 恒丰客户支取统计
 */
public class HfbankCustomerWithdrawalVO {

    /* 查询结果序号 */
    private Integer rowno;

    public String orderNo;

    /* 客户代码 */
    public String customerCode;

    public String userName;

    /* 支取金额 */
    public Double amount;

    /* 提现手续费 */
    private Double doneFee;

    /* 客户实际到账 */
    private Double revenueAmount;

    /* 支取平台 */
    private String channel;

    /* 支取成功的时间 */
    private Date updateTime;

    /* 支取类型奖励金提现/余额提现 */
    private String transType;

    public Integer getRowno() {
        return rowno;
    }

    public void setRowno(Integer rowno) {
        this.rowno = rowno;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getCustomerCode() {
        return customerCode;
    }

    public void setCustomerCode(String customerCode) {
        this.customerCode = customerCode;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Double getDoneFee() {
        return doneFee;
    }

    public void setDoneFee(Double doneFee) {
        this.doneFee = doneFee;
    }

    public Double getRevenueAmount() {
        return revenueAmount;
    }

    public void setRevenueAmount(Double revenueAmount) {
        this.revenueAmount = revenueAmount;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getTransType() {
        return transType;
    }

    public void setTransType(String transType) {
        this.transType = transType;
    }
}
