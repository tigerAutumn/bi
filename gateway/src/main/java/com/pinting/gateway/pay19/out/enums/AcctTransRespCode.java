/**
 * business.com Inc.
 * Copyright (c) 2015-2025 All Rights Reserved.
 */
package com.pinting.gateway.pay19.out.enums;

/**
 * 
 * @author Baby shark love blowing wind
 * @version $Id: AcctTransRespCode.java, v 0.1 2015-5-15 下午6:04:14 BabyShark Exp $
 */
public enum AcctTransRespCode {
    /** 成功*/
    SUCCESS_CODE_40000("00000", "处理成功"),

    /************************ 账户转账错误码 *******************************/
    /** */
    ERROR_CODE_15001("15001", "请求参数非空校验失败"),
    /** */
    ERROR_CODE_15002("15002", "merchantId字段长度超限或者不存在"),
    /** */
    ERROR_CODE_15003("15003", "发起IP验证失败"),
    /** */
    ERROR_CODE_15004("40004", "Md5校验失败"),
    /** */
    ERROR_CODE_15005("15005", "版本号错误"),
    /** */
    ERROR_CODE_15006("15006", "请求流水字段长度大于50"),
    /** */
    ERROR_CODE_15007("15007", "请求时间字段长度大于14或者格式不正确"),
    /** */
    ERROR_CODE_15008("15008", "订单金额字段格式不正确"),
    /** */
    ERROR_CODE_15009("15009", "商户对应账户字段长度过长或者格式不正确"),
    /** */
    ERROR_CODE_15010("15010", "商户没有权限操作此账户"),
    /** */
    ERROR_CODE_15011("15011", "转入方账户类型格式错误"),
    /** */
    ERROR_CODE_15012("15012", "转入方账户字段长度过长或者格式不正确"),
    /** */
    ERROR_CODE_15013("15013", "转入方账户不存在"),
    /** */
    ERROR_CODE_15014("15014", "转入方账户名称字段过长"),
    /** */
    ERROR_CODE_15015("15015", "转入方账户名称和账户不匹配"),
    /** */
    ERROR_CODE_15016("15016", "交易类型字段长度过长或者格式不正确"),
    /** */
    ERROR_CODE_15017("15017", "交易描述字段长度过长"),
    /** */
    ERROR_CODE_15018("15018", "后台通知地址字段长度过长或者格式不正确"),
    /** */
    ERROR_CODE_15019("15019", "备注字段长度过长"),
    /** */
    ERROR_CODE_15020("15020", "保留字段长度过长"),
    /** */
    ERROR_CODE_15021("15021", "付款账户可用余额不足"),
    /** */
    ERROR_CODE_15022("15022", "时间戳字段长度大于14或者格式不正确"),
    /** */
    ERROR_CODE_15023("15023", "订单不存在"),
    /** */
    ERROR_CODE_15024("15024", "转出方账户和转入方账户同一账户"),
    /** */
    ERROR_CODE_15025("15025", "订单重复"),
    /** */
    ERROR_CODE_15026("15026", "转出方oriOutMxId字段长度超限或者不存在"),
    /** */
    ERROR_CODE_15040("15040", "商户没有权限使用该功能"),
    /** */
    ERROR_CODE_15041("15041", "交易超限"),
    /** */
    ERROR_CODE_99999("99999", "系统忙，请稍后再试"), ;

    /** code */
    private String code;

    /** description */
    private String description;

    /**
     * 私有的构造方法
     * @param code
     * @param description
     */
    private AcctTransRespCode(String code, String description) {
        this.code = code;
        this.description = description;
    }

    /**
     * 
     * @param code
     * @return {@link ActivityState} 实例
     */
    public static AcctTransRespCode find(String code) {
        for (AcctTransRespCode key : AcctTransRespCode.values()) {
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
