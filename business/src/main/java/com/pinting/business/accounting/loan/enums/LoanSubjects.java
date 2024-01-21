package com.pinting.business.accounting.loan.enums;

/**
 * Created by 剑钊 on 2016/8/23.
 */
public enum LoanSubjects {
    //科目类别
    SUBJECT_CODE_PRINCIPAL("PRINCIPAL","本金"),
    SUBJECT_CODE_INTEREST("INTEREST","利息"),
    SUBJECT_CODE_LOAN_SERVICE_FEE("LOAN_SERVICE_FEE","借款手续费"),
    SUBJECT_CODE_SERVICE_FEE("SERVICE_FEE","手续费"),
    SUBJECT_CODE_SUPERVISE_FEE("SUPERVISE_FEE","监管费"),
    SUBJECT_CODE_INFO_SERVICE_FEE("INFO_SERVICE_FEE","信息服务费"),
    SUBJECT_CODE_ACCOUNT_MANAGE_FEE("ACCOUNT_MANAGE_FEE","账户管理费"),
    SUBJECT_CODE_PREMIUM("PREMIUM","保费"),
    SUBJECT_CODE_PROMOTE("PROMOTE","优惠金额"),
    SUBJECT_CODE_LATE_FEE("LATE_FEE","滞纳金"),
    SUBJECT_CODE_OTHER("OTHER","其他"),
    SUBJECT_CODE_PRINCIPAL_OVERDUE("PRINCIPAL_OVERDUE","本金逾期费"),
    SUBJECT_CODE_INTEREST_OVERDUE("INTEREST_OVERDUE","利息逾期费"),
    SUBJECT_CODE_PENALTY("OTHER","违约金"),
    
    // 赞时贷
    SUBJECT_CODE_PLATFORM_SERVICE_FEE("PLATFORM_SERVICE_FEE", "平台服务费"),
    SUBJECT_CODE_INFO_CERTIFIED_FEE("INFO_CERTIFIED_FEE", "信息认证费"),
    SUBJECT_CODE_RISK_MANAGE_SERVICE_FEE("RISK_MANAGE_SERVICE_FEE", "风控服务费"),
    SUBJECT_CODE_COLLECTION_CHANNEL_FEE("COLLECTION_CHANNEL_FEE", "代收通道费"),
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
    LoanSubjects(String code, String description) {
        this.code = code;
        this.description = description;
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
