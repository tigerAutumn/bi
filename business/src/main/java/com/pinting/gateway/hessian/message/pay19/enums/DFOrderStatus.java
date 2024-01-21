/**
 * Wenzi.com Inc.
 * Copyright (c) 2015-2025 All Rights Reserved.
 */
package com.pinting.gateway.hessian.message.pay19.enums;

/**
 * 
 * @author Baby shark love blowing wind
 * @version $Id: DFOrderStatus.java, v 0.1 2015-7-28 下午4:38:01 BabyShark Exp $
 */
public enum DFOrderStatus {
    /** 交易成功*/
    SUCCESS("SUCCESS", "交易成功"),
    /** 交易失败*/
    FAIL("FAIL", "交易失败"),
    /** 处理中*/
    PROCESS("PROCESS", "处理中"),
    /** 待审核*/
    WA_CHECK("WA_CHECK", "待审核"), ;

    /** code */
    private String code;

    /** description */
    private String description;

    /**
     * 私有的构造方法
     * @param code
     * @param description
     */
    private DFOrderStatus(String code, String description) {
        this.code = code;
        this.description = description;
    }

    /**
     * 
     * @param code
     * @return {@link DFOrderStatus} 实例
     */
    public static DFOrderStatus find(String code) {
        for (DFOrderStatus transCode : DFOrderStatus.values()) {
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
