/**
 * Wenzi.com Inc.
 * Copyright (c) 2015-2025 All Rights Reserved.
 */
package com.pinting.gateway.hessian.message.pay19.enums;

/**
 * 
 * @author Baby shark love blowing wind
 * @version $Id: AcctAttr.java, v 0.1 2015-7-28 下午4:38:01 BabyShark Exp $
 */
public enum AcctAttr {
    /** 公*/
    PUBLIC("PUBLIC", "公"),
    /** 私*/
    PRIVATE("PRIVATE", "私"), ;

    /** code */
    private String code;

    /** description */
    private String description;

    /**
     * 私有的构造方法
     * @param code
     * @param description
     */
    private AcctAttr(String code, String description) {
        this.code = code;
        this.description = description;
    }

    /**
     * 
     * @param code
     * @return {@link AcctAttr} 实例
     */
    public static AcctAttr find(String code) {
        for (AcctAttr transCode : AcctAttr.values()) {
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
