package com.pinting.business.dayend.task;

import com.pinting.business.accounting.finance.service.DepUserBonusGrantService;
import com.pinting.business.accounting.finance.service.UserBonusGrantService;
import com.pinting.business.dao.BsBonusGrantPlanMapper;
import com.pinting.business.model.BsBonusGrantPlan;
import com.pinting.business.model.BsBonusGrantPlanExample;
import com.pinting.business.service.site.SpecialJnlService;
import com.pinting.business.util.Constants;
import com.pinting.core.util.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 扫描用户奖励金发放计划表，进行奖励金发放
 * @Project: business
 * @Title: SysUserBonusGrantTask.java
 * @author dingpf
 * @date 2015-12-23 下午3:52:31
 * @Copyright: 2015 BiGangWan.com Inc. All rights reserved.
 */
@Service
public class SysUserBonusGrantTask {
	private Logger log = LoggerFactory.getLogger(SysUserBonusGrantTask.class);
	@Autowired
	private DepUserBonusGrantService depUserBonusGrantService;
	@Autowired
	private SpecialJnlService specialJnlService;
	@Autowired
	private BsBonusGrantPlanMapper bsBonusGrantPlanMapper;
	
	/**
	 * 任务执行
	 */
	public void execute() {
		// 定时任务{用户奖励金发放计划}
		log.info("=========定时任务{用户奖励金发放计划}开始=========");
		//查询当天待发放的奖励金记录
		Date currDate = DateUtil.parseDate(DateUtil.formatYYYYMMDD(new Date()));
		BsBonusGrantPlanExample example = new BsBonusGrantPlanExample();
		example.createCriteria().andGrantDateEqualTo(currDate).andStatusEqualTo(Constants.BONUS_GRANT_STATUS_INIT);
		List<BsBonusGrantPlan> plans = bsBonusGrantPlanMapper.selectByExample(example);
		if(plans != null && plans.size() > 0){
			//循环发放奖励金
			for (BsBonusGrantPlan plan : plans) {
				try {
					depUserBonusGrantService.grantBonus(plan);
				} catch (Exception e) {
					log.info("=========定时任务{用户奖励金发放计划}产品户编号"
							+plan.getSubAccountId()+"第"+plan.getSerialNo()+"笔奖励金发放执行异常=========", e);
					//告警
					specialJnlService.warn4Fail(plan.getAmount(), "定时任务{用户奖励金发放计划}产品户编号"
							+plan.getSubAccountId()+"第"+plan.getSerialNo()+"笔奖励金发放执行异常", 
							String.valueOf(plan.getSubAccountId()), "用户奖励金发放计划", true);
				}
				
			}
			
		}else{
			log.info("=========定时任务{用户奖励金发放计划}无发放计划=========");
		}
		
	}
	

}
