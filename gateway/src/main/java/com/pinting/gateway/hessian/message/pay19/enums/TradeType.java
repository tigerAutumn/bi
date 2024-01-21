/**
 * Wenzi.com Inc.
 * Copyright (c) 2015-2025 All Rights Reserved.
 */
package com.pinting.gateway.hessian.message.pay19.enums;

/**
 * 
 * @author Baby shark love blowing wind
 * @version $Id: TradeType.java, v 0.1 2015-7-28 下午4:38:01 BabyShark Exp $
 */
public enum TradeType {
    /** 快捷支付*/
    QUICKPAY("QUICKPAY", "快捷支付"),
    /** 代付*/
    DF("DF", "代付"), 
    /** 网银*/
    E_BANK("E_BANK", "网银"),
    /** 直连网银*/
    D_BANK("D_BANK", "直连网银");

    /** code */
    private String code;

    /** description */
    private String description;

    /**
     * 私有的构造方法
     * @param code
     * @param description
     */
    private TradeType(String code, String description) {
        this.code = code;
        this.description = description;
    }

    /**
     * 
     * @param code
     * @return {@link TradeType} 实例
     */
    public static TradeType find(String code) {
        for (TradeType transCode : TradeType.values()) {
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
