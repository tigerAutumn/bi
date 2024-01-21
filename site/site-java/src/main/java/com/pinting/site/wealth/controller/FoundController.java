package com.pinting.site.wealth.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pinting.business.hessian.site.message.ReqMsg_User_Feedback;
import com.pinting.business.hessian.site.message.ResMsg_User_Feedback;
import com.pinting.core.base.BaseController;
import com.pinting.core.cookie.CookieManager;
import com.pinting.core.util.ConstantUtil;
import com.pinting.site.weichat.util.WeChatUtil;
import com.pinting.site.common.enums.CookieEnums;
import com.pinting.site.common.utils.GlobEnv;
import com.pinting.site.communicate.CommunicateBusiness;
import com.pinting.util.MatrixToImageWriter;

@Controller
public class FoundController extends BaseController {
	@Autowired
	private CommunicateBusiness siteService;
	@Autowired
	private WeChatUtil weChatUtil;
	
	@RequestMapping("{channel}/wealth/help_index")
	public String helpInit(@PathVariable String channel, HttpServletRequest request,
			HttpServletResponse response) {
		
		return channel + "/wealth/help_index";
	}
	
	@RequestMapping("{channel}/wealth/about")
	public String wealthAbountInit(@PathVariable String channel, HttpServletRequest request,
			HttpServletResponse response) {
		return channel + "/wealth/about";
	}
	
	@RequestMapping("{channel}/wealth/feedback")
	public String wealthFeedbackInit(@PathVariable String channel, HttpServletRequest request,
			HttpServletResponse response) {
		return channel + "/wealth/feedback";
	}
	@RequestMapping("{channel}/wealth/address")
	public String wealthAddressInit(@PathVariable String channel, HttpServletRequest request,
			HttpServletResponse response) {
		return channel + "/wealth/address";
	}
	
	/**
	 * 意见反馈
	 * 
	 * @param channel
	 * @param reqMsg
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("{channel}/wealth/found_feedback")
	public @ResponseBody HashMap<String, Object> wealthFeedback(@PathVariable String channel,ReqMsg_User_Feedback reqMsg, 
			HttpServletRequest request, HttpServletResponse response) {
		HashMap<String, Object> dataMap = new HashMap<String, Object>();
		try {
			CookieManager manager = new CookieManager(request);
			String userId = manager.getValue(CookieEnums._SITE.getName(),
					CookieEnums._SITE_USER_ID.getName(), true);
			reqMsg.setUserId(Integer.valueOf(userId));
			ResMsg_User_Feedback resMsg = (ResMsg_User_Feedback) siteService.handleMsg(reqMsg);
			if(ConstantUtil.BSRESCODE_SUCCESS.equals(resMsg.getResCode())){
				successRes(dataMap, resMsg);
			}else{
				errorRes(dataMap, resMsg);
			}
		} catch (Exception e) {
			e.printStackTrace();
			errorRes(dataMap, e);
		}
		
		return dataMap;
	}
	
	@RequestMapping("{channel}/wealth/share")
	public String shareInit(@PathVariable String channel, HttpServletRequest request,
			HttpServletResponse response,Map<Object,Object> model) {
		
		
		String url = request.getRequestURL().toString();
		if(request.getQueryString() != null && !request.getQueryString().equals("")){
			url += "?" + request.getQueryString();
		}
		
		CookieManager cookieManager = new CookieManager(request);
		String userId = cookieManager.getValue(CookieEnums._SITE.getName(),CookieEnums._SITE_USER_ID.getName(), true);
		model.put("ivCode", com.pinting.util.Util.generateInvitationCode(Integer.parseInt(userId)));
		String user = userId + UUID.randomUUID().toString(); 
		
		Map<String,String> sign = weChatUtil.createAuth(request);
		
		String link = GlobEnv.getWebURL("/micro/index?user=" + user);
		sign.put("link", link);
		
		MatrixToImageWriter.createMatrixImage(link, userId,null,false);
		String matrix = GlobEnv.get("wxQRcode.url.prefix") ;
		
		if(matrix.charAt(matrix.length() - 1) != '/'){
			matrix = matrix + "/";
		}
		
		matrix += userId + ".png";
		
		model.put("matrix", matrix);
		model.put("weichat", sign);
		return channel + "/wealth/share";
	}
	
	@RequestMapping("{channel}/wealth/found")
	public String foundInit(@PathVariable String channel, HttpServletRequest request,
			HttpServletResponse response) {
		
		return channel + "/wealth/found";
	}
}
