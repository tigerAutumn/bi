package com.pinting.business.accounting.loan.service;

import com.pinting.business.accounting.loan.model.FinanceRepayInfo;
import com.pinting.business.accounting.loan.model.ReceiveMoneyNoticeInfo;
import com.pinting.business.model.BsLoanFinanceRepay;
import com.pinting.business.model.vo.RepayScheduleVO;

import java.util.List;

/**
 * 理财用户回款服务
 * Created by babyshark on 2016/8/31.
 */
public interface FinanceReceiveMoneyService {

    /**
     * 实时查询当前所有已逾期的还款计划
     * @return 已逾期的还款计划列表
     */
    List<RepayScheduleVO> queryOverdueRepaySchedules();


    /**
     * 生成逾期垫付的回款计划
     * @param financeRepayInfo
     */
    @Deprecated
    void generateOverdueRepayPlan(FinanceRepayInfo financeRepayInfo);

    /**
     * 生成正常还款的回款计划
     * @param financeRepayInfo
     */
    void generateNormalRepayPlan(FinanceRepayInfo financeRepayInfo);

    /**
     * 理财人单笔回款（回款到余额）
     * @param loanFinanceRepay
     */
    void receiveMoney2Balance(BsLoanFinanceRepay loanFinanceRepay);

    /**
     * 理财人单笔回款（回款到卡）
     * @param loanFinanceRepay
     */
    void receiveMoney2Card(BsLoanFinanceRepay loanFinanceRepay);

    /**
     * 通知理财人单笔回款结果（回款到卡）
     * @param notice
     */
    void notifyReceiveMoney2CardResult(ReceiveMoneyNoticeInfo notice);

}
