package com.pinting.site.common.interceptor;

import com.pinting.core.redis.JedisClientDaoSupport;
import com.pinting.util.Constants;
import com.pinting.util.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Author:      cyb
 * Date:        2017/6/21
 * Description:
 */
public class MobileTokenInterceptor extends HandlerInterceptorAdapter {

    private static Logger log = LoggerFactory.getLogger(MobileTokenInterceptor.class);
    private static JedisClientDaoSupport jsClientDaoSupport = JedisClientDaoSupport.getInstance(Constants.REDIS_SUBSYS_SITE);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (handler instanceof HandlerMethod) {
            Util.createMobileToken(request, response);
            return true;
        } else {
            return super.preHandle(request, response, handler);
        }
    }
}
