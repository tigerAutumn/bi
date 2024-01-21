package com.pinting.mall.model.vo;

import java.util.Date;

/**
 * 积分商城商品兑换vo
 *
 * @author shh
 * @date 2018/5/16 10:53
 * @Copyright: 2018 BiGangWan.com Inc. All rights reserved.
 */
public class MallExchangeVO {

    /* 商品主图 */
    private String commPictureUrl;

    /* 商品名称 */
    private String commName;

    /* 兑换时间 */
    private Date exchangeTime;

    /* 发货状态 */
    private String sendStatus;

    /* 商品编号 */
    private Integer commId;

    /* 支出积分 */
    private Long payPoints;

    /* 收货人姓名 */
    private String recName;

    /* 收货人电话 */
    private String recMobile;

    /* 收货人详细地址 */
    private String recAdressDetail;

    /* 订单状态 */
    private String orderStatus;

    /* 发货时间 */
    private Date sendCommodityTime;

    /* 发货信息 */
    private String deliveryNote;

    /* 积分商城订单表id */
    private Integer orderId;

    /* 商品属性 */
    private String commProperty;

    /* 收货人地址省市区 */
    private String recAdress;

    /* 注册手机号 */
    private String mobile;

    /* 用户姓名 */
    private String userName;

    public String getCommPictureUrl() {
        return commPictureUrl;
    }

    public void setCommPictureUrl(String commPictureUrl) {
        this.commPictureUrl = commPictureUrl;
    }

    public String getCommName() {
        return commName;
    }

    public void setCommName(String commName) {
        this.commName = commName;
    }

    public Date getExchangeTime() {
        return exchangeTime;
    }

    public void setExchangeTime(Date exchangeTime) {
        this.exchangeTime = exchangeTime;
    }

    public String getSendStatus() {
        return sendStatus;
    }

    public void setSendStatus(String sendStatus) {
        this.sendStatus = sendStatus;
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

    public String getRecName() {
        return recName;
    }

    public void setRecName(String recName) {
        this.recName = recName;
    }

    public String getRecMobile() {
        return recMobile;
    }

    public void setRecMobile(String recMobile) {
        this.recMobile = recMobile;
    }

    public String getRecAdressDetail() {
        return recAdressDetail;
    }

    public void setRecAdressDetail(String recAdressDetail) {
        this.recAdressDetail = recAdressDetail;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Date getSendCommodityTime() {
        return sendCommodityTime;
    }

    public void setSendCommodityTime(Date sendCommodityTime) {
        this.sendCommodityTime = sendCommodityTime;
    }

    public String getDeliveryNote() {
        return deliveryNote;
    }

    public void setDeliveryNote(String deliveryNote) {
        this.deliveryNote = deliveryNote;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public String getCommProperty() {
        return commProperty;
    }

    public void setCommProperty(String commProperty) {
        this.commProperty = commProperty;
    }

    public String getRecAdress() {
        return recAdress;
    }

    public void setRecAdress(String recAdress) {
        this.recAdress = recAdress;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
