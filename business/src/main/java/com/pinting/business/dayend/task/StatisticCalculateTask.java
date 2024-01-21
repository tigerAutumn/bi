package com.pinting.business.dayend.task;

import com.pinting.business.model.BsStatistics;
import com.pinting.business.service.manage.BsSpecialJnlService;
import com.pinting.business.service.manage.MStatisticsService;
import com.pinting.business.service.site.SubAccountService;
import com.pinting.business.service.site.UserService;
import com.pinting.business.util.Constants;
import com.pinting.core.util.DateUtil;
import com.pinting.core.util.MoneyUtil;
import com.pinting.gateway.in.service.DafyUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.ArrayList;
import java.util.Date;

/**
 * @Project: business
 * @Title: StatisticCalculateTask.java
 * @author Huang MengJian
 * @date 2015-3-2 下午4:43:29
 * @Copyright: 2015 BiGangWan.com Inc. All rights reserved.
 */
@Service
public class StatisticCalculateTask {

	private Logger log = LoggerFactory.getLogger(StatisticCalculateTask.class);
	@Autowired
	private TransactionTemplate transactionTemplate;
	@Autowired
	private SubAccountService subAccountService;
	@Autowired
	private MStatisticsService statisticsService;
	@Autowired
	private UserService userService;
	@Autowired
	private DafyUserService dafyUserService;
	@Autowired
	private BsSpecialJnlService bsSpecialJnlService;
	/**
	 * 任务执行
	 */
	public void execute() {
		
		ArrayList<BsStatistics> value = getStatisticCalculateValue();
		
		insertStatisticCalculateValueTask(value);
	}
	
	/**
	 *  1个月活动理财购买人数：1AcitityProductNum;
		1个月活动理财购买金额：1AcitityProductAmount;
		1个月活动理财平均每笔购买交易额：1AcitityProductAvg;
		1个月活动理财日购买金额：1AcitityProductDay;
		1个月活动理财日购买人数：1AcitityProductNumDay;
		1个月活动理财平均每人购买交易额：1AcitityProductDayAvg;

	 *  1个月理财购买人数：1ProductNum;
		1个月理财购买金额：1ProductAmount;
		1个月理财平均每笔购买交易额：1ProductAvg;
		1个月理财日购买金额：1ProductDay;
		1个月理财日购买人数：1ProductNumDay;
		1个月理财平均每人购买交易额：1ProductDayAvg;
		
	 *  3个月理财购买人数：3ProductNum;
		3个月理财购买金额：3ProductAmount;
		3个月理财平均每笔购买交易额：3ProductAvg;
		3个月理财日购买金额：3ProductDay;
		3个月理财日购买人数：3ProductNumDay;
		3个月理财平均每人购买交易额：3ProductDayAvg;
		
		6个月理财购买人数：6ProductNum;
		6个月理财购买金额：6ProductAmount;
		6个月理财平均每笔购买交易额：6ProductAvg;
		6个月理财日购买金额：6ProductDay;
		6个月理财日购买人数：6ProductNumDay;
		6个月理财平均每人购买交易额：6ProductDayAvg;
		
		12个月理财购买人数：12ProductNum;
		12个月理财购买金额：12ProductAmount;
		12个月理财平均每笔购买交易额：12ProductAvg;
		12个月理财日购买金额：12ProductDay;
		12个月理财日购买人数：12ProductNumDay;
		12个月理财平均每人购买交易额：12ProductDayAvg;
		
		全部理财购买总人数：ProductNumSum;
		全部理财购买总金额金额：ProductAmountSum;
		全部理财平均每笔购买交易额：ProductAvgSum;
		全部理财日购买金额：ProductDaySum;
		全部理财日购买人数：ProductNumDaySum;
		全部理财平均每人购买交易额：ProductDayAvgSum;
		
		
		转让理财购买人数：turnProductNum;
		转让理财购买金额：turnProductAmount;
		转让理财平均每笔购买交易额：turnProductAvg;
		转让理财日购买金额：turnProductDay;
		
		总计注册人数： SumRegNum
		总计实名绑卡人数：SumCardNum
		每日注册人数：DayRegNum
		每日实名绑卡人数：DayCardNum
		
		200元以下理财产品购买次数统计：1ProductBuyCount
		200-1000理财产品购买次数统计: 2ProductBuyCount
		1000-10000理财产品购买次数统计: 3ProductBuyCount
		10000-100000理财产品购买次数统计: 4ProductBuyCount
		100000以上理财产品购买次数统计：5ProductBuyCount
	 */
	
	/**
	 * 查询所有的统计的数据
	 * @return
	 */
	protected ArrayList<BsStatistics> getStatisticCalculateValue(){
		ArrayList<BsStatistics> statisticsList = new ArrayList<BsStatistics>();
		
		/**
		 * 1.购买人数:总人数 =  count(投资中 + 已转让 + 已结算) 去除相同账户      ProductNum
		 * 2.购买金额: 总金额 = sum(投资中 + 转让中 + 已结算 )       ProductAmount
		 * 3 平均每人购买交易额：购买金额 /购买人数                                             ProductDayAvg
		 * 4.日购买人数：总人数 = count(投资中)     去除相同账户 	   ProductNumDay
		 * 5.日购买金额：总金额 = sum(投资中) - sum(已转让)        ProductDay
		 * 6.平均每笔购买交易额：购买金额/购买次数                                            ProductAvg
		 */
		
		//开始组装1月活动理财产品的统计
		int productNumActivity1 = subAccountService.countProductNumByProductCode(Constants.PRODUCT_CODE_1_120);
		statisticsList.add(createBsStatistics("1ActivityProductNum","1个月活动理财购买人数",productNumActivity1));
		double productAmountActivity1 = subAccountService.countProductAmountByProductCode(Constants.PRODUCT_CODE_1_120);
		statisticsList.add(createBsStatistics("1ActivityProductAmount","1个月活动理财购买金额",productAmountActivity1));
		
		double productDayAvgActivity1 = 0;
		if(productNumActivity1 != 0){
			productDayAvgActivity1 = MoneyUtil.divide(productAmountActivity1, productNumActivity1, 2).doubleValue();
			//productAmountActivity1/productNumActivity1;
		}
		statisticsList.add(createBsStatistics("1ActivityProductDayAvg","1个月活动理财平均每人购买交易额",productDayAvgActivity1));
		
		
		int productNumDayActivity1 = subAccountService.countProductNumDayByProductCode(Constants.PRODUCT_CODE_1_120);
		statisticsList.add(createBsStatistics("1ActivityProductNumDay","1个月活动理财日购买人数",productNumDayActivity1));
		
		double productDayActivity1 = subAccountService.countProductDayByProductCode(Constants.PRODUCT_CODE_1_120);
		statisticsList.add(createBsStatistics("1ActivityProductDay","1个月活动理财日购买金额",productDayActivity1));
		
		int buyCountActivity1 = subAccountService.countProductBuyNum(Constants.PRODUCT_CODE_1_120);
		double productAvgActivity1 = 0;
		if(buyCountActivity1 != 0){
			productAvgActivity1 = MoneyUtil.divide(productAmountActivity1, buyCountActivity1, 2).doubleValue();
		}
		statisticsList.add(createBsStatistics("1ActivityProductAvg","1个月活动理财平均每笔购买交易额",productAvgActivity1));
		
		
		
		//开始组装1月理财产品的统计
		int productNum1 = subAccountService.countProductNumByProductCode(Constants.PRODUCT_CODE_1_70);
		statisticsList.add(createBsStatistics("1ProductNum","1个月理财购买人数",productNum1));
		double productAmount1 = subAccountService.countProductAmountByProductCode(Constants.PRODUCT_CODE_1_70);
		statisticsList.add(createBsStatistics("1ProductAmount","1个月理财购买金额",productAmount1));
		
		double productDayAvg1 = 0;
		if(productNum1 != 0){
			productDayAvg1 = MoneyUtil.divide(productAmount1, productNum1, 2).doubleValue();
		}
		statisticsList.add(createBsStatistics("1ProductDayAvg","1个月理财平均每人购买交易额",productDayAvg1));
		
		
		int productNumDay1 = subAccountService.countProductNumDayByProductCode(Constants.PRODUCT_CODE_1_70);
		statisticsList.add(createBsStatistics("1ProductNumDay","1个月理财日购买人数",productNumDay1));
		
		double productDay1 = subAccountService.countProductDayByProductCode(Constants.PRODUCT_CODE_1_70);
		statisticsList.add(createBsStatistics("1ProductDay","1个月理财日购买金额",productDay1));
		
		int buyCount1 = subAccountService.countProductBuyNum(Constants.PRODUCT_CODE_1_70);
		double productAvg1 = 0;
		if(buyCount1 != 0){
			productAvg1 = MoneyUtil.divide(productAmount1, buyCount1, 2).doubleValue();
		}
		statisticsList.add(createBsStatistics("1ProductAvg","1个月理财平均每笔购买交易额",productAvg1));
		
		
		//开始组装3月理财产品的统计
		int productNum3 = subAccountService.countProductNumByProductCode(Constants.PRODUCT_CODE_3_80);
		statisticsList.add(createBsStatistics("3ProductNum","3个月理财购买人数",productNum3));
		
		double productAmount3 = subAccountService.countProductAmountByProductCode(Constants.PRODUCT_CODE_3_80);
		statisticsList.add(createBsStatistics("3ProductAmount","3个月理财购买金额",productAmount3));
		
		double productDayAvg3 = 0;
		if(productNum3 != 0){
			productDayAvg3 = MoneyUtil.divide(productAmount3, productNum3, 2).doubleValue();
		}
		statisticsList.add(createBsStatistics("3ProductDayAvg","3个月理财平均每人购买交易额",productDayAvg3));
		
		
		int productNumDay3 = subAccountService.countProductNumDayByProductCode(Constants.PRODUCT_CODE_3_80);
		statisticsList.add(createBsStatistics("3ProductNumDay","3个月理财日购买人数",productNumDay3));
		
		double productDay3 = subAccountService.countProductDayByProductCode(Constants.PRODUCT_CODE_3_80);
		statisticsList.add(createBsStatistics("3ProductDay","3个月理财日购买金额",productDay3));
		
		int buyCount3 = subAccountService.countProductBuyNum(Constants.PRODUCT_CODE_3_80);
		double productAvg3 = 0;
		if(buyCount3 != 0){
			productAvg3 = MoneyUtil.divide(productAmount3, buyCount3, 2).doubleValue();
		}
		statisticsList.add(createBsStatistics("3ProductAvg","3个月理财平均每笔购买交易额",productAvg3));
		
		//开始组装6月理财产品的统计
		int productNum6 = subAccountService.countProductNumByProductCode(Constants.PRODUCT_CODE_6_90);
		statisticsList.add(createBsStatistics("6ProductNum","6个月理财购买人数",productNum6));
		
		double productAmount6 = subAccountService.countProductAmountByProductCode(Constants.PRODUCT_CODE_6_90);
		statisticsList.add(createBsStatistics("6ProductAmount","6个月理财购买金额",productAmount6));
		
		double productDayAvg6 = 0;
		if(productNum6 != 0){
			productDayAvg6 = MoneyUtil.divide(productAmount6, productNum6, 2).doubleValue();
		}
		statisticsList.add(createBsStatistics("6ProductDayAvg","6个月理财平均每人购买交易额",productDayAvg6));
		
		int productNumDay6 = subAccountService.countProductNumDayByProductCode(Constants.PRODUCT_CODE_6_90);
		statisticsList.add(createBsStatistics("6ProductNumDay","6个月理财日购买人数",productNumDay6));
		
		double productDay6 = subAccountService.countProductDayByProductCode(Constants.PRODUCT_CODE_6_90);
		statisticsList.add(createBsStatistics("6ProductDay","6个月理财日购买金额",productDay6));
		
		int buyCount6 = subAccountService.countProductBuyNum(Constants.PRODUCT_CODE_6_90);
		double productAvg6 = 0;
		if(buyCount6 != 0){
			productAvg6 = MoneyUtil.divide(productAmount6, buyCount6, 2).doubleValue();
		}
		statisticsList.add(createBsStatistics("6ProductAvg","6个月理财平均每笔购买交易额",productAvg6));
		
		//开始组装12月理财产品的统计
		int productNum12 = subAccountService.countProductNumByProductCode(Constants.PRODUCT_CODE_12_132);
		statisticsList.add(createBsStatistics("12ProductNum","12个月理财购买人数",productNum12));
		
		double productAmount12 = subAccountService.countProductAmountByProductCode(Constants.PRODUCT_CODE_12_132);
		statisticsList.add(createBsStatistics("12ProductAmount","12个月理财购买金额",productAmount12));
		
		double productDayAvg12 = 0;
		if(productNum12 != 0){
			productDayAvg12 = MoneyUtil.divide(productAmount12, productNum12, 2).doubleValue();
		}
		statisticsList.add(createBsStatistics("12ProductDayAvg","12个月理财平均每人购买交易额",productDayAvg12));
		
		int productNumDay12 = subAccountService.countProductNumDayByProductCode(Constants.PRODUCT_CODE_12_132);
		statisticsList.add(createBsStatistics("12ProductNumDay","12个月理财日购买人数",productNumDay12));
		
		double productDay12 = subAccountService.countProductDayByProductCode(Constants.PRODUCT_CODE_12_132);
		statisticsList.add(createBsStatistics("12ProductDay","12个月理财日购买金额",productDay12));
		
		int buyCount12 = subAccountService.countProductBuyNum(Constants.PRODUCT_CODE_12_132);
		double productAvg12 = 0;
		if(buyCount12 != 0){
			productAvg12 = MoneyUtil.divide(productAmount12, buyCount12, 2).doubleValue();
		}
		statisticsList.add(createBsStatistics("12ProductAvg","12个月理财平均每笔购买交易额",productAvg12));
		
		/**
		 * 1.全部理财购买总人数：ProductNumSum;
		 * 2.全部理财购买总金额金额：ProductAmountSum;
		 * 3.全部理财平均每人购买交易额：ProductDayAvgSum;
		 * 4.全部理财日购买金额：ProductDaySum;
		 * 5.全部理财日购买人数：ProductNumDaySum;
		 * 6.全部理财平均每笔购买交易额：ProductAvgSum;
		 */
		int productNumSum = subAccountService.countProductNumByProductCode(null);
		statisticsList.add(createBsStatistics("ProductNumSum","全部理财购买总人数",productNumSum));
		statisticsList.add(createBsStatistics("ProductAmountSum","全部理财购买总金额",sumAmount(productAmountActivity1, productAmount1, productAmount3,productAmount6,productAmount12)));
		
		statisticsList.add(createBsStatistics("ProductDaySum","全部理财日购买金额",sumAmount(productDayActivity1, productDay1, productDay3, productDay6, productDay12)));
		int productDaySum = subAccountService.countProductNumDayByProductCode(null);
		statisticsList.add(createBsStatistics("ProductNumDaySum","全部理财日购买人数",productDaySum));
		
		statisticsList.add(createBsStatistics("ProductDayAvgSum","全部理财平均每人购买交易额",
				MoneyUtil.divide(sumAmount(productAmountActivity1,productAmount1, productAmount3,productAmount6,productAmount12), productNumSum, 2).doubleValue()));
		statisticsList.add(createBsStatistics("ProductAvgSum","全部理财平均每笔购买交易额",MoneyUtil.divide(sumAmount(productAmountActivity1, productAmount1, productAmount3, productAmount6, productAmount12),
				sumAmount(buyCountActivity1, buyCount1, buyCount3, buyCount6,buyCount12)).doubleValue()));
		
		
		/**
		 * 转让理财购买人数：turnProductNum;
		 * 转让理财购买金额：turnProductAmount;
		 * 转让理财平均每笔购买交易额：turnProductAvg;
		 * 转让理财日购买金额：turnProductDay;
		 */
		
		
		/**
		 * 200元以下理财产品购买次数统计：1ProductBuyCount
		 * 200-1000理财产品购买次数统计: 2ProductBuyCount
		 * 1000-10000理财产品购买次数统计: 3ProductBuyCount
		 * 10000-100000理财产品购买次数统计: 4ProductBuyCount
		 * 100000以上理财产品购买次数统计：5ProductBuyCount
		 */
		int productBuyCount1 = subAccountService.countSubAccountBalanceBetweenNaNAndNaN(0,200.0);
		statisticsList.add(createBsStatistics("1ProductBuyCount","200元以下理财产品购买次数统计",productBuyCount1));
		int productBuyCount2 = subAccountService.countSubAccountBalanceBetweenNaNAndNaN(200.0,1000.0);
		statisticsList.add(createBsStatistics("2ProductBuyCount","200-1000理财产品购买次数统计",productBuyCount2));
		int productBuyCount3 = subAccountService.countSubAccountBalanceBetweenNaNAndNaN(1000.0,10000.0);
		statisticsList.add(createBsStatistics("3ProductBuyCount","1000-10000理财产品购买次数统计",productBuyCount3));
		int productBuyCount4 = subAccountService.countSubAccountBalanceBetweenNaNAndNaN(10000.0,100000.0);
		statisticsList.add(createBsStatistics("4ProductBuyCount","10000-100000理财产品购买次数统计",productBuyCount4));
		int productBuyCount5 = subAccountService.countSubAccountBalanceBetweenNaNAndNaN(100000.0,Double.MAX_VALUE);
		statisticsList.add(createBsStatistics("5ProductBuyCount","100000以上理财产品购买次数统计",productBuyCount5));
		
		
		
		/**
		 * 1.总计注册人数:用户中所有的条数   SumRegNum
		 * 2.总计实名绑卡人数：达飞注册表中所有的条数  SumCardNum
		 * 3.每日注册人数：用户中当天的所有条数   DayRegNum
		 * 4.每日实名绑卡人数：当天达飞注册表中所有的条数  DayCardNum
		 */
		
		int sumRegNum = userService.countRegNum();
		statisticsList.add(createBsStatistics("SumRegNum","总计注册人数",sumRegNum));
		
		int sumCardNum = dafyUserService.countCardNum();
		statisticsList.add(createBsStatistics("SumCardNum","总计实名绑卡人数",sumCardNum));
		
		int dayRegNum = userService.countDayRegNum();
		statisticsList.add(createBsStatistics("DayRegNum","每日注册人数",dayRegNum));
		
		int dayCardNum = dafyUserService.countDayCardNum();
		statisticsList.add(createBsStatistics("DayCardNum","每日实名绑卡人数",dayCardNum));
		
		return statisticsList;
	}
	
	private double sumAmount(double amount1,
			double amount2, double amount3,
			double amount4, double amount5) {
		Double amountTemp = MoneyUtil.add(amount1, amount2).doubleValue();
		amountTemp = MoneyUtil.add(amountTemp, amount3).doubleValue();
		amountTemp = MoneyUtil.add(amountTemp, amount4).doubleValue();
		amountTemp = MoneyUtil.add(amountTemp, amount5).doubleValue();
		return amountTemp;
	}

	/**
	 * 插入所有统计的数据
	 * @return
	 */
	protected void insertStatisticCalculateValueTask(final ArrayList<BsStatistics> valueList){
		log.info("==================【每日统计】开始====================");
		try {
			
			boolean isSucc = transactionTemplate
					.execute(new TransactionCallback<Boolean>() {
						@Override
						public Boolean doInTransaction(TransactionStatus ts) {
							try {
								statisticsService.insertValueList(valueList);	
								
							} catch (Exception e) {
								ts.setRollbackOnly();
								log.error(
										"==================【每日统计】插入数据库失败====================",
										e);
								return false;
							}
							return true;
						}
					});
			if (!isSucc) {
				throw new Exception("【每日统计】失败");
			}
		} catch (Exception e) {
			// 由于跑批失败，此处需记录失败信息 到 特殊交易流水表
			log.error("==================【每日统计】新增失败记录====================", e);
			String type = "【每日统计】失败";
			String detail = "【" + DateUtil.format(new Date())
					+ "】日终：每日统计失败";
			bsSpecialJnlService.addSpecialJnl(type, detail);
		}
		log.info("==================日终【每日统计】结束====================");
	}
	
	public BsStatistics createBsStatistics(String type,String typeName,double value){
		BsStatistics bsStatistics = new BsStatistics();
		bsStatistics.setType(type);
		bsStatistics.setTypeName(typeName);
		
		bsStatistics.setTime(DateUtil.parseDate(DateUtil.formatYYYYMMDD(DateUtil.addDays(new Date(),-1))));
		bsStatistics.setValue(value);
		
		return bsStatistics;
	}
}
