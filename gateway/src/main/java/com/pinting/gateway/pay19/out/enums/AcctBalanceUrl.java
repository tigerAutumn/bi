/**
 * business.com Inc.
 * Copyright (c) 2015-2025 All Rights Reserved.
 */
package com.pinting.gateway.pay19.out.enums;

/**
 * 19pay 接口url枚举
 * @author Baby shark love blowing wind
 * @version $Id: AcctBalanceUrl.java, v 0.1 2015-5-15 下午6:04:14 BabyShark Exp $
 */
public enum AcctBalanceUrl {

    /** 查询余额 */
    QUERY_ACCT_BALANCE("queryAcctBalance.do", "查询余额 "), ;

    /** code */
    private String code;

    /** description */
    private String description;

    /**
     * 私有的构造方法
     * @param code
     * @param description
     */
    private AcctBalanceUrl(String code, String description) {
        this.code = code;
        this.description = description;
    }

    /**
     * 
     * @param code
     * @return {@link ActivityState} 实例
     */
    public static AcctBalanceUrl find(String code) {
        for (AcctBalanceUrl key : AcctBalanceUrl.values()) {
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
