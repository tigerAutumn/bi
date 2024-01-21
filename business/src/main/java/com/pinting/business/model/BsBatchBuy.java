package com.pinting.business.model;

import java.util.Date;

public class BsBatchBuy {
    private Integer id;

    private String productCode;

    private Double amount;

    private Double sendAmount;

    private Double receiveAmount;

    private Double dafyRate;

    private String sendBatchId;

    private String pay19OrderNo;

    private String pay19MpOrderNo;

    private String dafyPay19MpOrderNo;

    private String status;

    private Date sendTime;

    private Date expectTime;

    private Date returnTime;

    private Date financingDate;

    private String pay19ReturnCode;

    private String pay19ReturnMsg;

    private String dafyReturnCode;

    private String dafyReturnMsg;

    private Date createTime;

    private Date updateTime;

    private String propertySymbol;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Double getSendAmount() {
        return sendAmount;
    }

    public void setSendAmount(Double sendAmount) {
        this.sendAmount = sendAmount;
    }

    public Double getReceiveAmount() {
        return receiveAmount;
    }

    public void setReceiveAmount(Double receiveAmount) {
        this.receiveAmount = receiveAmount;
    }

    public Double getDafyRate() {
        return dafyRate;
    }

    public void setDafyRate(Double dafyRate) {
        this.dafyRate = dafyRate;
    }

    public String getSendBatchId() {
        return sendBatchId;
    }

    public void setSendBatchId(String sendBatchId) {
        this.sendBatchId = sendBatchId;
    }

    public String getPay19OrderNo() {
        return pay19OrderNo;
    }

    public void setPay19OrderNo(String pay19OrderNo) {
        this.pay19OrderNo = pay19OrderNo;
    }

    public String getPay19MpOrderNo() {
        return pay19MpOrderNo;
    }

    public void setPay19MpOrderNo(String pay19MpOrderNo) {
        this.pay19MpOrderNo = pay19MpOrderNo;
    }

    public String getDafyPay19MpOrderNo() {
        return dafyPay19MpOrderNo;
    }

    public void setDafyPay19MpOrderNo(String dafyPay19MpOrderNo) {
        this.dafyPay19MpOrderNo = dafyPay19MpOrderNo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getSendTime() {
        return sendTime;
    }

    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }

    public Date getExpectTime() {
        return expectTime;
    }

    public void setExpectTime(Date expectTime) {
        this.expectTime = expectTime;
    }

    public Date getReturnTime() {
        return returnTime;
    }

    public void setReturnTime(Date returnTime) {
        this.returnTime = returnTime;
    }

    public Date getFinancingDate() {
        return financingDate;
    }

    public void setFinancingDate(Date financingDate) {
        this.financingDate = financingDate;
    }

    public String getPay19ReturnCode() {
        return pay19ReturnCode;
    }

    public void setPay19ReturnCode(String pay19ReturnCode) {
        this.pay19ReturnCode = pay19ReturnCode;
    }

    public String getPay19ReturnMsg() {
        return pay19ReturnMsg;
    }

    public void setPay19ReturnMsg(String pay19ReturnMsg) {
        this.pay19ReturnMsg = pay19ReturnMsg;
    }

    public String getDafyReturnCode() {
        return dafyReturnCode;
    }

    public void setDafyReturnCode(String dafyReturnCode) {
        this.dafyReturnCode = dafyReturnCode;
    }

    public String getDafyReturnMsg() {
        return dafyReturnMsg;
    }

    public void setDafyReturnMsg(String dafyReturnMsg) {
        this.dafyReturnMsg = dafyReturnMsg;
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

    public String getPropertySymbol() {
        return propertySymbol;
    }

    public void setPropertySymbol(String propertySymbol) {
        this.propertySymbol = propertySymbol;
    }
}