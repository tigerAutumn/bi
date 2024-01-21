/**
 * Wenzi.com Inc.
 * Copyright (c) 2015-2025 All Rights Reserved.
 */
package com.pinting.gateway.hessian.message.pay19.enums;

/**
 * 银行通道是否可用
 * @author Baby shark love blowing wind
 * @version $Id: ChanIfUse.java, v 0.1 2015-7-28 下午4:38:01 BabyShark Exp $
 */
public enum ChanIfUse {
    /** 不可用*/
    OPEN("OPEN", "不可用"),
    /** 可用*/
    CLOSE("CLOSE", "可用"), ;

    /** code */
    private String code;

    /** description */
    private String description;

    /**
     * 私有的构造方法
     * @param code
     * @param description
     */
    private ChanIfUse(String code, String description) {
        this.code = code;
        this.description = description;
    }

    /**
     * 
     * @param code
     * @return {@link ChanIfUse} 实例
     */
    public static ChanIfUse find(String code) {
        for (ChanIfUse transCode : ChanIfUse.values()) {
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
