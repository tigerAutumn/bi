package com.pinting.site.common.interceptor;





import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.pinting.site.common.utils.GlobEnv;
import com.pinting.util.Constants;


/**
 * 购买交易拦截器
 * @Project: manage-java
 * @Title: StatusInterceptor.java
 * @author Linkin
 * @date 2015-3-9 下午12:11:05
 * @Copyright: 2015 bigangwan.com Inc. All rights reserved.
 */
public class StatusInterceptor extends HandlerInterceptorAdapter {
	
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		if(Constants.tranValue != null){
			if (Constants.tranValue.equals(Constants.TRAN_VALUE_HALT)) {
				response.sendRedirect(GlobEnv.getWebURL("/protect.htm"));
				return false;
			}
		}
		return true;
	}
}