package com.pinting.mall.model;

import java.util.Date;

public class MallCommodityInfo {
    private Integer id; // 商品信息表ID

    private String commName; // 商品名称

    private Integer commTypeId; // 商品类别ID

    private String commProperty; // 商品属性  虚：EMPTY            实：REAL

    private Long points; // 所需积分

    private Integer leftCount; // 剩余库存

    private String isRecommend; // 是否推荐  是：YES           否：NO

    private String commPictureUrl; // 商品主图地址

    private Integer soldCount; // 已售量

    private String status; // 状态 上架：FOR_SALE  下架：SOLD_OUT

    private Integer creator; // 创建人

    private Integer lastOperator; // 最后操作人

    private Date forSaleTime; // 最近一次上架时间

    private Date createTime; // 创建时间

    private Date updateTime; // 更新时间

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

    public Integer getCommTypeId() {
        return commTypeId;
    }

    public void setCommTypeId(Integer commTypeId) {
        this.commTypeId = commTypeId;
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

    public Integer getCreator() {
        return creator;
    }

    public void setCreator(Integer creator) {
        this.creator = creator;
    }

    public Integer getLastOperator() {
        return lastOperator;
    }

    public void setLastOperator(Integer lastOperator) {
        this.lastOperator = lastOperator;
    }

    public Date getForSaleTime() {
        return forSaleTime;
    }

    public void setForSaleTime(Date forSaleTime) {
        this.forSaleTime = forSaleTime;
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