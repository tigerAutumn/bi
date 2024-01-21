package com.pinting.business.accounting.loan.service;

import com.pinting.business.accounting.loan.enums.PartnerEnum;
import com.pinting.business.accounting.loan.model.BaseAccount;

/**
 * 系统日常记账
 * 
 * F：理财人；SF：超级理财人；S：系统；B：借款人
 * 
 * @Project: business
 * @Title: SysDailyAccountService.java
 * @author dingpf
 * @date 2016-8-23 下午5:51:20
 * @Copyright: 2016 BiGangWan.com Inc. All rights reserved.
 */
public interface SysDailyAccountService {
	
	/**
	 * 合作方营收每日结算记账
	 * S:REVENUE_ZAN - 
	 * @param baseAccount 合作方、发生金额必输
	 */
	void chargeDailyRevenueSettle(BaseAccount baseAccount);

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