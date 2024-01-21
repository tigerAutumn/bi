package com.pinting.site.more.enums;

import org.apache.commons.lang.StringUtils;

/**
 * 业务流程service枚举
 */
public enum AgreeVersionServiceEnum {
    //----------------云贷代偿协议(收款确认函)----------------//
    HF_YUNDAI_RECEIPTCONFIRMAGREEMENT_V1_1("1_1", AgreeTypeEnum.HF_YUNDAI_RECEIPTCONFIRMAGREEMENT.getCode(),
            "receiptConfirmYunV11ServiceImpl", "云贷自主放款代偿协议(收款确认函)-消费循环贷"),

    HF_YUNDAI_RECEIPTCONFIRMAGREEMENT_STAGE_V1_1("1_1", AgreeTypeEnum.HF_YUNDAI_RECEIPTCONFIRMAGREEMENT_STAGE.getCode(),
            "receiptConfirmYun4StageV11ServiceImpl", "云贷自主放款代偿协议(收款确认函)-等额本息，等本等息"),
    //----------------云贷代偿协议----------------//


    //----------------云贷代偿协议(通知书)----------------//
    HF_YUNDAI_CREDITORNOTICEAGREEMENT_V1_1("1_1", AgreeTypeEnum.HF_YUNDAI_CREDITORNOTICEAGREEMENT.getCode(),
            "creditorNoticeYunV11ServiceImpl", "云贷自主放款代偿协议(通知书)-消费循环贷"),

    HF_YUNDAI_CREDITORNOTICEAGREEMENT_STAGE_V1_1("1_1", AgreeTypeEnum.HF_YUNDAI_CREDITORNOTICEAGREEMENT_STAGE.getCode(),
            "creditorNoticeYun4StageV11ServiceImpl", "云贷自主放款代偿协议(通知书)-等额本息，等本等息"),
    //----------------云贷代偿协议----------------//
    ;

    private AgreeVersionServiceEnum(String version, String agreeType, String serviceName, String desc) {
        this.version = version;
        this.agreeType = agreeType;
        this.serviceName = serviceName;
        this.desc = desc;
    }

    private String version;
    private String agreeType;
    private String serviceName;
    private String desc;

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getAgreeType() {
        return agreeType;
    }

    public void setAgreeType(String agreeType) {
        this.agreeType = agreeType;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public static AgreeVersionServiceEnum getEnumByServiceCode(String agreeType, String version) {
        if (StringUtils.isBlank(agreeType) || StringUtils.isBlank(version)) {
            return null;
        }
        for (AgreeVersionServiceEnum type : values()) {
            if (type.getAgreeType().equals(agreeType.trim()) && type.getVersion().equals(version.trim()))
                return type;
        }
        return null;
    }

    public static String getEnumByServiceName(String agreeType, String version) {
        if (StringUtils.isBlank(agreeType) || StringUtils.isBlank(version)) {
            return null;
        }
        for (AgreeVersionServiceEnum type : values()) {
            if (type.getAgreeType().equals(agreeType.trim()) && type.getVersion().equals(version.trim()))
                return type.getServiceName();
        }
        return null;
    }
}
