/**
 * business.com Inc.
 * Copyright (c) 2015-2025 All Rights Reserved.
 */
package com.pinting.gateway.reapal.out.enums;


/**
 * 19pay 接口url枚举
 * @author Baby shark love blowing wind
 * @version $Id: QuickPayUrl.java, v 0.1 2015-5-15 下午6:04:14 BabyShark Exp $
 */
public enum ReapalUrl {
    /** 未绑定用户快捷支付预下单 */
    NO_BIND_PRE_ORDER("/fast/debit/portal", "未绑定用户快捷支付预下单"),
    /** 绑定用户快捷支付预下单 */
    BIND_PRE_ORDER("/fast/bindcard/portal", "绑定用户快捷支付预下单"),
    /** 快捷支付确认支付 */
    CONFIRM_ORDER("/fast/pay", "快捷支付确认支付"),
    /** 解绑卡 */
    CANCEL_CARD("/fast/cancle/bindcard", "快捷支付解绑卡"),
    /** 支付结果查询 */
    QUERY_ORDER_RESULT("/fast/search", "支付结果查询"),
    /** 卡密鉴权接口 */
    CERTIFY_INTERFACE("/fast/certificate", "卡密鉴权接口"),
    /** 重发短信接口 */
    RESEND_CODE("/fast/sms", "重发短信接口"),
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
    private ReapalUrl(String code, String description) {
        this.code = code;
        this.description = description;
    }

    /**
     * 
     * @param code
     * @return {@link ActivityState} 实例
     */
    public static ReapalUrl find(String code) {
        for (ReapalUrl key : ReapalUrl.values()) {
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
