package com.pinting.site.activity.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pinting.business.hessian.site.message.ReqMsg_EndOf2016YearActivity_GetGrandPrize;
import com.pinting.business.hessian.site.message.ReqMsg_EndOf2016YearActivity_GetLuckyPrizeList;
import com.pinting.business.hessian.site.message.ReqMsg_EndOf2016YearActivity_GetOtherAwards;
import com.pinting.business.hessian.site.message.ReqMsg_EndOf2016YearActivity_LotteryScrollingData;
import com.pinting.business.hessian.site.message.ReqMsg_EndOf2016YearActivity_NumberOfDraws;
import com.pinting.business.hessian.site.message.ReqMsg_EndOf2016YearActivity_NumberOfParticipants;
import com.pinting.business.hessian.site.message.ResMsg_EndOf2016YearActivity_GetGrandPrize;
import com.pinting.business.hessian.site.message.ResMsg_EndOf2016YearActivity_GetLuckyPrizeList;
import com.pinting.business.hessian.site.message.ResMsg_EndOf2016YearActivity_GetOtherAwards;
import com.pinting.business.hessian.site.message.ResMsg_EndOf2016YearActivity_LotteryScrollingData;
import com.pinting.business.hessian.site.message.ResMsg_EndOf2016YearActivity_NumberOfDraws;
import com.pinting.business.hessian.site.message.ResMsg_EndOf2016YearActivity_NumberOfParticipants;
import com.pinting.site.communicate.CommunicateBusiness;

/**
 * 2016客户年终抽奖活动Controller
 * Created by shh on 2016/11/30 15:00.
 * @author shh
 * 
 */
@Controller
public class EndOf2016YearActivityController {
	
	private static Logger logger = LoggerFactory.getLogger(EndOf2016YearActivityController.class);
	
	@Autowired
    private CommunicateBusiness communicateBusiness;
	
	/**
	 * 1、进入抽奖页面
	 * 入口放在管理台,2016年终抽奖管理功能中点击"开始抽奖"，进入抽奖页面
	 * @param channel
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("{channel}/activity/yearEndDraw2016Index")
	public String CheckInUserIndex(@PathVariable String channel, HttpServletRequest request, HttpServletResponse response, Map<String, Object> model) {
		return channel+"/activity/yearEndDraw2016/yearEndDraw2016_index";
	}
	
	//抽奖滚屏数据
	@ResponseBody
    @RequestMapping("{channel}/activity/scrollingData")
	public String scrollingDataInit(@PathVariable String channel, HttpServletRequest request, HttpServletResponse response, 
			ReqMsg_EndOf2016YearActivity_LotteryScrollingData reqMsg) {
        Map<String, Object> model = new HashMap<String, Object>();
        String activityAwardId = request.getParameter("activityAwardId");
        ResMsg_EndOf2016YearActivity_LotteryScrollingData res = (ResMsg_EndOf2016YearActivity_LotteryScrollingData) communicateBusiness.handleMsg(reqMsg);
        //屏幕滚动手机号
        JSONArray scrollLlist = JSONArray.fromObject(res.getValueList());
        logger.info("=========抽奖滚屏数据："+scrollLlist+"=========");
        JSONObject scrollLlistData = scrollLlist.toJSONObject(scrollLlist);
        return scrollLlistData.toString();
	}
	
	//幸运奖
	@ResponseBody
    @RequestMapping("{channel}/activity/luckyPrize")
	public String luckyWinnersList(@PathVariable String channel, HttpServletRequest request, HttpServletResponse response, 
			ReqMsg_EndOf2016YearActivity_GetLuckyPrizeList reqMsg) {
        Map<String, Object> model = new HashMap<String, Object>();
        String activityAwardId = request.getParameter("activityAwardId");
        reqMsg.setActivityAwardId(Integer.parseInt(activityAwardId));
        ResMsg_EndOf2016YearActivity_GetLuckyPrizeList res = (ResMsg_EndOf2016YearActivity_GetLuckyPrizeList) communicateBusiness.handleMsg(reqMsg);
        //最终中奖的名单
        JSONArray valueList = JSONArray.fromObject(res.getValueList());
        logger.info("=========幸运奖名单："+valueList+"=========");
        JSONObject luckyPrizeData = valueList.toJSONObject(valueList);
        return luckyPrizeData.toString();
	}
	
	//特等奖
	@ResponseBody
    @RequestMapping("{channel}/activity/grandPrize")
	public String grandPrizeList(@PathVariable String channel, HttpServletRequest request, HttpServletResponse response, 
			ReqMsg_EndOf2016YearActivity_GetGrandPrize reqMsg) {
        Map<String, Object> model = new HashMap<String, Object>();
        String activityAwardId = request.getParameter("activityAwardId");
        reqMsg.setActivityAwardId(Integer.parseInt(activityAwardId));
        ResMsg_EndOf2016YearActivity_GetGrandPrize res = (ResMsg_EndOf2016YearActivity_GetGrandPrize) communicateBusiness.handleMsg(reqMsg);
        //最终中奖的名单
        JSONArray valueList = JSONArray.fromObject(res.getValueList());
        logger.info("=========特等奖名单："+valueList+"=========");
        JSONObject grandPrizJson = valueList.toJSONObject(valueList);
        return grandPrizJson.toString();
	}
	
	//其他奖项
	@ResponseBody
    @RequestMapping("{channel}/activity/otherAwards")
	public String otherAwardsList(@PathVariable String channel, HttpServletRequest request, HttpServletResponse response, 
			ReqMsg_EndOf2016YearActivity_GetOtherAwards reqMsg) {
        Map<String, Object> model = new HashMap<String, Object>();
        String activityAwardId = request.getParameter("activityAwardId");
        reqMsg.setActivityAwardId(Integer.parseInt(activityAwardId));
        ResMsg_EndOf2016YearActivity_GetOtherAwards res = (ResMsg_EndOf2016YearActivity_GetOtherAwards) communicateBusiness.handleMsg(reqMsg);
        //最终中奖的名单
        JSONArray valueList = JSONArray.fromObject(res.getValueList());
        logger.info("=========其他奖项："+valueList+"=========");
        JSONObject otherAwardsJson = valueList.toJSONObject(valueList);
        return otherAwardsJson.toString();
	}
	
	//查询一二三等奖，已经抽取次数
	@ResponseBody
    @RequestMapping("{channel}/activity/countOfDraws")
    public Map<String, Object> auditWithdrawDetailInfo(@PathVariable String channel, HttpServletRequest request, 
    		ReqMsg_EndOf2016YearActivity_NumberOfDraws reqMsg) {
        Map<String, Object> model = new HashMap<String, Object>();
        String activityAwardId = request.getParameter("activityAwardId");
        reqMsg.setActivityAwardId(Integer.parseInt(activityAwardId));
        ResMsg_EndOf2016YearActivity_NumberOfDraws res = (ResMsg_EndOf2016YearActivity_NumberOfDraws) communicateBusiness.handleMsg(reqMsg);
        model.put("maxNumberOfDraws", res.getMaxNumberOfDraws());
        if(activityAwardId.equals("26")){
        	logger.info("=========三等奖已抽取次数："+res.getMaxNumberOfDraws()+"=========");
        }else if (activityAwardId.equals("27")){
        	logger.info("=========二等奖已抽取次数："+res.getMaxNumberOfDraws()+"=========");
        }else if(activityAwardId.equals("28")) {
        	logger.info("=========一等奖已抽取次数："+res.getMaxNumberOfDraws()+"=========");
        }
        return model;
    }
	
	//查询参加抽奖活动的人数，人数大于等于20人时才可以抽奖
	@ResponseBody
    @RequestMapping("{channel}/activity/countOfLottery")
    public Map<String, Object> countOfLottery(@PathVariable String channel, HttpServletRequest request, 
    		ReqMsg_EndOf2016YearActivity_NumberOfParticipants reqMsg) {
        Map<String, Object> model = new HashMap<String, Object>();
        ResMsg_EndOf2016YearActivity_NumberOfParticipants res = (ResMsg_EndOf2016YearActivity_NumberOfParticipants) communicateBusiness.handleMsg(reqMsg);
        model.put("lotteryCount", res.getLotteryCount());
        logger.info("=========参加抽奖活动的人数："+res.getLotteryCount()+"=========");
        return model;
    }
	
}
