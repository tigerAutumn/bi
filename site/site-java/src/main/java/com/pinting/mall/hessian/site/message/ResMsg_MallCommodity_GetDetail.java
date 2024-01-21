package com.pinting.mall.hessian.site.message;

import com.pinting.core.hessian.msg.ResMsg;

public class ResMsg_MallCommodity_GetDetail extends ResMsg {

    private static final long serialVersionUID = 1L;

    private Integer id; // 商品信息表ID

    private String commName; // 商品名称

    private String commProperty; // 商品属性 虚：EMPTY            实：REAL

    private Long points; // 所需积分

    private Integer leftCount; // 剩余库存

    private String isRecommend; // 是否推荐  是：YES           否：NO

    private String commPictureUrl; // 商品主图地址

    private Integer soldCount; // 已售量

    private String status; // 状态 上架：FOR_SALE  下架：SOLD_OUT

    private String commDetails; // 商品介绍

    private String exchangeNote; // 兑换需知

    public String getCommDetails() {
        return commDetails;
    }

    public void setCommDetails(String commDetails) {
        this.commDetails = commDetails;
    }

    public String getExchangeNote() {
        return exchangeNote;
    }

    public void setExchangeNote(String exchangeNote) {
        this.exchangeNote = exchangeNote;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCommName() {
        return commName;
    }

    public void setCommName(String commName) {
        this.commName = commName;
    }

    public String getCommProperty() {
        return commProperty;
    }

    public void setCommProperty(String commProperty) {
        this.commProperty = commProperty;
    }

    public Long getPoints() {
        return points;
    }

    public void setPoints(Long points) {
        this.points = points;
    }

    public Integer getLeftCount() {
        return leftCount;
    }

    public void setLeftCount(Integer leftCount) {
        this.leftCount = leftCount;
    }

    public String getIsRecommend() {
        return isRecommend;
    }

    public void setIsRecommend(String isRecommend) {
        this.isRecommend = isRecommend;
    }

    public String getCommPictureUrl() {
        return commPictureUrl;
    }

    public void setCommPictureUrl(String commPictureUrl) {
        this.commPictureUrl = commPictureUrl;
    }

    public Integer getSoldCount() {
        return soldCount;
    }

    public void setSoldCount(Integer soldCount) {
        this.soldCount = soldCount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
