package com.pinting.business.accounting.service.impl;

import com.pinting.business.accounting.loan.enums.PartnerEnum;
import com.pinting.business.accounting.loan.model.BaseAccount;
import com.pinting.business.accounting.loan.model.PartnerSysActCode;
import com.pinting.business.accounting.service.BsSysSubAccountService;
import com.pinting.business.dao.BsSysAccountJnlMapper;
import com.pinting.business.dao.BsSysSubAccountMapper;
import com.pinting.business.exception.PTMessageException;
import com.pinting.business.model.BsSysAccountJnl;
import com.pinting.business.model.BsSysSubAccount;
import com.pinting.business.util.Constants;
import com.pinting.core.util.MoneyUtil;
import com.pinting.core.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import java.math.BigDecimal;
import java.util.Date;

@Service
public class BsSysSubAccountServiceImpl implements BsSysSubAccountService {
	@Autowired
	private TransactionTemplate transactionTemplate;
	@Autowired
	private BsSysSubAccountMapper bsSysSubAccountMapper;
	@Autowired
	private BsSysAccountJnlMapper bsSysAccountJnlMapper;
	
	private Logger log = LoggerFactory.getLogger(BsSysSubAccountServiceImpl.class);
	@Override
	@Transactional(rollbackFor=PTMessageException.class)
	public boolean updateJSHToUser(Double amount) {
		BsSysSubAccount jshSysAccount = bsSysSubAccountMapper.selectByCode(Constants.SYS_ACCOUNT_JSH);
		BsSysSubAccount userSysAccount = bsSysSubAccountMapper.selectByCode(Constants.SYS_ACCOUNT_USER);
		
		BsSysSubAccount jshAccount = new BsSysSubAccount();
		jshAccount.setId(jshSysAccount.getId());
		jshAccount.setBalance(-amount);
		jshAccount.setAvailableBalance(-amount);
		jshAccount.setCanWithdraw(-amount);
		jshAccount.setLastTransDate(new Date());
		
		BsSysSubAccount userAccount = new BsSysSubAccount();
		userAccount.setId(userSysAccount.getId());
		userAccount.setBalance(amount);
		userAccount.setAvailableBalance(amount);
		userAccount.setCanWithdraw(amount);
		userAccount.setLastTransDate(new Date());
		log.info("======【奖励金转账户余额】：系统子账户-结算户JSH，余额修改=====");
		bsSysSubAccountMapper.updateById(jshAccount);
		log.info("======【奖励金转账户余额】：系统子账户-用户USER查询，余额修改=====");
		bsSysSubAccountMapper.updateById(userAccount);
		
		return true;
	}
	
	@Override
	public void redPacketTopUp(final Double amount) {
		transactionTemplate.execute(new TransactionCallbackWithoutResult(){
			@Override
			protected void doInTransactionWithoutResult(TransactionStatus status) {
				//系统子账户表 redPakt 增加金额
				BsSysSubAccount redPaktSubAccountLock = findSysSubAccount4Lock(Constants.SYS_ACCOUNT_RED_PACKET);
				BsSysSubAccount readyUpdateRedPakt = new BsSysSubAccount();
				readyUpdateRedPakt.setId(redPaktSubAccountLock.getId());
				readyUpdateRedPakt.setBalance(MoneyUtil.add(redPaktSubAccountLock.getBalance(), amount).doubleValue());
				readyUpdateRedPakt.setAvailableBalance(MoneyUtil.add(redPaktSubAccountLock.getAvailableBalance(), amount).doubleValue());
				readyUpdateRedPakt.setCanWithdraw(MoneyUtil.add(redPaktSubAccountLock.getCanWithdraw(), amount).doubleValue());
				readyUpdateRedPakt.setLastTransDate(new Date());
				bsSysSubAccountMapper.updateByPrimaryKeySelective(readyUpdateRedPakt);
				
				//新增系统记账流水表，redPakt余额增加
				BsSysAccountJnl sysAccountJnl = new BsSysAccountJnl();
				sysAccountJnl.setTransTime(new Date());
				sysAccountJnl.setTransCode(Constants.SYS_RED_PACKET_TOP_UP);
				sysAccountJnl.setTransName("系统红包充值（红包预算审核通过）");
				sysAccountJnl.setTransAmount(amount);
				sysAccountJnl.setSysTime(new Date());
				sysAccountJnl.setChannelTime(new Date());
				sysAccountJnl.setCdFlag2(Constants.CD_FLAG_D);
				sysAccountJnl.setSubAccountCode2(redPaktSubAccountLock.getCode());
				sysAccountJnl.setBeforeBalance2(redPaktSubAccountLock.getBalance());
				sysAccountJnl.setAfterBalance2(readyUpdateRedPakt.getBalance());
				sysAccountJnl.setBeforeAvialableBalance2(redPaktSubAccountLock.getAvailableBalance());
				sysAccountJnl.setAfterAvialableBalance2(readyUpdateRedPakt.getAvailableBalance());
				sysAccountJnl.setBeforeFreezeBalance2(redPaktSubAccountLock.getFreezeBalance());
				sysAccountJnl.setAfterFreezeBalance2(redPaktSubAccountLock.getFreezeBalance());
				sysAccountJnl.setFee(0.0);
				sysAccountJnl.setStatus(Constants.SYS_ACCOUNT_STATUS_SUCCESS);
				bsSysAccountJnlMapper.insertSelective(sysAccountJnl);
			}
		});
		
	}

	@Override
	@Transactional
	public void redPacketUsed(final Double amount) {
		//存管自有子账户-
		BsSysSubAccount jshSubAccountLock = findSysSubAccount4Lock(Constants.SYS_ACCOUNT_DEP_JSH);
		BsSysSubAccount readyUpdateJsh = new BsSysSubAccount();
		readyUpdateJsh.setId(jshSubAccountLock.getId());
		readyUpdateJsh.setBalance(jshSubAccountLock.getBalance() - amount);
		readyUpdateJsh.setAvailableBalance(jshSubAccountLock.getAvailableBalance() - amount);
		readyUpdateJsh.setCanWithdraw(jshSubAccountLock.getCanWithdraw() - amount);
		readyUpdateJsh.setLastTransDate(new Date());
		bsSysSubAccountMapper.updateByPrimaryKeySelective(readyUpdateJsh);
		//存管红包户+
		BsSysSubAccount redSubAccountLock = findSysSubAccount4Lock(Constants.SYS_ACCOUNT_DEP_REDPACKET);
		BsSysSubAccount readyUpdateRed = new BsSysSubAccount();
		readyUpdateRed.setId(redSubAccountLock.getId());
		readyUpdateRed.setBalance(MoneyUtil.add(redSubAccountLock.getBalance(), amount).doubleValue());
		readyUpdateRed.setAvailableBalance(MoneyUtil.add(redSubAccountLock.getAvailableBalance(), amount).doubleValue());
		readyUpdateRed.setCanWithdraw(MoneyUtil.add(redSubAccountLock.getCanWithdraw(), amount).doubleValue());
		readyUpdateRed.setLastTransDate(new Date());
		bsSysSubAccountMapper.updateByPrimaryKeySelective(readyUpdateRed);

		//记账
		BsSysAccountJnl sysAccountJnl = new BsSysAccountJnl();
		sysAccountJnl.setTransTime(new Date());
		sysAccountJnl.setTransCode(Constants.SYS_RED_PACKET_USED);
		sysAccountJnl.setTransName("系统红包使用（购买使用红包）");
		sysAccountJnl.setTransAmount(amount);
		sysAccountJnl.setSysTime(new Date());
		sysAccountJnl.setChannelTime(new Date());
		sysAccountJnl.setCdFlag1(Constants.CD_FLAG_C);
		sysAccountJnl.setSubAccountCode1(jshSubAccountLock.getCode());
		sysAccountJnl.setBeforeBalance1(jshSubAccountLock.getBalance());
		sysAccountJnl.setAfterBalance1(readyUpdateJsh.getBalance());
		sysAccountJnl.setBeforeAvialableBalance1(jshSubAccountLock.getAvailableBalance());
		sysAccountJnl.setAfterAvialableBalance1(readyUpdateJsh.getAvailableBalance());
		sysAccountJnl.setBeforeFreezeBalance1(jshSubAccountLock.getFreezeBalance());
		sysAccountJnl.setAfterFreezeBalance1(jshSubAccountLock.getFreezeBalance());
		sysAccountJnl.setCdFlag2(Constants.CD_FLAG_D);
		sysAccountJnl.setSubAccountCode2(redSubAccountLock.getCode());
		sysAccountJnl.setBeforeBalance2(redSubAccountLock.getBalance());
		sysAccountJnl.setAfterBalance2(readyUpdateRed.getBalance());
		sysAccountJnl.setBeforeAvialableBalance2(redSubAccountLock.getAvailableBalance());
		sysAccountJnl.setAfterAvialableBalance2(readyUpdateRed.getAvailableBalance());
		sysAccountJnl.setBeforeFreezeBalance2(redSubAccountLock.getFreezeBalance());
		sysAccountJnl.setAfterFreezeBalance2(redSubAccountLock.getFreezeBalance());
		sysAccountJnl.setFee(0.0);
		sysAccountJnl.setStatus(Constants.SYS_ACCOUNT_STATUS_SUCCESS);
		bsSysAccountJnlMapper.insertSelective(sysAccountJnl);
	}

    @Override
    public void financialRegistry(final Double amount, final Integer opUserId) {
        transactionTemplate.execute(new TransactionCallbackWithoutResult(){
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus status) {
                // 系统子账户表 financialRegSubAccountLock 减少金额
                BsSysSubAccount financialRegSubAccountLock = findSysSubAccount4Lock(Constants.SYS_ACCOUNT_JSH);
                BsSysSubAccount readyUpdateFinancialReg = new BsSysSubAccount();
                readyUpdateFinancialReg.setId(financialRegSubAccountLock.getId());
                readyUpdateFinancialReg.setBalance(MoneyUtil.subtract(financialRegSubAccountLock.getBalance(), amount).doubleValue());
                readyUpdateFinancialReg.setAvailableBalance(MoneyUtil.subtract(financialRegSubAccountLock.getAvailableBalance(), amount).doubleValue());
                readyUpdateFinancialReg.setCanWithdraw(MoneyUtil.subtract(financialRegSubAccountLock.getCanWithdraw(), amount).doubleValue());
                readyUpdateFinancialReg.setLastTransDate(new Date());
                bsSysSubAccountMapper.updateByPrimaryKeySelective(readyUpdateFinancialReg);

                // 新增系统记账流水表
                BsSysAccountJnl sysAccountJnl = new BsSysAccountJnl();
                sysAccountJnl.setTransTime(new Date());
                sysAccountJnl.setTransCode(Constants.SYS_FINANCE_WITHDRAW_REGISTRY);
                sysAccountJnl.setTransName("系统财务19付提现登记");
                sysAccountJnl.setTransAmount(amount);
                sysAccountJnl.setSysTime(new Date());
                sysAccountJnl.setChannelTime(new Date());
                sysAccountJnl.setCdFlag1(Constants.CD_FLAG_C);
                sysAccountJnl.setSubAccountCode1(financialRegSubAccountLock.getCode());
                sysAccountJnl.setBeforeBalance1(financialRegSubAccountLock.getBalance());
                sysAccountJnl.setAfterBalance1(readyUpdateFinancialReg.getBalance());
                sysAccountJnl.setBeforeAvialableBalance1(financialRegSubAccountLock.getAvailableBalance());
                sysAccountJnl.setAfterAvialableBalance1(readyUpdateFinancialReg.getAvailableBalance());
                sysAccountJnl.setBeforeFreezeBalance1(financialRegSubAccountLock.getFreezeBalance());
                sysAccountJnl.setAfterFreezeBalance1(financialRegSubAccountLock.getFreezeBalance());
                sysAccountJnl.setFee(0.0);
                sysAccountJnl.setStatus(Constants.SYS_ACCOUNT_STATUS_SUCCESS);
                bsSysAccountJnlMapper.insertSelective(sysAccountJnl);
            }
        });
    }

	@Override
	public void updateMarginSysSubAccount(final Double amount) {
		transactionTemplate.execute(new TransactionCallbackWithoutResult() {

			@Override
			protected void doInTransactionWithoutResult(TransactionStatus status) {
				// 更新赞分期风险保证金户金额
				PartnerSysActCode partnerActCode = BaseAccount.partnerActCodeMap.get(PartnerEnum.ZAN);
				BsSysSubAccount patMarginAct = findSysSubAccount4Lock(partnerActCode.getMarginActCode());
				BsSysSubAccount tempPatMarginAct = new BsSysSubAccount();
				tempPatMarginAct.setId(patMarginAct.getId());
				tempPatMarginAct.setBalance(MoneyUtil.add(patMarginAct.getBalance(), amount).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
				tempPatMarginAct.setAvailableBalance(MoneyUtil.add(patMarginAct.getAvailableBalance(), amount).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
				tempPatMarginAct.setCanWithdraw(MoneyUtil.add(patMarginAct.getCanWithdraw(), amount).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
				bsSysSubAccountMapper.updateByPrimaryKeySelective(tempPatMarginAct);

				//赞分期风险保证金系统账户记账
				BsSysAccountJnl sysAccountJnl = new BsSysAccountJnl();
				sysAccountJnl.setTransTime(new Date());
				sysAccountJnl.setTransCode(Constants.SYS_TOP_UP_4_MARGIN);
				sysAccountJnl.setTransName("赞分期风险保证金户系统充值");
				sysAccountJnl.setTransAmount(amount);
				sysAccountJnl.setSysTime(new Date());
				sysAccountJnl.setChannelTime(null);
				sysAccountJnl.setChannelJnlNo(null);
				sysAccountJnl.setCdFlag1(Constants.CD_FLAG_C);
				sysAccountJnl.setSubAccountCode1(patMarginAct.getCode());
				sysAccountJnl.setBeforeBalance1(patMarginAct.getBalance());
				sysAccountJnl.setAfterBalance1(tempPatMarginAct.getBalance());
				sysAccountJnl.setBeforeAvialableBalance1(patMarginAct.getAvailableBalance());
				sysAccountJnl.setAfterAvialableBalance1(tempPatMarginAct.getAvailableBalance());
				sysAccountJnl.setBeforeFreezeBalance1(patMarginAct.getFreezeBalance());
				sysAccountJnl.setAfterFreezeBalance1(patMarginAct.getFreezeBalance());
				sysAccountJnl.setFee(0.0);
				bsSysAccountJnlMapper.insertSelective(sysAccountJnl);
			}

		});

	}

	/**
	 * 根据账户号查询记录并加行级锁
	 *
	 * @param code
	 * @return
	 */
	@Override
	public BsSysSubAccount findSysSubAccount4Lock(String code) {
		if(StringUtil.isNotEmpty(code)){
			BsSysSubAccount tempAct = bsSysSubAccountMapper.selectByCode(code);
			if(tempAct != null){
				return bsSysSubAccountMapper.selectSysSubAccountForLock(tempAct.getId());
			}
		}
		return null;
	}
}
