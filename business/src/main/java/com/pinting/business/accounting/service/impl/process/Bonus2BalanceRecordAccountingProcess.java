package com.pinting.business.accounting.service.impl.process;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.pinting.business.dao.BsAccountJnlMapper;
import com.pinting.business.dao.BsSubAccountMapper;
import com.pinting.business.model.BsAccountJnl;
import com.pinting.business.model.BsSubAccount;
import com.pinting.business.util.Constants;
import com.pinting.business.util.TransCode;
import com.pinting.core.util.MoneyUtil;

/**
 * 奖励金转余额记账
 * 
 * @author bianyatian
 * 
 */
@Component("UserBonus2BalanceRecordAccountingProcess")
public class Bonus2BalanceRecordAccountingProcess extends BaseAccountingProcess {

	@Autowired
	private BsSubAccountMapper bsSubAccountMapper;
	@Autowired
	private BsAccountJnlMapper bsAccountJnlMapper;
	
	@Override
	public Integer preRecordAccountingExecute(BsAccountJnl accountJnl) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void postfRecordAccountingExecute(BsAccountJnl accountJnl) {

		// 用户子账户-结算户、奖励金 查询
		BsSubAccount userJSH = bsSubAccountMapper
				.selectJSHSubAccountByUserId(accountJnl.getUserId1());
		BsSubAccount userJLJ = bsSubAccountMapper
				.selectJLJSubAccountByUserId(accountJnl.getUserId1());
		// 用户记账流水表-记账
		BsAccountJnl jnl = new BsAccountJnl();
		jnl.setTransTime(accountJnl.getTransTime());
		jnl.setTransCode(accountJnl.getTransCode());
		jnl.setTransAmount(accountJnl.getTransAmount());
		jnl.setStatus(accountJnl.getStatus());
		jnl.setUserId1(accountJnl.getUserId1());
		jnl.setUserId2(accountJnl.getUserId2());
		jnl.setTransType(Constants.TRANS_TYPE_BONUS2BALANCE);
		jnl.setTransName("奖励金转余额");
		jnl.setSysTime(new Date());
		jnl.setCdFlag1(Constants.CD_FLAG_C); //对JLJ而言是转出，为贷
		jnl.setAccountId1(userJLJ.getAccountId());
		jnl.setSubAccountId1(userJLJ.getId());
		jnl.setSubAccountCode1(userJLJ.getCode());
		jnl.setBeforeBalance1(userJLJ.getBalance());
		jnl.setBeforeAvialableBalance1(userJLJ.getAvailableBalance());
		jnl.setBeforeFreezeBalance1(userJLJ.getFreezeBalance());
		jnl.setAfterBalance1(userJLJ.getBalance() - jnl.getTransAmount());
		jnl.setAfterAvialableBalance1(userJLJ.getAvailableBalance()
				- jnl.getTransAmount());
		jnl.setAfterFreezeBalance1(userJLJ.getFreezeBalance());
		
		jnl.setCdFlag2(Constants.CD_FLAG_D); //对JSH而言是转入，为借
		jnl.setAccountId2(userJSH.getAccountId());
		jnl.setSubAccountId2(userJSH.getId());
		jnl.setSubAccountCode2(userJSH.getCode());
		jnl.setBeforeBalance2(userJSH.getBalance());
		jnl.setBeforeAvialableBalance2(userJSH.getAvailableBalance());
		jnl.setBeforeFreezeBalance2(userJSH.getFreezeBalance());
		jnl.setAfterBalance2(MoneyUtil.add(userJSH.getBalance(), jnl.getTransAmount()).doubleValue());
		jnl.setAfterAvialableBalance2(MoneyUtil.add(userJSH.getAvailableBalance(),jnl.getTransAmount()).doubleValue());
		jnl.setAfterFreezeBalance2(userJSH.getFreezeBalance());
		jnl.setCdFlag2(Constants.CD_FLAG_D);
		jnl.setFee(0d);

		// 用户子账户-结算户、奖励金 修改
		log.info("======【奖励金转账户余额】：用户子账户-结算户JSH、奖励金JLJ余额修改=====");
		BsSubAccount JSH = new BsSubAccount();
		JSH.setId(userJSH.getId());
		JSH.setBalance(accountJnl.getTransAmount());
		JSH.setAvailableBalance(accountJnl.getTransAmount());
		JSH.setCanWithdraw(accountJnl.getTransAmount());
		JSH.setLastTransDate(new Date());
		bsSubAccountMapper.updateBalancesByIncrement(JSH);
		BsSubAccount JLJ = new BsSubAccount();
		JLJ.setId(userJLJ.getId());
		JLJ.setBalance(accountJnl.getTransAmount() * -1);
		JLJ.setAvailableBalance(accountJnl.getTransAmount() * -1);
		JLJ.setCanWithdraw(accountJnl.getTransAmount() * -1);
		JLJ.setLastTransDate(new Date());
		bsSubAccountMapper.updateBalancesByIncrement(JLJ);
		
		log.info("======【奖励金转账户余额】：新增用户记账流水表=====");
		//新增用户记账流水表
		log.debug("用户记账流水表记账，JLJ转JSH：" + jnl.toString());
		bsAccountJnlMapper.insertSelective(jnl);
		log.info("======交易【奖励金转账户余额】用户子账户修改及用户记账流水新增：执行结束======");
		
	}

}
