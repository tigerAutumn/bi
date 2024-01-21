package com.pinting.mall.facade;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.pinting.core.util.MoneyUtil;
import com.pinting.mall.dao.MallPointsIncomeMapper;
import com.pinting.mall.enums.MallRuleEnum;
import com.pinting.mall.hessian.site.message.ReqMsg_MallSign_UserSign;
import com.pinting.mall.hessian.site.message.ResMsg_MallSign_UserSign;
import com.pinting.mall.model.MallPointsIncome;
import com.pinting.mall.model.MallUserSign;
import com.pinting.mall.model.dto.MallPointsIncomeQueueDTO;
import com.pinting.mall.service.MallPointsService;
import com.pinting.mall.service.site.MallUserSignService;
import com.pinting.mall.util.Constants;

/**
 * 签到相关
 * @project mall
 * @author bianyatian
 * @2018-5-15 下午5:56:50
 */
@Component("MallSign")
public class MallSignFacade {
	
	private static Logger logger = LoggerFactory.getLogger(MallSignFacade.class);
	
	@Autowired
	private MallUserSignService mallUserSignService;
	@Autowired
	private MallPointsIncomeMapper mallPointsIncomeMapper;
	@Autowired
	private MallPointsService mallPointsService;
	
	/**
	 * 用户签到：
	 * 1、检查用户是否已签到；
	 * 2、未签到，添加积分收入交易记录表，调用积分发放服务
	 * 3、已签到，返回已签到
	 * @author bianyatian
	 * @param req
	 * @param res
	 */
	public void userSign(ReqMsg_MallSign_UserSign req, ResMsg_MallSign_UserSign res){
		
		//查询用户签到信息
		boolean isSign = mallUserSignService.userIsSign(req.getUserId());
		if(isSign){
			res.setIsSign("YES");//已经签到
		}else{
			res.setIsSign("NO");//未签到
			//记录收入交易明细
			MallPointsIncome pointsIncome = new MallPointsIncome();
			pointsIncome.setUserId(req.getUserId());
			pointsIncome.setTransType(MallRuleEnum.MALL_SIGN.getCode());
			pointsIncome.setTransTime(new Date());
			pointsIncome.setPoints(0l);
			pointsIncome.setStatus(Constants.MALL_POINTS_INCOME_STATUS_INIT);
			pointsIncome.setNote(req.getChannel());
			pointsIncome.setCreateTime(new Date());
			pointsIncome.setUpdateTime(new Date());
			mallPointsIncomeMapper.insertSelective(pointsIncome);
			MallPointsIncomeQueueDTO mallPointsDTO = new MallPointsIncomeQueueDTO();
			mallPointsDTO.setId(pointsIncome.getId());
			mallPointsDTO.setTransType(pointsIncome.getTransType());
			mallPointsDTO.setUserId(pointsIncome.getUserId());
			mallPointsDTO.setChannel(req.getChannel());
			MallUserSign userSign  = mallPointsService.signPointsIn(mallPointsDTO);
			if(userSign != null && userSign.getSignPoints() != null && userSign.getSignDays() != null){
				//签到成功，修改MallPointsIncome数据
				MallPointsIncome pointsIncomeTemp = new MallPointsIncome();
				pointsIncomeTemp.setId(pointsIncome.getId());
				pointsIncomeTemp.setStatus(Constants.MALL_POINTS_INCOME_STATUS_FINISHED);
				pointsIncomeTemp.setPoints(userSign.getSignPoints());
				pointsIncomeTemp.setUpdateTime(new Date());
				pointsIncomeTemp.setFinishTime(new Date());
				mallPointsIncomeMapper.updateByPrimaryKeySelective(pointsIncomeTemp);
				
				int signDays =  userSign.getSignDays()% 7 == 0 ? 7: userSign.getSignDays()% 7;
				res.setSignDays(signDays);
				res.setSignPoints(userSign.getSignPoints());
				res.setSignSucc("YES");
			}else{
				//签到未成功，废除MallPointsIncome数据
				MallPointsIncome pointsIncomeTemp = new MallPointsIncome();
				pointsIncomeTemp.setId(pointsIncome.getId());
				pointsIncomeTemp.setStatus(Constants.MALL_POINTS_INCOME_STATUS_CANCELLED);
				pointsIncomeTemp.setFinishTime(new Date());
				pointsIncomeTemp.setUpdateTime(new Date());
				mallPointsIncomeMapper.updateByPrimaryKeySelective(pointsIncomeTemp);
				res.setSignSucc("NO");
			}
			
		}
		
	}

}
