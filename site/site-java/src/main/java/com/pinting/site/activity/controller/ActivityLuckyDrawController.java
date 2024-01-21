package com.pinting.site.activity.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.jsoup.helper.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pinting.business.hessian.site.message.ReqMsg_ActivityLuckyDraw_Activity618Index;
import com.pinting.business.hessian.site.message.ReqMsg_ActivityLuckyDraw_Get618Award;
import com.pinting.business.hessian.site.message.ReqMsg_ActivityLuckyDraw_Get618UserLuckyList;
import com.pinting.business.hessian.site.message.ResMsg_ActivityLuckyDraw_Activity618Index;
import com.pinting.business.hessian.site.message.ResMsg_ActivityLuckyDraw_Get618Award;
import com.pinting.business.hessian.site.message.ResMsg_ActivityLuckyDraw_Get618UserLuckyList;
import com.pinting.core.cookie.CookieManager;
import com.pinting.core.util.DateUtil;
import com.pinting.site.common.enums.CookieEnums;
import com.pinting.site.communicate.CommunicateBusiness;
import com.pinting.util.Constants;
import com.pinting.util.DESUtil;

/**
 * 活动相关
 * @author bianyatian
 * @2016-6-6 上午10:12:52
 */
@Controller
public class ActivityLuckyDrawController {
	
	private static Logger logger = LoggerFactory.getLogger(ActivityLuckyDrawController.class);
	
	@Autowired
    private CommunicateBusiness communicateBusiness;
	

	/**
	 * 618活动主页--H5，PC
	 * @param channel
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/{channel}/activity/activity618_index")
	private String activity618Index(@PathVariable String channel, HttpServletRequest request, 
			HttpServletResponse response,Map<String, Object> model){
		
		ReqMsg_ActivityLuckyDraw_Activity618Index req = new ReqMsg_ActivityLuckyDraw_Activity618Index();
		
		CookieManager cookieManager = new CookieManager(request);
		String userId = cookieManager.getValue(CookieEnums._SITE.getName(), CookieEnums._SITE_USER_ID.getName(), true);
	
		if(StringUtils.isNotBlank(userId)){
			req.setUserId(Integer.parseInt(userId));
		}
		
	    req.setActivityId(Constants.ACTIVITY_20160618);
	    ResMsg_ActivityLuckyDraw_Activity618Index res = (ResMsg_ActivityLuckyDraw_Activity618Index)communicateBusiness.handleMsg(req);
	  
	    model.put("luckyList", res.getLuckyList());
	    model.put("luckyNum", res.getLuckyNum());
	    model.put("userLuckyNum", res.getUserLuckyNum());
	    if("gen2.0".equals(channel) && StringUtils.isNotBlank(userId)){
	    	ReqMsg_ActivityLuckyDraw_Get618UserLuckyList reqU = new ReqMsg_ActivityLuckyDraw_Get618UserLuckyList();
	    	reqU.setUserId(Integer.parseInt(userId));
	    	reqU.setActivityId(Constants.ACTIVITY_20160618);
	    	ResMsg_ActivityLuckyDraw_Get618UserLuckyList resU = (ResMsg_ActivityLuckyDraw_Get618UserLuckyList)communicateBusiness.handleMsg(reqU);
	    	model.put("userLuckyList", resU.getLuckyList());
	    }
		return channel+"/activity/activity618/618_index";
	}
	
	
	/**
	 * 用户抽奖
	 * @param reqMsg
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/{channel}/activity/get618Dward")
	public Map<String, Object> financeDay518DrawRedPacket(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> result = new HashMap<String, Object>(); 
        
        Date startTime = DateUtil.parseDateTime(Constants.ACTIVITY_START_TIME);
        Date endTime = DateUtil.parseDateTime(Constants.ACTIVITY_END_TIME);
        Date now = new Date();
        logger.info("==========now:"+now+";startTime:"+startTime+";endTime:"+endTime+"==========");
        
        if(startTime.compareTo(now)>0){
        	result.put("timeStatus", "noStart");
        }
        if(endTime.compareTo(now)<0){
        	result.put("timeStatus", "isEnd");
        }
        if(endTime.compareTo(now)>0 && startTime.compareTo(now)<0){
        	result.put("timeStatus", "ing");
        	CookieManager cookieManager = new CookieManager(request);
            String userId = cookieManager.getValue(CookieEnums._SITE.getName(), CookieEnums._SITE_USER_ID.getName(), true);
            if(StringUtil.isBlank(userId)) {
                result.put("resCode", "9100049");
                result.put("resMsg", "您还未登录");
                return result;
            }
            ReqMsg_ActivityLuckyDraw_Get618Award req = new ReqMsg_ActivityLuckyDraw_Get618Award();
            req.setActivityId(Constants.ACTIVITY_20160618);
            req.setUserId(Integer.parseInt(userId));
            
            ResMsg_ActivityLuckyDraw_Get618Award res = (ResMsg_ActivityLuckyDraw_Get618Award)communicateBusiness.handleMsg(req);
            if(res.getBeforeTimes() == 0){
            	result.put("beforeTimes", "0");
            }else{
            	result.put("beforeTimes", res.getBeforeTimes());
            	result.put("afterTimes", res.getAfterTimes());
            	result.put("awardId", res.getAwardId());
            	result.put("awardContent", res.getAwardContent());
            }

        }
        
        
        return result;
	}
	
	
	/**
	 * 用户个人抽奖列表
	 * @param channel
	 * @param request
	 * @param response
	 * @param model
	 * @param reqU
	 * @return
	 */
	@RequestMapping("/{channel}/activity/activity618_userLuckyList")
	private String activity618UserLuckyList(@PathVariable String channel, HttpServletRequest request,
			HttpServletResponse response,Map<String, Object> model,ReqMsg_ActivityLuckyDraw_Get618UserLuckyList reqU){ 
		
		CookieManager cookieManager = new CookieManager(request);
		String userId = cookieManager.getValue(CookieEnums._SITE.getName(), CookieEnums._SITE_USER_ID.getName(), true);
	
		if(StringUtils.isNotBlank(userId)){
			reqU.setStartPage(0);
			reqU.setPageSize(Constants.EXCHANGE_PAGE_SIZE_LONG);
	    	reqU.setUserId(Integer.parseInt(userId));
	    	reqU.setActivityId(Constants.ACTIVITY_20160618);
	    	ResMsg_ActivityLuckyDraw_Get618UserLuckyList resU = (ResMsg_ActivityLuckyDraw_Get618UserLuckyList)communicateBusiness.handleMsg(reqU);
	    	
	    	model.put("pageIndex", 0);
			model.put("totalCount", resU.getTotal());
			model.put("userLuckyList", resU.getLuckyList());
		}
		
		
		return channel+"/activity/activity618/618_user";
	}
	
	/**
	 * 用户个人抽奖列表--加载更多
	 * @param channel
	 * @param request
	 * @param response
	 * @param model
	 * @param pageIndex
	 * @param reqU
	 * @return
	 */
	@RequestMapping("/{channel}/activity/activity618_userLuckyList_content")
	private String activity618UserLuckyListContent(@PathVariable String channel, HttpServletRequest request,
			HttpServletResponse response,Map<String, Object> model, Integer pageIndex,
			ReqMsg_ActivityLuckyDraw_Get618UserLuckyList reqU){ 
		try {
			CookieManager cookieManager = new CookieManager(request);
			String userId = cookieManager.getValue(CookieEnums._SITE.getName(), CookieEnums._SITE_USER_ID.getName(), true);
		
			if(StringUtils.isNotBlank(userId)){
				reqU.setStartPage(pageIndex);
				reqU.setPageSize(Constants.EXCHANGE_PAGE_SIZE_LONG);
		    	reqU.setUserId(Integer.parseInt(userId));
		    	reqU.setActivityId(Constants.ACTIVITY_20160618);
		    	ResMsg_ActivityLuckyDraw_Get618UserLuckyList resU = (ResMsg_ActivityLuckyDraw_Get618UserLuckyList)communicateBusiness.handleMsg(reqU);
		    	
		    	model.put("pageIndex", reqU.getStartPage());
				model.put("totalCount", resU.getTotal());
				model.put("userLuckyList", resU.getLuckyList());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		return channel+"/activity/activity618/618_user_content";
	}
	
	
	/**
	 * 618活动主页--APP
	 * @param channel
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/micro2.0/activity/activity618_index_app")
	private String activity618IndexAPP(HttpServletRequest request, 
			HttpServletResponse response,Map<String, Object> model){
		
		ReqMsg_ActivityLuckyDraw_Activity618Index req = new ReqMsg_ActivityLuckyDraw_Activity618Index();
		String client = request.getParameter("client");
		model.put("client", client);
		String userId = request.getParameter("userId");
		String afterUserId = request.getParameter("afterUserId");
		if(StringUtils.isNotBlank(afterUserId)){
			req.setUserId(Integer.parseInt(afterUserId));
			model.put("userId", afterUserId);
			model.put("afterUserId", afterUserId);
		}else{
			if(StringUtils.isNotBlank(userId)){
				userId = new DESUtil("cfgubijn").decryptStr(userId);
				req.setUserId(Integer.parseInt(userId));
				model.put("userId", userId);
				model.put("afterUserId", userId);
				
			}
		}
		
		
	    req.setActivityId(Constants.ACTIVITY_20160618);
	    ResMsg_ActivityLuckyDraw_Activity618Index res = (ResMsg_ActivityLuckyDraw_Activity618Index)communicateBusiness.handleMsg(req);
	  
	    model.put("luckyList", res.getLuckyList());
	    model.put("luckyNum", res.getLuckyNum());
	    model.put("userLuckyNum", res.getUserLuckyNum());
		return "micro2.0/activity/activity618/618_index_app";
	}
	
	
	/**
	 * 用户抽奖--	APP
	 * @param reqMsg
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/{channel}/activity/get618Dward_app")
	public Map<String, Object> financeDay518DrawRedPacketAPP(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> result = new HashMap<String, Object>(); 
        
        Date startTime = DateUtil.parseDateTime(Constants.ACTIVITY_START_TIME);
        Date endTime = DateUtil.parseDateTime(Constants.ACTIVITY_END_TIME);
        Date now = new Date();
        if(startTime.compareTo(now)>0){
        	result.put("timeStatus", "noStart");
        }
        if(endTime.compareTo(now)<0){
        	result.put("timeStatus", "isEnd");
        }
        if(endTime.compareTo(now)>0 && startTime.compareTo(now)<0){
        	result.put("timeStatus", "ing");
        	String userId = request.getParameter("userId");
            if(StringUtil.isBlank(userId)) {
                result.put("resCode", "9100049");
                result.put("resMsg", "您还未登录");
                return result;
            }
            /*userId = new DESUtil("cfgubijn").decryptStr(userId);*/
            ReqMsg_ActivityLuckyDraw_Get618Award req = new ReqMsg_ActivityLuckyDraw_Get618Award();
            req.setActivityId(Constants.ACTIVITY_20160618);
            req.setUserId(Integer.parseInt(userId));
            
            ResMsg_ActivityLuckyDraw_Get618Award res = (ResMsg_ActivityLuckyDraw_Get618Award)communicateBusiness.handleMsg(req);
            if(res.getBeforeTimes() == 0){
            	result.put("beforeTimes", "0");
            }else{
            	result.put("beforeTimes", res.getBeforeTimes());
            	result.put("afterTimes", res.getAfterTimes());
            	result.put("awardId", res.getAwardId());
            	result.put("awardContent", res.getAwardContent());
            }

        }
        
        
        return result;
	}
	
	/**
	 * 用户个人抽奖列表-app
	 * @param channel
	 * @param request
	 * @param response
	 * @param model
	 * @param reqU
	 * @return
	 */
	@RequestMapping("/{channel}/activity/activity618_userLuckyList_app")
	private String activity618UserLuckyListAPP(@PathVariable String channel, HttpServletRequest request,
			HttpServletResponse response,Map<String, Object> model,ReqMsg_ActivityLuckyDraw_Get618UserLuckyList reqU){ 
		String client = request.getParameter("client");
		model.put("client", client);
		String userId = request.getParameter("userId");
		model.put("userId", userId);
		if(StringUtils.isNotBlank(userId)){
			reqU.setStartPage(0);
			reqU.setPageSize(Constants.EXCHANGE_PAGE_SIZE_LONG);
	    	reqU.setUserId(Integer.parseInt(userId));
	    	reqU.setActivityId(Constants.ACTIVITY_20160618);
	    	ResMsg_ActivityLuckyDraw_Get618UserLuckyList resU = (ResMsg_ActivityLuckyDraw_Get618UserLuckyList)communicateBusiness.handleMsg(reqU);
	    	
	    	model.put("pageIndex", 0);
			model.put("totalCount", resU.getTotal());
			model.put("userLuckyList", resU.getLuckyList());
		}
		
		
		return channel+"/activity/activity618/618_user_app";
	}
	
	/**
	 * 用户个人抽奖列表--加载更多-app
	 * @param channel
	 * @param request
	 * @param response
	 * @param model
	 * @param pageIndex
	 * @param reqU
	 * @return
	 */
	@RequestMapping("/{channel}/activity/activity618_userLuckyList_content_app")
	private String activity618UserLuckyListContentAPP(@PathVariable String channel, HttpServletRequest request,
			HttpServletResponse response,Map<String, Object> model, Integer pageIndex,
			ReqMsg_ActivityLuckyDraw_Get618UserLuckyList reqU){ 
		try {
			String client = request.getParameter("client");
			model.put("client", client);
			String userId = request.getParameter("userId");
			if(StringUtils.isNotBlank(userId)){
				reqU.setStartPage(pageIndex);
				reqU.setPageSize(Constants.EXCHANGE_PAGE_SIZE_LONG);
		    	reqU.setUserId(Integer.parseInt(userId));
		    	reqU.setActivityId(Constants.ACTIVITY_20160618);
		    	ResMsg_ActivityLuckyDraw_Get618UserLuckyList resU = (ResMsg_ActivityLuckyDraw_Get618UserLuckyList)communicateBusiness.handleMsg(reqU);
		    	
		    	model.put("pageIndex", reqU.getStartPage());
				model.put("totalCount", resU.getTotal());
				model.put("userLuckyList", resU.getLuckyList());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		return channel+"/activity/activity618/618_user_content";
	}
}
