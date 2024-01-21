/**
 * Wenzi.com Inc.
 * Copyright (c) 2015-2025 All Rights Reserved.
 */
package com.pinting.gateway.hessian.message.pay19.enums;

/**
 * 
 * @author Baby shark love blowing wind
 * @version $Id: AcctType.java, v 0.1 2015-7-28 下午4:38:01 BabyShark Exp $
 */
public enum TradeResult {
    /** 成功*/
    SUCCESS("SUCCESS", "成功"),
    /** 失败*/
    FAIL("FAIL", "失败"),
    /** 处理中*/
    PROCESS("PROCESS", "处理中"),
    /** 异常*/
    EXCEPTION("EXCEPTION", "异常"), ;

    /** code */
    private String code;

    /** description */
    private String description;

    /**
     * 私有的构造方法
     * @param code
     * @param description
     */
    private TradeResult(String code, String description) {
        this.code = code;
        this.description = description;
    }

    /**
     * 
     * @param code
     * @return {@link TradeResult} 实例
     */
    public static TradeResult find(String code) {
        for (TradeResult transCode : TradeResult.values()) {
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
