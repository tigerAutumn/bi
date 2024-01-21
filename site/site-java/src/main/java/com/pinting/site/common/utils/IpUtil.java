package com.pinting.site.common.utils;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pinting.site.common.interceptor.URLInterceptor;

/**
 * 获取用户请求IP地址
 * 
 * @Project: site-java
 * @author yanwl
 * @Title: IpUtil.java
 * @date 2016-3-8 下午2:21:29
 * @Copyright: 2016 bigangwan.com Inc. All rights reserved.
 */
public class IpUtil {
	private static Logger log = LoggerFactory.getLogger(IpUtil.class);
	
	/**
	 * 获取真实IP（适用nginx+应用服务器）
	 * 
	 * @param request
	 * @return
	 */
	public static  String getClientIp(HttpServletRequest request) {
		String address = request.getHeader("X-Forwarded-For");
		if (address != null && address.length() > 0
				&& !"unknown".equalsIgnoreCase(address)) {
			if(address.contains(",")) {
				log.info("X-Forwarded-For:"+address);
			}
			return address;
		}
		address = request.getHeader("Proxy-Client-IP");
		if (address != null && address.length() > 0
				&& !"unknown".equalsIgnoreCase(address)) {
			if(address.contains(",")) {
				log.info("Proxy-Client-IP:"+address);
			}
			return address;
		}
		address = request.getHeader("WL-Proxy-Client-IP");
		if (address != null && address.length() > 0
				&& !"unknown".equalsIgnoreCase(address)) {
			if(address.contains(",")) {
				log.info("WL-Proxy-Client-IP:"+address);
			}
			return address;
		}
		if(request.getRemoteAddr().contains(",")) {
			log.info("RemoteAddr:"+request.getRemoteAddr());
		}
		return request.getRemoteAddr();
	}
}
