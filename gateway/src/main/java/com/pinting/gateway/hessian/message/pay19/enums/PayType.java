/**
 * Wenzi.com Inc.
 * Copyright (c) 2015-2025 All Rights Reserved.
 */
package com.pinting.gateway.hessian.message.pay19.enums;

/**
 * 
 * @author Baby shark love blowing wind
 * @version $Id: PayType.java, v 0.1 2015-7-28 下午4:38:01 BabyShark Exp $
 */
public enum PayType {
    /** */
    REALTIME("REALTIME", "实时"),
    /** */
    QUICK("QUICK", "快速(3小时)"),
    /** */
    COMMON("COMMON", "普通(T+1)"), ;

    /** code */
    private String code;

    /** description */
    private String description;

    /**
     * 私有的构造方法
     * @param code
     * @param description
     */
    private PayType(String code, String description) {
        this.code = code;
        this.description = description;
    }

    /**
     * 
     * @param code
     * @return {@link PayType} 实例
     */
    public static PayType find(String code) {
        for (PayType transCode : PayType.values()) {
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
