/**
 * Wenzi.com Inc.
 * Copyright (c) 2015-2025 All Rights Reserved.
 */
package com.pinting.gateway.hessian.message.pay19.enums;

/**
 * 
 * @author Baby shark love blowing wind
 * @version $Id: PersistHandling.java, v 0.1 2015-7-28 下午4:38:01 BabyShark Exp $
 */
public enum PersistHandling {
    /** 不执着处理*/
    NO_HANDLING("0", "不执着处理"),
    /** 执着处理*/
    //由于系统问题产生的订单没有结果的情况（例如系统超时）会有19Pay业务人员进行处理;由于参数错误造成的异常（例如卡号姓名不匹配）不会进行执着处理
    HANDLING("1", "执着处理"), ;
    /** code */
    private String code;

    /** description */
    private String description;

    /**
     * 私有的构造方法
     * @param code
     * @param description
     */
    private PersistHandling(String code, String description) {
        this.code = code;
        this.description = description;
    }

    /**
     * 
     * @param code
     * @return {@link PersistHandling} 实例
     */
    public static PersistHandling find(String code) {
        for (PersistHandling transCode : PersistHandling.values()) {
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
