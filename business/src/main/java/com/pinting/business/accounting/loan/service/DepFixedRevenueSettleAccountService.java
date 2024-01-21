package com.pinting.business.accounting.loan.service;

/**
 * Author:      cyb
 * Date:        2017/4/8
 * Description: 营收结算记账服务（砍头息、还款营收、重复还款）
 */
public interface DepFixedRevenueSettleAccountService {


    /**
     * 砍头息、还款营收、重复还款结算记账公共方法
     * @param sysAccountCode 系统账户code: REVENUE_YUN_DEP, FEE_YUN_DEP
     * @param settleAmount 结算总金额
     * @param revenueFlag REPAY_REPEAT:重复还款,LOAN_FEE:借款手续费,REPAY_REVENUE:还款营收
     */
    void revenueSettleRecord(String sysAccountCode, Double settleAmount, String revenueFlag);

}
