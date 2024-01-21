package com.pinting.mall.model;

import java.util.Date;

public class MallCommodityType {
    private Integer id;

    private String commTypeName;

    private String commTypeCode;

    private String isRecommend;

    private Integer creator;

    private Integer lastOperator;

    private Date createTime;

    private Date updateTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCommTypeName() {
        return commTypeName;
    }

    public void setCommTypeName(String commTypeName) {
        this.commTypeName = commTypeName;
    }

    public String getCommTypeCode() {
        return commTypeCode;
    }

    public void setCommTypeCode(String commTypeCode) {
        this.commTypeCode = commTypeCode;
    }

    public String getIsRecommend() {
        return isRecommend;
    }

    public void setIsRecommend(String isRecommend) {
        this.isRecommend = isRecommend;
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