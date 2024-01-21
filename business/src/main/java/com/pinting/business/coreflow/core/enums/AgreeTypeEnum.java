package com.pinting.business.coreflow.core.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * 协议类型标识
 */
public enum AgreeTypeEnum {
    // --------------代偿协议收款确认函-------------------
    HF_YUNDAI_RECEIPTCONFIRMAGREEMENT("hf_yundai_receiptConfirmAgreement", "代偿协议收款确认函-消费循环贷"),
    HF_YUNDAI_RECEIPTCONFIRMAGREEMENT_STAGE("hf_yundai_receiptConfirmAgreement_stage", "代偿协议收款确认函-等本等息，等额本息"),
    // --------------代偿协议收款确认函-------------------


    // --------------代偿协议通知书-------------------
    HF_YUNDAI_CREDITORNOTICEAGREEMENT("hf_yundai_creditorNoticeAgreement", "代偿协议通知书-消费循环贷"),
    HF_YUNDAI_CREDITORNOTICEAGREEMENT_STAGE("hf_yundai_creditorNoticeAgreement_stage", "代偿协议通知书-等本等息，等额本息"),
    // --------------代偿协议通知书-------------------
    ;

    private AgreeTypeEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    private String code;
    private String name;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * @param code
     * @return
     */
    public static AgreeTypeEnum getEnumByCode(String code) {
        if (null == code) {
            return null;
        }
        for (AgreeTypeEnum type : values()) {
            if (type.getCode().equals(code.trim()))
                return type;
        }
        return null;
    }

    /**
     * 转出Map
     *
     * @return
     */
    public static Map<String, String> toMap() {
        Map<String, String> enumDataMap = new HashMap<String, String>();
        for (AgreeTypeEnum type : values()) {
            enumDataMap.put(type.getCode(), type.getName());
        }
        return enumDataMap;
    }

}
