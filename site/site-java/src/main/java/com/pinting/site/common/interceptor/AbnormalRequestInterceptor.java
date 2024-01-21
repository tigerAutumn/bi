package com.pinting.site.common.interceptor;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.pinting.core.cookie.CookieManager;
import com.pinting.site.common.enums.CookieEnums;
import com.pinting.site.common.interceptor.bean.AbnormalRequestAlarmInfo;
import com.pinting.site.common.utils.GlobEnv;
import com.pinting.site.common.utils.IpUtil;

/**
 * 异常请求处理： 对频繁访问IP进行拦截限制，并进行登记
 * 
 * @Project: site-java
 * @Title: AbnormalRequestInterceptor.java
 * @author dingpf
 * @date 2015-2-2 下午3:21:00
 * @Copyright: 2015 BiGangWan.com Inc. All rights reserved.
 */
public class AbnormalRequestInterceptor extends HandlerInterceptorAdapter {
	private Logger log = LoggerFactory
			.getLogger(AbnormalRequestInterceptor.class);
	// 访问ip名单{ip:访问次数}
	public static HashMap<String, Integer> hostMap = new HashMap<String, Integer>();
	// 待告警ip名单{ip:告警信息}（用于发送告警信息给管理员手机号，同一ip一天只发一次）
	public static HashMap<String, AbnormalRequestAlarmInfo> alarmHostMap = new HashMap<String, AbnormalRequestAlarmInfo>();
	// 已告警ip名单（当天）
	public static List<String> alarmedHostList = new ArrayList<String>();

	/**
	 * 请求前置拦截
	 */
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {

		String clientHost = IpUtil.getClientIp(request);
		// 过滤放行的IP
		String ips = GlobEnv.get("ip.limit.exclude.ips");
		String[] excludeIps = ips.split(",");
		for (String ip : excludeIps) {
			if (ip.equals(clientHost)) {
				return true;
			}
		}
		// 时间段允许最大访问次数
		String maxAccessNum = GlobEnv.get("ip.limit.allows.accessNum");
		// 已访问次数
		Integer accessNum = hostMap.get(clientHost);
		// 判断该ip是否在名单中，没有则添加到名单，否则需进行超限判断，访问次数累加
		if (accessNum != null) {
			hostMap.put(clientHost, ++accessNum);
			// 判断该ip是否超限，超过则直接禁止访问，并记录到待告警ip名单
			if (accessNum > Integer.valueOf(maxAccessNum)) {
				log.error("================警告：IP【" + clientHost
						+ "】访问过于频繁！！！================");
				AbnormalRequestAlarmInfo alarm = alarmHostMap
						.get(clientHost);
				if (alarm == null) {
					alarm = new AbnormalRequestAlarmInfo();
					alarm.setIp(clientHost);
					alarm.setOpType(request.getServletPath());
					alarm.setTerminal(request.getHeader("user-agent"));
					alarm.setTime(new Date());
					alarm.setUserId(getCookieByKey(request, CookieEnums._SITE_USER_ID));
					alarm.setUserName(getCookieByKey(request, CookieEnums._SITE_USER_NAME));
					StringBuffer message = new StringBuffer("警告：IP【").append(clientHost)
							.append("】访问过于频繁！请务必警惕！");
					alarm.setInfo(message.toString());
					alarmHostMap.put(clientHost, alarm);
				}
				return false;
			}
		} else {// 添加到访问名单
			hostMap.put(clientHost, 1);
		}

		return true;
	}

	/**
	 * 根据key获取cookie值
	 * 
	 * @param request
	 * @param key
	 * @return
	 */
	private String getCookieByKey(HttpServletRequest request, CookieEnums key) {
		CookieManager manager = new CookieManager(request);
		String value = manager.getValue(CookieEnums._SITE.getName(),
				key.getName(), true);
		return value;
	}
}
