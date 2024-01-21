package com.pinting.business.accounting.loan.service;

import com.pinting.business.model.LnDailyAmount;

/**
 * 每日额度管理
 */
public interface LnDailyAmountService {

    /**
     * 查询资产方每日可用额度信息
     * @param partnerCode 资产方标识
     * @return
     */
    LnDailyAmount getLnDailyAmount4Avaliable (String partnerCode);

    /**
     * 借款申请，资产方每日可用总额度 - 借款金额
     * @param loanAmount 借款金额
     * @param lnDailyAmountId 每日额度记录ID
     */
    void chargeLoannDailyAmountFreeze(Double loanAmount, Integer lnDailyAmountId);

    /**
     * 借款失败，资产方每日可用总额度 + 借款金额
     *
     * @param loanAmount 借款金额
     * @param lnDailyAmountId 每日额度记录ID
     */
    void chargeLoannDailyAmountUnFreeze(Double loanAmount, Integer lnDailyAmountId);
}