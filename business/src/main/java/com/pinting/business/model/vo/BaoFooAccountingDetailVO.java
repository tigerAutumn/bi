package com.pinting.business.model.vo;

/**
 * Created by 剑钊
 * 宝付对账交易详情
 * @2016/9/9 14:50.
 */
public class BaoFooAccountingDetailVO {

    /**
     * 商户号
     */
    private String memberId;

    /**
     * 终端号
     */
    private String terminalId;

    /**
     * 交易类型
     */
    private String txnType;

    /**
     * 交易子类型
     */
    private String txnSubType;

    /**
     * 宝付订单号
     */
    private String baofooOrderNo;

    /**
     * 我方支付订单号
     */
    private String payOrderNo;

    /**
     * 清算日期
     */
    private String liquidationDate;

    /**
     * 订单状态
     */
    private String orderStatus;

    /**
     * 交易金额
     */
    private String amount;

    /**
     * 手续费
     */
    private String serviceFee;

    /**
     * 支付订单创建时间
     */
    private String createTime;

    /**
     * 退款订单号
     */
    private String refundOrderNo;

    /**
     * 退款订单创建时间
     */
    private String refundCreateTime;

    public String getRefundOrderNo() {
        return refundOrderNo;
    }

    public void setRefundOrderNo(String refundOrderNo) {
        this.refundOrderNo = refundOrderNo;
    }

    public String getRefundCreateTime() {
        return refundCreateTime;
    }

    public void setRefundCreateTime(String refundCreateTime) {
        this.refundCreateTime = refundCreateTime;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getTerminalId() {
        return terminalId;
    }

    public void setTerminalId(String terminalId) {
        this.terminalId = terminalId;
    }

    public String getTxnType() {
        return txnType;
    }

    public void setTxnType(String txnType) {
        this.txnType = txnType;
    }

    public String getTxnSubType() {
        return txnSubType;
    }

    public void setTxnSubType(String txnSubType) {
        this.txnSubType = txnSubType;
    }

    public String getLiquidationDate() {
        return liquidationDate;
    }

    public void setLiquidationDate(String liquidationDate) {
        this.liquidationDate = liquidationDate;
    }

    public String getBaofooOrderNo() {
        return baofooOrderNo;
    }

    public void setBaofooOrderNo(String baofooOrderNo) {
        this.baofooOrderNo = baofooOrderNo;
    }

    public String getPayOrderNo() {
        return payOrderNo;
    }

    public void setPayOrderNo(String payOrderNo) {
        this.payOrderNo = payOrderNo;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getServiceFee() {
        return serviceFee;
    }

    public void setServiceFee(String serviceFee) {
        this.serviceFee = serviceFee;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
