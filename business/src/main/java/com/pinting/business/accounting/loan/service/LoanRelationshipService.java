package com.pinting.business.accounting.loan.service;

import com.pinting.business.model.LnLoanRelation;
import com.pinting.business.model.vo.LoanRelation4DepVO;

import java.util.Date;
import java.util.List;

/**
 * 赞分期-借款债权相关
 * @author bianyatian
 * @2016-8-27 上午10:38:05
 */
public interface LoanRelationshipService {
	

	/**
	 * 借款时的债权关系匹配
	 * @param loanId 借款编号
	 * @param lnUserId 借款用户编号
	 * @param lnSubAccountId 借款子账户编号
	 * @param amount 借款金额
	 * @param loanTerm 借款期限（月）
	 * @return
	 */
	List<LnLoanRelation> confirmLoanRelation4Loan(Integer loanId, Integer lnUserId,
												  Integer lnSubAccountId, Double amount, Integer loanTerm);


	/**
	 * 借款时的债权关系匹配(2017/03/27 新版)
	 * @param loanId 借款编号
	 * @param lnUserId 借款用户编号
	 * @param lnSubAccountId 借款子账户编号
	 * @param amount 借款金额
	 * @param loanTerm 借款期限（月）
	 * @return
	 */
	List<LnLoanRelation> confirmLoanRelation4LoanNew(Integer loanId, Integer lnUserId,
												  Integer lnSubAccountId, Double amount, Integer loanTerm);
	
	/**
	 * 借款时的债权关系匹配(2017/03/27 新版)---存管
	 * @param loanId 借款编号
	 * @param lnUserId 借款用户编号
	 * @param lnSubAccountId 借款子账户编号
	 * @param amount 借款金额
	 * @param loanTerm 借款期限（月）
	 * @return
	 */
	List<LoanRelation4DepVO> confirmLoanRelation4LoanNewDep(Integer loanId, Integer lnUserId,
			  Integer lnSubAccountId, Double amount, Integer loanTerm);
	
	/**
	 * 超级理财人债权转让给普通用户
	 * @return
	 */
	boolean  superTransferNormal();
	
	
	/**
	 * 生成理财人还款计划表 
	 * @param loanRelationId 借贷关系编号
	 * @param amount 本金
	 * @param term 期数
	 * @param rate 年化利率， 不需要除100
	 * @param sucDate 成功时间
	 * @return
	 */
	void getFinanceRepayScheduleList(Integer loanRelationId,
									 Double amount, int term, Double rate, Date sucDate);
	
	/**
	 * 还款成功时修改债权相关信息
	 * @param loanId 借款编号
	 * @param repaySerial 期数
	 * @param repayAmount 归还金额（不确定是否需要）
	 */
	void changeLoanRelation4RepaySuccess(Integer loanId, Integer repaySerial, Double repayAmount);
	
	/**
	 * 获取超级用户列表
	 * @return
	 */
	List<Integer> getSuperUserList();
	
	/**
	 * 获取VIP理财人子账户中站岗户ID列表
	 * @return
	 */
	List<Integer> getSuperUserSubAccountIdList();
	
	
	 /**
     * 大额匹配，撮合规则
     *
     * @param baseAmount   基础数金额
     * @param agentId      渠道编号
     * @param borrowAmount 借款金额
     * @param limitAmount  债权匹配时低于该金额的不进行债权承接
     *                     当匹配金额确定后，若剩余金额<limitAmount 且 thisAmount+limitAmount <= baseAmount,则thisAmount = thisAmount+limitAmount
     *                     若剩余金额<limitAmount 且
     * @return thisAmount 撮合结果金额
     */
    Double getThisAmountNew(Double baseAmount, Integer agentId, Double limitAmount, Double borrowAmount);
    
	/**
	 * 获取超级用户列表
	 * @return
	 */
	List<Integer> getSuperUserListBySymbol(String propertySymbol);
	
}
