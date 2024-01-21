package com.pinting.business.aspect.mall;

import java.util.Date;
import java.util.List;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.pinting.business.dao.BsUserMapper;
import com.pinting.business.enums.MallRuleEnum;
import com.pinting.business.model.BizMallPointsIncome;
import com.pinting.business.model.BsUser;
import com.pinting.business.model.BsUserExample;
import com.pinting.business.model.dto.MallPointsIncomeQueueDTO;
import com.pinting.business.service.mall.MallPointsIncomeService;
import com.pinting.business.util.Constants;
import com.pinting.core.redis.JedisClientDaoSupport;
import com.pinting.core.util.DateUtil;

/**
 * 用户注册完成拦截
 * 注册成功，根据手机号查询用户表，判断用户是否存在，
 * 存在则新增数据mall_points_income
 * @project business
 * @author bianyatian
 * @2018-5-10 下午4:17:57
 */
@Aspect
@Component
@Order(10)
public class Register4MallAspect {
	private Logger log = LoggerFactory.getLogger(Register4MallAspect.class);
	private static JedisClientDaoSupport jsClientDaoSupport = JedisClientDaoSupport.getInstance(Constants.REDIS_SUBSYS_BUSINESS);
	
	@Autowired
	private BsUserMapper userMapper;
	@Autowired
	private MallPointsIncomeService mallPointsIncomeService;
	
	@Pointcut("execution(public * com.pinting.business.service.site.impl.UserServiceImpl.registerUser(..))")
	public void userRegister(){}
	
	
	@AfterReturning("userRegister()")
	public void userRegisterAfter(JoinPoint call) {
		BsUser userReq = (BsUser)call.getArgs()[0];
		
		BsUserExample bsUserExample = new BsUserExample();
		bsUserExample.createCriteria().andMobileEqualTo(userReq.getMobile())
				.andStatusEqualTo(Constants.BS_USER_STATUS_NORMAL);
		List<BsUser> bsUsers = userMapper.selectByExample(bsUserExample);
		if(CollectionUtils.isEmpty(bsUsers)){
			log.info("========用户注册切面："+userReq.getMobile()+"用户表数据未取得=======");
		}else{
			BsUser user = bsUsers.get(0);
			try {
				BizMallPointsIncome pointsIncome = new BizMallPointsIncome();
				pointsIncome.setUserId(user.getId());
				pointsIncome.setTransType(MallRuleEnum.MALL_REGISTER.getCode());
				pointsIncome.setTransTime(bsUsers.get(0).getRegisterTime());
				pointsIncome = mallPointsIncomeService.addPointsIncome(pointsIncome);
				if(pointsIncome != null){
					MallPointsIncomeQueueDTO queueDTO = new MallPointsIncomeQueueDTO();
					queueDTO.setId(pointsIncome.getId());
					queueDTO.setTransType(pointsIncome.getTransType());
					queueDTO.setUserId(pointsIncome.getUserId());
					jsClientDaoSupport.rpush(Constants.MALL_POINTS_INCOME_QUEUE_KEY, JSON.toJSONString(queueDTO));
					log.info(">>>入积分发放队列（用户注册）:" + JSON.toJSONString(queueDTO) + "<<<");
				}else{
					log.info(">>>入积分发放队列（用户注册）用户id="+user.getId()+ "在pointsIncome表中已存在发放完成数据<<<");
				}
			} catch (Exception e) {
				log.error("======（用户注册）入积分收入交易记录表或积分发放队列   异常======");
				e.printStackTrace();
			}
		}
		
		
	}
}
