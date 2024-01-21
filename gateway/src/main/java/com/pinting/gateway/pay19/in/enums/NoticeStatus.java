/**
 * business.com Inc.
 * Copyright (c) 2015-2025 All Rights Reserved.
 */
package com.pinting.gateway.pay19.in.enums;

/**
 * 19pay 快捷支付响应状态
 * @author Baby shark love blowing wind
 * @version $Id: QuickPayStatus.java, v 0.1 2015-5-15 下午6:04:14 BabyShark Exp $
 */
public enum NoticeStatus {
    /** 请求成功 */
    SUCCESS("SUCCESS", "请求成功"),
    /** 请求失败 */
    FAIL("FAIL", "请求失败"),
    /** 异常 */
    EXCEPTION("EXCEPTION", "异常");

    /** code */
    private String code;

    /** description */
    private String description;

    /**
     * 私有的构造方法
     * @param code
     * @param description
     */
    private NoticeStatus(String code, String description) {
        this.code = code;
        this.description = description;
    }

    /**
     * 
     * @param code
     * @return {@link ActivityState} 实例
     */
    public static NoticeStatus find(String code) {
        for (NoticeStatus key : NoticeStatus.values()) {
            if (key.getCode().equals(code)) {
                return key;
            }
        }
        return null;//throw new GatewayBaseException(GatewayBaseCode.SYSTEM_ERROR, "根据code=" + code + "获取组织类型失败");
    }

    public String getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

}
