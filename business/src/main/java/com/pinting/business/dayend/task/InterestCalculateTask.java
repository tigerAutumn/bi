package com.pinting.business.dayend.task;

import com.pinting.business.accounting.service.AccountingStatisticsService;
import com.pinting.business.model.*;
import com.pinting.business.model.vo.BsDailyBonusVO;
import com.pinting.business.model.vo.BsDailyInterestVO;
import com.pinting.business.model.vo.BsSubAc4InterestVO;
import com.pinting.business.model.vo.BsUserBonusVO;
import com.pinting.business.service.manage.BsSpecialJnlService;
import com.pinting.business.service.manage.MSysConfigService;
import com.pinting.business.service.site.*;
import com.pinting.business.util.Constants;
import com.pinting.core.util.DateUtil;
import com.pinting.core.util.MoneyUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.*;
import java.util.Map.Entry;

/**
 * 利息计算 计算所有用户 
 * 1、当天 所购买产品 产生利息{ 
 * 	利息=本金×利率×时间 
 * 	日利率=年利率÷360 
 * 	月利率=年利率÷12
 * 	*当天某产品利息=本金×产品年利率÷360×时间1天 
 * }
 * 
 * 2、当天 奖励金 收益{ 
 * 	*当天奖励金收益=被推荐人购买产品本金(投资中)×提成比例÷360×时间1天 
 * }
 * 
 * @Project: business
 * @Title: InterestCalculateTask.java
 * @author dingpf
 * @date 2015-2-4 下午2:24:11
 * @Copyright: 2015 BiGangWan.com Inc. All rights reserved.
 */
@Service
public class InterestCalculateTask {
	private Logger log = LoggerFactory.getLogger(InterestCalculateTask.class);
	@Autowired
	private TransactionTemplate transactionTemplate;
	@Autowired
	private AccountingStatisticsService accountingStatisticsService;
	@Autowired
	private SubAccountService subAccountService;
	@Autowired
	private UserService userService;
	@Autowired
	private InvestService investService;
	@Autowired
	private BsSpecialJnlService bsSpecialJnlService;
	@Autowired
	private MSysConfigService mSysConfigService;
	@Autowired
	private BonusService bonusService;
	@Autowired
	private SMSService smsService;

	/**
	 * 任务执行
	 */
	public void execute() {
		// 日终【到期投资状态修改】
		expireInvestInterestSettleTask();
		// 日终【计算每日投资利息收益】
		calculateInvestInterestTask();
		// 日终【计算每日奖励金收益】
		//calculateBonusEarningsTask();
	}

	private void expireInvestInterestSettleTask() {
		log.info("==================日终【到期投资状态修改】开始====================");
		try {
			
			//修改所有到期投资 的状态为7：结算中
			accountingStatisticsService.modifyExpireInvestInterest(new Date());
			
		} catch (Exception e) {
			// 由于跑批失败，此处需记录失败信息 到 特殊交易流水表
			log.error("==================日终【到期投资状态修改】失败，【特殊交易流水表】新增失败记录====================", e);
			String type = "日终【到期投资状态修改】";
			String detail = "【" + DateUtil.format(new Date()) + "】日终：到期投资状态修改跑批失败";
			bsSpecialJnlService.addSpecialJnl(type, detail);
			smsService.sendSysManagerMobiles("到期投资状态修改跑批失败",true);
		}
		log.info("==================日终【到期投资状态修改】结束====================");

	}

	private void calculateInvestInterestTask() {
		log.info("==================日终【计算每日投资利息】开始====================");
		try {
			// 计算所有用户 当天 所购买产品 产生利息 及其他所需信息
			final List<BsSubAc4InterestVO> productInterests = accountingStatisticsService
					.findInterestForProduct(DateUtil.getDateBegin(new Date()));
			final List<BsDailyInterestVO> userInterests = accountingStatisticsService
					.findUserDailyInterest(DateUtil.getDateBegin(new Date()));
			// 事务方式，当更新有误，则该天计息全部回滚，并新增【特殊交易流水表】失败记录，可能需提供其他途径继续进行该天息计算
			if (productInterests != null && productInterests.size() > 0) {
				log.info("==================日终【计算每日利息】数据更新开始====================");
				int uLength = userInterests.size();
				int uSize = 1000;
				int uMaxPage = uLength%uSize==0 ? uLength/uSize : uLength/uSize + 1;
				for (int i = 1; i <= uMaxPage; i++){
					log.info("==================日终【计算每日利息】截取userInterests子列表："+uSize*(i-1)+":"+(uLength<(uSize*i)?uLength:(uSize*i))+"====================");
					List<BsDailyInterestVO> userInterestsSub = userInterests.subList(uSize*(i-1), uLength<(uSize*i)?uLength:(uSize*i));
					// 批量新增每日利息表数据，并批量修改所有用户累计投资收益金额及当前投资收益金额
					batchAddDailyInterestAndUpdateUserTotalInterest(userInterestsSub);
					try {
						Thread.sleep(500);
					}catch (Exception e){
						e.printStackTrace();
					}
				}
				int pLength = productInterests.size();
				int pSize = 1000;
				int pMaxPage = pLength%pSize==0 ? pLength/pSize : pLength/pSize + 1;
				for (int i = 1; i <= pMaxPage; i++){
					log.info("==================日终【计算每日利息】截取productInterests子列表："+pSize*(i-1)+":"+(pLength<(pSize*i)?pLength:(pSize*i))+"====================");
					List<BsSubAc4InterestVO> productInterestsSub = productInterests.subList(pSize*(i-1), pLength<(pSize*i)?pLength:(pSize*i));
					// 批量更新 所有产品户的累计利息
					batchUpdateInterestForSubAccount(productInterestsSub);

					try {
						Thread.sleep(500);
					}catch (Exception e){
						e.printStackTrace();
					}
				}
				log.info("==================日终【计算每日利息】数据更新结束====================");
			}
		} catch (Exception e) {
			// 由于跑批失败，此处需记录失败信息 到 特殊交易流水表
			log.error("==================日终【计算每日利息】失败，【特殊交易流水表】新增失败记录====================", e);
			String type = "日终【计算每日利息】";
			String detail = "【" + DateUtil.format(new Date()) + "】日终：计算利息跑批失败";
			bsSpecialJnlService.addSpecialJnl(type, detail);
			smsService.sendSysManagerMobiles("计算每日利息跑批失败",true);
		}
		log.info("==================日终【计算每日投资利息】结束====================");
	}

	private void calculateBonusEarningsTask() {
		log.info("==================日终【计算每日奖励金收益】开始====================");
		try {
			// 查询（计算）各被推荐人信息及其投资金额信息 列表
			final List<BsUserBonusVO> userBonuss = accountingStatisticsService
					.findRecommendedUserAndInvestAmount();
			boolean isSucc = transactionTemplate
					.execute(new TransactionCallback<Boolean>() {
						@Override
						public Boolean doInTransaction(TransactionStatus ts) {
							try {
								if (userBonuss != null && userBonuss.size() > 0) {
									log.info("==================日终【计算每日奖励金收益】数据更新开始====================");
									// 批量新增每日奖励金表数据，并批量修改所有推荐人用户累计奖励金收益金额、结算户余额信息
									batchAddDailyBonusAndUpdateUserBonus(userBonuss);
									log.info("==================日终【计算每日奖励金收益】数据更新开始====================");
								}
							} catch (Exception e) {
								ts.setRollbackOnly();
								log.error(
										"==================日终【计算每日奖励金收益】更新数据库失败====================",
										e);
								return false;
							}
							return true;
						}
					});
			if (!isSucc) {
				throw new Exception("日终【计算每日奖励金收益】失败");
			}
		} catch (Exception e) {
			// 由于跑批失败，此处需记录失败信息 到 特殊交易流水表
			log.error("==================日终【计算每日奖励金收益】失败，【特殊交易流水表】新增失败记录====================", e);
			String type = "日终【计算每日奖励金收益】";
			String detail = "【" + DateUtil.format(new Date())
					+ "】日终：计算每日奖励金收益跑批失败";
			bsSpecialJnlService.addSpecialJnl(type, detail);
			smsService.sendSysManagerMobiles("计算每日奖励金收益跑批失败",true);
		}
		log.info("==================日终【计算每日奖励金收益】结束====================");
	}

	/**
	 * 批量新增每日奖励金表数据，并批量修改所有推荐人用户累计奖励金收益金额、结算户余额信息
	 * 
	 * @param userBonuss
	 */
	protected void batchAddDailyBonusAndUpdateUserBonus(
			List<BsUserBonusVO> userBonuss) {
		// 推荐金提成查询
		BsSysConfig bsSysConfig = mSysConfigService
				.findSysConfigByKey(Constants.PUSH_MONEY_RATE);
		Double pushMoneyRate = MoneyUtil.divide(Double.valueOf(bsSysConfig.getConfValue()), 100,4).doubleValue();
		// 推荐人收益集合，用于后面更新结算户余额和累计奖励金收益字段
		Map<Integer, Double> recommendBonusMap = new HashMap<Integer, Double>();
		// 批量新增用户每日奖励金记录
		for (BsUserBonusVO userBonus : userBonuss) {
			// bs_daily_bonus表插入记录
			Double pushMoney = MoneyUtil.divide(
					MoneyUtil.multiply(userBonus.getValidProductAmount(), pushMoneyRate).doubleValue(), 360, 2).doubleValue();
					/*(userBonus.getValidProductAmount() * pushMoneyRate) / 360;*/
			Double bonus = recommendBonusMap.get(userBonus.getRecommendId());
			// 该用户该天增加的奖励金
			if (bonus == null) {
				recommendBonusMap.put(userBonus.getRecommendId(), pushMoney);
			} else {
				recommendBonusMap.put(userBonus.getRecommendId(), MoneyUtil.add(bonus,pushMoney).doubleValue());
			}
			BsDailyBonus dailyBonus = new BsDailyBonus();
			dailyBonus.setUserId(userBonus.getRecommendId());
			dailyBonus.setBeRecommendUserId(userBonus.getId());
			dailyBonus.setBonus(pushMoney);
			dailyBonus.setTime(new Date());
			bonusService.addDailyBonus(dailyBonus);
		}

		// 批量更新结算户余额，以及用户累计奖励金收益、当前奖励金、可提现金
		batchUpdateJSHBalanceAndUserBonus(recommendBonusMap);

	}

	/**
	 * 批量更新结算户余额，以及用户累计奖励金收益、当前奖励金、可提现金
	 * 
	 * @param recommendBonusMap
	 */
	protected void batchUpdateJSHBalanceAndUserBonus(
			Map<Integer, Double> recommendBonusMap) {
		// 奖励金提现天数限制 获得
		BsSysConfig bsSysConfig = mSysConfigService
				.findSysConfigByKey(Constants.BONUS_WITHDRAW_DAYS);
		Set<Entry<Integer, Double>> recommendBonuss = recommendBonusMap
				.entrySet();
		for (Entry<Integer, Double> entry : recommendBonuss) {
			// 统计30天（可调）前一天 被解冻结的奖励金
			BsDailyBonusVO bonus = bonusService
					.findSumDailyBonusByUserIdAndTime(entry.getKey(), DateUtil
							.addDays(new Date(), -Integer.valueOf(bsSysConfig
									.getConfValue())));
			if (bonus != null) {
				Double unfreezeBonus = 0d;
				if (bonus.getBonus() != null) {
					unfreezeBonus = bonus.getBonus();
				}
				Integer jshSubAccountId = bonus.getSubAccountId();
				Double currentDayBonus = entry.getValue();
				Integer userId = entry.getKey();
				// 更新结算户（原余额+原可用余额+原可提现余额+原冻结余额）
				// 余额=原余额+当天奖励金
				// 可提现余额=原可提现余额+30天前一天 被解冻结的奖励金
				// 可用余额=原可用余额+30天前一天 被解冻结的奖励金
				// 冻结余额=原冻结余额+当天奖励金-30天前一天 被解冻结的奖励金
				BsSubAccount jshSubAccount = new BsSubAccount();
				jshSubAccount.setId(jshSubAccountId);
				jshSubAccount.setBalance(currentDayBonus);
				jshSubAccount.setAvailableBalance(unfreezeBonus);
				jshSubAccount.setCanWithdraw(unfreezeBonus);
				jshSubAccount.setFreezeBalance(MoneyUtil.subtract(currentDayBonus, unfreezeBonus).doubleValue());
				subAccountService.modifyBalancesByIncrement(jshSubAccount);
				// 更新用户（原当前剩余奖励金+原累计奖励金+原可提现奖励金）
				// 当前剩余奖励金=原当前剩余奖励金+当天奖励金
				// 累计奖励金=原累计奖励金+当天奖励金
				// 可提现余额=原可提现余额+30天前一天 被解冻结的奖励金
				BsUser user = new BsUser();
				user.setId(userId);
				user.setCurrentBonus(currentDayBonus);
				user.setCanWithdraw(unfreezeBonus);
				user.setTotalBonus(currentDayBonus);
				userService.modifyBonusByIdAndIncrement(user);
			}
		}
	}

	/**
	 * 批量新增每日利息表数据，并批量修改所有用户累计投资收益金额及当前投资收益金额
	 * 
	 * @param userInterests
	 */
	protected void batchAddDailyInterestAndUpdateUserTotalInterest(final List<BsDailyInterestVO> userInterests) {
		transactionTemplate.execute(new TransactionCallback<Boolean>() {
			@Override
			public Boolean doInTransaction(TransactionStatus ts) {
				try {
					for (BsDailyInterestVO userInterest : userInterests) {
						BsUser bsUser = new BsUser();
						bsUser.setId(userInterest.getUserId());
						bsUser.setTotalInterest(userInterest.getInterest());
						bsUser.setCurrentInterest(userInterest.getInterest());
						userService.modifyUserAmountIncrement(bsUser);

						// daily_inerest表
						BsDailyInterest bsDailyInterest = new BsDailyInterest();
						bsDailyInterest.setUserId(userInterest.getUserId());
						bsDailyInterest.setInterest(MoneyUtil.defaultRound(
								userInterest.getInterest()).doubleValue());
						bsDailyInterest.setNote("投资利息");
						bsDailyInterest.setTime(new Date());
						investService.addInvestService(bsDailyInterest);
					}
				} catch (Exception e) {
					ts.setRollbackOnly();
					log.error("==================日终【计算每日利息】更新数据库失败====================", e);
					String type = "日终【计算每日利息】子列表";
					String detail = "日终【计算每日利息】子列表执行异常";
					bsSpecialJnlService.addSpecialJnl(type, detail);
					smsService.sendSysManagerMobiles("每日利息计算子列表",true);
					return false;
				}
				return true;
			}
		});
	}

	/**
	 * 批量更新所有产品户的累计利息
	 * 
	 * @param productInterests
	 */
	protected void batchUpdateInterestForSubAccount(final List<BsSubAc4InterestVO> productInterests) {
		transactionTemplate.execute(new TransactionCallback<Boolean>() {
			@Override
			public Boolean doInTransaction(TransactionStatus ts) {
				try {
					for (BsSubAc4InterestVO productInterest : productInterests) {
						// sub_account表
						BsSubAccount bsSubAccount = new BsSubAccount();
						bsSubAccount.setId(productInterest.getId());
						bsSubAccount.setLastCalInterestDate(new Date());
						// 截止前一天该产品的累计利息
						Double accuInterest = productInterest.getAccumulationInerest();
						// 计算截止今天该产品应该得到的累计利息
						Double productAccuInterest = productInterest
								.getProductAccuInterest();
						// 更新产品累计利息
						bsSubAccount.setAccumulationInerest(MoneyUtil.defaultRound(
								productAccuInterest).doubleValue());
						subAccountService.modifySubAccountById(bsSubAccount);
					}
				} catch (Exception e) {
					ts.setRollbackOnly();
					log.error("==================日终【计算每日利息】更新数据库失败====================", e);
					String type = "日终【计算每日利息】子列表";
					String detail = "日终【计算每日利息】子列表执行异常";
					bsSpecialJnlService.addSpecialJnl(type, detail);
					smsService.sendSysManagerMobiles("每日利息计算子列表",true);
					return false;
				}
				return true;
			}
		});
	}

}
