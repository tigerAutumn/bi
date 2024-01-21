/**
 * Wenzi.com Inc.
 * Copyright (c) 2015-2025 All Rights Reserved.
 */
package com.pinting.gateway.hessian.message.pay19.enums;

/**
 * 
 * @author Baby shark love blowing wind
 * @version $Id: OrderStatus.java, v 0.1 2015-7-28 下午4:38:01 BabyShark Exp $
 */
public enum OrderStatus {
    /** 交易成功*/
    SUCCESS("SUCCESS", "交易成功"),
    /** 交易失败*/
    FAIL("FAIL", "交易失败"),
    /** 订单超过确认次数关闭*/
    CLOSE("CLOSE", "订单超过确认次数关闭"),
    /** 订单超时关闭*/
    EXPIRED("EXPIRED", "订单超时关闭"),
    /** 交易失败*/
    PRE_FAIL("PRE_FAIL", "预下单失败，允许商户重新进行预下单处理"),
    /** 交易失败*/
    CONF_FAIL("CONF_FAIL", "银行确认失败，允许商户重新进行预下单处理"),
    /** 交易失败*/
    CONF_DEAL("CONF_DEAL", "银行确认处理中，银行订单处理中，不允许商户对其进行交易操作"),
    /** 等待输入短信验证码，允许商户重新预下单或者输入短信码进行确认支付*/
    WA_CONF("WA_CONF", "等待输入短信验证码，允许商户重新预下单或者输入短信码进行确认支付"),
    /** 订单状态异常，请联系19付*/
    EXCEPTION("EXCEPTION", "订单状态异常，请联系19付"), ;

    /** code */
    private String code;

    /** description */
    private String description;

    /**
     * 私有的构造方法
     * @param code
     * @param description
     */
    private OrderStatus(String code, String description) {
        this.code = code;
        this.description = description;
    }

    /**
     * 
     * @param code
     * @return {@link OrderStatus} 实例
     */
    public static OrderStatus find(String code) {
        for (OrderStatus transCode : OrderStatus.values()) {
            if (transCode.getCode().equals(code)) {
                return transCode;
            }
        }
        return null;
    }

    public String getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }
}
