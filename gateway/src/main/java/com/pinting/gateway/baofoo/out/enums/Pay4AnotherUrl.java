package com.pinting.gateway.baofoo.out.enums;

/**
 * Created by 剑钊 on 2016/7/21.
 */
public enum  Pay4AnotherUrl {

    /** 代付 转账交易*/
    AGENT_PAY("BF0040001.do", "代付转账交易"),
    /** 代付 转账交易状态查询*/
    AGENT_PAY_STATUS_QUERY("BF0040002.do", "代付转账交易状态查询"),

    REFUND_STATUS_QUERY("BF0040003.do","退款状态查询"),

    APPLY_WHITE_LIST("BF0040005.do","申请白名单"),

    ONLINE_TRANS("BF0040007.do","宝付账户间转账"),
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
    Pay4AnotherUrl(String code, String description) {
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
