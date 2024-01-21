package com.pinting.business.accounting.loan.enums;

/**
 * Created by 剑钊
 *
 * @2016/9/9 19:55.
 */
public enum AmountTransType {

    AUTHPAY("04311","认证支付"),
    DFINTERFACE("00105","代付"),
    DIRECT("00104","转账"),
    COUNTER("01311","网银"),
    DK("10311","代扣"),
    //其他非系统正常业务码
    PAY_CARD("00313","支付卡类"),
    PAY_MOBILE("06311","快捷银联手机支付（插件支付）"),
    PAY_AUTHPAY_PC("03311","认证支付PC"),
    PAY_AUTHPAY_WAP("05311","认证支付WAP"),
    PAY_AUTHPAY_SDK("08311","认证支付SDK"),
    PAY_REPAY("09311","委托还款"),
    PAY_QUICK("11311","快捷支付"),
    PAY_ACCOUNT("07311","账户"),
    TOPUP_B2C("01301","充值B2C网银/B2B网银/线下打款/快捷"),
    TOPUP_FEE_ACCOUNT("30511","充值手续费账户"),
    WITHDRAW("00101","普通提现"),
    SERVICE_CARD_AUTH("01341","银行卡验证"),
    SERVICE_IDCARD_AUTH("01351","身份证验证"),
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
    AmountTransType(String code, String description) {
        this.code = code;
        this.description = description;
    }

    public static AmountTransType getEnumByCode(String code){
        if (null == code) {
            return null;
        }
        for (AmountTransType type : values()) {
            if (type.getCode().equals(code.trim()))
                return type;
        }
        return null;
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
}
