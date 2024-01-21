/**
 * Wenzi.com Inc.
 * Copyright (c) 2015-2025 All Rights Reserved.
 */
package com.pinting.cfcatransfer.bigangwan.enums;

/**
 * 印章类型0：企业 1：个人
 * @author Baby shark love blowing wind
 * @version $Id: SealType.java, v 0.1 2015-7-28 下午4:38:01 BabyShark Exp $
 */
public enum SealType {
    /** 企业*/
    COMPANY("0", "企业"),
    /** 个人*/
    PERSON("1", "个人"), ;

    /** code */
    private String code;

    /** description */
    private String description;

    /**
     * 私有的构造方法
     * @param code
     * @param description
     */
    private SealType(String code, String description) {
        this.code = code;
        this.description = description;
    }

    /**
     * 
     * @param code
     * @return {@link SealType} 实例
     */
    public static SealType find(String code) {
        for (SealType transCode : SealType.values()) {
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
