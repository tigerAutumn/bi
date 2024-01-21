package com.pinting.schedule.mongodb.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.pinting.business.accounting.loan.enums.LoanStatus;
import com.pinting.business.accounting.loan.enums.PartnerEnum;
import com.pinting.business.dao.BsPaymentChannelMapper;
import com.pinting.business.dao.BsSysDailyCheckGachaMapper;
import com.pinting.business.enums.BaoFooCheckBalanceTypeEnum;
import com.pinting.business.enums.HFBankCheckBalanceTypeEnum;
import com.pinting.business.enums.PayPlatformEnum;
import com.pinting.business.model.BsPaymentChannel;
import com.pinting.business.model.BsPaymentChannelExample;
import com.pinting.business.model.BsSysDailyCheckGacha;
import com.pinting.business.util.Constants;
import com.pinting.business.util.DateUtil;
import com.pinting.core.util.MoneyUtil;
import com.pinting.schedule.mongodb.dao.impl.MongoStatisticsDaoImpl;
import com.pinting.schedule.mongodb.model.LnPayOrders;
import com.pinting.schedule.mongodb.model.LnRepay;
import com.pinting.schedule.mongodb.service.MongoStatisticsService;

@Service
public class MongoStatisticsServiceImpl implements MongoStatisticsService {
	
	@Autowired
	private MongoStatisticsDaoImpl statisticsDaoImpl;
	
	@Autowired
    private BsSysDailyCheckGachaMapper bsSysDailyCheckGachaMapper;
	@Autowired
	private BsPaymentChannelMapper paymentChannelMapper;
	@Override
	public void generateMainCheckGacha() {
		BsPaymentChannelExample example = new BsPaymentChannelExample();
		example.createCriteria().andIsMainEqualTo(1).andPriorityEqualTo(2);
		List<BsPaymentChannel> paymentChannel = paymentChannelMapper.selectByExample(example);
		String merchantNo = paymentChannel.get(0).getMerchantNo();
		// 当前商户号 已生成过指定日期的对账结果 ，不再生成
    	String checkDate = DateUtil.getDate(DateUtils.addDays(new Date(), -1));
    	List<BsSysDailyCheckGacha> list = bsSysDailyCheckGachaMapper.selectSysDailyCheckGacha(merchantNo, checkDate);
    	if ( CollectionUtils.isEmpty(list) ) {
    		BsSysDailyCheckGacha sysDailyCheckGacha = fillSysDailyCheckGacha(Constants.CHANNEL_BAOFOO, merchantNo);
    		
    		Double zanOfflineRepayAmt = 0d;
    		Integer zanOfflineRepayCount = 0;
    		Double yunOfflineRepayAmt = 0d;
    		Integer yunOfflineRepayCount = 0;
    		Double sevenOfflineRepayAmt = 0d;
    		Integer sevenOfflineRepayCount=0;
    		Double zsdOfflineRepayAmt = 0d;
    		Integer zsdOfflineRepayCount =0;
        	// 余额提现 
    		JSONArray sumSummary = statisticsDaoImpl.sumOrdersSummary(com.pinting.schedule.util.Constants.MONGODB_SCHEDULE_BSPAYORDER_KEY,
    				Constants.TRANS_BALANCE_WITHDRAW,"","BAOFOO",
    				Constants.ORDER_STATUS_SUCCESS,
    				new Date());
        	sysDailyCheckGacha.setBusinessType(BaoFooCheckBalanceTypeEnum.PAID_BALANCE_WITHDRAW.getCode());
        	sysDailyCheckGacha.setTransferSuccAmount(sumSummary.getJSONObject(0).getDoubleValue("total"));
        	sysDailyCheckGacha.setTransferSuccCount(sumSummary.getJSONObject(0).getIntValue("count"));
        	sysDailyCheckGacha.setPartnerCode(PartnerEnum.BGW.getCode());
        	sysDailyCheckGacha.setFinancialFlag(Constants.BUSINESS_FLAG_OUT);
        	bsSysDailyCheckGachaMapper.insertSelective(sysDailyCheckGacha);
        	
        	// 奖励金提现
        	sumSummary = statisticsDaoImpl.sumOrdersSummary(com.pinting.schedule.util.Constants.MONGODB_SCHEDULE_BSPAYORDER_KEY,
        			Constants.TRANS_BONUS_WITHDRAW,"","BAOFOO",
    				Constants.ORDER_STATUS_SUCCESS,
    				new Date());
        	sysDailyCheckGacha.setBusinessType(BaoFooCheckBalanceTypeEnum.PAID_BONUS_WITHDRAW.getCode());
        	sysDailyCheckGacha.setTransferSuccAmount(sumSummary.getJSONObject(0).getDoubleValue("total"));
        	sysDailyCheckGacha.setTransferSuccCount(sumSummary.getJSONObject(0).getIntValue("count"));
        	sysDailyCheckGacha.setPartnerCode(PartnerEnum.BGW.getCode());
        	sysDailyCheckGacha.setFinancialFlag(Constants.BUSINESS_FLAG_OUT);
        	bsSysDailyCheckGachaMapper.insertSelective(sysDailyCheckGacha);
        	
          	// 宝付代付到归集户
        	sumSummary = statisticsDaoImpl.sumOrdersSummary(com.pinting.schedule.util.Constants.MONGODB_SCHEDULE_LNPAYORDERS_KEY,
    				Constants.SYS_WITHDRAW_2_DEP_REPAY_CARD,"","BAOFOO",
    				Constants.ORDER_STATUS_SUCCESS,
    				new Date());
        	sysDailyCheckGacha.setBusinessType(BaoFooCheckBalanceTypeEnum.PAID_2_DEP_REPAY_CARD.getCode());
        	sysDailyCheckGacha.setTransferSuccAmount(sumSummary.getJSONObject(0).getDoubleValue("total"));
        	sysDailyCheckGacha.setTransferSuccCount(sumSummary.getJSONObject(0).getIntValue("count"));
        	sysDailyCheckGacha.setPartnerCode(null);
        	sysDailyCheckGacha.setFinancialFlag(com.pinting.business.util.Constants.BUSINESS_FLAG_OUT);
        	bsSysDailyCheckGachaMapper.insertSelective(sysDailyCheckGacha);
        	
        	// 合作方营收划转--赞分期营收
        	sumSummary = statisticsDaoImpl.sumOrdersSummary(com.pinting.schedule.util.Constants.MONGODB_SCHEDULE_BSPAYORDER_KEY,
    				Constants.TRANS_SYS_PARTNER_REVENUE,"","",
    				Constants.ORDER_STATUS_SUCCESS,
    				new Date());
        	sysDailyCheckGacha.setBusinessType(BaoFooCheckBalanceTypeEnum.WALLET_TRANSFER_PARTNER_REVENUE.getCode());
        	sysDailyCheckGacha.setTransferSuccAmount(sumSummary.getJSONObject(0).getDoubleValue("total"));
        	sysDailyCheckGacha.setTransferSuccCount(sumSummary.getJSONObject(0).getIntValue("count"));
        	sysDailyCheckGacha.setPartnerCode(PartnerEnum.ZAN.getCode());
        	sysDailyCheckGacha.setFinancialFlag(Constants.BUSINESS_FLAG_OUT);	
        	bsSysDailyCheckGachaMapper.insertSelective(sysDailyCheckGacha);
        	// 云贷营收
        	sumSummary = statisticsDaoImpl.sumOrdersSummary(com.pinting.schedule.util.Constants.MONGODB_SCHEDULE_BSPAYORDER_KEY,
    				Constants.TRANS_SYS_YUN_REPAY_REVENUE,"","",
    				Constants.ORDER_STATUS_SUCCESS,
    				new Date());
         	sysDailyCheckGacha.setBusinessType(BaoFooCheckBalanceTypeEnum.WALLET_TRANSFER_PARTNER_REVENUE.getCode());
         	sysDailyCheckGacha.setTransferSuccAmount(sumSummary.getJSONObject(0).getDoubleValue("total"));
        	sysDailyCheckGacha.setTransferSuccCount(sumSummary.getJSONObject(0).getIntValue("count"));
        	sysDailyCheckGacha.setPartnerCode(PartnerEnum.YUN_DAI_SELF.getCode());
        	sysDailyCheckGacha.setFinancialFlag(Constants.BUSINESS_FLAG_OUT);
        	bsSysDailyCheckGachaMapper.insertSelective(sysDailyCheckGacha);
        	// 期贷营收
        	sumSummary = statisticsDaoImpl.sumOrdersSummary(com.pinting.schedule.util.Constants.MONGODB_SCHEDULE_BSPAYORDER_KEY,
    				Constants.TRANS_SYS_SEVEN_REPAY_REVENUE,"","",
    				Constants.ORDER_STATUS_SUCCESS,
    				new Date());
         	sysDailyCheckGacha.setBusinessType(BaoFooCheckBalanceTypeEnum.WALLET_TRANSFER_PARTNER_REVENUE.getCode());
        	sysDailyCheckGacha.setTransferSuccAmount(sumSummary.getJSONObject(0).getDoubleValue("total"));
        	sysDailyCheckGacha.setTransferSuccCount(sumSummary.getJSONObject(0).getIntValue("count"));
        	sysDailyCheckGacha.setPartnerCode(PartnerEnum.SEVEN_DAI_SELF.getCode());
        	sysDailyCheckGacha.setFinancialFlag(Constants.BUSINESS_FLAG_OUT);	
        	bsSysDailyCheckGachaMapper.insertSelective(sysDailyCheckGacha);
        	// 赞时贷营收
        	sumSummary = statisticsDaoImpl.sumOrdersSummary(com.pinting.schedule.util.Constants.MONGODB_SCHEDULE_BSPAYORDER_KEY,
    				Constants.TRANS_SYS_ZSD_REPAY_REVENUE,"","",
    				Constants.ORDER_STATUS_SUCCESS,
    				new Date());
         	sysDailyCheckGacha.setBusinessType(BaoFooCheckBalanceTypeEnum.WALLET_TRANSFER_PARTNER_REVENUE.getCode());
        	sysDailyCheckGacha.setTransferSuccAmount(sumSummary.getJSONObject(0).getDoubleValue("total"));
        	sysDailyCheckGacha.setTransferSuccCount(sumSummary.getJSONObject(0).getIntValue("count"));
        	sysDailyCheckGacha.setPartnerCode(PartnerEnum.ZSD.getCode());
        	sysDailyCheckGacha.setFinancialFlag(Constants.BUSINESS_FLAG_OUT);
        	bsSysDailyCheckGachaMapper.insertSelective(sysDailyCheckGacha);
           	// 合作方重复还款划转 - 云贷重复还款 
        	sumSummary = statisticsDaoImpl.sumOrdersSummary(com.pinting.schedule.util.Constants.MONGODB_SCHEDULE_BSPAYORDER_KEY,
    				Constants.TRANS_SYS_YUN_REPEAT,"","",
    				Constants.ORDER_STATUS_SUCCESS,
    				new Date());
        	sysDailyCheckGacha.setBusinessType(BaoFooCheckBalanceTypeEnum.WALLET_TRANSFER_PARTNER_REPEAT_REVENUE.getCode());
        	sysDailyCheckGacha.setTransferSuccAmount(sumSummary.getJSONObject(0).getDoubleValue("total"));
        	sysDailyCheckGacha.setTransferSuccCount(sumSummary.getJSONObject(0).getIntValue("count"));
        	sysDailyCheckGacha.setPartnerCode(PartnerEnum.YUN_DAI_SELF.getCode());
        	sysDailyCheckGacha.setFinancialFlag(Constants.BUSINESS_FLAG_OUT);
        	bsSysDailyCheckGachaMapper.insertSelective(sysDailyCheckGacha);
         	// 7贷重复还款
        	sumSummary = statisticsDaoImpl.sumOrdersSummary(com.pinting.schedule.util.Constants.MONGODB_SCHEDULE_BSPAYORDER_KEY,
    				Constants.TRANS_SYS_SEVEN_REPEAT,"","",
    				Constants.ORDER_STATUS_SUCCESS,
    				new Date());
        	sysDailyCheckGacha.setBusinessType(BaoFooCheckBalanceTypeEnum.WALLET_TRANSFER_PARTNER_REPEAT_REVENUE.getCode());
        	sysDailyCheckGacha.setTransferSuccAmount(sumSummary.getJSONObject(0).getDoubleValue("total"));
        	sysDailyCheckGacha.setTransferSuccCount(sumSummary.getJSONObject(0).getIntValue("count"));
        	sysDailyCheckGacha.setPartnerCode(PartnerEnum.SEVEN_DAI_SELF.getCode());
        	sysDailyCheckGacha.setFinancialFlag(Constants.BUSINESS_FLAG_OUT);
        	bsSysDailyCheckGachaMapper.insertSelective(sysDailyCheckGacha);
        	
         	// 赞时贷重复还款
        	sumSummary = statisticsDaoImpl.sumOrdersSummary(com.pinting.schedule.util.Constants.MONGODB_SCHEDULE_BSPAYORDER_KEY,
    				Constants.TRANS_SYS_ZSD_REPEAT,"","",
    				Constants.ORDER_STATUS_SUCCESS,
    				new Date());
        	sysDailyCheckGacha.setBusinessType(BaoFooCheckBalanceTypeEnum.WALLET_TRANSFER_PARTNER_REPEAT_REVENUE.getCode());
        	sysDailyCheckGacha.setTransferSuccAmount(sumSummary.getJSONObject(0).getDoubleValue("total"));
        	sysDailyCheckGacha.setTransferSuccCount(sumSummary.getJSONObject(0).getIntValue("count"));
        	sysDailyCheckGacha.setPartnerCode(PartnerEnum.ZSD.getCode());
        	sysDailyCheckGacha.setFinancialFlag(Constants.BUSINESS_FLAG_OUT);
        	bsSysDailyCheckGachaMapper.insertSelective(sysDailyCheckGacha);
        	
        	// 存管代偿    -- 云贷合作方	
        	sumSummary = statisticsDaoImpl.sumCompensateSummary( PartnerEnum.YUN_DAI_SELF.getCode(), Constants.COMPENSATE_REPAYS_STATUS_SUCC, new Date() );
        	
        	sysDailyCheckGacha.setBusinessType(BaoFooCheckBalanceTypeEnum.WALLET_TRANSFER_LN_COMPENSATE.getCode());
        	sysDailyCheckGacha.setTransferSuccAmount(sumSummary.getJSONObject(0).getDoubleValue("total"));
        	sysDailyCheckGacha.setTransferSuccCount(sumSummary.getJSONObject(0).getIntValue("count"));
        	sysDailyCheckGacha.setPartnerCode(PartnerEnum.YUN_DAI_SELF.getCode());
        	sysDailyCheckGacha.setFinancialFlag(Constants.BUSINESS_FLAG_IN);
        	bsSysDailyCheckGachaMapper.insertSelective(sysDailyCheckGacha);
        	
        	// 存管代偿 -- 7贷合作方 
        	sumSummary = statisticsDaoImpl.sumCompensateSummary( PartnerEnum.SEVEN_DAI_SELF.getCode(), Constants.COMPENSATE_REPAYS_STATUS_SUCC, new Date() );
        	sysDailyCheckGacha.setBusinessType(BaoFooCheckBalanceTypeEnum.WALLET_TRANSFER_LN_COMPENSATE.getCode());
        	sysDailyCheckGacha.setTransferSuccAmount(sumSummary.getJSONObject(0).getDoubleValue("total"));
        	sysDailyCheckGacha.setTransferSuccCount(sumSummary.getJSONObject(0).getIntValue("count"));
        	sysDailyCheckGacha.setPartnerCode(PartnerEnum.SEVEN_DAI_SELF.getCode());
        	sysDailyCheckGacha.setFinancialFlag(Constants.BUSINESS_FLAG_IN);
        	bsSysDailyCheckGachaMapper.insertSelective(sysDailyCheckGacha);
        	
        	// 赞时贷合作方代偿
        	sumSummary = statisticsDaoImpl.sumCompensateSummary( PartnerEnum.ZSD.getCode(), Constants.COMPENSATE_REPAYS_STATUS_SUCC, new Date() );
        	sysDailyCheckGacha.setBusinessType(BaoFooCheckBalanceTypeEnum.WALLET_TRANSFER_LN_COMPENSATE.getCode());
        	sysDailyCheckGacha.setTransferSuccAmount(sumSummary.getJSONObject(0).getDoubleValue("total"));
        	sysDailyCheckGacha.setTransferSuccCount(sumSummary.getJSONObject(0).getIntValue("count"));
        	sysDailyCheckGacha.setPartnerCode(PartnerEnum.ZSD.getCode());
        	sysDailyCheckGacha.setFinancialFlag(Constants.BUSINESS_FLAG_IN);
        	bsSysDailyCheckGachaMapper.insertSelective(sysDailyCheckGacha);
        	
        	//资产合作方线下还款- 赞分期
        	sumSummary = statisticsDaoImpl.sumLnPayOrderSummary( PartnerEnum.ZAN.getCode(), true, 1, Constants.ORDER_STATUS_SUCCESS, new Date() );
        	
        	zanOfflineRepayAmt = sumSummary.getJSONObject(0).getDoubleValue("total");
        	zanOfflineRepayCount=sumSummary.getJSONObject(0).getIntValue("count");
        	
        	sysDailyCheckGacha.setBusinessType(BaoFooCheckBalanceTypeEnum.WALLET_TRANSFER_OFFLINE_REPAY.getCode());
        	sysDailyCheckGacha.setTransferSuccAmount(sumSummary.getJSONObject(0).getDoubleValue("total"));
        	sysDailyCheckGacha.setTransferSuccCount(sumSummary.getJSONObject(0).getIntValue("count"));
        	sysDailyCheckGacha.setPartnerCode(PartnerEnum.ZAN.getCode());
        	sysDailyCheckGacha.setFinancialFlag(Constants.BUSINESS_FLAG_IN);
        	bsSysDailyCheckGachaMapper.insertSelective(sysDailyCheckGacha);
	    	
	    	//云贷线下还款
        	sumSummary = statisticsDaoImpl.sumLnPayOrderSummary( PartnerEnum.YUN_DAI_SELF.getCode(), true, 1, Constants.ORDER_STATUS_SUCCESS, new Date() );
        	
        	yunOfflineRepayAmt = sumSummary.getJSONObject(0).getDoubleValue("total");
        	yunOfflineRepayCount = sumSummary.getJSONObject(0).getIntValue("count");
        	
        	sysDailyCheckGacha.setBusinessType(BaoFooCheckBalanceTypeEnum.WALLET_TRANSFER_OFFLINE_REPAY.getCode());
        	sysDailyCheckGacha.setTransferSuccAmount(yunOfflineRepayAmt);
        	sysDailyCheckGacha.setTransferSuccCount(yunOfflineRepayCount);
        	sysDailyCheckGacha.setPartnerCode(PartnerEnum.YUN_DAI_SELF.getCode());
        	sysDailyCheckGacha.setFinancialFlag(Constants.BUSINESS_FLAG_IN);
        	bsSysDailyCheckGachaMapper.insertSelective(sysDailyCheckGacha);
	    	
        	//7贷线下还款
        	sumSummary = statisticsDaoImpl.sumLnPayOrderSummary( PartnerEnum.SEVEN_DAI_SELF.getCode(), true, 1, Constants.ORDER_STATUS_SUCCESS, new Date() );
        	
        	sevenOfflineRepayAmt   = sumSummary.getJSONObject(0).getDoubleValue("total");
        	sevenOfflineRepayCount = sumSummary.getJSONObject(0).getIntValue("count");
        	
        	sysDailyCheckGacha.setBusinessType(BaoFooCheckBalanceTypeEnum.WALLET_TRANSFER_OFFLINE_REPAY.getCode());
        	sysDailyCheckGacha.setTransferSuccAmount(sumSummary.getJSONObject(0).getDoubleValue("total"));
        	sysDailyCheckGacha.setTransferSuccCount(sumSummary.getJSONObject(0).getIntValue("count"));
        	sysDailyCheckGacha.setPartnerCode(PartnerEnum.SEVEN_DAI_SELF.getCode());
        	sysDailyCheckGacha.setFinancialFlag(Constants.BUSINESS_FLAG_IN);
        	bsSysDailyCheckGachaMapper.insertSelective(sysDailyCheckGacha);
	    	
        	//赞时贷线下还款
        	sumSummary = statisticsDaoImpl.sumLnPayOrderSummary( PartnerEnum.ZSD.getCode(), true, 1, Constants.ORDER_STATUS_SUCCESS, new Date() );

        	zsdOfflineRepayAmt   = sumSummary.getJSONObject(0).getDoubleValue("total");
        	zsdOfflineRepayCount = sumSummary.getJSONObject(0).getIntValue("count");
        	
        	sysDailyCheckGacha.setBusinessType(BaoFooCheckBalanceTypeEnum.WALLET_TRANSFER_OFFLINE_REPAY.getCode());
        	sysDailyCheckGacha.setTransferSuccAmount(sumSummary.getJSONObject(0).getDoubleValue("total"));
        	sysDailyCheckGacha.setTransferSuccCount(sumSummary.getJSONObject(0).getIntValue("count"));
        	sysDailyCheckGacha.setPartnerCode(PartnerEnum.ZSD.getCode());
        	sysDailyCheckGacha.setFinancialFlag(Constants.BUSINESS_FLAG_IN);
        	bsSysDailyCheckGachaMapper.insertSelective(sysDailyCheckGacha);
        	
        	// 存管产品还款   赞分期还款 (赞分期代扣还款-赞分期线下还款，其它合作方取值一样)
        	sumSummary = statisticsDaoImpl.sumLnPayOrderSummary( PartnerEnum.ZAN.getCode(), false, 1, Constants.ORDER_STATUS_SUCCESS, new Date() );
        	
        	sysDailyCheckGacha.setBusinessType(BaoFooCheckBalanceTypeEnum.WITHHOLDING_REPAY.getCode());
        	sysDailyCheckGacha.setTransferSuccAmount(MoneyUtil.subtract(sumSummary.getJSONObject(0).getDoubleValue("total"), zanOfflineRepayAmt).doubleValue());
        	sysDailyCheckGacha.setTransferSuccCount(sumSummary.getJSONObject(0).getIntValue("count") - zanOfflineRepayCount);
        	sysDailyCheckGacha.setPartnerCode(PartnerEnum.ZAN.getCode());
        	sysDailyCheckGacha.setFinancialFlag(Constants.BUSINESS_FLAG_IN);
        	bsSysDailyCheckGachaMapper.insertSelective(sysDailyCheckGacha);
	    	
        	// 云贷还款 
        	sumSummary = statisticsDaoImpl.sumLnPayOrderSummary( PartnerEnum.YUN_DAI_SELF.getCode(), false, 1, Constants.ORDER_STATUS_SUCCESS, new Date() );
	    	
        	sysDailyCheckGacha.setBusinessType(BaoFooCheckBalanceTypeEnum.WITHHOLDING_REPAY.getCode());
        	sysDailyCheckGacha.setTransferSuccAmount(MoneyUtil.subtract(sumSummary.getJSONObject(0).getDoubleValue("total"), yunOfflineRepayAmt).doubleValue());
        	sysDailyCheckGacha.setTransferSuccCount(sumSummary.getJSONObject(0).getIntValue("count") - yunOfflineRepayCount);
        	sysDailyCheckGacha.setPartnerCode(PartnerEnum.YUN_DAI_SELF.getCode());
        	sysDailyCheckGacha.setFinancialFlag(Constants.BUSINESS_FLAG_IN);
        	bsSysDailyCheckGachaMapper.insertSelective(sysDailyCheckGacha);
	    	
        	// 7贷还款
        	sumSummary = statisticsDaoImpl.sumLnPayOrderSummary( PartnerEnum.SEVEN_DAI_SELF.getCode(), false, 1, Constants.ORDER_STATUS_SUCCESS, new Date() );
        	
        	sysDailyCheckGacha.setBusinessType(BaoFooCheckBalanceTypeEnum.WITHHOLDING_REPAY.getCode());
        	sysDailyCheckGacha.setTransferSuccAmount(MoneyUtil.subtract(sumSummary.getJSONObject(0).getDoubleValue("total"), sevenOfflineRepayAmt).doubleValue());
        	sysDailyCheckGacha.setTransferSuccCount(sumSummary.getJSONObject(0).getIntValue("count") - sevenOfflineRepayCount);
        	sysDailyCheckGacha.setPartnerCode(PartnerEnum.SEVEN_DAI_SELF.getCode());
        	sysDailyCheckGacha.setFinancialFlag(Constants.BUSINESS_FLAG_IN);
        	bsSysDailyCheckGachaMapper.insertSelective(sysDailyCheckGacha);
        	
        	// 赞时贷还款
        	sumSummary = statisticsDaoImpl.sumLnPayOrderSummary( PartnerEnum.ZSD.getCode(), false, 1, Constants.ORDER_STATUS_SUCCESS, new Date() );
        	
        	sysDailyCheckGacha.setBusinessType(BaoFooCheckBalanceTypeEnum.WITHHOLDING_REPAY.getCode());
        	sysDailyCheckGacha.setTransferSuccAmount(MoneyUtil.subtract(sumSummary.getJSONObject(0).getDoubleValue("total"), zsdOfflineRepayAmt).doubleValue());
        	sysDailyCheckGacha.setTransferSuccCount(sumSummary.getJSONObject(0).getIntValue("count") - zsdOfflineRepayCount);
        	sysDailyCheckGacha.setPartnerCode(PartnerEnum.ZSD.getCode());
        	sysDailyCheckGacha.setFinancialFlag(Constants.BUSINESS_FLAG_IN);
        	bsSysDailyCheckGachaMapper.insertSelective(sysDailyCheckGacha);
        	
    	}
	}
	@Override
	public void generateAssistCheckGacha() {

		BsPaymentChannelExample example = new BsPaymentChannelExample();
		example.createCriteria().andIsMainEqualTo(2).andPriorityEqualTo(1);
		List<BsPaymentChannel> paymentChannel = paymentChannelMapper.selectByExample(example);
		String merchantNo = paymentChannel.get(0).getMerchantNo();
		// 当前商户号 已生成过指定日期的对账结果 ，不再生成
    	String checkDate = com.pinting.business.util.DateUtil.getDate(DateUtils.addDays(new Date(), -1));
    	List<BsSysDailyCheckGacha> list = bsSysDailyCheckGachaMapper.selectSysDailyCheckGacha(merchantNo, checkDate);
    	
    	if ( CollectionUtils.isEmpty(list) ) {
	    	BsSysDailyCheckGacha sysDailyCheckGacha = fillSysDailyCheckGacha(Constants.CHANNEL_BAOFOO, merchantNo);
	    	// 存管产品还款 - 赞分期还款
	    	JSONArray sumSummary = statisticsDaoImpl.sumLnPayOrderSummary(PartnerEnum.ZAN.getCode(), false, 2, Constants.ORDER_STATUS_SUCCESS, new Date());
	    	
	    	sysDailyCheckGacha.setBusinessType(BaoFooCheckBalanceTypeEnum.WITHHOLDING_REPAY.getCode());
	    	sysDailyCheckGacha.setTransferSuccAmount(sumSummary.getJSONObject(0).getDoubleValue("total"));
	    	sysDailyCheckGacha.setTransferSuccCount(sumSummary.getJSONObject(0).getIntValue("count"));
	    	sysDailyCheckGacha.setPartnerCode(PartnerEnum.ZAN.getCode());
	    	sysDailyCheckGacha.setFinancialFlag(Constants.BUSINESS_FLAG_IN);
	    	bsSysDailyCheckGachaMapper.insertSelective(sysDailyCheckGacha);
	    	
	    	// 云贷还款
	    	sumSummary = statisticsDaoImpl.sumLnPayOrderSummary(PartnerEnum.YUN_DAI_SELF.getCode(), false, 2, Constants.ORDER_STATUS_SUCCESS, new Date());
	    	
	    	sysDailyCheckGacha.setBusinessType(BaoFooCheckBalanceTypeEnum.WITHHOLDING_REPAY.getCode());
	    	sysDailyCheckGacha.setTransferSuccAmount(sumSummary.getJSONObject(0).getDoubleValue("total"));
	    	sysDailyCheckGacha.setTransferSuccCount(sumSummary.getJSONObject(0).getIntValue("count"));
	    	sysDailyCheckGacha.setPartnerCode(PartnerEnum.YUN_DAI_SELF.getCode());
	    	sysDailyCheckGacha.setFinancialFlag(Constants.BUSINESS_FLAG_IN);
	    	bsSysDailyCheckGachaMapper.insertSelective(sysDailyCheckGacha);
	    	
	    	// 7贷还款
	    	sumSummary = statisticsDaoImpl.sumLnPayOrderSummary(PartnerEnum.SEVEN_DAI_SELF.getCode(), false, 2, Constants.ORDER_STATUS_SUCCESS, new Date());
	    	sysDailyCheckGacha.setBusinessType(BaoFooCheckBalanceTypeEnum.WITHHOLDING_REPAY.getCode());
	    	sysDailyCheckGacha.setTransferSuccAmount(sumSummary.getJSONObject(0).getDoubleValue("total"));
	    	sysDailyCheckGacha.setTransferSuccCount(sumSummary.getJSONObject(0).getIntValue("count"));
	    	sysDailyCheckGacha.setPartnerCode(PartnerEnum.SEVEN_DAI_SELF.getCode());
	    	sysDailyCheckGacha.setFinancialFlag(Constants.BUSINESS_FLAG_IN);
	    	bsSysDailyCheckGachaMapper.insertSelective(sysDailyCheckGacha);
	    	
	    	// 赞时贷还款
	    	sumSummary = statisticsDaoImpl.sumLnPayOrderSummary(PartnerEnum.ZSD.getCode(), false, 2, Constants.ORDER_STATUS_SUCCESS, new Date());
	    	
	    	sysDailyCheckGacha.setBusinessType(BaoFooCheckBalanceTypeEnum.WITHHOLDING_REPAY.getCode());
	    	sysDailyCheckGacha.setTransferSuccAmount(sumSummary.getJSONObject(0).getDoubleValue("total"));
	    	sysDailyCheckGacha.setTransferSuccCount(sumSummary.getJSONObject(0).getIntValue("count"));
	    	sysDailyCheckGacha.setPartnerCode(PartnerEnum.ZSD.getCode());
	    	sysDailyCheckGacha.setFinancialFlag(Constants.BUSINESS_FLAG_IN);
	    	bsSysDailyCheckGachaMapper.insertSelective(sysDailyCheckGacha);
	    	
	    	//辅助通道转账到主通道
	    	sumSummary = statisticsDaoImpl.sumOrdersSummary( com.pinting.schedule.util.Constants.MONGODB_SCHEDULE_LNPAYORDERS_KEY, 
	    			"REPAY", Constants.CHANNEL_TRS_TRANSFER, "", Constants.ORDER_STATUS_SUCCESS, new Date());
	    	
	      	sysDailyCheckGacha.setBusinessType(BaoFooCheckBalanceTypeEnum.WALLET_TRANSFER_ASSIST_2_MAIN.getCode());
	    	sysDailyCheckGacha.setTransferSuccAmount(sumSummary.getJSONObject(0).getDoubleValue("total"));
	    	sysDailyCheckGacha.setTransferSuccCount(sumSummary.getJSONObject(0).getIntValue("count"));
	    	sysDailyCheckGacha.setPartnerCode(null);
	    	sysDailyCheckGacha.setFinancialFlag(Constants.BUSINESS_FLAG_OUT);
	    	bsSysDailyCheckGachaMapper.insertSelective(sysDailyCheckGacha);
	    	
	    	//主商户增加一条转账汇总记录
	    	BsPaymentChannelExample  mainExample = new BsPaymentChannelExample();
	    	mainExample.createCriteria().andIsMainEqualTo(1).andPriorityEqualTo(2);
			List<BsPaymentChannel> mainPaymentChannel = paymentChannelMapper.selectByExample(mainExample);
			String mainMerchantNo = mainPaymentChannel.get(0).getMerchantNo();
	    	BsSysDailyCheckGacha mainSysDailyCheckGacha = fillSysDailyCheckGacha(Constants.CHANNEL_BAOFOO, mainMerchantNo);
	    	
	    	mainSysDailyCheckGacha.setBusinessType(BaoFooCheckBalanceTypeEnum.WALLET_TRANSFER_ASSIST_2_MAIN.getCode());
	    	mainSysDailyCheckGacha.setTransferSuccAmount(sumSummary.getJSONObject(0).getDoubleValue("total"));
	    	mainSysDailyCheckGacha.setTransferSuccCount(sumSummary.getJSONObject(0).getIntValue("count"));
	    	mainSysDailyCheckGacha.setPartnerCode(null);
	    	mainSysDailyCheckGacha.setFinancialFlag(Constants.BUSINESS_FLAG_IN);
	    	bsSysDailyCheckGachaMapper.insertSelective(mainSysDailyCheckGacha);
    	}
	}

	 /**
     * 填充系统日常对账轧差信息
     * @param channel
     * @return
     */
    private BsSysDailyCheckGacha fillSysDailyCheckGacha(String channel, String merchantNo) {
    	BsSysDailyCheckGacha sysDailyCheckGacha = new BsSysDailyCheckGacha();
    	Date time = DateUtils.addDays(new Date(), -1);
    	sysDailyCheckGacha.setCheckDate(time);
    	sysDailyCheckGacha.setChannel(channel);
    	sysDailyCheckGacha.setMerchantNo(merchantNo);
    	return sysDailyCheckGacha;
    }
	@Override
	public void testMongoInterface() {
		//插入还款数据
    	LnPayOrders payObject = new LnPayOrders();
    	payObject.setId(1);
    	payObject.setPartnerCode("YUN_DAI_SELF");
    	payObject.setPaymentChannelId(2);
    	payObject.setOrderNo("20180619001");
    	payObject.setAmount(10d);
    	payObject.setLnUserId(10086);
    	payObject.setSubAccountId(10000);
    	payObject.setChannel("BAOFOO");
    	payObject.setPayPath(null);
    	payObject.setStatus(6);
    	payObject.setBankName("工商银行");
    	payObject.setAccountType(null);
    	payObject.setMoneyType(null);
    	payObject.setTerminalType(null);
    	payObject.setStartJnlNo(null);
    	payObject.setEndJnlNo(null);
    	payObject.setBankId(1);
    	payObject.setBankCardNo("6222021203029387056");
    	payObject.setTransType("REPAY");
    	payObject.setChannelTransType("DK");
    	payObject.setMobile("15068487916");
    	payObject.setIdCard("209292*****2911");
    	payObject.setUserName("杨洋代扣还款");
    	payObject.setIsProtocolPay("YES");
    	payObject.setReturnCode("000000");
    	payObject.setReturnMsg("交易成功");
    	payObject.setNote(null);
    	payObject.setCreateTime(new Date());
    	payObject.setUpdateTime(new Date());
    	statisticsDaoImpl.insert(payObject, com.pinting.schedule.util.Constants.MONGODB_SCHEDULE_LNPAYORDERS_KEY);
    	//线下还款
    	LnPayOrders payObject2 = new LnPayOrders();
    	payObject2.setId(2);
    	payObject2.setPartnerCode("YUN_DAI_SELF");
    	payObject2.setPaymentChannelId(1);
    	payObject2.setOrderNo("20180619002");
    	payObject2.setAmount(100d);
    	payObject2.setLnUserId(10086);
    	payObject2.setSubAccountId(10000);
    	payObject2.setChannel("BAOFOO");
    	payObject2.setPayPath(null);
    	payObject2.setStatus(6);
    	payObject2.setBankName("工商银行");
    	payObject2.setAccountType(null);
    	payObject2.setMoneyType(null);
    	payObject2.setTerminalType(null);
    	payObject2.setStartJnlNo(null);
    	payObject2.setEndJnlNo(null);
    	payObject2.setBankId(1);
    	payObject2.setBankCardNo("6222021203029387056");
    	payObject2.setTransType("REPAY");
    	payObject2.setChannelTransType("DK");
    	payObject2.setMobile("15068487916");
    	payObject2.setIdCard("209292*****2911");
    	payObject2.setUserName("杨洋线下还款");
    	payObject2.setIsProtocolPay("YES");
    	payObject2.setReturnCode("000000");
    	payObject2.setReturnMsg("交易成功");
    	payObject2.setNote(null);
    	payObject2.setCreateTime(new Date());
    	payObject2.setUpdateTime(new Date());
    	statisticsDaoImpl.insert(payObject2, com.pinting.schedule.util.Constants.MONGODB_SCHEDULE_LNPAYORDERS_KEY);
    	
    	LnPayOrders payObject3 = new LnPayOrders();
    	payObject3.setId(3);
    	payObject3.setPartnerCode("YUN_DAI_SELF");
    	payObject3.setPaymentChannelId(1);
    	payObject3.setOrderNo("20180619003");
    	payObject3.setAmount(100d);
    	payObject3.setLnUserId(10086);
    	payObject3.setSubAccountId(10000);
    	payObject3.setChannel("BAOFOO");
    	payObject3.setPayPath(null);
    	payObject3.setStatus(6);
    	payObject3.setBankName("工商银行");
    	payObject3.setAccountType(null);
    	payObject3.setMoneyType(null);
    	payObject3.setTerminalType(null);
    	payObject3.setStartJnlNo(null);
    	payObject3.setEndJnlNo(null);
    	payObject3.setBankId(1);
    	payObject3.setBankCardNo("6222021203029387056");
    	payObject3.setTransType("REPAY");
    	payObject3.setChannelTransType("TRANSFER");
    	payObject3.setMobile("15068487916");
    	payObject3.setIdCard("209292*****2911");
    	payObject3.setUserName("杨洋线下还款钱包转账");
    	payObject3.setIsProtocolPay("YES");
    	payObject3.setReturnCode("000000");
    	payObject3.setReturnMsg("交易成功");
    	payObject3.setNote(null);
    	payObject3.setCreateTime(new Date());
    	payObject3.setUpdateTime(new Date());
    	statisticsDaoImpl.insert(payObject3, com.pinting.schedule.util.Constants.MONGODB_SCHEDULE_LNPAYORDERS_KEY);
    	
    	//代扣还款
    	LnRepay repayObject= new LnRepay();
    	repayObject.setId(1);
    	repayObject.setLnUserId(10086);
    	repayObject.setLoanId(100);
    	repayObject.setRepayPlanId(1);
    	repayObject.setPartnerOrderNo("LnRepay20180619001");
    	repayObject.setBgwOrderNo("BDK20180619001");
    	repayObject.setPayOrderNo("20180619001");
    	repayObject.setBgwBindId("BGW20180619001");
    	repayObject.setDoneTime(new Date());
    	repayObject.setDoneTotal(10d);
    	repayObject.setStatus("REPAIED");
    	repayObject.setInformStatus("SUCCESS");
    	repayObject.setRepayType("");
    	repayObject.setApplyTime(new Date());
    	repayObject.setCreateTime(new Date());
    	repayObject.setUpdateTime(new Date());
    	statisticsDaoImpl.insert(repayObject, com.pinting.schedule.util.Constants.MONGODB_SCHEDULE_LNREPAY_KEY);
    	
    	LnRepay offRepayObject= new LnRepay();
    	offRepayObject.setId(2);
    	offRepayObject.setLnUserId(10086);
    	offRepayObject.setLoanId(100);
    	offRepayObject.setRepayPlanId(1);
    	offRepayObject.setPartnerOrderNo("LnRepay20180619002");
    	offRepayObject.setBgwOrderNo("BDK20180619002");
    	offRepayObject.setPayOrderNo("20180619002");
    	offRepayObject.setBgwBindId("BGW20180619002");
    	offRepayObject.setDoneTime(new Date());
    	offRepayObject.setDoneTotal(100d);
    	offRepayObject.setStatus("REPAIED");
    	offRepayObject.setInformStatus("SUCCESS");
    	offRepayObject.setRepayType("OFFLINE_REPAY");
    	offRepayObject.setApplyTime(new Date());
    	offRepayObject.setCreateTime(new Date());
    	offRepayObject.setUpdateTime(new Date());
    	statisticsDaoImpl.insert(offRepayObject, com.pinting.schedule.util.Constants.MONGODB_SCHEDULE_LNREPAY_KEY);
	}
	
	@Override
	public void generateHfBankCheckGacha() {
		/// 当前商户号 已生成过指定日期的对账结果 ，不再生成
    	String checkDate = DateUtil.getDate(DateUtils.addDays(new Date(), -1));
    	List<BsSysDailyCheckGacha> list = bsSysDailyCheckGachaMapper.selectHfbankDailyCheckGacha(PayPlatformEnum.HFBANK.getCode(), checkDate);
    	if (CollectionUtils.isEmpty(list) ) {
    		BsSysDailyCheckGacha sysDailyCheckGacha = fillSysDailyCheckGacha(Constants.CHANNEL_HFBANK, null);
    		
    		// 投资人提现 
    		JSONArray sumSummary = statisticsDaoImpl.sumOrdersSummary(com.pinting.schedule.util.Constants.MONGODB_SCHEDULE_BSPAYORDER_KEY,
    				Constants.TRANS_BALANCE_WITHDRAW, PayPlatformEnum.HFBANK.getCode(), "", "",
    				Constants.ORDER_STATUS_SUCCESS,
    				new Date());
        	sysDailyCheckGacha.setBusinessType(HFBankCheckBalanceTypeEnum.HFBANK_FINANCIAL_WITHDRAW.getCode());
        	sysDailyCheckGacha.setTransferSuccAmount(sumSummary.getJSONObject(0).getDoubleValue("total"));
        	sysDailyCheckGacha.setTransferSuccCount(sumSummary.getJSONObject(0).getIntValue("count"));
        	sysDailyCheckGacha.setPartnerCode(PartnerEnum.BGW.getCode());
        	sysDailyCheckGacha.setFinancialFlag(Constants.BUSINESS_FLAG_OUT);
        	bsSysDailyCheckGachaMapper.insertSelective(sysDailyCheckGacha);
    		
        	// 平台提现 
    		sumSummary = statisticsDaoImpl.sumOrdersSummary(com.pinting.schedule.util.Constants.MONGODB_SCHEDULE_BSPAYORDER_KEY,
    				Constants.TRANS_HFBANK_SYS_WITHDRAW, PayPlatformEnum.HFBANK.getCode(), "", "",
    				Constants.ORDER_STATUS_SUCCESS,
    				new Date());
        	sysDailyCheckGacha.setBusinessType(HFBankCheckBalanceTypeEnum.HFBANK_PLATFORM_WITHDRAW.getCode());
        	sysDailyCheckGacha.setTransferSuccAmount(sumSummary.getJSONObject(0).getDoubleValue("total"));
        	sysDailyCheckGacha.setTransferSuccCount(sumSummary.getJSONObject(0).getIntValue("count"));
        	sysDailyCheckGacha.setPartnerCode(PartnerEnum.BGW.getCode());
        	sysDailyCheckGacha.setFinancialFlag(Constants.BUSINESS_FLAG_OUT);
        	bsSysDailyCheckGachaMapper.insertSelective(sysDailyCheckGacha);
        	
        	// 投资人充值 
    		sumSummary = statisticsDaoImpl.sumOrdersSummary(com.pinting.schedule.util.Constants.MONGODB_SCHEDULE_BSPAYORDER_KEY,
    				Constants.TRANS_TOP_UP, PayPlatformEnum.HFBANK.getCode(), "", "",
    				Constants.ORDER_STATUS_SUCCESS,
    				new Date());
        	sysDailyCheckGacha.setBusinessType(HFBankCheckBalanceTypeEnum.HFBANK_FINANCIAL_TOP_UP.getCode());
        	sysDailyCheckGacha.setTransferSuccAmount(sumSummary.getJSONObject(0).getDoubleValue("total"));
        	sysDailyCheckGacha.setTransferSuccCount(sumSummary.getJSONObject(0).getIntValue("count"));
        	sysDailyCheckGacha.setPartnerCode(PartnerEnum.BGW.getCode());
        	sysDailyCheckGacha.setFinancialFlag(Constants.BUSINESS_FLAG_IN);
        	bsSysDailyCheckGachaMapper.insertSelective(sysDailyCheckGacha);
        	
        	// 平台充值 
    		sumSummary = statisticsDaoImpl.sumOrdersSummary(com.pinting.schedule.util.Constants.MONGODB_SCHEDULE_BSPAYORDER_KEY,
    				Constants.TRANS_HFBANK_SYS_TOP_UP, PayPlatformEnum.HFBANK.getCode(), "", "",
    				Constants.ORDER_STATUS_SUCCESS,
    				new Date());
        	sysDailyCheckGacha.setBusinessType(HFBankCheckBalanceTypeEnum.HFBANK_PLATFORM_TOP_UP.getCode());
        	sysDailyCheckGacha.setTransferSuccAmount(sumSummary.getJSONObject(0).getDoubleValue("total"));
        	sysDailyCheckGacha.setTransferSuccCount(sumSummary.getJSONObject(0).getIntValue("count"));
        	sysDailyCheckGacha.setPartnerCode(PartnerEnum.BGW.getCode());
        	sysDailyCheckGacha.setFinancialFlag(Constants.BUSINESS_FLAG_IN);
        	bsSysDailyCheckGachaMapper.insertSelective(sysDailyCheckGacha);   
        	
        	// 云贷放款
    		sumSummary = statisticsDaoImpl.sumOrdersSummary(com.pinting.schedule.util.Constants.MONGODB_SCHEDULE_LNPAYORDERS_KEY,
    				LoanStatus.TRANS_TYPE_LOAN.getCode(), PayPlatformEnum.HFBANK.getCode(), PartnerEnum.YUN_DAI_SELF.getCode(), "",
    				Constants.ORDER_STATUS_SUCCESS, new Date());
        	sysDailyCheckGacha.setBusinessType(HFBankCheckBalanceTypeEnum.HFBANK_YUN_LOAN.getCode());
        	sysDailyCheckGacha.setTransferSuccAmount(sumSummary.getJSONObject(0).getDoubleValue("total"));
        	sysDailyCheckGacha.setTransferSuccCount(sumSummary.getJSONObject(0).getIntValue("count"));
        	sysDailyCheckGacha.setPartnerCode(PartnerEnum.YUN_DAI_SELF.getCode());
        	sysDailyCheckGacha.setFinancialFlag(Constants.BUSINESS_FLAG_OUT);
        	bsSysDailyCheckGachaMapper.insertSelective(sysDailyCheckGacha);
        	
        	// 7贷放款
    		sumSummary = statisticsDaoImpl.sumOrdersSummary(com.pinting.schedule.util.Constants.MONGODB_SCHEDULE_LNPAYORDERS_KEY,
    				LoanStatus.TRANS_TYPE_LOAN.getCode(), PayPlatformEnum.HFBANK.getCode(), PartnerEnum.SEVEN_DAI_SELF.getCode(), "",
    				Constants.ORDER_STATUS_SUCCESS, new Date());
        	sysDailyCheckGacha.setBusinessType(HFBankCheckBalanceTypeEnum.HFBANK_SEVEN_LOAN.getCode());
        	sysDailyCheckGacha.setTransferSuccAmount(sumSummary.getJSONObject(0).getDoubleValue("total"));
        	sysDailyCheckGacha.setTransferSuccCount(sumSummary.getJSONObject(0).getIntValue("count"));
        	sysDailyCheckGacha.setPartnerCode(PartnerEnum.SEVEN_DAI_SELF.getCode());
        	sysDailyCheckGacha.setFinancialFlag(Constants.BUSINESS_FLAG_OUT);
        	bsSysDailyCheckGachaMapper.insertSelective(sysDailyCheckGacha);      
        	
           	// 入金（归集户到存管户）
    		sumSummary = statisticsDaoImpl.sumOrdersSummary(com.pinting.schedule.util.Constants.MONGODB_SCHEDULE_LNPAYORDERS_KEY,
    				LoanStatus.TRANS_TYPE_CUT_REPAY_2_BORROWER.getCode(), PayPlatformEnum.HFBANK.getCode(), null, "",
    				Constants.ORDER_STATUS_SUCCESS, new Date());
        	sysDailyCheckGacha.setBusinessType(HFBankCheckBalanceTypeEnum.HFBANK_REPAY_CARD_2_DEPOSITOR.getCode());
        	sysDailyCheckGacha.setTransferSuccAmount(sumSummary.getJSONObject(0).getDoubleValue("total"));
        	sysDailyCheckGacha.setTransferSuccCount(sumSummary.getJSONObject(0).getIntValue("count"));
        	sysDailyCheckGacha.setPartnerCode(PartnerEnum.BGW.getCode());
        	sysDailyCheckGacha.setFinancialFlag(Constants.BUSINESS_FLAG_IN);
        	bsSysDailyCheckGachaMapper.insertSelective(sysDailyCheckGacha);      
    	}
	}
	
}
