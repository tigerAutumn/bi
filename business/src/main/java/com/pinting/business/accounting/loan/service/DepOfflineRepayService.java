package com.pinting.business.accounting.loan.service;

import java.util.List;

import com.pinting.business.accounting.loan.model.*;

/**
 * 存管体系-线下还款服务
 * Created by babyshark on 2017/4/4.
 */
public interface DepOfflineRepayService {

    /**
     * （批量）提现到恒丰还款实体户（卡）
     * @param req
     */
    void withdraw2DepRepayCard(Withdraw2DepRepayCardReq req);

    /**
     * 提现到恒丰还款实体户（卡）结果处理
     * @param req
     */
    void notifyWithdraw2DepRepayCardResult(DFResultInfo req);

    /**
     * （批量）代扣还款到借款人/代偿人
     * @param req
     */
    void cutRepay2Borrower(CutRepay2BorrowerReq req);

    /**
     * 代扣还款到借款人结果处理
     * @param req
     */
    void notifyCutRepay2BorrowerResult(RepayResultInfo req);

    /**
     * （批量）借款人还款至标的
     * @param req
     */
    void repay2DepTarget(Repay2DepTargetReq req);

    /**
     * （单个标的）代偿人还款至标的
     * @param req
     */
    void compRepay2DepTarget(DepRepaySchedule req);

    /**
     * 标的还款至投资人账户
     * @param req
     */
    void repay2Investor(Repay2InvestorReq req);
    
    
    /**
     * 标的还款至投资人账户成功，处理
     * @param req
     */
    void repay2InvestorSucc(Repay2InvestorReq req,List<RepayInfo> yunAccountList);

}
