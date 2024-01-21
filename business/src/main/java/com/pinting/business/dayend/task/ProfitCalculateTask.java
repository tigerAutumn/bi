package com.pinting.business.dayend.task;

import com.pinting.business.accounting.service.PayOrdersService;
import com.pinting.business.model.BsProfitLoss;
import com.pinting.business.model.vo.BsSubAccountVO;
import com.pinting.business.service.manage.BsSpecialJnlService;
import com.pinting.business.service.manage.BsUserService;
import com.pinting.business.service.manage.MProfiteLossService;
import com.pinting.business.service.manage.MStatisticsService;
import com.pinting.business.service.site.BonusService;
import com.pinting.business.service.site.SMSService;
import com.pinting.business.service.site.SubAccountService;
import com.pinting.core.util.DateUtil;
import com.pinting.core.util.MoneyUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;

/**
 * 币港弯批量计算损益
 * @Project: business
 * @Title: ProfitCalculateTask.java
 * @author Huang MengJian
 * @date 2015-3-13 上午10:21:33
 * @Copyright: 2015 BiGangWan.com Inc. All rights reserved.
 */
@Service
public class ProfitCalculateTask {
	
	private Logger log = LoggerFactory.getLogger(ProfitCalculateTask.class);
	
	@Autowired
	private MProfiteLossService profiteService;
	
	@Autowired
	private SubAccountService subAccountService;
	
	@Autowired
	private MStatisticsService statisticsService;
	
	@Autowired
	private BsUserService userService;
	
	@Autowired
	private PayOrdersService payOrdersService;
	
	@Autowired
	private BonusService bonusService;
	
	@Autowired
	private BsSpecialJnlService bsSpecialJnlService;
	@Autowired
	private SMSService smsService;
	/**
	 * 任务执行
	 */
	public void execute() {
		boolean flag = getProfitDate();
		if(!flag){
			log.error("==================日终【币港湾收益】失败，【特殊交易流水表】新增失败记录====================");
			String type = "日终【币港湾收益】";
			String detail = "【" + DateUtil.format(new Date()) + "】日终：计算利息跑批失败";
			bsSpecialJnlService.addSpecialJnl(type, detail);
			smsService.sendSysManagerMobiles("币港湾收益跑批失败",true);
		}else{
			log.debug("==================日终【币港湾收益】结束");
		}
	}
	
	
	public boolean getProfitDate(){
		
		/**
		 * 一.周期内本金：（比如：三月（90天） - 六月（180天） - 十二月（365天））
		 * 		1.投资中的本金
		 * 		2.当月已结算的本金
		 * 二.周期内用户利息（计息日期数）
		 * 		1.投资中
		 * 			a)购买日期>=1号      		   购买日期+1 - 当日为止
		 * 			b)购买日期<1号（上月）         本月1号 - 当日为止
		 * 		2.已结算
		 * 			a)本月从1号 - 结算日期
		 * 		 周期利息 = （三个月本金 * 8% / 365 * 在投天数）  +  （六个月本金 * 9% / 365 * 在投天数） +  （十二个月本金 * 13.2% / 365 * 在投天数）
		 * 
		 * 三.品听应得收益(不包含用户收益，包含用户奖励金)
		 * 		 应得收益 = [（三个月本金 * 18% / 365 * 在投天数）  +  （六个月本金 * 18% / 365 * 在投天数） +  （十二个月本金 * 18% / 365 * 在投天数） ] - 周期利息
		 * 
		 * 四.品听累计应得净收益
		 * 		 累计应得净收益  = sum(应得收益) - 用户应提奖励金
		 * 
		 * 五.用户应提奖励金
		 * 		 应提奖励金 = 用户每日奖励金表中>0的部分金额的和
		 * 
		 * 六.用户已提奖励金
		 * 		 已提奖励金 = 用户每日奖励金表中<0的部分金额的和
		 * 
		 * 七.剩余奖励金总和
		 * 		 剩余奖励金 = 应提奖励金 - 已提奖励金
		 * 
		 * 八.2%营销费用
		 * 		起息日为本月的所有产品（n个月本金 * 2% / 365 * 投资期限 ）
		 * 
		 * 八.达飞应付收益金
		 * 		 应付收益金 = 品听应得利益 - 本月（1-当日）已体现奖励金
		 */
		
		
		double principal = subAccountService.sumPeriodCapital(); //周期内本金
		double interest = 0.0;//用户利息收益
		double sysInterest = 0.0;//品听本月总收益（包含用户收益）
		//查询金额，本月投资天数，产品利率
		ArrayList<BsSubAccountVO> productInvestMessage = subAccountService.selectCaptialAndInvestDayByProductCode(null);
		if(productInvestMessage != null && productInvestMessage.size() > 0 ){
			for (BsSubAccountVO bsSubAccountVO : productInvestMessage) {
				if(bsSubAccountVO.getInvestDay() < 0){
					continue;
				}
				interest = MoneyUtil.add(interest,
						MoneyUtil.divide(MoneyUtil.multiply(MoneyUtil.multiply(bsSubAccountVO.getBalance(), bsSubAccountVO.getProductRate()).doubleValue(), bsSubAccountVO.getInvestDay()).doubleValue(),
								36500, 2).doubleValue()).doubleValue();
						/*bsSubAccountVO.getBalance() * bsSubAccountVO.getProductRate() / 100 / 365 * bsSubAccountVO.getInvestDay();*/
				sysInterest = MoneyUtil.add( sysInterest,MoneyUtil.divide(
							MoneyUtil.multiply(bsSubAccountVO.getInvestDay(),
									MoneyUtil.multiply(bsSubAccountVO.getBalance(), 0.18).doubleValue()
							).doubleValue(), 365, 2).doubleValue()
						).doubleValue();
						//bsSubAccountVO.getBalance() * 0.18 / 365 * bsSubAccountVO.getInvestDay();
			}
		}
		
		double marketingCosts = subAccountService.getMarkingCosts(); // 计算这个月来2%的营销费用额
		//sysInterest += marketingCosts;  //品听应得的收入
		BsProfitLoss bsProfiteLoss = new BsProfitLoss();
		bsProfiteLoss.setPrincipal(principal);//本金
		bsProfiteLoss.setInterest(interest);//用户利息收益
		bsProfiteLoss.setDafy2Percent(marketingCosts);//2%营销费用
		bsProfiteLoss.setSysInterest(sysInterest - interest);//品听应得收益
		
		bsProfiteLoss.setClearDate(DateUtil.parseDate(DateUtil.formatYYYYMMDD(new Date())));
		
		
		double shouldBonus = bonusService.sumShouldBonus();		//用户应该提奖
		double bonus = bonusService.sumIncarnateBonus(); 		//用户已提奖励
		
		double surplusBonus = shouldBonus - bonus;  //用户剩余奖励金
		bsProfiteLoss.setBonus(bonus);
		bsProfiteLoss.setShouldBonus(shouldBonus);
		bsProfiteLoss.setSurplusBonus(surplusBonus);
		
        double pintingProfit = MoneyUtil.subtract(
        		MoneyUtil.add(marketingCosts,MoneyUtil.add(profiteService.sumProfite(), sysInterest).doubleValue()).doubleValue()
        		,interest).doubleValue();
        		/*profiteService.sumProfite() + sysInterest + marketingCosts
                               - interest;*/
        bsProfiteLoss.setPintingProfit(pintingProfit);//品听累计应得净收益
		bsProfiteLoss.setDafyShouldProfit(sysInterest);
		
	
		BsProfitLoss profit = profiteService.findProfitByClearDateMonth();
		boolean flag = false;
		if(profit != null){
			bsProfiteLoss.setId(profit.getId());
			flag = profiteService.updateProfitLossById(bsProfiteLoss);
		}else{
			bsProfiteLoss.setCreateTime(new Date());
			flag = profiteService.insertProfitLoss(bsProfiteLoss);
		}
		
		return flag;
	}
	
}
