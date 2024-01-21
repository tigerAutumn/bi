package com.pinting.manage.controller;





import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.pinting.core.cookie.CookieManager;
import com.pinting.core.util.StringUtil;
import com.pinting.manage.enums.CookieEnums;
import com.pinting.util.GlobEnv;

/**
 * URL拦截器
 * @Project: manage-java
 * @Title: URLInterceptor.java
 * @author Linkin
 * @date 2015-2-3 下午12:11:05
 * @Copyright: 2015 bigangwan.com Inc. All rights reserved.
 */
public class URLInterceptor extends HandlerInterceptorAdapter {
	
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		CookieManager cookie = new CookieManager(request);
		String _account = cookie.getValue(CookieEnums._MANAGE_PLAT_FORM.getName(), CookieEnums._MANAGE_PLAT_FORM_USER_ID.getName(), true);
		/* 当Cookie被清除时，则直接跳转到登录页面 */
		
		if (StringUtil.isEmpty(_account)) {
			response.sendRedirect(GlobEnv.getWebURL("/login/index.htm"));
			return false;
		}
		return true;
	}
}