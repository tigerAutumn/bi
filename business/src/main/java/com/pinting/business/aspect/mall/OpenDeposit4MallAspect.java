package com.pinting.business.aspect.mall;

import java.util.Date;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.pinting.business.enums.MallRuleEnum;
import com.pinting.business.model.BizMallPointsIncome;
import com.pinting.business.model.dto.MallPointsIncomeQueueDTO;
import com.pinting.business.service.mall.MallPointsIncomeService;
import com.pinting.business.util.Constants;
import com.pinting.core.redis.JedisClientDaoSupport;
import com.pinting.core.util.StringUtil;

/**
 * 开通存管拦截
 * 新增数据mall_points_income
 * @project business
 * @author bianyatian
 * @2018-5-10 下午8:04:24
 */
@Aspect
@Component
@Order(11)
public class OpenDeposit4MallAspect {
	
	private Logger log = LoggerFactory.getLogger(OpenDeposit4MallAspect.class);
	private static JedisClientDaoSupport jsClientDaoSupport = JedisClientDaoSupport.getInstance(Constants.REDIS_SUBSYS_BUSINESS);
	
	@Autowired
	private MallPointsIncomeService mallPointsIncomeService;
	
	@Pointcut("execution(public * com.pinting.business.accounting.finance.service.impl.UserCardOperateServiceImpl.bindCard(..))")
	public void openDeposit(){}

	@AfterReturning("openDeposit()")
	public void openDepositAfter(JoinPoint call) {
		String userId = (String)call.getArgs()[2];
		if(StringUtil.isNotBlank(userId)){
			try {
				BizMallPointsIncome pointsIncome = new BizMallPointsIncome();
				pointsIncome.setUserId(Integer.parseInt(StringUtil.trimStr(userId)));
				pointsIncome.setTransType(MallRuleEnum.MALL_OPEN_DEPOSIT.getCode());
				pointsIncome.setTransTime(new Date());
				pointsIncome = mallPointsIncomeService.addPointsIncome(pointsIncome);
				if(pointsIncome != null){
					MallPointsIncomeQueueDTO queueDTO = new MallPointsIncomeQueueDTO();
					queueDTO.setId(pointsIncome.getId());
					queueDTO.setTransType(pointsIncome.getTransType());
					queueDTO.setUserId(pointsIncome.getUserId());
					jsClientDaoSupport.rpush(Constants.MALL_POINTS_INCOME_QUEUE_KEY, JSON.toJSONString(queueDTO));
					log.info(">>>入积分发放队列（开通存管）:" + JSON.toJSONString(queueDTO) + "<<<");
				}else{
					log.info(">>>入积分发放队列（开通存管）用户id="+userId+ "在pointsIncome表中已存在发放完成数据<<<");
				}
			} catch (Exception e) {
				log.error("======（开通存管）入积分收入交易记录表或积分发放队列   异常======");
				e.printStackTrace();
			}
		}
		
	}

}
