package com.pinting.business.accounting.loan.service.impl;

import com.pinting.business.accounting.finance.model.SubAccountCode;
import com.pinting.business.accounting.finance.model.TransferAccountInfo;
import com.pinting.business.accounting.loan.enums.PartnerEnum;
import com.pinting.business.accounting.loan.model.BaseAccount;
import com.pinting.business.accounting.loan.model.FixedRepaySysSplitInfo;
import com.pinting.business.accounting.loan.model.PartnerSysActCode;
import com.pinting.business.accounting.loan.service.DepFixedRepayAccountService;
import com.pinting.business.accounting.service.BsSysSubAccountService;
import com.pinting.business.dao.BsAccountJnlMapper;
import com.pinting.business.dao.BsSubAccountMapper;
import com.pinting.business.dao.BsSysAccountJnlMapper;
import com.pinting.business.dao.BsSysSubAccountMapper;
import com.pinting.business.enums.PTMessageEnum;
import com.pinting.business.exception.PTMessageException;
import com.pinting.business.model.*;
import com.pinting.business.util.CalculatorUtil;
import com.pinting.business.util.Constants;
import com.pinting.core.util.MoneyUtil;
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
public class DepFixedRepayAccountServiceImpl implements
		DepFixedRepayAccountService {

    private final Logger logger = LoggerFactory.getLogger(DepFixedRepayAccountServiceImpl.class);
    
    @Autowired
    private BsSysSubAccountMapper bsSysSubAccountMapper;
    @Autowired
    private BsSysAccountJnlMapper bsSysAccountJnlMapper;
    @Autowired
    private BsSysSubAccountService bsSysSubAccountService;
    @Autowired
    private TransactionTemplate transactionTemplate;
    @Autowired
    private BsSubAccountMapper bsSubAccountMapper;
    @Autowired
    private BsAccountJnlMapper bsAccountJnlMapper;

	@Override
	public void repayRepeat2AccRecord(final LnRepeatRepayRecord repeatRepay, final Double marginAmount) {
		transactionTemplate.execute(new TransactionCallbackWithoutResult() {
			
			@Override
			protected void doInTransactionWithoutResult(TransactionStatus status) {
				
				String partnerCode = repeatRepay.getPartnerCode();
				if(partnerCode == null){
					logger.info("还款订单号=["+repeatRepay.getRepayOrderNo()+"]保证金marginAmount=["+repeatRepay.getMarginAmount()+"]repayAmount=["+repeatRepay.getRepayAmount()+"]returnAmount=["+repeatRepay.getReturnAmount());
					throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR);
				}
				
				PartnerSysActCode sysCode =BaseAccount.partnerActCodeMap.get(PartnerEnum.getEnumByCode(repeatRepay.getPartnerCode()));
				
				BsSysSubAccount sysSubAccount = bsSysSubAccountService.findSysSubAccount4Lock(sysCode.getRevenueActCode());
				String transCode = Constants.SYS_USER_REPAY_REPEAT_IN;
				if( Constants.PROPERTY_SYMBOL_ZSD.equals(repeatRepay.getPartnerCode()) ) {
					transCode = Constants.SYS_ZSD_USER_REPAY_REPEAT_IN;
				} else if( PartnerEnum.SEVEN_DAI_SELF.getCode().equals(repeatRepay.getPartnerCode()) ) {
					transCode = Constants.SYS_7_DAI_SELF_USER_REPAY_REPEAT_IN;
				}
				
				if(sysSubAccount == null){
					throw new PTMessageException(PTMessageEnum.NO_DATA_FOUND,	"{} 重复还款入营收账记账时未找到营收户记录");
				}
				//重复还款入合作方营收户,系统账户流水记录
				BsSysSubAccount tempSysSub = new BsSysSubAccount();
				tempSysSub.setId(sysSubAccount.getId());
				tempSysSub.setBalance(MoneyUtil.add(sysSubAccount.getBalance(), repeatRepay.getRepayAmount()).doubleValue());
				tempSysSub.setAvailableBalance(MoneyUtil.add(sysSubAccount.getAvailableBalance(), repeatRepay.getRepayAmount()).doubleValue());
				bsSysSubAccountMapper.updateByPrimaryKeySelective(tempSysSub);
				
				BsSysAccountJnl bsSysAccountJnl = new BsSysAccountJnl();
				bsSysAccountJnl.setBeforeBalance1(sysSubAccount.getBalance());
				bsSysAccountJnl.setAfterBalance1(MoneyUtil.add(sysSubAccount.getBalance(),repeatRepay.getRepayAmount()).doubleValue());
				bsSysAccountJnl.setBeforeAvialableBalance1(sysSubAccount.getAvailableBalance());
				bsSysAccountJnl.setAfterAvialableBalance1(MoneyUtil.add(sysSubAccount.getAvailableBalance(),repeatRepay.getRepayAmount()).doubleValue());
				bsSysAccountJnl.setBeforeFreezeBalance1(sysSubAccount.getFreezeBalance());
				bsSysAccountJnl.setAfterFreezeBalance1(sysSubAccount.getFreezeBalance());
				bsSysAccountJnl.setTransCode(transCode);
				bsSysAccountJnl.setSubAccountCode1(sysCode.getRevenueActCode());
				bsSysAccountJnl.setCdFlag1(Constants.CD_FLAG_D);
				bsSysAccountJnl.setTransAmount(repeatRepay.getRepayAmount());
				bsSysAccountJnl.setFee(0d);
				bsSysAccountJnl.setStatus(1);
				bsSysAccountJnl.setTransName("重复还款入账");
				bsSysAccountJnl.setTransTime(new Date());
				bsSysAccountJnl.setSysTime(new Date());
				bsSysAccountJnlMapper.insertSelective(bsSysAccountJnl);
			}
		});
	}

	/**
	 * 还款系统分账,系统记账
	 * S:THD_REPAY+
	 * S:THD_BGW_REVENUE_YUN+
	 * S:THD_REVENUE_YUN+(金额不为空时处理)
	 * S:FEE +
	 */
	@Override
	@Transactional
	public void repaySysSplit(FixedRepaySysSplitInfo repaySysSplitInfo) {
		logger.info("[noramlRepaySysSplit]入参：" + repaySysSplitInfo.toString());
		PartnerEnum partner = repaySysSplitInfo.getPartner();
		Double thdRepayAmount = repaySysSplitInfo.getThdRepayAmount();
		Double thdBGWRevenuePartnerAmount = repaySysSplitInfo.getThdBGWRevenueAmount();
		Double thdRevenueAmount = repaySysSplitInfo.getThdRevenueAmount();
		Double fee = repaySysSplitInfo.getFee();
		Double thdMarginAmount = repaySysSplitInfo.getThdMarginAmount();
		if(partner == null || thdRepayAmount == null || thdBGWRevenuePartnerAmount == null || fee == null){
			throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR);
		}
		PartnerSysActCode partnerActCode = BaseAccount.partnerActCodeMap.get(partner);
		
		//S:THD_REPAY+ 
		BsSysSubAccount thdRepayAct = bsSysSubAccountService.findSysSubAccount4Lock(partnerActCode.getRepayActCode());
		BsSysSubAccount tempThdRepayAct = new BsSysSubAccount();
		tempThdRepayAct.setId(thdRepayAct.getId());
		tempThdRepayAct.setBalance(MoneyUtil.add(thdRepayAct.getBalance(), thdRepayAmount).doubleValue());
		tempThdRepayAct.setAvailableBalance(MoneyUtil.add(thdRepayAct.getAvailableBalance(), thdRepayAmount).doubleValue());
		tempThdRepayAct.setCanWithdraw(MoneyUtil.add(thdRepayAct.getCanWithdraw(), thdRepayAmount).doubleValue());
		bsSysSubAccountMapper.updateByPrimaryKeySelective(tempThdRepayAct);
		//S:THD_BGW_REVENUE_YUN+
		if((PartnerEnum.YUN_DAI_SELF.getCode().equals(partner.getCode()))
				&& thdBGWRevenuePartnerAmount != null && thdBGWRevenuePartnerAmount != 0){
			//(云贷 ) &&币港湾营收金额不为null且>0
			BsSysSubAccount thdBGWRevenueYunAct = bsSysSubAccountService.findSysSubAccount4Lock(partnerActCode.getBgwRevenueActCode());
			BsSysSubAccount tempThdBGWRevenueYunAct = new BsSysSubAccount();
			tempThdBGWRevenueYunAct.setId(thdBGWRevenueYunAct.getId());
			tempThdBGWRevenueYunAct.setBalance(MoneyUtil.add(thdBGWRevenueYunAct.getBalance(), thdBGWRevenuePartnerAmount).doubleValue());
			tempThdBGWRevenueYunAct.setAvailableBalance(MoneyUtil.add(thdBGWRevenueYunAct.getAvailableBalance(), thdBGWRevenuePartnerAmount).doubleValue());
			tempThdBGWRevenueYunAct.setCanWithdraw(MoneyUtil.add(thdBGWRevenueYunAct.getCanWithdraw(), thdBGWRevenuePartnerAmount).doubleValue());
			bsSysSubAccountMapper.updateByPrimaryKeySelective(tempThdBGWRevenueYunAct);
			//币港湾宝付体系-币港湾对合作方营收子账户入账 记账
			BsSysAccountJnl sysActJnl4BGWRevenue = new BsSysAccountJnl();
			sysActJnl4BGWRevenue.setTransTime(new Date());
			sysActJnl4BGWRevenue.setTransCode(partnerActCode.getBgwRevenueActCode());
			sysActJnl4BGWRevenue.setTransName("币港湾对"+partner.getName()+"营收子账户入账");
			sysActJnl4BGWRevenue.setTransAmount(thdBGWRevenuePartnerAmount);
			sysActJnl4BGWRevenue.setSysTime(new Date());
			sysActJnl4BGWRevenue.setChannelTime(null);
			sysActJnl4BGWRevenue.setChannelJnlNo(null);
			sysActJnl4BGWRevenue.setCdFlag2(Constants.CD_FLAG_D);
			sysActJnl4BGWRevenue.setSubAccountCode2(thdBGWRevenueYunAct.getCode());
			sysActJnl4BGWRevenue.setBeforeBalance2(thdBGWRevenueYunAct.getBalance());
			sysActJnl4BGWRevenue.setAfterBalance2(tempThdBGWRevenueYunAct.getBalance());
			sysActJnl4BGWRevenue.setBeforeAvialableBalance2(thdBGWRevenueYunAct.getAvailableBalance());
			sysActJnl4BGWRevenue.setAfterAvialableBalance2(tempThdBGWRevenueYunAct.getAvailableBalance());
			sysActJnl4BGWRevenue.setBeforeFreezeBalance2(thdBGWRevenueYunAct.getFreezeBalance());
			sysActJnl4BGWRevenue.setAfterFreezeBalance2(thdBGWRevenueYunAct.getFreezeBalance());
			sysActJnl4BGWRevenue.setFee(0.0);
			sysActJnl4BGWRevenue.setNote("宝付体系还款营收");
			sysActJnl4BGWRevenue.setOpId(repaySysSplitInfo.getLnRepayScheduleId());
			bsSysAccountJnlMapper.insertSelective(sysActJnl4BGWRevenue);
		}
		
		//THD_BGW_REVENUE_ZSD+/THD_BGW_REVENUE_7+
		if((PartnerEnum.ZSD.getCode().equals(partner.getCode()) || PartnerEnum.SEVEN_DAI_SELF.getCode().equals(partner.getCode()) ) && thdBGWRevenuePartnerAmount != null ){
			//（赞时贷 || 七贷）&& 合作方营收金额不为null
			BsSysSubAccount thdBGWRevenueZSDAct = bsSysSubAccountService.findSysSubAccount4Lock(partnerActCode.getBgwRevenueActCode());
			BsSysSubAccount tempthdBGWRevenueZSDAct = new BsSysSubAccount();
			tempthdBGWRevenueZSDAct.setId(thdBGWRevenueZSDAct.getId());
			tempthdBGWRevenueZSDAct.setBalance(MoneyUtil.add(thdBGWRevenueZSDAct.getBalance(), thdBGWRevenuePartnerAmount).doubleValue());
			tempthdBGWRevenueZSDAct.setAvailableBalance(MoneyUtil.add(thdBGWRevenueZSDAct.getAvailableBalance(), thdBGWRevenuePartnerAmount).doubleValue());
			tempthdBGWRevenueZSDAct.setCanWithdraw(MoneyUtil.add(thdBGWRevenueZSDAct.getCanWithdraw(), thdBGWRevenuePartnerAmount).doubleValue());
			bsSysSubAccountMapper.updateByPrimaryKeySelective(tempthdBGWRevenueZSDAct);
			//币港湾宝付体系-币港湾对合作方营收子账户入账 记账
			BsSysAccountJnl sysActJnl4BGWRevenue = new BsSysAccountJnl();
			sysActJnl4BGWRevenue.setTransTime(new Date());
			sysActJnl4BGWRevenue.setTransCode(partnerActCode.getBgwRevenueActCode());
			sysActJnl4BGWRevenue.setTransName("币港湾对"+partner.getName()+"营收子账户入账");
			sysActJnl4BGWRevenue.setTransAmount(thdBGWRevenuePartnerAmount);
			sysActJnl4BGWRevenue.setSysTime(new Date());
			sysActJnl4BGWRevenue.setChannelTime(null);
			sysActJnl4BGWRevenue.setChannelJnlNo(null);
			sysActJnl4BGWRevenue.setCdFlag2(Constants.CD_FLAG_D);
			sysActJnl4BGWRevenue.setSubAccountCode2(thdBGWRevenueZSDAct.getCode());
			sysActJnl4BGWRevenue.setBeforeBalance2(thdBGWRevenueZSDAct.getBalance());
			sysActJnl4BGWRevenue.setAfterBalance2(tempthdBGWRevenueZSDAct.getBalance());
			sysActJnl4BGWRevenue.setBeforeAvialableBalance2(thdBGWRevenueZSDAct.getAvailableBalance());
			sysActJnl4BGWRevenue.setAfterAvialableBalance2(tempthdBGWRevenueZSDAct.getAvailableBalance());
			sysActJnl4BGWRevenue.setBeforeFreezeBalance2(thdBGWRevenueZSDAct.getFreezeBalance());
			sysActJnl4BGWRevenue.setAfterFreezeBalance2(thdBGWRevenueZSDAct.getFreezeBalance());
			sysActJnl4BGWRevenue.setFee(0.0);
			sysActJnl4BGWRevenue.setNote("宝付体系还款营收");
			sysActJnl4BGWRevenue.setOpId(repaySysSplitInfo.getLnRepayScheduleId());
			bsSysAccountJnlMapper.insertSelective(sysActJnl4BGWRevenue);
		}
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
		//S:THD_REVENUE_YUN+/THD_REVENUE_ZSD+/THD_REVENUE_SEVEN+
		if(thdRevenueAmount.compareTo(0d) != 0){
			BsSysSubAccount thdRevenueAct = bsSysSubAccountService.findSysSubAccount4Lock(partnerActCode.getRevenueActCode());
			BsSysSubAccount tempThdRevenueAct = new BsSysSubAccount();
			tempThdRevenueAct.setId(thdRevenueAct.getId());
			tempThdRevenueAct.setBalance(MoneyUtil.add(thdRevenueAct.getBalance(), thdRevenueAmount).doubleValue());
			tempThdRevenueAct.setAvailableBalance(MoneyUtil.add(thdRevenueAct.getAvailableBalance(), thdRevenueAmount).doubleValue());
			tempThdRevenueAct.setCanWithdraw(MoneyUtil.add(thdRevenueAct.getCanWithdraw(), thdRevenueAmount).doubleValue());
			bsSysSubAccountMapper.updateByPrimaryKeySelective(tempThdRevenueAct);
			
			//币港湾宝付体系-合作方营收子账户 记账
			BsSysAccountJnl sysActJnl4Revenue = new BsSysAccountJnl();
			sysActJnl4Revenue.setTransTime(new Date());
			sysActJnl4Revenue.setTransCode(partnerActCode.getRevenueActCode());
			sysActJnl4Revenue.setTransName(partner.getName()+"营收子账户入账");
			sysActJnl4Revenue.setTransAmount(thdRevenueAmount);
			sysActJnl4Revenue.setSysTime(new Date());
			sysActJnl4Revenue.setChannelTime(null);
			sysActJnl4Revenue.setChannelJnlNo(null);
			sysActJnl4Revenue.setCdFlag2(Constants.CD_FLAG_D);
			sysActJnl4Revenue.setSubAccountCode2(thdRevenueAct.getCode());
			sysActJnl4Revenue.setBeforeBalance2(thdRevenueAct.getBalance());
			sysActJnl4Revenue.setAfterBalance2(tempThdRevenueAct.getBalance());
			sysActJnl4Revenue.setBeforeAvialableBalance2(thdRevenueAct.getAvailableBalance());
			sysActJnl4Revenue.setAfterAvialableBalance2(tempThdRevenueAct.getAvailableBalance());
			sysActJnl4Revenue.setBeforeFreezeBalance2(thdRevenueAct.getFreezeBalance());
			sysActJnl4Revenue.setAfterFreezeBalance2(thdRevenueAct.getFreezeBalance());
			sysActJnl4Revenue.setFee(0.0);
			bsSysAccountJnlMapper.insertSelective(sysActJnl4Revenue);
		}
		
		//S:MARGIN + 
		if(PartnerEnum.ZSD.getCode().equals(partner.getCode()) && thdMarginAmount != null && thdMarginAmount > 0){
			BsSysSubAccount thdMarginAct = bsSysSubAccountService.findSysSubAccount4Lock(partnerActCode.getMarginActCode());
			BsSysSubAccount tempThdMarginAct = new BsSysSubAccount();
			tempThdMarginAct.setId(thdMarginAct.getId());
			tempThdMarginAct.setBalance(MoneyUtil.add(thdMarginAct.getBalance(), thdMarginAmount).doubleValue());
			tempThdMarginAct.setAvailableBalance(MoneyUtil.add(thdMarginAct.getAvailableBalance(), thdMarginAmount).doubleValue());
			tempThdMarginAct.setCanWithdraw(MoneyUtil.add(thdMarginAct.getCanWithdraw(), thdMarginAmount).doubleValue());
			bsSysSubAccountMapper.updateByPrimaryKeySelective(tempThdMarginAct);
			
			//币港湾宝付体系-合作方保证金营收子账户 记账
			BsSysAccountJnl sysActJnl4Margin = new BsSysAccountJnl();
			sysActJnl4Margin.setTransTime(new Date());
			sysActJnl4Margin.setTransCode(partnerActCode.getMarginActCode());
			sysActJnl4Margin.setTransName(partner.getName()+"保证金子账户入账");
			sysActJnl4Margin.setTransAmount(thdMarginAmount);
			sysActJnl4Margin.setSysTime(new Date());
			sysActJnl4Margin.setChannelTime(null);
			sysActJnl4Margin.setChannelJnlNo(null);
			sysActJnl4Margin.setCdFlag2(Constants.CD_FLAG_D);
			sysActJnl4Margin.setSubAccountCode2(thdMarginAct.getCode());
			sysActJnl4Margin.setBeforeBalance2(thdMarginAct.getBalance());
			sysActJnl4Margin.setAfterBalance2(tempThdMarginAct.getBalance());
			sysActJnl4Margin.setBeforeAvialableBalance2(thdMarginAct.getAvailableBalance());
			sysActJnl4Margin.setAfterAvialableBalance2(tempThdMarginAct.getAvailableBalance());
			sysActJnl4Margin.setBeforeFreezeBalance2(thdMarginAct.getFreezeBalance());
			sysActJnl4Margin.setAfterFreezeBalance2(thdMarginAct.getFreezeBalance());
			sysActJnl4Margin.setFee(0.0);
			bsSysAccountJnlMapper.insertSelective(sysActJnl4Margin);
		}
		
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
		
	}

	/**
	 * 债权转让成功记账
	 */
	@Override
	@Transactional
	public void chargeRelationTransfer(TransferAccountInfo transferInfo) {
		
		logger.info("[chargeRelationTransfer]入参：" + transferInfo.toString() );
		
		//承接人
		Integer investorAuthActId = transferInfo.getInUser_investorAuthActId();
		Integer investorRedActId = transferInfo.getInUser_investorRedActId();
		Double authAmount = transferInfo.getInUser_authAmount();
		Double redAmount = transferInfo.getInUser_redAmount();
		//出让人补差
		Integer diffActId = transferInfo.getOutUser_diffActId();
		Double diffAmount = transferInfo.getDiffFee();
		//出让人本息
		Integer authAddActId = transferInfo.getOutUser_investorAuthActId();
		Double authAdd_p_amount = transferInfo.getOutUser_principalAmount();
		Double authAdd_i_amount = transferInfo.getOutUser_interestAmount();
		
		//出让人应收的所有
		Double authAdd_sum_amount = CalculatorUtil.calculate("a+a+a",authAdd_p_amount,authAdd_i_amount,diffAmount);
		
		//系统营收
		Double fee = transferInfo.getFee();
		
		if(authAddActId == null || authAdd_p_amount == null || authAdd_i_amount == null){
			throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR);
		}
		if(diffAmount > 0 && diffActId == null){
			throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR);
		}
		if(investorAuthActId == null && investorRedActId == null){
			throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR);
		}
		if(authAmount > 0 && investorAuthActId == null){
			throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR);
		}
		if(redAmount > 0 && investorRedActId == null){
			throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR);
		}
		PartnerEnum partner = transferInfo.getPartnerEnum();
		if(fee == null || partner == null){
			throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR);
		}
		
		
		PartnerSysActCode partnerActCode = BaseAccount.partnerActCodeMap.get(partner);
		
		//出让人记账,增加本金+利息。流水分开记本金和利息
		BsSubAccount outAct = bsSubAccountMapper.selectSubAccountByIdForLock(authAddActId);
		if(outAct == null){
			logger.info("[chargeRelationTransfer]未获取到outACT账户数据");
			throw new PTMessageException(PTMessageEnum.NO_DATA_FOUND);
		}else{
			BsSubAccount tempOutAct = new BsSubAccount();
			tempOutAct.setId(outAct.getId());
			tempOutAct.setBalance(MoneyUtil.add(outAct.getBalance(), authAdd_sum_amount).doubleValue());
			tempOutAct.setAvailableBalance(MoneyUtil.add(outAct.getAvailableBalance(), authAdd_sum_amount).doubleValue());
			tempOutAct.setCanWithdraw(MoneyUtil.add(outAct.getCanWithdraw(), authAdd_sum_amount).doubleValue());
			if(outAct.getLeftPlanInterest() != null && SubAccountCode.productTypeMap.get(PartnerEnum.FREE).getAuthCode().equals(outAct.getProductType())){
				//剩余应还利息 = 原剩余应还利息-利息-补差金额
				tempOutAct.setLeftPlanInterest(CalculatorUtil.calculate("a-a-a", outAct.getLeftPlanInterest(), authAdd_i_amount, diffAmount));
			}
			bsSubAccountMapper.updateByPrimaryKeySelective(tempOutAct);
			
			BsAccountJnl accountJnlP = new BsAccountJnl();
			accountJnlP.setTransTime(new Date());
			accountJnlP.setTransCode(Constants.USER_TRANSFER_OUT_ADD);
			accountJnlP.setTransName("转让出让站岗金额增加本金");
			accountJnlP.setTransAmount(authAdd_p_amount);
			accountJnlP.setSysTime(new Date());
			accountJnlP.setChannelTime(null);
			accountJnlP.setChannelJnlNo(null);
			accountJnlP.setCdFlag1(Constants.CD_FLAG_D);
			accountJnlP.setUserId1(null);
			accountJnlP.setAccountId1(outAct.getAccountId());
			accountJnlP.setSubAccountId1(outAct.getId());
			accountJnlP.setSubAccountCode1(outAct.getCode());
			accountJnlP.setBeforeBalance1(outAct.getBalance());
			accountJnlP.setAfterBalance1(MoneyUtil.add(outAct.getBalance(), authAdd_p_amount).doubleValue());
			accountJnlP.setBeforeAvialableBalance1(outAct.getAvailableBalance());
			accountJnlP.setAfterAvialableBalance1(MoneyUtil.add(outAct.getAvailableBalance(), authAdd_p_amount).doubleValue());
			accountJnlP.setBeforeFreezeBalance1(outAct.getFreezeBalance());
			accountJnlP.setAfterFreezeBalance1(outAct.getFreezeBalance());
			accountJnlP.setFee(0.0);
			bsAccountJnlMapper.insertSelective(accountJnlP);
			
			if(authAdd_i_amount > 0){
				Double p_i_amount = MoneyUtil.add(authAdd_p_amount, authAdd_i_amount).doubleValue();
				BsAccountJnl accountJnlInt = new BsAccountJnl();
				accountJnlInt.setTransTime(new Date());
				accountJnlInt.setTransCode(Constants.USER_TRANSFER_OUT_ADD);
				accountJnlInt.setTransName("转让出让站岗金额利息增加");
				accountJnlInt.setTransAmount(authAdd_i_amount);
				accountJnlInt.setSysTime(new Date());
				accountJnlInt.setChannelTime(null);
				accountJnlInt.setChannelJnlNo(null);
				accountJnlInt.setCdFlag1(Constants.CD_FLAG_D);
				accountJnlInt.setUserId1(null);
				accountJnlInt.setAccountId1(outAct.getAccountId());
				accountJnlInt.setSubAccountId1(outAct.getId());
				accountJnlInt.setSubAccountCode1(outAct.getCode());
				accountJnlInt.setBeforeBalance1(MoneyUtil.add(outAct.getBalance(), authAdd_p_amount).doubleValue());
				accountJnlInt.setAfterBalance1(MoneyUtil.add(outAct.getBalance(), p_i_amount).doubleValue());
				accountJnlInt.setBeforeAvialableBalance1(MoneyUtil.add(outAct.getAvailableBalance(), authAdd_p_amount).doubleValue());
				accountJnlInt.setAfterAvialableBalance1(MoneyUtil.add(outAct.getAvailableBalance(), p_i_amount).doubleValue());
				accountJnlInt.setBeforeFreezeBalance1(outAct.getFreezeBalance());
				accountJnlInt.setAfterFreezeBalance1(outAct.getFreezeBalance());
				accountJnlInt.setFee(0.0);
				bsAccountJnlMapper.insertSelective(accountJnlInt);
				
				if(diffAmount > 0){
					BsAccountJnl accountJnlDiff = new BsAccountJnl();
					accountJnlDiff.setTransTime(new Date());
					accountJnlDiff.setTransCode(Constants.USER_TRANSFER_OUT_ADD);
					accountJnlDiff.setTransName("转让出让站岗金额补差增加");
					accountJnlDiff.setTransAmount(diffAmount);
					accountJnlDiff.setSysTime(new Date());
					accountJnlDiff.setChannelTime(null);
					accountJnlDiff.setChannelJnlNo(null);
					accountJnlDiff.setCdFlag1(Constants.CD_FLAG_D);
					accountJnlDiff.setUserId1(null);
					accountJnlDiff.setAccountId1(outAct.getAccountId());
					accountJnlDiff.setSubAccountId1(outAct.getId());
					accountJnlDiff.setSubAccountCode1(outAct.getCode());
					accountJnlDiff.setBeforeBalance1(MoneyUtil.add(outAct.getBalance(), p_i_amount).doubleValue());
					accountJnlDiff.setAfterBalance1(MoneyUtil.add(outAct.getBalance(), authAdd_sum_amount).doubleValue());
					accountJnlDiff.setBeforeAvialableBalance1(MoneyUtil.add(outAct.getAvailableBalance(), p_i_amount).doubleValue());
					accountJnlDiff.setAfterAvialableBalance1(MoneyUtil.add(outAct.getAvailableBalance(), authAdd_sum_amount).doubleValue());
					accountJnlDiff.setBeforeFreezeBalance1(outAct.getFreezeBalance());
					accountJnlDiff.setAfterFreezeBalance1(outAct.getFreezeBalance());
					accountJnlDiff.setFee(0.0);
					bsAccountJnlMapper.insertSelective(accountJnlDiff);
				}
			}			
		}
		//出让人补差户
		if(diffAmount > 0){
			BsSubAccount diffAct = bsSubAccountMapper.selectSubAccountByIdForLock(diffActId);
			if(diffAct == null){
				logger.info("[chargeRelationTransfer]未获取到diffAct账户数据");
				throw new PTMessageException(PTMessageEnum.NO_DATA_FOUND);
			}
			BsSubAccount tempDiffAct = new BsSubAccount();
			tempDiffAct.setId(diffAct.getId());
			tempDiffAct.setBalance(MoneyUtil.subtract(diffAct.getBalance(), diffAmount).doubleValue());
			tempDiffAct.setAvailableBalance(MoneyUtil.subtract(diffAct.getAvailableBalance(), diffAmount).doubleValue());
			tempDiffAct.setCanWithdraw(MoneyUtil.subtract(diffAct.getCanWithdraw(), diffAmount).doubleValue());
			bsSubAccountMapper.updateByPrimaryKeySelective(tempDiffAct);
			
			//债权转让出让人补差户 记账
			BsAccountJnl accountJnlRed = new BsAccountJnl();
			accountJnlRed.setTransTime(new Date());
			accountJnlRed.setTransCode(Constants.USER_DIFF_OUT);
			accountJnlRed.setTransName("转让出让补差金额减少");
			accountJnlRed.setTransAmount(diffAmount);
			accountJnlRed.setSysTime(new Date());
			accountJnlRed.setChannelTime(null);
			accountJnlRed.setChannelJnlNo(null);
			accountJnlRed.setCdFlag1(Constants.CD_FLAG_C);
			accountJnlRed.setUserId1(null);
			accountJnlRed.setAccountId1(diffAct.getAccountId());
			accountJnlRed.setSubAccountId1(diffAct.getId());
			accountJnlRed.setSubAccountCode1(diffAct.getCode());
			accountJnlRed.setBeforeBalance1(diffAct.getBalance());
			accountJnlRed.setAfterBalance1(tempDiffAct.getBalance());
			accountJnlRed.setBeforeAvialableBalance1(diffAct.getAvailableBalance());
			accountJnlRed.setAfterAvialableBalance1(tempDiffAct.getAvailableBalance());
			accountJnlRed.setBeforeFreezeBalance1(diffAct.getFreezeBalance());
			accountJnlRed.setAfterFreezeBalance1(diffAct.getFreezeBalance());
			accountJnlRed.setFee(0.0);
			bsAccountJnlMapper.insertSelective(accountJnlRed);
			
		}
		
		//承接人记账,红包户金额减少
		if(redAmount > 0){
			BsSubAccount redAct = bsSubAccountMapper.selectSubAccountByIdForLock(investorRedActId);
			if(redAct == null){
				logger.info("[chargeRelationTransfer]未获取到inRedAct账户数据");
				throw new PTMessageException(PTMessageEnum.NO_DATA_FOUND);
			}
			BsSubAccount tempRedAct = new BsSubAccount();
			tempRedAct.setId(redAct.getId());
			if(MoneyUtil.subtract(redAct.getBalance(), redAmount).doubleValue() < 0){
				throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR, "发生金额超限");
			}
			
			if(MoneyUtil.subtract(redAct.getFreezeBalance(), redAmount).doubleValue() < 0){
				throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR, "发生金额超限");
			}
			//banlance 减少
			tempRedAct.setBalance(MoneyUtil.subtract(redAct.getBalance(), redAmount).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
			//冻结余额减少
			tempRedAct.setFreezeBalance(MoneyUtil.subtract(redAct.getFreezeBalance(), redAmount).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
			bsSubAccountMapper.updateByPrimaryKeySelective(tempRedAct);
			
			//债权转让承接人用户账 记账
			BsAccountJnl accountJnlRed = new BsAccountJnl();
			accountJnlRed.setTransTime(new Date());
			accountJnlRed.setTransCode(Constants.USER_TRANSFER_IN_SUB);
			accountJnlRed.setTransName("转让承接授权红包金额减少");
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
			accountJnlRed.setAfterBalance1(tempRedAct.getBalance());
			accountJnlRed.setBeforeAvialableBalance1(redAct.getAvailableBalance());
			accountJnlRed.setAfterAvialableBalance1(redAct.getAvailableBalance());
			accountJnlRed.setBeforeFreezeBalance1(redAct.getFreezeBalance());
			accountJnlRed.setAfterFreezeBalance1(tempRedAct.getFreezeBalance());
			accountJnlRed.setFee(0.0);
			bsAccountJnlMapper.insertSelective(accountJnlRed);
			
		}
		//查询债权转让承接人站岗户
		BsSubAccount authAct = bsSubAccountMapper.selectSubAccountByIdForLock(investorAuthActId);
		if(authAmount > 0){
			
			if(authAct == null){
				logger.info("[chargeRelationTransfer]未获取到inACt账户数据");
				throw new PTMessageException(PTMessageEnum.NO_DATA_FOUND);
			}else{
				BsSubAccount tempAuthAct = new BsSubAccount();
				tempAuthAct.setId(authAct.getId());
				
				if(MoneyUtil.subtract(authAct.getBalance(), authAmount).doubleValue() < 0){
					throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR, "发生金额超限");
				}
				
				if(MoneyUtil.subtract(authAct.getFreezeBalance(), authAmount).doubleValue() < 0){
					throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR, "发生金额超限");
				}
				//banlance 减少
				tempAuthAct.setBalance(MoneyUtil.subtract(authAct.getBalance(), authAmount).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
				//冻结余额减少
				tempAuthAct.setFreezeBalance(MoneyUtil.subtract(authAct.getFreezeBalance(), authAmount).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue());
				bsSubAccountMapper.updateByPrimaryKeySelective(tempAuthAct);
				
				//债权转让承接人用户账 记账
				BsAccountJnl accountJnl = new BsAccountJnl();
				accountJnl.setTransTime(new Date());
				accountJnl.setTransCode(Constants.USER_TRANSFER_IN_SUB);
				accountJnl.setTransName("转让承接授权站岗金额减少");
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
				accountJnl.setAfterBalance1(tempAuthAct.getBalance());
				accountJnl.setBeforeAvialableBalance1(authAct.getAvailableBalance());
				accountJnl.setAfterAvialableBalance1(authAct.getAvailableBalance());
				accountJnl.setBeforeFreezeBalance1(authAct.getFreezeBalance());
				accountJnl.setAfterFreezeBalance1(tempAuthAct.getFreezeBalance());
				accountJnl.setFee(0.0);
				bsAccountJnlMapper.insertSelective(accountJnl);
			}
			
			
		}
		//系统红包户金额减少
		if(redAmount > 0){
			
			BsSysSubAccount depRed = bsSysSubAccountService.findSysSubAccount4Lock(Constants.SYS_ACCOUNT_DEP_REDPACKET);
			BsSysSubAccount tempRed = new BsSysSubAccount();
			tempRed.setId(depRed.getId());
			tempRed.setBalance(MoneyUtil.subtract(depRed.getBalance(), redAmount).doubleValue());
			tempRed.setAvailableBalance(MoneyUtil.subtract(depRed.getAvailableBalance(), redAmount).doubleValue());
			tempRed.setCanWithdraw(MoneyUtil.subtract(depRed.getCanWithdraw(), redAmount).doubleValue());
			bsSysSubAccountMapper.updateByPrimaryKeySelective(tempRed);
			
			//存管体系-红包户转站岗户 记账
			BsSysAccountJnl sysActJnl4RedOut = new BsSysAccountJnl();
			sysActJnl4RedOut.setTransTime(new Date());
			sysActJnl4RedOut.setTransCode(Constants.SYS_RED_2_AUTH_OUT);
			sysActJnl4RedOut.setTransName("转让系统红包户减少");
			sysActJnl4RedOut.setTransAmount(redAmount);
			sysActJnl4RedOut.setSysTime(new Date());
			sysActJnl4RedOut.setChannelTime(null);
			sysActJnl4RedOut.setChannelJnlNo(null);
			sysActJnl4RedOut.setCdFlag1(Constants.CD_FLAG_C);
			sysActJnl4RedOut.setSubAccountCode1(depRed.getCode());
			sysActJnl4RedOut.setBeforeBalance1(depRed.getBalance());
			sysActJnl4RedOut.setAfterBalance1(tempRed.getBalance());
			sysActJnl4RedOut.setBeforeAvialableBalance1(depRed.getAvailableBalance());
			sysActJnl4RedOut.setAfterAvialableBalance1(tempRed.getAvailableBalance());
			sysActJnl4RedOut.setBeforeFreezeBalance1(depRed.getFreezeBalance());
			sysActJnl4RedOut.setAfterFreezeBalance1(depRed.getFreezeBalance());
			sysActJnl4RedOut.setFee(0.0);

			bsSysAccountJnlMapper.insertSelective(sysActJnl4RedOut);

		}
		//获取出让人站岗户类型和受让人站岗户类型，为不同的类型时，不同的系统站岗户和产品户会产生金额变动
		String outAuthType = outAct.getProductType();
		String inAuthType = authAct.getProductType();
		PartnerSysActCode inActCode = null;
		PartnerSysActCode outActCode = null;
		if(outAuthType.equals(inAuthType)){
			//出让人和承接人的站岗户类型一致，则承接人站岗户对应的系统站岗户增加红包金额，减少营收金额；
			if(SubAccountCode.productTypeMap.get(PartnerEnum.FREE).getAuthCode().equals(inAuthType)){
				inActCode = BaseAccount.partnerActCodeMap.get(PartnerEnum.FREE);
			}else if(SubAccountCode.productTypeMap.get(PartnerEnum.YUN_DAI_SELF).getAuthCode().equals(inAuthType)){
				inActCode = BaseAccount.partnerActCodeMap.get(PartnerEnum.YUN_DAI_SELF);
			}else if(SubAccountCode.productTypeMap.get(PartnerEnum.SEVEN_DAI_SELF).getAuthCode().equals(inAuthType)){
				inActCode = BaseAccount.partnerActCodeMap.get(PartnerEnum.SEVEN_DAI_SELF);
			}
			//转让承接人子账户对应系统户：站岗户增加红包金额，减少营收金额
			inActChange(inActCode,redAmount,fee,null,null);
		}else{
			boolean inIsFirst = true;
			if(SubAccountCode.productTypeMap.get(PartnerEnum.FREE).getAuthCode().equals(inAuthType)){
				inActCode = BaseAccount.partnerActCodeMap.get(PartnerEnum.FREE);
				inIsFirst = false;
			}else if(SubAccountCode.productTypeMap.get(PartnerEnum.YUN_DAI_SELF).getAuthCode().equals(inAuthType)){
				inActCode = BaseAccount.partnerActCodeMap.get(PartnerEnum.YUN_DAI_SELF);
			}else if(SubAccountCode.productTypeMap.get(PartnerEnum.SEVEN_DAI_SELF).getAuthCode().equals(inAuthType)){
				inActCode = BaseAccount.partnerActCodeMap.get(PartnerEnum.SEVEN_DAI_SELF);
			}
			if(SubAccountCode.productTypeMap.get(PartnerEnum.FREE).getAuthCode().equals(outAuthType)){
				outActCode = BaseAccount.partnerActCodeMap.get(PartnerEnum.FREE);
			}else if(SubAccountCode.productTypeMap.get(PartnerEnum.YUN_DAI_SELF).getAuthCode().equals(outAuthType)){
				outActCode = BaseAccount.partnerActCodeMap.get(PartnerEnum.YUN_DAI_SELF);
			}else if(SubAccountCode.productTypeMap.get(PartnerEnum.SEVEN_DAI_SELF).getAuthCode().equals(outAuthType)){
				outActCode = BaseAccount.partnerActCodeMap.get(PartnerEnum.SEVEN_DAI_SELF);
			}
			
			if(inIsFirst){
				//承接人的站岗户为非自由子账户，则优先操作承接人相关的系统户操作：云贷或七贷系统站岗户增加红包金额，减少承接人站岗户减少金额（e）
				inActChange(inActCode,redAmount,null,MoneyUtil.add(authAmount, redAmount).doubleValue(),authAdd_p_amount);
				outActChange(outActCode,authAdd_sum_amount,authAdd_p_amount);
			}else{
				//承接人的站岗户为自由子账户，则优先操作出让人相关的系统户操作：云贷/七贷的系统站岗户增加金额为理财人站岗户增加总金额，流水记出让收入本息
				outActChange(outActCode,authAdd_sum_amount,authAdd_p_amount);
				inActChange(inActCode,redAmount,null,MoneyUtil.add(authAmount, redAmount).doubleValue(),authAdd_p_amount);
			}
			
		}
		
		//系统营收
		if(fee.compareTo(0d) != 0){			
			
			//营收户
			BsSysSubAccount depRevenueAct = bsSysSubAccountService.findSysSubAccount4Lock(partnerActCode.getBgwDepRevenueActCode());
			BsSysSubAccount tempRevRevenueAct = new BsSysSubAccount();
			tempRevRevenueAct.setId(depRevenueAct.getId());
			tempRevRevenueAct.setBalance(MoneyUtil.add(depRevenueAct.getBalance(), fee).doubleValue());
			tempRevRevenueAct.setAvailableBalance(MoneyUtil.add(depRevenueAct.getAvailableBalance(), fee).doubleValue());
			tempRevRevenueAct.setCanWithdraw(MoneyUtil.add(depRevenueAct.getCanWithdraw(), fee).doubleValue());
			bsSysSubAccountMapper.updateByPrimaryKeySelective(tempRevRevenueAct);
			
			//存管体系-币港湾对云贷营收子账户 记账
			BsSysAccountJnl sysActJnl4Revenue = new BsSysAccountJnl();
			sysActJnl4Revenue.setTransTime(new Date());
			sysActJnl4Revenue.setTransCode(partnerActCode.getBgwDepRevenueActCode());
			sysActJnl4Revenue.setTransName("币港湾对"+partner.getName()+"营收子账户入账(转让手续费)");
			sysActJnl4Revenue.setTransAmount(fee);
			sysActJnl4Revenue.setSysTime(new Date());
			sysActJnl4Revenue.setChannelTime(null);
			sysActJnl4Revenue.setChannelJnlNo(null);
			sysActJnl4Revenue.setCdFlag1(Constants.CD_FLAG_D);
			sysActJnl4Revenue.setSubAccountCode1(depRevenueAct.getCode());
			sysActJnl4Revenue.setBeforeBalance1(depRevenueAct.getBalance());
			sysActJnl4Revenue.setAfterBalance1(tempRevRevenueAct.getBalance());
			sysActJnl4Revenue.setBeforeAvialableBalance1(depRevenueAct.getAvailableBalance());
			sysActJnl4Revenue.setAfterAvialableBalance1(tempRevRevenueAct.getAvailableBalance());
			sysActJnl4Revenue.setBeforeFreezeBalance1(depRevenueAct.getFreezeBalance());
			sysActJnl4Revenue.setAfterFreezeBalance1(depRevenueAct.getFreezeBalance());
			sysActJnl4Revenue.setFee(0.0);
			sysActJnl4Revenue.setNote("债权转让营收增加");
			sysActJnl4Revenue.setOpId(transferInfo.getOut_relationId());
			bsSysAccountJnlMapper.insertSelective(sysActJnl4Revenue);
		}
	}

	/**
	 * 出让方，对应系统站岗户增加应回本息总和，系统产品户减少债转本金
	 * @author bianyatian
	 * @param outActCode 出让方对应子账户
	 * @param authAdd_sum_amount 出让方站岗户增加总额
	 * @param principal 债转本金
	 */
	private void outActChange(PartnerSysActCode outActCode,
			Double authAdd_sum_amount,Double principal) {
		
		BsSysSubAccount auth_out = bsSysSubAccountService.findSysSubAccount4Lock(outActCode.getAuthActCode());
		BsSysSubAccount temp_auth_out = new BsSysSubAccount();
		temp_auth_out.setId(auth_out.getId());
		temp_auth_out.setBalance(MoneyUtil.add(auth_out.getBalance(), authAdd_sum_amount).doubleValue());
		temp_auth_out.setAvailableBalance(MoneyUtil.add(auth_out.getAvailableBalance(), authAdd_sum_amount).doubleValue());
		temp_auth_out.setCanWithdraw(MoneyUtil.add(auth_out.getCanWithdraw(), authAdd_sum_amount).doubleValue());
		
		BsSysSubAccount depRegAct = bsSysSubAccountService.findSysSubAccount4Lock(outActCode.getRegActCode());
		BsSysSubAccount tempRegAct = new BsSysSubAccount();
		tempRegAct.setId(depRegAct.getId());
		tempRegAct.setBalance(MoneyUtil.subtract(depRegAct.getBalance(), principal).doubleValue());
		tempRegAct.setAvailableBalance(MoneyUtil.subtract(depRegAct.getAvailableBalance(), principal).doubleValue());
		tempRegAct.setCanWithdraw(MoneyUtil.subtract(depRegAct.getCanWithdraw(), principal).doubleValue());
		
		bsSysSubAccountMapper.updateByPrimaryKeySelective(temp_auth_out);
		BsSysAccountJnl sysActJnl4AuthIn = new BsSysAccountJnl();
		sysActJnl4AuthIn.setTransTime(new Date());
		sysActJnl4AuthIn.setTransCode(Constants.SYS_TRS_AUTH_IN);
		sysActJnl4AuthIn.setTransName("债转-系统站岗户转入");
		sysActJnl4AuthIn.setTransAmount(authAdd_sum_amount);
		sysActJnl4AuthIn.setSysTime(new Date());
		sysActJnl4AuthIn.setChannelTime(null);
		sysActJnl4AuthIn.setChannelJnlNo(null);
		sysActJnl4AuthIn.setCdFlag1(Constants.CD_FLAG_D);
		sysActJnl4AuthIn.setSubAccountCode1(auth_out.getCode());
		sysActJnl4AuthIn.setBeforeBalance1(auth_out.getBalance());
		sysActJnl4AuthIn.setAfterBalance1(temp_auth_out.getBalance());
		sysActJnl4AuthIn.setBeforeAvialableBalance1(auth_out.getAvailableBalance());
		sysActJnl4AuthIn.setAfterAvialableBalance1(temp_auth_out.getAvailableBalance());
		sysActJnl4AuthIn.setBeforeFreezeBalance1(auth_out.getFreezeBalance());
		sysActJnl4AuthIn.setAfterFreezeBalance1(auth_out.getFreezeBalance());
		sysActJnl4AuthIn.setCdFlag2(Constants.CD_FLAG_C);
		sysActJnl4AuthIn.setSubAccountCode2(depRegAct.getCode());
		sysActJnl4AuthIn.setBeforeBalance2(depRegAct.getBalance());
		sysActJnl4AuthIn.setAfterBalance2(tempRegAct.getBalance());
		sysActJnl4AuthIn.setBeforeAvialableBalance2(depRegAct.getAvailableBalance());
		sysActJnl4AuthIn.setAfterAvialableBalance2(tempRegAct.getAvailableBalance());
		sysActJnl4AuthIn.setBeforeFreezeBalance2(depRegAct.getFreezeBalance());
		sysActJnl4AuthIn.setAfterFreezeBalance2(depRegAct.getFreezeBalance());
		sysActJnl4AuthIn.setFee(0.0);
		sysActJnl4AuthIn.setNote("转让出让人对应系统站岗户增加");

		bsSysAccountJnlMapper.insertSelective(sysActJnl4AuthIn);
	}

	/**
	 * 转让受让人，对应系统站岗户增加红包金额，减少营收金额或减少所有站岗户支出金额，产品户增加本金
	 * @author bianyatian
	 * @param inActCode 承接方对应子账户
	 * @param redAmount 红包使用金额
	 * @param fee 手续费金额
	 * @param outPayAmount 站岗户减少总额
	 * @param principal债转本金
	 */
	private void inActChange(PartnerSysActCode inActCode, Double redAmount,
			Double fee, Double outPayAmount, Double principal) {
		if(redAmount > 0){
			BsSysSubAccount auth_in = bsSysSubAccountService.findSysSubAccount4Lock(inActCode.getAuthActCode());
			BsSysSubAccount temp_auth_in = new BsSysSubAccount();
			temp_auth_in.setId(auth_in.getId());
			temp_auth_in.setBalance(MoneyUtil.add(auth_in.getBalance(), redAmount).doubleValue());
			temp_auth_in.setAvailableBalance(MoneyUtil.add(auth_in.getAvailableBalance(), redAmount).doubleValue());
			temp_auth_in.setCanWithdraw(MoneyUtil.add(auth_in.getCanWithdraw(), redAmount).doubleValue());
			bsSysSubAccountMapper.updateByPrimaryKeySelective(temp_auth_in);
			
			BsSysAccountJnl sysActJnl4RedOut = new BsSysAccountJnl();
			sysActJnl4RedOut.setTransTime(new Date());
			sysActJnl4RedOut.setTransCode(Constants.SYS_RED_2_AUTH_IN);
			sysActJnl4RedOut.setTransName("转让站岗户增加红包金额");
			sysActJnl4RedOut.setTransAmount(redAmount);
			sysActJnl4RedOut.setSysTime(new Date());
			sysActJnl4RedOut.setChannelTime(null);
			sysActJnl4RedOut.setChannelJnlNo(null);
			sysActJnl4RedOut.setCdFlag1(Constants.CD_FLAG_D);
			sysActJnl4RedOut.setSubAccountCode1(auth_in.getCode());
			sysActJnl4RedOut.setBeforeBalance1(auth_in.getBalance());
			sysActJnl4RedOut.setAfterBalance1(temp_auth_in.getBalance());
			sysActJnl4RedOut.setBeforeAvialableBalance1(auth_in.getAvailableBalance());
			sysActJnl4RedOut.setAfterAvialableBalance1(temp_auth_in.getAvailableBalance());
			sysActJnl4RedOut.setBeforeFreezeBalance1(auth_in.getFreezeBalance());
			sysActJnl4RedOut.setAfterFreezeBalance1(auth_in.getFreezeBalance());
			sysActJnl4RedOut.setFee(0.0);

			bsSysAccountJnlMapper.insertSelective(sysActJnl4RedOut);
		}
		if(fee != null && fee.compareTo(0d) != 0){
			BsSysSubAccount depAuthAct = bsSysSubAccountService.findSysSubAccount4Lock(inActCode.getAuthActCode());
			BsSysSubAccount tempAuthAct = new BsSysSubAccount();
			tempAuthAct.setId(depAuthAct.getId());
			tempAuthAct.setBalance(MoneyUtil.subtract(depAuthAct.getBalance(), fee).doubleValue());
			tempAuthAct.setAvailableBalance(MoneyUtil.subtract(depAuthAct.getAvailableBalance(), fee).doubleValue());
			tempAuthAct.setCanWithdraw(MoneyUtil.subtract(depAuthAct.getCanWithdraw(), fee).doubleValue());
			bsSysSubAccountMapper.updateByPrimaryKeySelective(tempAuthAct);
			BsSysAccountJnl sysActJnl4Revenue = new BsSysAccountJnl();
			sysActJnl4Revenue.setTransTime(new Date());
			sysActJnl4Revenue.setTransCode(inActCode.getAuthActCode());
			sysActJnl4Revenue.setTransName("转让手续费从站岗户转出");
			sysActJnl4Revenue.setTransAmount(fee);
			sysActJnl4Revenue.setSysTime(new Date());
			sysActJnl4Revenue.setChannelTime(null);
			sysActJnl4Revenue.setChannelJnlNo(null);
			sysActJnl4Revenue.setCdFlag1(Constants.CD_FLAG_C);
			sysActJnl4Revenue.setSubAccountCode1(depAuthAct.getCode());
			sysActJnl4Revenue.setBeforeBalance1(depAuthAct.getBalance());
			sysActJnl4Revenue.setAfterBalance1(tempAuthAct.getBalance());
			sysActJnl4Revenue.setBeforeAvialableBalance1(depAuthAct.getAvailableBalance());
			sysActJnl4Revenue.setAfterAvialableBalance1(tempAuthAct.getAvailableBalance());
			sysActJnl4Revenue.setBeforeFreezeBalance1(depAuthAct.getFreezeBalance());
			sysActJnl4Revenue.setAfterFreezeBalance1(depAuthAct.getFreezeBalance());
			sysActJnl4Revenue.setFee(0.0);
			sysActJnl4Revenue.setNote("债权转让营收从站岗户转出");
			bsSysAccountJnlMapper.insertSelective(sysActJnl4Revenue);
			
		}
		
		if(outPayAmount != null && outPayAmount>0){
			BsSysSubAccount depAuthAct = bsSysSubAccountService.findSysSubAccount4Lock(inActCode.getAuthActCode());
			BsSysSubAccount tempAuthAct = new BsSysSubAccount();
			tempAuthAct.setId(depAuthAct.getId());
			tempAuthAct.setBalance(MoneyUtil.subtract(depAuthAct.getBalance(), outPayAmount).doubleValue());
			tempAuthAct.setAvailableBalance(MoneyUtil.subtract(depAuthAct.getAvailableBalance(), outPayAmount).doubleValue());
			tempAuthAct.setCanWithdraw(MoneyUtil.subtract(depAuthAct.getCanWithdraw(), outPayAmount).doubleValue());
			
			BsSysSubAccount depRegAct = bsSysSubAccountService.findSysSubAccount4Lock(inActCode.getRegActCode());
			BsSysSubAccount tempRegAct = new BsSysSubAccount();
			tempRegAct.setId(depRegAct.getId());
			tempRegAct.setBalance(MoneyUtil.add(depRegAct.getBalance(), principal).doubleValue());
			tempRegAct.setAvailableBalance(MoneyUtil.add(depRegAct.getAvailableBalance(), principal).doubleValue());
			tempRegAct.setCanWithdraw(MoneyUtil.add(depRegAct.getCanWithdraw(), principal).doubleValue());
			
			bsSysSubAccountMapper.updateByPrimaryKeySelective(tempAuthAct);
			BsSysAccountJnl sysActJnl4AuthOut = new BsSysAccountJnl();
			sysActJnl4AuthOut.setTransTime(new Date());
			sysActJnl4AuthOut.setTransCode(Constants.SYS_TRS_AUTH_OUT);
			sysActJnl4AuthOut.setTransName("债转-系统站岗户转出");
			sysActJnl4AuthOut.setTransAmount(outPayAmount);
			sysActJnl4AuthOut.setSysTime(new Date());
			sysActJnl4AuthOut.setChannelTime(null);
			sysActJnl4AuthOut.setChannelJnlNo(null);
			sysActJnl4AuthOut.setCdFlag1(Constants.CD_FLAG_C);
			sysActJnl4AuthOut.setSubAccountCode1(depAuthAct.getCode());
			sysActJnl4AuthOut.setBeforeBalance1(depAuthAct.getBalance());
			sysActJnl4AuthOut.setAfterBalance1(tempAuthAct.getBalance());
			sysActJnl4AuthOut.setBeforeAvialableBalance1(depAuthAct.getAvailableBalance());
			sysActJnl4AuthOut.setAfterAvialableBalance1(tempAuthAct.getAvailableBalance());
			sysActJnl4AuthOut.setBeforeFreezeBalance1(depAuthAct.getFreezeBalance());
			sysActJnl4AuthOut.setAfterFreezeBalance1(depAuthAct.getFreezeBalance());
			sysActJnl4AuthOut.setCdFlag2(Constants.CD_FLAG_D);
			sysActJnl4AuthOut.setSubAccountCode2(depRegAct.getCode());
			sysActJnl4AuthOut.setBeforeBalance2(depRegAct.getBalance());
			sysActJnl4AuthOut.setAfterBalance2(tempRegAct.getBalance());
			sysActJnl4AuthOut.setBeforeAvialableBalance2(depRegAct.getAvailableBalance());
			sysActJnl4AuthOut.setAfterAvialableBalance2(tempRegAct.getAvailableBalance());
			sysActJnl4AuthOut.setBeforeFreezeBalance2(depRegAct.getFreezeBalance());
			sysActJnl4AuthOut.setAfterFreezeBalance2(depRegAct.getFreezeBalance());
			sysActJnl4AuthOut.setFee(0.0);
			sysActJnl4AuthOut.setNote("转让承接人对应系统站岗户减少");
			bsSysAccountJnlMapper.insertSelective(sysActJnl4AuthOut);
		}
		
	}

}
