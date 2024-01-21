package com.pinting.site.common.interceptor;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.pinting.business.hessian.site.message.ReqMsg_WxUser_AddInfo;
import com.pinting.business.hessian.site.message.ResMsg_WxUser_AddInfo;
import com.pinting.core.cookie.CookieManager;
import com.pinting.core.util.GlobEnvUtil;
import com.pinting.core.util.StringUtil;
import com.pinting.site.common.enums.CookieEnums;
import com.pinting.site.communicate.CommunicateBusiness;

/**
 * 微信拦截器，用于网页授权
 */
public class WeixinInterceptor implements HandlerInterceptor {
    
    private Logger log = LoggerFactory.getLogger(WeixinInterceptor.class);
    @Autowired
	private CommunicateBusiness interceptorService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 0. 判断访问的域名是否是微网站的，如果不是，替换为微网站的,并做跳转
        // 1. 需要非静默授权的相关链接，需要用户手动确认以获取微信信息
        // 2. 判断是否来自菜单，如果来自菜单才需要拦截去获取openid，不是来自菜单的认为已经获取过openid，直接访问
        // 3. 来自菜单的前提下，判断cookie中的openid和parameter传过来的参数（微信回调获得的openid）

        log.info("{} 拦截到请求：{}", System.currentTimeMillis(), request.getRequestURL() + "?" + request.getQueryString());
        // 0. 判断访问的域名是否是微网站的，如果不是，替换为微网站的,并做跳转
        String webUrl = GlobEnvUtil.getWebURL("");
        try {
            String domain = webUrl.substring(webUrl.indexOf("//") + 2);
            //当前访问地址
            URL url = new URL(request.getRequestURL().toString());

            // 0. 判断访问的域名是否是微网站的，如果不是，替换为微网站的,并做跳转
            if (!url.toString().contains(domain)) {
                String str = url.toString();
                str = str.replace(url.getHost(), new URL(webUrl).getHost());
                str = str.replace(url.getProtocol(), new URL(webUrl).getProtocol());
                log.info("{} 跳转到url：{}", System.currentTimeMillis(), str);
                response.sendRedirect(str);
                return false;
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        // 1. 需要非静默授权的相关链接，需要用户手动确认以获取微信信息
        Boolean flag = notSilentAuthorization(request, response);
        if(null != flag) {
            return flag;
        }
        
        // 2. 判断是否来自菜单，如果来自菜单才需要拦截去获取openid，不是来自菜单的认为已经获取过openid，直接访问
        String menu = request.getParameter("menu");
        if (!"yes".equals(menu)) {//不是由菜单进来的，直接过
            return true;
        }
        
        // 3. 来自菜单的前提下，判断cookie中的openid和parameter传过来的参数（微信回调获得的openid）
        CookieManager manager = new CookieManager(request);
        String userId = manager.getValue(CookieEnums._SITE.getName(),
				CookieEnums._SITE_USER_ID.getName(), true);
        String openId = manager.getValue(CookieEnums._SITE.getName(),
				CookieEnums._SITE_OPEN_ID.getName(), true); //获取openId
        
        String openidPara = request.getParameter("i");
        String burl = request.getRequestURL().toString();//当前访问的链接
        burl = burl.replace(new URL(burl).getProtocol(), new URL(webUrl).getProtocol());//替换为配置里面的协议
        if (StringUtil.isNotBlank(openId) && openId.equals(openidPara)) {//一致，通过
        	if(StringUtil.isNotBlank(userId)){
        		ReqMsg_WxUser_AddInfo req = new ReqMsg_WxUser_AddInfo();
        		req.setUserId(userId);
        		req.setOpenId(openId);
        		ResMsg_WxUser_AddInfo res = (ResMsg_WxUser_AddInfo) interceptorService.handleMsg(req);
        		return true;
        	} 	
        }else{
        	request.getRequestDispatcher("/weChat/getCode" + "?burl=" + burl).forward(request,
                    response);
        	return false;
        }
        
		return true;
    }

    private Boolean notSilentAuthorization(HttpServletRequest request, HttpServletResponse response) {
        // 1. 2016年世界杯活动
        // 2. 2017年周年庆活动
        try {
            URL url = new URL(request.getRequestURL().toString());
            CookieManager manager = new CookieManager(request);
            String openId = manager.getValue(CookieEnums._SITE.getName(), CookieEnums._SITE_OPEN_ID.getName(), true);
            String openidPara = request.getParameter("i");

            if(url.toString().contains("/micro2.0/activity/ecup/quizResults")) {
                // 1. 2016年世界杯活动
                if (StringUtil.isNotBlank(openId) && openId.equals(openidPara)) {
                    // 一致，通过
                    return true;
                } else {
                    String userId = request.getParameter("userId");
                    String burl = url.toString() + "?userId=" + userId;
                    request.getRequestDispatcher("/weChat/getCodeNonSilentLicense" + "?burl="+burl).forward(request, response);
                    return false;
                }
            } else if(url.toString().contains("/micro2.0/activity/anniversary_share")) {
                if (StringUtil.isNotBlank(openId) && openId.equals(openidPara)) {
                    log.info("2017年周年庆活动用户授权");
                    // 一致，通过
                    return true;
                } else {
                    String userId = request.getParameter("userId");
                    String burl = url.toString() + "?userId=" + userId;
                    request.getRequestDispatcher("/weChat/getCodeNonSilentLicense" + "?burl="+burl).forward(request, response);
                    return false;
                }
            } else {
                return null;
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response,
                           Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
                                Object handler, Exception ex) throws Exception {

    }

}
