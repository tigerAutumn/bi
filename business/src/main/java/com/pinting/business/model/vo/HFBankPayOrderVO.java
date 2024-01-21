package com.pinting.business.model.vo;

import java.util.Date;

/**
 * 财务需求-恒丰系统充值/提现/划账 VO
 * Created by shh on 2017/4/19.
 */
public class HFBankPayOrderVO extends PageInfoObject {

    /* 订单号 */
    private String orderNo;

    /* 创建时间 */
    private Date createTime;

    /* 交易时间 */
    private Date updateTime;

    /* 交易金额 */
    private Double amount;

    /* 交易类型 */
    private String transType;

    /* 备注信息 */
    private String note;

    /* 订单状态 */
    private Integer status;

    /* 操作人 */
    private String userName;

    /* 转出账户 */
    private String transferOutAccount;

    /* 转入账户 */
    private String transferInAccount;

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getTransType() {
        return transType;
    }

    public void setTransType(String transType) {
        this.transType = transType;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getTransferOutAccount() {
        return transferOutAccount;
    }

    public void setTransferOutAccount(String transferOutAccount) {
        this.transferOutAccount = transferOutAccount;
    }

    public String getTransferInAccount() {
        return transferInAccount;
    }

    public void setTransferInAccount(String transferInAccount) {
        this.transferInAccount = transferInAccount;
    }
}
