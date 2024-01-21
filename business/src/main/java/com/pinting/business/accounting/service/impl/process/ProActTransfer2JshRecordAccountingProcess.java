package com.pinting.business.accounting.service.impl.process;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.pinting.business.dao.BsAccountJnlMapper;
import com.pinting.business.dao.BsSubAccountMapper;
import com.pinting.business.model.BsAccountJnl;
import com.pinting.business.model.BsSubAccount;
import com.pinting.business.model.BsSubAccountExample;
import com.pinting.business.service.site.SubAccountService;
import com.pinting.business.service.site.UserService;
import com.pinting.business.util.Constants;
import com.pinting.core.util.MoneyUtil;

/**
 * 用户内部产品子账户转账到JSH记账
 * @Project: business
 * @Title: ProActTransfer2JshRecordAccountingProcess.java
 * @author dingpf
 * @date 2015-3-23 上午11:11:41
 * @Copyright: 2015 BiGangWan.com Inc. All rights reserved.
 */
@Component("ProActTransfer2JshRecordAccountingProcess")
public class ProActTransfer2JshRecordAccountingProcess extends BaseAccountingProcess{
	@Autowired
	private BsSubAccountMapper bsSubAccountMapper;
	@Autowired
	private BsAccountJnlMapper bsAccountJnlMapper;
	@Autowired
	private UserService userService;
	@Override
	public Integer preRecordAccountingExecute(BsAccountJnl accountJnl) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	@Transactional(rollbackFor=Exception.class)
	public void postfRecordAccountingExecute(BsAccountJnl accountJnl)  {
		log.info("======交易【用户内部产品子账户转账到JSH记账】：执行开始======");
		
		log.info("======交易【用户内部产品子账户转账到JSH记账】：结算户余额增加======");
		BsSubAccount userSubAccount = bsSubAccountMapper.selectJSHSubAccountByUserId(accountJnl.getUserId1());
		//为防止并发问题， 结算户余额修改 这里作增量修改
		BsSubAccount userTmpSubAccount = new BsSubAccount();
		userTmpSubAccount.setId(userSubAccount.getId());
		userTmpSubAccount.setBalance(accountJnl.getTransAmount());
		userTmpSubAccount.setAvailableBalance(accountJnl.getTransAmount());
		userTmpSubAccount.setCanWithdraw(accountJnl.getTransAmount());
		bsSubAccountMapper.updateBalancesByIncrement(userTmpSubAccount);
		
		log.info("======交易【用户内部产品子账户转账到JSH记账】：产品设置为已结算======");
		//产品设置为已结算
		BsSubAccount productAccount = new BsSubAccount();
		productAccount.setId(accountJnl.getSubAccountId2());
		productAccount.setStatus(Constants.SUBACCOUNT_STATUS_SETTLE);
		BsSubAccountExample subAccountExample = new BsSubAccountExample();
		subAccountExample.createCriteria().andIdEqualTo(accountJnl.getSubAccountId2());
		bsSubAccountMapper.updateByExampleSelective(productAccount, subAccountExample);
		
		log.info("======交易【用户内部产品子账户转账到JSH记账】：流水记账======");
		//记流水表
		BsAccountJnl bsAccountJnl = new BsAccountJnl();
		bsAccountJnl.setTransTime(accountJnl.getTransTime());
		bsAccountJnl.setTransCode(accountJnl.getTransCode());
		bsAccountJnl.setTransType(Constants.TRANS_TYPE_TRANSFER);
		bsAccountJnl.setTransName("回款");//用户内部产品子账户转账到JSH
		bsAccountJnl.setTransAmount(accountJnl.getTransAmount());
		bsAccountJnl.setSysTime(new Date());
		bsAccountJnl.setCdFlag1(Constants.CD_FLAG_D);//用户结算户 金额增加
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
		bsAccountJnl.setAccountId2(userSubAccount.getAccountId());
		bsAccountJnl.setSubAccountId2(accountJnl.getSubAccountId2());
		bsAccountJnl.setSubAccountCode2(accountJnl.getSubAccountCode2());
		bsAccountJnl.setFee(0d);
		bsAccountJnl.setStatus(accountJnl.getStatus());
		log.debug("======交易【用户内部产品子账户转账到JSH记账】数据：" + bsAccountJnl.toString() + "======");
		bsAccountJnlMapper.insertSelective(bsAccountJnl);
		log.info("======交易【用户内部产品子账户转账到JSH记账】：执行结束======");
		
	}

}
