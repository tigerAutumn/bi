package com.pinting.business.accounting.loan.service;

import com.pinting.business.accounting.loan.model.BaseAccount;
import com.pinting.business.accounting.loan.model.InvestorRegInfo;
import com.pinting.business.accounting.loan.model.SuperTransferInfo;

import java.util.List;

/**
 * 借款相关账务服务
 * 
 * F：理财人；SF：超级理财人；S：系统；B：借款人
 * 
 * @Project: business
 * @Title: LoanAccountService.java
 * @author dingpf
 * @date 2016-8-23 下午4:57:12
 * @Copyright: 2016 BiGangWan.com Inc. All rights reserved.
 */
public interface LoanAccountService {

	/**
	 * 理财资金授权站岗
	 * F/SF:JSH >>> AUTH
	 * S:USER >>> AUTH_ZAN
	 * @param baseAccount 合作方、理财人编号、发生金额必输
	 * @param productId 产品编号必输
	 * @return 理财人站岗户编号
	 */
	int chargeAuthActAdd(BaseAccount baseAccount, Integer productId);

	/**
	 * 理财资金未借出，站岗户回退
	 * F:AUTH >>> JSH
	 * S:AUTH_ZAN >>> USER
	 * @param baseAccount 合作方、发生金额必输
	 * @param investorAuthActId 理财人站岗户编号必输
	 */
	void chargeAuthActBack(BaseAccount baseAccount, Integer investorAuthActId);
	
	/**
	 * 借款申请授权金额冻结
	 * F:AUTH balance >>> freeze balance
	 * @param amount 发生金额必输
	 * @param investorAuthActId 理财人站岗户编号必输
	 */
	void chargeLoanFreeze(Double amount, Integer investorAuthActId);
	
	/**
	 * 借款申请失败，授权金额解冻
	 * F:AUTH freeze balance >>> balance
	 * @param amount 发生金额必输
	 * @param investorAuthActId 理财人站岗户编号必输
	 */
	void chargeLoanUnFreeze(Double amount, Integer investorAuthActId);

	/**
	 * 借款申请时借款人子账户开户
	 * B:LOAN +
	 * @param baseAccount 合作方、借款人编号、开户金额必输
	 * @return 借款人子账户编号
     */
	Integer chargeLoanActOpen(BaseAccount baseAccount);
	/**
	 * 借款成功记账
	 * F:AUTH >>> REG_D
	 * S:AUTH_ZAN >>> REG_ZAN
	 * B:LOAN +
	 * S:FEE +
	 * @param baseAccount 合作方、借款人编号、理财人编号、发生金额必输
	 * @param investorRegs 理财人产品户编号、发生子金额 必输
	 * @param loanSubActId 借款人子账户编号 必输
	 * @param fee 手续费必输
	 */
	void chargeLoan(BaseAccount baseAccount, List<InvestorRegInfo> investorRegs, Integer loanSubActId, Double fee);
	
	/**
	 * SF转让记账:存管-先冻结（chargeLoanFreeze），后转让
	 * SF:REG_D >>> AUTH
	 * F:AUTH >>> REG_D
	 * F:AUTH >>> SF:JSH+
	 * S:AUTH_ZAN >>> USER（无付息，则不需要记账）
	 * @param sTransferInfo 超级理财人产品户编号、理财人站岗户编号、发生金额（超级理财人REG_D应减少的金额）
	 * 超级理财人id、普通理财人id、匹配金额（普通AUTH需减少的金额） 必输
	 */
	void chargeSuperTransfer(SuperTransferInfo sTransferInfo);

}
/**
用户子账户表扩展{
    投资人结算户：一个（余额账户）JSH
    投资人站岗户：多个（站岗账户）AUTH
    投资人产品户1：多个（投资金额固定不变，一次性还款）  REG
    投资人产品户2：多个（投资金额变动，允许多次回款）  REG_D
    投资人奖励金户：JLJ
}
系统子账户表扩展{
    系统结算户：JSH
    系统用户余额户：USER
    云贷产品户：REG_YUN
    云贷回款户：RETURN_YUN
 7贷产品户：REG_7
 7贷回款户：RETURN_7
    系统红包户: REDPACKET
    赞分期站岗户：AUTH_ZAN
    赞分期产品户：REG_ZAN
    赞分期回款户：RETURN_ZAN
    赞分期风险保证金账户：MARGIN_ZAN
    赞分期营收账户：REVENUE_ZAN
    币港湾对赞分期营收账户：REVENUE
    币港湾手续费账户：FEE
    平台备付金账户：（暂无）
    赞分期坏账专用账户：（暂无）
}
借款人子账户表{
    借款人结算户：JSH
    借款人借款户：LOAN（待还账户）多个
}
**/