package com.pinting.business.aspect.mall;

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
import com.pinting.business.dao.BsQuestionnaireMapper;
import com.pinting.business.enums.MallRuleEnum;
import com.pinting.business.hessian.site.message.ReqMsg_User_Questionnaire;
import com.pinting.business.hessian.site.message.ReqMsg_User_SaveQuestionnaire;
import com.pinting.business.model.BizMallPointsIncome;
import com.pinting.business.model.BsQuestionnaire;
import com.pinting.business.model.BsQuestionnaireExample;
import com.pinting.business.model.dto.MallPointsIncomeQueueDTO;
import com.pinting.business.service.mall.MallPointsIncomeService;
import com.pinting.business.util.Constants;
import com.pinting.core.redis.JedisClientDaoSupport;
import com.pinting.core.util.DateUtil;
/**
 * 完成风险测评切面
 * 查询BsQuestionnaire，比较创建时间是否为当日
 * 是则新增数据mall_points_income
 * @project business
 * @author bianyatian
 * @2018-5-11 下午1:52:46
 */

@Aspect
@Component
@Order(12)
public class FinishRiskAssessment4MallAspect {

	private Logger log = LoggerFactory.getLogger(FinishRiskAssessment4MallAspect.class);
	
	private static JedisClientDaoSupport jsClientDaoSupport = JedisClientDaoSupport.getInstance(Constants.REDIS_SUBSYS_BUSINESS);
	
	@Autowired
	private MallPointsIncomeService mallPointsIncomeService;
	@Autowired
	private BsQuestionnaireMapper bsQuestionnaireMapper;
	
	@Pointcut("execution(public * com.pinting.business.service.site.impl.QuestionnaireServiceImpl.doQuestionnaire(..))")
	public void doQuestionnaire(){}
	
	@Pointcut("execution(public * com.pinting.business.service.site.impl.QuestionnaireServiceImpl.saveQuestionnaire(..))")
	public void doQuestionnaire4App(){}
	
	
	@AfterReturning("doQuestionnaire()")
	public void doQuestionnaireAfter(JoinPoint call) {
		ReqMsg_User_Questionnaire req = (ReqMsg_User_Questionnaire)call.getArgs()[0];
		checkAndSave(req.getUserId());
		
	}

	@AfterReturning("doQuestionnaire4App()")
	public void doQuestionnaire4AppAfter(JoinPoint call) {
		ReqMsg_User_SaveQuestionnaire req = (ReqMsg_User_SaveQuestionnaire)call.getArgs()[0];
		checkAndSave(req.getUserId());
		
	}
	
	private void checkAndSave(Integer userId) {
		BsQuestionnaireExample questionnaireExample = new BsQuestionnaireExample();
		questionnaireExample.createCriteria().andUserIdEqualTo(userId);
        List<BsQuestionnaire> selectList = bsQuestionnaireMapper.selectByExample(questionnaireExample);
        if(CollectionUtils.isEmpty(selectList)){
			log.info("========完成风险测评切面：用户id="+userId+"未查到风险测评记录========");
		}else{
			BsQuestionnaire questionnaire = selectList.get(0);
			Integer diffDay = DateUtil.getDiffeDay(questionnaire.getCreateTime(),questionnaire.getUpdateTime());
			if(diffDay!=0){
				log.info("========完成风险测评切面：用户id="+userId+"BsQuestionnaire表创建时间："+DateUtil.format(questionnaire.getCreateTime())
						+",修改时间："+DateUtil.format(questionnaire.getUpdateTime())+",不同日不触发积分收入记录========");
			}else{
				try {
					BizMallPointsIncome pointsIncome = new BizMallPointsIncome();
					pointsIncome.setUserId(userId);
					pointsIncome.setTransType(MallRuleEnum.MALL_FINISH_RISK_ASSESSMENT.getCode());
					pointsIncome.setTransTime(questionnaire.getCreateTime());
					pointsIncome = mallPointsIncomeService.addPointsIncome(pointsIncome);
					if(pointsIncome != null){
						MallPointsIncomeQueueDTO queueDTO = new MallPointsIncomeQueueDTO();
						queueDTO.setId(pointsIncome.getId());
						queueDTO.setTransType(pointsIncome.getTransType());
						queueDTO.setUserId(userId);
						jsClientDaoSupport.rpush(Constants.MALL_POINTS_INCOME_QUEUE_KEY, JSON.toJSONString(queueDTO));
						log.info(">>>入积分发放队列（完成风险测评）:" + JSON.toJSONString(queueDTO) + "<<<");
					}else{
						log.info(">>>入积分发放队列（完成风险测评）用户id="+userId+ "在pointsIncome表中已存在发放完成数据<<<");
					}
					
				} catch (Exception e) {
					log.error("======（完成风险测评）入积分收入交易记录表或积分发放队列   异常======");
					e.printStackTrace();
				}
			}
		}
		
	}
}
