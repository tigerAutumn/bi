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
public enum ReapalQuickPayRespCode {
	
	/** 成功*/
    SUCCESS_CODE_0000("0000", "处理成功"),
    
    /** 签约错误列表 */
    ERROR_CODE_2001("2001", "签约已提交，正在处理中"),
    ERROR_CODE_2002("2002", "未签约"),
    ERROR_CODE_2003("2003", "签约失败,请稍后重试"),
    ERROR_CODE_2004("2004", "无效的发卡行"),
    ERROR_CODE_2005("2005", "无效的交易"),
    ERROR_CODE_2006("2006", "无效金额"),
    ERROR_CODE_2007("2007", "无效的银行卡"),
    ERROR_CODE_2008("2008", "该卡不支持无卡支付"),
    ERROR_CODE_2009("2009", "此卡受限制，请联系发卡行"),
    ERROR_CODE_2010("2010", "签约超时，请重试"),
    ERROR_CODE_2011("2011", "持卡人身份信息或手机号输入不正确"),
    ERROR_CODE_2012("2012", "签约重复,请稍后查询结果"),
    ERROR_CODE_2013("2013", "鉴权超过6次，次日再试"),
    ERROR_CODE_2014("2014", "金额与原订单不符"),
    ERROR_CODE_2015("2015", "暂不支持此卡"),
    ERROR_CODE_2016("2016", "系统异常，请联系客服"),//系统异常，请联系融宝支付
    ERROR_CODE_2017("2017", "此卡是信用卡，请用储蓄卡支付"),
    ERROR_CODE_2018("2018", "此卡是储蓄卡，请用信用卡支付"),
    ERROR_CODE_2019("2019", "其它"),
    ERROR_CODE_2020("2020", "身份证格式错误"),
    ERROR_CODE_2021("2021", "此卡已过期"),
    ERROR_CODE_2022("2022", "消费金额超限，请联系发卡银行"),
    ERROR_CODE_2023("2023", "超出最大输入密码次数，请联系发卡行"),
    ERROR_CODE_2024("2024", "身份证或姓名格式不正确"),
    ERROR_CODE_2025("2025", "手机号格式错误"),
    ERROR_CODE_2026("2026", "您的发卡行与卡号不匹配，请重新填写"),
    ERROR_CODE_2027("2027", "传入身份信息与原信息不符"),
    ERROR_CODE_2028("2028", "手机号不符"),
    ERROR_CODE_2029("2029", "更改的手机号未变"),
    ERROR_CODE_2030("2030", "绑卡id不存在"),
    ERROR_CODE_2031("2031", "会员id与原绑卡id不一致"),
    ERROR_CODE_2032("2032", "请先鉴权卡密，在调用重发短信接口"),
    
    /** 支付错误列表 */
    ERROR_CODE_3001("3001", "输入姓名有误"),
    ERROR_CODE_3002("3002", "银行预留手机号有误"),
    ERROR_CODE_3003("3003", "银行系统异常，请联系客服"),//银行系统异常，联系融宝
    ERROR_CODE_3004("3004", "卡号格式错误"),
    ERROR_CODE_3005("3005", "单卡超过当日累积支付限额"),
    ERROR_CODE_3006("3006", "支付失败，银行内部系统间调用超时或日切造成付款失败"),
    ERROR_CODE_3007("3007", "单卡超过单笔支付限额"),
    ERROR_CODE_3008("3008", "单卡超过单月累积支付限额"),
    ERROR_CODE_3009("3009", "单卡超过单日累积支付次数上限"),
    ERROR_CODE_3010("3010", "单卡超过单月累积支付次数上限"),
    ERROR_CODE_3011("3011", "订单重复提交"),
    ERROR_CODE_3012("3012", "订单已终态表示该订单已经支付成功或者支付失败"),
    ERROR_CODE_3013("3013", "无效的银行卡"),
    ERROR_CODE_3014("3014", "商户不支持该卡交易"),
    ERROR_CODE_3015("3015", "订单不存在"),
    ERROR_CODE_3016("3016", "证件号非法，请核实"),
    ERROR_CODE_3017("3017", "交易订单已经支付成功，不允许再发起支付请求"),
    ERROR_CODE_3018("3018", "查发卡方失败，请联系发卡银行"),
    ERROR_CODE_3019("3019", "本卡在该商户不允许此交易，请联系收单机构"),
    ERROR_CODE_3020("3020", "卡被发卡方没收，请联系发卡银行"),
    ERROR_CODE_3021("3021", "户名或证件错误"),
    ERROR_CODE_3022("3022", "支付失败，请联系发卡银行,银行对某些卡做了特殊的业务限制，需要用户联系银行解决"),
    ERROR_CODE_3023("3023", "消费金额超限"),
    ERROR_CODE_3024("3024", "本卡未激活或睡眠卡，请联系发卡银行"),
    ERROR_CODE_3025("3025", "该卡有作弊嫌疑或有相关限制，请联系发卡银行"),
    ERROR_CODE_3026("3026", "可用余额不足，请联系发卡银行"),
    ERROR_CODE_3027("3027", "该卡已过期或有效期错误，请联系发卡银行"),
    ERROR_CODE_3028("3028", "该卡未开通无卡支付，请在银联在线开通"),
    ERROR_CODE_3029("3029", "银行系统异常 银行返回的未知错误"),
    ERROR_CODE_3030("3030", "信用卡有效期或CVN2有误"),
    ERROR_CODE_3031("3031", "银行卡未开通认证支付"),
    ERROR_CODE_3032("3032", "订单已过期或已撤销"),
    ERROR_CODE_3036("3036", "交易订单信息不一致"),
    ERROR_CODE_3037("3037", "CVN验证失败或有作弊嫌疑"),
    ERROR_CODE_3038("3038", "身份证格式错误"),
    ERROR_CODE_3040("3040", "该卡为储蓄卡，请用信用卡支付"),
    ERROR_CODE_3041("3041", "该卡为信用卡，请用储蓄卡支付"),
    ERROR_CODE_3053("3053", "系统正在对数据处理，待查询"),
    ERROR_CODE_3054("3054", "银行超时，交易待查询"),
    ERROR_CODE_3056("3056", "银行处理异常，交易待查询"),
    ERROR_CODE_3059("3059", "此卡为挂失卡，请联系发卡行"),
    ERROR_CODE_3062("3062", "原交易信息不存在"),
    ERROR_CODE_3063("3063", "日期错误"),
    ERROR_CODE_3067("3067", "银行维护，请稍后再试"),//其他
    ERROR_CODE_3069("3069", "验证码错误"),
    ERROR_CODE_3070("3070", "无权限解绑银行卡"),
    ERROR_CODE_3071("3071", "绑卡已过期"),
    ERROR_CODE_3072("3072", "无效的绑卡ID"),
    ERROR_CODE_3073("3073", "与银行通讯失败"),
    ERROR_CODE_3074("3074", "验证码已过期"),
    ERROR_CODE_3075("3075", "用户于指定的银行卡无绑卡关系"),
    ERROR_CODE_3076("3076", "无法获取绑卡请求的卡信息"),
    ERROR_CODE_3077("3077", "此卡有效期已过"),
    ERROR_CODE_3078("3078", "消费金额超限"),//此交易被融宝支付拦截
    ERROR_CODE_3079("3079", "解绑失败"),
    ERROR_CODE_3080("3080", "绑卡列表卡类型错误"),
    ERROR_CODE_3081("3081", "交易处理中"),
    ERROR_CODE_3082("3082", "支付失败，详情请咨询您的发卡行"),
    ERROR_CODE_3083("3083", "接收成功"),
    ERROR_CODE_3084("3084", "支付失败"),
    ERROR_CODE_3085("3085", "信用卡有效期填写错误"),
    ERROR_CODE_3086("3086", "交易金额不能低于1.5元"),//交易金额不能低于150分
    ERROR_CODE_3087("3087", "卡已解绑"),
    ERROR_CODE_3088("3088", "刷卡密码错误次数超限，请联系发卡银行"),
    ERROR_CODE_3089("3089", "银行卡信息非法，请核实"),
    ERROR_CODE_3090("3090", "黑名单用户，不允许交易"),
    ERROR_CODE_3091("3091", "银行卡信息异常，请联系发卡行"),
    ERROR_CODE_3092("3092", "卡信息校验不通过"),
    ERROR_CODE_3093("3093", "扣款金额超出网银设置金额"),
    ERROR_CODE_3094("3094", "银行卡号有误，请重新支付"),
    ERROR_CODE_3095("3095", "持卡人身份信息或手机号输入不正确"),
    ERROR_CODE_3096("3096", "银行维护，请稍后再试"),//银行维护，请联系融宝支付
    
    /** 公共错误码 */
    ERROR_CODE_1001("1001", "系统超时，请重新操作"),
    ERROR_CODE_1002("1002", "传入参数错误或非法请求（参数错误，有必要参数为空）"),
    ERROR_CODE_1003("1003", "订单号不存在"),
    ERROR_CODE_1004("1004", "商户账户已冻结"),
    ERROR_CODE_1005("1005", "商户ID不存在"),
    ERROR_CODE_1006("1006", "交互解密失败"),
    ERROR_CODE_1007("1007", "sign验签失败"),
    ERROR_CODE_1009("1009", "查不到此交易订单"),
    ERROR_CODE_1016("1016", "商户ID必须是数字"),
    ERROR_CODE_1017("1017", "商户未开通在线业务"),
    ERROR_CODE_1018("1018", "商户未配置快捷支付服务"),
    ERROR_CODE_1020("1020", "后台未配置秘钥"),
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
    private ReapalQuickPayRespCode(String code, String description) {
        this.code = code;
        this.description = description;
    }

    /**
     * 
     * @param code
     * @return {@link ActivityState} 实例
     */
    public static ReapalQuickPayRespCode find(String code) {
        for (ReapalQuickPayRespCode key : ReapalQuickPayRespCode.values()) {
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
