package com.pinting.business.accounting.service.impl.process;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.pinting.business.dao.BsAccountJnlMapper;
import com.pinting.business.dao.BsSubAccountMapper;
import com.pinting.business.model.BsAccountJnl;
import com.pinting.business.model.BsSubAccount;
import com.pinting.business.service.site.SubAccountService;
import com.pinting.business.util.Constants;

/**
 * 回款提现
 * @Project: business
 * @Title: RePayWithdrawRecordAccountingProcess.java
 * @author dingpf
 * @date 2015-4-3 下午3:41:47
 * @Copyright: 2015 BiGangWan.com Inc. All rights reserved.
 */
@Component("RepayWithdrawRecordAccountingProcess")
public class RePayWithdrawRecordAccountingProcess extends BaseAccountingProcess{
	@Autowired
	private BsSubAccountMapper subAccountMapper;
	@Autowired
	private SubAccountService subAccountService;
	@Autowired
	private BsAccountJnlMapper bsAccountJnlMapper;
	
	@Override
	public Integer preRecordAccountingExecute(BsAccountJnl accountJnl)  {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@Transactional(rollbackFor=Exception.class)
	public void postfRecordAccountingExecute(BsAccountJnl accountJnl)  {
		
		log.info("======交易【结算户回款提现到银行卡记账】：开始执行======");
		log.info("======交易【结算户回款提现到银行卡记账】：结算户金额减少======");
		//结算户金额减少
		BsSubAccount jshSubAccount = subAccountService.findJSHSubAccountByUserId(accountJnl.getUserId1());
		BsSubAccount userTmpSubAccount = new BsSubAccount();
		userTmpSubAccount.setId(jshSubAccount.getId());
		userTmpSubAccount.setBalance(-accountJnl.getTransAmount());
		userTmpSubAccount.setAvailableBalance(-accountJnl.getTransAmount());
		userTmpSubAccount.setCanWithdraw(-accountJnl.getTransAmount());
		subAccountMapper.updateBalancesByIncrement(userTmpSubAccount);
		log.info("======交易【结算户回款提现到银行卡记账】：流水记账开始======");
		//记录流水
		BsAccountJnl bsAccountJnl = new BsAccountJnl();
		bsAccountJnl.setTransTime(accountJnl.getTransTime());
		bsAccountJnl.setTransCode(accountJnl.getTransCode());
		bsAccountJnl.setTransType(Constants.TRANS_TYPE_WITHDRAW);
		bsAccountJnl.setTransName("回款提现");//结算户提现到银行卡
		bsAccountJnl.setTransAmount(accountJnl.getTransAmount());
		bsAccountJnl.setSysTime(new Date());
		bsAccountJnl.setCdFlag1(Constants.CD_FLAG_C);
		bsAccountJnl.setUserId1(accountJnl.getUserId1());
		bsAccountJnl.setAccountId1(jshSubAccount.getAccountId());
		bsAccountJnl.setSubAccountId1(jshSubAccount.getId());
		bsAccountJnl.setSubAccountCode1(jshSubAccount.getCode());
		bsAccountJnl.setBeforeBalance1(jshSubAccount.getBalance());
		bsAccountJnl.setBeforeAvialableBalance1(jshSubAccount.getAvailableBalance() );
		bsAccountJnl.setBeforeFreezeBalance1(jshSubAccount.getFreezeBalance());
		bsAccountJnl.setAfterBalance1(jshSubAccount.getBalance() - accountJnl.getTransAmount());
		bsAccountJnl.setAfterAvialableBalance1(jshSubAccount.getAvailableBalance() - accountJnl.getTransAmount());
		bsAccountJnl.setAfterFreezeBalance1(jshSubAccount.getFreezeBalance());
		bsAccountJnl.setCdFlag2(Constants.CD_FLAG_D);
		bsAccountJnl.setUserId2(accountJnl.getUserId1());
		bsAccountJnl.setSubAccountCode2(accountJnl.getSubAccountCode2());
		bsAccountJnl.setFee(0d);
		bsAccountJnl.setStatus(Constants.ACCOUNT_JNL_STATUS_SUCCESS);
		log.debug("======交易【结算户回款提现到银行卡记账】数据：" + bsAccountJnl.toString() + "======");
		bsAccountJnlMapper.insertSelective(bsAccountJnl);		
		accountJnl.setId(bsAccountJnl.getId());
		log.info("======交易【结算户回款提现到银行卡记账】：执行结束======");
		
	}

}
