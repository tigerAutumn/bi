/**
 * business.com Inc.
 * Copyright (c) 2015-2025 All Rights Reserved.
 */
package com.pinting.gateway.pay19.out.enums;


/**
 * 19pay 接口url枚举
 * @author Baby shark love blowing wind
 * @version $Id: QuickPayUrl.java, v 0.1 2015-5-15 下午6:04:14 BabyShark Exp $
 */
public enum Pay4AnotherUrl {
    /** 代付下单 */
    NEW_MERCHANTDF("newMerchantDf.do", "代付下单"),
    /** 代付查询 */
    MERCHANTDF_QUERY("merchantDfQuery.do", "代付查询"),
    /** 代付可用银行通道查询 */
    AVAIL_BANK_CHANNEL_QUERY("availBankChannelQuery.do", "代付可用银行通道查询"), ;

    /** code */
    private String code;

    /** description */
    private String description;

    /**
     * 私有的构造方法
     * @param code
     * @param description
     */
    private Pay4AnotherUrl(String code, String description) {
        this.code = code;
        this.description = description;
    }

    /**
     * 
     * @param code
     * @return {@link ActivityState} 实例
     */
    public static Pay4AnotherUrl find(String code) {
        for (Pay4AnotherUrl key : Pay4AnotherUrl.values()) {
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
