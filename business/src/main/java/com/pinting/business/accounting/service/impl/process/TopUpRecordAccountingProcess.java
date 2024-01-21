package com.pinting.business.accounting.service.impl.process;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.pinting.business.dao.BsAccountJnlMapper;
import com.pinting.business.dao.BsSubAccountMapper;
import com.pinting.business.model.BsAccountJnl;
import com.pinting.business.model.BsAccountJnlExample;
import com.pinting.business.model.BsSubAccount;
import com.pinting.business.util.Constants;
import com.pinting.core.util.MoneyUtil;

/**
 * 充值记账
 * @Project: business
 * @Title: TopUpRecordAccountingProcess.java
 * @author dingpf
 * @date 2015-1-21 下午6:46:44
 * @Copyright: 2015 BiGangWan.com Inc. All rights reserved.
 */
@Component("TopUpRecordAccountingProcess")
public class TopUpRecordAccountingProcess extends BaseAccountingProcess{
	@Autowired
	private BsSubAccountMapper bsSubAccountMapper;
	@Autowired
	private BsAccountJnlMapper bsAccountJnlMapper;
	
	@Override
	//注意：现在充值过程自动进行，不会调用此前置过程
	public Integer preRecordAccountingExecute(BsAccountJnl accountJnl)  {
		log.info("======交易【前置充值记账】：执行开始======");
		//查询该用户 结算户 信息
		BsSubAccount subAccount1 = bsSubAccountMapper.selectJSHSubAccountByUserId(accountJnl.getUserId1());
		BsAccountJnl bsAccountJnl = new BsAccountJnl();
		bsAccountJnl.setTransTime(accountJnl.getTransTime());
		bsAccountJnl.setTransCode(accountJnl.getTransCode());
		bsAccountJnl.setTransType(Constants.TRANS_TYPE_TOPUP);
		bsAccountJnl.setTransName("充值");
		bsAccountJnl.setTransAmount(accountJnl.getTransAmount());
		bsAccountJnl.setSysTime(new Date());
		bsAccountJnl.setCdFlag1(Constants.CD_FLAG_D);
		bsAccountJnl.setUserId1(accountJnl.getUserId1());
		bsAccountJnl.setAccountId1(subAccount1.getAccountId());
		bsAccountJnl.setSubAccountId1(subAccount1.getId());
		bsAccountJnl.setSubAccountCode1(subAccount1.getCode());
		bsAccountJnl.setBeforeBalance1(subAccount1.getBalance());
		bsAccountJnl.setBeforeAvialableBalance1(subAccount1.getAvailableBalance());
		bsAccountJnl.setBeforeFreezeBalance1(subAccount1.getFreezeBalance());
		bsAccountJnl.setAfterBalance1(MoneyUtil.add(subAccount1.getBalance(), accountJnl.getTransAmount()).doubleValue());
		bsAccountJnl.setAfterAvialableBalance1(MoneyUtil.add(subAccount1.getAvailableBalance(), accountJnl.getTransAmount()).doubleValue());
		bsAccountJnl.setAfterFreezeBalance1(subAccount1.getFreezeBalance());
		bsAccountJnl.setCdFlag2(Constants.CD_FLAG_C);
		bsAccountJnl.setUserId2(accountJnl.getUserId2());
		bsAccountJnl.setSubAccountCode2(accountJnl.getSubAccountCode2());
		bsAccountJnl.setFee(0d);
		bsAccountJnl.setStatus(Constants.ACCOUNT_JNL_STATUS_INHAND);
		log.debug("======交易【前置充值记账】数据：" + bsAccountJnl.toString() + "======");
		bsAccountJnlMapper.insertSelective(bsAccountJnl);
		log.info("======交易【前置充值记账】：执行结束======");
		return bsAccountJnl.getId();
		
	}

	@Override
	@Transactional(rollbackFor=Exception.class)
	public void postfRecordAccountingExecute(BsAccountJnl accountJnl)  {
		log.info("======交易【后置充值记账】：执行开始======");
		//往结算户里面充值
		log.info("======交易【后置充值记账】：修改用户结算户余额======");
		//查询该用户 结算户 信息
		BsSubAccount userSubAccount = bsSubAccountMapper.selectJSHSubAccountByUserId(accountJnl.getUserId1());
		//为防止并发问题， 结算户余额修改 这里作增量修改
		BsSubAccount userTmpSubAccount = new BsSubAccount();
		userTmpSubAccount.setId(userSubAccount.getId());
		userTmpSubAccount.setBalance(accountJnl.getTransAmount());
		userTmpSubAccount.setAvailableBalance(accountJnl.getTransAmount());
		userTmpSubAccount.setCanWithdraw(accountJnl.getTransAmount());
		bsSubAccountMapper.updateBalancesByIncrement(userTmpSubAccount);
		
		/* 暂时去掉品听内部结算户 计算
		 * 
		 * log.info("======交易【后置充值记账】：修改品听结算户（内部户）余额======");
		//查询品听内部结算户
		BsSubAccount pinTingSubAccount = bsSubAccountMapper.selectByPrimaryKey(PINTING_JSH_ACCOUNT_ID);
		BsSubAccount pinTingTmpSubAccount = new BsSubAccount();
		pinTingTmpSubAccount.setId(PINTING_JSH_ACCOUNT_ID);
		pinTingTmpSubAccount.setBalance(pinTingSubAccount.getBalance() + transAmount);
		pinTingTmpSubAccount.setAvailableBalance(pinTingSubAccount.getAvailableBalance() + transAmount);
		bsSubAccountMapper.updateByPrimaryKeySelective(pinTingTmpSubAccount);*/
			
		//记流水表
		BsAccountJnl bsAccountJnl = new BsAccountJnl();
		bsAccountJnl.setTransTime(accountJnl.getTransTime());
		bsAccountJnl.setTransCode(accountJnl.getTransCode());
		bsAccountJnl.setTransType(Constants.TRANS_TYPE_TOPUP);
		bsAccountJnl.setTransName("充值");
		bsAccountJnl.setTransAmount(accountJnl.getTransAmount());
		bsAccountJnl.setSysTime(new Date());
		bsAccountJnl.setCdFlag1(Constants.CD_FLAG_D);
		bsAccountJnl.setUserId1(accountJnl.getUserId1());
		bsAccountJnl.setAccountId1(userSubAccount.getAccountId());
		bsAccountJnl.setSubAccountId1(userSubAccount.getId());
		bsAccountJnl.setSubAccountCode1(userSubAccount.getCode());
		bsAccountJnl.setBeforeBalance1(userSubAccount.getBalance());
		bsAccountJnl.setBeforeAvialableBalance1(userSubAccount.getAvailableBalance());
		bsAccountJnl.setBeforeFreezeBalance1(userSubAccount.getFreezeBalance());
		bsAccountJnl.setAfterBalance1(MoneyUtil.add(userSubAccount.getBalance(), accountJnl.getTransAmount()).doubleValue());
		bsAccountJnl.setAfterAvialableBalance1(MoneyUtil.add(userSubAccount.getAvailableBalance(), accountJnl.getTransAmount()).doubleValue());
		bsAccountJnl.setAfterFreezeBalance1(userSubAccount.getFreezeBalance());
		bsAccountJnl.setCdFlag2(Constants.CD_FLAG_C);
		bsAccountJnl.setFee(0d);
		bsAccountJnl.setStatus(accountJnl.getStatus());
		log.debug("======交易【后置充值记账流水】数据：" + bsAccountJnl.toString() + "======");
		bsAccountJnlMapper.insertSelective(bsAccountJnl);
		log.info("======交易【后置充值记账】：执行结束======");
		
	}

}
