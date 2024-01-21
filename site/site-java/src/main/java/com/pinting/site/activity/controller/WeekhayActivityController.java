package com.pinting.site.activity.controller;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.pinting.business.hessian.site.message.*;
import org.apache.commons.lang.StringUtils;
import org.codehaus.janino.Java.NewAnonymousClassInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pinting.core.cookie.CookieManager;
import com.pinting.core.util.DateUtil;
import com.pinting.core.util.StringUtil;
import com.pinting.site.common.enums.CookieEnums;
import com.pinting.site.common.utils.GlobEnv;
import com.pinting.site.communicate.CommunicateBusiness;
import com.pinting.site.weichat.util.WeChatUtil;
import com.pinting.util.Constants;
import com.pinting.util.DESUtil;
import com.pinting.util.WeChatShareUtil;

/**
 * 周周乐活动
 * @author bianyatian
 * @2017-8-4 上午11:23:15
 */
@Controller
public class WeekhayActivityController {
	private Logger log = LoggerFactory.getLogger(WeekhayActivityController.class);
	
	@Autowired
    private CommunicateBusiness communicateBusiness;
	@Autowired
	private WeChatUtil weChatUtil;
	
	/**
	 * 周周乐活动主页
	 * @param channel
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/{channel}/activity/weekhay_index")
	private String weekhayIndex(@PathVariable String channel, HttpServletRequest request, 
			HttpServletResponse response,Map<String, Object> model){
/*		if (request.getProtocol().compareTo("HTTP/1.0") == 0){    
		    response.setHeader("Pragma","no-cache");    
		}else if (request.getProtocol().compareTo("HTTP/1.1") == 0){    
		    response.setHeader("Cache-Control","no-cache");    
		} */
		
		if ("micro2.0".equals(channel)) {
            // 分享内容
            String link = GlobEnv.getWebURL("/micro2.0/activity/weekhay_index");
            String shareTitle = "币港湾周周乐，每周来欢乐！";
            String shareContent = "周周有欢乐，惊喜等着你！锁定周四，锁定币港湾！";
            String logo = GlobEnv.getWebURL("/resources/micro/images/bgw.jpg");
            WeChatShareUtil.share(channel, link, shareTitle, shareContent, logo, true, request, model, weChatUtil);
        }
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		Integer week = cal.get(Calendar.DAY_OF_WEEK)-1;
		log.info("========week:"+week+"==============");
		log.info("========constants.week:"+Constants.WEEKHAY_WEEK_4+"==============");

		String url = "";
		// 1、判断配置的banner链接是争标夺秒还是全民刮刮乐
		ReqMsg_BannerConfig_GetBanner banerReq = new ReqMsg_BannerConfig_GetBanner();
		if("micro2.0".equals(channel)) {
			banerReq.setBannerChannel(Constants.BANNER_CHANNEL_MICRO);
			banerReq.setUrl(Constants.BANNER_URL_WEEKHAY_BGW_H5);
		}else {
			banerReq.setBannerChannel(Constants.BANNER_CHANNEL_GEN);
			banerReq.setUrl(Constants.BANNER_URL_WEEKHAY_BGW_PC);
		}
		ResMsg_BannerConfig_GetBanner bannerRes = (ResMsg_BannerConfig_GetBanner) communicateBusiness.handleMsg(banerReq);
		if(Constants.WEEKHAY_FIGHT_SECONDS.equals(bannerRes.getActivityType())) {
			// 2、争标夺秒活动
			if(week != Constants.WEEKHAY_WEEK_3 && week != Constants.WEEKHAY_WEEK_4 ){
				//不是周三周四，直接跳转到周五-周二的展示页
				url = channel+"/activity/weekhay_show";
			}else{
				//判断用户是否已经设置提醒
				CookieManager cookieManager = new CookieManager(request);
				String userId = cookieManager.getValue(CookieEnums._SITE.getName(), CookieEnums._SITE_USER_ID.getName(), true);
				if(StringUtil.isNotBlank(userId)) {
					ReqMsg_WeekHayActivity_CountUserRemind req = new ReqMsg_WeekHayActivity_CountUserRemind();
					req.setUserId(Integer.parseInt(userId));
					ResMsg_WeekHayActivity_CountUserRemind res = (ResMsg_WeekHayActivity_CountUserRemind)communicateBusiness.handleMsg(req);
					if(res.getRemindCount() != null && res.getRemindCount() > 0){
						//已设置提醒
						model.put("button_remind", "hasRemind");
					}
				}

				//根据时间判断按钮状态（三个时间段活动的按钮）
				if(week == Constants.WEEKHAY_WEEK_3 ) {
					//当前时间为周三,3个按钮状态都为未开始
					model.put("button_1", "notBegin");
					model.put("button_2", "notBegin");
					model.put("button_3", "notBegin");
					Date now = new Date();
					//当前日期+1天
					String nowDateStr = DateUtil.formatDateTime(DateUtil.addDays(now, 1), DateUtil.DATE_FORMAT);
					Date start_time_1 = DateUtil.parseDateTime(nowDateStr + " " + Constants.WEEKHAY_WEEK_TIME_9_55_00);
					//按钮1从notBegin->buying的倒计时毫秒数
					model.put("countdown", Math.abs(DateUtil.getDiffeSeconds(now,start_time_1)*1000));

					//从预热开始到最后一轮
					Date last_time_start = DateUtil.parseDateTime(nowDateStr + " " + Constants.WEEKHAY_WEEK_TIME_20_00_00);
					model.put("reminde_countdown", Math.abs(DateUtil.getDiffeSeconds(now,last_time_start)*1000));
				}else if(week == Constants.WEEKHAY_WEEK_4 ) {

					//当前时间为周四
					Date now = new Date();
					//当前日期
					String nowDateStr = DateUtil.formatDateTime(now, DateUtil.DATE_FORMAT);
					/**
					 * 阶段一：9:55:00 变进行中，11:00:00变结束
					 * 阶段二：13:55:00 变进行中，15:00:00变结束
					 * 阶段三：19:55:00 变进行中，21:00:00变结束
					 */
					Date start_time_1 = DateUtil.parseDateTime(nowDateStr + " " + Constants.WEEKHAY_WEEK_TIME_9_55_00);
					Date end_time_1 = DateUtil.parseDateTime(nowDateStr + " " + Constants.WEEKHAY_WEEK_TIME_11_00_00);
					Date start_time_2 = DateUtil.parseDateTime(nowDateStr + " " + Constants.WEEKHAY_WEEK_TIME_13_55_00);
					Date end_time_2 = DateUtil.parseDateTime(nowDateStr + " " + Constants.WEEKHAY_WEEK_TIME_15_00_00);
					Date start_time_3 = DateUtil.parseDateTime(nowDateStr + " " + Constants.WEEKHAY_WEEK_TIME_19_55_00);
					Date end_time_3 = DateUtil.parseDateTime(nowDateStr + " " + Constants.WEEKHAY_WEEK_TIME_21_00_00);

					if(now.compareTo(start_time_1) < 0 ){
						//三个阶段都未开始
						model.put("button_1", "notBegin");
						model.put("button_2", "notBegin");
						model.put("button_3", "notBegin");
						//按钮1从notBegin->buying的倒计时毫秒数
						model.put("countdown", Math.abs(DateUtil.getDiffeSeconds(now,start_time_1)*1000));
					}else if(now.compareTo(end_time_1) < 0){
						model.put("button_1", "buying");
						model.put("button_2", "notBegin");
						model.put("button_3", "notBegin");
						//按钮1从buying->ending的倒计时毫秒数
						model.put("countdown", Math.abs(DateUtil.getDiffeSeconds(now,end_time_1)*1000));
					}else if(now.compareTo(start_time_2) < 0){
						model.put("button_1", "end");
						model.put("button_2", "notBegin");
						model.put("button_3", "notBegin");
						//按钮2从notBegin->buying的倒计时毫秒数
						model.put("countdown", Math.abs(DateUtil.getDiffeSeconds(now,start_time_2)*1000));
					}else if(now.compareTo(end_time_2) < 0){
						model.put("button_1", "end");
						model.put("button_2", "buying");
						model.put("button_3", "notBegin");
						//按钮2从buying->ending的倒计时毫秒数
						model.put("countdown", Math.abs(DateUtil.getDiffeSeconds(now,end_time_2)*1000));
					}else if(now.compareTo(start_time_3) < 0){
						model.put("button_1", "end");
						model.put("button_2", "end");
						model.put("button_3", "notBegin");
						//按钮3从notBegin->buying的倒计时毫秒数
						model.put("countdown", Math.abs(DateUtil.getDiffeSeconds(now,start_time_3)*1000));
						System.out.println(DateUtil.getDiffeSeconds(now,start_time_3)*1000);
					}else if(now.compareTo(end_time_3) < 0){
						model.put("button_1", "end");
						model.put("button_2", "end");
						model.put("button_3", "buying");
						//按钮3从buying->ending的倒计时毫秒数
						model.put("countdown", Math.abs(DateUtil.getDiffeSeconds(now,end_time_3)*1000));
					}else{
						model.put("button_1", "end");
						model.put("button_2", "end");
						model.put("button_3", "end");
					}

					//提醒按钮设置
					Date last_time_start = DateUtil.parseDateTime(nowDateStr + " " + Constants.WEEKHAY_WEEK_TIME_20_00_00);
					Date last_time_end = DateUtil.parseDateTime(nowDateStr + " " + Constants.WEEKHAY_WEEK_TIME_21_00_00);
					if(now.compareTo(last_time_start) >=0 && now.compareTo(last_time_end) < 0){
						model.put("button_remind", "last");
						//从最后一轮到结束
						model.put("reminde_countdown", Math.abs(DateUtil.getDiffeSeconds(now,last_time_end)*1000));
					}else if(now.compareTo(last_time_end) >= 0){
						model.put("button_remind", "end");
					}else{
						//从预热开始到最后一轮
						model.put("reminde_countdown", Math.abs(DateUtil.getDiffeSeconds(now,last_time_start)*1000));
					}

				}
				url = channel+"/activity/weekhay_index";
			}
		}else if (Constants.WEEKHAY_SCRATCH_CARD.equals(bannerRes.getActivityType())){
			// 3、刮刮乐活动

			CookieManager cookieManager = new CookieManager(request);
			String userId = cookieManager.getValue(CookieEnums._SITE.getName(), CookieEnums._SITE_USER_ID.getName(), true);
			model.put("userId", userId);
			//systemFormatTime用于判断页面实时刷新
			model.put("systemFormatTime", DateUtil.formatWeekInMonth(new Date()) +"_"+ DateUtil.format(new Date()));

			if(week == Constants.WEEKHAY_WEEK_1 || week == Constants.WEEKHAY_WEEK_2) {
				// 3.1、预热页 周一0:00:00 ~ 周二23:59:59
				url =  channel+"/activity/weekhay_show";
			}else {
				if(week == Constants.WEEKHAY_WEEK_3) {
					// 3.2、活动开始页 周三0:00:00 ~ 周三23:59:59 活动未开始
					model.put("ticketsPlayStatus", "isNotStart");
				}else if(week == Constants.WEEKHAY_WEEK_4) {
					// 3.3、活动开始页 周四0:00:00 ~ 周四24:00:00 活动进行中
					model.put("ticketsPlayStatus", "isStart");
					if(StringUtils.isNotEmpty(userId)) {
						ReqMsg_ScratchcardActivity_HasScratchChance chanceReq = new ReqMsg_ScratchcardActivity_HasScratchChance();
						chanceReq.setUserId(Integer.parseInt(userId));
						ResMsg_ScratchcardActivity_HasScratchChance  chanceRes = (ResMsg_ScratchcardActivity_HasScratchChance) communicateBusiness.handleMsg(chanceReq);
						//是否有刮奖机会
						model.put("hasScratchChance", chanceRes.isHasScratchChance());

						ReqMsg_ScratchcardActivity_CountUserAward countAwardReq = new ReqMsg_ScratchcardActivity_CountUserAward();
						countAwardReq.setUserId(Integer.parseInt(userId));
						ResMsg_ScratchcardActivity_CountUserAward countAwardRes = (ResMsg_ScratchcardActivity_CountUserAward) communicateBusiness.handleMsg(countAwardReq);
						//刮刮乐用户中奖次数
						model.put("userAwardCount", countAwardRes.getUserAwardCount());

						//奖品
						ReqMsg_ScratchcardActivity_ScratchcardPrize prizeReq = new ReqMsg_ScratchcardActivity_ScratchcardPrize();
						prizeReq.setUserId(Integer.parseInt(userId));
						ResMsg_ScratchcardActivity_ScratchcardPrize  prizeRes = (ResMsg_ScratchcardActivity_ScratchcardPrize) communicateBusiness.handleMsg(prizeReq);
						model.put("isScratch", prizeRes.getIsScratch());
						model.put("prizeContent", prizeRes.getPrizeContent());
					}
				}else {
					// 3.4、抽奖结果页 活动结果页 周五0:00:01 ~ 周日23:59:59 活动已结束
					model.put("ticketsPlayStatus", "isEnd");

					// 3.5、判断用户是否已经抽奖
					if(StringUtils.isNotEmpty(userId)) {
						ReqMsg_ScratchcardActivity_ScratchcardPrize prizeReq = new ReqMsg_ScratchcardActivity_ScratchcardPrize();
						prizeReq.setUserId(Integer.parseInt(userId));
						ResMsg_ScratchcardActivity_ScratchcardPrize  prizeRes = (ResMsg_ScratchcardActivity_ScratchcardPrize) communicateBusiness.handleMsg(prizeReq);
						model.put("isScratch", prizeRes.getIsScratch());
						model.put("prizeContent", prizeRes.getPrizeContent());
						log.info("========刮刮乐活动结束时用户中奖的奖品prizeContent :" + prizeRes.getPrizeContent() + "==============");
					}
				}

				url = channel+"/activity/2017/scratchCard/scratchCard_index";
			}
		} else if (Constants.WEEKHAY_LUCKY_LENDERS.equals(bannerRes.getActivityType())){
			// 4、幸运出借人活动
			//查询配置表，
			ReqMsg_WeekHayActivity_CheckWeekhayStatus req = new ReqMsg_WeekHayActivity_CheckWeekhayStatus();
			req.setActivityType(Constants.WEEKHAY_LUCKY_LENDERS);
			ResMsg_WeekHayActivity_CheckWeekhayStatus res = (ResMsg_WeekHayActivity_CheckWeekhayStatus)communicateBusiness.handleMsg(req);
			String displayTime = res.getDisplayTime();
			Integer activityStartTime = res.getActivityStartTime();
			
			if(displayTime.indexOf(week+"") != -1 ){
				url = channel+"/activity/weekhay_show";
			} else {
				//预热页 周一0:00:00 ~ 周二23:59:59
				Date now = new Date();
				//当前日期
				String nowDateStr = DateUtil.formatDateTime(DateUtil.addDays(now, 1), DateUtil.DATE_FORMAT);
				Date start_time = DateUtil.parseDateTime(nowDateStr + " " + Constants.WEEKHAY_WEEK_TIME_0_00_00);
				//按钮1从notBegin->buying的倒计时毫秒数
				model.put("countdown", Math.abs(DateUtil.getDiffeSeconds(now,start_time)*1000));
				
				if (week != 0 && week < activityStartTime) {
					model.put("participate", "notBegin");
				} else if (week == activityStartTime) {
					model.put("participate", "processing");
				} else {
					model.put("participate", "end");
					ReqMsg_ActivityLuckyDraw_LuckyLenders luckyReq = new ReqMsg_ActivityLuckyDraw_LuckyLenders();
					luckyReq.setActivityId(Constants.ACTIVITY_LUCKY_LENDERS); //活动编号
					ResMsg_ActivityLuckyDraw_LuckyLenders luckyRes = (ResMsg_ActivityLuckyDraw_LuckyLenders)communicateBusiness.handleMsg(luckyReq);
					model.put("luckyList", luckyRes.getLuckyList());
				}
				url = channel+"/activity/lucky_lenders";
			}
		} else {
			// 活动类型既不是刮刮乐活动，又不是争标夺秒活动
			log.info("==============周周乐活动的活动类型不存在，请联系运营人员==============");
			// url 链接不存在
			model.put("error_reason_info", "币港湾周周乐活动正在维护，");
			model.put("description_info", "请您敬请期待周周乐归来！");
			url = channel+"/regular/url_not_find";
		}

		return url;
	}
	
	
	/**
	 * 周周乐活动-添加短信提醒
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/{channel}/activity/weekhay_addRemind")
	public Map<String, Object> weekhayAddRemind(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> result = new HashMap<String, Object>();
        CookieManager cookieManager = new CookieManager(request);
        String userId = cookieManager.getValue(CookieEnums._SITE.getName(), CookieEnums._SITE_USER_ID.getName(), true);
        Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		Integer week = cal.get(Calendar.DAY_OF_WEEK)-1;
		if(week != Constants.WEEKHAY_WEEK_4 && week != Constants.WEEKHAY_WEEK_3 ){
			//非活动时间，不体现
			result.put("resCode", "refresh");
			result.put("resMsg", "请您刷新页面重新参与~~");
			return result;
		}
        if(StringUtil.isBlank(userId)) {
        	result.put("resCode", "notLogin");
        	result.put("resMsg", "您还未登录");
        	return result;
        }else{
        	ReqMsg_WeekHayActivity_CountUserRemind req = new ReqMsg_WeekHayActivity_CountUserRemind();
        	req.setUserId(Integer.parseInt(userId));
        	ResMsg_WeekHayActivity_CountUserRemind res = (ResMsg_WeekHayActivity_CountUserRemind)communicateBusiness.handleMsg(req);
        	if(res.getRemindCount() != null && res.getRemindCount() > 0){
        		result.put("resCode", "hasRemind");
            	result.put("resMsg", "您已设置短信提醒");
        	}else{
        		Date now = new Date();
				//当前日期
				String nowDateStr = DateUtil.formatDateTime(now, DateUtil.DATE_FORMAT);
        		//周四19:55:00点之后不再设置提醒
        		if(week == Constants.WEEKHAY_WEEK_4 ){
        			Date end_time1 = DateUtil.parseDateTime(nowDateStr + " " + Constants.WEEKHAY_WEEK_TIME_19_55_00);
        			Date end_time2 = DateUtil.parseDateTime(nowDateStr + " " + Constants.WEEKHAY_WEEK_TIME_20_00_00);
        			if(now.compareTo(end_time1) > 0 && now.compareTo(end_time2) < 0){
        				result.put("resCode", "refresh");
                    	result.put("resMsg", "本时段活动即将开始，请您锁定页面。");
                    	return result;
        			}else if(now.compareTo(end_time2) >= 0){
        				result.put("resCode", "refresh");
        				result.put("resMsg", "请您刷新页面重新参与~~");
    					return result;
        			}
        		}
        		ReqMsg_WeekHayActivity_AddUserRemind reqAdd = new ReqMsg_WeekHayActivity_AddUserRemind();
        		reqAdd.setUserId(Integer.parseInt(userId));
        		
        		ResMsg_WeekHayActivity_AddUserRemind resAdd = (ResMsg_WeekHayActivity_AddUserRemind)communicateBusiness.handleMsg(reqAdd);
        		if("999".equals(resAdd.getResCode())){
        			result.put("resCode", res.getResCode());
                	result.put("resMsg", res.getResMsg());
        		}else{
        			result.put("resCode", "000");
        			//提醒添加成功，不同时间段返回不同文案
        			if(week == Constants.WEEKHAY_WEEK_3 ){
        				result.put("resMsg", "活动开始前5分钟您将收到短信提醒。");
        			}else{
        				
        				/**
        				 * 阶段一：周四9:55:00之前，活动开始前5分钟您将收到短信提醒。
        				 * 阶段二：周四9:55:01——周四9:59:59
        				 * 阶段三：周四10:00:00——周四13:55:00
        				 * 阶段四：周四13:55:01——周四13:59:59
        				 * 阶段五：周四14:00:00——周四19:55:00
        				 * 阶段六：周四19:55:01——周四19:59:59
        				 */
        				Date end_time_1 = DateUtil.parseDateTime(nowDateStr + " " + Constants.WEEKHAY_WEEK_TIME_9_55_00);
        				Date end_time_2 = DateUtil.parseDateTime(nowDateStr + " " + Constants.WEEKHAY_WEEK_TIME_10_00_00);	
        				Date end_time_3 = DateUtil.parseDateTime(nowDateStr + " " + Constants.WEEKHAY_WEEK_TIME_13_55_00);
        				Date end_time_4 = DateUtil.parseDateTime(nowDateStr + " " + Constants.WEEKHAY_WEEK_TIME_14_00_00);
        				Date end_time_5 = DateUtil.parseDateTime(nowDateStr + " " + Constants.WEEKHAY_WEEK_TIME_19_55_00);
        				if(now.compareTo(end_time_1) <= 0){
        					result.put("resMsg", "活动开始前5分钟您将收到短信提醒。");
        					return result;
        				}else if(now.compareTo(end_time_2) <= 0){
        					result.put("resMsg", "本时段活动即将开始，请您锁定页面，下一时段活动开始前5分钟您将收到短信提醒。");
        					return result;
        				}else if(now.compareTo(end_time_3) <= 0){
        					result.put("resMsg", "下一场活动开始前5分钟您将收到短信提醒。");
        					return result;
        				}else if(now.compareTo(end_time_4) <= 0){
        					result.put("resMsg", "本时段活动即将开始，请您锁定页面，下一时段活动开始前5分钟您将收到短信提醒。");
        					return result;
        				}else if(now.compareTo(end_time_5) <= 0){
        					result.put("resMsg", "下一场活动开始前5分钟您将收到短信提醒。");
        					return result;
        				}
        			}
        		}
        	}
        }
        
        return result;
	}
	
	/**
	 * 周周乐活动主页-APP
	 * @param channel
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/{channel}/activity/weekhay_index_app")
	private String weekhayIndexAPP(@PathVariable String channel, HttpServletRequest request, 
			HttpServletResponse response,Map<String, Object> model){
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		Integer week = cal.get(Calendar.DAY_OF_WEEK)-1;
		String url = "";
		// 1、判断配置的banner链接是争标夺秒还是全民刮刮乐
		ReqMsg_BannerConfig_GetBanner banerReq = new ReqMsg_BannerConfig_GetBanner();
		banerReq.setBannerChannel(Constants.BANNER_CHANNEL_APP);
		banerReq.setUrl(Constants.BANNER_URL_WEEKHAY_APP);
		ResMsg_BannerConfig_GetBanner bannerRes = (ResMsg_BannerConfig_GetBanner) communicateBusiness.handleMsg(banerReq);
		if(Constants.WEEKHAY_FIGHT_SECONDS.equals(bannerRes.getActivityType())) {
			// 2、争标夺秒活动
			if(week != Constants.WEEKHAY_WEEK_4 && week != Constants.WEEKHAY_WEEK_3 ){
				//不是周三周四，直接跳转到周五-周二的展示页
				url = channel+"/activity/weekhay_show";
			}else{
				//判断用户是否已经设置提醒
				CookieManager cookieManager = new CookieManager(request);
				String client = request.getParameter("client");
				model.put("client", client);
				String userId = request.getParameter("userId");
				if(StringUtil.isNotBlank(userId)) {
					if(userId.length() >= 12){
						userId = new DESUtil("cfgubijn").decryptStr(userId);
					}
					ReqMsg_WeekHayActivity_CountUserRemind req = new ReqMsg_WeekHayActivity_CountUserRemind();
					req.setUserId(Integer.parseInt(userId));
					model.put("userId", userId);
					ResMsg_WeekHayActivity_CountUserRemind res = (ResMsg_WeekHayActivity_CountUserRemind)communicateBusiness.handleMsg(req);
					if(res.getRemindCount() != null && res.getRemindCount() > 0){
						//已设置提醒
						model.put("button_remind", "hasRemind");
					}
				}

				//根据时间判断按钮状态（三个时间段活动的按钮）
				if(week == Constants.WEEKHAY_WEEK_3 ) {
					//当前时间为周三,3个按钮状态都为未开始
					model.put("button_1", "notBegin");
					model.put("button_2", "notBegin");
					model.put("button_3", "notBegin");
					Date now = new Date();
					//当前日期
					String nowDateStr = DateUtil.formatDateTime(DateUtil.addDays(now, 1), DateUtil.DATE_FORMAT);
					Date start_time_1 = DateUtil.parseDateTime(nowDateStr + " " + Constants.WEEKHAY_WEEK_TIME_9_55_00);
					//按钮1从notBegin->buying的倒计时毫秒数
					model.put("countdown", Math.abs(DateUtil.getDiffeSeconds(now,start_time_1)*1000));

					//从预热开始到最后一轮
					Date last_time_start = DateUtil.parseDateTime(nowDateStr + " " + Constants.WEEKHAY_WEEK_TIME_20_00_00);
					model.put("reminde_countdown", Math.abs(DateUtil.getDiffeSeconds(now,last_time_start)*1000));
				}else if(week == Constants.WEEKHAY_WEEK_4 ) {

					//当前时间为周四
					Date now = new Date();
					//当前日期
					String nowDateStr = DateUtil.formatDateTime(now, DateUtil.DATE_FORMAT);
					/**
					 * 阶段一：9:55:00 变进行中，11:00:00变结束
					 * 阶段二：13:55:00 变进行中，15:00:00变结束
					 * 阶段三：19:55:00 变进行中，21:00:00变结束
					 */
					Date start_time_1 = DateUtil.parseDateTime(nowDateStr + " " + Constants.WEEKHAY_WEEK_TIME_9_55_00);
					Date end_time_1 = DateUtil.parseDateTime(nowDateStr + " " + Constants.WEEKHAY_WEEK_TIME_11_00_00);
					Date start_time_2 = DateUtil.parseDateTime(nowDateStr + " " + Constants.WEEKHAY_WEEK_TIME_13_55_00);
					Date end_time_2 = DateUtil.parseDateTime(nowDateStr + " " + Constants.WEEKHAY_WEEK_TIME_15_00_00);
					Date start_time_3 = DateUtil.parseDateTime(nowDateStr + " " + Constants.WEEKHAY_WEEK_TIME_19_55_00);
					Date end_time_3 = DateUtil.parseDateTime(nowDateStr + " " + Constants.WEEKHAY_WEEK_TIME_21_00_00);

					if(now.compareTo(start_time_1) < 0 ){
						//三个阶段都未开始
						model.put("button_1", "notBegin");
						model.put("button_2", "notBegin");
						model.put("button_3", "notBegin");
						//按钮1从notBegin->buying的倒计时毫秒数
						model.put("countdown", Math.abs(DateUtil.getDiffeSeconds(now,start_time_1)*1000));
					}else if(now.compareTo(end_time_1) < 0){
						model.put("button_1", "buying");
						model.put("button_2", "notBegin");
						model.put("button_3", "notBegin");
						//按钮1从buying->ending的倒计时毫秒数
						model.put("countdown", Math.abs(DateUtil.getDiffeSeconds(now,end_time_1)*1000));
					}else if(now.compareTo(start_time_2) < 0){
						model.put("button_1", "end");
						model.put("button_2", "notBegin");
						model.put("button_3", "notBegin");
						//按钮2从notBegin->buying的倒计时毫秒数
						model.put("countdown", Math.abs(DateUtil.getDiffeSeconds(now,start_time_2)*1000));
					}else if(now.compareTo(end_time_2) < 0){
						model.put("button_1", "end");
						model.put("button_2", "buying");
						model.put("button_3", "notBegin");
						//按钮2从buying->ending的倒计时毫秒数
						model.put("countdown", Math.abs(DateUtil.getDiffeSeconds(now,end_time_2)*1000));
					}else if(now.compareTo(start_time_3) < 0){
						model.put("button_1", "end");
						model.put("button_2", "end");
						model.put("button_3", "notBegin");
						//按钮3从notBegin->buying的倒计时毫秒数
						model.put("countdown", Math.abs(DateUtil.getDiffeSeconds(now,start_time_3)*1000));
						System.out.println(DateUtil.getDiffeSeconds(now,start_time_3)*1000);
					}else if(now.compareTo(end_time_3) < 0){
						model.put("button_1", "end");
						model.put("button_2", "end");
						model.put("button_3", "buying");
						//按钮3从buying->ending的倒计时毫秒数
						model.put("countdown", Math.abs(DateUtil.getDiffeSeconds(now,end_time_3)*1000));
					}else{
						model.put("button_1", "end");
						model.put("button_2", "end");
						model.put("button_3", "end");
					}

					//提醒按钮设置
					Date last_time_start = DateUtil.parseDateTime(nowDateStr + " " + Constants.WEEKHAY_WEEK_TIME_20_00_00);
					Date last_time_end = DateUtil.parseDateTime(nowDateStr + " " + Constants.WEEKHAY_WEEK_TIME_21_00_00);
					if(now.compareTo(last_time_start) >=0 && now.compareTo(last_time_end) < 0){
						model.put("button_remind", "last");
						//从最后一轮到结束
						model.put("reminde_countdown", Math.abs(DateUtil.getDiffeSeconds(now,last_time_end)*1000));
					}else if(now.compareTo(last_time_end) >= 0){
						model.put("button_remind", "end");
					}else{
						//从预热开始到最后一轮
						model.put("reminde_countdown", Math.abs(DateUtil.getDiffeSeconds(now,last_time_start)*1000));
					}

				}
				url = channel+"/activity/weekhay_index_app";
			}
		}else if(Constants.WEEKHAY_SCRATCH_CARD.equals(bannerRes.getActivityType())) {
			String client = request.getParameter("client");
			model.put("client", client);
			String userId = request.getParameter("userId");
			//systemFormatTime用于判断页面实时刷新
			model.put("systemFormatTime", DateUtil.formatWeekInMonth(new Date()) +"_"+ DateUtil.format(new Date()));

			if(StringUtils.isNotEmpty(userId)) {
				model.put("isLogin", "true");
				if(userId.length() >= 12){
					userId = new DESUtil("cfgubijn").decryptStr(userId);
				}
				model.put("userId", userId);
			}else {
				model.put("isLogin", "false");
			}

			log.info("========刮刮乐活动app传过来的userId:" + userId + "==============");

			// 3、刮刮乐活动
			if(week == Constants.WEEKHAY_WEEK_1 || week == Constants.WEEKHAY_WEEK_2) {
				// 3.1、预热页 周一0:00:00 ~ 周二23:59:59
				url =  channel+"/activity/weekhay_show";
			}else {
				if(week == Constants.WEEKHAY_WEEK_3) {
					// 3.2、活动开始页 周三0:00:00 ~ 周三23:59:59 活动未开始
					model.put("ticketsPlayStatus", "isNotStart");
				}else if(week == Constants.WEEKHAY_WEEK_4) {
					// 3.3、活动开始页 周四0:00:00 ~ 周四24:00:00 活动进行中
					model.put("ticketsPlayStatus", "isStart");
					if(StringUtils.isNotEmpty(userId)) {
						ReqMsg_ScratchcardActivity_HasScratchChance chanceReq = new ReqMsg_ScratchcardActivity_HasScratchChance();
						chanceReq.setUserId(Integer.parseInt(userId));
						ResMsg_ScratchcardActivity_HasScratchChance  chanceRes = (ResMsg_ScratchcardActivity_HasScratchChance) communicateBusiness.handleMsg(chanceReq);
						//是否有刮奖机会
						model.put("hasScratchChance", chanceRes.isHasScratchChance());

						ReqMsg_ScratchcardActivity_CountUserAward countAwardReq = new ReqMsg_ScratchcardActivity_CountUserAward();
						countAwardReq.setUserId(Integer.parseInt(userId));
						ResMsg_ScratchcardActivity_CountUserAward countAwardRes = (ResMsg_ScratchcardActivity_CountUserAward) communicateBusiness.handleMsg(countAwardReq);
						//刮刮乐用户中奖次数
						model.put("userAwardCount", countAwardRes.getUserAwardCount());

						//奖品
						ReqMsg_ScratchcardActivity_ScratchcardPrize prizeReq = new ReqMsg_ScratchcardActivity_ScratchcardPrize();
						prizeReq.setUserId(Integer.parseInt(userId));
						ResMsg_ScratchcardActivity_ScratchcardPrize  prizeRes = (ResMsg_ScratchcardActivity_ScratchcardPrize) communicateBusiness.handleMsg(prizeReq);
						model.put("isScratch", prizeRes.getIsScratch());
						model.put("prizeContent", prizeRes.getPrizeContent());
					}
				}else {
					// 3.4、抽奖结果页 活动结果页 周五0:00:01 ~ 周日23:59:59 活动已结束
					model.put("ticketsPlayStatus", "isEnd");

					// 3.5、判断用户是否已经抽奖
					if(StringUtils.isNotEmpty(userId)) {
						ReqMsg_ScratchcardActivity_ScratchcardPrize prizeReq = new ReqMsg_ScratchcardActivity_ScratchcardPrize();
						prizeReq.setUserId(Integer.parseInt(userId));
						ResMsg_ScratchcardActivity_ScratchcardPrize  prizeRes = (ResMsg_ScratchcardActivity_ScratchcardPrize) communicateBusiness.handleMsg(prizeReq);
						model.put("isScratch", prizeRes.getIsScratch());
						model.put("prizeContent", prizeRes.getPrizeContent());
					}
				}

				url = channel+"/activity/2017/scratchCard/scratchCard_index_app";
			}
		}else if (Constants.WEEKHAY_LUCKY_LENDERS.equals(bannerRes.getActivityType())){
			// 4、幸运出借人活动
			//查询配置表，
			String client = request.getParameter("client");
			model.put("client", client);
			ReqMsg_WeekHayActivity_CheckWeekhayStatus req = new ReqMsg_WeekHayActivity_CheckWeekhayStatus();
			req.setActivityType(Constants.WEEKHAY_LUCKY_LENDERS);
			ResMsg_WeekHayActivity_CheckWeekhayStatus res = (ResMsg_WeekHayActivity_CheckWeekhayStatus)communicateBusiness.handleMsg(req);
			String displayTime = res.getDisplayTime();
			Integer activityStartTime = res.getActivityStartTime();
			
			if(displayTime.indexOf(week+"") != -1 ){
				url = channel+"/activity/weekhay_show";
			} else {
				//预热页 周一0:00:00 ~ 周二23:59:59
				Date now = new Date();
				//当前日期
				String nowDateStr = DateUtil.formatDateTime(DateUtil.addDays(now, 1), DateUtil.DATE_FORMAT);
				Date start_time = DateUtil.parseDateTime(nowDateStr + " " + Constants.WEEKHAY_WEEK_TIME_0_00_00);
				//按钮1从notBegin->buying的倒计时毫秒数
				model.put("countdown", Math.abs(DateUtil.getDiffeSeconds(now,start_time)*1000));
				
				if (week != 0 && week < activityStartTime) {
					model.put("participate", "notBegin");
				} else if (week == activityStartTime) {
					model.put("participate", "processing");
				} else {
					model.put("participate", "end");
					ReqMsg_ActivityLuckyDraw_LuckyLenders luckyReq = new ReqMsg_ActivityLuckyDraw_LuckyLenders();
					luckyReq.setActivityId(Constants.ACTIVITY_LUCKY_LENDERS); //活动编号
					ResMsg_ActivityLuckyDraw_LuckyLenders luckyRes = (ResMsg_ActivityLuckyDraw_LuckyLenders)communicateBusiness.handleMsg(luckyReq);
					model.put("luckyList", luckyRes.getLuckyList());
				}
				url = channel+"/activity/lucky_lenders_app";
			}
		} else {
			// 活动类型既不是刮刮乐活动，又不是争标夺秒活动
			log.info("==============周周乐活动的活动类型不存在，请联系运营人员==============");
			// url 链接不存在
			String client = request.getParameter("client");
			model.put("client", client);
			model.put("error_reason_info", "币港湾周周乐活动正在维护，");
			model.put("description_info", "请您敬请期待周周乐归来！");
			url = channel+"/regular/url_not_find_app";
		}

		return url;
	}
	
	
	/**
	 * 周周乐活动-添加短信提醒
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/{channel}/activity/weekhay_addRemind_app")
	public Map<String, Object> weekhayAddRemindAPP(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> result = new HashMap<String, Object>();
        String userId = request.getParameter("userId");
        Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		Integer week = cal.get(Calendar.DAY_OF_WEEK)-1;
		if(week != Constants.WEEKHAY_WEEK_4 && week != Constants.WEEKHAY_WEEK_3 ){
			//非活动时间，不体现
			result.put("resCode", "refresh");
			result.put("resMsg", "请您刷新页面重新参与~~");
			return result;
		}
        if(StringUtil.isBlank(userId)) {
        	result.put("resCode", "notLogin");
        	result.put("resMsg", "您还未登录");
        	return result;
        }else{
        	ReqMsg_WeekHayActivity_CountUserRemind req = new ReqMsg_WeekHayActivity_CountUserRemind();
        	req.setUserId(Integer.parseInt(userId));
        	ResMsg_WeekHayActivity_CountUserRemind res = (ResMsg_WeekHayActivity_CountUserRemind)communicateBusiness.handleMsg(req);
        	if(res.getRemindCount() != null && res.getRemindCount() > 0){
        		result.put("resCode", "hasRemind");
            	result.put("resMsg", "您已设置短信提醒");
        	}else{
        		Date now = new Date();
				//当前日期
				String nowDateStr = DateUtil.formatDateTime(now, DateUtil.DATE_FORMAT);
        		//周四19:55:00点之后不再设置提醒
        		if(week == Constants.WEEKHAY_WEEK_4 ){
        			Date end_time1 = DateUtil.parseDateTime(nowDateStr + " " + Constants.WEEKHAY_WEEK_TIME_19_55_00);
        			Date end_time2 = DateUtil.parseDateTime(nowDateStr + " " + Constants.WEEKHAY_WEEK_TIME_20_00_00);
        			if(now.compareTo(end_time1) > 0 && now.compareTo(end_time2) < 0){
        				result.put("resCode", "refresh");
                    	result.put("resMsg", "本时段活动即将开始，请您锁定页面。");
                    	return result;
        			}else if(now.compareTo(end_time2) >= 0){
        				result.put("resCode", "refresh");
        				result.put("resMsg", "请您刷新页面重新参与~~");
    					return result;
        			}
        		}
        		ReqMsg_WeekHayActivity_AddUserRemind reqAdd = new ReqMsg_WeekHayActivity_AddUserRemind();
        		reqAdd.setUserId(Integer.parseInt(userId));
        		
        		ResMsg_WeekHayActivity_AddUserRemind resAdd = (ResMsg_WeekHayActivity_AddUserRemind)communicateBusiness.handleMsg(reqAdd);
        		if("999".equals(resAdd.getResCode())){
        			result.put("resCode", res.getResCode());
                	result.put("resMsg", res.getResMsg());
        		}else{
        			result.put("resCode", "000");
        			//提醒添加成功，不同时间段返回不同文案
        			if(week == Constants.WEEKHAY_WEEK_3 ){
        				result.put("resMsg", "活动开始前5分钟您将收到短信提醒。");
        			}else{
        				
        				/**
        				 * 阶段一：周四9:55:00之前，活动开始前5分钟您将收到短信提醒。
        				 * 阶段二：周四9:55:01——周四9:59:59
        				 * 阶段三：周四10:00:00——周四13:55:00
        				 * 阶段四：周四13:55:01——周四13:59:59
        				 * 阶段五：周四14:00:00——周四19:55:00
        				 * 阶段六：周四19:55:01——周四19:59:59
        				 */
        				Date end_time_1 = DateUtil.parseDateTime(nowDateStr + " " + Constants.WEEKHAY_WEEK_TIME_9_55_00);
        				Date end_time_2 = DateUtil.parseDateTime(nowDateStr + " " + Constants.WEEKHAY_WEEK_TIME_10_00_00);	
        				Date end_time_3 = DateUtil.parseDateTime(nowDateStr + " " + Constants.WEEKHAY_WEEK_TIME_13_55_00);
        				Date end_time_4 = DateUtil.parseDateTime(nowDateStr + " " + Constants.WEEKHAY_WEEK_TIME_14_00_00);
        				Date end_time_5 = DateUtil.parseDateTime(nowDateStr + " " + Constants.WEEKHAY_WEEK_TIME_19_55_00);
        				if(now.compareTo(end_time_1) <= 0){
        					result.put("resMsg", "活动开始前5分钟您将收到短信提醒。");
        					return result;
        				}else if(now.compareTo(end_time_2) <= 0){
        					result.put("resMsg", "本时段活动即将开始，请您锁定页面，下一时段活动开始前5分钟您将收到短信提醒。");
        					return result;
        				}else if(now.compareTo(end_time_3) <= 0){
        					result.put("resMsg", "下一场活动开始前5分钟您将收到短信提醒。");
        					return result;
        				}else if(now.compareTo(end_time_4) <= 0){
        					result.put("resMsg", "本时段活动即将开始，请您锁定页面，下一时段活动开始前5分钟您将收到短信提醒。");
        					return result;
        				}else if(now.compareTo(end_time_5) <= 0){
        					result.put("resMsg", "下一场活动开始前5分钟您将收到短信提醒。");
        					return result;
        				}
        			}
        		}
        	}
        }
        
        return result;
	}

}
