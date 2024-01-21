package com.pinting.mall.model.manange;

import com.pinting.mall.enums.MallInfoStatusEnum;
import com.pinting.mall.enums.MallPropertyEnum;
import com.pinting.mall.model.MallCommodityInfo;

public class MallCommodityInfoVO extends MallCommodityInfo {

    // 查询条件
    private String commNameSearch; // 商品名称

    private Integer commTypeIdSearch; // 商品类别

    private Integer leftCountMinSearch; // 剩余库存最小值

    private Integer leftCountMaxSearch; // 剩余库存最大值

    private String statusSearch = MallInfoStatusEnum.FOR_SALE.getCode(); // 状态

    // 展示数据
    private String commTypeName; // 商品类别名称

    private String commPropertyDesc; // 商品属性描述

    private String statusDesc; // 状态描述



    public String getCommNameSearch() {
        return commNameSearch;
    }

    public void setCommNameSearch(String commNameSearch) {
        this.commNameSearch = commNameSearch;
    }

    public Integer getCommTypeIdSearch() {
        return commTypeIdSearch;
    }

    public void setCommTypeIdSearch(Integer commTypeIdSearch) {
        this.commTypeIdSearch = commTypeIdSearch;
    }

    public Integer getLeftCountMinSearch() {
        return leftCountMinSearch;
    }

    public void setLeftCountMinSearch(Integer leftCountMinSearch) {
        this.leftCountMinSearch = leftCountMinSearch;
    }

    public Integer getLeftCountMaxSearch() {
        return leftCountMaxSearch;
    }

    public void setLeftCountMaxSearch(Integer leftCountMaxSearch) {
        this.leftCountMaxSearch = leftCountMaxSearch;
    }

    public String getStatusSearch() {
        return statusSearch;
    }

    public void setStatusSearch(String statusSearch) {
        this.statusSearch = statusSearch;
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