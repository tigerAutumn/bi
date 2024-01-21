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
public enum Response {
    /** 接收通知成功 */
    RECEIVE_SUCCESS("Y", "接收通知成功"),
    /** 接收通知失败 */
    RECEIVE_FAIL("N", "接收通知失败");

    /** code */
    private String code;

    /** description */
    private String description;

    /**
     * 私有的构造方法
     * @param code
     * @param description
     */
    private Response(String code, String description) {
        this.code = code;
        this.description = description;
    }

    /**
     * 
     * @param code
     * @return {@link ActivityState} 实例
     */
    public static Response find(String code) {
        for (Response key : Response.values()) {
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
