package com.pinting.site.common.interceptor;

import com.pinting.business.hessian.site.message.ReqMsg_User_UserSessionConnection;
import com.pinting.business.hessian.site.message.ReqMsg_WxUser_UnbindOpenId;
import com.pinting.business.hessian.site.message.ResMsg_User_UserSessionConnection;
import com.pinting.core.cookie.CookieManager;
import com.pinting.core.util.ConstantUtil;
import com.pinting.core.util.StringUtil;
import com.pinting.site.common.enums.CookieEnums;
import com.pinting.site.common.session.SessionConstant;
import com.pinting.site.common.session.SessionService;
import com.pinting.site.common.utils.GlobEnv;
import com.pinting.site.common.utils.IpUtil;
import com.pinting.site.communicate.CommunicateBusiness;
import com.pinting.util.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by cyb on 2018/1/31.
 */
public class SessionInterceptor extends HandlerInterceptorAdapter {

    private Logger logger = LoggerFactory.getLogger(SessionInterceptor.class);
    @Autowired
    private CommunicateBusiness interceptorService;
    @Autowired
    private SessionService sessionService;

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (request.getHeader("x-requested-with") != null && request.getHeader("x-requested-with").equalsIgnoreCase("XMLHttpRequest")){
            //如果是ajax请求响应头会有x-requested-with
            // ajax请求不处理
            return true;
        }

        CookieManager cookieManager = new CookieManager(request);
        String userId = cookieManager.getValue(CookieEnums._SITE.getName(), CookieEnums._SITE_USER_ID.getName(), true);
        Map<String, Object> session = sessionService.getSession(request);
        if(StringUtil.isNotBlank(userId)) {
            try {
                Integer.parseInt(userId);
                if(null == session) {
                    session = sessionService.saveSession(request, response, Integer.parseInt(userId));
                }

                String sessionId = (String)session.get(SessionConstant.JSESSIONID);
                logger.info("sessionId: {} ; userId: {}; URL: {}", sessionId, userId, request.getServletPath());
                Integer userIdRedis = (Integer)session.get(SessionConstant.USER_SESSION_KEY);
                Integer userIdCookie = Integer.parseInt(userId);
                if(userIdCookie.equals(userIdRedis)) {
                    String ip = IpUtil.getClientIp(request);
                    ReqMsg_User_UserSessionConnection req = new ReqMsg_User_UserSessionConnection();
                    req.setUserId(Integer.parseInt(userId));
                    req.setSessionId(sessionId);
                    if(request.getServletPath().contains("gen178") || request.getServletPath().contains("gen2.0")) {
                        req.setTerminal(Constants.SESSION_TERMINAL_PC);
                    } else if(request.getServletPath().contains("micro2.0")) {
                        req.setTerminal(Constants.SESSION_TERMINAL_H5);
                    } else {
                        return true;
                    }
                    req.setIp(ip);
                    req.setLogout(Constants.IS_NO);
                    ResMsg_User_UserSessionConnection res = (ResMsg_User_UserSessionConnection) interceptorService.handleMsg(req);
                    if(Constants.USER_NEED_FORCED_LOGOUT_CODE.equals(res.getResCode())) {
                        resultProcessPage(request, response);
                    }
                }
            } catch (Exception e) {
                this.clearCookie(response, cookieManager);
                return true;
            }
        }
        return true;
    }

    /**
     * 结果处理
     * @param request
     * @param response
     * @throws Exception
     */
    private void resultProcessPage(HttpServletRequest request, HttpServletResponse response) throws Exception {
        if(request.getServletPath().contains("gen2.0")) {
            String rUrl = GlobEnv.get("gen.server.web")+"/gen2.0/user/login/out";
            response.sendRedirect(rUrl);
        } else if(request.getServletPath().contains("gen178")) {
            String rUrl = GlobEnv.get("gen.server.web")+"/gen178/user/login/out";
            response.sendRedirect(rUrl);
        } else if(request.getServletPath().contains("micro2.0") && Constants.USER_AGENT_QIANBAO.equals(request.getParameter("qianbao"))) {
            String rUrl = "/micro2.0/user/login/out?qianbao=qianbao";
            response.sendRedirect(rUrl);
        } else if(request.getServletPath().contains("micro2.0")) {
            String rUrl = "/micro2.0/user/login/out";
            response.sendRedirect(rUrl);
        }
    }

    /**
     * 清空cookie
     * @param response
     * @param manager
     */
    private void clearCookie(HttpServletResponse response, CookieManager manager) {
        manager.setValue(CookieEnums._SITE.getName(), CookieEnums._SITE_USER_ID.getName(), null, true);
        manager.setValue(CookieEnums._SITE.getName(), CookieEnums._SITE_USER_NAME.getName(), null, true);
        manager.setValue(CookieEnums._SITE.getName(), CookieEnums._SITE_USER_TYPE.getName(), null, true);
        manager.setValue(CookieEnums._SITE.getName(), CookieEnums._SITE_MOBILE_NUM.getName(), null, true);
        String rUrl = manager.getValue(CookieEnums._SITE_BIZ.getName(), CookieEnums._BIZ_REDIRECT_URL.getName(), true);
        if(StringUtil.isNotEmpty(rUrl)) {
            manager.setValue(CookieEnums._SITE_BIZ.getName(), CookieEnums._BIZ_REDIRECT_URL.getName(), "", true);
            manager.save(response, CookieEnums._SITE_BIZ.getName(), null, "/", -1, true);
        }
        manager.cleanAll(response, CookieEnums._SITE.getName(),  true);
    }
}
