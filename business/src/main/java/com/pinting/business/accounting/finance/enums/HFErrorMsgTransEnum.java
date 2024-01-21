package com.pinting.business.accounting.finance.enums;

/**
 * Author:      cyb
 * Date:        2017/6/2
 * Description:
 */
public enum HFErrorMsgTransEnum {

    ACOUNT_CAN_WITHDRAW_BALANCE_NOT_ENOUGH("20002", "交易失败", "账户可提现资金不足，无法进行提现！")
    ;

    HFErrorMsgTransEnum(String code, String errorMsg, String oldErrorMsg) {
        this.code = code;
        this.errorMsg = errorMsg;
        this.oldErrorMsg = oldErrorMsg;
    }

    private String code;

    private String errorMsg;

    private String oldErrorMsg;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public String getOldErrorMsg() {
        return oldErrorMsg;
    }

    public void setOldErrorMsg(String oldErrorMsg) {
        this.oldErrorMsg = oldErrorMsg;
    }

    public static HFErrorMsgTransEnum getEnumByCode(String code){
        if (null == code) {
            return null;
        }
        for (HFErrorMsgTransEnum type : values()) {
            if (type.getCode().equals(code.trim()))
                return type;
        }
        return null;
    }
}
