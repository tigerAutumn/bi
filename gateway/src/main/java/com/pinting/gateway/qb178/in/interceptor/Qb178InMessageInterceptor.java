package com.pinting.gateway.qb178.in.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pinting.core.redis.JedisClientDaoSupport;
import com.pinting.gateway.qb178.in.util.Qb178InMsgUtil;
import com.pinting.gateway.util.Constants;

/**
 * 恒丰银行存管请求拦截
 * @project gateway
 * @title Qb178InMessageInterceptor.java
 * @author Dragon & cat
 * @date 2017-7-29
 * @Copyright 2015 BiGangWan.com Inc. All rights reserved.
 * @Description
 */
public class Qb178InMessageInterceptor {
	private Logger log = LoggerFactory
			.getLogger(Qb178InMessageInterceptor.class);
    private JedisClientDaoSupport jsClientDaoSupport = JedisClientDaoSupport.getInstance(Constants.REDIS_SUBSYS_GATEWAY);


	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		Qb178InMsgUtil.lastAccessTime = System.currentTimeMillis();//正常操作，设置最后时间
		
		
		return true;
	}
    
}
