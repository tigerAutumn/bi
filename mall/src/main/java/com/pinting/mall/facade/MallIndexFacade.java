package com.pinting.mall.facade;

import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pinting.mall.dao.MallPointsRuleMapper;
import com.pinting.mall.enums.MallRuleEnum;
import com.pinting.mall.hessian.site.message.ReqMsg_MallIndex_UserIndex;
import com.pinting.mall.hessian.site.message.ResMsg_MallIndex_UserIndex;
import com.pinting.mall.model.MallAccount;
import com.pinting.mall.model.MallPointsRule;
import com.pinting.mall.model.MallPointsRuleExample;
import com.pinting.mall.service.site.MallAccountService;
import com.pinting.mall.service.site.MallUserSignService;
import com.pinting.mall.util.Constants;

/**
 * 积分商城首页相关
 * @project mall
 * @author bianyatian
 * @2018-5-15 下午5:56:35
 */
@Component("MallIndex")
public class MallIndexFacade {

	private static Logger logger = LoggerFactory.getLogger(MallIndexFacade.class);
	
	@Autowired
	private MallAccountService mallAccountService;
	@Autowired
	private MallUserSignService mallUserSignService;
	@Autowired
    private MallPointsRuleMapper pointsRuleMapper;
	
	/**
	 * 积分商城首页用户相关信息：积分信息，签到信息
	 * @author bianyatian
	 * @param req
	 * @param res
	 */
	public void userIndex(ReqMsg_MallIndex_UserIndex req,ResMsg_MallIndex_UserIndex res){
		Integer userId = req.getUserId();
		
		if(userId != null){
			//查询用户积分账户信息
			MallAccount account = mallAccountService.getAccountByUserId(userId);
			if(account != null){
				res.setPoints(account.getBalance());
			}
			//查询用户签到信息
			boolean isSign = mallUserSignService.userIsSign(userId);
			res.setIsSign(isSign ? "YES":"NO");
		}else{
			res.setPoints(0l);
			res.setIsSign("NO");
		}
		
		//查询是否存在签到规则
		MallPointsRuleExample pointsRule = new MallPointsRuleExample();
		pointsRule.createCriteria().andGetSceneEqualTo(MallRuleEnum.MALL_SIGN.getCode()) 
			.andStatusEqualTo(Constants.MALL_RULE_STATUS_OPEN) 
			.andTriggerTimeStartLessThanOrEqualTo(new Date()) 
			.andTriggerTimeEndGreaterThanOrEqualTo(new Date());
		
		List<MallPointsRule> pointsRuleList = pointsRuleMapper.selectByExample(pointsRule);
		if(CollectionUtils.isEmpty(pointsRuleList)){
			//规则为空，返回无规则
			res.setIsSign("NORULE");
		}
		
	}
}
