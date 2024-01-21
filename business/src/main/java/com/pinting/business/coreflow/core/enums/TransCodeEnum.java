package com.pinting.business.coreflow.core.enums;

import com.pinting.business.coreflow.compensate.util.ConstantsForCompensate;
import com.pinting.business.coreflow.loan.util.ConstantsForLoan;
import com.pinting.business.coreflow.repay.util.ConstantsForRepay;
import com.pinting.business.coreflow.transfer.util.ConstantsForTransfer;

import java.util.HashMap;
import java.util.Map;

/**
 * 交易类型标识
 */
public enum TransCodeEnum {
    APPLY_LOAN(ConstantsForLoan.TRANS_CODE_APPLY_LOAN, "借款申请"),

    APPLY_REPAY(ConstantsForRepay.TRANS_CODE_REPAY_APPLY, "还款申请"),
    REPAY_WITHHOLDING(ConstantsForRepay.REPAY_WITHHOLDING, "代扣还款"),
    REPAY_SHARE_PROFIT(ConstantsForRepay.REPAY_SHARE_PROFIT, "代扣结果处理(分账)"),
    TRANSFER_GET_NEED_PAY(ConstantsForTransfer.TRANSFER_GET_NEED_PAY, "债权转让获取承接人应付"),

    COMPENSATE_NOTICE(ConstantsForCompensate.TRANS_CODE_COMPENSATE_NOTICE, "代偿还款通知"),
    COMPENSATE_REPAY_SPLIT(ConstantsForCompensate.TRANS_CODE_COMPENSATE_REPAY_SPLIT, "代偿还款系统分账"),
    COMPENSATE_AGREEMENT(ConstantsForCompensate.TRANS_CODE_COMPENSATE_AGREEMENT, "单笔借款代偿协议"),
    COMPENSATE_NOTIFY_AGREEMENT_DOWNLOAD(ConstantsForCompensate.TRANS_CODE_COMPENSATE_NOTIFY_AGREEMENT_DOWNLOAD, "代偿协议通知下载"),
    ;

    private TransCodeEnum(String code, String name) {
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
    public static TransCodeEnum getEnumByCode(String code) {
        if (null == code) {
            return null;
        }
        for (TransCodeEnum type : values()) {
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
        for (TransCodeEnum type : values()) {
            enumDataMap.put(type.getCode(), type.getName());
        }
        return enumDataMap;
    }

}
