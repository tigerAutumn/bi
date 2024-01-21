package com.pinting.mall.model.vo;

import com.pinting.mall.enums.MallInfoStatusEnum;
import com.pinting.mall.enums.MallPropertyEnum;
import com.pinting.mall.model.MallCommodityInfo;

public class MallCommodityInfoAndTypeVO extends MallCommodityInfo {

    // 展示数据
    private String commTypeCode; // 商品类别code

    private String commTypeName; // 商品类别名称

    private String commPropertyDesc; // 商品属性描述

    private String statusDesc; // 状态描述

    public String getCommTypeCode() {
        return commTypeCode;
    }

    public void setCommTypeCode(String commTypeCode) {
        this.commTypeCode = commTypeCode;
    }

    public String getCommTypeName() {
        return commTypeName;
    }

    public void setCommTypeName(String commTypeName) {
        this.commTypeName = commTypeName;
    }

    public String getStatusDesc() {
        MallInfoStatusEnum statusEnum = MallInfoStatusEnum.getEnumByCode(getStatus());
        return statusEnum != null ? statusEnum.getMessage() : "";
    }

    public void setStatusDesc(String statusDesc) {
        this.statusDesc = statusDesc;
    }

    public String getCommPropertyDesc() {
        MallPropertyEnum propertyEnum = MallPropertyEnum.getEnumByCode(getCommProperty());
        return propertyEnum != null ? propertyEnum.getMessage() : "";
    }

    public void setCommPropertyDesc(String commPropertyDesc) {
        this.commPropertyDesc = commPropertyDesc;
    }
}