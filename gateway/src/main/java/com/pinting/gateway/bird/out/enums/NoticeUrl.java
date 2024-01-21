package com.pinting.gateway.bird.out.enums;

/**
 * Created by 剑钊 on 2016/7/21.
 */
public enum NoticeUrl {

    /** 借款通知*/
    LOAN_NOTICE("/credit-payment/api/v1/loan/notify", "借款通知"),
    /** 还款通知*/
    REPAY_NOTICE("/credit-payment/api/v1/repay/notify", "还款通知"),
    /** 银行限额推送*/
    BANK_LIMIT_NOTICE("/credit-payment/api/v1/bankLimit/notify", "银行限额推送"),
    /** token获取 */
    ZAN_TOKEN("/cif/v2/auth/tenant-token", "token获取"),
    /** 营销代付通知*/
    MARKET_NOTICE("/credit-payment/api/v1/withdraw/notify", "营销代付通知"),
    
    
    /** 借款通知2.0*/
    LOAN_NOTICE_V2("/loan/notify", "借款通知2.0"),
    /** 还款通知2.0*/
    REPAY_NOTICE_V2("/repay/notify", "还款通知2.0"),
    /** 银行限额推送*/
    BANK_LIMIT_NOTICE_V2("/banklimits", "银行限额推送2.0"),
    /** 营销代付通知2.0*/
    MARKET_NOTICE_V2("/withdraw/notify", "营销代付通知2.0"),
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
    NoticeUrl(String code, String description) {
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
}
