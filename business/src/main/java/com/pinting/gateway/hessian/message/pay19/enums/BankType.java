/**
 * Wenzi.com Inc.
 * Copyright (c) 2015-2025 All Rights Reserved.
 */
package com.pinting.gateway.hessian.message.pay19.enums;

/**
 * 
 * @author Baby shark love blowing wind
 * @version $Id: BankType.java, v 0.1 2015-7-28 下午4:38:01 BabyShark Exp $
 */
public enum BankType {
    /** 借*/
    DEBITCARD("DEBIT", "借记卡"),
    /** 贷*/
    CREDITCARD("CREDIT", "贷记卡"), ;

    /** code */
    private String code;

    /** description */
    private String description;

    /**
     * 私有的构造方法
     * @param code
     * @param description
     */
    private BankType(String code, String description) {
        this.code = code;
        this.description = description;
    }

    /**
     * 
     * @param code
     * @return {@link BankType} 实例
     */
    public static BankType find(String code) {
        for (BankType transCode : BankType.values()) {
            if (transCode.getCode().equals(code)) {
                return transCode;
            }
        }
        return null;
    }

    public String getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }
}
