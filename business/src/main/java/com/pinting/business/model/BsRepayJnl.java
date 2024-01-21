package com.pinting.business.model;

import java.util.Date;

public class BsRepayJnl {
    private Integer id;

    private String customerId;

    private String borrowNo;

    private String repayerName;

    private String repayerIdCard;

    private Date repayTime;

    private Double repayPrincipal;

    private String repayTransNo;

    private Date createTime;

    private Date updateTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getBorrowNo() {
        return borrowNo;
    }

    public void setBorrowNo(String borrowNo) {
        this.borrowNo = borrowNo;
    }

    public String getRepayerName() {
        return repayerName;
    }

    public void setRepayerName(String repayerName) {
        this.repayerName = repayerName;
    }

    public String getRepayerIdCard() {
        return repayerIdCard;
    }

    public void setRepayerIdCard(String repayerIdCard) {
        this.repayerIdCard = repayerIdCard;
    }

    public Date getRepayTime() {
        return repayTime;
    }

    public void setRepayTime(Date repayTime) {
        this.repayTime = repayTime;
    }

    public Double getRepayPrincipal() {
        return repayPrincipal;
    }

    public void setRepayPrincipal(Double repayPrincipal) {
        this.repayPrincipal = repayPrincipal;
    }

    public String getRepayTransNo() {
        return repayTransNo;
    }

    public void setRepayTransNo(String repayTransNo) {
        this.repayTransNo = repayTransNo;
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