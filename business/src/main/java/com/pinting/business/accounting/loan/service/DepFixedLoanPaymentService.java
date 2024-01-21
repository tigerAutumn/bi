package com.pinting.business.accounting.loan.service;

import com.pinting.business.accounting.finance.model.DFResultInfo;
import com.pinting.business.accounting.loan.enums.DepTargetEnum;
import com.pinting.business.accounting.loan.enums.PartnerEnum;
import com.pinting.business.accounting.loan.model.BindCardInfoReq;
import com.pinting.business.accounting.loan.model.DepFixedLoanFailReq;
import com.pinting.business.model.LnBindCard;
import com.pinting.business.model.LnDepositionTarget;
import com.pinting.business.model.LnLoan;
import com.pinting.gateway.hessian.message.dafy.G2BReqMsg_DafyLoan_ApplyLoan;
import com.pinting.gateway.hessian.message.dafy.G2BReqMsg_DafyLoan_QueryLoanResult;
import com.pinting.gateway.hessian.message.dafy.G2BResMsg_DafyLoan_QueryLoanResult;
import com.pinting.gateway.hessian.message.hfbank.G2BReqMsg_HFBank_OutOfAccount;
import com.pinting.gateway.hessian.message.zsd.G2BReqMsg_ZsdLoanApply_AddLoan;
import com.pinting.gateway.hessian.message.zsd.G2BReqMsg_ZsdLoanApply_QueryLoan;
import com.pinting.gateway.hessian.message.zsd.G2BResMsg_ZsdLoanApply_QueryLoan;
import com.pinting.gateway.hessian.message.zsd.model.OpenAccountReq;

/**
 * Created by zhangbao on 2017/4/1.
 *  自主放款借款服务
 */
public interface DepFixedLoanPaymentService {

    /**
     * 云贷/7贷自主放款借款申请
     * @param req
     * @throws Exception
     */
    void loanApply(G2BReqMsg_DafyLoan_ApplyLoan req, PartnerEnum partnerEnum);

    /**
     * 赞时贷借款申请（包含借款人登记、绑卡）
     * @param req   请求信息
     */
    void loanApply(G2BReqMsg_ZsdLoanApply_AddLoan req);

    /**
     * 借款人开户登记（赞时贷、云贷）
     * @param req
     * @return
     */
    Integer openAccount(OpenAccountReq req);

    /**
     * 借款人绑卡（赞时贷、云贷）
     * @param req
     */
    void bindCard(BindCardInfoReq req);

    /**
     * 债权匹配+代付（云贷自主+赞时贷）
     * @param lnLoan
     * @param lnBindCard
     * @param channel
     */
    void matchAndLoanPay(LnLoan lnLoan, LnBindCard lnBindCard, String channel);

    /**
     * 支付处理（云贷自主+赞时贷）
     */
    void payProcess(LnLoan lnLoan);

    /**
     * 出账结果通知接收
     * @param req
     * @param depTargetEnum 如果为出账申请直接失败调用，须传 DepTargetEnum.DEP_TARGET_OPERATE_CHARGE_OFF_PRE；其他情况传null
     */
    void outOfAccountResultAccept(G2BReqMsg_HFBank_OutOfAccount req, DepTargetEnum depTargetEnum, PartnerEnum partnerEnum);

    /**
     * 出账结果通知接收，控制是否通知合作方
     * @param req
     * @param depTargetEnum
     * @param partner
     * @param isNotify ture：通知合作方 false：不通知合作方
     */
    void outOfAccountResultAccept(G2BReqMsg_HFBank_OutOfAccount req, DepTargetEnum depTargetEnum, PartnerEnum partner, boolean isNotify);

    /**
     * 放款结果通知云贷
     * @param lnLoan
     * @param errorMsg
     */
    public void notifyLoan2YunDai(LnLoan lnLoan,String errorMsg, String agreementNo);
    
    /**
     * 债权匹配失败处理（云贷自主+赞时贷）
     * @param matchFailReq
     */
    void matchFail(DepFixedLoanFailReq matchFailReq);
    
    /**
     * 批量投标失败处理（云贷自主+赞时贷）
     * @param failReq
     */
    void buyActionFail(DepFixedLoanFailReq failReq);

    /**
     * 云贷/7贷查询放款结果
     * @param req
     */
    void loanResultQuery(G2BReqMsg_DafyLoan_QueryLoanResult req, G2BResMsg_DafyLoan_QueryLoanResult res, PartnerEnum partnerEnum);

    /**
     * 借款人代付提现
     * @param resultInfo
     */
    void borrowerDFWithdraw(DFResultInfo resultInfo);

    /**
     * 借款结果通知赞时贷
     * @param lnLoan
     * @param errorMsg
     */
    void noticeLoan2Dsd(LnLoan lnLoan, final String errorMsg);

    /**
     * 赞时贷查询借款结果
     * @param req
     * @param res
     */
    void zsdLoanResultQuery(G2BReqMsg_ZsdLoanApply_QueryLoan req, G2BResMsg_ZsdLoanApply_QueryLoan res);

    /**
     * 借款结果通知7贷
     * @param lnLoan
     * @param errorMsg
     */
    void noticeLoan2Seven(LnLoan lnLoan, final String errorMsg, String agreementNo);
    
    /**
     * 退票处理
     * @param lnLoan
     */
    void doRefundTicket(LnLoan lnLoan);

    /**
     * 债券回退
     * @param lnLoan
     */
    void backLoanDebtFinancing(LnLoan lnLoan);

    /**
     * 债券回退
     * @param lnLoan
     * @param orderNo
     * @param depositionTarget
     * @param depTargetEnum
     */
    void backLoanDebtFinancing(LnLoan lnLoan, String orderNo, LnDepositionTarget depositionTarget, DepTargetEnum depTargetEnum);
}
