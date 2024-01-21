package com.pinting.business.model;

public class BsProductSubAccountPrefixKey {
    private Integer productId;

    private String subAccountPrefix;

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public String getSubAccountPrefix() {
        return subAccountPrefix;
    }

    public void setSubAccountPrefix(String subAccountPrefix) {
        this.subAccountPrefix = subAccountPrefix;
    }
}