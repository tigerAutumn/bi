package com.pinting.gateway.zsd.out.enums;

/**
 * Created by 剑钊 on 2016/7/21.
 */
public enum NoticeUrl {
	
    /** token获取 */
    ZSD_TOKEN("/cif/v2/auth/tenant-token", "token获取"),

    /** 借款通知*/
    LOAN_NOTICE("/loan/notify", "借款通知"),
    /** 还款通知*/
    REPAY_NOTICE("/repay/notify", "还款通知"),
    /** 银行限额推送*/
    BANK_LIMIT_NOTICE("/banklimits", "银行限额推送"),
    /** 营销代付通知*/
    MARKET_NOTICE("/withdraw/notify", "营销代付通知"),
    /** 借款协议签章结果*/
    SIGN_RESULT_NOTICE("/signresult/notify", "借款协议签章结果"),
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
