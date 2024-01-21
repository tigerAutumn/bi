package com.pinting.mall.model;

import java.util.Date;

public class MallExchangeOrders {
    private Integer id; // 积分商城订单表ID

    private String orderNo; // 订单号

    private Integer userId; // 用户编号ID

    private Integer commId; // 商品编号ID

    private Long payPoints; // 交易积分

    private Integer buyNum; // 兑换数量

    private String orderStatus; // 订单状态：            PROCESS-支付处理中           SUCCESS-支付成功            FAIL-支付失败           FINISHED-已完成

    private String orderNote; // 备注

    private Date createTime; // 创建时间

    private Date updateTime; // 更新时间

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getCommId() {
        return commId;
    }

    public void setCommId(Integer commId) {
        this.commId = commId;
    }

    public Long getPayPoints() {
        return payPoints;
    }

    public void setPayPoints(Long payPoints) {
        this.payPoints = payPoints;
    }

    public Integer getBuyNum() {
        return buyNum;
    }

    public void setBuyNum(Integer buyNum) {
        this.buyNum = buyNum;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getOrderNote() {
        return orderNote;
    }

    public void setOrderNote(String orderNote) {
        this.orderNote = orderNote;
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