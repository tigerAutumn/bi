/**
 * business.com Inc.
 * Copyright (c) 2015-2025 All Rights Reserved.
 */
package com.pinting.gateway.reapal.out.enums;


/**
 * 融宝代付 接口url枚举
 * @author Baby shark love blowing wind
 * @version $Id: ReapalDsfRespStatus.java, v 0.1 2015-5-15 下午6:04:14 BabyShark Exp $
 */
public enum ReapalDsfRespStatus {
    /** 出金提交成功 */
    SUCC("succ", "出金提交成功"),
    /** 出金提交失败 */
    FAIL("fail", "出金提交失败"),
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
    private ReapalDsfRespStatus(String code, String description) {
        this.code = code;
        this.description = description;
    }

    /**
     * 
     * @param code
     * @return {@link ActivityState} 实例
     */
    public static ReapalDsfRespStatus find(String code) {
        for (ReapalDsfRespStatus key : ReapalDsfRespStatus.values()) {
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
