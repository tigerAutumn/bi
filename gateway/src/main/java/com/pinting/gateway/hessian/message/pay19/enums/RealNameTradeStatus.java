/**
 * Wenzi.com Inc.
 * Copyright (c) 2015-2025 All Rights Reserved.
 */
package com.pinting.gateway.hessian.message.pay19.enums;

/**
 * 
 * @author Baby shark love blowing wind
 * @version $Id: RealNameTradeStatus.java, v 0.1 2015-7-28 下午4:38:01 BabyShark Exp $
 */
public enum RealNameTradeStatus {
    /** 匹配*/
    MATCH("MATCH", "匹配"),
    /** 不匹配*/
    UNMATCH("UNMATCH", "不匹配"),
    /** 未验证*/
    UNVERIFY("UNVERIFY", "未验证"), ;

    /** code */
    private String code;

    /** description */
    private String description;

    /**
     * 私有的构造方法
     * @param code
     * @param description
     */
    private RealNameTradeStatus(String code, String description) {
        this.code = code;
        this.description = description;
    }

    /**
     * 
     * @param code
     * @return {@link RealNameTradeStatus} 实例
     */
    public static RealNameTradeStatus find(String code) {
        for (RealNameTradeStatus transCode : RealNameTradeStatus.values()) {
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
