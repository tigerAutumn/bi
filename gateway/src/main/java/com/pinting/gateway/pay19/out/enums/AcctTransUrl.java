/**
 * business.com Inc.
 * Copyright (c) 2015-2025 All Rights Reserved.
 */
package com.pinting.gateway.pay19.out.enums;

/**
 * 19pay 接口url枚举
 * @author Baby shark love blowing wind
 * @version $Id: AcctBalanceUrl.java, v 0.1 2015-5-15 下午6:04:14 BabyShark Exp $
 */
public enum AcctTransUrl {

    /** 账户转账 */
    ACCT_TRANS("acctTrans.do", "账户转账"),
    /** 转账订单查询 */
    QUERY_ACCT_TRANS("queryAcctTrans.do", "转账订单查询"), 
    /** 转账订单查询 (收款方查询订单)*/
    QUERY_RECV_ACCT_TRANS("queryRecvAcctTrans.do", "转账订单查询(收款方查询订单)"), 
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
    private AcctTransUrl(String code, String description) {
        this.code = code;
        this.description = description;
    }

    /**
     * 
     * @param code
     * @return {@link ActivityState} 实例
     */
    public static AcctTransUrl find(String code) {
        for (AcctTransUrl key : AcctTransUrl.values()) {
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
