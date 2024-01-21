package com.pinting.site.common.interceptor;





import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.pinting.business.hessian.site.message.ReqMsg_WxUser_isExist;
import com.pinting.business.hessian.site.message.ResMsg_WxUser_isExist;
import com.pinting.core.cookie.CookieManager;
import com.pinting.core.util.StringUtil;
import com.pinting.site.common.enums.CookieEnums;
import com.pinting.site.common.utils.GlobEnv;
import com.pinting.site.communicate.CommunicateBusiness;

/**
 * 微信授权拦截器
 * @Project: site-java
 * @Title: WeiChatInterceptor.java
 * @author Huang MengJian
 * @date 2015-4-2 下午7:45:40
 * @Copyright: 2015 BiGangWan.com Inc. All rights reserved.
 */
public class WeiChatInterceptor extends HandlerInterceptorAdapter {
	
	
	private Logger log = LoggerFactory.getLogger(WeiChatInterceptor.class);
	@Autowired
	private CommunicateBusiness interceptorService;
	
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		
		if(GlobEnv.get("open.weichat.auth").equals("false")){ //关闭
			return true;
		}
		
		String status = request.getParameter("openId");
		if(status != null && !"".equals(status)){ //当status 有值，代表着是从微信controller端跳转到游戏页面，此时已经拿到openId
			return true;
		}
		
		CookieManager cookie = new CookieManager(request);
		CookieManager manager = new CookieManager(request);
		String openId = manager.getValue(CookieEnums._SITE.getName(), 
				CookieEnums._SITE_OPEN_ID.getName(), true);
		
		String state = "micro/games/games";
		if (StringUtil.isEmpty(openId)) {
			request.getRequestDispatcher("/weiChat/getCode?state=" + state).forward(request, response);
			return false;
		}else	
		{
			ReqMsg_WxUser_isExist req = new ReqMsg_WxUser_isExist();
			req.setOpenId(openId);
			ResMsg_WxUser_isExist resp = (ResMsg_WxUser_isExist) interceptorService.handleMsg(req);
			if(resp.getWxUserId()==0)
			{
				request.getRequestDispatcher("/weiChat/getCode?state=" + state).forward(request, response);
				return false;
			}
		}
		return true;
	}
}