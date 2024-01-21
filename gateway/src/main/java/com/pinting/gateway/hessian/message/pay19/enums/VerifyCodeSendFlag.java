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
public enum VerifyCodeSendFlag {
    /** 发送新验证码*/
    DEBITCARD("0", "发送新验证码"),
    /** 原验证码重新发送*/
    CREDITCARD("1", "原验证码重新发送"), ;

    /** code */
    private String code;

    /** description */
    private String description;

    /**
     * 私有的构造方法
     * @param code
     * @param description
     */
    private VerifyCodeSendFlag(String code, String description) {
        this.code = code;
        this.description = description;
    }

    /**
     * 
     * @param code
     * @return {@link VerifyCodeSendFlag} 实例
     */
    public static VerifyCodeSendFlag find(String code) {
        for (VerifyCodeSendFlag transCode : VerifyCodeSendFlag.values()) {
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
