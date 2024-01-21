/**
 * Wenzi.com Inc.
 * Copyright (c) 2015-2025 All Rights Reserved.
 */
package com.pinting.gateway.hessian.message.pay19.enums;

/**
 * 是否补全持卡人姓名和身份证号码
 * @author Baby shark love blowing wind
 * @version $Id: IsFixBindInfo.java, v 0.1 2015-7-28 下午4:38:01 BabyShark Exp $
 */
public enum IsFixBindInfo {
    /** 不需要补全*/
    NO_NEED_FIX("0", "不需要补全"),
    /** 需要补全*/
    NEED_FIX("1", "需要补全"), ;

    /** code */
    private String code;

    /** description */
    private String description;

    /**
     * 私有的构造方法
     * @param code
     * @param description
     */
    private IsFixBindInfo(String code, String description) {
        this.code = code;
        this.description = description;
    }

    /**
     * 
     * @param code
     * @return {@link IsFixBindInfo} 实例
     */
    public static IsFixBindInfo find(String code) {
        for (IsFixBindInfo transCode : IsFixBindInfo.values()) {
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
