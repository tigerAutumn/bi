/**
 * business.com Inc.
 * Copyright (c) 2015-2025 All Rights Reserved.
 */
package com.pinting.gateway.reapal.in.enums;

/**
 * Reapal 快捷支付响应状态
 * @author Baby shark love blowing wind
 * @version $Id: OrderStatus.java, v 0.1 2015-5-15 下午6:04:14 BabyShark Exp $
 */
public enum NoticeOrderStatus {
    /** 快捷支付订单成功 */
    YES("TRADE_FINISHED", "快捷支付订单成功"),
    /** 快捷支付订单失败 */
    NO("TRADE_FAILURE", "快捷支付订单失败");

    /** code */
    private String code;

    /** description */
    private String description;

    /**
     * 私有的构造方法
     * @param code
     * @param description
     */
    private NoticeOrderStatus(String code, String description) {
        this.code = code;
        this.description = description;
    }

    /**
     * 
     * @param code
     * @return {@link ActivityState} 实例
     */
    public static NoticeOrderStatus find(String code) {
        for (NoticeOrderStatus key : NoticeOrderStatus.values()) {
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
