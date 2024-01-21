package com.pinting.site.wealth.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.pinting.business.hessian.site.message.ReqMsg_User_AssetInfoQuery;
import com.pinting.business.hessian.site.message.ReqMsg_User_BsSubUserListQuery;
import com.pinting.business.hessian.site.message.ResMsg_User_AssetInfoQuery;
import com.pinting.business.hessian.site.message.ResMsg_User_BsSubUserListQuery;
import com.pinting.core.base.BaseController;
import com.pinting.core.cookie.CookieManager;
import com.pinting.site.weichat.util.WeChatUtil;
import com.pinting.site.common.enums.CookieEnums;
import com.pinting.site.common.utils.GlobEnv;
import com.pinting.site.communicate.CommunicateBusiness;
import com.pinting.util.Constants;

@Controller
@Scope("prototype")
public class MyWealthController extends BaseController {
	@Autowired
	private CommunicateBusiness siteService;
	@Autowired
	private WeChatUtil weChatUtil;
	
	@RequestMapping("{channel}/wealth/index")
	public String wealthInit(@PathVariable String channel, ReqMsg_User_AssetInfoQuery reqMsg, 
			Map<String, Object> dataMap, HttpServletRequest request, HttpServletResponse response) {
		CookieManager manager = new CookieManager(request);
		String userId = manager.getValue(CookieEnums._SITE.getName(), 
				CookieEnums._SITE_USER_ID.getName(), true);
		String nick = manager.getValue(CookieEnums._SITE.getName(), 
				CookieEnums._SITE_USER_NAME.getName(), true);
		String userType = manager.getValue(CookieEnums._SITE.getName(), 
				CookieEnums._SITE_USER_TYPE.getName(), true);
		//由于会有拦截器进行userId是否为空的判断，这里就不再重复
		reqMsg.setUserId(Integer.valueOf(userId));
		reqMsg.setNick(nick);
		ResMsg_User_AssetInfoQuery resMsg = (ResMsg_User_AssetInfoQuery) siteService.handleMsg(reqMsg);
		dataMap.put("resMsg", resMsg);
		dataMap.put("userType", userType);
		Map<String,String> sign = weChatUtil.createAuth(request);
		String url = GlobEnv.getWebURL("/micro/index");
		String link = "";
		
		if(url.contains("?")){
			link = url + "&user=" + userId + UUID.randomUUID().toString();
		}else{
			link = url + "?user=" + userId + UUID.randomUUID().toString();
		}
		sign.put("link", link);
		dataMap.put("weichat", sign);
		return channel + "/wealth/wealth_index";
	}
	@RequestMapping("{channel}/wealth/profit")
	public String myProfit(@PathVariable String channel, HttpServletRequest request,
			HttpServletResponse response) {
		
		return channel + "/wealth/profit";
	}
	
	@RequestMapping("{channel}/wealth/friend")
	public String myFriend(@PathVariable String channel, HttpServletRequest request,
			HttpServletResponse response,ReqMsg_User_BsSubUserListQuery req,HashMap<String,Object> model) {
		
		try {
			CookieManager manager = new CookieManager(request);
			String userId = manager.getValue(CookieEnums._SITE.getName(), CookieEnums._SITE_USER_ID.getName(), true);
			req.setUserId(Integer.valueOf(userId));
			req.setPageIndex(0);
			req.setPageSize(Constants.EXCHANGE_PAGE_SIZE);
			ResMsg_User_BsSubUserListQuery resp = (ResMsg_User_BsSubUserListQuery) siteService
					.handleMsg(req);
			model.put("pageIndex", 0);
			model.put("totalCount", resp.getTotalCount());
			model.put("bsUserList", resp.getBsUserList());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return channel + "/wealth/friend_detail";
	}
	@RequestMapping("{channel}/wealth/friend_detail_content")
	public String myFriendDetailContent(@PathVariable String channel,Integer pageIndex, HttpServletRequest request,
			HttpServletResponse response,ReqMsg_User_BsSubUserListQuery req,HashMap<String,Object> model) {
		
		CookieManager manager = new CookieManager(request);
		String userId = manager.getValue(CookieEnums._SITE.getName(), CookieEnums._SITE_USER_ID.getName(), true);
		req.setUserId(Integer.valueOf(userId));
		req.setPageIndex(pageIndex);
		req.setPageSize(Constants.EXCHANGE_PAGE_SIZE);
		ResMsg_User_BsSubUserListQuery resp = (ResMsg_User_BsSubUserListQuery) siteService
				.handleMsg(req);
		model.put("pageIndex", resp.getPageIndex());
		model.put("totalCount", resp.getTotalCount());
		model.put("bsUserList", resp.getBsUserList());
		
		return channel + "/more/friend_detail_content";
	}
}
