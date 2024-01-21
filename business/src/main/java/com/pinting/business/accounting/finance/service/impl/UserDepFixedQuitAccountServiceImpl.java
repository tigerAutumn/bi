package com.pinting.business.accounting.finance.service.impl;

import com.pinting.business.accounting.finance.service.UserDepFixedQuitAccountService;
import com.pinting.business.accounting.loan.model.BaseAccount;
import com.pinting.business.accounting.loan.model.PartnerSysActCode;
import com.pinting.business.accounting.service.BsSysSubAccountService;
import com.pinting.business.dao.*;
import com.pinting.business.model.BsAccountJnl;
import com.pinting.business.model.BsSubAccount;
import com.pinting.business.model.BsSysAccountJnl;
import com.pinting.business.model.BsSysSubAccount;
import com.pinting.business.util.Constants;
import com.pinting.core.util.MoneyUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
/**
 * 
 * @project business
 * @title UserDepFixedQuitAccountServiceImpl.java
 * @author Dragon & cat
 * @date 2017-4-5
 * @Copyright 2015 BiGangWan.com Inc. All rights reserved.
 * @Description 
 */
@Service
public class UserDepFixedQuitAccountServiceImpl implements
		UserDepFixedQuitAccountService {
	
	private final Logger logger = LoggerFactory.getLogger(UserDepFixedQuitAccountServiceImpl.class);
	@Autowired
	private		BsSubAccountMapper  bsSubAccountMapper;	
	@Autowired
	private 	BsAccountJnlMapper  bsAccountJnlMapper;
	@Autowired
	private		BsSysSubAccountService	bsSysSubAccountService;
	@Autowired
	private		BsSysSubAccountMapper	bsSysSubAccountMapper;
	@Autowired
	private		BsSysAccountJnlMapper	bsSysAccountJnlMapper;

	
	@Override
	public void depQuitAccount(final Integer subAccountId, final Integer userId, final Double fillAmount,
							   final Double totalPrincipal, final Double totalInterest, 
							   final Double overflowInterest, BaseAccount baseAccount) {
		logger.info("========={存管系统固定期限产品退出服务}记账开始=========");
//		transactionTemplate.execute(new TransactionCallbackWithoutResult(){
//			@Override
//			protected void doInTransactionWithoutResult(TransactionStatus status) {
				//F:AUTH + 补息金额
				if (fillAmount > 0) {
					BsSubAccount bsSubAccount = bsSubAccountMapper.selectSubAccountByIdForLock(subAccountId);
					BsSubAccount bsSubAccountFillInterest = new BsSubAccount();
					bsSubAccountFillInterest.setId(bsSubAccount.getId());
					bsSubAccountFillInterest.setAvailableBalance(MoneyUtil.add(bsSubAccount.getAvailableBalance(), fillAmount).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
					bsSubAccountFillInterest.setCanWithdraw(MoneyUtil.add(bsSubAccount.getCanWithdraw(), fillAmount).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
					bsSubAccountFillInterest.setBalance(MoneyUtil.add(bsSubAccount.getBalance(), fillAmount).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
					bsSubAccountMapper.updateByPrimaryKeySelective(bsSubAccountFillInterest);
					//新增用户记账流水表
					BsAccountJnl accountJnl = new BsAccountJnl();
					accountJnl.setTransTime(new Date());
					accountJnl.setTransCode(Constants.USER_DEP_FIXED_QUIT_FILL_INTEREST);
					accountJnl.setTransName("存管固定期限退出补息");
					accountJnl.setTransAmount(fillAmount);
					accountJnl.setSysTime(new Date());
					accountJnl.setCdFlag1(Constants.CD_FLAG_D);
					accountJnl.setUserId1(userId);
					accountJnl.setAccountId1(bsSubAccount.getAccountId());
					accountJnl.setSubAccountId1(bsSubAccount.getId());
					accountJnl.setSubAccountCode1(bsSubAccount.getCode());
					accountJnl.setBeforeBalance1(bsSubAccount.getBalance());
					accountJnl.setAfterBalance1(bsSubAccountFillInterest.getBalance());
					accountJnl.setBeforeAvialableBalance1(bsSubAccount.getAvailableBalance());
					accountJnl.setAfterAvialableBalance1(bsSubAccountFillInterest.getAvailableBalance());
					accountJnl.setBeforeFreezeBalance1(bsSubAccount.getFreezeBalance());
					accountJnl.setAfterFreezeBalance1(bsSubAccount.getFreezeBalance());
					accountJnl.setFee(0.0);
					bsAccountJnlMapper.insertSelective(accountJnl);
				}
				
//				PartnerSysActCode partnerActCode = BaseAccount.partnerActCodeMap.get(baseAccount.getPartner());
//				logger.info("理财端合作方"+baseAccount.getPartner());
//				//S:RETURN + 本金+总利息+溢出利息
//				Double returnAmount = MoneyUtil.add(totalPrincipal, MoneyUtil.add(totalInterest, overflowInterest).doubleValue()).doubleValue();
				/*MoneyUtil.defaultRound(totalPrincipal + totalInterest + overflowInterest).doubleValue();*/
//				BsSysSubAccount bsSysSubAccountLock = bsSysSubAccountService.findSysSubAccount4Lock(partnerActCode.getReturnActCode());
//				BsSysSubAccount bsSysSubAccount = new BsSysSubAccount();
//				bsSysSubAccount.setId(bsSysSubAccountLock.getId());
//				bsSysSubAccount.setBalance(MoneyUtil.add(bsSysSubAccountLock.getBalance(), returnAmount).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
//				bsSysSubAccount.setAvailableBalance(MoneyUtil.add(bsSysSubAccountLock.getAvailableBalance(), returnAmount).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
//				bsSysSubAccount.setCanWithdraw(MoneyUtil.add(bsSysSubAccountLock.getCanWithdraw(), returnAmount).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
//				bsSysSubAccount.setLastTransDate(new Date());
//				bsSysSubAccountMapper.updateByPrimaryKeySelective(bsSysSubAccount);
//				//新增系统记账流水表
//				BsSysAccountJnl sysAccountJnl = new BsSysAccountJnl();
//				sysAccountJnl.setTransTime(new Date());
//			    sysAccountJnl.setTransCode(Constants.SYS_ACCOUNT_BGW_RETURN_IN + baseAccount.getPartner().getCode());
//				sysAccountJnl.setTransName("存管定期产品回款户转入");
//				sysAccountJnl.setTransAmount(returnAmount);
//				sysAccountJnl.setSysTime(new Date());
//				sysAccountJnl.setChannelTime(new Date());
//				sysAccountJnl.setCdFlag2(Constants.CD_FLAG_D);
//				sysAccountJnl.setSubAccountCode1(bsSysSubAccountLock.getCode());
//				sysAccountJnl.setBeforeBalance1(bsSysSubAccountLock.getBalance());
//				sysAccountJnl.setAfterBalance1(bsSysSubAccount.getBalance());
//				sysAccountJnl.setBeforeAvialableBalance1(bsSysSubAccountLock.getAvailableBalance());
//				sysAccountJnl.setAfterAvialableBalance1(bsSysSubAccount.getAvailableBalance());
//				sysAccountJnl.setBeforeFreezeBalance1(bsSysSubAccountLock.getFreezeBalance());
//				sysAccountJnl.setAfterFreezeBalance1(bsSysSubAccountLock.getFreezeBalance());
//				sysAccountJnl.setFee(0.0);
//				sysAccountJnl.setStatus(Constants.SYS_ACCOUNT_STATUS_SUCCESS);
//				bsSysAccountJnlMapper.insertSelective(sysAccountJnl);

				//S:DEP_JSH — 补息
				if (fillAmount > 0) {
					BsSysSubAccount depJshSubAccountLock = bsSysSubAccountService.findSysSubAccount4Lock(Constants.SYS_ACCOUNT_DEP_JSH);
					BsSysSubAccount depJshSubAccount = new BsSysSubAccount();
					depJshSubAccount.setId(depJshSubAccountLock.getId());
					depJshSubAccount.setBalance(MoneyUtil.subtract(depJshSubAccountLock.getBalance(), fillAmount).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
					depJshSubAccount.setAvailableBalance(MoneyUtil.subtract(depJshSubAccountLock.getAvailableBalance(), fillAmount).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
					depJshSubAccount.setCanWithdraw(MoneyUtil.subtract(depJshSubAccountLock.getCanWithdraw(), fillAmount).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
					depJshSubAccount.setLastTransDate(new Date());
					bsSysSubAccountMapper.updateByPrimaryKeySelective(depJshSubAccount);

					//新增系统记账流水表
					BsSysAccountJnl depJshAccountJnl = new BsSysAccountJnl();
					depJshAccountJnl.setTransTime(new Date());
					depJshAccountJnl.setTransCode(Constants.SYS_ACCOUNT_DEP_JSH_OUT);
					depJshAccountJnl.setTransName("存管自有子账户减少（补息）");
					depJshAccountJnl.setTransAmount(fillAmount);
					depJshAccountJnl.setSysTime(new Date());
					depJshAccountJnl.setChannelTime(new Date());
					depJshAccountJnl.setCdFlag1(Constants.CD_FLAG_C);
					depJshAccountJnl.setSubAccountCode1(depJshSubAccountLock.getCode());
					depJshAccountJnl.setBeforeBalance1(depJshSubAccountLock.getBalance());
					depJshAccountJnl.setAfterBalance1(depJshSubAccount.getBalance());
					depJshAccountJnl.setBeforeAvialableBalance1(depJshSubAccountLock.getAvailableBalance());
					depJshAccountJnl.setAfterAvialableBalance1(depJshSubAccount.getAvailableBalance());
					depJshAccountJnl.setBeforeFreezeBalance1(depJshSubAccountLock.getFreezeBalance());
					depJshAccountJnl.setAfterFreezeBalance1(depJshSubAccountLock.getFreezeBalance());
					depJshAccountJnl.setFee(0.0);
					depJshAccountJnl.setStatus(Constants.SYS_ACCOUNT_STATUS_SUCCESS);
					bsSysAccountJnlMapper.insertSelective(depJshAccountJnl);
				}
			}
//		});
//	}
}
