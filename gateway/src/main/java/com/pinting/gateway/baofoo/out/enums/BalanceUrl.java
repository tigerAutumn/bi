package com.pinting.gateway.baofoo.out.enums;

/**
 * Created by 剑钊 on 2016/7/21.
 */
public enum BalanceUrl {

    /** 余额查询交易*/
    BALANCE_QUERY("BF0001", "余额查询交易"),
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
    BalanceUrl(String code, String description) {
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
