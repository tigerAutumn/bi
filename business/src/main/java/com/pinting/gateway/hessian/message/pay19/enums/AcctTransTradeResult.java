/**
 * Wenzi.com Inc.
 * Copyright (c) 2015-2025 All Rights Reserved.
 */
package com.pinting.gateway.hessian.message.pay19.enums;

/**
 * 
 * @author Baby shark love blowing wind
 * @version $Id: AcctTransTradeResult.java, v 0.1 2015-7-28 下午4:38:01 BabyShark Exp $
 */
public enum AcctTransTradeResult {
    /** 成功*/
    Y("Y", "成功"),
    /** 失败*/
    F("F", "失败"),;

    /** code */
    private String code;

    /** description */
    private String description;

    /**
     * 私有的构造方法
     * @param code
     * @param description
     */
    private AcctTransTradeResult(String code, String description) {
        this.code = code;
        this.description = description;
    }

    /**
     * 
     * @param code
     * @return {@link AcctTransTradeResult} 实例
     */
    public static AcctTransTradeResult find(String code) {
        for (AcctTransTradeResult transCode : AcctTransTradeResult.values()) {
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
