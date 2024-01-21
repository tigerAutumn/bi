/**
 * business.com Inc.
 * Copyright (c) 2015-2025 All Rights Reserved.
 */
package com.pinting.gateway.pay19.out.enums;

/**
 * 19pay 实名认证响应状态
 * @author Baby shark love blowing wind
 * @version $Id: RealNameRespStatus.java, v 0.1 2015-5-15 下午6:04:14 BabyShark Exp $
 */
public enum RealNameRespStatus {
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
    private RealNameRespStatus(String code, String description) {
        this.code = code;
        this.description = description;
    }

    /**
     * 
     * @param code
     * @return {@link ActivityState} 实例
     */
    public static RealNameRespStatus find(String code) {
        for (RealNameRespStatus key : RealNameRespStatus.values()) {
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
