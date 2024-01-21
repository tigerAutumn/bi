package com.pinting.business.accounting.loan.service;

import com.pinting.business.accounting.loan.enums.PartnerEnum;
import com.pinting.business.model.vo.LoanRelation4DepVO;

import java.util.List;

/**
 * 存管固定期限产品债权匹配新规则相关
 *
 * @author zousheng
 * @2018-6-4
 */
public interface DepFixedMatchLoanerInvestorService {

    /**
     * 存管固期，借款时的债权关系匹配(云贷自主、七贷共用)
     *
     * @param loanId         借款编号
     * @param lnUserId       借款用户编号
     * @param lnSubAccountId 借款子账户编号
     * @param amount         借款金额
     * @param loanTerm       借款期限（月）
     * @param partnerEnum    合作方
     * @return
     */
    List<LoanRelation4DepVO> confirmLoanRelation4Loan(Integer loanId, Integer lnUserId,
                                                      Integer lnSubAccountId, Double amount,
                                                      Integer loanTerm, PartnerEnum partnerEnum);
}
