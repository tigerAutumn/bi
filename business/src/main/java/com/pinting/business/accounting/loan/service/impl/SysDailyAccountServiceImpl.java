package com.pinting.business.accounting.loan.service.impl;

import com.pinting.business.accounting.loan.enums.PartnerEnum;
import com.pinting.business.accounting.loan.model.BaseAccount;
import com.pinting.business.accounting.loan.model.PartnerSysActCode;
import com.pinting.business.accounting.loan.service.SysDailyAccountService;
import com.pinting.business.accounting.service.BsSysSubAccountService;
import com.pinting.business.dao.BsSysAccountJnlMapper;
import com.pinting.business.dao.BsSysSubAccountMapper;
import com.pinting.business.enums.PTMessageEnum;
import com.pinting.business.exception.PTMessageException;
import com.pinting.business.model.BsSysAccountJnl;
import com.pinting.business.model.BsSysSubAccount;
import com.pinting.business.util.Constants;
import com.pinting.core.util.MoneyUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;

import java.math.BigDecimal;
import java.util.Date;

@Service
public class SysDailyAccountServiceImpl implements SysDailyAccountService {
	private final Logger logger = LoggerFactory.getLogger(SysDailyAccountServiceImpl.class);
	@Autowired
	private TransactionTemplate transactionTemplate;
	@Autowired
	private BsSysSubAccountMapper bsSysSubAccountMapper;
	@Autowired
	private BsSysAccountJnlMapper bsSysAccountJnlMapper;
	@Autowired
	private BsSysSubAccountService bsSysSubAccountService;
	@Override
	@Transactional
	public void chargeDailyRevenueSettle(BaseAccount baseAccount) {
		logger.info("[chargeDailyRevenueSettle]入参：" + baseAccount.toString());
		final PartnerEnum partner = baseAccount.getPartner();
		final Double amount = baseAccount.getAmount();
		if(partner == null || amount == null){
			throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR);
		}
		/*transactionTemplate.execute(new TransactionCallbackWithoutResult(){
			@Override
			protected void doInTransactionWithoutResult(TransactionStatus status) {*/
				//S:REVENUE_ZAN - 
				PartnerSysActCode partnerActCode = BaseAccount.partnerActCodeMap.get(partner);
				BsSysSubAccount patRevenueAct = bsSysSubAccountService.findSysSubAccount4Lock(partnerActCode.getRevenueActCode());
				BsSysSubAccount tempPatRevenueAct = new BsSysSubAccount();
				tempPatRevenueAct.setId(patRevenueAct.getId());
				tempPatRevenueAct.setBalance(MoneyUtil.subtract(patRevenueAct.getBalance(), amount).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
				tempPatRevenueAct.setAvailableBalance(MoneyUtil.subtract(patRevenueAct.getAvailableBalance(), amount).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
				tempPatRevenueAct.setCanWithdraw(MoneyUtil.subtract(patRevenueAct.getCanWithdraw(), amount).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
				bsSysSubAccountMapper.updateByPrimaryKeySelective(tempPatRevenueAct);
				
				//系统账户记账
				BsSysAccountJnl sysAccountJnl = new BsSysAccountJnl();
				sysAccountJnl.setTransTime(new Date());
				sysAccountJnl.setTransCode(Constants.SYS_PAT_REVENUE_SETTLE);
				sysAccountJnl.setTransName("合作方营收账户结算");
				sysAccountJnl.setTransAmount(amount);
				sysAccountJnl.setSysTime(new Date());
				sysAccountJnl.setChannelTime(null);
				sysAccountJnl.setChannelJnlNo(null);
				sysAccountJnl.setCdFlag1(Constants.CD_FLAG_C);
				sysAccountJnl.setSubAccountCode1(patRevenueAct.getCode());
				sysAccountJnl.setBeforeBalance1(patRevenueAct.getBalance());
				sysAccountJnl.setAfterBalance1(tempPatRevenueAct.getBalance());
				sysAccountJnl.setBeforeAvialableBalance1(patRevenueAct.getAvailableBalance());
				sysAccountJnl.setAfterAvialableBalance1(tempPatRevenueAct.getAvailableBalance());
				sysAccountJnl.setBeforeFreezeBalance1(patRevenueAct.getFreezeBalance());
				sysAccountJnl.setAfterFreezeBalance1(patRevenueAct.getFreezeBalance());
				sysAccountJnl.setFee(0.0);
				bsSysAccountJnlMapper.insertSelective(sysAccountJnl);
				
			/*}
		});*/
		

	}

}
