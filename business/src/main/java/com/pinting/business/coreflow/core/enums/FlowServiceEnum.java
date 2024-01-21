package com.pinting.business.coreflow.core.enums;

import com.pinting.business.accounting.loan.enums.PartnerEnum;
import com.pinting.business.coreflow.core.util.ConstantsForCore;

/**
 * 业务流程service枚举
 */
public enum FlowServiceEnum {
    //----------------借款流程 start----------------//
    LOAN_DEFAULT_STEP1("step1", TransCodeEnum.APPLY_LOAN.getCode() + ConstantsForCore.DEFAULT_SERVICE_IMPL, "loanApply4DefaultServiceImpl", "默认服务借款申请"),
    LOAN_YUN_DAI_SELF_AVERAGE_CAPITAL_PLUS_INTEREST_STEP1("step1", TransCodeEnum.APPLY_LOAN.getCode() + PartnerEnum.YUN_DAI_SELF.getCode() + BusinessTypeEnum.AVERAGE_CAPITAL_PLUS_INTEREST.getCode(),
            "loanApply4AverageCapitalPlusInterestServiceImpl", "云贷等额本息借款申请"),
    LOAN_YUN_DAI_SELF_EQUAL_PRINCIPAL_INTEREST_STEP1("step1", TransCodeEnum.APPLY_LOAN.getCode() + PartnerEnum.YUN_DAI_SELF.getCode() + BusinessTypeEnum.EQUAL_PRINCIPAL_INTEREST.getCode(),
            "loanApply4EqualPrincipalInterestServiceImpl", "云贷等本本息借款申请"),
    //----------------借款流程 end----------------//



    //----------------还款流程 start----------------//
    REPAY_DEFAULT_STEP1("step1", TransCodeEnum.APPLY_REPAY.getCode() + ConstantsForCore.DEFAULT_SERVICE_IMPL, "repayApply4DefaultServiceImpl", "默认服务还款申请"),
    REPAY_YUN_DAI_SELF_WITHHOLDING_STEP2("step2", 
    		TransCodeEnum.REPAY_WITHHOLDING.getCode()+PartnerEnum.YUN_DAI_SELF.getCode(),
    		"repayWithholding4ServiceImpl", "代扣还款"),
    REPAY_YUN_DAI_SELF_SHARE_PROFIT_STEP31("step31", 
    		TransCodeEnum.REPAY_SHARE_PROFIT.getCode() + PartnerEnum.YUN_DAI_SELF.getCode() + BusinessTypeEnum.AVERAGE_CAPITAL_PLUS_INTEREST.getCode(),
            "repayShareProfit4AverageCapitalPlusInterestServiceImpl", "云贷等额本息结果处理"),
    REPAY_YUN_DAI_SELF_SHARE_PROFIT_STEP32("step32", 
    		TransCodeEnum.REPAY_SHARE_PROFIT.getCode() + PartnerEnum.YUN_DAI_SELF.getCode() + BusinessTypeEnum.EQUAL_PRINCIPAL_INTEREST.getCode(),
            "repayShareProfit4EqualPrincipalInterestServiceImpl", "云贷等本本息结果处理"),
    //----------------还款流程 end----------------//


    //----------------代偿还款流程 start----------------//
    COMPENSATE_DEFAULT_STEP1("step1", TransCodeEnum.COMPENSATE_NOTICE.getCode() + ConstantsForCore.DEFAULT_SERVICE_IMPL, "compensateNotice4DefaultServiceImpl", "默认服务代偿还款通知"),

    COMPENSATE_DEFAULT_STEP2("step2", TransCodeEnum.COMPENSATE_REPAY_SPLIT.getCode() + ConstantsForCore.DEFAULT_SERVICE_IMPL, "compensateRepaySplit4DefaultServiceImpl", "默认服务代偿还款系统分账"),
    COMPENSATE_YUN_DAI_SELF_AVERAGE_CAPITAL_PLUS_INTEREST_STEP2("step2", TransCodeEnum.COMPENSATE_REPAY_SPLIT.getCode() + PartnerEnum.YUN_DAI_SELF.getCode() + BusinessTypeEnum.AVERAGE_CAPITAL_PLUS_INTEREST.getCode(),
            "compensateRepaySplit4AverageCapitalPlusInterestServiceImpl", "云贷等额本息代偿还款系统分账"),
    COMPENSATE_YUN_DAI_SELF_EQUAL_PRINCIPAL_INTEREST_STEP2("step2", TransCodeEnum.COMPENSATE_REPAY_SPLIT.getCode() + PartnerEnum.YUN_DAI_SELF.getCode() + BusinessTypeEnum.EQUAL_PRINCIPAL_INTEREST.getCode(),
            "compensateRepaySplit4EqualPrincipalInterestServiceImpl", "云贷等本等息代偿还款系统分账"),

    COMPENSATE_DEFAULT_STEP3("step3", TransCodeEnum.COMPENSATE_AGREEMENT.getCode() + ConstantsForCore.DEFAULT_SERVICE_IMPL, "compensateAgreement4DefaultServiceImpl", "默认服务单笔借款代偿签章协议"),
    COMPENSATE_YUN_DAI_SELF_AVERAGE_CAPITAL_PLUS_INTEREST_STEP3("step3", TransCodeEnum.COMPENSATE_AGREEMENT.getCode() + PartnerEnum.YUN_DAI_SELF.getCode() + BusinessTypeEnum.AVERAGE_CAPITAL_PLUS_INTEREST.getCode(),
            "compensateAgreement4AverageCapitalPlusInterestServiceImpl", "云贷等额本息单笔借款代偿签章协议"),
    COMPENSATE_YUN_DAI_SELF_EQUAL_PRINCIPAL_INTEREST_STEP3("step3", TransCodeEnum.COMPENSATE_AGREEMENT.getCode() + PartnerEnum.YUN_DAI_SELF.getCode() + BusinessTypeEnum.EQUAL_PRINCIPAL_INTEREST.getCode(),
            "compensateAgreement4EqualPrincipalInterestServiceImpl", "云贷等本等息单笔借款代偿签章协议"),

    COMPENSATE_DEFAULT_STEP4("step4", TransCodeEnum.COMPENSATE_NOTIFY_AGREEMENT_DOWNLOAD.getCode() + ConstantsForCore.DEFAULT_SERVICE_IMPL, "compensateNotifyAgreementDownload4DefaultServiceImpl", "默认服务代偿协议通知下载"),
    //----------------代偿还款流程 end----------------//

    //----------------债权转让流程 start----------------//
    TRANSFER_DEFULT_STEP1("step1",TransCodeEnum.TRANSFER_GET_NEED_PAY.getCode() + ConstantsForCore.DEFAULT_SERVICE_IMPL,
    		"transferGetNeedPay4DefaultServiceImpl","默认债转获取承接人应付数据"), //默认为七贷
    YUN_DAI_SELF_REPAY_ANY_TIME_STEP1("step1",TransCodeEnum.TRANSFER_GET_NEED_PAY.getCode() + PartnerEnum.YUN_DAI_SELF.getCode() + BusinessTypeEnum.REPAY_ANY_TIME.getCode(),
    		"transferGetNeedPay4YunRepayAnyTimeServiceImpl","云贷随借随还债转获取承接人应付数据"),
    YUN_DAI_SELF_AVERAGE_CAPITAL_PLUS_INTEREST_STEP1("step1",TransCodeEnum.TRANSFER_GET_NEED_PAY.getCode() + PartnerEnum.YUN_DAI_SELF.getCode() + BusinessTypeEnum.AVERAGE_CAPITAL_PLUS_INTEREST.getCode(),
    		"transferGetNeedPay4YunFixedInstallmentServiceImpl","云贷等额本息债转获取承接人应付数据"),
    YUN_DAI_SELF_EQUAL_PRINCIPAL_INTEREST_STEP1("step1",TransCodeEnum.TRANSFER_GET_NEED_PAY.getCode() + PartnerEnum.YUN_DAI_SELF.getCode() + BusinessTypeEnum.EQUAL_PRINCIPAL_INTEREST.getCode(),
    	    "transferGetNeedPay4YunFixedPrincipalInterestServiceImpl","云贷等本等息债转获取承接人应付数据"),
    //----------------债权转让流程 send----------------//

    ;

    private FlowServiceEnum(String step, String serviceCode, String serviceName, String desc) {
        this.step = step;
        this.serviceCode = serviceCode;
        this.serviceName = serviceName;
        this.desc = desc;
    }

    private String step;
    private String serviceCode;
    private String serviceName;
    private String desc;

    public String getStep() {
        return step;
    }

    public void setStep(String step) {
        this.step = step;
    }

    public String getServiceCode() {
        return serviceCode;
    }

    public void setServiceCode(String serviceCode) {
        this.serviceCode = serviceCode;
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

    /**
     * @param serviceCode
     * @return
     */
    public static FlowServiceEnum getEnumByServiceCode(String serviceCode) {
        if (null == serviceCode) {
            return null;
        }
        for (FlowServiceEnum type : values()) {
            if (type.getServiceCode().equals(serviceCode.trim()))
                return type;
        }
        return null;
    }

    /**
     * @param serviceCode
     * @return
     */
    public static String getEnumByServiceName(String serviceCode) {
        if (null == serviceCode) {
            return null;
        }
        for (FlowServiceEnum type : values()) {
            if (type.getServiceCode().equals(serviceCode.trim()))
                return type.getServiceName();
        }
        return null;
    }
}
