package com.pinting.business.dayend.task;

import com.pinting.business.dao.BsUserKeepViewMapper;
import com.pinting.business.model.*;
import com.pinting.business.model.vo.BsUserKeepViewVO;
import com.pinting.business.service.manage.*;
import com.pinting.core.util.DateUtil;
import com.pinting.core.util.MoneyUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;


/**
 * 统计用户投资
 *
 * @Project: business
 * @author yanwl
 * @Title: StatisticUserInvestTask.java
 * @date 2016-3-22 上午10:40:45
 * @Copyright: 2016 bigangwan.com Inc. All rights reserved.
 */
@Service
public class StatisticUserInvestTask {
	private Logger log = LoggerFactory.getLogger(StatisticUserInvestTask.class);
	@Autowired
	private BsSubAccountService bsSubAccountService;
	@Autowired
	private MUserInvestViewService userInvestViewService;
	@Autowired
	private BsUserRegisterService bsUserRegisterService;
	@Autowired
	private AgentService agentService;
	@Autowired
	private BsUserKeepViewService bsUserKeepViewService;
	
	@Autowired
	private BsUserKeepViewMapper bsUserKeepViewMapper;
	
	/**
	 * 任务执行
	 */
	public void execute() {
		//统计用户投资(今天统计的是昨天的用户投资)
		statisticUserInvestJob();
		
		//统计注册情况(今天统计的是昨天的用户)
		ststicRegisterJob();
		
		//统计注册、活跃情况(今天统计的是昨天的用户)
		staticRetentionJob();
		
	}


	private void statisticUserInvestJob() {
		try {
			log.info("==================定时任务用户投资统计开始====================");
			
			//查询条件
			Map<String,Object> params = new HashMap<String,Object>();
			params.put("todayTime",new Date());
			//统计投资用户、投资金额、投资笔数、年化金额
			Map<String,Object> subAccountCountMap = bsSubAccountService.statisticSubAccount(params);
			//年化金额
			Double tAmount1 = (Double)subAccountCountMap.get("t1MonthAmount");
			Double tAmount2 = (Double)subAccountCountMap.get("t3MonthAmount");
			Double tAmount3 = (Double)subAccountCountMap.get("t6MonthAmount");
			Double tAmount4 = (Double)subAccountCountMap.get("t1YearAmount");
			Double tAmount5 = (Double)subAccountCountMap.get("t7DayAmount");
			
			Double yearAmount_30 = MoneyUtil.divide(MoneyUtil.multiply(tAmount1,30).doubleValue(), 365, 2).doubleValue();
			Double yearAmount_90 = MoneyUtil.divide(MoneyUtil.multiply(tAmount2,90).doubleValue(), 365, 2).doubleValue();
			Double yearAmount_180 = MoneyUtil.divide(MoneyUtil.multiply(tAmount3,180).doubleValue(), 365, 2).doubleValue();
			Double yearAmount_7 = MoneyUtil.divide(MoneyUtil.multiply(tAmount5,7).doubleValue(), 365, 2).doubleValue();
			Double tAnnualAmount = MoneyUtil.add(yearAmount_30, 
					MoneyUtil.add(yearAmount_90,
							MoneyUtil.add(yearAmount_180,
									MoneyUtil.add(tAmount4,yearAmount_7).doubleValue()
							).doubleValue()
						).doubleValue()
					).doubleValue();
					
					//tAmount1/365*30+tAmount2/365*90+tAmount3/365*180+tAmount4+tAmount5/365*7;
			//新增年化金额
			Double newAmount1 = (Double)subAccountCountMap.get("new1MonthAmount");
			Double newAmount2 = (Double)subAccountCountMap.get("new3MonthAmount");
			Double newAmount3 = (Double)subAccountCountMap.get("new6MonthAmount");
			Double newAmount4 = (Double)subAccountCountMap.get("new1YearAmount");
			Double newAmount5 = (Double)subAccountCountMap.get("new7DayAmount");
			Double yearNewAmount_30 = MoneyUtil.divide(MoneyUtil.multiply(newAmount1,30).doubleValue(), 365, 2).doubleValue();
			Double yearNewAmount_90 = MoneyUtil.divide(MoneyUtil.multiply(newAmount2,90).doubleValue(), 365, 2).doubleValue();
			Double yearNewAmount_180 = MoneyUtil.divide(MoneyUtil.multiply(newAmount3,180).doubleValue(), 365, 2).doubleValue();
			Double yearNewAmount_7 = MoneyUtil.divide(MoneyUtil.multiply(newAmount5,7).doubleValue(), 365, 2).doubleValue();
			Double newAnnualAmount = MoneyUtil.add(yearNewAmount_30, 
					MoneyUtil.add(yearNewAmount_90,
							MoneyUtil.add(yearNewAmount_180,
									MoneyUtil.add(newAmount4,yearNewAmount_7).doubleValue()
							).doubleValue()
						).doubleValue()
					).doubleValue();
					//newAmount1/365*30+newAmount2/365*90+newAmount3/365*180+newAmount4+newAmount5/365*7;
			
			
			Date d = DateUtil.parseDate(DateUtil.formatYYYYMMDD(DateUtil.addDays(new Date(), -1)));
			BsUserInvestViewExample example = new BsUserInvestViewExample();
			example.createCriteria().andDateEqualTo(d);
			BsUserInvestView userInvest = userInvestViewService.findUserInvestByDate(example);
			if(userInvest != null) {
				//更新用户投资信息
				userInvest.setTodayAnnualAmount(newAnnualAmount);
				userInvest.setTotalAnnualAmount(tAnnualAmount);
				userInvest.setTodayInvestAmount((Double)subAccountCountMap.get("newInvestAmount"));
				userInvest.setTotalInvestAmount((Double)subAccountCountMap.get("tInvestAmount"));
				userInvest.setTodayInvestNum(((Long)subAccountCountMap.get("newDealCount")).intValue());
				userInvest.setTotalInvestNum(((Long)subAccountCountMap.get("tDealCount")).intValue());
				userInvest.setTodayUserNum(((Long)subAccountCountMap.get("newInvestUserCount")).intValue());
				userInvest.setTotalUserNum(((Long)subAccountCountMap.get("tInvestUserCount")).intValue());
				userInvestViewService.updateUserInvest(userInvest);
			}else {
				//新增用户投资信息
				userInvest = new BsUserInvestView();
				userInvest.setDate(d);
				userInvest.setTodayAnnualAmount(newAnnualAmount);
				userInvest.setTotalAnnualAmount(tAnnualAmount);
				userInvest.setTodayInvestAmount((Double)subAccountCountMap.get("newInvestAmount"));
				userInvest.setTotalInvestAmount((Double)subAccountCountMap.get("tInvestAmount"));
				userInvest.setTodayInvestNum(((Long)subAccountCountMap.get("newDealCount")).intValue());
				userInvest.setTotalInvestNum(((Long)subAccountCountMap.get("tDealCount")).intValue());
				userInvest.setTodayUserNum(((Long)subAccountCountMap.get("newInvestUserCount")).intValue());
				userInvest.setTotalUserNum(((Long)subAccountCountMap.get("tInvestUserCount")).intValue());
				userInvestViewService.saveUserInvest(userInvest);
			}
		} catch (Exception e) {
			log.error("==================定时任务用户投资统计失败====================", e);
		}
	}
	
	
	
	private void ststicRegisterJob() {
		try {
			log.info("==================定时任务昨日注册情况统计开始====================");
			
			String startDate = DateUtil.formatYYYYMMDD(DateUtil.addDays(new Date(), -1));
			String endDate = DateUtil.formatYYYYMMDD(new Date());
			Date d = DateUtil.parseDate(startDate);
			BsUserRegistViewExample example = new BsUserRegistViewExample();
			example.createCriteria().andRegistDateEqualTo(d);
			BsUserRegistView view = bsUserRegisterService.selectByExample(example);
			if(view == null){
				log.info("=================="+startDate+"数据未存，进行新增==================");
				BsUserRegistView record = bsUserRegisterService.selectByTime(startDate, endDate);
				record.setRegistDate(DateUtil.parseDateTime(startDate+" 00:00:00"));
				bsUserRegisterService.addRegisterView(record);
			}else{
				log.info("=================="+startDate+"数据已存在，进行修改==================");
				BsUserRegistView record = bsUserRegisterService.selectByTime(startDate, endDate);
				record.setRegistDate(DateUtil.parseDateTime(startDate+" 00:00:00"));
				record.setId(view.getId());
				bsUserRegisterService.updateView(record);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			log.error("==================定时任务昨日注册情况统计失败====================", e);
		}
		
	}
	
	private void staticRetentionJob() {
		try {
			log.info("==================定时任务昨日注册、活跃用户情况统计开始====================");
			
			Date today = new Date();   
		    SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd");
		    // 今天跑昨天的数据 系统时间-1天
		    String yesterday = df.format(new Date(today.getTime() - 1 * 24 * 60 * 60 * 1000)); 
		    // yesterdayDate 注册时间
		    Date yesterdayDate = DateUtil.parseDate(yesterday);
		    List<BsAgent> agents = agentService.findAllAgentList();
		    // agentIdList 渠道ID   1、all_user  2、非渠道 agent_id is null   3、渠道
		    List<String> agentIdList = new ArrayList<String>();
		    for(int i = 0;i<agents.size();i++) {
		    	String agentId = agents.get(i).getId().toString();
		    	agentIdList.add(agentId);
		    }
		    agentIdList.add("all_user");
		    agentIdList.add("is_null");
		    
		    // 每次传一个渠道ID过去查询
		    for(int j=0;j<agentIdList.size();j++) {
		    	String agentId = agentIdList.get(j);
		    	List<BsUserKeepViewVO> registerUserCountList = bsUserKeepViewMapper.selectUserRegisterList(yesterday, agentId); //注册用户数集合
		    	if(registerUserCountList != null && registerUserCountList.size() > 0){
		    		List<BsUserKeepViewVO> activeUserCountList = bsUserKeepViewMapper.selectActiveUserList(yesterday, agentId); //用户登录次数集合
			    	if(activeUserCountList != null && activeUserCountList.size() > 0) {
			    		// 1)、注册用户数集合，用户登录次数集合， 都不为空
				    	for(int k = 0; k < registerUserCountList.size();k++) {
				    		BsUserKeepViewVO userKeepViewVO = new BsUserKeepViewVO();
				    		for(int m = 0;m < activeUserCountList.size();m++) {
				    			BsUserKeepViewVO registerVO = registerUserCountList.get(k);
				    			BsUserKeepViewVO activeVO = activeUserCountList.get(m);
				    			
			    				userKeepViewVO.setRegistNum(registerVO.getRegistUserCount());
		    					int day = DateUtil.getDiffeDay(activeVO.getLoginDate(), registerVO.getRegistDate());
				    			switch (day) {
				    			case 1:
				    				//注册时间相等
					    			if(registerVO.getRegistDate().compareTo(activeVO.getRegistDate()) == 0 ){
					    				userKeepViewVO.setDay2LoginNum(activeVO.getActiveUserCount());
						    		}
				    				break;
				    			case 2:
				    				if(registerVO.getRegistDate().compareTo(activeVO.getRegistDate()) == 0){
				    					userKeepViewVO.setDay3LoginNum(activeVO.getActiveUserCount());
				    				}
				    				break;
				    			case 6:
				    				if(registerVO.getRegistDate().compareTo(activeVO.getRegistDate()) == 0){
				    					userKeepViewVO.setDay7LoginNum(activeVO.getActiveUserCount());
				    				}
				    				break;
				    			case 13:
				    				if(registerVO.getRegistDate().compareTo(activeVO.getRegistDate()) == 0){
				    					userKeepViewVO.setDay14LoginNum(activeVO.getActiveUserCount());
				    				}
				    				break;
				    			case 29:
				    				if(registerVO.getRegistDate().compareTo(activeVO.getRegistDate()) == 0){
				    					userKeepViewVO.setDay30LoginNum(activeVO.getActiveUserCount());
				    				}
				    				break;
				    			case 59:
				    				if(registerVO.getRegistDate().compareTo(activeVO.getRegistDate()) == 0){
				    					userKeepViewVO.setDay60LoginNum(activeVO.getActiveUserCount());
				    				}
				    				break;
				    			default:
									break;
								}
				    			if(agentId.equals("all_user")){
					    			userKeepViewVO.setExtensiveAgentId(-2);
					    		}else if(agentId.equals("is_null")){
					    			userKeepViewVO.setExtensiveAgentId(-1);
						    		userKeepViewVO.setAgentName("非渠道用户");
					    		}else{
					    			userKeepViewVO.setExtensiveAgentId(registerVO.getExtensiveAgentId());
					    			userKeepViewVO.setAgentName(registerVO.getAgentName());
					    		}
				    			
				    			
				    			//根据注册时间和渠道查询对象，查询是否存在，做新增或修改;
			    				BsUserKeepView userKeepViewRecord =  bsUserKeepViewMapper.selectByRegistDateAgent(
			    						DateUtil.formatYYYYMMDD(registerVO.getRegistDate()), userKeepViewVO.getExtensiveAgentId());
			    				
				    			if(userKeepViewRecord == null){
					    			userKeepViewVO.setRegistDate(registerVO.getRegistDate());
						    					
						    		userKeepViewVO.setRegistNum(registerVO.getRegistUserCount());
						    		userKeepViewVO.setCreateTime(new Date());
						    		bsUserKeepViewService.addKeepView(userKeepViewVO);
				    			}else{
				    					userKeepViewVO.setId(userKeepViewRecord.getId());
				    					bsUserKeepViewService.updateKeepView(userKeepViewVO);
				    			}
				    		}
				    	}
			    	} else if(activeUserCountList == null || activeUserCountList.size() == 0) {
			    		// 2)、注册用户数集合不为空，用户登录为空
				    	for(int k = 0; k < registerUserCountList.size();k++) {
				    		BsUserKeepViewVO userKeepViewVO = new BsUserKeepViewVO();
				    		
				    		BsUserKeepViewVO registerVO = registerUserCountList.get(k);
				    		if(agentId.equals("all_user")){
				    			userKeepViewVO.setExtensiveAgentId(-2);
				    		}else if(agentId.equals("is_null")){
				    			userKeepViewVO.setExtensiveAgentId(-1);
				    			userKeepViewVO.setAgentName("非渠道用户");
				    		}else{
				    			userKeepViewVO.setExtensiveAgentId(registerVO.getExtensiveAgentId());
				    			userKeepViewVO.setAgentName(registerVO.getAgentName());
				    		}
				    		userKeepViewVO.setRegistNum(registerVO.getRegistUserCount());
				    		//根据注册时间和渠道查询对象，查询是否存在，做新增或修改;
		    				BsUserKeepView userKeepViewRecord =  bsUserKeepViewMapper.selectByRegistDateAgent(
		    						DateUtil.formatYYYYMMDD(registerVO.getRegistDate()), userKeepViewVO.getExtensiveAgentId());
		    				
		    				if(userKeepViewRecord == null){
		    					userKeepViewVO.setRegistDate(registerVO.getRegistDate());
					    					
					    		userKeepViewVO.setRegistNum(registerVO.getRegistUserCount());
					    		userKeepViewVO.setCreateTime(new Date());
					    		bsUserKeepViewService.addKeepView(userKeepViewVO);
			    			}
				    	}
			    	} 
		    	}
		    	
		   } 
			
		} catch (Exception e) {
			log.error("==================定时任务昨日注册、活跃用户情况统计失败====================", e);
		}
	    
	}
	
}
