/**
 * Wenzi.com Inc.
 * Copyright (c) 2015-2025 All Rights Reserved.
 */
package com.pinting.gateway.hessian.message.pay19.enums;

/**
 * 是否保存快捷支付绑定信息
 * @author Baby shark love blowing wind
 * @version $Id: IsSaveBind.java, v 0.1 2015-7-28 下午4:38:01 BabyShark Exp $
 */
public enum IsSaveBind {
    /** 保存快捷支付绑定信息*/
    SAVE_BIND("0", "保存快捷支付绑定信息"),
    /** 私*/
    UN_SAVE_BIND("1", "不保存快捷支付绑定信息"), ;

    /** code */
    private String code;

    /** description */
    private String description;

    /**
     * 私有的构造方法
     * @param code
     * @param description
     */
    private IsSaveBind(String code, String description) {
        this.code = code;
        this.description = description;
    }

    /**
     * 
     * @param code
     * @return {@link IsSaveBind} 实例
     */
    public static IsSaveBind find(String code) {
        for (IsSaveBind transCode : IsSaveBind.values()) {
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
