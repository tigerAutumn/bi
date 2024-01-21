package com.pinting.site.common.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.pinting.core.cookie.CookieManager;
import com.pinting.core.util.StringUtil;
import com.pinting.site.common.enums.CookieEnums;
import com.pinting.site.common.utils.GlobEnv;

/**
 * GrowingIO 页面监控拦截器
 * @author Dragon & cat
 */
public class GrowingIOInterceptor extends HandlerInterceptorAdapter {
	
	
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
                             Object handler) throws Exception {
    	String growing_project_id = GlobEnv.get("growing_project_id");
    	request.setAttribute("growing_project_id", growing_project_id);
    	CookieManager manager = new CookieManager(request);
        String mobile = manager.getValue(CookieEnums._SITE.getName(), CookieEnums._SITE_MOBILE_NUM.getName(), true);
        String user_id = manager.getValue(CookieEnums._SITE.getName(), CookieEnums._SITE_USER_ID.getName(), true);
        String agent_id = manager.getValue(CookieEnums._SITE.getName(), CookieEnums._SITE_REAL_AGENT_ID.getName(), true);
        if(!StringUtil.isBlank(mobile)) {
            request.setAttribute("growing_user_id", user_id);
            request.setAttribute("growing_agent_id", agent_id);
            request.setAttribute("growing_user_is_login", "yes");
        } else {
            request.setAttribute("growing_user_is_login", "no");
        }
        return true;
    }
	

}
