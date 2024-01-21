package com.pinting.business.accounting.loan.service;

import com.pinting.business.accounting.loan.enums.PartnerEnum;
import com.pinting.business.accounting.loan.model.LoanRelation4TransferVO;
import com.pinting.business.model.LnFinanceRepaySchedule;
import com.pinting.business.model.vo.BsSubAccountVO4DepFixedMatch;
import com.pinting.business.model.vo.LoanRelation4DepVO;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 存管固定期限产品债权匹配相关
 * @author bianyatian
 * @2017-4-1 下午5:09:08
 */
public interface DepFixedLoanRelationshipService {
	
	/**
	 * 存管固期，借款时的债权关系匹配(云贷自主、七贷共用)
	 * @param loanId 借款编号
	 * @param lnUserId 借款用户编号
	 * @param lnSubAccountId 借款子账户编号
	 * @param amount 借款金额
	 * @param loanTerm 借款期限（月）
	 * @param partnerEnum 合作方
	 * @return
	 */
	@Deprecated
	List<LoanRelation4DepVO> confirmLoanRelation4Loan(Integer loanId, Integer lnUserId,
												  Integer lnSubAccountId, Double amount, 
												  Integer loanTerm, PartnerEnum partnerEnum);


	/**
	 * 计算利息=计息天数* 利率(%)* 计息金额/365
	 * @param days 计息天数
	 * @param rate 利率
	 * @param amount 计息金额
	 * @param roundNum 小数位数
	 * @return
	 */
	Double calInterest(Integer days, Double rate, Double amount, Integer roundNum);
	
	/**
	 * 获取VIP用户列表
	 * @param configKey:云贷：YUN_DAI_SELF_SUPER_FINANCE_USER_ID
	 * 赞时贷：ZSD_SUPER_FINANCE_USER_ID
	 * @return
	 */
	List<Integer> getDepVIPUserList(String configKey);
	
	/**
	 * 云贷，赞时贷  --- 生成理财人还款计划数据（债权转让和还款共用），仅生成数据返回不插入
	 * @param record
	 * @param calInterestPrincipal 计息本金
	 * @param lastDay 还款计划计息日后一天
	 * @param agreementAmount 协议利息 = 协议利率*计息本金*还款人计息天数/365，债权转让调用时传null
	 * @param agreementRate 协议利率 还款调用时传null
	 * @param thisRepayAmount 此次还款本金
	 * @return LnFinanceRepaySchedule
	 */
	LnFinanceRepaySchedule generateFinanceRepaySchedule(LoanRelation4TransferVO record, Double calInterestPrincipal,
			Date lastDay, Double agreementAmount, Double agreementRate, Double thisRepayAmount);

	
	/**
	 * 七贷-债权转让 生成理财人还款计划数据，仅生成数据返回不插入
	 * @author bianyatian
	 * @param record
	 * @param lastDay 还款计划计息日后一天
	 * @return
	 */
	LnFinanceRepaySchedule getFinanceRepaySchedule4SevenTransfer(LoanRelation4TransferVO record, Date lastDay);
	
	/**
	 * 云贷等额本息-债权转让 生成理财人还款计划数据，仅生成数据返回不插入
	 * @author bianyatian
	 * @param record
	 * @return
	 */
	LnFinanceRepaySchedule getFinanceRepaySchedule4FixedInstallmentTransfer(LoanRelation4TransferVO record);

	/**
	 * 云贷转让具体操作，切面生成协议签章
	 * @param today
	 * @param record
	 * @param vipTransFlag vip转出传true,普通转出传false
	 */
	Map<String, Object> doDepFixedTransferDetail(Date today, LoanRelation4TransferVO record, boolean vipTransFlag, PartnerEnum partnerEnum);
	
	/**
	 * 赞时贷转让具体操作
	 * @param today
	 * @param record
	 * @param vipTransFlag vip转出传true,普通转出传false
	 */
	void doDepFixedZsdTransferDetail(Date today, LoanRelation4TransferVO record, boolean vipTransFlag);
	
	
	/**
	 * 存管固期，借款时的债权关系匹配（赞时贷）
	 * @param loanId 借款编号
	 * @param lnUserId 借款用户编号
	 * @param lnSubAccountId 借款子账户编号
	 * @param amount 借款金额
	 * @return
	 */
	List<LoanRelation4DepVO> confirmLoanRelation4LoanZSD(Integer loanId, Integer lnUserId,
			  Integer lnSubAccountId, Double amount);
	
	
	/**
	 * 七贷还款时， 生成理财人还款计划数据
	 * @param record
	 * @param calDays 还款计息天数
	 * @param lastDay 最后计息日（计息）
	 * @param agreementAmount 协议利息 = 协议利率*计息本金*还款人计息天数/365，债权转让调用时传null
	 * @param lastSettleTime 上次还款结息日+1(起息日)或借款成功日
	 * @return LnFinanceRepaySchedule
	 */
	LnFinanceRepaySchedule getFinanceRepaySchedule4SevenRepay(LoanRelation4TransferVO record, Integer calDays,
			Date lastDay, Double agreementAmount, Date lastSettleTime);
	
	
	/**
	 * 包含自由资金债权转让的具体转让，包括冻结站岗户红包户，调用恒丰标的转让，转让成功和失败的相关操作
	 * @author bianyatian
	 * @param record 转出的对象数据
	 * @param inSubAccount 受让站岗户信息
	 * @param matchAuthAmount 受让人站岗户付出金额
	 * @param matchRedAmount 受让人红包户付出金额
	 * @param planFee 平台营收
	 * @param transInAmount 受让人付出金额除本金部分
	 * @param diffAmount 补差户应减金额
	 * @param diffActId 补差户id
	 */
	Map<String, Object> doTransfer4Free(LoanRelation4TransferVO record, BsSubAccountVO4DepFixedMatch inSubAccount,
			Double matchAuthAmount, Double matchRedAmount, Double planFee,  Double transInAmount, Double diffAmount, Integer diffActId);
	
	/**
	 * 云贷等本等息-债权转让 生成理财人还款计划数据,仅生成数据返回不插入
	 * @author gemma
	 * @param record
	 * @return
	 */
	LnFinanceRepaySchedule getFinanceRepaySchedule4FixedPrincipalInterestTransfer(LoanRelation4TransferVO record);
}
