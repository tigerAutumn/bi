package com.pinting.mall.service.impl;

import java.util.Date;

import com.pinting.mall.enums.MallAcountTypeEnum;
import com.pinting.mall.enums.MallRuleEnum;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pinting.core.util.MoneyUtil;
import com.pinting.mall.dao.MallAccountJnlMapper;
import com.pinting.mall.dao.MallAccountMapper;
import com.pinting.mall.dao.MallPointsIncomeMapper;
import com.pinting.mall.enums.MallAccountEnum;
import com.pinting.mall.model.MallAccount;
import com.pinting.mall.model.MallAccountJnl;
import com.pinting.mall.model.MallPointsIncome;
import com.pinting.mall.model.dto.MallIncomeResultInfo;
import com.pinting.mall.service.MallPointsAccountService;
import com.pinting.mall.util.Constants;

@Service
public class MallPointsAccountServiceImpl implements MallPointsAccountService{
	
	private Logger logger = LoggerFactory.getLogger(MallPointsAccountServiceImpl.class);
    
    @Autowired
	private MallAccountMapper accountMapper;
    @Autowired
    private MallAccountJnlMapper accountJnlMapper;
    @Autowired
    private MallPointsIncomeMapper pointsIncomeMapper;
    
    /**
     * 更新账户余额信息
     * */
    @Override
    @Transactional
	public MallAccountJnl grantsPointsAccount(MallIncomeResultInfo resultInfo) {
    	
    	//更新mall_account
    	MallAccount pointsAcct = accountMapper.selectMallAccountByIdForLock(resultInfo.getAcctId());
    	MallAccount tmpPointsAcct = new MallAccount();
    	tmpPointsAcct.setId(resultInfo.getAcctId());
    	tmpPointsAcct.setBalance(MoneyUtil.add(pointsAcct.getBalance(), resultInfo.getPoints()).longValue());
    	tmpPointsAcct.setAvaliableBalance(MoneyUtil.add(pointsAcct.getAvaliableBalance(), resultInfo.getPoints()).longValue());
    	tmpPointsAcct.setTotalGainPoints(MoneyUtil.add(pointsAcct.getTotalGainPoints(), resultInfo.getPoints()).longValue());
    	accountMapper.updateByPrimaryKeySelective(tmpPointsAcct);
    	//插入mall_account_jnl
    	MallAccountJnl accountJnl = new MallAccountJnl();
    	accountJnl.setUserId(pointsAcct.getUserId());
    	accountJnl.setAccountId(pointsAcct.getId());
    	accountJnl.setBeforeBalance(pointsAcct.getBalance());
    	accountJnl.setAfterBalance(MoneyUtil.add(pointsAcct.getBalance(), resultInfo.getPoints()).longValue());
    	accountJnl.setBeforeAvaliableBalance(pointsAcct.getAvaliableBalance());
    	accountJnl.setAfterAvaliableBalance(MoneyUtil.add(pointsAcct.getAvaliableBalance(), resultInfo.getPoints()).longValue());
    	accountJnl.setPoints(resultInfo.getPoints());
    	accountJnl.setTransType(MallAcountTypeEnum.getEnumByCode(resultInfo.getTransType()).getCode());
    	accountJnl.setTransName(MallAcountTypeEnum.getEnumByCode(resultInfo.getTransType()).getMessage());
    	accountJnl.setRuleId(resultInfo.getRuleId());
    	accountJnl.setTransTime(new Date());
    	accountJnl.setCreateTime(new Date());
    	accountJnl.setUpdateTime(new Date());
    	accountJnlMapper.insertSelective(accountJnl);
    	return accountJnl;
	}	
    
	@Override
	@Transactional
	public MallAccount openAccount( int userId ) {
		/**
		 * 用户积分账户开户
		 * 新增Mall_Account,Mall_Account_Jnl流水
		 * */
		MallAccount account = new MallAccount();
		account.setUserId(userId);
		account.setAccountType(MallAccountEnum.MALL_POINTS_JSH.getCode());
		account.setBalance(0l);
		account.setAvaliableBalance(0l);
		account.setFreezeBalance(0l);
		account.setStatus(Constants.MALL_ACCOUNT_STATUS_OPEN);
		account.setTotalGainPoints(0l);
		account.setTotalUsedPoints(0l);
		account.setCreateTime(new Date());
		account.setUpdateTime(new Date());
		accountMapper.insertSelective(account);
		int accountId = account.getId();
		MallAccount resAcct = account;
		MallAccountJnl accountJnl = new MallAccountJnl();
		accountJnl.setAccountId(accountId);
		accountJnl.setUserId(userId);
		accountJnl.setTransName(MallAcountTypeEnum.OPEN_ACCOUNT.getMessage());
		accountJnl.setTransType(MallAcountTypeEnum.OPEN_ACCOUNT.getCode());
		accountJnl.setTransTime(new Date());
		accountJnl.setCreateTime(new Date());
		accountJnl.setUpdateTime(new Date());
		accountJnlMapper.insertSelective(accountJnl);
		logger.info("用户["+userId+"]开户成功,积分账户编号=["+accountId+"]");
		return resAcct;
	}
	
}