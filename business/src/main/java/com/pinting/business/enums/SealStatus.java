/**
 * Wenzi.com Inc.
 * Copyright (c) 2015-2025 All Rights Reserved.
 */
package com.pinting.business.enums;

/**
 * 
 * @author Baby shark love blowing wind
 * @version $Id: SealStatus.java, v 0.1 2015-7-16 下午7:21:59 BabyShark Exp $
 */
public enum SealStatus {
    /** 签章失败 */
    FAIL("FAIL", "签章失败"),
    /** 签章成功 */
    SUCC("SUCC", "签章成功"),
    /** 待下载 */
    UNDOWNLOAD("UNDOWNLOAD", "待下载"),
    /** 待签章*/
    INIT("INIT", "待签章"),
    /**待上传 */
    FILE_CREATE("FILE_CREATE", "待上传"),
    /**待签章 */
	FILE_UPLOAD("FILE_UPLOAD", "待签章"),

    ;

    /** code */
    private String code;

    /** description */
    private String description;

    /**
     * 私有的构造方法
     * @param code
     * @param description
     */
    private SealStatus(String code, String description) {
        this.code = code;
        this.description = description;
    }

    /**
     * 
     * @param code
     * @return {@link SealStatus} 实例
     */
    public static SealStatus find(String code) {
        for (SealStatus userState : SealStatus.values()) {
            if (userState.getCode().equals(code)) {
                return userState;
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
