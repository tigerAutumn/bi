package com.pinting.site.activity.controller;

import com.pinting.business.hessian.site.message.*;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.pinting.site.communicate.CommunicateBusiness;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * 2016公司年会抽奖活动Controller
 * Created by shh on 2017/01/14 10:00.
 * @author shh
 */
@Controller
public class CompanyAnnualMeeting2016Controller {
	
	private static Logger logger = LoggerFactory.getLogger(EndOf2016YearActivityController.class);
	
	@Autowired
    private CommunicateBusiness communicateBusiness;

	/**
	 * 1、进入抽奖页面
	 * 入口放在管理台,2016公司年会抽奖管理功能中点击"开始抽奖"，进入抽奖页面
	 * @param channel
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("{channel}/annualMeeting/annualMeeting2017Index")
	public String annualMeetingIndex(@PathVariable String channel, HttpServletRequest request, HttpServletResponse response, Map<String, Object> model) {
		return channel+"/activity/annualMeeting2016/annualMeeting2016_index";
	}

	// 抽奖滚屏数据
	@ResponseBody
	@RequestMapping("{channel}/annualMeeting/scrollingData")
	public String scrollingDataInit(@PathVariable String channel, HttpServletRequest request, HttpServletResponse response,
									ReqMsg_EndOf2016CompanyAnnual_LotteryScrollingData reqMsg) {
		Map<String, Object> model = new HashMap<String, Object>();
		String activityAwardId = request.getParameter("activityAwardId");
		ResMsg_EndOf2016CompanyAnnual_LotteryScrollingData res = (ResMsg_EndOf2016CompanyAnnual_LotteryScrollingData) communicateBusiness.handleMsg(reqMsg);
		//屏幕滚动的姓名
		JSONArray scrollLlist = JSONArray.fromObject(res.getValueList());
		logger.info("=========抽奖滚屏数据："+scrollLlist+"=========");
		JSONObject scrollLlistData = scrollLlist.toJSONObject(scrollLlist);
		return scrollLlistData.toString();
	}

	// 五、六、七等奖
	@ResponseBody
	@RequestMapping("{channel}/annualMeeting/luckyPrize")
	public String luckyWinnersList(@PathVariable String channel, HttpServletRequest request, HttpServletResponse response,
								   ReqMsg_EndOf2016CompanyAnnual_GetLuckyPrizeList reqMsg) {
		Map<String, Object> model = new HashMap<String, Object>();
		String activityAwardId = request.getParameter("activityAwardId");
		reqMsg.setActivityAwardId(Integer.parseInt(activityAwardId));
		ResMsg_EndOf2016CompanyAnnual_GetLuckyPrizeList res = (ResMsg_EndOf2016CompanyAnnual_GetLuckyPrizeList) communicateBusiness.handleMsg(reqMsg);
		//最终中奖的名单
		JSONArray valueList = JSONArray.fromObject(res.getValueList());
		if(activityAwardId.equals("30")){
			logger.info("=========七等奖名单："+valueList+"=========");
		}else if (activityAwardId.equals("31")){
			logger.info("=========六等奖名单："+valueList+"=========");
		}else if(activityAwardId.equals("32")) {
			logger.info("=========五等奖名单："+valueList+"=========");
		}
		JSONObject luckyPrizeData = valueList.toJSONObject(valueList);
		return luckyPrizeData.toString();
	}

	// 三、四等奖
	@ResponseBody
	@RequestMapping("{channel}/annualMeeting/otherAwards")
	public String otherAwardsList(@PathVariable String channel, HttpServletRequest request, HttpServletResponse response,
								  ReqMsg_EndOf2016CompanyAnnual_GetOtherAwards reqMsg) {
		Map<String, Object> model = new HashMap<String, Object>();
		String activityAwardId = request.getParameter("activityAwardId");
		reqMsg.setActivityAwardId(Integer.parseInt(activityAwardId));
		ResMsg_EndOf2016CompanyAnnual_GetOtherAwards res = (ResMsg_EndOf2016CompanyAnnual_GetOtherAwards) communicateBusiness.handleMsg(reqMsg);
		//最终中奖的名单
		JSONArray valueList = JSONArray.fromObject(res.getValueList());
		if(activityAwardId.equals("33")){
			logger.info("=========四等奖名单："+valueList+"=========");
		}else if (activityAwardId.equals("34")){
			logger.info("=========三等奖名单："+valueList+"=========");
		}
		JSONObject otherAwardsJson = valueList.toJSONObject(valueList);
		return otherAwardsJson.toString();
	}

	// 一、二等奖
	@ResponseBody
	@RequestMapping("{channel}/annualMeeting/grandPrize")
	public String grandPrizeList(@PathVariable String channel, HttpServletRequest request, HttpServletResponse response,
								 ReqMsg_EndOf2016CompanyAnnual_GetGrandPrize reqMsg) {
		Map<String, Object> model = new HashMap<String, Object>();
		String activityAwardId = request.getParameter("activityAwardId");
		reqMsg.setActivityAwardId(Integer.parseInt(activityAwardId));
		ResMsg_EndOf2016CompanyAnnual_GetGrandPrize res = (ResMsg_EndOf2016CompanyAnnual_GetGrandPrize) communicateBusiness.handleMsg(reqMsg);
		//最终中奖的名单
		JSONArray valueList = JSONArray.fromObject(res.getValueList());
		if(activityAwardId.equals("35")){
			logger.info("=========二等奖名单："+valueList+"=========");
		}else if (activityAwardId.equals("36")){
			logger.info("=========一等奖名单："+valueList+"=========");
		}
		JSONObject grandPrizJson = valueList.toJSONObject(valueList);
		return grandPrizJson.toString();
	}

	// 查询不同奖项，已经抽取的次数
	@ResponseBody
	@RequestMapping("{channel}/annualMeeting/countOfDraws")
	public Map<String, Object> auditWithdrawDetailInfo(@PathVariable String channel, HttpServletRequest request,
													   ReqMsg_EndOf2016CompanyAnnual_NumberOfDraws reqMsg) {
		Map<String, Object> model = new HashMap<String, Object>();
		String activityAwardId = request.getParameter("activityAwardId");
		reqMsg.setActivityAwardId(Integer.parseInt(activityAwardId));
		ResMsg_EndOf2016CompanyAnnual_NumberOfDraws res = (ResMsg_EndOf2016CompanyAnnual_NumberOfDraws) communicateBusiness.handleMsg(reqMsg);
		model.put("maxNumberOfDraws", res.getMaxNumberOfDraws());
		if(activityAwardId.equals("30")){
			logger.info("=========七等奖已抽取次数："+res.getMaxNumberOfDraws()+"=========");
		}else if (activityAwardId.equals("31")){
			logger.info("=========六等奖已抽取次数："+res.getMaxNumberOfDraws()+"=========");
		}else if(activityAwardId.equals("32")) {
			logger.info("=========五等奖已抽取次数："+res.getMaxNumberOfDraws()+"=========");
		}else if(activityAwardId.equals("33")) {
			logger.info("=========四等奖已抽取次数："+res.getMaxNumberOfDraws()+"=========");
		}else if(activityAwardId.equals("34")){
			logger.info("=========三等奖已抽取次数："+res.getMaxNumberOfDraws()+"=========");
		}else if (activityAwardId.equals("35")){
			logger.info("=========二等奖已抽取次数："+res.getMaxNumberOfDraws()+"=========");
		}else if(activityAwardId.equals("36")) {
			logger.info("=========一等奖已抽取次数："+res.getMaxNumberOfDraws()+"=========");
		}
		return model;
	}


}
