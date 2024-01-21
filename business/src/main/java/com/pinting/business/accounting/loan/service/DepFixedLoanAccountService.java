package com.pinting.business.accounting.loan.service;

import com.pinting.business.accounting.loan.model.BaseAccount;
import com.pinting.business.accounting.loan.model.InvestorAuthYunInfo;

import java.util.List;

/**
 * 存管固定期限产品 -- 借款相关账务服务
 * 
 * F：理财人；SF：超级理财人；S：系统；B：借款人
 * 
 * @author bianyatian
 * @2017-4-4 下午4:21:59
 */
public interface DepFixedLoanAccountService {

	
	/**
	 * 借款申请授权金额冻结,转让前冻结
	 * F:AUTH_YUN balance >>> freeze balance
	 * F:RED balance >>> freeze balance
	 * @param authAmount 站岗户发生金额必输
	 * @param redAmount 红包户发生金额
	 * @param investorAuthActId 站岗户发生金额>0时理财人站岗户编号必输
	 * @param investorRedActId 红包户发生金额>0时必填
	 * investorAuthActId和investorRedActId不能都为空
	 */
	void chargeLoanFreeze(Double authAmount, Integer investorAuthActId,
			Double redAmount,Integer investorRedActId);
	
	
	/**
	 * 借款申请失败（转让失败），解冻
	 * F:AUTH_YUN freeze balance >>> balance
	 * F:RED freeze balance >>> balance
	 * @param authAmount 站岗户发生金额
	 * @param redAmount 红包户发生金额
	 * @param investorAuthActId 站岗户发生金额>0时理财人站岗户编号必输
	 * @param investorRedActId 红包户发生金额>0时必填
	 * investorAuthActId和investorRedActId不能都为空
	 */
	void chargeLoanUnFreeze(Double authAmount, Integer investorAuthActId,
			Double redAmount,Integer investorRedActId);

	/**
	 * 借款成功记账
	 * F:AUTH_INCR -
	 * S:AUTH_YUN_INCR  >>> REG_YUN_INCR
	 * B:LOAN +
	 * S:FEE +
	 * S:FEE_YUN_INCR +
	 * @param baseAccount 合作方、借款人编号、理财人编号、发生金额必输
	 * @param investorAuthIncrs 理财人增计划站岗户编号、发生子金额 必输
	 * @param loanSubActId 借款人子账户编号 必输
	 * @param fee 手续费必输
	 */
	void chargeUpLoan(BaseAccount baseAccount, List<InvestorAuthYunInfo> investorAuthIncrs,
					Integer loanSubActId, Double fee);
	
}
