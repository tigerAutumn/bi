/**
 * business.com Inc.
 * Copyright (c) 2015-2025 All Rights Reserved.
 */
package com.pinting.gateway.reapal.out.enums;


/**
 * 融宝代付 接口url枚举
 * @author Baby shark love blowing wind
 * @version $Id: ReapalDsfUrl.java, v 0.1 2015-5-15 下午6:04:14 BabyShark Exp $
 */
public enum ReapalDsfUrl {
    /** 代付 */
    AGENT_PAY("/pay", "出金提交"),
    /** 代付 */
    AGENT_PAY_QUERY("/payquery?", "出金查询"),
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
    private ReapalDsfUrl(String code, String description) {
        this.code = code;
        this.description = description;
    }

    /**
     * 
     * @param code
     * @return {@link ActivityState} 实例
     */
    public static ReapalDsfUrl find(String code) {
        for (ReapalDsfUrl key : ReapalDsfUrl.values()) {
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
