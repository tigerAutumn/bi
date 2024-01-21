package com.pinting.site.game.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pinting.business.hessian.site.message.ReqMsg_Game_Activity;
import com.pinting.business.hessian.site.message.ReqMsg_Game_ActivityUser;
import com.pinting.business.hessian.site.message.ReqMsg_Game_Bonus;
import com.pinting.business.hessian.site.message.ReqMsg_Game_BonusCheck;
import com.pinting.business.hessian.site.message.ReqMsg_Game_BonusList;
import com.pinting.business.hessian.site.message.ReqMsg_Game_Check;
import com.pinting.business.hessian.site.message.ReqMsg_Home_InfoQuery;
import com.pinting.business.hessian.site.message.ResMsg_Game_Activity;
import com.pinting.business.hessian.site.message.ResMsg_Game_ActivityUser;
import com.pinting.business.hessian.site.message.ResMsg_Game_Bonus;
import com.pinting.business.hessian.site.message.ResMsg_Game_BonusCheck;
import com.pinting.business.hessian.site.message.ResMsg_Game_BonusList;
import com.pinting.business.hessian.site.message.ResMsg_Game_Check;
import com.pinting.core.base.BaseController;
import com.pinting.core.cookie.CookieManager;
import com.pinting.site.weichat.util.WeChatUtil;
import com.pinting.site.common.enums.CookieEnums;
import com.pinting.site.communicate.CommunicateBusiness;

@Controller
public class GamesController extends BaseController {

	@Autowired
	private CommunicateBusiness gameService;
	@Autowired
	private WeChatUtil weChatUtil;

	/**
	 * 数钱游戏
	 * 
	 * @param channel
	 * @param request
	 * @param response
	 * @param regHome
	 * @param model
	 * @return
	 */
	@RequestMapping("{channel}/games/game")
	public String gameInit(@PathVariable String channel,
			HttpServletRequest request, HttpServletResponse response,
			ReqMsg_Home_InfoQuery regHome, Map<String, Object> model) {

		return channel + "/games/game";
	}

	/**
	 * 玩游戏前介绍页面，需获取用户微信信息认证
	 * 
	 * @param channel
	 * @param request
	 * @param response
	 * @param req
	 * @param dataMap
	 * @return
	 */
	@RequestMapping("{channel}/games/share")
	public String shareInit(@PathVariable String channel,
			HttpServletRequest request, HttpServletResponse response,
			ReqMsg_Game_Check req, HashMap<String, Object> dataMap) {
		CookieManager manager = new CookieManager(request);
		String openId = manager.getValue(CookieEnums._SITE.getName(),
				CookieEnums._SITE_OPEN_ID.getName(), true);
		// 本地，所以指定了openid="onrI-swEgeScSSi9bsX1gp3deTJ0"本地调试请打开
		
		  /*if (openId == null || "".equals(openId)) { openId =
		  "onrI-s_i71rc_KKlLjBARGj5FkQM"; }*/
		
		// 这里要特别注意，activityUserId这个值其实是微信user表的id，不等于null的话代表是别人分享的
		String activityUserId = request.getParameter("activityUserId");
		if (activityUserId != null) {
			dataMap.put("activityUserId", activityUserId);
		}
		req.setOpenId(openId);
		
		ResMsg_Game_Check resp = (ResMsg_Game_Check) gameService.handleMsg(req);
		// 根据openid来check该用户是不是已经玩过，玩过则返回info=1，没玩过返回0，activityUserId如果不等于null的话代表是别人分享的，玩家助力
		if (resp.getInfo() != null && resp.getInfo() == 1
				&& activityUserId == null) {
			ReqMsg_Game_BonusList req2 = new ReqMsg_Game_BonusList();
			req2.setOpenId(openId);
			ResMsg_Game_BonusList resp2 = (ResMsg_Game_BonusList) gameService
					.handleMsg(req2);
			dataMap.put("lists", resp2.getList());
			Map<String, String> sign = weChatUtil.createAuth(request);
			dataMap.put("weichat", sign);
			return channel + "/games/shareus";
		} else {
			if(resp.getMobile()!=null)
			{
				dataMap.put("oldMobile", resp.getMobile());
			}
			return channel + "/games/share";
		}
	}

	/*
	 * @RequestMapping("{channel}/games/check") public @ResponseBody
	 * HashMap<String, Object> checkInit(@PathVariable String channel,
	 * HttpServletRequest request, HttpServletResponse response,
	 * ReqMsg_Game_Check req, HashMap<String, Object> dataMap) { CookieManager
	 * manager = new CookieManager(request); String openId =
	 * manager.getValue(CookieEnums._SITE.getName(),
	 * CookieEnums._SITE_OPEN_ID.getName(), true); //
	 * 本地，所以指定了openid="onrI-swEgeScSSi9bsX1gp3deTJ0"
	 * 
	 * if (openId == null || "".equals(openId)) { openId =
	 * "onrI-s3gRiblkzVMQYyLjFP7VTpk"; } req.setOpenId(openId);
	 * ResMsg_Game_Check resp = (ResMsg_Game_Check) gameService.handleMsg(req);
	 * dataMap.put("msg", resp.getResMsg()); return dataMap;
	 * 
	 * }
	 */
	/**
	 * 已经玩过了，介绍游戏页面就变成了此页面
	 * 
	 * @param channel
	 * @param request
	 * @param response
	 * @param req
	 * @param dataMap
	 * @return
	 */
	@RequestMapping("{channel}/games/shareus")
	public String shareusInit(@PathVariable String channel,
			HttpServletRequest request, HttpServletResponse response,
			ReqMsg_Game_BonusList req, HashMap<String, Object> dataMap) {
		CookieManager manager = new CookieManager(request);
		String openId = manager.getValue(CookieEnums._SITE.getName(),
				CookieEnums._SITE_OPEN_ID.getName(), true);
		// 本地，所以指定了openid="onrI-swEgeScSSi9bsX1gp3deTJ0"
		/* if (openId == null || "".equals(openId)) { openId =
				  "onrI-s_i71rc_KKlLjBARGj5FkQM"; }*/
		// 根据openid来获取所有助力你的好友信息，包括自己
		req.setOpenId(openId);
		ResMsg_Game_BonusList resp = (ResMsg_Game_BonusList) gameService
				.handleMsg(req);
		dataMap.put("lists", resp.getList());
		Map<String, String> sign = weChatUtil.createAuth(request);
		dataMap.put("weichat", sign);
		return channel + "/games/shareus";
	}

	/**
	 * 游戏结果提交页面，会更新bonus记录和activity_user表成绩
	 * 
	 * @param channel
	 * @param req
	 * @param request
	 * @param response
	 * @param dataMap
	 * @return
	 */
	@RequestMapping("{channel}/games/gameResult")
	public @ResponseBody
	HashMap<String, Object> gameResult(@PathVariable String channel,
			ReqMsg_Game_Bonus req, HttpServletRequest request,
			HttpServletResponse response, HashMap<String, Object> dataMap) {

		ResMsg_Game_Bonus resp = (ResMsg_Game_Bonus) gameService.handleMsg(req);

		// dataMap.put("bsmsg", "");
		return dataMap;
	}
	
	/**
	 * 游戏主页面
	 * 
	 * @param channel
	 * @param request
	 * @param response
	 * @param req
	 * @param dataMap
	 * @return
	 */
	@RequestMapping("{channel}/games/gamess")
	public String gamesInit(@PathVariable String channel,
			HttpServletRequest request, HttpServletResponse response,
			ReqMsg_Game_Activity req, HashMap<String, Object> dataMap) {
		CookieManager manager = new CookieManager(request);
		String openId = manager.getValue(CookieEnums._SITE.getName(),
				CookieEnums._SITE_OPEN_ID.getName(), true);
		String wxUserId = manager.getValue(CookieEnums._SITE.getName(),
				CookieEnums._SITE_BS_ACTIVITY_USER.getName(), true);
		Integer activityUserId = req.getActivityUser();// 获取分享者的id
		// 本地，所以指定了openid="onrI-swEgeScSSi9bsX1gp3deTJ0"
	/*	if (openId == null || "".equals(openId)) { openId =
				  "onrI-s_i71rc_KKlLjBARGj5FkQM"; }*/
		// 获取分享者的id，如果为null的话，说明是为自己玩

		if (activityUserId == null) {
			// 判断是否是第一次玩
			ReqMsg_Game_Check req2 = new ReqMsg_Game_Check();
			req2.setOpenId(openId);
			ResMsg_Game_Check resp2 = (ResMsg_Game_Check) gameService
					.handleMsg(req2);
			if (resp2.getInfo() != null && resp2.getInfo().equals(1)) {
				ReqMsg_Game_BonusList req3 = new ReqMsg_Game_BonusList();
				req3.setOpenId(openId);
				ResMsg_Game_BonusList resp3 = (ResMsg_Game_BonusList) gameService
						.handleMsg(req3);
				dataMap.put("lists", resp3.getList());
				return channel + "/games/shareus";
			}
			req.setWxUserId(wxUserId);
			ResMsg_Game_Activity resp = (ResMsg_Game_Activity) gameService
					.handleMsg(req);

			dataMap.put("activityId", resp.getId());
			dataMap.put("activityName", resp.getName());
			dataMap.put("activityUserId", resp.getActivityUserId());
		} else {
			ReqMsg_Game_BonusCheck req6 = new ReqMsg_Game_BonusCheck();
			req6.setMobile(req.getMobile());
			req6.setActivityUserId(activityUserId.toString());
			req6.setOpenId(openId);
			ResMsg_Game_BonusCheck resp6 = (ResMsg_Game_BonusCheck) gameService
					.handleMsg(req6);
			//判断是否替分享者玩过，并且该用户是否已满10次。isHelped=true则跳转到介绍页面，终止游戏
			if (resp6.isHelped()) {
				ReqMsg_Game_BonusList req2 = new ReqMsg_Game_BonusList();
				req2.setOpenId(openId);
				ResMsg_Game_BonusList resp2 = (ResMsg_Game_BonusList) gameService
						.handleMsg(req2);
				dataMap.put("lists", resp2.getList());
				Map<String, String> sign = weChatUtil.createAuth(request);
				dataMap.put("weichat", sign);
				return channel + "/games/shareus";
			}
			
			ResMsg_Game_Activity resp = (ResMsg_Game_Activity) gameService
					.handleMsg(req);
			dataMap.put("activityId", resp.getId());
			dataMap.put("activityName", resp.getName());
			//分享者wxuserid
			dataMap.put("activityUserId", activityUserId);
			// 根据手机号获取用户活动编号
			ReqMsg_Game_ActivityUser req8 = new ReqMsg_Game_ActivityUser();
			req8.setOpenId(openId);
			ResMsg_Game_ActivityUser resp8 = (ResMsg_Game_ActivityUser) gameService
					.handleMsg(req8);
			//玩家自己wxuserid
			dataMap.put("helperWxUserId", resp8.getId());// !"".equals(activityUser)||activityUser!=null?activityUser:
		}
		Map<String, String> sign = weChatUtil.createAuth(request);
		dataMap.put("weichat", sign);
		return channel + "/games/games";
	}
}
