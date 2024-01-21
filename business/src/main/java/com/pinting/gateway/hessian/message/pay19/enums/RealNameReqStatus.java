/**
 * Wenzi.com Inc.
 * Copyright (c) 2015-2025 All Rights Reserved.
 */
package com.pinting.gateway.hessian.message.pay19.enums;

/**
 * 
 * @author Baby shark love blowing wind
 * @version $Id: RealNameReqStatus.java, v 0.1 2015-7-28 下午4:38:01 BabyShark Exp $
 */
public enum RealNameReqStatus {
    /** 请求成功*/
    SUCCESS("SUCCESS", "请求成功"),
    /** 请求失败*/
    FAIL("FAIL", "请求失败"),
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
    private RealNameReqStatus(String code, String description) {
        this.code = code;
        this.description = description;
    }

    /**
     * 
     * @param code
     * @return {@link RealNameReqStatus} 实例
     */
    public static RealNameReqStatus find(String code) {
        for (RealNameReqStatus transCode : RealNameReqStatus.values()) {
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
