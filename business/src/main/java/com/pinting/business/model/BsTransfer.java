package com.pinting.business.model;

import java.util.Date;

public class BsTransfer {
    private Integer id;

    private Integer userId1;

    private Integer userId2;

    private Integer productId;

    private Integer subAccountId1;

    private Integer subAccountId2;

    private Double price;

    private Double worth;

    private Double oldRate;

    private Double realRate;

    private Integer status;

    private Date distributeTime;

    private Date dealTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId1() {
        return userId1;
    }

    public void setUserId1(Integer userId1) {
        this.userId1 = userId1;
    }

    public Integer getUserId2() {
        return userId2;
    }

    public void setUserId2(Integer userId2) {
        this.userId2 = userId2;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Integer getSubAccountId1() {
        return subAccountId1;
    }

    public void setSubAccountId1(Integer subAccountId1) {
        this.subAccountId1 = subAccountId1;
    }

    public Integer getSubAccountId2() {
        return subAccountId2;
    }

    public void setSubAccountId2(Integer subAccountId2) {
        this.subAccountId2 = subAccountId2;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getWorth() {
        return worth;
    }

    public void setWorth(Double worth) {
        this.worth = worth;
    }

    public Double getOldRate() {
        return oldRate;
    }

    public void setOldRate(Double oldRate) {
        this.oldRate = oldRate;
    }

    public Double getRealRate() {
        return realRate;
    }

    public void setRealRate(Double realRate) {
        this.realRate = realRate;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getDistributeTime() {
        return distributeTime;
    }

    public void setDistributeTime(Date distributeTime) {
        this.distributeTime = distributeTime;
    }

    public Date getDealTime() {
        return dealTime;
    }

    public void setDealTime(Date dealTime) {
        this.dealTime = dealTime;
    }
}