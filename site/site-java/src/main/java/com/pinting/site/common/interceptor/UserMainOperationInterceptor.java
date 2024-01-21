package com.pinting.site.common.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.pinting.business.hessian.site.message.ReqMsg_UserMainOperation_UserMainOperationAdd;
import com.pinting.business.hessian.site.message.ResMsg_UserMainOperation_UserMainOperationAdd;
import com.pinting.core.cookie.CookieManager;
import com.pinting.core.util.StringUtil;
import com.pinting.site.common.enums.CookieEnums;
import com.pinting.site.common.utils.IpUtil;
import com.pinting.site.communicate.CommunicateBusiness;

/**
 * 用户进入关键业务页面（登录，购买，我的资产）时，登记用户和IP信息
 * 
 * @Project: site-java
 * @author yanwl
 * @Title: UserMainOperationInterceptor.java
 * @date 2016-3-8 下午2:13:43
 * @Copyright: 2016 bigangwan.com Inc. All rights reserved.
 */
public class UserMainOperationInterceptor extends HandlerInterceptorAdapter{
	@Autowired
	private CommunicateBusiness interceptorService;
	
	/**
	 * 请求后置拦截
	 */
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,  
            ModelAndView modelAndView) throws Exception {
		//获取用户userId
  		CookieManager manager = new CookieManager(request);
  		String userId = manager.getValue(CookieEnums._SITE.getName(), CookieEnums._SITE_USER_ID.getName(), true);
  		if(StringUtil.isNotEmpty(userId)) {
  			try {
  				//获取Ip地址
  	  			String ip = IpUtil.getClientIp(request);
  	  			//获取浏览器
  	  			String srcAgent = request.getHeader("user-agent");
  	  			//获取请求url
  	  			StringBuffer url = new StringBuffer(request.getRequestURL() );
  	  	  		if(StringUtil.isNotEmpty(request.getQueryString())) {
  	  	  			url.append("?"+request.getQueryString());
  	  	  		}
  	  	  		//获取该请求的上一个跳转url
  	  	  		String referer = request.getHeader("Referer");
  	  	  		
  	  	  		ReqMsg_UserMainOperation_UserMainOperationAdd req = new ReqMsg_UserMainOperation_UserMainOperationAdd();
  	  	  		req.setUserId(Integer.valueOf(userId));
  	  	  		req.setUrl(url.toString());
  	  	  		req.setReferer(referer);
  	  	  		req.setSrcIp(ip);
  	  	  		req.setSrcAgent(srcAgent);
  	  	  		ResMsg_UserMainOperation_UserMainOperationAdd res = (ResMsg_UserMainOperation_UserMainOperationAdd)interceptorService.handleMsg(req);
  			}catch(Exception e){
  				e.printStackTrace();
  			}
  		}
	}
}
