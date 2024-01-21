package com.pinting.business.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * Author:      cyb
 * Date:        2016/8/25
 * Description: 宝付应答码
 */
public enum BaoFooResCode {

    /** 成功*/
    SUCCESS_CODE_00000("0000", "处理成功"),

    SUCCESS_CODE_BF00100("BF00100", "支付平台异常"),
    SUCCESS_CODE_BF00101("BF00101", "持卡人信息有误"),
    SUCCESS_CODE_BF00102("BF00102", "银行卡已过有效期"),
    SUCCESS_CODE_BF00103("BF00103", "账户余额不足"),
    SUCCESS_CODE_BF00104("BF00104", "交易金额超限"),
    SUCCESS_CODE_BF00105("BF00105", "短信验证码错误"),
    SUCCESS_CODE_BF00106("BF00106", "短信验证码失效"),
    SUCCESS_CODE_BF00107("BF00107", "当前银行卡不支持该业务"),
    SUCCESS_CODE_BF00108("BF00108", "交易失败，请联系发卡行"),
    SUCCESS_CODE_BF00109("BF00109", "交易金额低于限额"),
    SUCCESS_CODE_BF00110("BF00110", "该卡暂不支持此交易"),
    SUCCESS_CODE_BF00111("BF00111", "交易失败"),
    SUCCESS_CODE_BF00112("BF00112", "系统繁忙，请稍后再试"),
    SUCCESS_CODE_BF00113("BF00113", "交易结果未知，请稍后查询"),
    SUCCESS_CODE_BF00114("BF00114", "订单已支付成功，请勿重复支付"),
    SUCCESS_CODE_BF00115("BF00115", "交易处理中，请稍后查询"),
    SUCCESS_CODE_BF00116("BF00116", "该终端号不存在"),
    SUCCESS_CODE_BF00117("BF00117", "交易金额超限，请联系客服"),
    SUCCESS_CODE_BF00118("BF00118", "报文中密文解析失败"),
    SUCCESS_CODE_BF00119("BF00119", "短信验证超时，请稍后再试"),
    SUCCESS_CODE_BF00120("BF00120", "报文交易要素缺失"),
    SUCCESS_CODE_BF00121("BF00121", "报文交易要素格式错误"),
    SUCCESS_CODE_BF00122("BF00122", "卡号和支付通道不匹配"),
    SUCCESS_CODE_BF00123("BF00123", "商户不存在或状态不正常，请联系客服"),
    SUCCESS_CODE_BF00124("BF00124", "商户与终端号不匹配"),
    SUCCESS_CODE_BF00125("BF00125", "商户该终端下未开通此类型交易"),
    SUCCESS_CODE_BF00126("BF00126", "该笔订单已存在"),
    SUCCESS_CODE_BF00127("BF00127", "不支持该支付通道的交易"),
    SUCCESS_CODE_BF00128("BF00128", "该笔订单不存在"),
    SUCCESS_CODE_BF00129("BF00129", "参数匹配不一致,请联系客服"),
    SUCCESS_CODE_BF00130("BF00130", "请确认是否发送短信,当前交易必须通过短信验证"),
    SUCCESS_CODE_BF00131("BF00131", "当前交易信息与短信交易信息不一致,请核对信息"),
    SUCCESS_CODE_BF00132("BF00132", "短信验证超时，请稍后再试"),
    SUCCESS_CODE_BF00133("BF00133", "短信验证失败"),
    SUCCESS_CODE_BF00134("BF00134", "绑定关系不存在"),
    SUCCESS_CODE_BF00135("BF00135", "交易金额不正确"),
    SUCCESS_CODE_BF00136("BF00136", "订单创建失败"),
    SUCCESS_CODE_BF00137("BF00137", "个人会员不能为空"),
    SUCCESS_CODE_BF00138("BF00138", "个人会员不存在"),
    SUCCESS_CODE_BF00140("BF00140", "该卡已被注销"),
    SUCCESS_CODE_BF00141("BF00141", "该卡已挂失"),
    SUCCESS_CODE_BF00142("BF00142", "暂不支持该银行卡的绑卡"),
    SUCCESS_CODE_BF00143("BF00143", "绑卡失败"),
    SUCCESS_CODE_BF00144("BF00144", "该交易有风险,订单处理中"),
    SUCCESS_CODE_BF00146("BF00146", "订单金额超过单笔限额"),
    SUCCESS_CODE_BF00147("BF00147", "该银行卡不支持此交易"),
    SUCCESS_CODE_BF00177("BF00177", "非法的交易"),
    SUCCESS_CODE_BF00180("BF00180", "获取短信验证码失败"),
    SUCCESS_CODE_BF00182("BF00182", "您输入的银行卡号有误，请重新输入"),
    SUCCESS_CODE_BF00186("BF00186", "该卡已绑定"),
    SUCCESS_CODE_BF00187("BF00187", "暂不支持信用卡的绑定"),
    SUCCESS_CODE_BF00188("BF00188", "绑卡失败"),
    SUCCESS_CODE_BF00189("BF00189", "交易金额超过限额"),
    SUCCESS_CODE_BF00190("BF00190", "商户流水号不能重复"),
    SUCCESS_CODE_BF00191("BF00191", "绑定id和用户id不匹配"),
    SUCCESS_CODE_BF00192("BF00192", "标的开始日期格式不正确"),
    SUCCESS_CODE_BF00193("BF00193", "标的结束日期格式不正确"),
    SUCCESS_CODE_BF00194("BF00194", "标的到期还款日期格式不正确"),
    SUCCESS_CODE_BF00195("BF00195", "交易金额不正确"),
    SUCCESS_CODE_BF00196("BF00196", "标的金额不正确"),
    SUCCESS_CODE_BF00197("BF00197", "还款总金额不正确"),
    SUCCESS_CODE_BF00198("BF00198", "年化率格式不正确"),
    SUCCESS_CODE_BF00199("BF00199", "订单日期格式不正确"),
    SUCCESS_CODE_BF00200("BF00200", "发送短信和支付时商户订单号不一致"),
    SUCCESS_CODE_BF00201("BF00201", "发送短信和支付交易时金额不相等"),
    SUCCESS_CODE_BF00202("BF00202", "交易超时，请稍后查询"),
    SUCCESS_CODE_BF00203("BF00203", "退款交易已受理"),
    SUCCESS_CODE_BF00204("BF00204", "确认绑卡时与预绑卡时的商户订单号不一致"),
    SUCCESS_CODE_BF00232("BF00232", "银行卡未开通认证支付"),
    SUCCESS_CODE_BF00233("BF00233", "密码输入次数超限，请联系发卡行"),
    SUCCESS_CODE_BF00234("BF00234", "单日交易金额超限"),
    SUCCESS_CODE_BF00235("BF00235", "单笔交易金额超限"),
    SUCCESS_CODE_BF00236("BF00236", "卡号无效，请确认后输入"),
    SUCCESS_CODE_BF00237("BF00237", "该卡已冻结，请联系发卡行"),
    SUCCESS_CODE_BF00238("BF00238", "交易结果未知，请稍后查"),
    SUCCESS_CODE_BF00309("BF00309", "绑卡和发送短信时手机号不一致"),
    SUCCESS_CODE_BF00311("BF00311", "卡类型和接入类型值不匹配"),
    SUCCESS_CODE_BF00312("BF00312", "卡号校验失败"),
    SUCCESS_CODE_BF00313("BF00313", "商户请求IP不合法"),


    SUCCESS_CODE_BF00315("BF00108", "手机号码为空，请重新输入")
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
    BaoFooResCode(String code, String description) {
        this.code = code;
        this.description = description;
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

    public static BaoFooResCode getEnumByCode(String code){
        if (null == code) {
            return null;
        }
        for (BaoFooResCode type : values()) {
            if (type.getCode().equals(code.trim()))
                return type;
        }
        return null;
    }

    /**
     * 转出Map
     * @return
     */
    public static Map<String, Object> toMap() {
        Map<String, Object> enumDataMap = new HashMap<String, Object>();
        for (BaoFooResCode type : values()) {
            enumDataMap.put(type.getCode(), type.getDescription());
        }
        return enumDataMap;
    }
}
