package com.pinting.business.model;

import java.util.Date;

public class BsSysReceiveMoney {
    private Integer id;

    private String type;

    private String payOrderNo;

    private Date payReqTime;

    private Date payFinshTime;

    private Double amount;

    private String productOrderNo;

    private String productCode;

    private Double productAmount;

    private Double productInterest;

    private String productReturnTerm;

    private String status;

    private Date createTime;

    private Date updateTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPayOrderNo() {
        return payOrderNo;
    }

    public void setPayOrderNo(String payOrderNo) {
        this.payOrderNo = payOrderNo;
    }

    public Date getPayReqTime() {
        return payReqTime;
    }

    public void setPayReqTime(Date payReqTime) {
        this.payReqTime = payReqTime;
    }

    public Date getPayFinshTime() {
        return payFinshTime;
    }

    public void setPayFinshTime(Date payFinshTime) {
        this.payFinshTime = payFinshTime;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getProductOrderNo() {
        return productOrderNo;
    }

    public void setProductOrderNo(String productOrderNo) {
        this.productOrderNo = productOrderNo;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public Double getProductAmount() {
        return productAmount;
    }

    public void setProductAmount(Double productAmount) {
        this.productAmount = productAmount;
    }

    public Double getProductInterest() {
        return productInterest;
    }

    public void setProductInterest(Double productInterest) {
        this.productInterest = productInterest;
    }

    public String getProductReturnTerm() {
        return productReturnTerm;
    }

    public void setProductReturnTerm(String productReturnTerm) {
        this.productReturnTerm = productReturnTerm;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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
}