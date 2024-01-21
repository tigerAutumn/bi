package com.pinting.business.aspect.mall;

import java.util.HashMap;

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
import com.pinting.business.dao.BsInterestTicketInfoMapper;
import com.pinting.business.dao.BsSubAccountMapper;
import com.pinting.business.enums.MallRuleEnum;
import com.pinting.business.hessian.site.message.ResMsg_RegularBuy_BalanceBuy;
import com.pinting.business.model.BizMallPointsIncome;
import com.pinting.business.model.BsSubAccount;
import com.pinting.business.model.dto.MallPointsIncomeQueueDTO;
import com.pinting.business.service.mall.MallPointsIncomeService;
import com.pinting.business.util.Constants;
import com.pinting.core.redis.JedisClientDaoSupport;
import com.pinting.core.util.MoneyUtil;

/**
 * 产品购买结果拦截
 * 1、根据购买产品计算本次预计收益（包括加息券收益）
 * 2、计算该用户累计年化投资额
 * 3、判断是否首次购买
 * 4、数据入mall_points_income
 * @project business
 * @author bianyatian
 * @2018-5-10 下午1:48:18
 */
@Aspect
@Component
@Order(9)
public class ProductBuyResult4MallAspect {
	private Logger log = LoggerFactory.getLogger(ProductBuyResult4MallAspect.class);
	private static JedisClientDaoSupport jsClientDaoSupport = JedisClientDaoSupport.getInstance(Constants.REDIS_SUBSYS_BUSINESS);
	
	@Autowired
	private BsSubAccountMapper subAccountMapper;
	@Autowired
	private BsInterestTicketInfoMapper interestTicketInfoMapper;
	@Autowired
	private MallPointsIncomeService mallPointsIncomeService;
	
	@Pointcut("execution(public * com.pinting.business.accounting.finance.service.impl.DepUserBalanceBuyServiceImpl.buyFixed(..))")
	public void balanceBuyPointcut(){}
	
	@AfterReturning("balanceBuyPointcut()")
	public void balanceBuyAfter(JoinPoint call) {
		ResMsg_RegularBuy_BalanceBuy res = (ResMsg_RegularBuy_BalanceBuy)call.getArgs()[1];
		HashMap<String, Object> extendProMap = res.getExtendMap();
		
		Object userIdObject = extendProMap.get("userId");
		Object subAccountIdObject = extendProMap.get("subAccountId");
		Object orderNoObject = extendProMap.get("orderNo");
		if(userIdObject != null && userIdObject != null && orderNoObject != null){
			Integer userId = (Integer)userIdObject;
			Integer subAccountId = (Integer)subAccountIdObject;
			String orderNo = (String)orderNoObject;
			BsSubAccount sub = subAccountMapper.selectByPrimaryKey(subAccountId);
			if(sub == null){
				log.info("========完成产品购买切面，未获取到子账户信息：子账户id="+subAccountId+"========");
			}else{
				//根据购买产品计算本次预计收益（包括加息券收益）
				Double ticketInterest = interestTicketInfoMapper.ticketInterest(userId, subAccountId); //加息券利息
				Double productInterest = subAccountMapper.subAccountInterest(subAccountId);
				
				//查询用户首次购买成功信息
				BsSubAccount firstSubAccount = subAccountMapper.firstInvestByUserId(userId);
				try {
					BizMallPointsIncome pointsIncomeInvest = new BizMallPointsIncome();
					pointsIncomeInvest.setUserId(userId);
					pointsIncomeInvest.setTransType(MallRuleEnum.MALL_INVEST.getCode());
					pointsIncomeInvest.setTransTime(sub.getOpenTime());
					pointsIncomeInvest.setInvestInterest(MoneyUtil.add(ticketInterest == null ? 0d :ticketInterest, productInterest).doubleValue());
					pointsIncomeInvest.setFirstInvestFlag(firstSubAccount.getId().equals(subAccountId) ? "YES":"NO");
					pointsIncomeInvest.setTransOrderNo(orderNo);
					
					pointsIncomeInvest = mallPointsIncomeService.addPointsIncome(pointsIncomeInvest);
					if(pointsIncomeInvest != null){
						MallPointsIncomeQueueDTO queueDTOInvest = new MallPointsIncomeQueueDTO();
						queueDTOInvest.setId(pointsIncomeInvest.getId());
						queueDTOInvest.setTransType(pointsIncomeInvest.getTransType());
						queueDTOInvest.setUserId(userId);
						jsClientDaoSupport.rpush(Constants.MALL_POINTS_INCOME_INVEST_QUEUE_KEY, JSON.toJSONString(queueDTOInvest));
						log.info(">>>入积分发放队列（完成产品购买）-投资:" + JSON.toJSONString(queueDTOInvest) + "<<<");
					}else{
						log.info(">>>入积分发放队列（完成产品购买)-投资,用户id="+userId+ "在pointsIncome表中已存在发放完成数据<<<");
					}
					log.info(">>>入积分发放队列判断首次投资，首次投资id:"+firstSubAccount.getId()+"本次投资id"+subAccountId+"<<<");
					if(firstSubAccount.getId().equals(subAccountId)){
						BizMallPointsIncome pointsIncomeFirst = new BizMallPointsIncome();
						pointsIncomeFirst.setUserId(userId);
						pointsIncomeFirst.setTransType(MallRuleEnum.MALL_FIRST_INVEST.getCode());
						pointsIncomeFirst.setTransTime(sub.getOpenTime());
						pointsIncomeFirst.setInvestInterest(MoneyUtil.add(ticketInterest == null ? 0d :ticketInterest, productInterest).doubleValue());
						pointsIncomeFirst.setFirstInvestFlag("YES");
						pointsIncomeFirst.setTransOrderNo(orderNo);
						
						pointsIncomeFirst = mallPointsIncomeService.addPointsIncome(pointsIncomeFirst);
						if(pointsIncomeFirst != null){
							MallPointsIncomeQueueDTO queueDTOFirst = new MallPointsIncomeQueueDTO();
							queueDTOFirst.setId(pointsIncomeFirst.getId());
							queueDTOFirst.setTransType(pointsIncomeFirst.getTransType());
							queueDTOFirst.setUserId(userId);
							jsClientDaoSupport.rpush(Constants.MALL_POINTS_INCOME_INVEST_QUEUE_KEY, JSON.toJSONString(queueDTOFirst));
							log.info(">>>入积分发放队列（完成产品购买）-首次投资:" + JSON.toJSONString(queueDTOFirst) + "<<<");
						}else{
							log.info(">>>入积分发放队列（完成产品购买)-首次投资,用户id="+userId+ "在pointsIncome表中已存在发放完成数据<<<");
						}
					}
					
					BizMallPointsIncome pointsIncome = new BizMallPointsIncome();
					pointsIncome.setUserId(userId);
					pointsIncome.setTransType(MallRuleEnum.MALL_TOTAL_INVEST.getCode());
					pointsIncome.setTransTime(sub.getOpenTime());
					pointsIncome.setInvestInterest(MoneyUtil.add(ticketInterest == null ? 0d :ticketInterest, productInterest).doubleValue());
					pointsIncome.setFirstInvestFlag(firstSubAccount.getId().equals(subAccountId) ? "YES":"NO");
					pointsIncome.setTransOrderNo(orderNo);
					
					pointsIncome = mallPointsIncomeService.addPointsIncome(pointsIncome);
					if(pointsIncome != null){
						MallPointsIncomeQueueDTO queueDTO = new MallPointsIncomeQueueDTO();
						queueDTO.setId(pointsIncome.getId());
						queueDTO.setTransType(pointsIncome.getTransType());
						queueDTO.setUserId(userId);
						jsClientDaoSupport.rpush(Constants.MALL_POINTS_INCOME_INVEST_QUEUE_KEY, JSON.toJSONString(queueDTO));
						log.info(">>>入积分发放队列（完成产品购买）-累计投资:" + JSON.toJSONString(queueDTO) + "<<<");
					}else{
						log.info(">>>入积分发放队列（完成产品购买)-累计投资,用户id="+userId+ "在pointsIncome表中已存在发放完成数据<<<");
					}
				} catch (Exception e) {
					log.error("======（完成产品购买）入积分收入交易记录表或积分发放队列   异常======");
					e.printStackTrace();
				}
			}
		}else{
			log.info("========完成产品购买切面，未获取到用户或子账户信息：用户id="+userIdObject+"，子账户id="+subAccountIdObject+"========");
		}
	}
}
