package com.pinting.gateway.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 *  
 * @project gateway
 * @title RouteInterceptor.java
 * @author Dragon & cat
 * @date 2017-9-1
 * @Copyright 2015 BiGangWan.com Inc. All rights reserved.
 * @Description
 */
public class DepositoryInterceptor  extends HandlerInterceptorAdapter {
	 private Logger log = LoggerFactory.getLogger(DepositoryInterceptor.class);
	 
	    @Override
	    public boolean preHandle(HttpServletRequest request,
	                             HttpServletResponse response, Object handler) throws Exception {            // body中前台传入的参数  
	    	String requestPathInfo = request.getPathInfo();
	    	log.info("拦截器拦截到请求："+requestPathInfo);
            return false;  
        }
	    
}
