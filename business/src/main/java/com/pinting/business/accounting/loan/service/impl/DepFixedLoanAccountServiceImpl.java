package com.pinting.business.accounting.loan.service.impl;

import com.pinting.business.accounting.finance.model.ProductType;
import com.pinting.business.accounting.finance.model.SubAccountCode;
import com.pinting.business.accounting.loan.enums.PartnerEnum;
import com.pinting.business.accounting.loan.model.BaseAccount;
import com.pinting.business.accounting.loan.model.InvestorAuthYunInfo;
import com.pinting.business.accounting.loan.model.PartnerSysActCode;
import com.pinting.business.accounting.loan.service.DepFixedLoanAccountService;
import com.pinting.business.accounting.service.BsSysSubAccountService;
import com.pinting.business.dao.*;
import com.pinting.business.enums.PTMessageEnum;
import com.pinting.business.enums.RedisLockEnum;
import com.pinting.business.exception.PTMessageException;
import com.pinting.business.model.*;
import com.pinting.business.util.Constants;
import com.pinting.core.redis.JedisClientDaoSupport;
import com.pinting.core.util.MoneyUtil;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DepFixedLoanAccountServiceImpl implements
		DepFixedLoanAccountService {
	private final Logger logger = LoggerFactory.getLogger(DepFixedLoanAccountServiceImpl.class);
	private JedisClientDaoSupport jsClientDaoSupport = JedisClientDaoSupport.getInstance(Constants.REDIS_SUBSYS_BUSINESS);
	@Autowired
	private BsSubAccountMapper bsSubAccountMapper;
	@Autowired
	private BsAccountJnlMapper bsAccountJnlMapper;
	@Autowired
	private LnSubAccountMapper lnSubAccountMapper;
	@Autowired
	private BsSysSubAccountService bsSysSubAccountService;
	@Autowired
	private BsSysSubAccountMapper bsSysSubAccountMapper;
	@Autowired
	private BsSysAccountJnlMapper bsSysAccountJnlMapper;
	@Autowired
	private LnAccountJnlMapper lnAccountJnlMapper;
	@Autowired
	private BsSubAccountPairMapper bsSubAccountPairMapper;

	@Override
	@Transactional
	public void chargeLoanFreeze(Double authAmount, Integer investorAuthActId,
			Double redAmount, Integer investorRedActId) {
		logger.info("[chargeLoanFreeze]入参：authAmount=" + authAmount + ",investorAuthActId=" + investorAuthActId
				+",redAmount=" + redAmount+",investorRedActId=" + investorRedActId);
		
		if(investorAuthActId == null && investorRedActId == null){
			throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR);
		}
		if(authAmount > 0 && investorAuthActId == null){
			throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR);
		}
		if(redAmount > 0 && investorRedActId == null){
			throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR);
		}
		if(authAmount > 0){
			BsSubAccount authAct = bsSubAccountMapper.selectSubAccountByIdForLock(investorAuthActId);
			if(authAct == null){
				logger.info("[chargeLoanFreeze]未获取到相应账户数据");
				throw new PTMessageException(PTMessageEnum.NO_DATA_FOUND);
			}else{
				BsSubAccount tempAuthAct = new BsSubAccount();
				tempAuthAct.setId(authAct.getId());
				if(MoneyUtil.subtract(authAct.getAvailableBalance(), authAmount).doubleValue() < 0){
					throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR, "发生金额超限");
				}
				//可用余额，可提现余额减少
				tempAuthAct.setAvailableBalance(MoneyUtil.subtract(authAct.getAvailableBalance(), authAmount).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
				tempAuthAct.setCanWithdraw(MoneyUtil.subtract(authAct.getCanWithdraw(), authAmount).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
				//冻结余额增加
				tempAuthAct.setFreezeBalance(MoneyUtil.add(authAct.getFreezeBalance(), authAmount).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
				bsSubAccountMapper.updateByPrimaryKeySelective(tempAuthAct);
				
				//借款申请授权金额 用户账 记账
				BsAccountJnl accountJnl = new BsAccountJnl();
				accountJnl.setTransTime(new Date());
				accountJnl.setTransCode(Constants.USER_LOAN_FREEZE);
				accountJnl.setTransName("授权站岗金额冻结");
				accountJnl.setTransAmount(authAmount);
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
		}
		if(redAmount > 0){
			BsSubAccount redAct = bsSubAccountMapper.selectSubAccountByIdForLock(investorRedActId);
			if(redAct == null){
				logger.info("[chargeLoanFreeze]未获取到相应账户数据");
				throw new PTMessageException(PTMessageEnum.NO_DATA_FOUND);
			}else{
				BsSubAccount tempRedAct = new BsSubAccount();
				tempRedAct.setId(redAct.getId());
				if(MoneyUtil.subtract(redAct.getAvailableBalance(), redAmount).doubleValue() < 0){
					throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR, "发生金额超限");
				}
				//可用余额，可提现余额减少
				tempRedAct.setAvailableBalance(MoneyUtil.subtract(redAct.getAvailableBalance(), redAmount).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
				tempRedAct.setCanWithdraw(MoneyUtil.subtract(redAct.getCanWithdraw(), redAmount).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
				//冻结余额增加
				tempRedAct.setFreezeBalance(MoneyUtil.add(redAct.getFreezeBalance(), redAmount).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
				bsSubAccountMapper.updateByPrimaryKeySelective(tempRedAct);
				
				//借款申请授权金额 用户账 记账
				BsAccountJnl accountJnlRed = new BsAccountJnl();
				accountJnlRed.setTransTime(new Date());
				accountJnlRed.setTransCode(Constants.USER_LOAN_FREEZE);
				accountJnlRed.setTransName("授权红包金额冻结");
				accountJnlRed.setTransAmount(redAmount);
				accountJnlRed.setSysTime(new Date());
				accountJnlRed.setChannelTime(null);
				accountJnlRed.setChannelJnlNo(null);
				accountJnlRed.setCdFlag1(Constants.CD_FLAG_C);
				accountJnlRed.setUserId1(null);
				accountJnlRed.setAccountId1(redAct.getAccountId());
				accountJnlRed.setSubAccountId1(redAct.getId());
				accountJnlRed.setSubAccountCode1(redAct.getCode());
				accountJnlRed.setBeforeBalance1(redAct.getBalance());
				accountJnlRed.setAfterBalance1(redAct.getBalance());
				accountJnlRed.setBeforeAvialableBalance1(redAct.getAvailableBalance());
				accountJnlRed.setAfterAvialableBalance1(tempRedAct.getAvailableBalance());
				accountJnlRed.setBeforeFreezeBalance1(redAct.getFreezeBalance());
				accountJnlRed.setAfterFreezeBalance1(tempRedAct.getFreezeBalance());
				accountJnlRed.setFee(0.0);
				bsAccountJnlMapper.insertSelective(accountJnlRed);
			}
		}
	}
	
	
	@Override
	@Transactional
	public void chargeLoanUnFreeze(Double authAmount,
			Integer investorAuthActId, Double redAmount,
			Integer investorRedActId) {
		logger.info("[chargeLoanUnFreeze]入参：authAmount=" + authAmount + ",investorAuthActId=" + investorAuthActId
				+",redAmount=" + redAmount+",investorRedActId=" + investorRedActId);
		
		if(investorAuthActId == null && investorRedActId == null){
			throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR);
		}
		if(authAmount > 0 && investorAuthActId == null){
			throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR);
		}
		if(redAmount > 0 && investorRedActId == null){
			throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR);
		}
		if(authAmount > 0){
			BsSubAccount authAct = bsSubAccountMapper.selectSubAccountByIdForLock(investorAuthActId);
			if(authAct == null){
				logger.info("[chargeLoanUnFreeze]未获取到相应账户数据");
				throw new PTMessageException(PTMessageEnum.NO_DATA_FOUND);
			}else{
				BsSubAccount tempAuthAct = new BsSubAccount();
				tempAuthAct.setId(authAct.getId());
				if(MoneyUtil.subtract(authAct.getFreezeBalance(), authAmount).doubleValue() < 0){
					throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR, "发生金额超限");
				}
				//可用余额，可提现余额增加
				tempAuthAct.setAvailableBalance(MoneyUtil.add(authAct.getAvailableBalance(), authAmount).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
				tempAuthAct.setCanWithdraw(MoneyUtil.add(authAct.getCanWithdraw(), authAmount).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
				//冻结余额减少
				tempAuthAct.setFreezeBalance(MoneyUtil.subtract(authAct.getFreezeBalance(), authAmount).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
				bsSubAccountMapper.updateByPrimaryKeySelective(tempAuthAct);
				
				//借款申请授权金额 用户账 记账
				BsAccountJnl accountJnl = new BsAccountJnl();
				accountJnl.setTransTime(new Date());
				accountJnl.setTransCode(Constants.USER_LOAN_UNFREEZE);
				accountJnl.setTransName("授权站岗金额解冻");
				accountJnl.setTransAmount(authAmount);
				accountJnl.setSysTime(new Date());
				accountJnl.setChannelTime(null);
				accountJnl.setChannelJnlNo(null);
				accountJnl.setCdFlag1(Constants.CD_FLAG_D);
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
		}
		if(redAmount > 0){
			BsSubAccount redAct = bsSubAccountMapper.selectSubAccountByIdForLock(investorRedActId);
			if(redAct == null){
				logger.info("[chargeLoanUnFreeze]未获取到相应账户数据");
				throw new PTMessageException(PTMessageEnum.NO_DATA_FOUND);
			}else{
				BsSubAccount tempRedAct = new BsSubAccount();
				tempRedAct.setId(redAct.getId());
				if(MoneyUtil.subtract(redAct.getFreezeBalance(), redAmount).doubleValue() < 0){
					throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR, "发生金额超限");
				}
				//可用余额，可提现余额增加
				tempRedAct.setAvailableBalance(MoneyUtil.add(redAct.getAvailableBalance(), redAmount).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
				tempRedAct.setCanWithdraw(MoneyUtil.add(redAct.getCanWithdraw(), redAmount).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
				//冻结余额减少
				tempRedAct.setFreezeBalance(MoneyUtil.subtract(redAct.getFreezeBalance(), redAmount).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
				bsSubAccountMapper.updateByPrimaryKeySelective(tempRedAct);
				
				//借款申请授权金额 用户账 记账
				BsAccountJnl accountJnlRed = new BsAccountJnl();
				accountJnlRed.setTransTime(new Date());
				accountJnlRed.setTransCode(Constants.USER_LOAN_UNFREEZE);
				accountJnlRed.setTransName("授权红包金额解冻");
				accountJnlRed.setTransAmount(redAmount);
				accountJnlRed.setSysTime(new Date());
				accountJnlRed.setChannelTime(null);
				accountJnlRed.setChannelJnlNo(null);
				accountJnlRed.setCdFlag1(Constants.CD_FLAG_C);
				accountJnlRed.setUserId1(null);
				accountJnlRed.setAccountId1(redAct.getAccountId());
				accountJnlRed.setSubAccountId1(redAct.getId());
				accountJnlRed.setSubAccountCode1(redAct.getCode());
				accountJnlRed.setBeforeBalance1(redAct.getBalance());
				accountJnlRed.setAfterBalance1(redAct.getBalance());
				accountJnlRed.setBeforeAvialableBalance1(redAct.getAvailableBalance());
				accountJnlRed.setAfterAvialableBalance1(tempRedAct.getAvailableBalance());
				accountJnlRed.setBeforeFreezeBalance1(redAct.getFreezeBalance());
				accountJnlRed.setAfterFreezeBalance1(tempRedAct.getFreezeBalance());
				accountJnlRed.setFee(0.0);
				bsAccountJnlMapper.insertSelective(accountJnlRed);
			}
		}
		
	}

	@Override
	@Transactional
	public void chargeUpLoan(BaseAccount baseAccount, List<InvestorAuthYunInfo> investorAuthYunInfos, Integer loanSubActId, Double headFee) {
		//投资人站岗户-，产品红包户-(>0时)，融资人余额-，系统站岗户-，存管红包户-，系统产品户+，系统砍头息户+，支付手续费户+
		logger.info("[chargeUpLoan]入参：" + baseAccount + ",investorAuthYunInfos="
				+ investorAuthYunInfos + ",loanSubActId=" + loanSubActId);
		PartnerEnum partner = baseAccount.getPartner();
		Integer borrowerUserId = baseAccount.getBorrowerUserId();
		Integer investorUserId = baseAccount.getInvestorUserId();
		Double realAmount = baseAccount.getRealAmount();
		Double redPacAmount = baseAccount.getRedPacAmount();
		Double amount = MoneyUtil.add(realAmount, redPacAmount).doubleValue();
		if(partner == null || borrowerUserId == null || realAmount == null || redPacAmount == null ||
				investorAuthYunInfos == null || investorAuthYunInfos.size() == 0 || loanSubActId == null || headFee == null){
			throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR);
		}

		Map<PartnerEnum, Map> partnerMap = new HashMap<>(); // 资产方记账map
		for (InvestorAuthYunInfo investorAuthYunInfo : investorAuthYunInfos) {
			Integer authYunId = investorAuthYunInfo.getInvestorAuthYunActId(); //动账云贷产品户id
			Double authYunAmount = investorAuthYunInfo.getAuthYunAmount(); //匹配上的金额
			Double subRedPacAmount = investorAuthYunInfo.getRedPacAmount();//红包金额
			Double subRealAuthAmount = MoneyUtil.subtract(authYunAmount, subRedPacAmount).doubleValue();

			try {
				jsClientDaoSupport.unlock(RedisLockEnum.LOCK_LOAN_SELF_OUTOF_ACCOUNT.getKey() + authYunId);

				//投资人站岗户-
				BsSubAccount authAct = bsSubAccountMapper.selectSubAccountByIdForLock(authYunId);
				BsSubAccount tempAuthAct = new BsSubAccount();
				tempAuthAct.setId(authAct.getId());

				if(MoneyUtil.subtract(authAct.getFreezeBalance(), subRealAuthAmount).doubleValue() < 0){
					throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR, "发生金额超限");
				}
				tempAuthAct.setBalance(MoneyUtil.subtract(authAct.getBalance(), subRealAuthAmount).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
				tempAuthAct.setFreezeBalance(MoneyUtil.subtract(authAct.getFreezeBalance(), subRealAuthAmount).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
				bsSubAccountMapper.updateByPrimaryKeySelective(tempAuthAct);

				Map<String, Double> authMap = partnerMap.get(SubAccountCode.getPartnerByAuthCode(authAct.getProductType()));
				if (authMap == null) {
					authMap = new HashMap<>(); // 记账站岗户数据
				}
				authMap.put(authAct.getProductType(), MoneyUtil.add(authMap.get(authAct.getProductType()) != null ? authMap.get(authAct.getProductType()) : 0d, subRealAuthAmount).doubleValue()); // 累积站岗户金额

				//用户账记账
				BsAccountJnl accountJnl = new BsAccountJnl();
				accountJnl.setTransTime(new Date());
				String transCode = Constants.USER_AUTH_OUT + SubAccountCode.getPartnerByAuthCode(authAct.getProductType()).getCode();
				accountJnl.setTransCode(transCode);
				accountJnl.setTransName("自主放款站岗户资金转出");
				accountJnl.setTransAmount(subRealAuthAmount);
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
				accountJnl.setFee(0.0);
				bsAccountJnlMapper.insertSelective(accountJnl);

				//产品红包户-
				if(subRedPacAmount > 0) {
					BsSubAccountPairExample examplePair = new BsSubAccountPairExample();
					examplePair.createCriteria().andAuthAccountIdEqualTo(authYunId);
					List<BsSubAccountPair> bsSubAccountPairs = bsSubAccountPairMapper.selectByExample(examplePair);
					if(CollectionUtils.isNotEmpty(bsSubAccountPairs)){
						BsSubAccountPair bsSubAccountPair = bsSubAccountPairs.get(0);
						BsSubAccount redAct = bsSubAccountMapper.selectSubAccountByIdForLock(bsSubAccountPair.getRedAccountId());
						BsSubAccount tempRedAct = new BsSubAccount();
						tempRedAct.setId(redAct.getId());

						if(MoneyUtil.defaultRound(redAct.getFreezeBalance()-subRedPacAmount).doubleValue() < 0){
							throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR, ",产品红包户-时发生金额超限");
						}
						tempRedAct.setBalance(MoneyUtil.subtract(redAct.getBalance(), subRedPacAmount).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
						tempRedAct.setFreezeBalance(MoneyUtil.subtract(redAct.getFreezeBalance(), subRedPacAmount).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
						bsSubAccountMapper.updateByPrimaryKeySelective(tempRedAct);

						authMap.put(redAct.getProductType(), MoneyUtil.add(authMap.get(redAct.getProductType()) != null ? authMap.get(redAct.getProductType()) : 0d, subRedPacAmount).doubleValue()); // 累积红包户金额

						//用户账记账
						BsAccountJnl accountJnlRed = new BsAccountJnl();
						accountJnlRed.setTransTime(new Date());
						accountJnlRed.setTransCode(Constants.USER_RED_OUT);
						accountJnlRed.setTransName("自主放款红包户资金转出");
						accountJnlRed.setTransAmount(subRedPacAmount);
						accountJnlRed.setSysTime(new Date());
						accountJnlRed.setChannelTime(null);
						accountJnlRed.setChannelJnlNo(null);
						accountJnlRed.setCdFlag1(Constants.CD_FLAG_C);
						accountJnlRed.setUserId1(investorUserId);
						accountJnlRed.setAccountId1(redAct.getAccountId());
						accountJnlRed.setSubAccountId1(redAct.getId());
						accountJnlRed.setSubAccountCode1(redAct.getCode());
						accountJnlRed.setBeforeBalance1(redAct.getBalance());
						accountJnlRed.setAfterBalance1(tempRedAct.getBalance());
						accountJnlRed.setBeforeAvialableBalance1(redAct.getAvailableBalance());
						accountJnlRed.setAfterAvialableBalance1(redAct.getAvailableBalance());
						accountJnlRed.setBeforeFreezeBalance1(redAct.getFreezeBalance());
						accountJnlRed.setAfterFreezeBalance1(tempRedAct.getFreezeBalance());
						accountJnlRed.setCdFlag2(Constants.CD_FLAG_D);
						accountJnlRed.setUserId2(investorUserId);
						accountJnlRed.setFee(0.0);
						bsAccountJnlMapper.insertSelective(accountJnlRed);
					}
				}
				partnerMap.put(SubAccountCode.getPartnerByAuthCode(authAct.getProductType()), authMap);
			} finally {
				jsClientDaoSupport.unlock(RedisLockEnum.LOCK_LOAN_SELF_OUTOF_ACCOUNT.getKey() + authYunId);
			}
		}

		for (PartnerEnum partnerEnum : partnerMap.keySet()) {
			Map<String, Double> authMap = partnerMap.get(partnerEnum);
			ProductType productType = SubAccountCode.productTypeMap.get(partnerEnum);
			Double realAuthAmount = authMap.get(productType.getAuthCode());
            Double realredPacAmount = authMap.get(productType.getRedCode()) == null ? 0d : authMap.get(productType.getRedCode());
            Double realTotalAmount = MoneyUtil.add(realAuthAmount, realredPacAmount).doubleValue();

			try {
				jsClientDaoSupport.unlock(RedisLockEnum.LOCK_LOAN_SELF_OUTOF_ACCOUNT.getKey() + productType.getAuthCode());

				//S:系统站岗户-，系统红包户-  >>> 系统产品户+
				PartnerSysActCode partnerActCode = BaseAccount.partnerActCodeMap.get(partnerEnum);
				BsSysSubAccount patAuthAct = bsSysSubAccountService.findSysSubAccount4Lock(partnerActCode.getAuthActCode());
				BsSysSubAccount tempPatAuthAct = new BsSysSubAccount();
				tempPatAuthAct.setId(patAuthAct.getId());
				tempPatAuthAct.setBalance(MoneyUtil.subtract(patAuthAct.getBalance(), realAuthAmount).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
				tempPatAuthAct.setAvailableBalance(MoneyUtil.subtract(patAuthAct.getAvailableBalance(), realAuthAmount).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
				tempPatAuthAct.setCanWithdraw(MoneyUtil.subtract(patAuthAct.getCanWithdraw(), realAuthAmount).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
				bsSysSubAccountMapper.updateByPrimaryKeySelective(tempPatAuthAct);

				BsSysSubAccount patRegAct = bsSysSubAccountService.findSysSubAccount4Lock(partnerActCode.getRegActCode());
				BsSysSubAccount tempPatRegAct = new BsSysSubAccount();
				tempPatRegAct.setId(patRegAct.getId());
				tempPatRegAct.setBalance(MoneyUtil.add(patRegAct.getBalance(), realTotalAmount).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
				tempPatRegAct.setAvailableBalance(MoneyUtil.add(patRegAct.getAvailableBalance(), realTotalAmount).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
				tempPatRegAct.setCanWithdraw(MoneyUtil.add(patRegAct.getCanWithdraw(), realTotalAmount).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
				bsSysSubAccountMapper.updateByPrimaryKeySelective(tempPatRegAct);

				Double redBalanceBefore = MoneyUtil.defaultRound(tempPatRegAct.getBalance() - realredPacAmount).doubleValue();
				Double redAvailableBalanceBefore = MoneyUtil.defaultRound(tempPatRegAct.getAvailableBalance() - realredPacAmount).doubleValue();
				//系统账记账
				BsSysAccountJnl sysAccountJnl = new BsSysAccountJnl();
				sysAccountJnl.setTransTime(new Date());
				sysAccountJnl.setTransCode(Constants.SYS_AUTH_2_REG);
				sysAccountJnl.setTransName("合作方站岗户资金转产品户");
				sysAccountJnl.setTransAmount(realAuthAmount);
				sysAccountJnl.setSysTime(new Date());
				sysAccountJnl.setChannelTime(null);
				sysAccountJnl.setChannelJnlNo(null);
				sysAccountJnl.setCdFlag1(Constants.CD_FLAG_C);
				sysAccountJnl.setSubAccountCode1(patAuthAct.getCode());
				sysAccountJnl.setBeforeBalance1(patAuthAct.getBalance());
				sysAccountJnl.setAfterBalance1(tempPatAuthAct.getBalance());
				sysAccountJnl.setBeforeAvialableBalance1(patAuthAct.getAvailableBalance() );
				sysAccountJnl.setAfterAvialableBalance1(tempPatAuthAct.getAvailableBalance());
				sysAccountJnl.setBeforeFreezeBalance1(patAuthAct.getFreezeBalance());
				sysAccountJnl.setAfterFreezeBalance1(patAuthAct.getFreezeBalance());
				sysAccountJnl.setCdFlag2(Constants.CD_FLAG_D);
				sysAccountJnl.setSubAccountCode2(patRegAct.getCode());
				sysAccountJnl.setBeforeBalance2(patRegAct.getBalance());
				sysAccountJnl.setAfterBalance2(redBalanceBefore);
				sysAccountJnl.setBeforeAvialableBalance2(patRegAct.getAvailableBalance());
				sysAccountJnl.setAfterAvialableBalance2(redAvailableBalanceBefore);
				sysAccountJnl.setBeforeFreezeBalance2(patRegAct.getFreezeBalance());
				sysAccountJnl.setAfterFreezeBalance2(patRegAct.getFreezeBalance());
				sysAccountJnl.setFee(0.0);
				bsSysAccountJnlMapper.insertSelective(sysAccountJnl);

				if(realredPacAmount > 0){
					BsSysSubAccount redPacActLock = bsSysSubAccountService.findSysSubAccount4Lock(Constants.SYS_ACCOUNT_DEP_REDPACKET);
					BsSysSubAccount redPacAct = new BsSysSubAccount();
					redPacAct.setId(redPacActLock.getId());
					redPacAct.setBalance(MoneyUtil.subtract(redPacActLock.getBalance(), realredPacAmount).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
					redPacAct.setAvailableBalance(MoneyUtil.subtract(redPacActLock.getAvailableBalance(), realredPacAmount).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
					redPacAct.setCanWithdraw(MoneyUtil.subtract(redPacActLock.getCanWithdraw(), realredPacAmount).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
					bsSysSubAccountMapper.updateByPrimaryKeySelective(redPacAct);
					//系统账记账
					BsSysAccountJnl sysRedAccountJnl = new BsSysAccountJnl();
					sysRedAccountJnl.setTransTime(new Date());
					sysRedAccountJnl.setTransCode(Constants.SYS_RED_2_REG);
					sysRedAccountJnl.setTransName("红包户资金转产品户");
					sysRedAccountJnl.setTransAmount(realredPacAmount);
					sysRedAccountJnl.setSysTime(new Date());
					sysRedAccountJnl.setChannelTime(null);
					sysRedAccountJnl.setChannelJnlNo(null);
					sysRedAccountJnl.setCdFlag1(Constants.CD_FLAG_C);
					sysRedAccountJnl.setSubAccountCode1(redPacActLock.getCode());
					sysRedAccountJnl.setBeforeBalance1(redPacActLock.getBalance());
					sysRedAccountJnl.setAfterBalance1(redPacAct.getBalance());
					sysRedAccountJnl.setBeforeAvialableBalance1(redPacActLock.getAvailableBalance() );
					sysRedAccountJnl.setAfterAvialableBalance1(redPacAct.getAvailableBalance());
					sysRedAccountJnl.setBeforeFreezeBalance1(redPacActLock.getFreezeBalance());
					sysRedAccountJnl.setAfterFreezeBalance1(redPacActLock.getFreezeBalance());
					sysRedAccountJnl.setCdFlag2(Constants.CD_FLAG_D);
					sysRedAccountJnl.setSubAccountCode2(patRegAct.getCode());
					sysRedAccountJnl.setBeforeBalance2(redBalanceBefore);
					sysRedAccountJnl.setAfterBalance2(tempPatRegAct.getBalance());
					sysRedAccountJnl.setBeforeAvialableBalance2(redAvailableBalanceBefore);
					sysRedAccountJnl.setAfterAvialableBalance2(tempPatRegAct.getAvailableBalance());
					sysRedAccountJnl.setBeforeFreezeBalance2(patRegAct.getFreezeBalance());
					sysRedAccountJnl.setAfterFreezeBalance2(patRegAct.getFreezeBalance());
					sysRedAccountJnl.setFee(0.0);
					bsSysAccountJnlMapper.insertSelective(sysRedAccountJnl);
				}
			} finally {
				jsClientDaoSupport.unlock(RedisLockEnum.LOCK_LOAN_SELF_OUTOF_ACCOUNT.getKey() + productType.getAuthCode());
			}
		}

		//融资余额户-
		LnSubAccount loanActLock= lnSubAccountMapper.selectByPrimaryKey4Lock(loanSubActId);
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
		LnAccountJnl lnAccountJnl = new LnAccountJnl();
		lnAccountJnl.setTransTime(new Date());
		lnAccountJnl.setTransCode(Constants.LN_DEP_JSH_SUB);
		lnAccountJnl.setTransName("借款出账");
		lnAccountJnl.setTransAmount(amount);
		lnAccountJnl.setSysTime(new Date());
		lnAccountJnl.setCdFlag1(Constants.CD_FLAG_C);
		lnAccountJnl.setUserId1(loanActLock.getLnUserId());
		lnAccountJnl.setSubAccountId1(loanActLock.getId());
		lnAccountJnl.setBeforeBalance1(loanActLock.getBalance());
		lnAccountJnl.setAfterBalance1(tempLnAct.getBalance());
		lnAccountJnl.setBeforeAvialableBalance1(loanActLock.getAvailableBalance());
		lnAccountJnl.setAfterAvialableBalance1(tempLnAct.getAvailableBalance());
		lnAccountJnl.setBeforeFreezeBalance1(loanActLock.getFreezeBalance());
		lnAccountJnl.setAfterFreezeBalance1(loanActLock.getFreezeBalance());
		lnAccountJnl.setFee(0.0);
		lnAccountJnlMapper.insertSelective(lnAccountJnl);

		if(headFee > 0){
			//砍头息系统账户FEE_YUN_INCR增加记账bs_sys_sub_account、bs_sys_account_jnl
			//S:FEE_YUN_INCR+
			PartnerSysActCode partnerActCode = BaseAccount.partnerActCodeMap.get(partner);
			BsSysSubAccount feeYunIncr = bsSysSubAccountService.findSysSubAccount4Lock(partnerActCode.getDepHeadFeeActCode());
			BsSysSubAccount tempFeeYunIncr= new BsSysSubAccount();
			tempFeeYunIncr.setId(feeYunIncr.getId());
			tempFeeYunIncr.setBalance(MoneyUtil.add(feeYunIncr.getBalance(), headFee).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
			tempFeeYunIncr.setAvailableBalance(MoneyUtil.add(feeYunIncr.getAvailableBalance(), headFee).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
			tempFeeYunIncr.setCanWithdraw(MoneyUtil.add(feeYunIncr.getCanWithdraw(), headFee).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
			bsSysSubAccountMapper.updateByPrimaryKeySelective(tempFeeYunIncr);

			//砍头息账户收入 记账
			BsSysAccountJnl sysActJnl4FeeYunIncr = new BsSysAccountJnl();
			sysActJnl4FeeYunIncr.setTransTime(new Date());
			String transCode = Constants.SYS_HEAD_FEE + partner.getCode();
			sysActJnl4FeeYunIncr.setTransCode(transCode);
			sysActJnl4FeeYunIncr.setTransName("系统砍头息账户收入");
			sysActJnl4FeeYunIncr.setTransAmount(headFee);
			sysActJnl4FeeYunIncr.setSysTime(new Date());
			sysActJnl4FeeYunIncr.setChannelTime(null);
			sysActJnl4FeeYunIncr.setChannelJnlNo(null);
			sysActJnl4FeeYunIncr.setCdFlag2(Constants.CD_FLAG_D);
			sysActJnl4FeeYunIncr.setSubAccountCode2(feeYunIncr.getCode());
			sysActJnl4FeeYunIncr.setBeforeBalance2(feeYunIncr.getBalance());
			sysActJnl4FeeYunIncr.setAfterBalance2(tempFeeYunIncr.getBalance());
			sysActJnl4FeeYunIncr.setBeforeAvialableBalance2(feeYunIncr.getAvailableBalance());
			sysActJnl4FeeYunIncr.setAfterAvialableBalance2(tempFeeYunIncr.getAvailableBalance());
			sysActJnl4FeeYunIncr.setBeforeFreezeBalance2(feeYunIncr.getFreezeBalance());
			sysActJnl4FeeYunIncr.setAfterFreezeBalance2(feeYunIncr.getFreezeBalance());
			sysActJnl4FeeYunIncr.setFee(0.0);
			bsSysAccountJnlMapper.insertSelective(sysActJnl4FeeYunIncr);
		}

	}


}
