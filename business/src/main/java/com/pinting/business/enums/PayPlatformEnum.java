package com.pinting.business.enums;

/**
 * Author:      cyb
 * Date:        2017/1/22
 * Description: 支付平台
 */
public enum PayPlatformEnum {
    BAOFOO("BAOFOO","宝付"),
    PAY19("PAY19","一九付"),
    OTHER("OTHER","其他辅助支付渠道"),
    HF_BAOFOO("HF_BAOFOO","恒丰宝付"),
    HFBANK("HFBANK","恒丰银行"),
    ;

    private String code;

    private String description;


    PayPlatformEnum(String code, String description){
        this.setCode(code);
        this.setDescription(description);
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
