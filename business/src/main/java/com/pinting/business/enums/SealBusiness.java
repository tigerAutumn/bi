/**
 * Wenzi.com Inc.
 * Copyright (c) 2015-2025 All Rights Reserved.
 */
package com.pinting.business.enums;

/**
 * 
 * @author Baby shark love blowing wind
 * @version $Id: SealStatus.java, v 0.1 2015-7-16 下午7:21:59 BabyShark Exp $
 */
public enum SealBusiness {
    /** 出借服务协议签章 */
    BUY("BUY", "授权委托书"),
    BORROW_SERVICES("BORROW_SERVICES","借款咨询与服务协议签章"),
    ZSD_BORROW_SERVICES("ZSD_BORROW_SERVICES","借款咨询与服务协议签章"),
    ZSD_LOAN_AGREEMENT("ZSD_LOAN_AGREEMENT","借款协议"),
    LOAN_AGREEMENT("LOAN_AGREEMENT","借款协议"),
    RECHARGE_DELEGATE_AUTHORIZATION("RECHARGE_DELEGATE_AUTHORIZATION","宝付支付用户服务协议"),
    
    
    SERVICE_FEE_CONFIRM("SERVICE_FEE_CONFIRM","收款确认函（服务费）"),
    DEBT_TRANS_CONFIRM("DEBT_TRANS_CONFIRM","确认函"),
    DEBT_TRANS_NOTICES("DEBT_TRANS_NOTICES","通知书"),
    DEBT_TRANSFER("DEBT_TRANSFER","债权转让协议"),

    /** 理财人债转协议签章 */
    BGW_CLAIMS_AGREEMENT("BGW_CLAIMS_AGREEMENT","债权转让协议"),

    /** 赞分期债转协议签章 */
    ZAN_CLAIMS_AGREEMENT("ZAN_CLAIMS_AGREEMENT","债转协议"),
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
    private SealBusiness(String code, String description) {
        this.code = code;
        this.description = description;
    }

    /**
     * 
     * @param code
     * @return {@link SealBusiness} 实例
     */
    public static SealBusiness find(String code) {
        for (SealBusiness userState : SealBusiness.values()) {
            if (userState.getCode().equals(code)) {
                return userState;
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
