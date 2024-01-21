package com.pinting.site.activity.controller;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pinting.business.hessian.site.message.ReqMsg_ScratchcardActivity_CountUserAward;
import com.pinting.business.hessian.site.message.ReqMsg_ScratchcardActivity_HasScratchChance;
import com.pinting.business.hessian.site.message.ReqMsg_ScratchcardActivity_UserAwardList;
import com.pinting.business.hessian.site.message.ReqMsg_ScratchcardActivity_UserScratch;
import com.pinting.business.hessian.site.message.ResMsg_ScratchcardActivity_CountUserAward;
import com.pinting.business.hessian.site.message.ResMsg_ScratchcardActivity_HasScratchChance;
import com.pinting.business.hessian.site.message.ResMsg_ScratchcardActivity_UserAwardList;
import com.pinting.business.hessian.site.message.ResMsg_ScratchcardActivity_UserScratch;
import com.pinting.core.cookie.CookieManager;
import com.pinting.core.util.ConstantUtil;
import com.pinting.core.util.StringUtil;
import com.pinting.site.common.enums.CookieEnums;
import com.pinting.site.communicate.CommunicateBusiness;
import com.pinting.util.Constants;
import com.pinting.util.DESUtil;

/**
 * 周周乐活动
 * @author SHENGUOPING
 * @2017-8-17 下午4:07:56
 */
@Controller
@Scope("prototype")
public class ScratchcardActivityController {
	
	private Logger log = LoggerFactory.getLogger(ScratchcardActivityController.class);
	
    @Autowired
    private CommunicateBusiness communicateBusiness;
    
	/**
	 * 用户中奖名单
	 * @param channel
	 * @param request
	 * @param response
     * @return
     */
	@ResponseBody
	@RequestMapping(value = "/{channel}/activity/userAwardList", method = RequestMethod.GET)
	public Map<String, Object> userAwardList(@PathVariable String channel, HttpServletRequest request, HttpServletResponse response) {
		ReqMsg_ScratchcardActivity_UserAwardList req = new ReqMsg_ScratchcardActivity_UserAwardList();
		Map<String, Object> result = new HashMap<>();
		ResMsg_ScratchcardActivity_UserAwardList res = (ResMsg_ScratchcardActivity_UserAwardList)communicateBusiness.handleMsg(req);
		if(ConstantUtil.BSRESCODE_SUCCESS.equals(res.getResCode())) {
            result.put("resCode", res.getResCode());
            result.put("resMsg", res.getResMsg());
            result.put("userAwardList", res.getAwardList());
        } else {
            result.put("resCode", res.getResCode());
            result.put("resMsg", res.getResMsg());
        }
		return result;
	}
	
	/**
	 * 用户刮奖
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/{channel}/activity/user_scratchcard")
	public Map<String, Object> userScratchcard(@PathVariable String channel, HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> result = new HashMap<String, Object>(); 
		CookieManager cookieManager = new CookieManager(request);
	    String userId = cookieManager.getValue(CookieEnums._SITE.getName(), CookieEnums._SITE_USER_ID.getName(), true);
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		Integer week = cal.get(Calendar.DAY_OF_WEEK)-1;
		if(week != Constants.WEEKHAY_WEEK_4) {
			result.put("resCode", "notDuring");
			result.put("resMsg", "期待您的下次参与~~");
			return result;
		}
		if (StringUtil.isBlank(userId)) {
			result.put("resCode", "notLogin");
			result.put("resMsg", "您还未登录");
			return result;
		} else {
			// 用户是否已刮奖
			ReqMsg_ScratchcardActivity_CountUserAward req = new ReqMsg_ScratchcardActivity_CountUserAward();
			req.setUserId(Integer.parseInt(userId));
			ResMsg_ScratchcardActivity_CountUserAward res = (ResMsg_ScratchcardActivity_CountUserAward)communicateBusiness.handleMsg(req);
			if(res.getUserAwardCount() != null && res.getUserAwardCount() > 0){
        		result.put("resCode", "hasAward");
            	result.put("resMsg", "您已刮奖");
				result.put("prizeContent", res.getPrizeContent());
            	log.info("ScratchcardActivity_CountUserAward Res{}:", res.getUserAwardCount());
        	} else {
        		// 用户是否有刮奖机会
        		ReqMsg_ScratchcardActivity_HasScratchChance hasScratchChanceReq = new ReqMsg_ScratchcardActivity_HasScratchChance();
        		hasScratchChanceReq.setUserId(Integer.parseInt(userId));
    			ResMsg_ScratchcardActivity_HasScratchChance hasScratchChanceRes = (ResMsg_ScratchcardActivity_HasScratchChance)communicateBusiness.handleMsg(hasScratchChanceReq);
        		boolean hasScratchChance = hasScratchChanceRes.isHasScratchChance();
        		log.info("ScratchcardActivityFacade HasScratchChance method: isHasScratchChance:{}, yearsInvestAmount:{}", hasScratchChanceRes.isHasScratchChance(), hasScratchChanceRes.getYearsInvestAmount());
        		if(!hasScratchChance) {
        			result.put("resCode", "noChance");
                	result.put("resMsg", "请投资参与刮奖");
        		} else {
        			// 用户刮奖并返回奖品名给前端
            		ReqMsg_ScratchcardActivity_UserScratch scratchReq = new ReqMsg_ScratchcardActivity_UserScratch();
            		scratchReq.setUserId(Integer.parseInt(userId));
            		Double investAmount = hasScratchChanceRes.getYearsInvestAmount();
            		scratchReq.setInvestAmount(investAmount);
            		ResMsg_ScratchcardActivity_UserScratch scratchRes = (ResMsg_ScratchcardActivity_UserScratch)communicateBusiness.handleMsg(scratchReq);
            		log.info("ScratchcardActivityFacade UserScratch method: prizeContent:{}", scratchRes.getPrizeContent());
            		if (ConstantUtil.BSRESCODE_SUCCESS.equals(scratchRes.getResCode())) {
            			result.put("resCode", scratchRes.getResCode());
                    	result.put("resMsg", scratchRes.getResMsg());
                    	result.put("prize", scratchRes.getPrizeContent());
            		} else {
            			result.put("resCode", scratchRes.getResCode());
                    	result.put("resMsg", scratchRes.getResMsg());
            		}
        		}
        	}
		}
		return result;
	}
	
	/**
	 * 用户刮奖App
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/{channel}/activity/user_scratchcard_app")
	public Map<String, Object> userScratchcardApp(@PathVariable String channel, HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> result = new HashMap<String, Object>(); 
		String userId = request.getParameter("userId");
		log.info("==============刮刮乐活动 刮奖时 app传过来的userId:" + userId + "==============");
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		Integer week = cal.get(Calendar.DAY_OF_WEEK)-1;
		if(week != Constants.WEEKHAY_WEEK_4) {
			result.put("resCode", "notDuring");
			result.put("resMsg", "期待你的下次参与~~");
			return result;
		}
		if (StringUtil.isBlank(userId)) {
			result.put("resCode", "notLogin");
			result.put("resMsg", "您还未登录");
			return result;
		} else {
			if(userId.length() >= 12){
				userId = new DESUtil("cfgubijn").decryptStr(userId);
			}
			// 用户是否已刮奖
			ReqMsg_ScratchcardActivity_CountUserAward req = new ReqMsg_ScratchcardActivity_CountUserAward();
			req.setUserId(Integer.parseInt(userId));
			ResMsg_ScratchcardActivity_CountUserAward res = (ResMsg_ScratchcardActivity_CountUserAward)communicateBusiness.handleMsg(req);
			if(res.getUserAwardCount() != null && res.getUserAwardCount() > 0){
        		result.put("resCode", "hasAward");
            	result.put("resMsg", "您已刮奖");
				result.put("prizeContent", res.getPrizeContent());
            	log.info("ScratchcardActivity_CountUserAward Res{}:", res.getUserAwardCount());
        	} else {
        		// 用户是否有刮奖机会
        		ReqMsg_ScratchcardActivity_HasScratchChance hasScratchChanceReq = new ReqMsg_ScratchcardActivity_HasScratchChance();
        		hasScratchChanceReq.setUserId(Integer.parseInt(userId));
    			ResMsg_ScratchcardActivity_HasScratchChance hasScratchChanceRes = (ResMsg_ScratchcardActivity_HasScratchChance)communicateBusiness.handleMsg(hasScratchChanceReq);
        		boolean hasScratchChance = hasScratchChanceRes.isHasScratchChance();
        		log.info("ScratchcardActivityFacade HasScratchChance method: isHasScratchChance:{}, yearsInvestAmount:{}", hasScratchChanceRes.isHasScratchChance(), hasScratchChanceRes.getYearsInvestAmount());
        		if(!hasScratchChance) {
        			result.put("resCode", "noChance");
                	result.put("resMsg", "请投资参与刮奖");
        		} else {
        			// 用户刮奖并返回奖品名给前端
            		ReqMsg_ScratchcardActivity_UserScratch scratchReq = new ReqMsg_ScratchcardActivity_UserScratch();
            		scratchReq.setUserId(Integer.parseInt(userId));
            		Double investAmount = hasScratchChanceRes.getYearsInvestAmount();
            		scratchReq.setInvestAmount(investAmount);
            		ResMsg_ScratchcardActivity_UserScratch scratchRes = (ResMsg_ScratchcardActivity_UserScratch)communicateBusiness.handleMsg(scratchReq);
            		log.info("ScratchcardActivityFacade UserScratch method: prizeContent:{}", scratchRes.getPrizeContent());
            		if (ConstantUtil.BSRESCODE_SUCCESS.equals(scratchRes.getResCode())) {
            			result.put("resCode", scratchRes.getResCode());
                    	result.put("resMsg", scratchRes.getResMsg());
                    	result.put("prize", scratchRes.getPrizeContent());
            		} else {
            			result.put("resCode", scratchRes.getResCode());
                    	result.put("resMsg", scratchRes.getResMsg());
            		}
        		}
        	}
		}
		return result;
	}

	//点击刮奖按钮时 判断用户是否已经抽过奖
	@ResponseBody
	@RequestMapping("/{channel}/activity/user_scraping_opportunities")
	public Map<String, Object> userScrapingOpportunities(@PathVariable String channel, HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> result = new HashMap<String, Object>();
		String client = request.getParameter("client");
		String userId = "";
		if(StringUtil.isNotEmpty(client)) {
			//app端
			userId = request.getParameter("userId");
			if(StringUtil.isBlank(userId)) {
				result.put("resCode", "notLogin");
				result.put("resMsg", "您还未登录");
				return result;
			}else {
				if(userId.length() >= 12){
					userId = new DESUtil("cfgubijn").decryptStr(userId);
				}
			}
		}else {
			//PC H5
			CookieManager cookieManager = new CookieManager(request);
			userId = cookieManager.getValue(CookieEnums._SITE.getName(), CookieEnums._SITE_USER_ID.getName(), true);
			if (StringUtil.isBlank(userId)) {
				result.put("resCode", "notLogin");
				result.put("resMsg", "您还未登录");
				return result;
			}else {
				userId = userId;
			}
		}

		ReqMsg_ScratchcardActivity_CountUserAward req = new ReqMsg_ScratchcardActivity_CountUserAward();
		req.setUserId(Integer.parseInt(userId));
		ResMsg_ScratchcardActivity_CountUserAward res = (ResMsg_ScratchcardActivity_CountUserAward)communicateBusiness.handleMsg(req);
		if(res.getUserAwardCount() != null && res.getUserAwardCount() > 0){
			result.put("resCode", "hasAward");
			result.put("resMsg", "您已刮奖");
			result.put("prize", res.getPrizeContent());
			log.info("==============点击刮奖按钮时 判断用户是否已经抽过奖============== ScratchcardActivity_CountUserAward Res{}:", res.getUserAwardCount());
		}else {
			result.put("resCode", "notAward");
			result.put("resMsg", "您未刮奖");
		}

		//刮奖乐活动 未开始状态进入活动进行中状态 判断用户是否有刮奖机会
		ReqMsg_ScratchcardActivity_HasScratchChance chanceReq = new ReqMsg_ScratchcardActivity_HasScratchChance();
		chanceReq.setUserId(Integer.parseInt(userId));
		ResMsg_ScratchcardActivity_HasScratchChance  chanceRes = (ResMsg_ScratchcardActivity_HasScratchChance) communicateBusiness.handleMsg(chanceReq);
		//是否有刮奖机会
		result.put("hasScratchChance", chanceRes.isHasScratchChance());
		log.info("==============未开始进入活动进行中判断用户是否有刮奖机会============== ScratchcardActivity_HasScratchChance Res{}:", chanceRes.isHasScratchChance());

		return  result;
	}

}
