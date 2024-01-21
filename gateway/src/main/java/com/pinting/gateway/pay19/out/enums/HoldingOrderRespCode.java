package com.pinting.gateway.pay19.out.enums;

public enum HoldingOrderRespCode {
    /** 成功*/
    SUCCESS_CODE_00000("00000", "操作成功"),

    /************************ 代付错误码 *******************************/
    /** */
    ERROR_CODE_10001("10001", "有参数为空，或者verifyString验证失败"),
    /** */
    ERROR_CODE_10002("10002", "获取用户信息为空"),
    /** */
    ERROR_CODE_10003("10003", "提交的信息与存储的信息不一致"),
    /** */
    ERROR_CODE_10004("10004", "解密持卡人姓名或银行卡号失败"),
    /** */
    ERROR_CODE_11002("11002", "未查询到订单"),
    /** */
    ERROR_CODE_15010("15010", "商户账户状态验证失败"),
    /** */
    ERROR_CODE_30010("30010", "merchantId为空或字段过长"),
    /** */
    ERROR_CODE_30011("30011", "merchantId不存在"),
    /** */
    ERROR_CODE_30012("30012", "发起IP验证失败"),
    /** */
    ERROR_CODE_30013("30013", "verifyString校验失败"),
    /** */
    ERROR_CODE_30014("30014", "代扣限制交易"),
    /** */
    ERROR_CODE_30015("30015", "代扣限制商家交易"),
    /** */
    ERROR_CODE_30016("30016", "代扣获取交易限制信息异常"),
    /** */
    ERROR_CODE_30017("30017", "版本号错误"),
    /** */
    ERROR_CODE_30019("30019", "mxUserId字段长度过长或为空"),
    /** */
    ERROR_CODE_30020("30020", "mxOrderId字段长度为空或过长"),
    /** */
    ERROR_CODE_30021("30021", "orderDate字段为空或过长或格式不正确"),
    /** */
    ERROR_CODE_30022("30022", "amount字段为空或格式不正确"),
    /** */
    ERROR_CODE_30023("30023", "不支持的卡类型"),
    /** */
    ERROR_CODE_30030("30030", "currency格式错误"),
    /** */
    ERROR_CODE_30031("30031", "contractNo字段和银行卡信息字段不能同时为空"),
    /** */
    ERROR_CODE_30032("30032", "原绑定协议中，姓名或身份证号为空，需要补全信息后才能继续使用"),
    /** */
    ERROR_CODE_30033("30033", "银行卡格式错误"),
    /** */
    ERROR_CODE_30034("30034", "持卡人姓名格式错误"),
    /** */
    ERROR_CODE_30035("30035", "持卡人身份证号格式错误"),
    /** */
    ERROR_CODE_30036("30036", "银行卡信息字段解密失败（银行卡号，持卡人名称，身份证号码字段）"),
    /** */
    ERROR_CODE_30037("30037", "mxGoodsName字段格式错误"),
    /** */
    ERROR_CODE_30038("30038", "mxGoodsType字段格式错误"),
    /** */
    ERROR_CODE_30045("30045", "mobile格式错误"),
    /** */
    ERROR_CODE_30046("30046", "mobile字段解密失败"),
    /** */
    ERROR_CODE_30048("30048", "notifyUrl字段格式不正确"),
    /** */
    ERROR_CODE_30049("30049", "原绑定协议中，手机号码和本次请求中的手机号码不一致"),
    /** */
    ERROR_CODE_30050("30050", "其它参数错误"),
    /** */
    ERROR_CODE_30051("30051", "系统错误"),
    /** */
    ERROR_CODE_30054("30054", "(金额/笔数)限制交易"),
    /** */
    ERROR_CODE_30072("30072", "tradeType交易类型校验失败"),
    /** */
    ERROR_CODE_30074("30074", "tradeDesc交易描述校验失败"),
    /** */
    ERROR_CODE_30077("30077", "公用回传参数校验失败"),
    /** */
    ERROR_CODE_30078("30078", "公用业务扩展字段校验失败"),
    /** */
    ERROR_CODE_30080("30080", "订单描述信息校验失败"),
    /** */
    ERROR_CODE_30081("30081", "商户描述信息校验失败"),
    /** */
    ERROR_CODE_30082("30082", "保留信息校验失败"),
    /** */
    ERROR_CODE_30084("30084", "风控不通过"),
    /** */
    ERROR_CODE_30085("30085", "系统错误"),
    /** */
    ERROR_CODE_30086("30086", "系统错误"),
    /** */
    ERROR_CODE_30087("30087", "系统错误"),
    /** */
    ERROR_CODE_30091("30091", "银行卡号和银行通道不匹配，请重新确认"),
    /** */
    ERROR_CODE_30092("30092", "原有绑定信息中，银行卡号和银行通道不匹配，请解绑后，重新绑定"),
    /** */
    ERROR_CODE_30093("30093", "原有绑定信息中，银行卡信息不正确"),
    /** */
    ERROR_CODE_31018("31018", "信息校验超时，请重试或联系客服"),
    /** */
    ERROR_CODE_31019("31019", "银行验证卡号姓名不匹配"),
    /** */
    ERROR_CODE_31020("31020", "预留手机号验证失败"),
    /** */
    ERROR_CODE_31021("31021", "姓名或身份证，与卡号不符"),
    /** */
    ERROR_CODE_31022("31022", "银行账户余额不足"),
    /** */
    ERROR_CODE_31025("31025", "未开通银行卡的相关功能(请咨询银行)"),
    /** */
    ERROR_CODE_31028("31028", "预留手机号或身份证号错误"),
    /** */
    ERROR_CODE_31029("31029", "银行卡开卡身份证错误"),
    /** */
    ERROR_CODE_31030("31030", "开卡预留手机号或身份证、姓名错误"),
    /** */
    ERROR_CODE_31031("31031", "风险交易，禁止支付"),
    /** */
    ERROR_CODE_31032("31032", "为交易安全，30天内不能使用超过1张银行卡"),
    /** */
    ERROR_CODE_31033("31033", "该商户没有权限进行预支付"),
    /** */
    ERROR_CODE_31034("31034", "预留手机号或身份证号错误，已超过今日最大尝试次数"),
    /** */
    ERROR_CODE_32000("32000", "银行确认处理中"),
    /** */
    ERROR_CODE_34001("34001", "系统错误"),
    /** */
    ERROR_CODE_34002("34002", "重复订单"),
    /** */
    ERROR_CODE_34004("34004", "协议号不正确"),
    /** */
    ERROR_CODE_34005("34005", "协议号不存在"),
    /** */
    ERROR_CODE_34006("34006", "代扣绑定状态非正常或非代扣绑定"),
    /** */
    ERROR_CODE_34007("34007", "通道当前不可用"),
    /** */
    ERROR_CODE_34008("34008", "系统异常"),
    /** */
    ERROR_CODE_34009("34009", "系统异常"),
    /** */
    ERROR_CODE_34010("34010", "订单号错误"),
    /** */
    ERROR_CODE_34011("34011", "订单号错误"),
    /** */
    ERROR_CODE_34012("34012", "订单号错误"),
    /** */
    ERROR_CODE_34013("34013", "订单号错误"),
    /** */
    ERROR_CODE_34014("34014", "系统错误"),
    /** */
    ERROR_CODE_34015("34015", "系统错误"),
    /** */
    ERROR_CODE_34016("34016", "系统错误"),
    /** */
    ERROR_CODE_34017("34017", "系统错误"),
    /** */
    ERROR_CODE_34018("34018", "系统错误"),
    /** */
    ERROR_CODE_34019("34019", "系统错误"),
    /** */
    ERROR_CODE_34020("34020", "系统错误"),
    /** */
    ERROR_CODE_34021("34021", "系统错误"),
    /** */
    ERROR_CODE_99999("99999", "系统错误");

    /** code */
    private String code;

    /** description */
    private String description;

    /**
     * 私有的构造方法
     * @param code
     * @param description
     */
    private HoldingOrderRespCode(String code, String description) {
        this.code = code;
        this.description = description;
    }

    /**
     * 
     * @param code
     * @return {@link ActivityState} 实例
     */
    public static HoldingOrderRespCode find(String code) {
        for (HoldingOrderRespCode key : HoldingOrderRespCode.values()) {
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
