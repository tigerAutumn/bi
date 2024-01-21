package com.pinting.site.user.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.pinting.business.hessian.site.message.ReqMsg_User_UserSessionConnection;
import com.pinting.business.hessian.site.message.ResMsg_User_UserSessionConnection;
import com.pinting.site.common.session.SessionConstant;
import com.pinting.site.common.session.SessionService;
import com.pinting.site.common.utils.IpUtil;
import com.pinting.util.BannerUtil;
import com.pinting.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.pinting.business.hessian.site.message.ReqMsg_WxUser_UnbindOpenId;
import com.pinting.core.base.BaseController;
import com.pinting.core.cookie.CookieManager;
import com.pinting.core.util.StringUtil;
import com.pinting.site.weichat.util.WeChatUtil;
import com.pinting.site.common.enums.CookieEnums;
import com.pinting.site.common.utils.GlobEnv;
import com.pinting.site.communicate.CommunicateBusiness;

@Controller
@Scope("prototype")
public class UserLoginOutController extends BaseController {
    
    @Autowired
    private CommunicateBusiness siteService;
	@Autowired
	private WeChatUtil weChatUtil;
	@Autowired
	private SessionService sessionService;

	/**
	 * 用户退出
	 * 
	 * @param channel
	 * @param request
	 * @param response
	 * @return
	 */
	String url = "";
	@RequestMapping("{channel}/user/login/out")
	public String userLoginInit(@PathVariable String channel, HttpServletRequest request,
			HttpServletResponse response, Map<String, Object> dataMap) {

		List<HashMap<String, Object>> bannerList = BannerUtil.queryBannerList(siteService, channel, Constants.SHOW_PAGE_LOGIN);
		if(!org.springframework.util.CollectionUtils.isEmpty(bannerList)) {
			dataMap.put("banner", bannerList.get(0));
		}

		CookieManager manager = new CookieManager(request);
		
		// H5退出解绑openId
		if("micro2.0".equals(channel)) {
		    String userId = manager.getValue(CookieEnums._SITE.getName(), CookieEnums._SITE_USER_ID.getName(), true);
	        if(StringUtil.isNotBlank(userId)) {
	            ReqMsg_WxUser_UnbindOpenId req = new ReqMsg_WxUser_UnbindOpenId();
	            req.setUserId(Integer.parseInt(userId));
	            siteService.handleMsg(req);
	        }
		}

//		用户多端登录限制代码
//		String userId = manager.getValue(CookieEnums._SITE.getName(), CookieEnums._SITE_USER_ID.getName(), true);
//		if(StringUtil.isNotBlank(userId)) {
//			Map<String, Object> session = sessionService.getSession(request);
//			if(null != session) {
//				String sessionId = (String) session.get(SessionConstant.JSESSIONID);
//				String ip = IpUtil.getClientIp(request);
//				ReqMsg_User_UserSessionConnection req = new ReqMsg_User_UserSessionConnection();
//				req.setUserId(Integer.parseInt(userId));
//				req.setSessionId(sessionId);
//				req.setIp(ip);
//				req.setLogout(Constants.IS_YES);
//				if(channel.equals("gen178") || channel.equals("gen2.0")) {
//					req.setTerminal(Constants.SESSION_TERMINAL_PC);
//				} else if(channel.equals("micro2.0")) {
//					req.setTerminal(Constants.SESSION_TERMINAL_H5);
//				} else {
//					req.setTerminal(Constants.SESSION_TERMINAL_H5);
//				}
//				siteService.handleMsg(req);
//				sessionService.removeSession(request, response);
//			}
//		}

		// 清除cookie
		manager.setValue(CookieEnums._SITE.getName(),
            CookieEnums._SITE_USER_ID.getName(),
            null, true);
        manager.setValue(CookieEnums._SITE.getName(),
                CookieEnums._SITE_USER_NAME.getName(),
                null, true);
        manager.setValue(CookieEnums._SITE.getName(),
                CookieEnums._SITE_USER_TYPE.getName(),
                null, true);
        manager.setValue(CookieEnums._SITE.getName(),
            CookieEnums._SITE_MOBILE_NUM.getName(),
            null, true);
        
        request.setAttribute("user_is_login", "no");
        
        //String rUrl = (String)request.getSession().getAttribute("redirectUrl");
        String rUrl = manager.getValue(CookieEnums._SITE_BIZ.getName(), CookieEnums._BIZ_REDIRECT_URL.getName(), true);
        if(StringUtil.isNotEmpty(rUrl)) {
            //request.getSession().removeAttribute("redirectUrl");
        	manager.setValue(CookieEnums._SITE_BIZ.getName(), CookieEnums._BIZ_REDIRECT_URL.getName(), "", true);
        	manager.save(response, CookieEnums._SITE_BIZ.getName(), null, "/", -1, true);
        }
        
        String link = GlobEnv.getWebURL("/micro2.0/index");
		// 钱报
		String qianbao = request.getParameter("qianbao");
		if(StringUtil.isNotBlank(qianbao) && "qianbao".equals(qianbao) && "micro2.0".equals(channel)) {
	        manager.cleanAll(response, CookieEnums._SITE.getName(),  true);
	        manager.cleanAll(response, CookieEnums._USER.getName(),  true);
	        dataMap.put("qianbao", "qianbao");
	        link += "?qianbao=qianbao";
            String viewAgentFlagCookie = manager.getValue(CookieEnums._VIEW.getName(), CookieEnums._VIEW_AGENT_FLAG.getName(), true);
	        if(StringUtil.isNotBlank(viewAgentFlagCookie)){
	        	link += "&agentViewFlag="+viewAgentFlagCookie;
	        }
	        // 分享
	        Map<String,String> sign = weChatUtil.createAuth(request);
	        sign.put("link", link);
	        dataMap.put("weichat", sign);
	        return "qianbao178/user/login_index";
		}
		
        // 分享
        Map<String,String> sign = weChatUtil.createAuth(request);
        sign.put("link", link);
        dataMap.put("weichat", sign);
		
        manager.cleanAll(response, CookieEnums._SITE.getName(),  true);
        manager.cleanAll(response, CookieEnums._USER.getName(),  true);
		return channel + "/user/login_index";
	}


}
