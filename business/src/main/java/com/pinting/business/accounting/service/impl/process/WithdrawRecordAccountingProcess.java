package com.pinting.business.accounting.service.impl.process;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.pinting.business.dao.BsAccountJnlMapper;
import com.pinting.business.dao.BsSubAccountMapper;
import com.pinting.business.dao.BsUserMapper;
import com.pinting.business.model.BsAccountJnl;
import com.pinting.business.model.BsSubAccount;
import com.pinting.business.model.BsUser;
import com.pinting.business.service.site.SubAccountService;
import com.pinting.business.util.Constants;
import com.pinting.business.util.TransCode;
import com.pinting.core.util.MoneyUtil;

/**
 * 提现
 * 
 * @Project: business
 * @Title: WithdrawRecordAccountingProcess.java
 * @author dingpf
 * @date 2015-4-3 下午3:41:47
 * @Copyright: 2015 BiGangWan.com Inc. All rights reserved.
 */
@Component("WithdrawRecordAccountingProcess")
public class WithdrawRecordAccountingProcess extends BaseAccountingProcess {
	@Autowired
	private BsSubAccountMapper subAccountMapper;
	@Autowired
	private SubAccountService subAccountService;
	@Autowired
	private BsAccountJnlMapper bsAccountJnlMapper;
	@Autowired
	private BsUserMapper bsUserMapper;

	@Override
	@Transactional(rollbackFor = Exception.class)
	public Integer preRecordAccountingExecute(BsAccountJnl accountJnl) {
		log.info("======交易【提现前置记账（冻结）】：开始执行======");
		log.info("======交易【提现前置记账（冻结）】：结算户冻结余额增加 ，可用余额减少======");
		BsSubAccount jshSubAccount = subAccountService
				.findJSHSubAccountByUserId(accountJnl.getUserId1());
		BsSubAccount userTmpSubAccount = new BsSubAccount();
		userTmpSubAccount.setId(jshSubAccount.getId());
		userTmpSubAccount.setAvailableBalance(-accountJnl.getTransAmount());
		userTmpSubAccount.setCanWithdraw(-accountJnl.getTransAmount());
		userTmpSubAccount.setFreezeBalance(accountJnl.getTransAmount());
		subAccountMapper.updateBalancesByIncrement(userTmpSubAccount);

		log.info("======交易【提现前置记账（冻结）】：用户表可提现金额和当前奖励金金额减少======");
		BsUser tempUser = new BsUser();
		tempUser.setCanWithdraw(-accountJnl.getTransAmount());
		tempUser.setCurrentBonus(-accountJnl.getTransAmount());
		tempUser.setTotalBonus(0d);
		tempUser.setId(accountJnl.getUserId1());
		bsUserMapper.updateBonusById(tempUser);

		log.info("======交易【提现前置记账（冻结）】：流水记账开始======");
		// 记录流水
		BsAccountJnl bsAccountJnl = new BsAccountJnl();
		bsAccountJnl.setTransTime(accountJnl.getTransTime());
		bsAccountJnl.setTransCode(Constants.USER_FREEZE);
		bsAccountJnl.setTransType(Constants.TRANS_TYPE_TRANSFER);
		bsAccountJnl.setTransName("冻结");// 冻结
		bsAccountJnl.setTransAmount(accountJnl.getTransAmount());
		bsAccountJnl.setSysTime(new Date());
		bsAccountJnl.setCdFlag1(Constants.CD_FLAG_C);
		bsAccountJnl.setUserId1(accountJnl.getUserId1());
		bsAccountJnl.setAccountId1(jshSubAccount.getAccountId());
		bsAccountJnl.setSubAccountId1(jshSubAccount.getId());
		bsAccountJnl.setSubAccountCode1(jshSubAccount.getCode());
		bsAccountJnl.setBeforeBalance1(jshSubAccount.getBalance());
		bsAccountJnl.setBeforeAvialableBalance1(jshSubAccount
				.getAvailableBalance());
		bsAccountJnl.setBeforeFreezeBalance1(jshSubAccount.getFreezeBalance());
		bsAccountJnl.setAfterBalance1(jshSubAccount.getBalance());
		bsAccountJnl.setAfterAvialableBalance1(MoneyUtil.subtract(jshSubAccount
				.getAvailableBalance(), accountJnl.getTransAmount()).doubleValue());
		bsAccountJnl.setAfterFreezeBalance1(MoneyUtil.add(jshSubAccount.getFreezeBalance()
				, accountJnl.getTransAmount()).doubleValue());
		bsAccountJnl.setCdFlag2(Constants.CD_FLAG_D);
		bsAccountJnl.setUserId2(accountJnl.getUserId1());
		bsAccountJnl.setSubAccountCode2(jshSubAccount.getCode());
		bsAccountJnl.setFee(0d);
		bsAccountJnl.setStatus(Constants.ACCOUNT_JNL_STATUS_SUCCESS);
		log.debug("======交易【提现前置记账（冻结）】数据：" + bsAccountJnl.toString()
				+ "======");
		bsAccountJnlMapper.insertSelective(bsAccountJnl);
		accountJnl.setId(bsAccountJnl.getId());
		log.info("======交易【提现前置记账（冻结）】：执行结束======");

		return bsAccountJnl.getId();
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void postfRecordAccountingExecute(BsAccountJnl accountJnl) {

		log.info("======交易【提现后置记账】：开始执行======");
		log.info("======交易【提现后置记账】：结算户总余额、冻结余额减少，其他余额不变======");
		// 结算户冻结余额减少，其他余额不变
		BsSubAccount jshSubAccount = subAccountService
				.findJSHSubAccountByUserId(accountJnl.getUserId1());
		BsSubAccount userTmpSubAccount = new BsSubAccount();
		userTmpSubAccount.setId(jshSubAccount.getId());
		userTmpSubAccount.setBalance(-accountJnl.getTransAmount());
		userTmpSubAccount.setFreezeBalance(-accountJnl.getTransAmount());
		subAccountMapper.updateBalancesByIncrement(userTmpSubAccount);
		log.info("======交易【提现后置记账】：流水记账开始======");
		// 记录流水
		BsAccountJnl bsAccountJnl = new BsAccountJnl();
		bsAccountJnl.setTransTime(accountJnl.getTransTime());
		bsAccountJnl.setTransCode(accountJnl.getTransCode());
		bsAccountJnl.setTransType(Constants.TRANS_TYPE_WITHDRAW);
		bsAccountJnl.setTransName("提现");// 结算户提现到银行卡
		bsAccountJnl.setTransAmount(accountJnl.getTransAmount());
		bsAccountJnl.setSysTime(new Date());
		bsAccountJnl.setCdFlag1(Constants.CD_FLAG_C);
		bsAccountJnl.setUserId1(accountJnl.getUserId1());
		bsAccountJnl.setAccountId1(jshSubAccount.getAccountId());
		bsAccountJnl.setSubAccountId1(jshSubAccount.getId());
		bsAccountJnl.setSubAccountCode1(jshSubAccount.getCode());
		bsAccountJnl.setBeforeBalance1(jshSubAccount.getBalance());
		bsAccountJnl.setBeforeAvialableBalance1(jshSubAccount
				.getAvailableBalance());
		bsAccountJnl.setBeforeFreezeBalance1(jshSubAccount.getFreezeBalance());
		bsAccountJnl.setAfterBalance1(jshSubAccount.getBalance()
				- accountJnl.getTransAmount());
		bsAccountJnl.setAfterAvialableBalance1(jshSubAccount
				.getAvailableBalance());
		bsAccountJnl.setAfterFreezeBalance1(jshSubAccount.getFreezeBalance()
				- accountJnl.getTransAmount());
		bsAccountJnl.setCdFlag2(Constants.CD_FLAG_D);
		bsAccountJnl.setUserId2(accountJnl.getUserId1());
		bsAccountJnl.setSubAccountCode2(accountJnl.getSubAccountCode2());
		bsAccountJnl.setFee(0d);
		bsAccountJnl.setStatus(Constants.ACCOUNT_JNL_STATUS_SUCCESS);
		log.debug("======交易【提现后置记账】数据：" + bsAccountJnl.toString() + "======");
		bsAccountJnlMapper.insertSelective(bsAccountJnl);
		accountJnl.setId(bsAccountJnl.getId());
		log.info("======交易【提现后置记账】：执行结束======");

	}

}
