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
public enum Pay4AnotherRespCode {
    /** 成功*/
    SUCCESS_CODE_00000("00000", "处理成功"),

    /************************ 代付错误码 *******************************/
    /** */
    ERROR_CODE_00001("00001", "缺少必要参数"),
    /** */
    ERROR_CODE_00002("00002", "不存在的商户Id"),
    /** */
    ERROR_CODE_00003("00003", "订单金额无效"),
    /** */
    ERROR_CODE_00004("00004", "时间格式不正确"),
    /** */
    ERROR_CODE_00005("00005", "卡类型不支持"),
    /** */
    ERROR_CODE_00006("00006", "账户类型不支持"),
    /** */
    ERROR_CODE_00007("00007", "银行编码错误"),
    /** */
    ERROR_CODE_00008("00008", "账户余额不足"),
    /** */
    ERROR_CODE_00009("00009", "单笔最高金额限制"),
    /** */
    ERROR_CODE_00010("00010", "单笔最低金额限制"),
    /** */
    ERROR_CODE_00011("00011", "下单总金额限制"),
    /** */
    ERROR_CODE_00012("00012", "下单总笔数限制"),
    /** */
    ERROR_CODE_00013("00013", "重复下单"),
    /** */
    ERROR_CODE_00014("00014", "未找到对应订单"),
    /** */
    ERROR_CODE_00015("00015", "后台通知地址不合法"),
    /** */
    ERROR_CODE_00016("00016", "已完成订单总金额超限"),
    /** */
    ERROR_CODE_00017("00017", "已完成订单总笔数超限"),
    /** */
    ERROR_CODE_00018("00018", "查询订单没有成功"),
    /** */
    ERROR_CODE_00019("00019", "加密卡号或持卡人姓名不合法"),
    /** */
    ERROR_CODE_00020("00020", "卡号姓名不匹配(本地验证)"),
    /** */
    ERROR_CODE_00021("00021", "卡BIN信息验证失败"),
    /** */
    ERROR_CODE_00023("00023", "验证通道不匹配(本地验证)"),
    /** */
    ERROR_CODE_00025("00025", "付款类型不合法"),
    /** */
    ERROR_CODE_00026("00026", "交易类型不合法"),
    /** */
    ERROR_CODE_00028("00028", "商户不支持当前通道"),
    /** */
    ERROR_CODE_00029("00029", "安全信息校验无效"),
    /** */
    ERROR_CODE_00030("00030", "订单有风险，不予通过"),
    /** */
    ERROR_CODE_11000("11000", "hmac校验失败"),
    /** */
    ERROR_CODE_11001("11001", "IP限制"),
    /** */
    ERROR_CODE_11111("11111", "系统异常"), ;

    /** code */
    private String code;

    /** description */
    private String description;

    /**
     * 私有的构造方法
     * @param code
     * @param description
     */
    private Pay4AnotherRespCode(String code, String description) {
        this.code = code;
        this.description = description;
    }

    /**
     * 
     * @param code
     * @return {@link ActivityState} 实例
     */
    public static Pay4AnotherRespCode find(String code) {
        for (Pay4AnotherRespCode key : Pay4AnotherRespCode.values()) {
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
