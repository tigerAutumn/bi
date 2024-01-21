package com.pinting.site.common.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.pinting.site.common.utils.GlobEnv;

public class MicroInterceptor extends HandlerInterceptorAdapter {

	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		if(request.getServletPath().contains("/gen/")) {
			response.sendRedirect(GlobEnv.get("gen.server.web")+"/gen2.0/index");
			return false;
		}
		else if(request.getServletPath().contains("/micro/")) {
			response.sendRedirect(GlobEnv.getWebURL("/micro2.0/index"));
			return false;
		}
		else {
			return true;
		}
	}
}
