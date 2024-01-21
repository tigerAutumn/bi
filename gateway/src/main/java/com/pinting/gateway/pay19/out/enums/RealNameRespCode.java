/**
 * business.com Inc.
 * Copyright (c) 2015-2025 All Rights Reserved.
 */
package com.pinting.gateway.pay19.out.enums;

/**
 * 
 * @author Baby shark love blowing wind
 * @version $Id: Pay4AnotherRespCode.java, v 0.1 2015-5-15 下午6:04:14 BabyShark Exp $
 */
public enum RealNameRespCode {
    /** 成功*/
    SUCCESS_CODE_40000("00000", "处理成功"),

    /************************ 代付错误码 *******************************/
    /** */
    ERROR_CODE_40001("40001", "merchantId字段为空或过长"),
    /** */
    ERROR_CODE_40002("40002", "获取商户信息失败"),
    /** */
    ERROR_CODE_40003("40003", "发起IP验证失败"),
    /** */
    ERROR_CODE_40004("40004", "hmac校验失败"),
    /** */
    ERROR_CODE_40005("40005", "version字段为空或过长或格式不正确"),
    /** */
    ERROR_CODE_40006("40006", "mxReqSno字段为空或过长"),
    /** */
    ERROR_CODE_40007("40007", "mxReqDate字段为空或过长或格式不正确"),
    /** */
    ERROR_CODE_40008("40008", "idType字段为空或长度过长或类型不支持"),
    /** */
    ERROR_CODE_40009("40009", "持卡人姓名格式错误"),
    /** */
    ERROR_CODE_40010("40010", "持卡人姓名错误"), //cardHolder字段解密失败
    /** */
    ERROR_CODE_40011("40011", "持卡人姓名为空"),
    /** */
    ERROR_CODE_40012("40012", "身份证号格式错误"),
    /** */
    ERROR_CODE_40013("40013", "身份证号错误"), //idNo字段解密失败
    /** */
    ERROR_CODE_40014("40014", "身份证字段为空"),
    /** */
    ERROR_CODE_40015("40015", "notifyUrl字段为空或格式不正确"),
    /** */
    ERROR_CODE_40016("40016", "remark字段格式校验失败"),
    /** */
    ERROR_CODE_40017("40017", "reserved字段格式校验失败"),
    /** */
    ERROR_CODE_40018("40018", "参数校验异常"),
    /** */
    ERROR_CODE_40021("40021", "银行卡号为空"),
    /** */
    ERROR_CODE_40022("40022", "银行卡号格式错误"),
    /** */
    ERROR_CODE_40023("40023", "银行卡号错误"), //bankCardNo字段解密失败
    /** */
    ERROR_CODE_40024("40024", "银行卡类型为空或格式不正确"),
    /** */
    ERROR_CODE_40025("40025", "银行卡属性为空或格式不正确"),
    /** */
    ERROR_CODE_40026("40026", "手机号为空"),
    /** */
    ERROR_CODE_40027("40027", "手机号格式错误"),
    /** */
    ERROR_CODE_40028("40028", "手机号错误"), //手机号字段解密失败
    /** */
    ERROR_CODE_40029("40029", "pcId字段为空"),
    /** */
    ERROR_CODE_40030("40030", "银行卡号和银行码不匹配"),
    /** */
    ERROR_CODE_40101("40101", "订单已存在"),
    /** */
    ERROR_CODE_40102("40102", "收单失败"),
    /** */
    ERROR_CODE_40103("40103", "计算手续费失败"),
    /** */
    ERROR_CODE_40104("40104", "余额不足"),
    /** */
    ERROR_CODE_40105("40105", "扣款失败"),
    /** */
    ERROR_CODE_40106("40106", "扣款中"),
    /** */
    ERROR_CODE_40107("40107", "订单不存在"),
    /** */
    ERROR_CODE_40108("40108", "验证中"),
    /** */
    ERROR_CODE_40109("40109", "验证失败"),
    /** */
    ERROR_CODE_40110("40110", "预留手机号或CVV2错误"),
    /** */
    ERROR_CODE_40111("40111", "CVV2错误或有效期错误"),
    /** */
    ERROR_CODE_40112("40112", "预留手机号或身份证号错误"),
    /** */
    ERROR_CODE_40113("40113", "卡号与身份证不匹配"),
    /** */
    ERROR_CODE_40114("40114", "户名或身份证，或开卡预留手机号码与卡号不符"),
    /** */
    ERROR_CODE_40115("40115", "预留手机号或身份证号错误，已超过今日最大尝试次数"),
    /** */
    ERROR_CODE_40116("40116", "卡或账户无效(包含过期，作废)"),
    /** */
    ERROR_CODE_40117("40117", "银行验证卡号不合法"),
    /** */
    ERROR_CODE_40118("40118", "银行不支持此类型的卡"),
    /** */
    ERROR_CODE_40119("40119", "银行没有返回错误描述"),
    /** */
    ERROR_CODE_40120("40120", "银行cvv2验证失败（信用卡）"),
    /** */
    ERROR_CODE_40121("40121", "银行有效期验证失败（信用卡）"),
    /** */
    ERROR_CODE_40122("40122", "卡号与姓名不匹配"),
    /** */
    ERROR_CODE_40123("40123", "银行预留手机号验证错误"),
    /** */
    ERROR_CODE_40124("40124", "卡号姓名验证失败"),
    /** */
    ERROR_CODE_40125("40125", "账户不存在"),
    /** */
    ERROR_CODE_40126("40126", "交易要素不正确"),
    /** */
    ERROR_CODE_40127("40127", "行名行号不一致"),
    /** */
    ERROR_CODE_40128("40128", "户名或身份证与卡号不符"), ;

    /** code */
    private String code;

    /** description */
    private String description;

    /**
     * 私有的构造方法
     * @param code
     * @param description
     */
    private RealNameRespCode(String code, String description) {
        this.code = code;
        this.description = description;
    }

    /**
     * 
     * @param code
     * @return {@link ActivityState} 实例
     */
    public static RealNameRespCode find(String code) {
        for (RealNameRespCode key : RealNameRespCode.values()) {
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
