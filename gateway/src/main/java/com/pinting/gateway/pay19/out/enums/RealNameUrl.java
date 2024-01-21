/**
 * business.com Inc.
 * Copyright (c) 2015-2025 All Rights Reserved.
 */
package com.pinting.gateway.pay19.out.enums;

/**
 * 19pay 接口url枚举
 * @author Baby shark love blowing wind
 * @version $Id: RealNameUrl.java, v 0.1 2015-5-15 下午6:04:14 BabyShark Exp $
 */
public enum RealNameUrl {
    /** 实名认证 */
    REAL_NAME_VERIFY("realNameVerify.do", "实名认证"),
    /** 实名认证查询 */
    QUERY_REAL_NAME_VERIFY("queryRealNameVerify.do", "实名认证查询"), ;

    /** code */
    private String code;

    /** description */
    private String description;

    /**
     * 私有的构造方法
     * @param code
     * @param description
     */
    private RealNameUrl(String code, String description) {
        this.code = code;
        this.description = description;
    }

    /**
     * 
     * @param code
     * @return {@link ActivityState} 实例
     */
    public static RealNameUrl find(String code) {
        for (RealNameUrl key : RealNameUrl.values()) {
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
