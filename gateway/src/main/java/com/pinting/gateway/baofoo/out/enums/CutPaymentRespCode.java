package com.pinting.gateway.baofoo.out.enums;

/**
 * 代扣支付错误码枚举类
 * @author bianyatian
 * @2016-11-26 上午11:32:27
 */
public enum CutPaymentRespCode {

	/** 成功*/
    SUCCESS_CODE_0000("0000", "处理成功", "SUCC"),
    
    UNKNOWN_CODE_BF00100("BF00100", "系统异常，请联系客服", "UNKNOWN"),//系统异常，请联系宝付
    UNKNOWN_CODE_BF00112("BF00112", "系统繁忙，请稍后再试", "UNKNOWN"),
    UNKNOWN_CODE_BF00113("BF00113", "交易结果未知，请稍后查询", "UNKNOWN"),
    UNKNOWN_CODE_BF00115("BF00115", "交易处理中，请稍后查询", "UNKNOWN"),
    UNKNOWN_CODE_BF00144("BF00144", "该交易有风险,订单处理中", "UNKNOWN"),
    UNKNOWN_CODE_BF00202("BF00202", "交易超时，请稍后查询", "UNKNOWN"),
    
    ERROR_CODE_BF00114("BF00114", "订单已支付成功，请勿重复支付", "BIZ_FAIL"),
    
    ERROR_CODE_BF00101("BF00101", "持卡人信息有误", "BIZ_FAIL"),
    ERROR_CODE_BF00102("BF00102", "银行卡已过有效期", "BIZ_FAIL"),
    ERROR_CODE_BF00103("BF00103", "账户余额不足", "BIZ_FAIL"),
    ERROR_CODE_BF00104("BF00104", "交易金额超限", "BIZ_FAIL"),
    ERROR_CODE_BF00107("BF00107", "当前银行卡不支持该业务", "BIZ_FAIL"),
    ERROR_CODE_BF00108("BF00108", "交易失败，请联系发卡行", "BIZ_FAIL"),
    ERROR_CODE_BF00109("BF00109", "交易金额低于限额", "BIZ_FAIL"),
    ERROR_CODE_BF00110("BF00110", "该卡暂不支持此交易", "BIZ_FAIL"),
    ERROR_CODE_BF00111("BF00111", "交易失败", "BIZ_FAIL"),
    ERROR_CODE_BF00116("BF00116", "该终端号不存在", "INF_FAIL"),
    ERROR_CODE_BF00118("BF00118", "报文中密文解析失败", "INF_FAIL"),
    ERROR_CODE_BF00120("BF00120", "报文交易要素缺失", "INF_FAIL"),
    ERROR_CODE_BF00121("BF00121", "报文交易要素格式错误", "BIZ_FAIL"), //报文交易要素格式  密文域中id_holder表示姓名错误，mobile表示手机号错误
    ERROR_CODE_BF00122("BF00122", "卡号和支付通道不匹配", "BIZ_FAIL"),
    ERROR_CODE_BF00123("BF00123", "商户不存在或状态不正常，请联系客服", "INF_FAIL"),
    ERROR_CODE_BF00124("BF00124", "商户与终端号不匹配", "INF_FAIL"),
    ERROR_CODE_BF00125("BF00125", "商户该终端下未开通此类型交易", "INF_FAIL"),
    ERROR_CODE_BF00126("BF00126", "该笔订单已存在", "BIZ_FAIL"),
    ERROR_CODE_BF00127("BF00127", "不支持该支付通道的交易", "INF_FAIL"),
    ERROR_CODE_BF00128("BF00128", "该笔订单不存在", "BIZ_FAIL"),
    ERROR_CODE_BF00129("BF00129", "参数匹配不一致,请联系客服", "INF_FAIL"),
    ERROR_CODE_BF00135("BF00135", "交易金额不正确", "BIZ_FAIL"),
    ERROR_CODE_BF00136("BF00136", "订单创建失败", "BIZ_FAIL"),
    ERROR_CODE_BF00140("BF00140", "该卡已被注销", "BIZ_FAIL"),
    ERROR_CODE_BF00141("BF00141", "该卡已挂失", "BIZ_FAIL"),
    ERROR_CODE_BF00146("BF00146", "订单金额超过单笔限额", "BIZ_FAIL"),
    ERROR_CODE_BF00147("BF00147", "该银行卡不支持此交易", "BIZ_FAIL"),
    ERROR_CODE_BF00177("BF00177", "非法的交易", "INF_FAIL"),
    ERROR_CODE_BF00190("BF00190", "商户流水号不能重复", "INF_FAIL"),
    ERROR_CODE_BF00199("BF00199", "订单日期格式不正确", "INF_FAIL"),
    ERROR_CODE_BF00232("BF00232", "银行卡未开通认证支付", "BIZ_FAIL"),
    ERROR_CODE_BF00233("BF00233", "密码输入次数超限，请联系发卡行", "BIZ_FAIL"),
    ERROR_CODE_BF00234("BF00234", "单日交易金额超限", "BIZ_FAIL"),
    ERROR_CODE_BF00235("BF00235", "单笔交易金额超限", "BIZ_FAIL"),
    ERROR_CODE_BF00236("BF00236", "卡号无效，请确认后输入", "BIZ_FAIL"),
    ERROR_CODE_BF00237("BF00237", "该卡已冻结，请联系发卡行", "BIZ_FAIL"),
    ERROR_CODE_BF00249("BF00249", "订单已过期，请使用新的订单号发起交易", "BIZ_FAIL"),
    ERROR_CODE_BF00251("BF00251", "订单未支付", "BIZ_FAIL"),
    ERROR_CODE_BF00253("BF00253", "交易拒绝", "BIZ_FAIL"),
    ERROR_CODE_BF00258("BF00258", "手机号码校验失败", "BIZ_FAIL"),
    ERROR_CODE_BF00262("BF00262", "交易金额与扣款成功金额不一致，请联系客服", "BIZ_FAIL"),//交易金额与扣款成功金额不一致，请联系宝付
    ERROR_CODE_BF00311("BF00311", "卡类型和接入类型值不匹配", "BIZ_FAIL"),
    ERROR_CODE_BF00312("BF00312", "卡号校验失败", "BIZ_FAIL"),
    ERROR_CODE_BF00313("BF00313", "商户未开通此产品", "INF_FAIL"),
    ERROR_CODE_BF00315("BF00315", "手机号码为空，请重新输入", "BIZ_FAIL"),
    ERROR_CODE_BF00316("BF00316", "ip 未绑定，请联系客服", "INF_FAIL"),//ip 未绑定，请联系宝付
    ERROR_CODE_BF00321("BF00321", "身份证号不合法", "BIZ_FAIL"),
    ERROR_CODE_BF00322("BF00322", "卡类型和卡号不匹配", "BIZ_FAIL"),
    ERROR_CODE_BF00323("BF00323", "商户未开通交易模版", "INF_FAIL"),
    ERROR_CODE_BF00324("BF00324", "暂不支持此银行卡支付，请更换其他银行卡或咨询商户客服", "BIZ_FAIL"),
    ERROR_CODE_BF00325("BF00325", "非常抱歉！目前该银行正在维护中，请更换其他银行卡支付", "INF_FAIL"),
    ERROR_CODE_BF00327("BF00327", "请联系银行核实您的卡状态是否正常", "BIZ_FAIL"),
    ERROR_CODE_BF00331("BF00331", "卡号校验失败", "BIZ_FAIL"),
    ERROR_CODE_BF00332("BF00332", "交易失败，请重新支付", "BIZ_FAIL"),
    ERROR_CODE_BF00333("BF00333", "该卡有风险，发卡行限制交易", "BIZ_FAIL"),
    ERROR_CODE_BF00341("BF00341", "该卡有风险，请持卡人联系银联客服", "BIZ_FAIL"),
    ERROR_CODE_BF00342("BF00342", "单卡单日余额不足次数超限", "BIZ_FAIL"),
    ERROR_CODE_BF00343("BF00343", "验证失败（手机号有误）", "BIZ_FAIL"),
    ERROR_CODE_BF00344("BF00344", "验证失败（卡号有误）", "BIZ_FAIL"),
    ERROR_CODE_BF00345("BF00345", "验证失败（姓名有误）", "BIZ_FAIL"),
    ERROR_CODE_BF00346("BF00346", "验证失败（身份证号有误）", "BIZ_FAIL"),
    ERROR_CODE_BF00347("BF00347", "交易次数频繁，请稍后重试", "BIZ_FAIL"),
    ERROR_CODE_BF00350("BF00350", "该卡当日失败次数已超过3次，请次日再试！", "BIZ_FAIL"),
    ERROR_CODE_BF00351("BF00351", "该卡当日交易笔数超过限制，请次日再试！", "BIZ_FAIL"),
    ERROR_CODE_BF00353("BF00353", "未设置手机号码，请联系发卡行确认", "BIZ_FAIL"),
    ERROR_CODE_BF08701("BF08701", "金额超限，请调整金额或更换其他银行卡", "BIZ_FAIL"),//该卡本次可支付***元，请更换其他银行卡
    ERROR_CODE_BF08702("BF08702", "额度不足，请更换其他银行卡或咨询客服！", "BIZ_FAIL"),//该商户本次可支付***元，请更换其他银行卡或咨询商户客服！
    ERROR_CODE_BF08703("BF08703", "支付金额低于最低金额", "BIZ_FAIL"),//支付金额不能低于最低限额...元！
    ERROR_CODE_BF08704("BF08704", "单笔金额超限，请调整金额或更换其他银行卡", "BIZ_FAIL"),//单笔金额超限，该银行单笔可支付3000.00元！
    ERROR_CODE_BF08705("BF08705", "该卡连续交易失败次数超限", "BIZ_FAIL"),
    ERROR_CODE_BF00424("BF00424", "持卡人身份证已过期", "BIZ_FAIL"),
    ;
    
    /** code */
    private String code;

    /** description */
    private String description;

    /**
     * state:SUCC成功、FAIL失败，UNKNOWN未知
     */
    private String status;

    /**
     * 私有的构造方法
     * @param code
     * @param description
     */
    CutPaymentRespCode(String code, String description, String status) {
        this.status = status;
        this.code = code;
        this.description = description;
    }

    

    /**
     * 
     * @param code
     * @return {@link ActivityState} 实例
     */
    public static CutPaymentRespCode find(String code) {
        for (CutPaymentRespCode key : CutPaymentRespCode.values()) {
            if (key.getCode().equals(code)) {
                return key;
            }
        }
        return null;//throw new GatewayBaseException(GatewayBaseCode.SYSTEM_ERROR, "根据code=" + code + "获取组织类型失败");
    }


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
