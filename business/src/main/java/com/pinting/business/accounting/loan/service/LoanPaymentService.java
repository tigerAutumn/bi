package com.pinting.business.accounting.loan.service;

import com.pinting.business.accounting.loan.enums.DepTargetEnum;
import com.pinting.business.accounting.loan.model.DFResultInfo;
import com.pinting.business.model.LnBindCard;
import com.pinting.business.model.LnLoan;
import com.pinting.gateway.hessian.message.hfbank.G2BReqMsg_HFBank_OutOfAccount;
import com.pinting.gateway.hessian.message.loan.G2BReqMsg_Loan_Loan;

public interface LoanPaymentService {

    /**
     * 借款申请
     * 借款信息记录，并另起线程进行借款后续操作
     * @param req
     */
    void loan(G2BReqMsg_Loan_Loan req) throws Exception;

    /**
     * 债权匹配并发起支付
     * @param lnLoan
     * @param lnBindCard
     * @param channel
     */
    void matchAndLoanPay(LnLoan lnLoan, LnBindCard lnBindCard, String channel);

    /**
     * 赞分期借款支付处理
     * @param lnLoan
     */
    void payProcessZan(LnLoan lnLoan);

    /**
     * 赞分期出账结果通知接收
     * @param req
     * @param depTargetEnum 如果为出账申请直接失败调用，须传 DepTargetEnum.DEP_TARGET_OPERATE_CHARGE_OFF_PRE；其他情况传null
     */
    void outOfAccountResultAcceptZan(G2BReqMsg_HFBank_OutOfAccount req, DepTargetEnum depTargetEnum);

    /**
     * 借款结果通知处理
     * @param req
     */
    @Deprecated
    void notifyLoan(DFResultInfo req);

    /**
     * 借款结果通知合作方
     * @param lnLoan
     */
    void notifyLoan2Partner(LnLoan lnLoan,String errorMsg);

}
