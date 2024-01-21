/**
 * Wenzi.com Inc.
 * Copyright (c) 2015-2025 All Rights Reserved.
 */
package com.pinting.gateway.hessian.message.pay19.enums;

/**
 * 
 * @author Baby shark love blowing wind
 * @version $Id: GoodsType.java, v 0.1 2015-7-28 下午4:38:01 BabyShark Exp $
 */
public enum GoodsType {
    /** 实物商品*/
    PHYSICAL_GOODS("1", "实物商品"),
    /** 虚拟商品*/
    VIRTUAL_GOODS("2", "虚拟商品"),
    /** 虚拟账户*/
    VIRTUAL_ACCOUNT("3", "虚拟账户"), ;

    /** code */
    private String code;

    /** description */
    private String description;

    /**
     * 私有的构造方法
     * @param code
     * @param description
     */
    private GoodsType(String code, String description) {
        this.code = code;
        this.description = description;
    }

    /**
     * 
     * @param code
     * @return {@link GoodsType} 实例
     */
    public static GoodsType find(String code) {
        for (GoodsType transCode : GoodsType.values()) {
            if (transCode.getCode().equals(code)) {
                return transCode;
            }
        }
        return null;
    }

    public String getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }
}
