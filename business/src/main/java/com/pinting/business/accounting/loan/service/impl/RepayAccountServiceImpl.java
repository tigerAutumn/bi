package com.pinting.business.accounting.loan.service.impl;

import com.pinting.business.accounting.loan.enums.PartnerEnum;
import com.pinting.business.accounting.loan.model.BaseAccount;
import com.pinting.business.accounting.loan.model.PartnerSysActCode;
import com.pinting.business.accounting.loan.model.ReceiveMoneyInfo;
import com.pinting.business.accounting.loan.model.RepayInfo;
import com.pinting.business.accounting.loan.service.RepayAccountService;
import com.pinting.business.accounting.service.BsSysSubAccountService;
import com.pinting.business.dao.*;
import com.pinting.business.enums.PTMessageEnum;
import com.pinting.business.exception.PTMessageException;
import com.pinting.business.model.*;
import com.pinting.business.util.Constants;
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
public class RepayAccountServiceImpl implements RepayAccountService {
	private final Logger logger = LoggerFactory.getLogger(RepayAccountServiceImpl.class);
	@Autowired
	private TransactionTemplate transactionTemplate;
	@Autowired
	private BsSysSubAccountMapper bsSysSubAccountMapper;
	@Autowired
	private BsSubAccountMapper bsSubAccountMapper;
	@Autowired
	private BsAccountJnlMapper bsAccountJnlMapper;
	@Autowired
	private BsSysAccountJnlMapper bsSysAccountJnlMapper;
	@Autowired
	private LnSubAccountMapper lnSubAccountMapper;
	@Autowired
	private LnAccountJnlMapper lnAccountJnlMapper;
	@Autowired
	private LnPartnerChargeRuleMapper lnPartnerChargeRuleMapper;
	@Autowired
	private BsSubAccountPairMapper bsSubAccountPairMapper;
	@Autowired
	private BsSysSubAccountService bsSysSubAccountService;
	
	/**
	 * 正常还款记账
	 * B:LOAN - 本金
	 * S:REG_ZAN - 理财人本金
	 * S:RETURN_ZAN + (THD_REPAY)
	 * S:REVENUE_ZAN + (还款总金额 - 币港湾结算 -保证金)
	 * S:THD_REVENUE_ZAN+ (保证金)
	 * S:THD_REPAY + 协议利率（本+息）
	 * S:THD_BGW_REVENUE_ZAN + （币港湾结算-每笔债权协议利率）为负值
	 * S:FEE + 
	 * @param repayInfo 合作方、借款人借款户编号、发生金额、本次本金、本次理财人利息、
	 * 	手续费、币港湾营收、保证金计费规则编号、还款到理财人总金额 必输
	 */
	@Override
	@Transactional
	public void chargeNormalRepay(RepayInfo repayInfo) {
		logger.info("[chargeNormalRepay]入参：" + repayInfo.toString());
		PartnerEnum partner = repayInfo.getPartner();
		Double amount = repayInfo.getAmount();
		Integer loanActId = repayInfo.getLoanActId();
		Double principal = repayInfo.getPrincipal();
		Double interest = repayInfo.getInterest();
		Double fee = repayInfo.getFee();
		Double bailAmount = repayInfo.getBailAmount();
		Integer chargeRuleId = repayInfo.getChargeRuleId();
		Double thdRepayAmount = repayInfo.getThdRepayAmount();
		Double revenueZanAmount = repayInfo.getRevenueZanAmount();
		Double bgwRevenueAmount = repayInfo.getBgwRevenueAmount();
		Double sumFinancePrincipal = repayInfo.getFinancePrincipal();
		if(partner == null || loanActId == null || amount == null || fee == null || bailAmount == null ||
				principal == null || interest == null || chargeRuleId == null || 
				thdRepayAmount == null || revenueZanAmount == null || sumFinancePrincipal == null){
			throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR);
		}

		/*transactionTemplate.execute(new TransactionCallbackWithoutResult(){
			@Override
			protected void doInTransactionWithoutResult(TransactionStatus status) {*/
				//B:LOAN - 本金
				LnSubAccount lnAct = lnSubAccountMapper.selectByPrimaryKey4Lock(loanActId);
				LnSubAccount tempLnAct = new LnSubAccount();
				if(MoneyUtil.subtract(lnAct.getBalance(), principal).doubleValue() < 0){
					logger.info("[正常还款记账]发生金额超限");
					throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR, "发生金额超限");
				}
				if(MoneyUtil.subtract(lnAct.getBalance(), principal).doubleValue() == 0){
					tempLnAct.setStatus(Constants.LOAN_SUBACCOUNT_STATUS_REPAIED);
				}
				tempLnAct.setId(lnAct.getId());
				tempLnAct.setBalance(MoneyUtil.subtract(lnAct.getBalance(), principal).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
				tempLnAct.setAvailableBalance(MoneyUtil.subtract(lnAct.getAvailableBalance(), principal).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
				tempLnAct.setCanWithdraw(MoneyUtil.subtract(lnAct.getCanWithdraw(), principal).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
				tempLnAct.setUpdateTime(new Date());
				lnSubAccountMapper.updateByPrimaryKeySelective(tempLnAct);
				//借款人账户记账
				LnAccountJnl lnAccountJnl = new LnAccountJnl();
				lnAccountJnl.setTransTime(new Date());
				lnAccountJnl.setTransCode(Constants.LN_USER_REPAY);
				lnAccountJnl.setTransName("还款");
				lnAccountJnl.setTransAmount(principal);
				lnAccountJnl.setSysTime(new Date());
				lnAccountJnl.setCdFlag1(Constants.CD_FLAG_C);
				lnAccountJnl.setUserId1(lnAct.getLnUserId());
				lnAccountJnl.setSubAccountId1(lnAct.getId());
				lnAccountJnl.setBeforeBalance1(lnAct.getBalance());
				lnAccountJnl.setAfterBalance1(tempLnAct.getBalance());
				lnAccountJnl.setBeforeAvialableBalance1(lnAct.getAvailableBalance());
				lnAccountJnl.setAfterAvialableBalance1(tempLnAct.getAvailableBalance());
				lnAccountJnl.setBeforeFreezeBalance1(lnAct.getFreezeBalance());
				lnAccountJnl.setAfterFreezeBalance1(lnAct.getFreezeBalance());
				lnAccountJnl.setFee(0.0);
				lnAccountJnlMapper.insertSelective(lnAccountJnl);

				//S:REG_ZAN - 本金
				PartnerSysActCode partnerActCode = BaseAccount.partnerActCodeMap.get(partner);
				BsSysSubAccount patRegAct = bsSysSubAccountService.findSysSubAccount4Lock(partnerActCode.getRegActCode());
				BsSysSubAccount tempPatRegAct = new BsSysSubAccount();
				tempPatRegAct.setId(patRegAct.getId());
				tempPatRegAct.setBalance(MoneyUtil.subtract(patRegAct.getBalance(), sumFinancePrincipal).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
				tempPatRegAct.setAvailableBalance(MoneyUtil.subtract(patRegAct.getAvailableBalance(), sumFinancePrincipal).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
				tempPatRegAct.setCanWithdraw(MoneyUtil.subtract(patRegAct.getCanWithdraw(), sumFinancePrincipal).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
				bsSysSubAccountMapper.updateByPrimaryKeySelective(tempPatRegAct);

				//S:RETURN_ZAN + (THD_REPAY)
				BsSysSubAccount patReturnAct = bsSysSubAccountService.findSysSubAccount4Lock(partnerActCode.getReturnActCode());
				BsSysSubAccount tempPatReturnAct = new BsSysSubAccount();
				tempPatReturnAct.setId(patReturnAct.getId());
				tempPatReturnAct.setBalance(MoneyUtil.add(patReturnAct.getBalance(), thdRepayAmount).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
				tempPatReturnAct.setAvailableBalance(MoneyUtil.add(patReturnAct.getAvailableBalance(), thdRepayAmount).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
				tempPatReturnAct.setCanWithdraw(MoneyUtil.add(patReturnAct.getCanWithdraw(), thdRepayAmount).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
				bsSysSubAccountMapper.updateByPrimaryKeySelective(tempPatReturnAct);
				//合作方回款户转入 记账
				BsSysAccountJnl sysActJnl4Return = new BsSysAccountJnl();
				sysActJnl4Return.setTransTime(new Date());
				sysActJnl4Return.setTransCode(Constants.SYS_LOAN_2_RETURN);
				sysActJnl4Return.setTransName("合作方回款户转入");
				sysActJnl4Return.setTransAmount(thdRepayAmount);
				sysActJnl4Return.setSysTime(new Date());
				sysActJnl4Return.setChannelTime(null);
				sysActJnl4Return.setChannelJnlNo(null);
				sysActJnl4Return.setCdFlag1(Constants.CD_FLAG_C);
				sysActJnl4Return.setSubAccountCode1(patRegAct.getCode());
				sysActJnl4Return.setBeforeBalance1(patRegAct.getBalance());
				sysActJnl4Return.setAfterBalance1(tempPatRegAct.getBalance());
				sysActJnl4Return.setBeforeAvialableBalance1(patRegAct.getAvailableBalance());
				sysActJnl4Return.setAfterAvialableBalance1(tempPatRegAct.getAvailableBalance());
				sysActJnl4Return.setBeforeFreezeBalance1(patRegAct.getFreezeBalance());
				sysActJnl4Return.setAfterFreezeBalance1(patRegAct.getFreezeBalance());
				sysActJnl4Return.setCdFlag2(Constants.CD_FLAG_D);
				sysActJnl4Return.setSubAccountCode2(patReturnAct.getCode());
				sysActJnl4Return.setBeforeBalance2(patReturnAct.getBalance());
				sysActJnl4Return.setAfterBalance2(tempPatReturnAct.getBalance());
				sysActJnl4Return.setBeforeAvialableBalance2(patReturnAct.getAvailableBalance());
				sysActJnl4Return.setAfterAvialableBalance2(tempPatReturnAct.getAvailableBalance());
				sysActJnl4Return.setBeforeFreezeBalance2(patReturnAct.getFreezeBalance());
				sysActJnl4Return.setAfterFreezeBalance2(patReturnAct.getFreezeBalance());
				sysActJnl4Return.setFee(0.0);
				bsSysAccountJnlMapper.insertSelective(sysActJnl4Return);
				
				//S:REVENUE_ZAN + (还款总金额 - 币港湾结算 -保证金 = revenueZanAmount)
				BsSysSubAccount patRevenueAct = bsSysSubAccountService.findSysSubAccount4Lock(partnerActCode.getRevenueActCode());
				BsSysSubAccount tempPatRevenueAct = new BsSysSubAccount();
				tempPatRevenueAct.setId(patRevenueAct.getId());
				tempPatRevenueAct.setBalance(MoneyUtil.add(patRevenueAct.getBalance(), revenueZanAmount).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
				tempPatRevenueAct.setAvailableBalance(MoneyUtil.add(patRevenueAct.getAvailableBalance(), revenueZanAmount).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
				tempPatRevenueAct.setCanWithdraw(MoneyUtil.add(patRevenueAct.getCanWithdraw(), revenueZanAmount).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
				bsSysSubAccountMapper.updateByPrimaryKeySelective(tempPatRevenueAct);
				//合作方营收账户入账 记账
				BsSysAccountJnl sysActJnl4Revenue = new BsSysAccountJnl();
				sysActJnl4Revenue.setTransTime(new Date());
				sysActJnl4Revenue.setTransCode(Constants.SYS_LOAN_2_REVENUE);
				sysActJnl4Revenue.setTransName("合作方营收账户入账");
				sysActJnl4Revenue.setTransAmount(revenueZanAmount);
				sysActJnl4Revenue.setSysTime(new Date());
				sysActJnl4Revenue.setChannelTime(null);
				sysActJnl4Revenue.setChannelJnlNo(null);
				sysActJnl4Revenue.setCdFlag2(Constants.CD_FLAG_D);
				sysActJnl4Revenue.setSubAccountCode2(patRevenueAct.getCode());
				sysActJnl4Revenue.setBeforeBalance2(patRevenueAct.getBalance());
				sysActJnl4Revenue.setAfterBalance2(tempPatRevenueAct.getBalance());
				sysActJnl4Revenue.setBeforeAvialableBalance2(patRevenueAct.getAvailableBalance());
				sysActJnl4Revenue.setAfterAvialableBalance2(tempPatRevenueAct.getAvailableBalance());
				sysActJnl4Revenue.setBeforeFreezeBalance2(patRevenueAct.getFreezeBalance());
				sysActJnl4Revenue.setAfterFreezeBalance2(patRevenueAct.getFreezeBalance());
				sysActJnl4Revenue.setFee(0.0);
				bsSysAccountJnlMapper.insertSelective(sysActJnl4Revenue);
				
				//S:REVENUE_ZAN >>> MARGIN_ZAN + 保证金
				BsSysSubAccount patMarginAct = bsSysSubAccountService.findSysSubAccount4Lock(partnerActCode.getMarginActCode());
				BsSysSubAccount tempPatMarginAct = new BsSysSubAccount();
				tempPatMarginAct.setId(patMarginAct.getId());
				tempPatMarginAct.setBalance(MoneyUtil.add(patMarginAct.getBalance(), bailAmount).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
				tempPatMarginAct.setAvailableBalance(MoneyUtil.add(patMarginAct.getAvailableBalance(), bailAmount).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
				tempPatMarginAct.setCanWithdraw(MoneyUtil.add(patMarginAct.getCanWithdraw(), bailAmount).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
				bsSysSubAccountMapper.updateByPrimaryKeySelective(tempPatMarginAct);
				//合作方营收账户保证金转入 记账
				BsSysAccountJnl sysActJnl4Margin = new BsSysAccountJnl();
				sysActJnl4Margin.setTransTime(new Date());
				sysActJnl4Margin.setTransCode(Constants.SYS_REPAY_2_MARGIN);
				sysActJnl4Margin.setTransName("合作方保证金户入账");
				sysActJnl4Margin.setTransAmount(bailAmount);
				sysActJnl4Margin.setSysTime(new Date());
				sysActJnl4Margin.setChannelTime(null);
				sysActJnl4Margin.setChannelJnlNo(null);
				sysActJnl4Margin.setCdFlag1(Constants.CD_FLAG_D);
				sysActJnl4Margin.setSubAccountCode2(patMarginAct.getCode());
				sysActJnl4Margin.setBeforeBalance2(patMarginAct.getBalance());
				sysActJnl4Margin.setAfterBalance2(tempPatMarginAct.getBalance());
				sysActJnl4Margin.setBeforeAvialableBalance2(patMarginAct.getAvailableBalance());
				sysActJnl4Margin.setAfterAvialableBalance2(tempPatMarginAct.getAvailableBalance());
				sysActJnl4Margin.setBeforeFreezeBalance2(patMarginAct.getFreezeBalance());
				sysActJnl4Margin.setAfterFreezeBalance2(patMarginAct.getFreezeBalance());
				sysActJnl4Margin.setFee(0.0);
				bsSysAccountJnlMapper.insertSelective(sysActJnl4Margin);
				
				//S:THD_REPAY+ 
				BsSysSubAccount thdRepayAct = bsSysSubAccountService.findSysSubAccount4Lock(partnerActCode.getRepayActCode());
				BsSysSubAccount tempThdRepayAct = new BsSysSubAccount();
				tempThdRepayAct.setId(thdRepayAct.getId());
				tempThdRepayAct.setBalance(MoneyUtil.add(thdRepayAct.getBalance(), thdRepayAmount).doubleValue());
				tempThdRepayAct.setAvailableBalance(MoneyUtil.add(thdRepayAct.getAvailableBalance(), thdRepayAmount).doubleValue());
				tempThdRepayAct.setCanWithdraw(MoneyUtil.add(thdRepayAct.getCanWithdraw(), thdRepayAmount).doubleValue());
				bsSysSubAccountMapper.updateByPrimaryKeySelective(tempThdRepayAct);
				
				//还款资金子账户入账 记账
				BsSysAccountJnl sysActJnl4ThdRepay = new BsSysAccountJnl();
				sysActJnl4ThdRepay.setTransTime(new Date());
				sysActJnl4ThdRepay.setTransCode(Constants.SYS_THD_REPAY);
				sysActJnl4ThdRepay.setTransName("还款资金子账户入账");
				sysActJnl4ThdRepay.setTransAmount(thdRepayAmount);
				sysActJnl4ThdRepay.setSysTime(new Date());
				sysActJnl4ThdRepay.setChannelTime(null);
				sysActJnl4ThdRepay.setChannelJnlNo(null);
				sysActJnl4ThdRepay.setCdFlag2(Constants.CD_FLAG_D);
				sysActJnl4ThdRepay.setSubAccountCode2(thdRepayAct.getCode());
				sysActJnl4ThdRepay.setBeforeBalance2(thdRepayAct.getBalance());
				sysActJnl4ThdRepay.setAfterBalance2(tempThdRepayAct.getBalance());
				sysActJnl4ThdRepay.setBeforeAvialableBalance2(thdRepayAct.getAvailableBalance());
				sysActJnl4ThdRepay.setAfterAvialableBalance2(tempThdRepayAct.getAvailableBalance());
				sysActJnl4ThdRepay.setBeforeFreezeBalance2(thdRepayAct.getFreezeBalance());
				sysActJnl4ThdRepay.setAfterFreezeBalance2(thdRepayAct.getFreezeBalance());
				sysActJnl4ThdRepay.setFee(0.0);
				bsSysAccountJnlMapper.insertSelective(sysActJnl4ThdRepay);
				
				//S:THD_BGW_REVENUE_ZAN+
				BsSysSubAccount thdBGWRevenueZanAct = bsSysSubAccountService.findSysSubAccount4Lock(partnerActCode.getBgwRevenueActCode());
				BsSysSubAccount tempThdBGWRevenueZanAct = new BsSysSubAccount();
				tempThdBGWRevenueZanAct.setId(thdBGWRevenueZanAct.getId());
				tempThdBGWRevenueZanAct.setBalance(MoneyUtil.add(thdBGWRevenueZanAct.getBalance(), bgwRevenueAmount).doubleValue());
				tempThdBGWRevenueZanAct.setAvailableBalance(MoneyUtil.add(thdBGWRevenueZanAct.getAvailableBalance(), bgwRevenueAmount).doubleValue());
				tempThdBGWRevenueZanAct.setCanWithdraw(MoneyUtil.add(thdBGWRevenueZanAct.getCanWithdraw(), bgwRevenueAmount).doubleValue());
				bsSysSubAccountMapper.updateByPrimaryKeySelective(tempThdBGWRevenueZanAct);
				
				//币港湾宝付体系-币港湾对赞分期营收子账户入账 记账
				BsSysAccountJnl sysActJnl4BGWRevenueZan = new BsSysAccountJnl();
				sysActJnl4BGWRevenueZan.setTransTime(new Date());
				sysActJnl4BGWRevenueZan.setTransCode(Constants.SYS_THD_BGW_REVENUE_ZAN);
				sysActJnl4BGWRevenueZan.setTransName("币港湾对赞分期营收子账户入账");
				sysActJnl4BGWRevenueZan.setTransAmount(bgwRevenueAmount);
				sysActJnl4BGWRevenueZan.setSysTime(new Date());
				sysActJnl4BGWRevenueZan.setChannelTime(null);
				sysActJnl4BGWRevenueZan.setChannelJnlNo(null);
				sysActJnl4BGWRevenueZan.setCdFlag2(Constants.CD_FLAG_D);
				sysActJnl4BGWRevenueZan.setSubAccountCode2(thdBGWRevenueZanAct.getCode());
				sysActJnl4BGWRevenueZan.setBeforeBalance2(thdBGWRevenueZanAct.getBalance());
				sysActJnl4BGWRevenueZan.setAfterBalance2(tempThdBGWRevenueZanAct.getBalance());
				sysActJnl4BGWRevenueZan.setBeforeAvialableBalance2(thdBGWRevenueZanAct.getAvailableBalance());
				sysActJnl4BGWRevenueZan.setAfterAvialableBalance2(tempThdBGWRevenueZanAct.getAvailableBalance());
				sysActJnl4BGWRevenueZan.setBeforeFreezeBalance2(thdBGWRevenueZanAct.getFreezeBalance());
				sysActJnl4BGWRevenueZan.setAfterFreezeBalance2(thdBGWRevenueZanAct.getFreezeBalance());
				sysActJnl4BGWRevenueZan.setFee(0.0);
				
				sysActJnl4BGWRevenueZan.setNote("宝付体系还款营收");
				sysActJnl4BGWRevenueZan.setOpId(repayInfo.getLnRepayScheduleId());
				
				bsSysAccountJnlMapper.insertSelective(sysActJnl4BGWRevenueZan);
				
				if(fee > 0){
					//S:FEE + 
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

	/**
	 * 逾期垫付记账
	 * S:REG_ZAN - 理财人本金
	 * S:RETURN_ZAN + (本金+用户利息)
	 * S:REVENUE_ZAN >>> REVENUE
	 * 赞分期营收 = 币港湾结算-每笔债权协议利率之和  >0 不处理,< 0 赞分期营收+
	 * 币港湾营收 = 币港湾结算-每笔债权协议利率之和 >0 币港湾营收+,<0 币港湾营收-
	 * @param repayInfo 合作方、借款人借款户编号、发生金额、本金、服务费 必输
	 */
	@Override
	@Transactional
	public void chargeOverdueAdvance(RepayInfo repayInfo) {
		// S:RETURN_ZAN + (本金+用户利息)
		logger.info("[chargeOverdueAdvance]入参：" + repayInfo.toString());
		final PartnerEnum partner = repayInfo.getPartner();
		final Double amount = repayInfo.getAmount();//(本金+用户利息)
		final Double principal = repayInfo.getPrincipal();
		final Integer loanActId = repayInfo.getLoanActId();
		final Double bgwServiceFee = repayInfo.getServiceFee();
		Double sumFinancePrincipal = repayInfo.getFinancePrincipal();
		if(partner == null || loanActId == null || amount == null || principal == null || bgwServiceFee == null || sumFinancePrincipal == null){
			throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR);
		}
		/*transactionTemplate.execute(new TransactionCallbackWithoutResult(){
			@Override
			protected void doInTransactionWithoutResult(TransactionStatus status) {*/
		
				PartnerSysActCode partnerActCode = BaseAccount.partnerActCodeMap.get(partner);
				BsSysSubAccount patRegAct = bsSysSubAccountService.findSysSubAccount4Lock(partnerActCode.getRegActCode());
				BsSysSubAccount patReturnAct = bsSysSubAccountService.findSysSubAccount4Lock(partnerActCode.getReturnActCode());

				//S:REG_ZAN - 本金
				BsSysSubAccount tempPatRegAct = new BsSysSubAccount();
				tempPatRegAct.setId(patRegAct.getId());
				tempPatRegAct.setBalance(MoneyUtil.subtract(patRegAct.getBalance(), sumFinancePrincipal).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
				tempPatRegAct.setAvailableBalance(MoneyUtil.subtract(patRegAct.getAvailableBalance(), sumFinancePrincipal).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
				tempPatRegAct.setCanWithdraw(MoneyUtil.subtract(patRegAct.getCanWithdraw(), sumFinancePrincipal).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
				bsSysSubAccountMapper.updateByPrimaryKeySelective(tempPatRegAct);

				//系统账户记账
				BsSysAccountJnl sysActJnl4Return = new BsSysAccountJnl();
				sysActJnl4Return.setTransTime(new Date());
				sysActJnl4Return.setTransCode(Constants.SYS_PAT_REG_OUT);
				sysActJnl4Return.setTransName("合作方剩余产品户逾期转出");
				sysActJnl4Return.setTransAmount(principal);
				sysActJnl4Return.setSysTime(new Date());
				sysActJnl4Return.setChannelTime(null);
				sysActJnl4Return.setChannelJnlNo(null);
				sysActJnl4Return.setCdFlag1(Constants.CD_FLAG_C);
				sysActJnl4Return.setSubAccountCode1(patRegAct.getCode());
				sysActJnl4Return.setBeforeBalance1(patRegAct.getBalance());
				sysActJnl4Return.setAfterBalance1(tempPatRegAct.getBalance());
				sysActJnl4Return.setBeforeAvialableBalance1(patRegAct.getAvailableBalance());
				sysActJnl4Return.setAfterAvialableBalance1(tempPatRegAct.getAvailableBalance());
				sysActJnl4Return.setBeforeFreezeBalance1(patRegAct.getFreezeBalance());
				sysActJnl4Return.setAfterFreezeBalance1(patRegAct.getFreezeBalance());
				sysActJnl4Return.setFee(0.0);
				bsSysAccountJnlMapper.insertSelective(sysActJnl4Return);

				// S:RETURN_ZAN + (本金+用户利息)
				BsSysSubAccount tempPatReturnAct = new BsSysSubAccount();
				tempPatReturnAct.setId(patReturnAct.getId());
				tempPatReturnAct.setBalance(MoneyUtil.add(patReturnAct.getBalance(), amount).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
				tempPatReturnAct.setAvailableBalance(MoneyUtil.add(patReturnAct.getAvailableBalance(), amount).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
				tempPatReturnAct.setCanWithdraw(MoneyUtil.add(patReturnAct.getCanWithdraw(), amount).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
				bsSysSubAccountMapper.updateByPrimaryKeySelective(tempPatReturnAct);
				
				BsSysAccountJnl sysAccountJnl = new BsSysAccountJnl();
				sysAccountJnl.setTransTime(new Date());
				sysAccountJnl.setTransCode(Constants.SYS_BGW_RETURN_ZAN_ADD);
				sysAccountJnl.setTransName("回款户增加（逾期垫付）");
				sysAccountJnl.setTransAmount(amount);
				sysAccountJnl.setSysTime(new Date());
				sysAccountJnl.setChannelTime(null);
				sysAccountJnl.setChannelJnlNo(null);
				sysAccountJnl.setCdFlag1(Constants.CD_FLAG_D);
				sysAccountJnl.setSubAccountCode1(patReturnAct.getCode());
				sysAccountJnl.setBeforeBalance1(patReturnAct.getBalance());
				sysAccountJnl.setAfterBalance1(tempPatReturnAct.getBalance());
				sysAccountJnl.setBeforeAvialableBalance1(patReturnAct.getAvailableBalance());
				sysAccountJnl.setAfterAvialableBalance1(tempPatReturnAct.getAvailableBalance());
				sysAccountJnl.setBeforeFreezeBalance1(patReturnAct.getFreezeBalance());
				sysAccountJnl.setAfterFreezeBalance1(patReturnAct.getFreezeBalance());
				sysAccountJnl.setFee(0.0);
				bsSysAccountJnlMapper.insertSelective(sysAccountJnl);

				BsSysSubAccount patRevenueAct = null;
				BsSysSubAccount tempPatRevenueAct = null;
				if(bgwServiceFee < 0){
					//S:REVENUE_ZAN - 服务费
					//服务费 入参传入
					patRevenueAct = bsSysSubAccountService.findSysSubAccount4Lock(partnerActCode.getRevenueActCode());
					tempPatRevenueAct = new BsSysSubAccount();
					tempPatRevenueAct.setId(patRevenueAct.getId());
					tempPatRevenueAct.setBalance(MoneyUtil.subtract(patRevenueAct.getBalance(), bgwServiceFee).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
					tempPatRevenueAct.setAvailableBalance(MoneyUtil.subtract(patRevenueAct.getAvailableBalance(), bgwServiceFee).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
					tempPatRevenueAct.setCanWithdraw(MoneyUtil.subtract(patRevenueAct.getCanWithdraw(), bgwServiceFee).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
					bsSysSubAccountMapper.updateByPrimaryKeySelective(tempPatRevenueAct);
				}
				//S:REVENUE_ZAN >>> REVENUE + 服务费
				BsSysSubAccount bgwRevenueAct = bsSysSubAccountService.findSysSubAccount4Lock(partnerActCode.getBgwRevenueActCode());
				BsSysSubAccount tempBgwRevenueAct = new BsSysSubAccount();
				tempBgwRevenueAct.setId(bgwRevenueAct.getId());
				tempBgwRevenueAct.setBalance(MoneyUtil.add(bgwRevenueAct.getBalance(), bgwServiceFee).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
				tempBgwRevenueAct.setAvailableBalance(MoneyUtil.add(bgwRevenueAct.getAvailableBalance(), bgwServiceFee).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
				tempBgwRevenueAct.setCanWithdraw(MoneyUtil.add(bgwRevenueAct.getCanWithdraw(), bgwServiceFee).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
				bsSysSubAccountMapper.updateByPrimaryKeySelective(tempBgwRevenueAct);
				if(bgwServiceFee < 0){
					//币港湾营收账户逾期垫付出账 记账
					BsSysAccountJnl sysActJnl4Service = new BsSysAccountJnl();
					sysActJnl4Service.setTransTime(new Date());
					sysActJnl4Service.setTransCode(Constants.SYS_BGW_REVENUE_OUT_4_COMP);
					sysActJnl4Service.setTransName("币港湾营收账户逾期垫付出账");
					sysActJnl4Service.setTransAmount(-bgwServiceFee);
					sysActJnl4Service.setSysTime(new Date());
					sysActJnl4Service.setChannelTime(null);
					sysActJnl4Service.setChannelJnlNo(null);
					sysActJnl4Service.setCdFlag1(Constants.CD_FLAG_C);
					sysActJnl4Service.setSubAccountCode1(bgwRevenueAct.getCode());
					sysActJnl4Service.setBeforeBalance1(bgwRevenueAct.getBalance());
					sysActJnl4Service.setAfterBalance1(tempBgwRevenueAct.getBalance());
					sysActJnl4Service.setBeforeAvialableBalance1(bgwRevenueAct.getAvailableBalance());
					sysActJnl4Service.setAfterAvialableBalance1(tempBgwRevenueAct.getAvailableBalance());
					sysActJnl4Service.setBeforeFreezeBalance1(bgwRevenueAct.getFreezeBalance());
					sysActJnl4Service.setAfterFreezeBalance1(bgwRevenueAct.getFreezeBalance());
					sysActJnl4Service.setCdFlag2(Constants.CD_FLAG_D);
					sysActJnl4Service.setSubAccountCode2(patRevenueAct.getCode());
					sysActJnl4Service.setBeforeBalance2(patRevenueAct.getBalance());
					sysActJnl4Service.setAfterBalance2(tempPatRevenueAct.getBalance());
					sysActJnl4Service.setBeforeAvialableBalance2(patRevenueAct.getAvailableBalance());
					sysActJnl4Service.setAfterAvialableBalance2(tempPatRevenueAct.getAvailableBalance());
					sysActJnl4Service.setBeforeFreezeBalance2(patRevenueAct.getFreezeBalance());
					sysActJnl4Service.setAfterFreezeBalance2(patRevenueAct.getFreezeBalance());
					sysActJnl4Service.setFee(0.0);
					sysActJnl4Service.setOpId(repayInfo.getLnRepayScheduleId());
					bsSysAccountJnlMapper.insertSelective(sysActJnl4Service);
				}else{
					//币港湾营收账户逾期垫付入账
					BsSysAccountJnl sysActJnl4Service = new BsSysAccountJnl();
					sysActJnl4Service.setTransTime(new Date());
					sysActJnl4Service.setTransCode(Constants.SYS_BGW_REVENUE_IN_4_COMP);
					sysActJnl4Service.setTransName("币港湾营收账户逾期垫付入账");
					sysActJnl4Service.setTransAmount(bgwServiceFee);
					sysActJnl4Service.setSysTime(new Date());
					sysActJnl4Service.setChannelTime(null);
					sysActJnl4Service.setChannelJnlNo(null);
					sysActJnl4Service.setCdFlag1(Constants.CD_FLAG_D);
					sysActJnl4Service.setSubAccountCode1(bgwRevenueAct.getCode());
					sysActJnl4Service.setBeforeBalance1(bgwRevenueAct.getBalance());
					sysActJnl4Service.setAfterBalance1(tempBgwRevenueAct.getBalance());
					sysActJnl4Service.setBeforeAvialableBalance1(bgwRevenueAct.getAvailableBalance());
					sysActJnl4Service.setAfterAvialableBalance1(tempBgwRevenueAct.getAvailableBalance());
					sysActJnl4Service.setBeforeFreezeBalance1(bgwRevenueAct.getFreezeBalance());
					sysActJnl4Service.setAfterFreezeBalance1(bgwRevenueAct.getFreezeBalance());
					sysActJnl4Service.setFee(0.0);
					sysActJnl4Service.setOpId(repayInfo.getLnRepayScheduleId());
					bsSysAccountJnlMapper.insertSelective(sysActJnl4Service);
				}
				
			/*}
		});*/
	}


	/**
	 * 逾期还款记账（赞分期和赞时贷共用）
	 * B:LOAN - 本金  （仅赞分期）
	 * S:REVENUE_ZAN + (还款总金额 - 还款资金 -保证金)
	 * S:THD_MARGIN_ZAN+ (保证金)
	 * S:THD_REPAY + 协议利率（本+息）
	 * S:FEE +
	 * @param repayInfo 合作方、借款人借款户编号、发生金额、本次本金、本次理财人利息、保证金、手续费 必输
	 */
	@Override
	@Transactional
	public void chargeOverdueRepay(RepayInfo repayInfo) {
		logger.info("[chargeOverdueRepay]入参：" + repayInfo.toString());
		final PartnerEnum partner = repayInfo.getPartner();
		final Double amount = repayInfo.getAmount();
		final Integer loanActId = repayInfo.getLoanActId();
		final Double principal = repayInfo.getPrincipal();
		final Double interest = repayInfo.getInterest();
		final Double fee = repayInfo.getFee();
		final Double bailAmount = repayInfo.getBailAmount(); //保证金
		//final Integer chargeRuleId = repayInfo.getChargeRuleId();
		if(partner == null || amount == null ||
				principal == null || interest == null || fee == null || bailAmount == null){
			throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR);
		}
		if(PartnerEnum.ZAN.getCode().equals(partner.getCode()) && loanActId == null){
			//赞分期loanActId校验
			throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR);
		}
		
		final Double prinAndInter = MoneyUtil.add(principal, interest).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
		//还款金额-本金-协议利率利息-保证金
		final Double revenueAmount = MoneyUtil.subtract(amount, MoneyUtil.add(prinAndInter, bailAmount).doubleValue()).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();
		/*transactionTemplate.execute(new TransactionCallbackWithoutResult(){
			@Override
			protected void doInTransactionWithoutResult(TransactionStatus status) {*/
			PartnerSysActCode partnerActCode = BaseAccount.partnerActCodeMap.get(partner);	
			
			if(loanActId != null){		
				LnSubAccount lnAct = lnSubAccountMapper.selectByPrimaryKey4Lock(loanActId);
				
				//B:LOAN - 本金
				LnSubAccount tempLnAct = new LnSubAccount();
				if(MoneyUtil.subtract(lnAct.getBalance(), principal).doubleValue() < 0){
					logger.info("[逾期还款记账]发生金额超限");
					throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR, "发生金额超限");
				}
				if(MoneyUtil.subtract(lnAct.getBalance(), principal).doubleValue() == 0){
					tempLnAct.setStatus(Constants.LOAN_SUBACCOUNT_STATUS_REPAIED);
				}
				tempLnAct.setId(lnAct.getId());
				tempLnAct.setBalance(MoneyUtil.subtract(lnAct.getBalance(), principal).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
				tempLnAct.setAvailableBalance(MoneyUtil.subtract(lnAct.getAvailableBalance(), principal).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
				tempLnAct.setCanWithdraw(MoneyUtil.subtract(lnAct.getCanWithdraw(), principal).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
				tempLnAct.setUpdateTime(new Date());
				lnSubAccountMapper.updateByPrimaryKeySelective(tempLnAct);
			
				//借款人账户记账
				LnAccountJnl lnAccountJnl = new LnAccountJnl();
				lnAccountJnl.setTransTime(new Date());
				lnAccountJnl.setTransCode(Constants.LN_USER_REPAY);
				lnAccountJnl.setTransName("还款");
				lnAccountJnl.setTransAmount(principal);
				lnAccountJnl.setSysTime(new Date());
				lnAccountJnl.setCdFlag1(Constants.CD_FLAG_C);
				lnAccountJnl.setUserId1(lnAct.getLnUserId());
				lnAccountJnl.setSubAccountId1(lnAct.getId());
				lnAccountJnl.setBeforeBalance1(lnAct.getBalance());
				lnAccountJnl.setAfterBalance1(tempLnAct.getBalance());
				lnAccountJnl.setBeforeAvialableBalance1(lnAct.getAvailableBalance());
				lnAccountJnl.setAfterAvialableBalance1(tempLnAct.getAvailableBalance());
				lnAccountJnl.setBeforeFreezeBalance1(lnAct.getFreezeBalance());
				lnAccountJnl.setAfterFreezeBalance1(lnAct.getFreezeBalance());
				lnAccountJnl.setFee(0.0);
				lnAccountJnlMapper.insertSelective(lnAccountJnl);
			}
				//THD_MARGIN_ZAN + 借款总额*3%/12
				//S:REVENUE_ZAN >>> MARGIN_ZAN + 保证金
				BsSysSubAccount patMarginAct = bsSysSubAccountService.findSysSubAccount4Lock(partnerActCode.getMarginActCode());
				BsSysSubAccount tempPatMarginAct = new BsSysSubAccount();
				tempPatMarginAct.setId(patMarginAct.getId());
				tempPatMarginAct.setBalance(MoneyUtil.add(patMarginAct.getBalance(), bailAmount).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
				tempPatMarginAct.setAvailableBalance(MoneyUtil.add(patMarginAct.getAvailableBalance(), bailAmount).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
				tempPatMarginAct.setCanWithdraw(MoneyUtil.add(patMarginAct.getCanWithdraw(), bailAmount).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
				bsSysSubAccountMapper.updateByPrimaryKeySelective(tempPatMarginAct);
				//保证金转入 记账
				BsSysAccountJnl sysActJnl4Margin = new BsSysAccountJnl();
				sysActJnl4Margin.setTransTime(new Date());
				sysActJnl4Margin.setTransCode("逾期还款_"+partnerActCode.getMarginActCode());
				sysActJnl4Margin.setTransName(partner.getName()+"保证金子账户入账");
				sysActJnl4Margin.setTransAmount(bailAmount);
				sysActJnl4Margin.setSysTime(new Date());
				sysActJnl4Margin.setChannelTime(null);
				sysActJnl4Margin.setChannelJnlNo(null);
				sysActJnl4Margin.setCdFlag1(Constants.CD_FLAG_D);
				sysActJnl4Margin.setSubAccountCode1(patMarginAct.getCode());
				sysActJnl4Margin.setBeforeBalance1(patMarginAct.getBalance());
				sysActJnl4Margin.setAfterBalance1(tempPatMarginAct.getBalance());
				sysActJnl4Margin.setBeforeAvialableBalance1(patMarginAct.getAvailableBalance());
				sysActJnl4Margin.setAfterAvialableBalance1(tempPatMarginAct.getAvailableBalance());
				sysActJnl4Margin.setBeforeFreezeBalance1(patMarginAct.getFreezeBalance());
				sysActJnl4Margin.setAfterFreezeBalance1(patMarginAct.getFreezeBalance());
				sysActJnl4Margin.setFee(0.0);
				bsSysAccountJnlMapper.insertSelective(sysActJnl4Margin);

				//S:REVENUE_ZAN + （还款金额-还款资金-保证金）
				BsSysSubAccount patRevenueAct = bsSysSubAccountService.findSysSubAccount4Lock(partnerActCode.getRevenueActCode());
				BsSysSubAccount tempPatRevenueAct = new BsSysSubAccount();
				tempPatRevenueAct.setId(patRevenueAct.getId());
				tempPatRevenueAct.setBalance(MoneyUtil.add(patRevenueAct.getBalance(), revenueAmount).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
				tempPatRevenueAct.setAvailableBalance(MoneyUtil.add(patRevenueAct.getAvailableBalance(), revenueAmount).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
				tempPatRevenueAct.setCanWithdraw(MoneyUtil.add(patRevenueAct.getCanWithdraw(), revenueAmount).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
				bsSysSubAccountMapper.updateByPrimaryKeySelective(tempPatRevenueAct);
				//合作方营收账户入账 记账
				BsSysAccountJnl sysActJnl4Revenue = new BsSysAccountJnl();
				sysActJnl4Revenue.setTransTime(new Date());
				sysActJnl4Revenue.setTransCode("逾期还款_"+partnerActCode.getRevenueActCode());
				sysActJnl4Revenue.setTransName(partner.getName()+"营收子账户入账");
				sysActJnl4Revenue.setTransAmount(revenueAmount);
				sysActJnl4Revenue.setSysTime(new Date());
				sysActJnl4Revenue.setChannelTime(null);
				sysActJnl4Revenue.setChannelJnlNo(null);
				sysActJnl4Revenue.setCdFlag2(Constants.CD_FLAG_D);
				sysActJnl4Revenue.setSubAccountCode2(patRevenueAct.getCode());
				sysActJnl4Revenue.setBeforeBalance2(patRevenueAct.getBalance());
				sysActJnl4Revenue.setAfterBalance2(tempPatRevenueAct.getBalance());
				sysActJnl4Revenue.setBeforeAvialableBalance2(patRevenueAct.getAvailableBalance());
				sysActJnl4Revenue.setAfterAvialableBalance2(tempPatRevenueAct.getAvailableBalance());
				sysActJnl4Revenue.setBeforeFreezeBalance2(patRevenueAct.getFreezeBalance());
				sysActJnl4Revenue.setAfterFreezeBalance2(patRevenueAct.getFreezeBalance());
				sysActJnl4Revenue.setFee(0.0);
				bsSysAccountJnlMapper.insertSelective(sysActJnl4Revenue);

				//S:THD_REPAY+
				BsSysSubAccount thdRepayAct = bsSysSubAccountService.findSysSubAccount4Lock(partnerActCode.getRepayActCode());
				BsSysSubAccount tempThdRepayAct = new BsSysSubAccount();
				tempThdRepayAct.setId(thdRepayAct.getId());
				tempThdRepayAct.setBalance(MoneyUtil.add(thdRepayAct.getBalance(), prinAndInter).doubleValue());
				tempThdRepayAct.setAvailableBalance(MoneyUtil.add(thdRepayAct.getAvailableBalance(), prinAndInter).doubleValue());
				tempThdRepayAct.setCanWithdraw(MoneyUtil.add(thdRepayAct.getCanWithdraw(), prinAndInter).doubleValue());
				bsSysSubAccountMapper.updateByPrimaryKeySelective(tempThdRepayAct);

				//还款资金子账户入账 记账
				BsSysAccountJnl sysActJnl4ThdRepay = new BsSysAccountJnl();
				sysActJnl4ThdRepay.setTransTime(new Date());
				sysActJnl4ThdRepay.setTransCode(Constants.SYS_THD_REPAY);
				sysActJnl4ThdRepay.setTransName("还款资金子账户入账");
				sysActJnl4ThdRepay.setTransAmount(prinAndInter);
				sysActJnl4ThdRepay.setSysTime(new Date());
				sysActJnl4ThdRepay.setChannelTime(null);
				sysActJnl4ThdRepay.setChannelJnlNo(null);
				sysActJnl4ThdRepay.setCdFlag2(Constants.CD_FLAG_D);
				sysActJnl4ThdRepay.setSubAccountCode2(thdRepayAct.getCode());
				sysActJnl4ThdRepay.setBeforeBalance2(thdRepayAct.getBalance());
				sysActJnl4ThdRepay.setAfterBalance2(tempThdRepayAct.getBalance());
				sysActJnl4ThdRepay.setBeforeAvialableBalance2(thdRepayAct.getAvailableBalance());
				sysActJnl4ThdRepay.setAfterAvialableBalance2(tempThdRepayAct.getAvailableBalance());
				sysActJnl4ThdRepay.setBeforeFreezeBalance2(thdRepayAct.getFreezeBalance());
				sysActJnl4ThdRepay.setAfterFreezeBalance2(thdRepayAct.getFreezeBalance());
				sysActJnl4ThdRepay.setFee(0.0);
				bsSysAccountJnlMapper.insertSelective(sysActJnl4ThdRepay);

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
				
				
				
				//记账
				//借款人借款户记账暂无
				//系统帐记账：B:LOAN - 本金，宝付体系赞分期营收户+，宝付体系保证金户+，还款资金户+
				//见上
				
			/*}
		});*/
	}

	@Override
	public void chargeNormalBadLoans(BaseAccount baseAccount) {
		logger.info("[BaseAccount]入参：" + baseAccount.toString());
		PartnerEnum partner = baseAccount.getPartner();
		Double bailAmount = MoneyUtil.defaultRound(baseAccount.getAmount()).doubleValue();
		PartnerSysActCode partnerActCode = BaseAccount.partnerActCodeMap.get(partner);
		//S:REVENUE_ZAN >>> MARGIN_ZAN + 保证金
		BsSysSubAccount patMarginAct = bsSysSubAccountService.findSysSubAccount4Lock(partnerActCode.getMarginActCode());
		BsSysSubAccount tempPatMarginAct = new BsSysSubAccount();
		tempPatMarginAct.setId(patMarginAct.getId());
		tempPatMarginAct.setBalance(MoneyUtil.add(patMarginAct.getBalance(), bailAmount).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
		tempPatMarginAct.setAvailableBalance(MoneyUtil.add(patMarginAct.getAvailableBalance(), bailAmount).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
		tempPatMarginAct.setCanWithdraw(MoneyUtil.add(patMarginAct.getCanWithdraw(), bailAmount).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
		bsSysSubAccountMapper.updateByPrimaryKeySelective(tempPatMarginAct);
		//保证金转入 记账
		BsSysAccountJnl sysActJnl4Margin = new BsSysAccountJnl();
		sysActJnl4Margin.setTransTime(new Date());
		sysActJnl4Margin.setTransCode(Constants.SYS_REVENUE_4_MARGIN);
		sysActJnl4Margin.setTransName("合作方保证金转入");
		sysActJnl4Margin.setTransAmount(bailAmount);
		sysActJnl4Margin.setSysTime(new Date());
		sysActJnl4Margin.setChannelTime(null);
		sysActJnl4Margin.setChannelJnlNo(null);
		sysActJnl4Margin.setCdFlag1(Constants.CD_FLAG_D);
		sysActJnl4Margin.setSubAccountCode1(patMarginAct.getCode());
		sysActJnl4Margin.setBeforeBalance1(patMarginAct.getBalance());
		sysActJnl4Margin.setAfterBalance1(tempPatMarginAct.getBalance());
		sysActJnl4Margin.setBeforeAvialableBalance1(patMarginAct.getAvailableBalance());
		sysActJnl4Margin.setAfterAvialableBalance1(tempPatMarginAct.getAvailableBalance());
		sysActJnl4Margin.setBeforeFreezeBalance1(patMarginAct.getFreezeBalance());
		sysActJnl4Margin.setAfterFreezeBalance1(patMarginAct.getFreezeBalance());
		sysActJnl4Margin.setFee(0.0);
		bsSysAccountJnlMapper.insertSelective(sysActJnl4Margin);
	}

	@Override
	@Transactional
	public void chargeReceiveMoney2Balance(ReceiveMoneyInfo receiveMoneyInfo) {
		// * 用户回款记账(回款到余额)
		// * F:REG_D - 本金
		// * F:DEP_JSH + (本金+用户利息)
		// * S:BGW_USER + (本金+用户利息)
		// * S:BGW_RETURN_ZAN - (本金+用户利息+币港湾营收)
		// * S:DEP_BGW_REVENUE_ZAN +
		logger.info("[chargeUserReceiveMoney]入参：" + receiveMoneyInfo.toString());
		final PartnerEnum partner = receiveMoneyInfo.getPartner();
		final Double principal = receiveMoneyInfo.getPrincipal();
		final Double interest = receiveMoneyInfo.getInterest();
		final Double amount = receiveMoneyInfo.getAmount();
		final Integer investorUserId = receiveMoneyInfo.getInvestorUserId();
		final Integer investorRegActId = receiveMoneyInfo.getInvestorRegActId();
		final Double serviceFee = receiveMoneyInfo.getServiceFee();
		final Integer lnFinancePlanId = receiveMoneyInfo.getLnFinancePlanId();
		
		if(partner == null || investorUserId == null || investorRegActId == null || 
				amount == null || principal == null || interest == null || serviceFee == null){
			throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR);
		}
		/*transactionTemplate.execute(new TransactionCallbackWithoutResult(){
			@Override
			protected void doInTransactionWithoutResult(TransactionStatus status) {*/
				BsSubAccount tempUserJsh = bsSubAccountMapper.selectSingleSubActByUserAndType(investorUserId, Constants.PRODUCT_TYPE_DEP_JSH);
				BsSubAccount jshAct = bsSubAccountMapper.selectSubAccountByIdForLock(tempUserJsh.getId());
				BsSubAccount regAct = bsSubAccountMapper.selectSubAccountByIdForLock(investorRegActId);
				// * F:DEP_JSH + (本金+用户利息)
				BsSubAccount tempJshAct = new BsSubAccount();
				tempJshAct.setId(jshAct.getId());
				tempJshAct.setBalance(MoneyUtil.add(jshAct.getBalance(), amount).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
				tempJshAct.setAvailableBalance(MoneyUtil.add(jshAct.getAvailableBalance(), amount).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
				tempJshAct.setCanWithdraw(MoneyUtil.add(jshAct.getCanWithdraw(), amount).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
				tempJshAct.setLastTransDate(new Date());
				bsSubAccountMapper.updateByPrimaryKeySelective(tempJshAct);
				// * F:REG_D - 本金
				BsSubAccount tempRegAct = new BsSubAccount();
				tempRegAct.setId(regAct.getId());
				if(MoneyUtil.subtract(regAct.getBalance(), principal).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue() < 0){
					logger.info("[回款到余额记账]发生金额超限");
					throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR, "发生金额超限");
				}
				tempRegAct.setBalance(MoneyUtil.subtract(regAct.getBalance(), principal).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
				tempRegAct.setFreezeBalance(MoneyUtil.subtract(regAct.getFreezeBalance(), principal).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
				if(MoneyUtil.subtract(regAct.getBalance(), principal).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue() == 0){
					BsSubAccountPairExample pairExample = new BsSubAccountPairExample();
					pairExample.createCriteria().andRegDAccountIdEqualTo(regAct.getId());
					List<BsSubAccountPair> subAccountPairs =  bsSubAccountPairMapper.selectByExample(pairExample);
					if(!CollectionUtils.isEmpty(subAccountPairs)){
						Integer pairAuthSubActId = subAccountPairs.get(0).getAuthAccountId();
						BsSubAccount pairAuthSubAct = bsSubAccountMapper.selectByPrimaryKey(pairAuthSubActId);
						if(pairAuthSubAct.getBalance().compareTo(0d) == 0){
							//AUTH和REG_D户余额均为0时，设置REG_D\AUTH状态为已结算
							tempRegAct.setStatus(Constants.SUBACCOUNT_STATUS_SETTLE);
							BsSubAccount pairAuthSubActTemp = new BsSubAccount();
							pairAuthSubActTemp.setId(pairAuthSubActId);
							pairAuthSubActTemp.setStatus(Constants.SUBACCOUNT_STATUS_SETTLE);
							bsSubAccountMapper.updateByPrimaryKeySelective(pairAuthSubActTemp);
						}
					}
				}
				bsSubAccountMapper.updateByPrimaryKeySelective(tempRegAct);

				//用户账记账
				BsAccountJnl accountJnl = new BsAccountJnl();
				accountJnl.setTransTime(new Date());
				accountJnl.setTransCode(Constants.USER_RETURN_2_BALANCE);
				accountJnl.setTransName("标的还款回款到余额");
				accountJnl.setTransAmount(amount);
				accountJnl.setSysTime(new Date());
				accountJnl.setChannelTime(null);
				accountJnl.setChannelJnlNo(null);
				accountJnl.setCdFlag1(Constants.CD_FLAG_C);
				accountJnl.setUserId1(investorUserId);
				accountJnl.setAccountId1(regAct.getAccountId());
				accountJnl.setSubAccountId1(regAct.getId());
				accountJnl.setSubAccountCode1(regAct.getCode());
				accountJnl.setBeforeBalance1(regAct.getBalance());
				accountJnl.setAfterBalance1(tempRegAct.getBalance());
				accountJnl.setBeforeAvialableBalance1(regAct.getAvailableBalance());
				accountJnl.setAfterAvialableBalance1(regAct.getAvailableBalance());
				accountJnl.setBeforeFreezeBalance1(regAct.getFreezeBalance());
				accountJnl.setAfterFreezeBalance1(tempRegAct.getFreezeBalance());
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
				

				BsSysSubAccount sysUserAct = bsSysSubAccountService.findSysSubAccount4Lock(Constants.SYS_ACCOUNT_BGW_USER);
				// * S:BGW_USER + (本金+用户利息)
				BsSysSubAccount tempSysUserAct = new BsSysSubAccount();
				tempSysUserAct.setId(sysUserAct.getId());
				tempSysUserAct.setBalance(MoneyUtil.add(sysUserAct.getBalance(), amount).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
				tempSysUserAct.setAvailableBalance(MoneyUtil.add(sysUserAct.getAvailableBalance(), amount).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
				tempSysUserAct.setCanWithdraw(MoneyUtil.add(sysUserAct.getCanWithdraw(), amount).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
				bsSysSubAccountMapper.updateByPrimaryKeySelective(tempSysUserAct);
				// S:BGW_RETURN_ZAN - (本金+用户利息+币港湾营收)
				PartnerSysActCode partnerActCode = BaseAccount.partnerActCodeMap.get(partner);
				BsSysSubAccount patReturnAct = bsSysSubAccountService.findSysSubAccount4Lock(partnerActCode.getReturnActCode());
				BsSysSubAccount tempPatReturnAct = new BsSysSubAccount();
				tempPatReturnAct.setId(patReturnAct.getId());
				tempPatReturnAct.setBalance(MoneyUtil.subtract(patReturnAct.getBalance(), MoneyUtil.add(amount, serviceFee).doubleValue()).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
				tempPatReturnAct.setAvailableBalance(MoneyUtil.subtract(patReturnAct.getAvailableBalance(), MoneyUtil.add(amount, serviceFee).doubleValue()).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
				tempPatReturnAct.setCanWithdraw(MoneyUtil.subtract(patReturnAct.getCanWithdraw(), MoneyUtil.add(amount, serviceFee).doubleValue()).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
				bsSysSubAccountMapper.updateByPrimaryKeySelective(tempPatReturnAct);
				Double beforeServiceFeeBalance = MoneyUtil.subtract(patReturnAct.getBalance(), amount).doubleValue();
				Double beforeServiceFeeAvailableBalance = MoneyUtil.subtract(patReturnAct.getAvailableBalance(), amount).doubleValue();
				//系统账户记账
				BsSysAccountJnl sysAccountJnl = new BsSysAccountJnl();
				sysAccountJnl.setTransTime(new Date());
				sysAccountJnl.setTransCode(Constants.SYS_USER_RETURN_2_BALANCE);
				sysAccountJnl.setTransName("标的还款回款到余额（本+息）");
				sysAccountJnl.setTransAmount(amount);
				sysAccountJnl.setSysTime(new Date());
				sysAccountJnl.setChannelTime(null);
				sysAccountJnl.setChannelJnlNo(null);
				sysAccountJnl.setCdFlag1(Constants.CD_FLAG_C);
				sysAccountJnl.setSubAccountCode1(patReturnAct.getCode());
				sysAccountJnl.setBeforeBalance1(patReturnAct.getBalance());
				sysAccountJnl.setAfterBalance1(beforeServiceFeeBalance);
				sysAccountJnl.setBeforeAvialableBalance1(patReturnAct.getAvailableBalance());
				sysAccountJnl.setAfterAvialableBalance1(beforeServiceFeeAvailableBalance);
				sysAccountJnl.setBeforeFreezeBalance1(patReturnAct.getFreezeBalance());
				sysAccountJnl.setAfterFreezeBalance1(patReturnAct.getFreezeBalance());
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
				//S:DEP_BGW_REVENUE +
				if(serviceFee > 0){
					BsSysSubAccount patBgwDepRevenueAct = bsSysSubAccountService.findSysSubAccount4Lock(partnerActCode.getBgwDepRevenueActCode());
					BsSysSubAccount tempPatBgwDepRevenueAct = new BsSysSubAccount();
					tempPatBgwDepRevenueAct.setId(patBgwDepRevenueAct.getId());
					tempPatBgwDepRevenueAct.setBalance(MoneyUtil.add(patBgwDepRevenueAct.getBalance(), serviceFee).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
					tempPatBgwDepRevenueAct.setAvailableBalance(MoneyUtil.add(patBgwDepRevenueAct.getAvailableBalance(), serviceFee).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
					tempPatBgwDepRevenueAct.setCanWithdraw(MoneyUtil.add(patBgwDepRevenueAct.getCanWithdraw(), serviceFee).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
					bsSysSubAccountMapper.updateByPrimaryKeySelective(tempPatBgwDepRevenueAct);
					//系统标的还款营收记账
					BsSysAccountJnl patBgwDepRevenueActJnl = new BsSysAccountJnl();
					patBgwDepRevenueActJnl.setTransTime(new Date());
					patBgwDepRevenueActJnl.setTransCode(Constants.SYS_REPAY_2_INVESTOR_REVENUE);
					patBgwDepRevenueActJnl.setTransName("标的还款营收");
					patBgwDepRevenueActJnl.setTransAmount(serviceFee);
					patBgwDepRevenueActJnl.setSysTime(new Date());
					patBgwDepRevenueActJnl.setChannelTime(null);
					patBgwDepRevenueActJnl.setChannelJnlNo(null);
					patBgwDepRevenueActJnl.setCdFlag1(Constants.CD_FLAG_C);
					patBgwDepRevenueActJnl.setSubAccountCode1(patReturnAct.getCode());
					patBgwDepRevenueActJnl.setBeforeBalance1(beforeServiceFeeBalance);
					patBgwDepRevenueActJnl.setAfterBalance1(tempPatReturnAct.getBalance());
					patBgwDepRevenueActJnl.setBeforeAvialableBalance1(beforeServiceFeeAvailableBalance);
					patBgwDepRevenueActJnl.setAfterAvialableBalance1(tempPatReturnAct.getAvailableBalance());
					patBgwDepRevenueActJnl.setBeforeFreezeBalance1(patReturnAct.getFreezeBalance());
					patBgwDepRevenueActJnl.setAfterFreezeBalance1(patReturnAct.getFreezeBalance());
					patBgwDepRevenueActJnl.setCdFlag2(Constants.CD_FLAG_D);
					patBgwDepRevenueActJnl.setSubAccountCode2(patBgwDepRevenueAct.getCode());
					patBgwDepRevenueActJnl.setBeforeBalance2(patBgwDepRevenueAct.getBalance());
					patBgwDepRevenueActJnl.setAfterBalance2(tempPatBgwDepRevenueAct.getBalance());
					patBgwDepRevenueActJnl.setBeforeAvialableBalance2(patBgwDepRevenueAct.getAvailableBalance());
					patBgwDepRevenueActJnl.setAfterAvialableBalance2(tempPatBgwDepRevenueAct.getAvailableBalance());
					patBgwDepRevenueActJnl.setBeforeFreezeBalance2(patBgwDepRevenueAct.getFreezeBalance());
					patBgwDepRevenueActJnl.setAfterFreezeBalance2(patBgwDepRevenueAct.getFreezeBalance());
					patBgwDepRevenueActJnl.setOpId(lnFinancePlanId);
					bsSysAccountJnlMapper.insertSelective(patBgwDepRevenueActJnl);
				}
				
			/*}
		});*/
		
	}
	/**
	 * 回款到卡记账
	 * S:RETURN_ZAN - (本金+用户利息)
	 * F:REG - 本金
	 *
	 * @param receiveMoneyInfo 合作方、理财人用户编号、理财人产品户编号、发生金额、本次本金、本次理财人利息 必输
	 */
	@Override
	@Transactional
	public void chargeReceiveMoney2Card(ReceiveMoneyInfo receiveMoneyInfo) {
		// * F:REG - 本金
		// * S:RETURN_ZAN - (本金+用户利息)
		logger.info("[chargeUserReceiveMoney]入参：" + receiveMoneyInfo.toString());
		final PartnerEnum partner = receiveMoneyInfo.getPartner();
		final Double principal = receiveMoneyInfo.getPrincipal();
		final Double interest = receiveMoneyInfo.getInterest();
		final Double amount = receiveMoneyInfo.getAmount();
		final Integer investorUserId = receiveMoneyInfo.getInvestorUserId();
		final Integer investorRegActId = receiveMoneyInfo.getInvestorRegActId();

		if(partner == null || investorUserId == null || investorRegActId == null ||
				amount == null || principal == null || interest == null){
			throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR);
		}
		/*transactionTemplate.execute(new TransactionCallbackWithoutResult(){
			@Override
			protected void doInTransactionWithoutResult(TransactionStatus status) {*/
				// * F:REG - 本金
				BsSubAccount regAct = bsSubAccountMapper.selectSubAccountByIdForLock(investorRegActId);
				BsSubAccount tempRegAct = new BsSubAccount();
				tempRegAct.setId(regAct.getId());
				if(MoneyUtil.subtract(regAct.getBalance(), principal).doubleValue() < 0){
					logger.info("[回款到卡记账]发生金额超限");
					throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR, "发生金额超限");
				}
				tempRegAct.setBalance(MoneyUtil.subtract(regAct.getBalance(), principal).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
				tempRegAct.setFreezeBalance(MoneyUtil.subtract(regAct.getFreezeBalance(), principal).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
				if(MoneyUtil.subtract(regAct.getBalance(), principal).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue() == 0){
					BsSubAccountPairExample pairExample = new BsSubAccountPairExample();
					pairExample.createCriteria().andRegDAccountIdEqualTo(regAct.getId());
					List<BsSubAccountPair> subAccountPairs =  bsSubAccountPairMapper.selectByExample(pairExample);
					if(!CollectionUtils.isEmpty(subAccountPairs)){
						Integer pairAuthSubActId = subAccountPairs.get(0).getAuthAccountId();
						BsSubAccount pairAuthSubAct = bsSubAccountMapper.selectByPrimaryKey(pairAuthSubActId);
						if(pairAuthSubAct.getBalance().compareTo(0d) == 0){
							//AUTH和REG_D户余额均为0时，设置REG_D状态为已结算
							tempRegAct.setStatus(Constants.SUBACCOUNT_STATUS_SETTLE);
						}
					}
				}
				bsSubAccountMapper.updateByPrimaryKeySelective(tempRegAct);

				//用户账记账
				BsAccountJnl accountJnl = new BsAccountJnl();
				accountJnl.setTransTime(new Date());
				accountJnl.setTransCode(Constants.USER_RETURN_2_BANK_CARD);
				accountJnl.setTransName("回款到卡");
				accountJnl.setTransAmount(principal);
				accountJnl.setSysTime(new Date());
				accountJnl.setChannelTime(null);
				accountJnl.setChannelJnlNo(null);
				accountJnl.setCdFlag1(Constants.CD_FLAG_C);
				accountJnl.setUserId1(investorUserId);
				accountJnl.setAccountId1(regAct.getAccountId());
				accountJnl.setSubAccountId1(regAct.getId());
				accountJnl.setSubAccountCode1(regAct.getCode());
				accountJnl.setBeforeBalance1(regAct.getBalance());
				accountJnl.setAfterBalance1(tempRegAct.getBalance());
				accountJnl.setBeforeAvialableBalance1(regAct.getAvailableBalance());
				accountJnl.setAfterAvialableBalance1(regAct.getAvailableBalance());
				accountJnl.setBeforeFreezeBalance1(regAct.getFreezeBalance());
				accountJnl.setAfterFreezeBalance1(tempRegAct.getFreezeBalance());
				accountJnl.setNote("利息：" + interest);
				bsAccountJnlMapper.insertSelective(accountJnl);

				// S:RETURN_ZAN - (本金+用户利息)
				PartnerSysActCode partnerActCode = BaseAccount.partnerActCodeMap.get(partner);
				BsSysSubAccount patReturnAct = bsSysSubAccountService.findSysSubAccount4Lock(partnerActCode.getReturnActCode());
				BsSysSubAccount tempPatReturnAct = new BsSysSubAccount();
				tempPatReturnAct.setId(patReturnAct.getId());
				tempPatReturnAct.setBalance(MoneyUtil.subtract(patReturnAct.getBalance(), amount).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
				tempPatReturnAct.setAvailableBalance(MoneyUtil.subtract(patReturnAct.getAvailableBalance(), amount).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
				tempPatReturnAct.setCanWithdraw(MoneyUtil.subtract(patReturnAct.getCanWithdraw(), amount).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
				bsSysSubAccountMapper.updateByPrimaryKeySelective(tempPatReturnAct);

				//系统账户记账
				BsSysAccountJnl sysAccountJnl = new BsSysAccountJnl();
				sysAccountJnl.setTransTime(new Date());
				sysAccountJnl.setTransCode(Constants.SYS_USER_RETURN_2_BALANCE);
				sysAccountJnl.setTransName("系统回款到卡（本+息）");
				sysAccountJnl.setTransAmount(amount);
				sysAccountJnl.setSysTime(new Date());
				sysAccountJnl.setChannelTime(null);
				sysAccountJnl.setChannelJnlNo(null);
				sysAccountJnl.setCdFlag1(Constants.CD_FLAG_C);
				sysAccountJnl.setSubAccountCode1(patReturnAct.getCode());
				sysAccountJnl.setBeforeBalance1(patReturnAct.getBalance());
				sysAccountJnl.setAfterBalance1(tempPatReturnAct.getBalance());
				sysAccountJnl.setBeforeAvialableBalance1(patReturnAct.getAvailableBalance());
				sysAccountJnl.setAfterAvialableBalance1(tempPatReturnAct.getAvailableBalance());
				sysAccountJnl.setBeforeFreezeBalance1(patReturnAct.getFreezeBalance());
				sysAccountJnl.setAfterFreezeBalance1(patReturnAct.getFreezeBalance());
				bsSysAccountJnlMapper.insertSelective(sysAccountJnl);

			/*}
		});*/

	}

	/**
	 * 借款人DEP_JSH-：avaliable_balance不变，freeze_balance扣减逾期还款金额
	 * 代偿人DEP_JSH+：avaliable_balance不变，freeze_balance增加逾期还款金额
	 * 系统用户余额户+：avaliable_balance不变，freeze_balance增加逾期还款金额
	 */
	@Override
	@Transactional
	public void chargeRepay2CcompensateSuccAct(Integer lnDEPJSHId,
			Integer compensateDEPJSHId, Double planTotal) {
		//借款人DEP_JSH-
		LnSubAccount lnSubAccount = lnSubAccountMapper.selectByPrimaryKey4Lock(lnDEPJSHId);
		if(lnSubAccount == null){
			logger.info("【借款人还款到代偿人】未获取到借款人DEP_JSH账户数据");
			throw new PTMessageException(PTMessageEnum.NO_DATA_FOUND);
		}
		LnSubAccount lnSubAccountTemp = new LnSubAccount();
		lnSubAccountTemp.setId(lnDEPJSHId);
		lnSubAccountTemp.setBalance(MoneyUtil.subtract(lnSubAccount.getBalance(), planTotal).doubleValue());
		lnSubAccountTemp.setFreezeBalance(MoneyUtil.subtract(lnSubAccount.getFreezeBalance(), planTotal).doubleValue());
		lnSubAccountMapper.updateByPrimaryKeySelective(lnSubAccountTemp);
		
		LnAccountJnl lnAccountJnl = new LnAccountJnl();
		
		lnAccountJnl.setTransTime(new Date());
		lnAccountJnl.setTransCode(Constants.LN_USER_REPAY);
		lnAccountJnl.setTransName("逾期还款");
		lnAccountJnl.setTransAmount(planTotal);
		lnAccountJnl.setSysTime(new Date());
		lnAccountJnl.setCdFlag1(Constants.CD_FLAG_C);
		lnAccountJnl.setUserId1(lnSubAccount.getLnUserId());
		lnAccountJnl.setSubAccountId1(lnSubAccount.getId());
		lnAccountJnl.setBeforeBalance1(lnSubAccount.getBalance());
		lnAccountJnl.setAfterBalance1(lnSubAccountTemp.getBalance());
		lnAccountJnl.setBeforeAvialableBalance1(lnSubAccount.getAvailableBalance());
		lnAccountJnl.setAfterAvialableBalance1(lnSubAccount.getAvailableBalance());
		lnAccountJnl.setBeforeFreezeBalance1(lnSubAccount.getFreezeBalance());
		lnAccountJnl.setAfterFreezeBalance1(lnSubAccountTemp.getFreezeBalance());
		lnAccountJnl.setFee(0.0);
		lnAccountJnlMapper.insertSelective(lnAccountJnl);
		
		//代偿人DEP_JSH+
		BsSubAccount bsSubAccount = bsSubAccountMapper.selectSubAccountByIdForLock(compensateDEPJSHId);
		if(bsSubAccount == null){
			logger.info("【借款人还款到代偿人】未获取到代偿人DEP_JSH账户数据");
			throw new PTMessageException(PTMessageEnum.NO_DATA_FOUND);
		}
		BsSubAccount bsSubAccountTemp = new BsSubAccount();
		bsSubAccountTemp.setId(compensateDEPJSHId);
		bsSubAccountTemp.setBalance(MoneyUtil.add(bsSubAccount.getBalance(), planTotal).doubleValue());
		bsSubAccountTemp.setFreezeBalance(MoneyUtil.add(bsSubAccount.getFreezeBalance(), planTotal).doubleValue());
		bsSubAccountMapper.updateByPrimaryKeySelective(bsSubAccountTemp);
		
		BsAccountJnl accountJnl = new BsAccountJnl();
		accountJnl.setTransTime(new Date());
		accountJnl.setTransCode(Constants.USER_COMPENSATE_DEP_JSH_ADD);
		accountJnl.setTransName("代偿人DEP_JSH增加");
		accountJnl.setTransAmount(planTotal);
		accountJnl.setSysTime(new Date());
		accountJnl.setChannelTime(null);
		accountJnl.setChannelJnlNo(null);
		accountJnl.setCdFlag1(Constants.CD_FLAG_D);
		accountJnl.setUserId1(null);
		accountJnl.setAccountId1(bsSubAccount.getAccountId());
		accountJnl.setSubAccountId1(bsSubAccount.getId());
		accountJnl.setSubAccountCode1(bsSubAccount.getCode());
		accountJnl.setBeforeBalance1(bsSubAccount.getBalance());
		accountJnl.setAfterBalance1(bsSubAccountTemp.getBalance());
		accountJnl.setBeforeAvialableBalance1(bsSubAccount.getAvailableBalance());
		accountJnl.setAfterAvialableBalance1(bsSubAccount.getAvailableBalance());
		accountJnl.setBeforeFreezeBalance1(bsSubAccount.getFreezeBalance());
		accountJnl.setAfterFreezeBalance1(bsSubAccountTemp.getFreezeBalance());
		accountJnl.setFee(0.0);
		bsAccountJnlMapper.insertSelective(accountJnl);
		
		//系统用户余额户+
		BsSysSubAccount sysUserAct = bsSysSubAccountService.findSysSubAccount4Lock(Constants.SYS_ACCOUNT_BGW_USER);
		BsSysSubAccount tempSysUserAct = new BsSysSubAccount();
		tempSysUserAct.setId(sysUserAct.getId());
		tempSysUserAct.setBalance(MoneyUtil.add(sysUserAct.getBalance(), planTotal).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
		tempSysUserAct.setFreezeBalance(MoneyUtil.add(sysUserAct.getFreezeBalance(), planTotal).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
		bsSysSubAccountMapper.updateByPrimaryKeySelective(tempSysUserAct);
		
		//系统账户记账
		BsSysAccountJnl sysAccountJnl = new BsSysAccountJnl();
		sysAccountJnl.setTransTime(new Date());
		sysAccountJnl.setTransCode(Constants.SYS_USER_RETURN_2_BALANCE);
		sysAccountJnl.setTransName("借款人还款到代偿人");
		sysAccountJnl.setTransAmount(planTotal);
		sysAccountJnl.setSysTime(new Date());
		sysAccountJnl.setChannelTime(null);
		sysAccountJnl.setChannelJnlNo(null);
		sysAccountJnl.setCdFlag1(Constants.CD_FLAG_D);
		sysAccountJnl.setSubAccountCode1(sysUserAct.getCode());
		sysAccountJnl.setBeforeBalance1(sysUserAct.getBalance());
		sysAccountJnl.setAfterBalance1(tempSysUserAct.getBalance());
		sysAccountJnl.setBeforeAvialableBalance1(sysUserAct.getAvailableBalance());
		sysAccountJnl.setAfterAvialableBalance1(sysUserAct.getAvailableBalance());
		sysAccountJnl.setBeforeFreezeBalance1(sysUserAct.getFreezeBalance());
		sysAccountJnl.setAfterFreezeBalance1(tempSysUserAct.getFreezeBalance());
		
		sysAccountJnl.setFee(0.0);
		bsSysAccountJnlMapper.insertSelective(sysAccountJnl);
		
	}

	/**
	 * 赞分期理财人提前赎回后，赞分期借款人主动还款或提前还款的分账记账
	 * B:LOAN - 本金
	 * S:REVENUE_ZAN + 赞分期营收(还款总金额 - 币港湾结算 -保证金)
	 * S:MARGIN_ZAN+ (保证金)
	 * S:THD_BGW_REVENUE_ZAN_TEMP + 币港湾营收记临时户（币港湾结算）
	 * S:FEE +
	 * @param repayInfo入参：合作方、借款人借款户编号、本次本金、手续费、赞分期营收、币港湾营收、保证金（需校验，必输）
	 */
	@Override
	@Transactional
	public void chargeNormalRepay4ZANNew(RepayInfo repayInfo) {
		logger.info("[chargeNormalRepay4ZANNew]入参：" + repayInfo.toString());
		PartnerEnum partner = repayInfo.getPartner();
		Integer loanActId = repayInfo.getLoanActId();
		Double principal = repayInfo.getPrincipal();
		Double bailAmount = repayInfo.getBailAmount();//保证金
		Double fee = repayInfo.getFee();
		Double revenueZanAmount = repayInfo.getRevenueZanAmount();
		Double bgwRevenueAmount = repayInfo.getBgwRevenueAmount();
		if(partner == null || loanActId == null || fee == null || bailAmount == null ||
				principal == null ||  revenueZanAmount == null || bgwRevenueAmount == null){
			throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR);
		}
		//B:LOAN - 本金
		LnSubAccount lnAct = lnSubAccountMapper.selectByPrimaryKey4Lock(loanActId);
		LnSubAccount tempLnAct = new LnSubAccount();
		if(MoneyUtil.subtract(lnAct.getBalance(), principal).doubleValue() < 0){
			logger.info("[正常还款记账]发生金额超限");
			throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR, "发生金额超限");
		}
		if(MoneyUtil.subtract(lnAct.getBalance(), principal).doubleValue() == 0){
			tempLnAct.setStatus(Constants.LOAN_SUBACCOUNT_STATUS_REPAIED);
		}
		tempLnAct.setId(lnAct.getId());
		tempLnAct.setBalance(MoneyUtil.subtract(lnAct.getBalance(), principal).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
		tempLnAct.setAvailableBalance(MoneyUtil.subtract(lnAct.getAvailableBalance(), principal).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
		tempLnAct.setCanWithdraw(MoneyUtil.subtract(lnAct.getCanWithdraw(), principal).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
		tempLnAct.setUpdateTime(new Date());
		lnSubAccountMapper.updateByPrimaryKeySelective(tempLnAct);
		//借款人账户记账
		LnAccountJnl lnAccountJnl = new LnAccountJnl();
		lnAccountJnl.setTransTime(new Date());
		lnAccountJnl.setTransCode(Constants.LN_USER_REPAY);
		lnAccountJnl.setTransName("还款");
		lnAccountJnl.setTransAmount(principal);
		lnAccountJnl.setSysTime(new Date());
		lnAccountJnl.setCdFlag1(Constants.CD_FLAG_C);
		lnAccountJnl.setUserId1(lnAct.getLnUserId());
		lnAccountJnl.setSubAccountId1(lnAct.getId());
		lnAccountJnl.setBeforeBalance1(lnAct.getBalance());
		lnAccountJnl.setAfterBalance1(tempLnAct.getBalance());
		lnAccountJnl.setBeforeAvialableBalance1(lnAct.getAvailableBalance());
		lnAccountJnl.setAfterAvialableBalance1(tempLnAct.getAvailableBalance());
		lnAccountJnl.setBeforeFreezeBalance1(lnAct.getFreezeBalance());
		lnAccountJnl.setAfterFreezeBalance1(lnAct.getFreezeBalance());
		lnAccountJnl.setFee(0.0);
		lnAccountJnlMapper.insertSelective(lnAccountJnl);
		PartnerSysActCode partnerActCode = BaseAccount.partnerActCodeMap.get(partner);
		//S:REVENUE_ZAN + (还款总金额 - 币港湾结算 -保证金 = revenueZanAmount)
		BsSysSubAccount patRevenueAct = bsSysSubAccountService.findSysSubAccount4Lock(partnerActCode.getRevenueActCode());
		BsSysSubAccount tempPatRevenueAct = new BsSysSubAccount();
		tempPatRevenueAct.setId(patRevenueAct.getId());
		tempPatRevenueAct.setBalance(MoneyUtil.add(patRevenueAct.getBalance(), revenueZanAmount).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
		tempPatRevenueAct.setAvailableBalance(MoneyUtil.add(patRevenueAct.getAvailableBalance(), revenueZanAmount).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
		tempPatRevenueAct.setCanWithdraw(MoneyUtil.add(patRevenueAct.getCanWithdraw(), revenueZanAmount).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
		bsSysSubAccountMapper.updateByPrimaryKeySelective(tempPatRevenueAct);
		//合作方营收账户入账 记账
		BsSysAccountJnl sysActJnl4Revenue = new BsSysAccountJnl();
		sysActJnl4Revenue.setTransTime(new Date());
		sysActJnl4Revenue.setTransCode(Constants.SYS_LOAN_2_REVENUE);
		sysActJnl4Revenue.setTransName("合作方营收账户入账");
		sysActJnl4Revenue.setTransAmount(revenueZanAmount);
		sysActJnl4Revenue.setSysTime(new Date());
		sysActJnl4Revenue.setChannelTime(null);
		sysActJnl4Revenue.setChannelJnlNo(null);
		sysActJnl4Revenue.setCdFlag2(Constants.CD_FLAG_D);
		sysActJnl4Revenue.setSubAccountCode2(patRevenueAct.getCode());
		sysActJnl4Revenue.setBeforeBalance2(patRevenueAct.getBalance());
		sysActJnl4Revenue.setAfterBalance2(tempPatRevenueAct.getBalance());
		sysActJnl4Revenue.setBeforeAvialableBalance2(patRevenueAct.getAvailableBalance());
		sysActJnl4Revenue.setAfterAvialableBalance2(tempPatRevenueAct.getAvailableBalance());
		sysActJnl4Revenue.setBeforeFreezeBalance2(patRevenueAct.getFreezeBalance());
		sysActJnl4Revenue.setAfterFreezeBalance2(patRevenueAct.getFreezeBalance());
		sysActJnl4Revenue.setFee(0.0);
		bsSysAccountJnlMapper.insertSelective(sysActJnl4Revenue);
		
		//S:REVENUE_ZAN >>> MARGIN_ZAN + 保证金
		BsSysSubAccount patMarginAct = bsSysSubAccountService.findSysSubAccount4Lock(partnerActCode.getMarginActCode());
		BsSysSubAccount tempPatMarginAct = new BsSysSubAccount();
		tempPatMarginAct.setId(patMarginAct.getId());
		tempPatMarginAct.setBalance(MoneyUtil.add(patMarginAct.getBalance(), bailAmount).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
		tempPatMarginAct.setAvailableBalance(MoneyUtil.add(patMarginAct.getAvailableBalance(), bailAmount).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
		tempPatMarginAct.setCanWithdraw(MoneyUtil.add(patMarginAct.getCanWithdraw(), bailAmount).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
		bsSysSubAccountMapper.updateByPrimaryKeySelective(tempPatMarginAct);
		//合作方营收账户保证金转入 记账
		BsSysAccountJnl sysActJnl4Margin = new BsSysAccountJnl();
		sysActJnl4Margin.setTransTime(new Date());
		sysActJnl4Margin.setTransCode(Constants.SYS_REPAY_2_MARGIN);
		sysActJnl4Margin.setTransName("合作方保证金户入账");
		sysActJnl4Margin.setTransAmount(bailAmount);
		sysActJnl4Margin.setSysTime(new Date());
		sysActJnl4Margin.setChannelTime(null);
		sysActJnl4Margin.setChannelJnlNo(null);
		sysActJnl4Margin.setCdFlag1(Constants.CD_FLAG_D);
		sysActJnl4Margin.setSubAccountCode2(patMarginAct.getCode());
		sysActJnl4Margin.setBeforeBalance2(patMarginAct.getBalance());
		sysActJnl4Margin.setAfterBalance2(tempPatMarginAct.getBalance());
		sysActJnl4Margin.setBeforeAvialableBalance2(patMarginAct.getAvailableBalance());
		sysActJnl4Margin.setAfterAvialableBalance2(tempPatMarginAct.getAvailableBalance());
		sysActJnl4Margin.setBeforeFreezeBalance2(patMarginAct.getFreezeBalance());
		sysActJnl4Margin.setAfterFreezeBalance2(patMarginAct.getFreezeBalance());
		sysActJnl4Margin.setFee(0.0);
		bsSysAccountJnlMapper.insertSelective(sysActJnl4Margin);
		
		//S:THD_BGW_REVENUE_ZAN+
		BsSysSubAccount thdBGWRevenueZanAct = bsSysSubAccountService.findSysSubAccount4Lock(Constants.SYS_ACCOUNT_THD_BGW_REVENUE_ZAN_TEMP);
		BsSysSubAccount tempThdBGWRevenueZanAct = new BsSysSubAccount();
		tempThdBGWRevenueZanAct.setId(thdBGWRevenueZanAct.getId());
		tempThdBGWRevenueZanAct.setBalance(MoneyUtil.add(thdBGWRevenueZanAct.getBalance(), bgwRevenueAmount).doubleValue());
		tempThdBGWRevenueZanAct.setAvailableBalance(MoneyUtil.add(thdBGWRevenueZanAct.getAvailableBalance(), bgwRevenueAmount).doubleValue());
		tempThdBGWRevenueZanAct.setCanWithdraw(MoneyUtil.add(thdBGWRevenueZanAct.getCanWithdraw(), bgwRevenueAmount).doubleValue());
		bsSysSubAccountMapper.updateByPrimaryKeySelective(tempThdBGWRevenueZanAct);
		
		//币港湾宝付体系-币港湾对赞分期营收子账户入账 记账
		BsSysAccountJnl sysActJnl4BGWRevenueZan = new BsSysAccountJnl();
		sysActJnl4BGWRevenueZan.setTransTime(new Date());
		sysActJnl4BGWRevenueZan.setTransCode(Constants.SYS_THD_BGW_REVENUE_ZAN);
		sysActJnl4BGWRevenueZan.setTransName("币港湾对赞分期营收子账户临时户入账");
		sysActJnl4BGWRevenueZan.setTransAmount(bgwRevenueAmount);
		sysActJnl4BGWRevenueZan.setSysTime(new Date());
		sysActJnl4BGWRevenueZan.setChannelTime(null);
		sysActJnl4BGWRevenueZan.setChannelJnlNo(null);
		sysActJnl4BGWRevenueZan.setCdFlag2(Constants.CD_FLAG_D);
		sysActJnl4BGWRevenueZan.setSubAccountCode2(thdBGWRevenueZanAct.getCode());
		sysActJnl4BGWRevenueZan.setBeforeBalance2(thdBGWRevenueZanAct.getBalance());
		sysActJnl4BGWRevenueZan.setAfterBalance2(tempThdBGWRevenueZanAct.getBalance());
		sysActJnl4BGWRevenueZan.setBeforeAvialableBalance2(thdBGWRevenueZanAct.getAvailableBalance());
		sysActJnl4BGWRevenueZan.setAfterAvialableBalance2(tempThdBGWRevenueZanAct.getAvailableBalance());
		sysActJnl4BGWRevenueZan.setBeforeFreezeBalance2(thdBGWRevenueZanAct.getFreezeBalance());
		sysActJnl4BGWRevenueZan.setAfterFreezeBalance2(thdBGWRevenueZanAct.getFreezeBalance());
		sysActJnl4BGWRevenueZan.setFee(0.0);
		
		sysActJnl4BGWRevenueZan.setNote("宝付体系还款营收-未减恒丰线下还款部分");
		sysActJnl4BGWRevenueZan.setOpId(repayInfo.getLnRepayScheduleId());
		
		bsSysAccountJnlMapper.insertSelective(sysActJnl4BGWRevenueZan);
		
		if(fee > 0){
			//S:FEE + 
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
		
		
	}

	/**
	 * 赞分期理财人提前赎回,进行恒丰前分账，系统记账
	 * S:REG_ZAN - 本金
	 * S:RETURN_ZAN + (THD_REPAY)
	 * S:THD_REPAY + 协议利率（本+息）
	 */
	@Override
	@Transactional
	public void chargeNormalRepay4ZANHF(RepayInfo repayInfo) {
		logger.info("[chargeNormalRepay4ZANHF]入参：" + repayInfo.toString());
		PartnerEnum partner = repayInfo.getPartner();
		Double principal = repayInfo.getPrincipal();
		Double interest = repayInfo.getInterest();
		Integer chargeRuleId = repayInfo.getChargeRuleId();
		Double thdRepayAmount = repayInfo.getThdRepayAmount();
		Double sumFinancePrincipal = repayInfo.getFinancePrincipal();
		if(partner == null || principal == null || interest == null || chargeRuleId == null || 
				thdRepayAmount == null || sumFinancePrincipal == null){
			throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR);
		}
		//S:REG_ZAN - 本金
		PartnerSysActCode partnerActCode = BaseAccount.partnerActCodeMap.get(partner);
		BsSysSubAccount patRegAct = bsSysSubAccountService.findSysSubAccount4Lock(partnerActCode.getRegActCode());
		BsSysSubAccount tempPatRegAct = new BsSysSubAccount();
		tempPatRegAct.setId(patRegAct.getId());
		tempPatRegAct.setBalance(MoneyUtil.subtract(patRegAct.getBalance(), sumFinancePrincipal).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
		tempPatRegAct.setAvailableBalance(MoneyUtil.subtract(patRegAct.getAvailableBalance(), sumFinancePrincipal).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
		tempPatRegAct.setCanWithdraw(MoneyUtil.subtract(patRegAct.getCanWithdraw(), sumFinancePrincipal).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
		bsSysSubAccountMapper.updateByPrimaryKeySelective(tempPatRegAct);

		//S:RETURN_ZAN + (THD_REPAY)
		BsSysSubAccount patReturnAct = bsSysSubAccountService.findSysSubAccount4Lock(partnerActCode.getReturnActCode());
		BsSysSubAccount tempPatReturnAct = new BsSysSubAccount();
		tempPatReturnAct.setId(patReturnAct.getId());
		tempPatReturnAct.setBalance(MoneyUtil.add(patReturnAct.getBalance(), thdRepayAmount).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
		tempPatReturnAct.setAvailableBalance(MoneyUtil.add(patReturnAct.getAvailableBalance(), thdRepayAmount).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
		tempPatReturnAct.setCanWithdraw(MoneyUtil.add(patReturnAct.getCanWithdraw(), thdRepayAmount).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
		bsSysSubAccountMapper.updateByPrimaryKeySelective(tempPatReturnAct);
		//合作方回款户转入 记账
		BsSysAccountJnl sysActJnl4Return = new BsSysAccountJnl();
		sysActJnl4Return.setTransTime(new Date());
		sysActJnl4Return.setTransCode(Constants.SYS_LOAN_2_RETURN);
		sysActJnl4Return.setTransName("合作方回款户转入");
		sysActJnl4Return.setTransAmount(thdRepayAmount);
		sysActJnl4Return.setSysTime(new Date());
		sysActJnl4Return.setChannelTime(null);
		sysActJnl4Return.setChannelJnlNo(null);
		sysActJnl4Return.setCdFlag1(Constants.CD_FLAG_C);
		sysActJnl4Return.setSubAccountCode1(patRegAct.getCode());
		sysActJnl4Return.setBeforeBalance1(patRegAct.getBalance());
		sysActJnl4Return.setAfterBalance1(tempPatRegAct.getBalance());
		sysActJnl4Return.setBeforeAvialableBalance1(patRegAct.getAvailableBalance());
		sysActJnl4Return.setAfterAvialableBalance1(tempPatRegAct.getAvailableBalance());
		sysActJnl4Return.setBeforeFreezeBalance1(patRegAct.getFreezeBalance());
		sysActJnl4Return.setAfterFreezeBalance1(patRegAct.getFreezeBalance());
		sysActJnl4Return.setCdFlag2(Constants.CD_FLAG_D);
		sysActJnl4Return.setSubAccountCode2(patReturnAct.getCode());
		sysActJnl4Return.setBeforeBalance2(patReturnAct.getBalance());
		sysActJnl4Return.setAfterBalance2(tempPatReturnAct.getBalance());
		sysActJnl4Return.setBeforeAvialableBalance2(patReturnAct.getAvailableBalance());
		sysActJnl4Return.setAfterAvialableBalance2(tempPatReturnAct.getAvailableBalance());
		sysActJnl4Return.setBeforeFreezeBalance2(patReturnAct.getFreezeBalance());
		sysActJnl4Return.setAfterFreezeBalance2(patReturnAct.getFreezeBalance());
		sysActJnl4Return.setFee(0.0);
		bsSysAccountJnlMapper.insertSelective(sysActJnl4Return);
				
				
				
		//S:THD_REPAY+ 
		BsSysSubAccount thdRepayAct = bsSysSubAccountService.findSysSubAccount4Lock(partnerActCode.getRepayActCode());
		BsSysSubAccount tempThdRepayAct = new BsSysSubAccount();
		tempThdRepayAct.setId(thdRepayAct.getId());
		tempThdRepayAct.setBalance(MoneyUtil.add(thdRepayAct.getBalance(), thdRepayAmount).doubleValue());
		tempThdRepayAct.setAvailableBalance(MoneyUtil.add(thdRepayAct.getAvailableBalance(), thdRepayAmount).doubleValue());
		tempThdRepayAct.setCanWithdraw(MoneyUtil.add(thdRepayAct.getCanWithdraw(), thdRepayAmount).doubleValue());
		bsSysSubAccountMapper.updateByPrimaryKeySelective(tempThdRepayAct);
		
		//还款资金子账户入账 记账
		BsSysAccountJnl sysActJnl4ThdRepay = new BsSysAccountJnl();
		sysActJnl4ThdRepay.setTransTime(new Date());
		sysActJnl4ThdRepay.setTransCode(Constants.SYS_THD_REPAY);
		sysActJnl4ThdRepay.setTransName("还款资金子账户入账");
		sysActJnl4ThdRepay.setTransAmount(thdRepayAmount);
		sysActJnl4ThdRepay.setSysTime(new Date());
		sysActJnl4ThdRepay.setChannelTime(null);
		sysActJnl4ThdRepay.setChannelJnlNo(null);
		sysActJnl4ThdRepay.setCdFlag2(Constants.CD_FLAG_D);
		sysActJnl4ThdRepay.setSubAccountCode2(thdRepayAct.getCode());
		sysActJnl4ThdRepay.setBeforeBalance2(thdRepayAct.getBalance());
		sysActJnl4ThdRepay.setAfterBalance2(tempThdRepayAct.getBalance());
		sysActJnl4ThdRepay.setBeforeAvialableBalance2(thdRepayAct.getAvailableBalance());
		sysActJnl4ThdRepay.setAfterAvialableBalance2(tempThdRepayAct.getAvailableBalance());
		sysActJnl4ThdRepay.setBeforeFreezeBalance2(thdRepayAct.getFreezeBalance());
		sysActJnl4ThdRepay.setAfterFreezeBalance2(thdRepayAct.getFreezeBalance());
		sysActJnl4ThdRepay.setFee(0.0);
		bsSysAccountJnlMapper.insertSelective(sysActJnl4ThdRepay);
		
	}
}
