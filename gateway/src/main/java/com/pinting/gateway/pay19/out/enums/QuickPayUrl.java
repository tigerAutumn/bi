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
public enum QuickPayUrl {
    /** 快捷支付短信重发 */
    RE_SEND_SMS("rSendSms.do", "快捷支付短信重发"),
    /** 快捷支付预下单 */
    PRE_ORDER("preOrder.do", "快捷支付预下单"),
    /** 快捷支付确认支付 */
    CONFIRM_ORDER("confirmOrder.do", "快捷支付确认支付"),
    /** 快捷订单查询 */
    QUERY_ORDER("queryMOrder.do", "快捷订单查询"),
    /** 快捷银行查询 */
    QUERY_BANK_LIST("queryBankList.do", "快捷银行查询"),
    /** 快捷银行卡查询 */
    QUERY_BANK_CARD_LIST("queryBankCardList.do", "快捷银行卡查询"),
    /** 快捷银行卡查询 */
    UNBIND_CARD("unBindCard.do", "快捷银行卡解绑"), ;

    /** code */
    private String code;

    /** description */
    private String description;

    /**
     * 私有的构造方法
     * @param code
     * @param description
     */
    private QuickPayUrl(String code, String description) {
        this.code = code;
        this.description = description;
    }

    /**
     * 
     * @param code
     * @return {@link ActivityState} 实例
     */
    public static QuickPayUrl find(String code) {
        for (QuickPayUrl key : QuickPayUrl.values()) {
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
