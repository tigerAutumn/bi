package com.pinting.business.accounting.loan.service.impl;

import com.pinting.business.accounting.loan.enums.PartnerEnum;
import com.pinting.business.accounting.loan.model.BaseAccount;
import com.pinting.business.accounting.loan.model.InvestorRegInfo;
import com.pinting.business.accounting.loan.model.PartnerSysActCode;
import com.pinting.business.accounting.loan.model.SuperTransferInfo;
import com.pinting.business.accounting.loan.service.LoanAccountService;
import com.pinting.business.accounting.loan.service.LoanRelationshipService;
import com.pinting.business.accounting.service.AccountHandleService;
import com.pinting.business.accounting.service.BsSysSubAccountService;
import com.pinting.business.dao.*;
import com.pinting.business.enums.PTMessageEnum;
import com.pinting.business.exception.PTMessageException;
import com.pinting.business.model.*;
import com.pinting.business.util.Constants;
import com.pinting.core.util.DateUtil;
import com.pinting.core.util.MoneyUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Service
public class LoanAccountServiceImpl implements LoanAccountService {
	private final Logger logger = LoggerFactory.getLogger(LoanAccountServiceImpl.class);
	@Autowired
	private TransactionTemplate transactionTemplate;
	@Autowired
	private AccountHandleService accountHandleService;
	@Autowired
	private LoanRelationshipService loanRelationshipService;
	@Autowired
	private BsSysSubAccountMapper bsSysSubAccountMapper;
	@Autowired
	private BsSubAccountMapper bsSubAccountMapper;
	@Autowired
	private BsAccountJnlMapper bsAccountJnlMapper;
	@Autowired
	private BsSysAccountJnlMapper bsSysAccountJnlMapper;
	@Autowired
	private BsProductMapper bsProductMapper;
	@Autowired
	private BsSysConfigMapper bsSysConfigMapper;
	@Autowired
	private BsSubAccountPairMapper bsSubAccountPairMapper;
	@Autowired
	private LnAccountMapper lnAccountMapper;
	@Autowired
	private BsSubAccountPairJnlMapper bsSubAccountPairJnlMapper;
	@Autowired
	private LnSubAccountMapper lnSubAccountMapper;
	@Autowired
	private LnAccountJnlMapper lnAccountJnlMapper;
	@Autowired
	private BsSysSubAccountService bsSysSubAccountService;

	@Override
	@Transactional
	public int chargeAuthActAdd(final BaseAccount baseAccount, final Integer productId) {
		logger.info("[chargeAuthActAdd]入参：" + baseAccount.toString() + ",productId=" + productId);
		final PartnerEnum partner = baseAccount.getPartner();
		final Integer investorUserId = baseAccount.getInvestorUserId();
		final Double amount = baseAccount.getAmount();
		if(partner == null || investorUserId == null || amount == null || productId == null){
			throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR);
		}
		/*return transactionTemplate.execute(new TransactionCallback<Integer>(){
			@Override
			public Integer doInTransaction(TransactionStatus status) {*/
				//F:JSH >>> AUTH
				BsSubAccount tempUserJsh = bsSubAccountMapper.selectSingleSubActByUserAndType(investorUserId, Constants.PRODUCT_TYPE_JSH);
				BsSubAccount jshAct = bsSubAccountMapper.selectSubAccountByIdForLock(tempUserJsh.getId());
				if(MoneyUtil.subtract(jshAct.getAvailableBalance(), amount).doubleValue() < 0) {
					throw new PTMessageException(PTMessageEnum.ACCOUNT_BALANCE_NOT_ENOUGH);
				}
				BsSubAccount tempJshAct = new BsSubAccount();
				tempJshAct.setId(jshAct.getId());
				tempJshAct.setBalance(MoneyUtil.subtract(jshAct.getBalance(), amount).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
				tempJshAct.setAvailableBalance(MoneyUtil.subtract(jshAct.getAvailableBalance(), amount).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
				tempJshAct.setCanWithdraw(MoneyUtil.subtract(jshAct.getCanWithdraw(), amount).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
				bsSubAccountMapper.updateByPrimaryKeySelective(tempJshAct);
				//是否是超级用户
				boolean isSuperUser = false;
				List<Integer> superUsers = loanRelationshipService.getSuperUserList();
				if(!CollectionUtils.isEmpty(superUsers)){
					for (Integer superUser: superUsers) {
						if(investorUserId.equals(superUser)){
							isSuperUser = true;
							break;
						}
					}
				}
				
				BsProduct product = bsProductMapper.selectByPrimaryKey(productId);
				BsSubAccount authAct = new BsSubAccount();
				Date now = new Date();
				Integer authActId = null;
				Double beforeAuthBalance = 0d;
				Double beforeAuthAvailableBalance = 0d;
				Double beforeAuthFreezeBalance = 0d;
				if(isSuperUser){
					//超级用户需先查询AUTH户是否存在
					BsSubAccount tempSuperAuth = bsSubAccountMapper.selectSingleSubActByUserAndType(investorUserId, Constants.PRODUCT_TYPE_AUTH);
					if(tempSuperAuth != null) {
						BsSubAccount superAuth = bsSubAccountMapper.selectSubAccountByIdForLock(tempSuperAuth.getId());
						authAct.setId(superAuth.getId());
						authAct.setBalance(MoneyUtil.add(superAuth.getBalance(), amount).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
						authAct.setAvailableBalance(MoneyUtil.add(superAuth.getAvailableBalance(), amount).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
						authAct.setCanWithdraw(MoneyUtil.add(superAuth.getCanWithdraw(), amount).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
						authAct.setLastTransDate(now);
						bsSubAccountMapper.updateByPrimaryKeySelective(authAct);
						authActId = superAuth.getId();
						beforeAuthBalance = superAuth.getBalance();
						beforeAuthAvailableBalance = superAuth.getAvailableBalance();
						beforeAuthFreezeBalance = superAuth.getFreezeBalance();
					}
				}
				if(authActId == null){
					authAct.setAccountId(jshAct.getAccountId());
					authAct.setCode(accountHandleService.generateProductAccount(productId, investorUserId));
					authAct.setProductId(productId);
					authAct.setProductType(Constants.PRODUCT_TYPE_AUTH);
					authAct.setProductCode(product.getCode());
					authAct.setProductName(product.getName());
					authAct.setProductRate(product.getBaseRate());
					authAct.setExtraRate(0.0);
					authAct.setOpenBalance(amount);
					authAct.setBalance(amount);
					authAct.setAvailableBalance(amount);
					authAct.setCanWithdraw(amount);
					authAct.setFreezeBalance(0.0);
					authAct.setTransStatus(Constants.USER_SUB_TRANS_STATUS_2);
					authAct.setStatus(Constants.SUBACCOUNT_STATUS_FINANCING);
					authAct.setInterestBeginDate(DateUtil.addDays(now, 1)); //起息日期
					BsSysConfig authDaysConfig = bsSysConfigMapper.selectByPrimaryKey(Constants.DAY_4_WAIT_LOAN);
					if(!isSuperUser){
						authAct.setLastFinishInterestDate(DateUtil.addDays(now,
								1+Integer.valueOf(authDaysConfig.getConfValue()))); //结束日期
					}
					authAct.setAccumulationInerest(0.0);
					authAct.setOpenTime(now);
					bsSubAccountMapper.insertSelective(authAct);
					authActId = authAct.getId();//可返回
					//REG_D户初始生成
					BsSubAccount regAct = new BsSubAccount();
					regAct.setAccountId(authAct.getAccountId());
					regAct.setCode(accountHandleService.generateProductAccount(authAct.getProductId(), investorUserId));
					regAct.setProductId(authAct.getProductId());
					regAct.setProductType(Constants.PRODUCT_TYPE_REG_D);
					regAct.setProductCode(authAct.getProductCode());
					regAct.setProductName(authAct.getProductName());
					regAct.setProductRate(authAct.getProductRate());
					regAct.setExtraRate(0.0);
					regAct.setOpenBalance(0.0);
					regAct.setBalance(0.0);
					regAct.setAvailableBalance(0.0);
					regAct.setCanWithdraw(0.0);
					regAct.setFreezeBalance(0.0);
					regAct.setTransStatus(Constants.USER_SUB_TRANS_STATUS_0);
					regAct.setStatus(Constants.SUBACCOUNT_STATUS_FINANCING);
					regAct.setInterestBeginDate(DateUtil.addDays(now, 1)); //起息日期
					regAct.setLastFinishInterestDate(null); //结束日期
					regAct.setAccumulationInerest(0.0);
					regAct.setOpenTime(now);
					bsSubAccountMapper.insertSelective(regAct);
					Integer regActId = regAct.getId();//可返回
					//AUTH、REG_D关系表生成
					BsSubAccountPair pair = new BsSubAccountPair();
					pair.setAuthAccountId(authActId);
					pair.setRegDAccountId(regActId);
					pair.setTerm(product.getTerm());
					pair.setCreateTime(now);
					pair.setUpdateTime(now);
					bsSubAccountPairMapper.insertSelective(pair);
				}
				//用户账记账
				BsAccountJnl accountJnl = new BsAccountJnl();
				accountJnl.setTransTime(new Date());
				accountJnl.setTransCode(Constants.USER_AUTH_ADD);
				accountJnl.setTransName("站岗户资金授权");
				accountJnl.setTransAmount(amount);
				accountJnl.setSysTime(new Date());
				accountJnl.setChannelTime(null);
				accountJnl.setChannelJnlNo(null);
				accountJnl.setCdFlag1(Constants.CD_FLAG_C);
				accountJnl.setUserId1(investorUserId);
				accountJnl.setAccountId1(jshAct.getAccountId());
				accountJnl.setSubAccountId1(jshAct.getId());
				accountJnl.setSubAccountCode1(jshAct.getCode());
				accountJnl.setBeforeBalance1(jshAct.getBalance());
				accountJnl.setAfterBalance1(tempJshAct.getBalance());
				accountJnl.setBeforeAvialableBalance1(jshAct.getAvailableBalance());
				accountJnl.setAfterAvialableBalance1(tempJshAct.getAvailableBalance());
				accountJnl.setBeforeFreezeBalance1(jshAct.getFreezeBalance());
				accountJnl.setAfterFreezeBalance1(jshAct.getFreezeBalance());
				accountJnl.setCdFlag2(Constants.CD_FLAG_D);
				accountJnl.setUserId2(investorUserId);
				accountJnl.setAccountId2(authAct.getAccountId());
				accountJnl.setSubAccountId2(authAct.getId());
				accountJnl.setSubAccountCode2(authAct.getCode());
				accountJnl.setBeforeBalance2(beforeAuthBalance);
				accountJnl.setAfterBalance2(authAct.getBalance());
				accountJnl.setBeforeAvialableBalance2(beforeAuthAvailableBalance);
				accountJnl.setAfterAvialableBalance2(authAct.getAvailableBalance());
				accountJnl.setBeforeFreezeBalance2(beforeAuthFreezeBalance);
				accountJnl.setAfterFreezeBalance2(authAct.getFreezeBalance());
				accountJnl.setFee(0.0);
				bsAccountJnlMapper.insertSelective(accountJnl);
				
				//S:USER >>> AUTH_ZAN
				BsSysSubAccount sysUserAct = bsSysSubAccountService.findSysSubAccount4Lock(Constants.SYS_ACCOUNT_USER);
				BsSysSubAccount tempSysUserAct = new BsSysSubAccount();
				tempSysUserAct.setId(sysUserAct.getId());
				tempSysUserAct.setBalance(MoneyUtil.subtract(sysUserAct.getBalance(), amount).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
				tempSysUserAct.setAvailableBalance(MoneyUtil.subtract(sysUserAct.getAvailableBalance(), amount).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
				tempSysUserAct.setCanWithdraw(MoneyUtil.subtract(sysUserAct.getCanWithdraw(), amount).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
				bsSysSubAccountMapper.updateByPrimaryKeySelective(tempSysUserAct);
				
				PartnerSysActCode partnerActCode = BaseAccount.partnerActCodeMap.get(baseAccount.getPartner());
				BsSysSubAccount patAuthAct = bsSysSubAccountService.findSysSubAccount4Lock(partnerActCode.getAuthActCode());
				BsSysSubAccount tempPatAuthAct = new BsSysSubAccount();
				tempPatAuthAct.setId(patAuthAct.getId());
				tempPatAuthAct.setBalance(MoneyUtil.add(patAuthAct.getBalance(), amount).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
				tempPatAuthAct.setAvailableBalance(MoneyUtil.add(patAuthAct.getAvailableBalance(), amount).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
				tempPatAuthAct.setCanWithdraw(MoneyUtil.add(patAuthAct.getCanWithdraw(), amount).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
				bsSysSubAccountMapper.updateByPrimaryKeySelective(tempPatAuthAct);
				
				//系统账户记账
				BsSysAccountJnl sysAccountJnl = new BsSysAccountJnl();
				sysAccountJnl.setTransTime(new Date());
				sysAccountJnl.setTransCode(Constants.SYS_AUTH_ADD);
				sysAccountJnl.setTransName("系统站岗户资金授权");
				sysAccountJnl.setTransAmount(amount);
				sysAccountJnl.setSysTime(new Date());
				sysAccountJnl.setChannelTime(null);
				sysAccountJnl.setChannelJnlNo(null);
				sysAccountJnl.setCdFlag1(Constants.CD_FLAG_C);
				sysAccountJnl.setSubAccountCode1(sysUserAct.getCode());
				sysAccountJnl.setBeforeBalance1(sysUserAct.getBalance());
				sysAccountJnl.setAfterBalance1(tempSysUserAct.getBalance());
				sysAccountJnl.setBeforeAvialableBalance1(sysUserAct.getAvailableBalance());
				sysAccountJnl.setAfterAvialableBalance1(tempSysUserAct.getAvailableBalance());
				sysAccountJnl.setBeforeFreezeBalance1(sysUserAct.getFreezeBalance());
				sysAccountJnl.setAfterFreezeBalance1(sysUserAct.getFreezeBalance());
				sysAccountJnl.setCdFlag2(Constants.CD_FLAG_D);
				sysAccountJnl.setSubAccountCode2(patAuthAct.getCode());
				sysAccountJnl.setBeforeBalance2(patAuthAct.getBalance());
				sysAccountJnl.setAfterBalance2(tempPatAuthAct.getBalance());
				sysAccountJnl.setBeforeAvialableBalance2(patAuthAct.getAvailableBalance());
				sysAccountJnl.setAfterAvialableBalance2(tempPatAuthAct.getAvailableBalance());
				sysAccountJnl.setBeforeFreezeBalance2(patAuthAct.getFreezeBalance());
				sysAccountJnl.setAfterFreezeBalance2(patAuthAct.getFreezeBalance());
				sysAccountJnl.setFee(0.0);
				bsSysAccountJnlMapper.insertSelective(sysAccountJnl);

				return authActId;
			/*}
		});*/
	}

	@Override
	@Transactional
	public void chargeAuthActBack(final BaseAccount baseAccount, final Integer investorAuthActId) {
		logger.info("[chargeAuthActBack]入参：" + baseAccount.toString() + ",investorAuthActId=" + investorAuthActId);
		final PartnerEnum partner = baseAccount.getPartner();
		final Integer investorUserId = baseAccount.getInvestorUserId();
		final Double amount = baseAccount.getAmount();
		if(partner == null || investorUserId == null || amount == null || investorAuthActId == null){
			throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR);
		}
		/*transactionTemplate.execute(new TransactionCallbackWithoutResult(){
			@Override
			protected void doInTransactionWithoutResult(TransactionStatus status) {*/
				//F:AUTH >>> JSH
				BsSubAccount tempUserJsh = bsSubAccountMapper.selectSingleSubActByUserAndType(investorUserId, Constants.PRODUCT_TYPE_DEP_JSH);
				BsSubAccount jshAct = bsSubAccountMapper.selectSubAccountByIdForLock(tempUserJsh.getId());
				BsSubAccount authAct = bsSubAccountMapper.selectSubAccountByIdForLock(investorAuthActId);
				BsSubAccount tempAuthAct = new BsSubAccount();
				tempAuthAct.setId(authAct.getId());
				if(MoneyUtil.subtract(authAct.getAvailableBalance(), amount).doubleValue() < 0){
					throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR, "发生金额超限");
				}
				tempAuthAct.setBalance(MoneyUtil.subtract(authAct.getBalance(), amount).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
				tempAuthAct.setAvailableBalance(MoneyUtil.subtract(authAct.getAvailableBalance(), amount).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
				tempAuthAct.setCanWithdraw(MoneyUtil.subtract(authAct.getCanWithdraw(), amount).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
				if(authAct.getOpenBalance().equals(amount)){
					tempAuthAct.setStatus(Constants.SUBACCOUNT_STATUS_SETTLE); //已结算
					
					//修改对应reg_d的状态
					BsSubAccountPairExample pairExample = new BsSubAccountPairExample();
					pairExample.createCriteria().andAuthAccountIdEqualTo(investorAuthActId);
					List<BsSubAccountPair> pairs = bsSubAccountPairMapper.selectByExample(pairExample);
					if(CollectionUtils.isEmpty(pairs)){
						throw new PTMessageException(PTMessageEnum.ACCOUNT_NOT_FOUND);
					}
					BsSubAccount reg_d = bsSubAccountMapper.selectSubAccountByIdForLock(pairs.get(0).getRegDAccountId());
					BsSubAccount tempRegdAct = new BsSubAccount();
					tempRegdAct.setId(reg_d.getId());
					tempRegdAct.setStatus(Constants.SUBACCOUNT_STATUS_SETTLE); //已结算
					tempRegdAct.setLastTransDate(new Date());
					bsSubAccountMapper.updateByPrimaryKeySelective(tempRegdAct);
				}
				tempAuthAct.setLastTransDate(new Date());
				bsSubAccountMapper.updateByPrimaryKeySelective(tempAuthAct);
				
				BsSubAccount tempJshAct = new BsSubAccount();
				tempJshAct.setId(jshAct.getId());
				tempJshAct.setBalance(MoneyUtil.add(jshAct.getBalance(), amount).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
				tempJshAct.setAvailableBalance(MoneyUtil.add(jshAct.getAvailableBalance(), amount).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
				tempJshAct.setCanWithdraw(MoneyUtil.add(jshAct.getCanWithdraw(), amount).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
				tempJshAct.setLastTransDate(new Date());
				bsSubAccountMapper.updateByPrimaryKeySelective(tempJshAct);
				
				//用户账记账
				BsAccountJnl accountJnl = new BsAccountJnl();
				accountJnl.setTransTime(new Date());
				accountJnl.setTransCode(Constants.USER_AUTH_BACK);
				accountJnl.setTransName("站岗户资金回退");
				accountJnl.setTransAmount(amount);
				accountJnl.setSysTime(new Date());
				accountJnl.setChannelTime(null);
				accountJnl.setChannelJnlNo(null);
				accountJnl.setCdFlag1(Constants.CD_FLAG_C);
				accountJnl.setUserId1(investorUserId);
				accountJnl.setAccountId1(authAct.getAccountId());
				accountJnl.setSubAccountId1(authAct.getId());
				accountJnl.setSubAccountCode1(authAct.getCode());
				accountJnl.setBeforeBalance1(authAct.getBalance());
				accountJnl.setAfterBalance1(tempAuthAct.getBalance());
				accountJnl.setBeforeAvialableBalance1(authAct.getAvailableBalance());
				accountJnl.setAfterAvialableBalance1(tempAuthAct.getAvailableBalance());
				accountJnl.setBeforeFreezeBalance1(authAct.getFreezeBalance());
				accountJnl.setAfterFreezeBalance1(authAct.getFreezeBalance());
				accountJnl.setCdFlag2(Constants.CD_FLAG_D);
				accountJnl.setUserId2(investorUserId);
				accountJnl.setAccountId2(jshAct.getAccountId());
				accountJnl.setSubAccountId2(jshAct.getId());
				accountJnl.setSubAccountCode2(jshAct.getCode());
				accountJnl.setBeforeBalance2(jshAct.getBalance());
				accountJnl.setAfterBalance2(tempJshAct.getBalance());
				accountJnl.setBeforeAvialableBalance2(jshAct.getAvailableBalance());
				accountJnl.setAfterAvialableBalance2(tempJshAct.getAvailableBalance());
				accountJnl.setBeforeFreezeBalance2(jshAct.getFreezeBalance());
				accountJnl.setAfterFreezeBalance2(jshAct.getFreezeBalance());
				
				accountJnl.setFee(0.0);
				bsAccountJnlMapper.insertSelective(accountJnl);
				
				//S:AUTH_ZAN >>> USER
				BsSysSubAccount sysUserAct =bsSysSubAccountService.findSysSubAccount4Lock(Constants.SYS_ACCOUNT_BGW_USER);
				PartnerSysActCode partnerActCode = BaseAccount.partnerActCodeMap.get(baseAccount.getPartner());
				BsSysSubAccount patAuthAct = bsSysSubAccountService.findSysSubAccount4Lock(partnerActCode.getAuthActCode());
				BsSysSubAccount tempPatAuthAct = new BsSysSubAccount();
				tempPatAuthAct.setId(patAuthAct.getId());
				tempPatAuthAct.setBalance(MoneyUtil.subtract(patAuthAct.getBalance(), amount).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
				tempPatAuthAct.setAvailableBalance(MoneyUtil.subtract(patAuthAct.getAvailableBalance(), amount).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
				tempPatAuthAct.setCanWithdraw(MoneyUtil.subtract(patAuthAct.getCanWithdraw(), amount).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
				bsSysSubAccountMapper.updateByPrimaryKeySelective(tempPatAuthAct);
				
				BsSysSubAccount tempSysUserAct = new BsSysSubAccount();
				tempSysUserAct.setId(sysUserAct.getId());
				tempSysUserAct.setBalance(MoneyUtil.add(sysUserAct.getBalance(), amount).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
				tempSysUserAct.setAvailableBalance(MoneyUtil.add(sysUserAct.getAvailableBalance(), amount).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
				tempSysUserAct.setCanWithdraw(MoneyUtil.add(sysUserAct.getCanWithdraw(), amount).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
				bsSysSubAccountMapper.updateByPrimaryKeySelective(tempSysUserAct);
				
				//系统账记账
				BsSysAccountJnl sysAccountJnl = new BsSysAccountJnl();
				sysAccountJnl.setTransTime(new Date());
				sysAccountJnl.setTransCode(Constants.SYS_AUTH_BACK);
				sysAccountJnl.setTransName("系统站岗户资金回退");
				sysAccountJnl.setTransAmount(amount);
				sysAccountJnl.setSysTime(new Date());
				sysAccountJnl.setChannelTime(null);
				sysAccountJnl.setChannelJnlNo(null);
				sysAccountJnl.setCdFlag1(Constants.CD_FLAG_C);
				sysAccountJnl.setSubAccountCode1(patAuthAct.getCode());
				sysAccountJnl.setBeforeBalance1(patAuthAct.getBalance());
				sysAccountJnl.setAfterBalance1(tempPatAuthAct.getBalance());
				sysAccountJnl.setBeforeAvialableBalance1(patAuthAct.getAvailableBalance());
				sysAccountJnl.setAfterAvialableBalance1(tempPatAuthAct.getAvailableBalance());
				sysAccountJnl.setBeforeFreezeBalance1(patAuthAct.getFreezeBalance());
				sysAccountJnl.setAfterFreezeBalance1(patAuthAct.getFreezeBalance());
				sysAccountJnl.setCdFlag2(Constants.CD_FLAG_D);
				sysAccountJnl.setSubAccountCode2(sysUserAct.getCode());
				sysAccountJnl.setBeforeBalance2(sysUserAct.getBalance());
				sysAccountJnl.setAfterBalance2(tempSysUserAct.getBalance());
				sysAccountJnl.setBeforeAvialableBalance2(sysUserAct.getAvailableBalance());
				sysAccountJnl.setAfterAvialableBalance2(tempSysUserAct.getAvailableBalance());
				sysAccountJnl.setBeforeFreezeBalance2(sysUserAct.getFreezeBalance());
				sysAccountJnl.setAfterFreezeBalance2(sysUserAct.getFreezeBalance());
				sysAccountJnl.setFee(0.0);
				bsSysAccountJnlMapper.insertSelective(sysAccountJnl);
			/*}
		});*/
	}
	
	@Override
	@Transactional
	public void chargeLoanFreeze(final Double amount, final Integer investorAuthActId) {
		logger.info("[chargeLoanFreeze]入参：amount=" + amount + ",investorAuthActId=" + investorAuthActId);
		if(investorAuthActId == null || amount == null){
			throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR);
		}
		/*transactionTemplate.execute(new TransactionCallbackWithoutResult(){
			@Override
			protected void doInTransactionWithoutResult(TransactionStatus status) {*/
				BsSubAccount authAct = bsSubAccountMapper.selectSubAccountByIdForLock(investorAuthActId);
				if(authAct == null){
					logger.info("[chargeLoanFreeze]未获取到相应账户数据");
					throw new PTMessageException(PTMessageEnum.NO_DATA_FOUND);
				}else{
					BsSubAccount tempAuthAct = new BsSubAccount();
					tempAuthAct.setId(authAct.getId());
					if(MoneyUtil.subtract(authAct.getAvailableBalance(), amount).doubleValue() < 0){
						throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR, "发生金额超限");
					}
					//可用余额，可提现余额减少
					tempAuthAct.setAvailableBalance(MoneyUtil.subtract(authAct.getAvailableBalance(), amount).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
					tempAuthAct.setCanWithdraw(MoneyUtil.subtract(authAct.getCanWithdraw(), amount).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
					//冻结余额增加
					tempAuthAct.setFreezeBalance(MoneyUtil.add(authAct.getFreezeBalance(), amount).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
					bsSubAccountMapper.updateByPrimaryKeySelective(tempAuthAct);
					
					//借款申请授权金额 用户账 记账
					BsAccountJnl accountJnl = new BsAccountJnl();
					accountJnl.setTransTime(new Date());
					accountJnl.setTransCode(Constants.USER_LOAN_FREEZE);
					accountJnl.setTransName("授权金额冻结");
					accountJnl.setTransAmount(amount);
					accountJnl.setSysTime(new Date());
					accountJnl.setChannelTime(null);
					accountJnl.setChannelJnlNo(null);
					accountJnl.setCdFlag1(Constants.CD_FLAG_C);
					accountJnl.setUserId1(null);
					accountJnl.setAccountId1(authAct.getAccountId());
					accountJnl.setSubAccountId1(authAct.getId());
					accountJnl.setSubAccountCode1(authAct.getCode());
					accountJnl.setBeforeBalance1(authAct.getBalance());
					accountJnl.setAfterBalance1(authAct.getBalance());
					accountJnl.setBeforeAvialableBalance1(authAct.getAvailableBalance());
					accountJnl.setAfterAvialableBalance1(tempAuthAct.getAvailableBalance());
					accountJnl.setBeforeFreezeBalance1(authAct.getFreezeBalance());
					accountJnl.setAfterFreezeBalance1(tempAuthAct.getFreezeBalance());
					accountJnl.setFee(0.0);
					bsAccountJnlMapper.insertSelective(accountJnl);
				}
			/*}
		});*/
		
	}

	@Override
	@Transactional
	public void chargeLoanUnFreeze(final Double amount, final Integer investorAuthActId) {
		logger.info("[chargeLoanUnFreeze]入参：amount=" + amount + ",investorAuthActId=" + investorAuthActId);
		if(investorAuthActId == null || amount == null){
			throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR);
		}
		/*transactionTemplate.execute(new TransactionCallbackWithoutResult(){
			@Override
			protected void doInTransactionWithoutResult(TransactionStatus status) {*/
				BsSubAccount authAct = bsSubAccountMapper.selectSubAccountByIdForLock(investorAuthActId);
				if(authAct == null){
					logger.info("[chargeLoanUnFreeze]未获取到相应账户数据");
					throw new PTMessageException(PTMessageEnum.NO_DATA_FOUND);
				}else{
					BsSubAccount tempAuthAct = new BsSubAccount();
					tempAuthAct.setId(authAct.getId());
					if(MoneyUtil.subtract(authAct.getFreezeBalance(), amount).doubleValue() < 0){
						throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR, "发生金额超限");
					}
					//可用余额，可提现余额 增加
					tempAuthAct.setAvailableBalance(MoneyUtil.add(authAct.getAvailableBalance(), amount).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
					tempAuthAct.setCanWithdraw(MoneyUtil.add(authAct.getCanWithdraw(), amount).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
					//冻结余额 减少
					tempAuthAct.setFreezeBalance(MoneyUtil.subtract(authAct.getFreezeBalance(), amount).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
					bsSubAccountMapper.updateByPrimaryKeySelective(tempAuthAct);
					
					//借款申请授权金额 用户账 记账
					BsAccountJnl accountJnl = new BsAccountJnl();
					accountJnl.setTransTime(new Date());
					accountJnl.setTransCode(Constants.USER_LOAN_UNFREEZE);
					accountJnl.setTransName("授权金额解冻");
					accountJnl.setTransAmount(amount);
					accountJnl.setSysTime(new Date());
					accountJnl.setChannelTime(null);
					accountJnl.setChannelJnlNo(null);
					accountJnl.setCdFlag2(Constants.CD_FLAG_D);
					accountJnl.setUserId2(null);
					accountJnl.setAccountId2(authAct.getAccountId());
					accountJnl.setSubAccountId2(authAct.getId());
					accountJnl.setSubAccountCode2(authAct.getCode());
					accountJnl.setBeforeBalance2(authAct.getBalance());
					accountJnl.setAfterBalance2(authAct.getBalance());
					accountJnl.setBeforeAvialableBalance2(authAct.getAvailableBalance());
					accountJnl.setAfterAvialableBalance2(tempAuthAct.getAvailableBalance());
					accountJnl.setBeforeFreezeBalance2(authAct.getFreezeBalance());
					accountJnl.setAfterFreezeBalance2(tempAuthAct.getFreezeBalance());
					accountJnl.setFee(0.0);
					bsAccountJnlMapper.insertSelective(accountJnl);
				}
			/*}
		});*/
		
		
	}

	@Override
	@Transactional
	public Integer chargeLoanActOpen(BaseAccount baseAccount) {
		logger.info("[chargeLoanActOpen]入参：" + baseAccount);
		final PartnerEnum partner = baseAccount.getPartner();
		final Integer borrowerUserId = baseAccount.getBorrowerUserId();
		final Double amount = baseAccount.getAmount();
		if(partner == null || borrowerUserId == null || amount == null){
			throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR);
		}
		/*return transactionTemplate.execute(new TransactionCallback<Integer>() {
			@Override
			public Integer doInTransaction(TransactionStatus status) {*/
				// B:LOAN +
				LnSubAccount loanAct = new LnSubAccount();
				LnAccountExample lnActExample = new LnAccountExample();
				lnActExample.createCriteria().andLnUserIdEqualTo(borrowerUserId);
				List<LnAccount> lnActs = lnAccountMapper.selectByExample(lnActExample);
				loanAct.setLnUserId(borrowerUserId);
				loanAct.setAccountId(lnActs.get(0).getId());
				loanAct.setAccountType(Constants.LOAN_ACT_TYPE_INSTALMENT);
				loanAct.setOpenBalance(amount);
				loanAct.setBalance(amount);
				loanAct.setAvailableBalance(amount);
				loanAct.setCanWithdraw(amount);
				loanAct.setFreezeBalance(0.0);
				loanAct.setStatus(Constants.LOAN_SUBACCOUNT_STATUS_OPEN);
				loanAct.setAccumulationInerest(0.0);
				loanAct.setOpenTime(new Date());
				loanAct.setCreateTime(new Date());
				loanAct.setUpdateTime(new Date());
				lnSubAccountMapper.insertSelective(loanAct);
				Integer loanActId = loanAct.getId();//可返回

				LnAccountJnl accountJnl = new LnAccountJnl();
				accountJnl.setTransTime(new Date());
				accountJnl.setTransCode(Constants.LN_USER_LOAN_OPEN);
				accountJnl.setTransName("借款子账户开户");
				accountJnl.setTransAmount(amount);
				accountJnl.setSysTime(new Date());
				accountJnl.setChannelTime(null);
				accountJnl.setChannelJnlNo(null);
				accountJnl.setCdFlag2(Constants.CD_FLAG_D);
				accountJnl.setUserId2(borrowerUserId);
				accountJnl.setSubAccountId2(loanActId);
				accountJnl.setBeforeBalance2(0d);
				accountJnl.setAfterBalance2(loanAct.getBalance());
				accountJnl.setBeforeAvialableBalance2(0d);
				accountJnl.setAfterAvialableBalance2(loanAct.getAvailableBalance());
				accountJnl.setBeforeFreezeBalance2(0d);
				accountJnl.setAfterFreezeBalance2(loanAct.getFreezeBalance());
				accountJnl.setFee(0.0);
				lnAccountJnlMapper.insertSelective(accountJnl);

				return loanActId;
		/*	}
		});*/
	}

	@Override
	@Transactional
	public void chargeLoan(final BaseAccount baseAccount, final List<InvestorRegInfo> investorRegs,
						   final Integer loanSubActId, final Double fee) {
		// F:AUTH >>> REG_D
		// S:AUTH_ZAN >>> REG_ZAN
		// B:LOAN +
		// S:FEE +
		logger.info("[chargeLoan]入参：" + baseAccount + ",investorRegs="
				+ investorRegs + ",loanSubActId=" + loanSubActId);
		final PartnerEnum partner = baseAccount.getPartner();
		final Integer borrowerUserId = baseAccount.getBorrowerUserId();
		final Integer investorUserId = baseAccount.getInvestorUserId();
		final Double amount = baseAccount.getAmount();
		if(partner == null || borrowerUserId == null || amount == null ||
				investorRegs == null || investorRegs.size() == 0 || loanSubActId == null || fee == null){
			throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR);
		}
		/*transactionTemplate.execute(new TransactionCallbackWithoutResult(){
			@Override
			protected void doInTransactionWithoutResult(TransactionStatus status) {*/
				Integer term = 0;
				for (InvestorRegInfo investorReg : investorRegs) {
					Integer investorRegActId = investorReg.getInvestorRegActId();
					Double regAmount = investorReg.getRegAmount();
					// F:AUTH >>> REG_D
					//查询对应AUTH编号（目前auth和reg_d为一对一关系）
					BsSubAccountPairExample pairExample = new BsSubAccountPairExample();
					pairExample.createCriteria().andRegDAccountIdEqualTo(investorRegActId);
					List<BsSubAccountPair> pairs = bsSubAccountPairMapper.selectByExample(pairExample);
					if(CollectionUtils.isEmpty(pairs)){
						throw new PTMessageException(PTMessageEnum.ACCOUNT_NOT_FOUND);
					}
					//AUTH -
					BsSubAccountPair pair = pairs.get(0);
					term = pair.getTerm();
					BsSubAccount authAct = bsSubAccountMapper.selectSubAccountByIdForLock(pair.getAuthAccountId());
					BsSubAccount tempAuthAct = new BsSubAccount();
					tempAuthAct.setId(authAct.getId());
					if(MoneyUtil.subtract(authAct.getFreezeBalance(), regAmount).doubleValue() < 0){
						throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR, "发生金额超限");
					}
					tempAuthAct.setBalance(MoneyUtil.subtract(authAct.getBalance(), regAmount).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
					tempAuthAct.setFreezeBalance(MoneyUtil.subtract(authAct.getFreezeBalance(), regAmount).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
					bsSubAccountMapper.updateByPrimaryKeySelective(tempAuthAct);
					//REG_D +
					BsSubAccount regAct = bsSubAccountMapper.selectSubAccountByIdForLock(pair.getRegDAccountId());
					BsSubAccount tempRegAct = new BsSubAccount();
					tempRegAct.setId(regAct.getId());
					tempRegAct.setBalance(MoneyUtil.add(regAct.getBalance(), regAmount).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
					tempRegAct.setFreezeBalance(MoneyUtil.add(regAct.getFreezeBalance(), regAmount).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
					Integer dayNum = pair.getTerm() != null ? getTerm4Day(pair.getTerm()) : 0;
					tempRegAct.setLastFinishInterestDate(DateUtil.addDays(new Date(), dayNum)); //结束日期
					bsSubAccountMapper.updateByPrimaryKeySelective(tempRegAct);
					Integer regActId = regAct.getId();

					//用户账记账
					BsAccountJnl accountJnl = new BsAccountJnl();
					accountJnl.setTransTime(new Date());
					accountJnl.setTransCode(Constants.USER_AUTH_2_REG);
					accountJnl.setTransName("站岗户资金转产品户");
					accountJnl.setTransAmount(regAmount);
					accountJnl.setSysTime(new Date());
					accountJnl.setChannelTime(null);
					accountJnl.setChannelJnlNo(null);
					accountJnl.setCdFlag1(Constants.CD_FLAG_C);
					accountJnl.setUserId1(investorUserId);
					accountJnl.setAccountId1(authAct.getAccountId());
					accountJnl.setSubAccountId1(authAct.getId());
					accountJnl.setSubAccountCode1(authAct.getCode());
					accountJnl.setBeforeBalance1(authAct.getBalance());
					accountJnl.setAfterBalance1(tempAuthAct.getBalance());
					accountJnl.setBeforeAvialableBalance1(authAct.getAvailableBalance());
					accountJnl.setAfterAvialableBalance1(authAct.getAvailableBalance());
					accountJnl.setBeforeFreezeBalance1(authAct.getFreezeBalance());
					accountJnl.setAfterFreezeBalance1(tempAuthAct.getFreezeBalance());
					accountJnl.setCdFlag2(Constants.CD_FLAG_D);
					accountJnl.setUserId2(investorUserId);
					accountJnl.setAccountId2(regAct.getAccountId());
					accountJnl.setSubAccountId2(regAct.getId());
					accountJnl.setSubAccountCode2(regAct.getCode());
					accountJnl.setBeforeBalance2(regAct.getBalance());
					accountJnl.setAfterBalance2(tempRegAct.getBalance());
					accountJnl.setBeforeAvialableBalance2(regAct.getAvailableBalance());
					accountJnl.setAfterAvialableBalance2(regAct.getAvailableBalance());
					accountJnl.setBeforeFreezeBalance2(regAct.getFreezeBalance());
					accountJnl.setAfterFreezeBalance2(tempRegAct.getFreezeBalance());
					accountJnl.setFee(0.0);
					bsAccountJnlMapper.insertSelective(accountJnl);

					//关系表金额变动记账
					BsSubAccountPairJnl pairJnl = new BsSubAccountPairJnl();
					pairJnl.setAmount(regAmount);
					pairJnl.setPairId(pair.getId());
					pairJnl.setTransferTime(new Date());
					pairJnl.setCreateTime(new Date());
					pairJnl.setUpdateTime(new Date());
					bsSubAccountPairJnlMapper.insertSelective(pairJnl);
				}

				//S:AUTH_ZAN >>> REG_ZAN
				LnSubAccount loanAct = lnSubAccountMapper.selectByPrimaryKey4Lock(loanSubActId);
				PartnerSysActCode partnerActCode = BaseAccount.partnerActCodeMap.get(baseAccount.getPartner());
				BsSysSubAccount patAuthAct = bsSysSubAccountService.findSysSubAccount4Lock(partnerActCode.getAuthActCode());
				BsSysSubAccount tempPatAuthAct = new BsSysSubAccount();
				tempPatAuthAct.setId(patAuthAct.getId());
				tempPatAuthAct.setBalance(MoneyUtil.subtract(patAuthAct.getBalance(), amount).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
				tempPatAuthAct.setAvailableBalance(MoneyUtil.subtract(patAuthAct.getAvailableBalance(), amount).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
				tempPatAuthAct.setCanWithdraw(MoneyUtil.subtract(patAuthAct.getCanWithdraw(), amount).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
				bsSysSubAccountMapper.updateByPrimaryKeySelective(tempPatAuthAct);
				
				BsSysSubAccount patRegAct = bsSysSubAccountService.findSysSubAccount4Lock(partnerActCode.getRegActCode());
				BsSysSubAccount tempPatRegAct = new BsSysSubAccount();
				tempPatRegAct.setId(patRegAct.getId());
				tempPatRegAct.setBalance(MoneyUtil.add(patRegAct.getBalance(), amount).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
				tempPatRegAct.setAvailableBalance(MoneyUtil.add(patRegAct.getAvailableBalance(), amount).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
				tempPatRegAct.setCanWithdraw(MoneyUtil.add(patRegAct.getCanWithdraw(), amount).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
				bsSysSubAccountMapper.updateByPrimaryKeySelective(tempPatRegAct);
				
				//系统账记账
				BsSysAccountJnl sysAccountJnl = new BsSysAccountJnl();
				sysAccountJnl.setTransTime(new Date());
				sysAccountJnl.setTransCode(Constants.SYS_AUTH_2_REG);
				sysAccountJnl.setTransName("合作方站岗户资金转产品户");
				sysAccountJnl.setTransAmount(amount);
				sysAccountJnl.setSysTime(new Date());
				sysAccountJnl.setChannelTime(null);
				sysAccountJnl.setChannelJnlNo(null);
				sysAccountJnl.setCdFlag1(Constants.CD_FLAG_C);
				sysAccountJnl.setSubAccountCode1(patAuthAct.getCode());
				sysAccountJnl.setBeforeBalance1(patAuthAct.getBalance());
				sysAccountJnl.setAfterBalance1(tempPatAuthAct.getBalance());
				sysAccountJnl.setBeforeAvialableBalance1(patAuthAct.getAvailableBalance());
				sysAccountJnl.setAfterAvialableBalance1(tempPatAuthAct.getAvailableBalance());
				sysAccountJnl.setBeforeFreezeBalance1(patAuthAct.getFreezeBalance());
				sysAccountJnl.setAfterFreezeBalance1(patAuthAct.getFreezeBalance());
				sysAccountJnl.setCdFlag2(Constants.CD_FLAG_D);
				sysAccountJnl.setSubAccountCode2(patRegAct.getCode());
				sysAccountJnl.setBeforeBalance2(patRegAct.getBalance());
				sysAccountJnl.setAfterBalance2(tempPatRegAct.getBalance());
				sysAccountJnl.setBeforeAvialableBalance2(patRegAct.getAvailableBalance());
				sysAccountJnl.setAfterAvialableBalance2(tempPatRegAct.getAvailableBalance());
				sysAccountJnl.setBeforeFreezeBalance2(patRegAct.getFreezeBalance());
				sysAccountJnl.setAfterFreezeBalance2(patRegAct.getFreezeBalance());
				sysAccountJnl.setFee(0.0);
				bsSysAccountJnlMapper.insertSelective(sysAccountJnl);
				
				// B:LOAN +
				LnSubAccount tempLoanAct = new LnSubAccount();
				tempLoanAct.setId(loanSubActId);
				tempLoanAct.setBalance(MoneyUtil.add(loanAct.getBalance(), amount).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
				tempLoanAct.setAvailableBalance(MoneyUtil.add(loanAct.getAvailableBalance(), amount).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
				tempLoanAct.setCanWithdraw(MoneyUtil.add(loanAct.getCanWithdraw(), amount).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
				tempLoanAct.setStatus(Constants.LOAN_SUBACCOUNT_STATUS_USING);
				tempLoanAct.setInterestBeginDate(DateUtil.addDays(new Date(), 1)); //起息日期
				tempLoanAct.setLastFinishInterestDate(DateUtil.addDays(new Date(), 1+getTerm4Day(term))); //结束日期
				tempLoanAct.setUpdateTime(new Date());
				lnSubAccountMapper.updateByPrimaryKeySelective(tempLoanAct);
				//借款人账户记账
				LnAccountJnl lnAccountJnl = new LnAccountJnl();
				lnAccountJnl.setTransTime(new Date());
				lnAccountJnl.setTransCode(Constants.LN_USER_LOAN);
				lnAccountJnl.setTransName("借款");
				lnAccountJnl.setTransAmount(amount);
				lnAccountJnl.setSysTime(new Date());
				lnAccountJnl.setChannelTime(null);
				lnAccountJnl.setChannelJnlNo(null);
				lnAccountJnl.setCdFlag2(Constants.CD_FLAG_D);
				lnAccountJnl.setUserId2(borrowerUserId);
				lnAccountJnl.setSubAccountId2(loanSubActId);
				lnAccountJnl.setBeforeBalance2(loanAct.getBalance());
				lnAccountJnl.setAfterBalance2(tempLoanAct.getBalance());
				lnAccountJnl.setBeforeAvialableBalance2(loanAct.getAvailableBalance());
				lnAccountJnl.setAfterAvialableBalance2(tempLoanAct.getAvailableBalance());
				lnAccountJnl.setBeforeFreezeBalance2(loanAct.getFreezeBalance());
				lnAccountJnl.setAfterFreezeBalance2(loanAct.getFreezeBalance());
				lnAccountJnl.setFee(0.0);
				lnAccountJnlMapper.insertSelective(lnAccountJnl);

				//融资余额户-
				LnSubAccountExample example = new LnSubAccountExample();
				example.createCriteria().andAccountTypeEqualTo(Constants.LOAN_ACT_TYPE_DEP_JSH).andLnUserIdEqualTo(baseAccount.getBorrowerUserId());
				List<LnSubAccount> lnSubAccounts = lnSubAccountMapper.selectByExample(example);
				if(org.apache.commons.collections.CollectionUtils.isEmpty(lnSubAccounts)){
					throw new PTMessageException(PTMessageEnum.SUB_ACCOUNT_NO_EXIT,",云贷融资人余额子账户不存在");
				}
				LnSubAccount lnSubAct = lnSubAccounts.get(0);
				LnSubAccount loanActLock= lnSubAccountMapper.selectByPrimaryKey4Lock(lnSubAct.getId());
				LnSubAccount tempLnAct = new LnSubAccount();
				tempLnAct.setId(loanActLock.getId());
				if(MoneyUtil.subtract(loanActLock.getBalance(), amount).doubleValue() < 0){
					logger.info("[融资余额户-]发生金额超限");
					throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR, ",发生金额超限");
				}
				tempLnAct.setBalance(MoneyUtil.subtract(loanActLock.getBalance(), amount).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
				tempLnAct.setAvailableBalance(MoneyUtil.subtract(loanActLock.getAvailableBalance(), amount).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
				tempLnAct.setCanWithdraw(MoneyUtil.subtract(loanActLock.getCanWithdraw(), amount).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
				tempLnAct.setUpdateTime(new Date());
				lnSubAccountMapper.updateByPrimaryKeySelective(tempLnAct);
				//借款人账户记账
				LnAccountJnl lnAccountJnlZan = new LnAccountJnl();
				lnAccountJnlZan.setTransTime(new Date());
				lnAccountJnlZan.setTransCode(Constants.LN_DEP_JSH_SUB);
				lnAccountJnlZan.setTransName("借款出账");
				lnAccountJnlZan.setTransAmount(amount);
				lnAccountJnlZan.setSysTime(new Date());
				lnAccountJnlZan.setCdFlag1(Constants.CD_FLAG_C);
				lnAccountJnlZan.setUserId1(loanActLock.getLnUserId());
				lnAccountJnlZan.setSubAccountId1(loanActLock.getId());
				lnAccountJnlZan.setBeforeBalance1(loanActLock.getBalance());
				lnAccountJnlZan.setAfterBalance1(tempLnAct.getBalance());
				lnAccountJnlZan.setBeforeAvialableBalance1(loanActLock.getAvailableBalance());
				lnAccountJnlZan.setAfterAvialableBalance1(tempLnAct.getAvailableBalance());
				lnAccountJnlZan.setBeforeFreezeBalance1(loanActLock.getFreezeBalance());
				lnAccountJnlZan.setAfterFreezeBalance1(loanActLock.getFreezeBalance());
				lnAccountJnlZan.setFee(0.0);
				lnAccountJnlMapper.insertSelective(lnAccountJnlZan);

				//S:FEE +
				if(fee > 0){
					BsSysSubAccount bgwFeeAct = bsSysSubAccountService.findSysSubAccount4Lock(Constants.SYS_ACCOUNT_FEE);
					BsSysSubAccount tempBgwFeeAct = new BsSysSubAccount();
					tempBgwFeeAct.setId(bgwFeeAct.getId());
					tempBgwFeeAct.setBalance(MoneyUtil.add(bgwFeeAct.getBalance(), fee).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
					tempBgwFeeAct.setAvailableBalance(MoneyUtil.add(bgwFeeAct.getAvailableBalance(), fee).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
					tempBgwFeeAct.setCanWithdraw(MoneyUtil.add(bgwFeeAct.getCanWithdraw(), fee).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
					bsSysSubAccountMapper.updateByPrimaryKeySelective(tempBgwFeeAct);

					//系统手续费账户收入 记账
					BsSysAccountJnl sysActJnl4BgwFee = new BsSysAccountJnl();
					sysActJnl4BgwFee.setTransTime(new Date());
					sysActJnl4BgwFee.setTransCode(Constants.SYS_FEE_INCOME);
					sysActJnl4BgwFee.setTransName("系统手续费账户收入");
					sysActJnl4BgwFee.setTransAmount(fee);
					sysActJnl4BgwFee.setSysTime(new Date());
					sysActJnl4BgwFee.setChannelTime(null);
					sysActJnl4BgwFee.setChannelJnlNo(null);
					sysActJnl4BgwFee.setCdFlag2(Constants.CD_FLAG_D);
					sysActJnl4BgwFee.setSubAccountCode2(bgwFeeAct.getCode());
					sysActJnl4BgwFee.setBeforeBalance2(bgwFeeAct.getBalance());
					sysActJnl4BgwFee.setAfterBalance2(tempBgwFeeAct.getBalance());
					sysActJnl4BgwFee.setBeforeAvialableBalance2(bgwFeeAct.getAvailableBalance());
					sysActJnl4BgwFee.setAfterAvialableBalance2(tempBgwFeeAct.getAvailableBalance());
					sysActJnl4BgwFee.setBeforeFreezeBalance2(bgwFeeAct.getFreezeBalance());
					sysActJnl4BgwFee.setAfterFreezeBalance2(bgwFeeAct.getFreezeBalance());
					sysActJnl4BgwFee.setFee(0.0);
					bsSysAccountJnlMapper.insertSelective(sysActJnl4BgwFee);
				}

			/*}
		});*/
	}

	@Override
	@Transactional
	public void chargeSuperTransfer(final SuperTransferInfo sTransferInfo) {
		logger.info("[chargeSuperTransfer]入参：amount=" + sTransferInfo.getAmount() + ",sInvestorRegActId=" + sTransferInfo.getsInvestorRegActId()
				+ ",investorAuthActId=" + sTransferInfo.getInvestorAuthActId()+ ",superUserId="+ sTransferInfo.getSuperUserId()
				+ ",normalUserId=" + sTransferInfo.getNormalUserId() + ",matchAmount=" + sTransferInfo.getMatchAmount());
		//必传项校验
		if(sTransferInfo.getAmount() == null || sTransferInfo.getsInvestorRegActId() == null || sTransferInfo.getInvestorAuthActId() == null
				|| sTransferInfo.getSuperUserId() == null || sTransferInfo.getNormalUserId() == null || sTransferInfo.getMatchAmount() == null){
			throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR);
		}
		/*transactionTemplate.execute(new TransactionCallbackWithoutResult(){
			@Override
			protected void doInTransactionWithoutResult(TransactionStatus status) {*/

				//查询对应AUTH编号（目前auth和reg_d为一对一关系）
				BsSubAccountPairExample pairExampleS = new BsSubAccountPairExample();
				pairExampleS.createCriteria().andRegDAccountIdEqualTo(sTransferInfo.getsInvestorRegActId());
				List<BsSubAccountPair> pairsSuper = bsSubAccountPairMapper.selectByExample(pairExampleS);
				if(CollectionUtils.isEmpty(pairsSuper)){
					throw new PTMessageException(PTMessageEnum.ACCOUNT_NOT_FOUND);
				}
				BsSubAccountPair pairSuper = pairsSuper.get(0);
				//超级理财人JSH增加
				BsSubAccount tempSuperJSH = bsSubAccountMapper.selectSingleSubActByUserAndType(sTransferInfo.getSuperUserId(), Constants.PRODUCT_TYPE_DEP_JSH);
				BsSubAccount superJSH = bsSubAccountMapper.selectSubAccountByIdForLock(tempSuperJSH.getId());
				//超级AUTH
				BsSubAccount superAUTH = bsSubAccountMapper.selectSubAccountByIdForLock(pairSuper.getAuthAccountId());
				//普通AUTH
				BsSubAccount normalAUTH = bsSubAccountMapper.selectSubAccountByIdForLock(sTransferInfo.getInvestorAuthActId());
				//超级REG_D
				BsSubAccount superREG_D = bsSubAccountMapper.selectSubAccountByIdForLock(sTransferInfo.getsInvestorRegActId());

				BsSubAccountPairExample pairExample = new BsSubAccountPairExample();
				pairExample.createCriteria().andAuthAccountIdEqualTo(sTransferInfo.getInvestorAuthActId());
				List<BsSubAccountPair> pairs = bsSubAccountPairMapper.selectByExample(pairExample);
				if(CollectionUtils.isEmpty(pairs)){
					throw new PTMessageException(PTMessageEnum.ACCOUNT_NOT_FOUND);
				}
				
				BsSubAccountPair pair = pairs.get(0);
				//普通REG_D
				BsSubAccount  normalREG_D= bsSubAccountMapper.selectSubAccountByIdForLock(pair.getRegDAccountId());
			
				//判断超级理财人REG_D户金额是否符合,普通理财人AUTH户金额是否符合
				if(MoneyUtil.subtract(superREG_D.getBalance(), sTransferInfo.getAmount()).doubleValue() < 0 ||
						MoneyUtil.subtract(normalAUTH.getBalance(), sTransferInfo.getMatchAmount()).doubleValue() < 0){
					logger.info("superREG_D:"+superREG_D.getBalance()+"sTransferInfo.getAmount():"+sTransferInfo.getAmount()
							+"normalAUTH.getBalance()"+normalAUTH.getBalance()+"sTransferInfo.getMatchAmount()"+sTransferInfo.getMatchAmount());
					throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR, "发生金额超限");
				}
				
				//超级理财人 REG_D 减少
				BsSubAccount sREG_D = new BsSubAccount();
				sREG_D.setId(superREG_D.getId());
				sREG_D.setBalance(MoneyUtil.subtract(superREG_D.getBalance(), sTransferInfo.getAmount()).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
				sREG_D.setFreezeBalance(MoneyUtil.subtract(superREG_D.getFreezeBalance(), sTransferInfo.getAmount()).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
				bsSubAccountMapper.updateByPrimaryKeySelective(sREG_D);
				
				//超级理财人 AUTH 增加
				BsSubAccount sAUTH = new BsSubAccount();
				sAUTH.setId(superAUTH.getId());
				sAUTH.setBalance(MoneyUtil.add(superAUTH.getBalance(), sTransferInfo.getAmount()).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
				sAUTH.setAvailableBalance(MoneyUtil.add(superAUTH.getAvailableBalance(), sTransferInfo.getAmount()).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
				sAUTH.setCanWithdraw(MoneyUtil.add(superAUTH.getCanWithdraw(), sTransferInfo.getAmount()).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
				bsSubAccountMapper.updateByPrimaryKeySelective(sAUTH);
				
				//超级理财人用户账记账
				BsAccountJnl accountJnl = new BsAccountJnl();
				accountJnl.setTransTime(new Date());
				accountJnl.setTransCode(Constants.USER_REG_2_AUTH);
				accountJnl.setTransName("产品户资金转站岗户");
				accountJnl.setTransAmount(sTransferInfo.getAmount());
				accountJnl.setSysTime(new Date());
				accountJnl.setChannelTime(null);
				accountJnl.setChannelJnlNo(null);
				accountJnl.setCdFlag1(Constants.CD_FLAG_C);
				accountJnl.setUserId1(sTransferInfo.getSuperUserId());
				accountJnl.setAccountId1(superREG_D.getAccountId());
				accountJnl.setSubAccountId1(superREG_D.getId());
				accountJnl.setSubAccountCode1(superREG_D.getCode());
				accountJnl.setBeforeBalance1(superREG_D.getBalance());
				accountJnl.setAfterBalance1(sREG_D.getBalance());
				accountJnl.setBeforeAvialableBalance1(superREG_D.getAvailableBalance());
				accountJnl.setAfterAvialableBalance1(superREG_D.getAvailableBalance());
				accountJnl.setBeforeFreezeBalance1(superREG_D.getFreezeBalance());
				accountJnl.setAfterFreezeBalance1(sREG_D.getFreezeBalance());
				accountJnl.setCdFlag2(Constants.CD_FLAG_D);
				accountJnl.setUserId2(sTransferInfo.getSuperUserId());
				accountJnl.setAccountId2(superAUTH.getAccountId());
				accountJnl.setSubAccountId2(superAUTH.getId());
				accountJnl.setSubAccountCode2(superAUTH.getCode());
				accountJnl.setBeforeBalance2(superAUTH.getBalance());
				accountJnl.setAfterBalance2(sAUTH.getBalance());
				accountJnl.setBeforeAvialableBalance2(superAUTH.getAvailableBalance());
				accountJnl.setAfterAvialableBalance2(sAUTH.getAvailableBalance());
				accountJnl.setBeforeFreezeBalance2(superAUTH.getFreezeBalance());
				accountJnl.setAfterFreezeBalance2(superAUTH.getFreezeBalance());
				accountJnl.setFee(0.0);
				bsAccountJnlMapper.insertSelective(accountJnl);

				//普通理财人AUTH减少，balance减少，冻结减少
				BsSubAccount nAUTH = new BsSubAccount();
				nAUTH.setId(normalAUTH.getId());
				nAUTH.setBalance(MoneyUtil.subtract(normalAUTH.getBalance(), sTransferInfo.getAmount()).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
				nAUTH.setFreezeBalance(MoneyUtil.subtract(normalAUTH.getFreezeBalance(), sTransferInfo.getAmount()).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
				bsSubAccountMapper.updateByPrimaryKeySelective(nAUTH);
				
				//普通理财人REG_D增加
				BsSubAccount nREG_D = new BsSubAccount();
				nREG_D.setId(normalREG_D.getId());
				nREG_D.setBalance(MoneyUtil.add(normalREG_D.getBalance(), sTransferInfo.getAmount()).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
				nREG_D.setFreezeBalance(MoneyUtil.add(normalREG_D.getFreezeBalance(), sTransferInfo.getAmount()).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
				//结束日期
				nREG_D.setLastFinishInterestDate(DateUtil.addDays(new Date(), 1+getTerm4Day(pair.getTerm())));
				bsSubAccountMapper.updateByPrimaryKeySelective(nREG_D);
				
				//用户账记账
				BsAccountJnl accountJnlNormal = new BsAccountJnl();
				accountJnlNormal.setTransTime(new Date());
				accountJnlNormal.setTransCode(Constants.USER_AUTH_2_REG);
				accountJnlNormal.setTransName("站岗户资金转产品户");
				accountJnlNormal.setTransAmount(sTransferInfo.getAmount());
				accountJnlNormal.setSysTime(new Date());
				accountJnlNormal.setChannelTime(null);
				accountJnlNormal.setChannelJnlNo(null);
				accountJnlNormal.setCdFlag1(Constants.CD_FLAG_C);
				accountJnlNormal.setUserId1(sTransferInfo.getNormalUserId());
				accountJnlNormal.setAccountId1(normalAUTH.getAccountId());
				accountJnlNormal.setSubAccountId1(normalAUTH.getId());
				accountJnlNormal.setSubAccountCode1(normalAUTH.getCode());
				accountJnlNormal.setBeforeBalance1(normalAUTH.getBalance());
				accountJnlNormal.setAfterBalance1(nAUTH.getBalance());
				accountJnlNormal.setBeforeAvialableBalance1(normalAUTH.getAvailableBalance());
				accountJnlNormal.setAfterAvialableBalance1(normalAUTH.getAvailableBalance());
				accountJnlNormal.setBeforeFreezeBalance1(normalAUTH.getFreezeBalance());
				accountJnlNormal.setAfterFreezeBalance1(nAUTH.getFreezeBalance());
				accountJnlNormal.setCdFlag2(Constants.CD_FLAG_D);
				accountJnlNormal.setUserId2(sTransferInfo.getNormalUserId());
				accountJnlNormal.setAccountId2(normalREG_D.getAccountId());
				accountJnlNormal.setSubAccountId2(normalREG_D.getId());
				accountJnlNormal.setSubAccountCode2(normalREG_D.getCode());
				accountJnlNormal.setBeforeBalance2(normalREG_D.getBalance());
				accountJnlNormal.setAfterBalance2(nREG_D.getBalance());
				accountJnlNormal.setBeforeAvialableBalance2(normalREG_D.getAvailableBalance());
				accountJnlNormal.setAfterAvialableBalance2(normalREG_D.getAvailableBalance());
				accountJnlNormal.setBeforeFreezeBalance2(normalREG_D.getFreezeBalance());
				accountJnlNormal.setAfterFreezeBalance2(nREG_D.getFreezeBalance());
				accountJnlNormal.setFee(0.0);
				bsAccountJnlMapper.insertSelective(accountJnlNormal);

				//普通理财人AUTH减少垫付利息部分
				Double changeAmount = MoneyUtil.subtract(sTransferInfo.getMatchAmount(), sTransferInfo.getAmount()).doubleValue();
				if(changeAmount >0 ){
					
					BsSubAccount nAUTH4Interest = new BsSubAccount();
					nAUTH4Interest.setId(normalAUTH.getId());
					nAUTH4Interest.setBalance(MoneyUtil.subtract(nAUTH.getBalance(), changeAmount).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
					nAUTH4Interest.setAvailableBalance(MoneyUtil.subtract(nAUTH.getAvailableBalance(), changeAmount).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
					nAUTH4Interest.setCanWithdraw(MoneyUtil.subtract(nAUTH.getCanWithdraw(), changeAmount).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
					bsSubAccountMapper.updateByPrimaryKeySelective(nAUTH4Interest);
	
					BsSubAccount tempJshAct = new BsSubAccount();
					tempJshAct.setId(superJSH.getId());
					tempJshAct.setBalance(MoneyUtil.add(superJSH.getBalance(), changeAmount).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
					tempJshAct.setAvailableBalance(MoneyUtil.add(superJSH.getAvailableBalance(), changeAmount).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
					tempJshAct.setCanWithdraw(MoneyUtil.add(superJSH.getCanWithdraw(), changeAmount).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
					bsSubAccountMapper.updateByPrimaryKeySelective(tempJshAct);
					
					//用户账记账
					BsAccountJnl accountJnlJSH = new BsAccountJnl();
					accountJnlJSH.setTransTime(new Date());
					accountJnlJSH.setTransCode(Constants.USER_JSH_ADD);
					accountJnlJSH.setTransName("超级理财人债权转让产生利息");
					accountJnlJSH.setTransAmount(changeAmount);
					accountJnlJSH.setSysTime(new Date());
					accountJnlJSH.setChannelTime(null);
					accountJnlJSH.setChannelJnlNo(null);
					accountJnlJSH.setCdFlag1(Constants.CD_FLAG_C);
					accountJnlJSH.setUserId1(sTransferInfo.getNormalUserId());
					accountJnlJSH.setAccountId1(normalAUTH.getAccountId());
					accountJnlJSH.setSubAccountId1(normalAUTH.getId());
					accountJnlJSH.setSubAccountCode1(normalAUTH.getCode());
					accountJnlJSH.setBeforeBalance1(nAUTH.getBalance());
					accountJnlJSH.setAfterBalance1(nAUTH4Interest.getBalance());
					accountJnlJSH.setBeforeAvialableBalance1(nAUTH.getAvailableBalance());
					accountJnlJSH.setAfterAvialableBalance1(nAUTH4Interest.getAvailableBalance());
					accountJnlJSH.setBeforeFreezeBalance1(nAUTH.getFreezeBalance());
					accountJnlJSH.setAfterFreezeBalance1(nAUTH.getFreezeBalance());
					accountJnlJSH.setCdFlag2(Constants.CD_FLAG_D);
					accountJnlJSH.setUserId2(sTransferInfo.getSuperUserId());
					accountJnlJSH.setAccountId2(superJSH.getAccountId());
					accountJnlJSH.setSubAccountId2(superJSH.getId());
					accountJnlJSH.setSubAccountCode2(superJSH.getCode());
					accountJnlJSH.setBeforeBalance2(superJSH.getBalance());
					accountJnlJSH.setAfterBalance2(tempJshAct.getBalance());
					accountJnlJSH.setBeforeAvialableBalance2(superJSH.getAvailableBalance());
					accountJnlJSH.setAfterAvialableBalance2(tempJshAct.getAvailableBalance());
					accountJnlJSH.setBeforeFreezeBalance2(superJSH.getFreezeBalance());
					accountJnlJSH.setAfterFreezeBalance2(superJSH.getFreezeBalance());
					accountJnlJSH.setFee(0.0);
					bsAccountJnlMapper.insertSelective(accountJnlJSH);
					
					//S:AUTH_ZAN >>> USER
					BsSysSubAccount sysUserAct = bsSysSubAccountService.findSysSubAccount4Lock(Constants.SYS_ACCOUNT_BGW_USER);
					PartnerSysActCode partnerActCode = BaseAccount.partnerActCodeMap.get(sTransferInfo.getPartner());
					BsSysSubAccount patAuthAct = bsSysSubAccountService.findSysSubAccount4Lock(partnerActCode.getAuthActCode());
					BsSysSubAccount tempPatAuthAct = new BsSysSubAccount();
					tempPatAuthAct.setId(patAuthAct.getId());
					tempPatAuthAct.setBalance(MoneyUtil.subtract(patAuthAct.getBalance(), changeAmount).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
					tempPatAuthAct.setAvailableBalance(MoneyUtil.subtract(patAuthAct.getAvailableBalance(), changeAmount).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
					tempPatAuthAct.setCanWithdraw(MoneyUtil.subtract(patAuthAct.getCanWithdraw(), changeAmount).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
					bsSysSubAccountMapper.updateByPrimaryKeySelective(tempPatAuthAct);
					
					BsSysSubAccount tempSysUserAct = new BsSysSubAccount();
					tempSysUserAct.setId(sysUserAct.getId());
					tempSysUserAct.setBalance(MoneyUtil.add(sysUserAct.getBalance(), changeAmount).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
					tempSysUserAct.setAvailableBalance(MoneyUtil.add(sysUserAct.getAvailableBalance(), changeAmount).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
					tempSysUserAct.setCanWithdraw(MoneyUtil.add(sysUserAct.getCanWithdraw(), changeAmount).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
					bsSysSubAccountMapper.updateByPrimaryKeySelective(tempSysUserAct);
					
					//系统账记账
					BsSysAccountJnl sysAccountJnl = new BsSysAccountJnl();
					sysAccountJnl.setTransTime(new Date());
					sysAccountJnl.setTransCode(Constants.SYS_AUTH_BACK_TRANSFER);
					sysAccountJnl.setTransName("债权转让时系统站岗户资金回退到JSH");
					sysAccountJnl.setTransAmount(changeAmount);
					sysAccountJnl.setSysTime(new Date());
					sysAccountJnl.setChannelTime(null);
					sysAccountJnl.setChannelJnlNo(null);
					sysAccountJnl.setCdFlag1(Constants.CD_FLAG_C);
					sysAccountJnl.setSubAccountCode1(patAuthAct.getCode());
					sysAccountJnl.setBeforeBalance1(patAuthAct.getBalance());
					sysAccountJnl.setAfterBalance1(tempPatAuthAct.getBalance());
					sysAccountJnl.setBeforeAvialableBalance1(patAuthAct.getAvailableBalance());
					sysAccountJnl.setAfterAvialableBalance1(tempPatAuthAct.getAvailableBalance());
					sysAccountJnl.setBeforeFreezeBalance1(patAuthAct.getFreezeBalance());
					sysAccountJnl.setAfterFreezeBalance1(patAuthAct.getFreezeBalance());
					sysAccountJnl.setCdFlag2(Constants.CD_FLAG_D);
					sysAccountJnl.setSubAccountCode2(sysUserAct.getCode());
					sysAccountJnl.setBeforeBalance2(sysUserAct.getBalance());
					sysAccountJnl.setAfterBalance2(tempSysUserAct.getBalance());
					sysAccountJnl.setBeforeAvialableBalance2(sysUserAct.getAvailableBalance());
					sysAccountJnl.setAfterAvialableBalance2(tempSysUserAct.getAvailableBalance());
					sysAccountJnl.setBeforeFreezeBalance2(sysUserAct.getFreezeBalance());
					sysAccountJnl.setAfterFreezeBalance2(sysUserAct.getFreezeBalance());
					sysAccountJnl.setFee(0.0);
					bsSysAccountJnlMapper.insertSelective(sysAccountJnl);
				}
				
			/*}
		});*/
	}
	
	
	public Integer getTerm4Day(Integer term) {
		Integer term4Day = null;
		if (term == null || "".equals(term)) {
			return null;
		}
		
		if(term < 0){
			term4Day = Math.abs(term);
		}else if(term == 12){
			term4Day = 365;
		}else{
			term4Day = term*30;
		}
		return term4Day;
	}

}
