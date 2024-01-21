package com.pinting.business.service.common.impl;

import com.pinting.business.enums.PayPlatformEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pinting.business.dao.BsSysConfigMapper;
import com.pinting.business.enums.PTMessageEnum;
import com.pinting.business.enums.TransTypeEnum;
import com.pinting.business.exception.PTMessageException;
import com.pinting.business.model.BsSysConfig;
import com.pinting.business.model.vo.CommissionVO;
import com.pinting.business.service.common.CommissionService;
import com.pinting.business.util.Constants;
import com.pinting.core.util.MoneyUtil;

/**
 * 手续费相关服务实现
 * @author bianyatian
 * @2016-8-31 下午3:53:40
 */
@Service
public class CommissionServiceImpl implements CommissionService {
	@Autowired
	private BsSysConfigMapper bsSysConfigMapper;

	@Override
	public CommissionVO calServiceFee(Double amount, TransTypeEnum transTypeEnum, PayPlatformEnum payPlatformEnum) {
		if(transTypeEnum == null){
			throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR);
		}

		Double needPayAmount = 0d;
		Double actPayAmount = 0d;
		if(TransTypeEnum.USER_TOP_UP_QUICK_PAY.equals(transTypeEnum)){
			//理财用户充值（快捷）手续费，实扣0
		}if(TransTypeEnum.USER_TOP_UP_E_BANK.equals(transTypeEnum)){
			//理财用户充值（网银）手续费，实扣0
		}else if(TransTypeEnum.USER_WITHDRAW.equals(transTypeEnum)){
			//理财用户提现手续费，应扣：n元每笔，实扣：n元每笔
			BsSysConfig withdrawCounterFeeConfig = bsSysConfigMapper.selectByPrimaryKey(Constants.FINANCE_WITHDRAW_COUNTER_FEE);
			needPayAmount = Double.valueOf(withdrawCounterFeeConfig.getConfValue());
			actPayAmount = Double.valueOf(withdrawCounterFeeConfig.getConfValue());
		}else if(TransTypeEnum.LOAN_USER_LOAN.equals(transTypeEnum) || TransTypeEnum.ZSD_LOAN.equals(transTypeEnum)){
			//借款用户借款手续费，应扣：n元每笔，实扣0
			BsSysConfig loanFeeConfig = bsSysConfigMapper.selectByPrimaryKey(Constants.LOAN_USER_LOAN_FEE);
			needPayAmount = Double.valueOf(loanFeeConfig.getConfValue());

		}else if(TransTypeEnum.LOAN_USER_REPAY_QUICK_PAY.equals(transTypeEnum) || TransTypeEnum.LOAN_USER_REPAY_E_BANK.equals(transTypeEnum)){
			if(amount == null){
				throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR);
			}
			//借款用户还款手续费，应扣：千n每笔（最低2元，最高10元），实扣0
			BsSysConfig repayRateConfig = bsSysConfigMapper.selectByPrimaryKey(Constants.LOAN_USER_REPAY_FEE_RATE);
			Double repayRate = MoneyUtil.divide(Double.valueOf(repayRateConfig.getConfValue()), 1000, 6).doubleValue();
			//金额-分
			Double calAmount = MoneyUtil.multiply(amount, 100).doubleValue();
			//手续费-分
			calAmount = MoneyUtil.multiply(calAmount, repayRate).doubleValue();
			//手续费-分(四舍五入)
			calAmount = (double) Math.round(calAmount);
			//手续费-元
			calAmount = MoneyUtil.divide(calAmount, 100,2).doubleValue();

			if(calAmount < 2){
				needPayAmount = 2d;
			}else if(calAmount>10){
				needPayAmount = 10d;
			}else{
				needPayAmount = calAmount;
			}
		}else if(TransTypeEnum.PARTNER_MARKET_FEE.equals(transTypeEnum)){
			//合作方营销费用提现手续费
			BsSysConfig partnerMarketConfig = bsSysConfigMapper.selectByPrimaryKey(Constants.USER_PARTNER_MARKET_FEE);
			needPayAmount = Double.valueOf(partnerMarketConfig.getConfValue());
		}else if(TransTypeEnum.ZAN_REPAY_DK.equals(transTypeEnum) || TransTypeEnum.ZSD_REPAY_DK.equals(transTypeEnum)) {
			//赞分期代扣手续费
			BsSysConfig config = bsSysConfigMapper.selectByPrimaryKey(Constants.ZAN_DK_FEE);
			needPayAmount = Double.valueOf(config.getConfValue());
		}else if(TransTypeEnum.YUN_DAI_SELF_LOAN.equals(transTypeEnum)){
			//云贷自主_借款用户借款  手续费   应扣1，实扣0
			BsSysConfig partnerMarketConfig = bsSysConfigMapper.selectByPrimaryKey(Constants.YUN_DAI_SELF_LOAN_FEE);
			needPayAmount = Double.valueOf(partnerMarketConfig.getConfValue());
		} else if(TransTypeEnum.SEVEN_DAI_SELF_LOAN.equals(transTypeEnum)) {
			//7贷自主_借款用户借款  手续费   应扣1，实扣0
			BsSysConfig partnerMarketConfig = bsSysConfigMapper.selectByPrimaryKey(Constants.SEVEN_DAI_SELF_LOAN_FEE);
			needPayAmount = Double.valueOf(partnerMarketConfig.getConfValue());
		}else if((TransTypeEnum.YUN_DAI_SELF_REPAY_QUICK_PAY.equals(transTypeEnum) || TransTypeEnum.YUN_DAI_SELF_REPAY_E_BANK.equals(transTypeEnum))){
			//云贷自主_借款用户还款  手续费  应扣1，实扣0
			BsSysConfig partnerMarketConfig = bsSysConfigMapper.selectByPrimaryKey(Constants.YUN_DAI_SELF_REPAY_FEE);
			needPayAmount = Double.valueOf(partnerMarketConfig.getConfValue());
		}else if(TransTypeEnum.YUN_DAI_SELF_DK.equals(transTypeEnum)) {
			//云贷自主_借款代扣手续费
			BsSysConfig config = bsSysConfigMapper.selectByPrimaryKey(Constants.YUN_DAI_SELF_DK_FEE);
			needPayAmount = Double.valueOf(config.getConfValue());
		}else if(TransTypeEnum.ZSD_REPAY_DK.equals(transTypeEnum)) {
			BsSysConfig config = bsSysConfigMapper.selectByPrimaryKey(Constants.ZSD_DK_FEE);
			needPayAmount = Double.valueOf(config.getConfValue());
		}else if(TransTypeEnum.SEVEN_DAI_SELF_DK.equals(transTypeEnum)) {
			BsSysConfig config = bsSysConfigMapper.selectByPrimaryKey(Constants.SEVEN_DAI_SELF_DK_FEE);
			needPayAmount = Double.valueOf(config.getConfValue());
		}

		CommissionVO commission = new CommissionVO();
		commission.setNeedPayAmount(needPayAmount == null ? 0d : needPayAmount);
		commission.setActPayAmount(actPayAmount == null ? 0d : actPayAmount);

		// 三方支付渠道手续费
		double threePartyPaymentServiceFee = calThreePartyPaymentServiceFee(amount, payPlatformEnum, transTypeEnum);
		commission.setThreePartyPaymentServiceFee(threePartyPaymentServiceFee);
		return commission;
	}


	/**
	 * 计算第三方支付平台手续费
	 * 宝付：{
	 *     1.理财用户充值：{
	 *     		1. 快捷：千2/笔（最低2元，封顶10元）
	 *     		2. 网银：千2/笔，无最低无封顶
	 *     }
	 *     2.理财用户提现：{
	 *			1. 1元/笔
	 *     }
	 *     3.赞分期借款用户借款：{
	 *     		1. 1元/笔
	 *     }
	 *     4.赞分期借款用户还款：{
	 *     		1. 快捷：千2/笔（最低2元，封顶10元）
	 *     		2. 网银：千2/笔，无最低无封顶
	 *     }
	 *     5.赞分期营销费用提现：{
	 *			1. 1元/笔
	 *     }
	 *     6.赞分期代扣：{
	 *          1. 3元/笔
	 *     }
	 *
	 *     云贷借款用户借款：{
	 *          1. 1元/笔
	 *     }
	 *     云贷借款用户还款：{
	 *          1. 快捷：千2/笔（最低2元，封顶10元）
	 *			2. 网银：千2/笔，无最低无封顶
	 *     }
	 *     
	 *     7贷同云贷
	 * }
	 * @param amount		金额
	 * @param payPlatform	支付平台
	 * @param transTypeEnum	交易类型
	 * @return
     */
	private double calThreePartyPaymentServiceFee(Double amount, PayPlatformEnum payPlatform, TransTypeEnum transTypeEnum) {
		double threePartyPaymentServiceFee = 0d;
		if (null != payPlatform) {
			if (PayPlatformEnum.BAOFOO.equals(payPlatform)) {
				// 宝付支付渠道
				if (TransTypeEnum.USER_TOP_UP_QUICK_PAY.equals(transTypeEnum)) {
					// 1.1 理财用户快捷充值（快捷） 千2/笔（最低2元，封顶10元）
					if(amount == null){
						throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR);
					}
					BsSysConfig config = bsSysConfigMapper.selectByPrimaryKey(Constants.BAOFOO_FEE_QUICK_PAY);
					Double rate = MoneyUtil.divide(Double.valueOf(config.getConfValue()), 1000, 6).doubleValue();
					//金额-分
					threePartyPaymentServiceFee = MoneyUtil.multiply(amount, 100).doubleValue();
					//手续费-分
					threePartyPaymentServiceFee = MoneyUtil.multiply(threePartyPaymentServiceFee, rate).doubleValue();
					//手续费-分(四舍五入)
					threePartyPaymentServiceFee = (double) Math.round(threePartyPaymentServiceFee);
					//手续费-元
					threePartyPaymentServiceFee = MoneyUtil.divide(threePartyPaymentServiceFee, 100, 2).doubleValue();

					if(threePartyPaymentServiceFee < 2) {
						threePartyPaymentServiceFee = 2d;
					} else if (threePartyPaymentServiceFee > 10) {
						threePartyPaymentServiceFee = 10d;
					}
				}
				if (TransTypeEnum.USER_TOP_UP_E_BANK.equals(transTypeEnum)) {
					// 1.2 理财用户快捷充值（网银） 千2/笔，无最低无封顶
					if(amount == null){
						throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR);
					}
					BsSysConfig config = bsSysConfigMapper.selectByPrimaryKey(Constants.BAOFOO_FEE_E_BANK);
					Double rate = MoneyUtil.divide(Double.valueOf(config.getConfValue()), 1000, 6).doubleValue();
					//金额-分
					threePartyPaymentServiceFee = MoneyUtil.multiply(amount, 100).doubleValue();
					//手续费-分
					threePartyPaymentServiceFee = MoneyUtil.multiply(threePartyPaymentServiceFee, rate).doubleValue();
					//手续费-分(四舍五入)
					threePartyPaymentServiceFee = (double) Math.round(threePartyPaymentServiceFee);
					//手续费-元
					threePartyPaymentServiceFee = MoneyUtil.divide(threePartyPaymentServiceFee, 100, 2).doubleValue();
				}
				if (TransTypeEnum.USER_WITHDRAW.equals(transTypeEnum)) {
					// 2. 理财用户提现 1元/笔
					BsSysConfig config = bsSysConfigMapper.selectByPrimaryKey(Constants.BAOFOO_FEE_DF);
					threePartyPaymentServiceFee = Double.valueOf(config.getConfValue());
				}
				if (TransTypeEnum.LOAN_USER_LOAN.equals(transTypeEnum) || TransTypeEnum.ZSD_LOAN.equals(transTypeEnum)) {
					// 3. 赞分期借款用户借款 1元/笔
					BsSysConfig baooFooFeeWithdrawConfig = bsSysConfigMapper.selectByPrimaryKey(Constants.BAOFOO_FEE_DF);
					threePartyPaymentServiceFee = Double.valueOf(baooFooFeeWithdrawConfig.getConfValue());
				}
				if (TransTypeEnum.LOAN_USER_REPAY_QUICK_PAY.equals(transTypeEnum)) {
					// 4.1 赞分期借款用户还款（快捷） 千2/笔（最低2元，封顶10元）
					if(amount == null){
						throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR);
					}
					BsSysConfig config = bsSysConfigMapper.selectByPrimaryKey(Constants.BAOFOO_FEE_QUICK_PAY);
					Double rate = MoneyUtil.divide(Double.valueOf(config.getConfValue()), 1000, 6).doubleValue();
					//金额-分
					threePartyPaymentServiceFee = MoneyUtil.multiply(amount, 100).doubleValue();
					//手续费-分
					threePartyPaymentServiceFee = MoneyUtil.multiply(threePartyPaymentServiceFee, rate).doubleValue();
					//手续费-分(四舍五入)
					threePartyPaymentServiceFee = (double) Math.round(threePartyPaymentServiceFee);
					//手续费-元
					threePartyPaymentServiceFee = MoneyUtil.divide(threePartyPaymentServiceFee, 100, 2).doubleValue();

					if(threePartyPaymentServiceFee < 2) {
						threePartyPaymentServiceFee = 2d;
					} else if (threePartyPaymentServiceFee > 10) {
						threePartyPaymentServiceFee = 10d;
					}
				}
				if (TransTypeEnum.LOAN_USER_REPAY_E_BANK.equals(transTypeEnum)) {
					// 4.2 赞分期借款用户还款（网银） 千2/笔，无最低无封顶
					if(amount == null){
						throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR);
					}
					BsSysConfig config = bsSysConfigMapper.selectByPrimaryKey(Constants.BAOFOO_FEE_E_BANK);
					Double rate = MoneyUtil.divide(Double.valueOf(config.getConfValue()), 1000, 6).doubleValue();
					//金额-分
					threePartyPaymentServiceFee = MoneyUtil.multiply(amount, 100).doubleValue();
					//手续费-分
					threePartyPaymentServiceFee = MoneyUtil.multiply(threePartyPaymentServiceFee, rate).doubleValue();
					//手续费-分(四舍五入)
					threePartyPaymentServiceFee = (double) Math.round(threePartyPaymentServiceFee);
					//手续费-元
					threePartyPaymentServiceFee = MoneyUtil.divide(threePartyPaymentServiceFee, 100, 2).doubleValue();
				}
				if (TransTypeEnum.PARTNER_MARKET_FEE.equals(transTypeEnum)) {
					// 5. 赞分期营销费用提现 1元/笔
					BsSysConfig config = bsSysConfigMapper.selectByPrimaryKey(Constants.BAOFOO_FEE_DF);
					threePartyPaymentServiceFee = Double.valueOf(config.getConfValue());
				}
				if (TransTypeEnum.ZAN_REPAY_DK.equals(transTypeEnum) || TransTypeEnum.ZSD_REPAY_DK.equals(transTypeEnum)) {
					// 6.赞分期代扣	3元/笔
					BsSysConfig config = bsSysConfigMapper.selectByPrimaryKey(Constants.BAOFOO_FEE_DK);
					threePartyPaymentServiceFee = Double.valueOf(config.getConfValue());
				}

				if (TransTypeEnum.YUN_DAI_SELF_LOAN.equals(transTypeEnum) ||
						TransTypeEnum.SEVEN_DAI_SELF_LOAN.equals(transTypeEnum)) {
					// 7. 云贷自主借款用户借款	1元/笔
					BsSysConfig baooFooFeeWithdrawConfig = bsSysConfigMapper.selectByPrimaryKey(Constants.BAOFOO_FEE_DF);
					threePartyPaymentServiceFee = Double.valueOf(baooFooFeeWithdrawConfig.getConfValue());
				}
				if (TransTypeEnum.YUN_DAI_SELF_REPAY_QUICK_PAY.equals(transTypeEnum) ||
						TransTypeEnum.SEVEN_DAI_SELF_REPAY_QUICK_PAY.equals(transTypeEnum)) {
					// 8.1 云贷自主借款用户还款（快捷）	千2/笔（最低2元，封顶10元）
					if(amount == null){
						throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR);
					}
					BsSysConfig config = bsSysConfigMapper.selectByPrimaryKey(Constants.BAOFOO_FEE_QUICK_PAY);
					Double rate = MoneyUtil.divide(Double.valueOf(config.getConfValue()), 1000, 6).doubleValue();
					//金额-分
					threePartyPaymentServiceFee = MoneyUtil.multiply(amount, 100).doubleValue();
					//手续费-分
					threePartyPaymentServiceFee = MoneyUtil.multiply(threePartyPaymentServiceFee, rate).doubleValue();
					//手续费-分(四舍五入)
					threePartyPaymentServiceFee = (double) Math.round(threePartyPaymentServiceFee);
					//手续费-元
					threePartyPaymentServiceFee = MoneyUtil.divide(threePartyPaymentServiceFee, 100, 2).doubleValue();

					if(threePartyPaymentServiceFee < 2) {
						threePartyPaymentServiceFee = 2d;
					} else if (threePartyPaymentServiceFee > 10) {
						threePartyPaymentServiceFee = 10d;
					}
				}
				if (TransTypeEnum.YUN_DAI_SELF_REPAY_E_BANK.equals(transTypeEnum) ||
						TransTypeEnum.SEVEN_DAI_SELF_REPAY_E_BANK.equals(transTypeEnum)) {
					// 8.2 云贷自主借款用户还款（网银） 千2/笔，无最低无封顶
					if(amount == null){
						throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR);
					}
					BsSysConfig config = bsSysConfigMapper.selectByPrimaryKey(Constants.BAOFOO_FEE_E_BANK);
					Double rate = MoneyUtil.divide(Double.valueOf(config.getConfValue()), 1000, 6).doubleValue();
					//金额-分
					threePartyPaymentServiceFee = MoneyUtil.multiply(amount, 100).doubleValue();
					//手续费-分
					threePartyPaymentServiceFee = MoneyUtil.multiply(threePartyPaymentServiceFee, rate).doubleValue();
					//手续费-分(四舍五入)
					threePartyPaymentServiceFee = (double) Math.round(threePartyPaymentServiceFee);
					//手续费-元
					threePartyPaymentServiceFee = MoneyUtil.divide(threePartyPaymentServiceFee, 100, 2).doubleValue();
				}
				if (TransTypeEnum.YUN_DAI_SELF_DK.equals(transTypeEnum) || TransTypeEnum.SEVEN_DAI_SELF_DK.equals(transTypeEnum)) {
					// 8.3云贷||7贷自主代扣	3元/笔
					BsSysConfig config = bsSysConfigMapper.selectByPrimaryKey(Constants.BAOFOO_FEE_DK);
					threePartyPaymentServiceFee = Double.valueOf(config.getConfValue());
				}
			}else if(PayPlatformEnum.HF_BAOFOO.equals(payPlatform)){
				if (TransTypeEnum.YUN_DAI_SELF_LOAN.equals(transTypeEnum) ||
						TransTypeEnum.SEVEN_DAI_SELF_LOAN.equals(transTypeEnum)) {
					// 7. 云贷自主借款用户借款	1元/笔       7贷同云贷
					BsSysConfig baooFooFeeWithdrawConfig = bsSysConfigMapper.selectByPrimaryKey(Constants.BAOFOO_FEE_DF);
					threePartyPaymentServiceFee = Double.valueOf(baooFooFeeWithdrawConfig.getConfValue());
				}
			}
		}
		return threePartyPaymentServiceFee;
	}

}
