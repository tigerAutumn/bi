package com.pinting.business.accounting.service.impl.process;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.pinting.business.dao.BsAccountJnlMapper;
import com.pinting.business.dao.BsSubAccountMapper;
import com.pinting.business.model.BsAccountJnl;
import com.pinting.business.model.BsAccountJnlExample;
import com.pinting.business.model.BsDailyBonus;
import com.pinting.business.model.BsSubAccount;
import com.pinting.business.model.BsUser;
import com.pinting.business.service.site.BonusService;
import com.pinting.business.service.site.UserService;
import com.pinting.business.util.Constants;
import com.pinting.core.util.MoneyUtil;

/**
 * 推荐奖励记账
 * @Project: business
 * @Title: BonusRecordAccountingProcess.java
 * @author dingpf
 * @date 2015-3-23 上午11:11:41
 * @Copyright: 2015 BiGangWan.com Inc. All rights reserved.
 */
@Component("RecommendBonusRecordAccountingProcess")
public class BonusRecordAccountingProcess extends BaseAccountingProcess{
	@Autowired
	private BsSubAccountMapper bsSubAccountMapper;
	@Autowired
	private BsAccountJnlMapper bsAccountJnlMapper;
	@Autowired
	private BonusService bonusService;
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
		log.info("======交易【推荐奖励记账】：执行开始======");
		//往推荐人结算户里面累计奖励金
		log.info("======交易【推荐奖励记账】：修改推荐人用户结算户余额======");
		//查询该用户 结算户 信息
		BsSubAccount userSubAccount = bsSubAccountMapper.selectJSHSubAccountByUserId(accountJnl.getUserId1());
		//为防止并发问题， 结算户余额修改 这里作增量修改
		BsSubAccount userTmpSubAccount = new BsSubAccount();
		userTmpSubAccount.setId(userSubAccount.getId());
		userTmpSubAccount.setBalance(accountJnl.getTransAmount());
		userTmpSubAccount.setAvailableBalance(accountJnl.getTransAmount());
		userTmpSubAccount.setCanWithdraw(accountJnl.getTransAmount());
		bsSubAccountMapper.updateBalancesByIncrement(userTmpSubAccount);
		
		//新增推荐奖励金记录
		BsDailyBonus dailyBonus = new BsDailyBonus();
		dailyBonus.setUserId(accountJnl.getUserId1());
		dailyBonus.setBeRecommendUserId(accountJnl.getUserId2());
		dailyBonus.setBonus(accountJnl.getTransAmount());
		dailyBonus.setTime(new Date());
		bonusService.addDailyBonus(dailyBonus);
		
		//修改用户表推荐奖励金字段
		BsUser user = new BsUser();
		user.setId(accountJnl.getUserId1());
		user.setCurrentBonus(accountJnl.getTransAmount());
		user.setCanWithdraw(accountJnl.getTransAmount());
		user.setTotalBonus(accountJnl.getTransAmount());
		userService.modifyBonusByIdAndIncrement(user);
		
		/* 暂时去掉品听内部结算户 计算
		 * 
		 * log.info("======交易【推荐奖励记账】：修改品听结算户（内部户）余额======");
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
		bsAccountJnl.setTransType(Constants.TRANS_TYPE_BONUS);
		bsAccountJnl.setTransName("推荐奖励");
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
		bsAccountJnl.setFee(0d);
		bsAccountJnl.setStatus(accountJnl.getStatus());
		log.debug("======交易【推荐奖励记账】数据：" + bsAccountJnl.toString() + "======");
		bsAccountJnlMapper.insertSelective(bsAccountJnl);
		log.info("======交易【推荐奖励记账】：执行结束======");
		
	}

}
