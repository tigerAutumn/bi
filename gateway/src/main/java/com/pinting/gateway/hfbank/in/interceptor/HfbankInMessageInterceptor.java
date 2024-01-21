package com.pinting.gateway.hfbank.in.interceptor;

import java.io.PrintWriter;
import java.util.Date;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.pinting.gateway.hfbank.in.util.HfbankInMsgUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.pinting.core.redis.JedisClientDaoSupport;
import com.pinting.core.util.StringUtil;
import com.pinting.core.util.encrypt.MD5Util;
import com.pinting.gateway.dafy.in.model.BaseReqModel;
import com.pinting.gateway.dafy.in.model.BaseResModel;
import com.pinting.gateway.dafy.in.util.DafyInConstant;
import com.pinting.gateway.dafy.in.util.DafyInMsgUtil;
import com.pinting.gateway.util.Constants;



/**
 * 恒丰银行存管请求拦截，并解析报文
 * @Project: gateway
 * @Title: HfbankInMessageInterceptor.java
 * @author dragon & cat
 * @date 2016-12-20 
 * @Copyright: 2015 BiGangWan.com Inc. All rights reserved.
 */
public class HfbankInMessageInterceptor extends HandlerInterceptorAdapter{
	private Logger log = LoggerFactory
			.getLogger(HfbankInMessageInterceptor.class);
    private JedisClientDaoSupport jsClientDaoSupport = JedisClientDaoSupport.getInstance(Constants.REDIS_SUBSYS_GATEWAY);


	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {


		HfbankInMsgUtil.lastAccessTime = System.currentTimeMillis();//正常操作，设置最后时间
		return true;
	}
}
