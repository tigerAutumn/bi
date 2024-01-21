package com.pinting.business.accounting.service.impl.process;

import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.pinting.business.dao.BsAccountJnlMapper;
import com.pinting.business.dao.BsSubAccountMapper;
import com.pinting.business.model.BsAccountJnl;
import com.pinting.business.model.BsAccountJnlExample;
import com.pinting.business.model.BsSubAccount;
import com.pinting.business.service.site.SubAccountService;
import com.pinting.business.util.Constants;
import com.pinting.core.util.MoneyUtil;

/**
 * 产品购买记账
 * @Project: business
 * @Title: BuyRecordAccountingProcess.java
 * @author dingpf
 * @date 2015-1-21 下午6:47:07
 * @Copyright: 2015 BiGangWan.com Inc. All rights reserved.
 */
@Component("BuyProductRecordAccountingProcess")
public class BuyRecordAccountingProcess extends BaseAccountingProcess{
	@Autowired
	private BsSubAccountMapper subAccountMapper;
	@Autowired
	private SubAccountService subAccountService;
	@Autowired
	private BsAccountJnlMapper bsAccountJnlMapper;
	
	@Override
	public Integer preRecordAccountingExecute(BsAccountJnl accountJnl)  {
		//再进行购买记账
		log.info("======交易【前置产品购买记账】：执行开始======");
		//查询该用户 结算户 信息
		BsSubAccount subAccount1 = subAccountService.findJSHSubAccountByUserId(accountJnl.getUserId1());
		BsAccountJnl bsAccountJnl = new BsAccountJnl();
		bsAccountJnl.setTransTime(accountJnl.getTransTime());
		bsAccountJnl.setTransCode(accountJnl.getTransCode());
		bsAccountJnl.setTransType(Constants.TRANS_TYPE_TRANSFER);
		bsAccountJnl.setTransName("产品购买");
		bsAccountJnl.setTransAmount(accountJnl.getTransAmount());
		bsAccountJnl.setSysTime(new Date());
		bsAccountJnl.setCdFlag1(Constants.CD_FLAG_C);
		bsAccountJnl.setUserId1(accountJnl.getUserId1());
		bsAccountJnl.setAccountId1(subAccount1.getAccountId());
		bsAccountJnl.setSubAccountId1(subAccount1.getId());
		bsAccountJnl.setSubAccountCode1(subAccount1.getCode());
		bsAccountJnl.setBeforeBalance1(MoneyUtil.add(subAccount1.getBalance(), accountJnl.getTransAmount()).doubleValue());
		bsAccountJnl.setBeforeAvialableBalance1(MoneyUtil.add(subAccount1.getAvailableBalance(), accountJnl.getTransAmount()).doubleValue());
		bsAccountJnl.setBeforeFreezeBalance1(subAccount1.getFreezeBalance());
		bsAccountJnl.setAfterBalance1(subAccount1.getBalance());
		bsAccountJnl.setAfterAvialableBalance1(subAccount1.getAvailableBalance());
		bsAccountJnl.setAfterFreezeBalance1(subAccount1.getFreezeBalance());
		bsAccountJnl.setCdFlag2(Constants.CD_FLAG_D);
		bsAccountJnl.setUserId2(accountJnl.getUserId2());
		bsAccountJnl.setSubAccountCode2(accountJnl.getSubAccountCode2());
		bsAccountJnl.setFee(accountJnl.getFee());
		bsAccountJnl.setStatus(Constants.ACCOUNT_JNL_STATUS_INHAND);
		log.debug("======交易【前置产品购买记账】数据：" + bsAccountJnl.toString() + "======");
		bsAccountJnlMapper.insertSelective(bsAccountJnl);		
		log.info("======交易【前置产品购买记账】：执行结束======");
		
		return bsAccountJnl.getId();
	}

	@Override
	@Transactional(rollbackFor=Exception.class)
	public void postfRecordAccountingExecute(BsAccountJnl accountJnl)  {
		//1.结算户金额减少
		//2.产品户金额增加
		//3.记流水
		
		log.info("======交易【后置产品购买记账】：结算户金额减少======");
		//1.结算户金额减少
		BsSubAccount jshSubAccount = subAccountService.findJSHSubAccountByUserId(accountJnl.getUserId1());
		BsSubAccount userTmpSubAccount = new BsSubAccount();
		userTmpSubAccount.setId(jshSubAccount.getId());
		userTmpSubAccount.setBalance(-accountJnl.getTransAmount());
		userTmpSubAccount.setAvailableBalance(-accountJnl.getTransAmount());
		userTmpSubAccount.setCanWithdraw(-accountJnl.getTransAmount());
		subAccountMapper.updateBalancesByIncrement(userTmpSubAccount);
		
		
		//2.产品户金额增加
		log.info("======交易【后置产品购买记账】：产品户金额增加======");
		BsSubAccount subAccount = subAccountService.findSubAccountById(accountJnl.getSubAccountId2());
		userTmpSubAccount = new BsSubAccount();
		userTmpSubAccount.setId(subAccount.getId());
		userTmpSubAccount.setBalance(accountJnl.getTransAmount());
		userTmpSubAccount.setOpenBalance(accountJnl.getTransAmount());
		userTmpSubAccount.setFreezeBalance(accountJnl.getTransAmount());
		userTmpSubAccount.setStatus(Constants.SUBACCOUNT_STATUS_FINANCING);
		subAccountMapper.updateBalancesByIncrement(userTmpSubAccount);
		
		//记录流水
		log.info("======交易【后置产品购买记账流水】：执行开始======");
		BsAccountJnl bsAccountJnl = new BsAccountJnl();
		bsAccountJnl.setTransTime(accountJnl.getTransTime());
		bsAccountJnl.setTransCode(accountJnl.getTransCode());
		bsAccountJnl.setTransType(Constants.TRANS_TYPE_TRANSFER);
		bsAccountJnl.setTransName("产品购买");
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
		bsAccountJnl.setUserId2(accountJnl.getUserId2());
		bsAccountJnl.setSubAccountId2(subAccount.getId());
		bsAccountJnl.setSubAccountCode2(subAccount.getCode());
		bsAccountJnl.setBeforeBalance2(userTmpSubAccount.getBalance());
		bsAccountJnl.setBeforeAvialableBalance2(userTmpSubAccount.getAvailableBalance() );
		bsAccountJnl.setBeforeFreezeBalance2(userTmpSubAccount.getFreezeBalance());
		bsAccountJnl.setAfterBalance2(MoneyUtil.add(userTmpSubAccount.getBalance(), accountJnl.getTransAmount()).doubleValue());
		bsAccountJnl.setAfterAvialableBalance2(userTmpSubAccount.getAvailableBalance());
		bsAccountJnl.setAfterFreezeBalance2(MoneyUtil.add(userTmpSubAccount.getFreezeBalance(), accountJnl.getTransAmount()).doubleValue());
		bsAccountJnl.setFee(accountJnl.getFee());
		bsAccountJnl.setStatus(Constants.ACCOUNT_JNL_STATUS_SUCCESS);
		log.debug("======交易【后置产品购买记账流水】数据：" + bsAccountJnl.toString() + "======");
		bsAccountJnlMapper.insertSelective(bsAccountJnl);		
		accountJnl.setId(bsAccountJnl.getId());
		log.info("======交易【后置产品购买记账流水】：执行结束======");
		
		
		/* 暂时去掉品听内部结算户 计算
		 * 
		 * log.info("======交易【后置产品购买记账】：修改品听结算户（内部户）余额======");
		//查询品听内部结算户
		BsSubAccount pinTingSubAccount = bsSubAccountMapper.selectByPrimaryKey(PINTING_JSH_ACCOUNT_ID);
		BsSubAccount pinTingTmpSubAccount = new BsSubAccount();
		pinTingTmpSubAccount.setId(PINTING_JSH_ACCOUNT_ID);
		pinTingTmpSubAccount.setBalance(pinTingSubAccount.getBalance() - transAmount);
		pinTingTmpSubAccount.setAvailableBalance(pinTingSubAccount.getAvailableBalance() - transAmount);
		bsSubAccountMapper.updateByPrimaryKeySelective(pinTingTmpSubAccount);*/
		
	}

}
