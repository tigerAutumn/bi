/**
 * Wenzi.com Inc.
 * Copyright (c) 2015-2025 All Rights Reserved.
 */
package com.pinting.site.common.interceptor;

import javax.naming.CompositeName;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.pinting.business.hessian.site.message.ReqMsg_Home_RedPacketAmount;
import com.pinting.business.hessian.site.message.ResMsg_Home_RedPacketAmount;
import com.pinting.site.communicate.CommunicateBusiness;
import com.pinting.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.pinting.core.cookie.CookieManager;
import com.pinting.core.util.StringUtil;
import com.pinting.site.common.enums.CookieEnums;

/**
 * PC头部登录状态的判断拦截
 * @author HuanXiong
 * @version $Id: UserLoginInterceptor.java, v 0.1 2016-4-5 上午10:15:32 HuanXiong Exp $
 */
public class HeadChangeByLoginStatusInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private CommunicateBusiness interceptorService;

    /** 
     * @see org.springframework.web.servlet.handler.HandlerInterceptorAdapter#preHandle(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, java.lang.Object)
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
                             Object handler) throws Exception {
        CookieManager manager = new CookieManager(request);
        String mobile = manager.getValue(CookieEnums._SITE.getName(), CookieEnums._SITE_MOBILE_NUM.getName(), true);
        if(!StringUtil.isBlank(mobile)) {
            request.setAttribute("user_mobile", StringUtil.left(mobile, 3) + "****" + StringUtil.right(mobile, 4));
            request.setAttribute("user_is_login", "yes");
        } else {
            request.setAttribute("user_is_login", "no");
        }

        String XRequested = request.getHeader("X-Requested-With");
        if("XMLHttpRequest".equals(XRequested)) {
            // ajax请求不处理
        } else {
            ReqMsg_Home_RedPacketAmount req = new ReqMsg_Home_RedPacketAmount();
            if(request.getServletPath().contains("gen178")) {
                req.setProductShowTerminal(Constants.PRODUCT_SHOW_TERMINAL_PC_178);
            } else {
                req.setProductShowTerminal(Constants.PRODUCT_SHOW_TERMINAL_PC);
            }
            ResMsg_Home_RedPacketAmount res = (ResMsg_Home_RedPacketAmount) interceptorService.handleMsg(req);
            request.setAttribute("totalRedPacketAmount", res.getTotalRedPacketSubtract());
        }

        return true;
    }
    
}
