/**
 * Wenzi.com Inc.
 * Copyright (c) 2015-2025 All Rights Reserved.
 */
package com.pinting.site.user.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.pinting.core.cookie.CookieManager;
import com.pinting.core.util.StringUtil;
import com.pinting.site.common.enums.CookieEnums;
import com.pinting.site.common.utils.GlobEnv;
import com.pinting.site.weichat.util.WeChatUtil;
import com.pinting.util.Constants;

/**
 * 
 * @author HuanXiong
 * @version $Id: UserForgetPsdController.java, v 0.1 2015-11-12 下午12:03:43 HuanXiong Exp $
 */
@Controller
public class UserForgetPsdController {
    
    @Autowired
    private WeChatUtil weChatUtil;

    /**
     * 忘记密码 1
     * 
     * @param channel
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping("{channel}/user/forget_password_first")
    public String forgetPasswordFirst(@PathVariable String channel, HttpServletRequest request,
                                      HttpServletResponse response, Map<String, Object> model) {
        String link = GlobEnv.getWebURL("/micro2.0/index");
        // 钱报的参数
        String qianbao = request.getParameter("qianbao");
        if(StringUtil.isNotBlank(qianbao) && Constants.USER_AGENT_QIANBAO.equals(qianbao) && "micro2.0".equals(channel)) {
            model.put("qianbao", Constants.USER_AGENT_QIANBAO);
            link += "?qianbao=qianbao";
            CookieManager manager = new CookieManager(request);
            String viewAgentFlagCookie = manager.getValue(CookieEnums._VIEW.getName(), CookieEnums._VIEW_AGENT_FLAG.getName(), true);
	        if(StringUtil.isNotBlank(viewAgentFlagCookie)){
	        	link += "&agentViewFlag="+viewAgentFlagCookie;
	        }
        }
        
        Map<String,String> sign = weChatUtil.createAuth(request);
        sign.put("link", link);
        model.put("weichat", sign);
        
        return channel + "/user/forget_password_first";
    }

    /**
     * 微信小程序忘记密码 1
     * 
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping("weixin/user/forget_password_first")
    public String weChatForgetPasswordFirst(HttpServletRequest request,
                                      HttpServletResponse response, Map<String, Object> model) {
        return "weixin/user/forget_password_first";
    }
    
    /**
     * 忘记密码 2
     * 
     * @param channel
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping("{channel}/user/forget_password_second")
    public String forgetPasswordSecond(@PathVariable String channel, HttpServletRequest request,
                                       HttpServletResponse response, Map<String, Object> model) {
        model.put("verCode", request.getParameter("verCode"));
        model.put("mobile", request.getParameter("mobile"));

        // 钱报的参数
        String link = GlobEnv.getWebURL("/micro2.0/index");
        // 钱报的参数
        String qianbao = request.getParameter("qianbao");
        if(StringUtil.isNotBlank(qianbao) && Constants.USER_AGENT_QIANBAO.equals(qianbao) && "micro2.0".equals(channel)) {
            model.put("qianbao", Constants.USER_AGENT_QIANBAO);
            link += "?qianbao=qianbao";
            CookieManager manager = new CookieManager(request);
            String viewAgentFlagCookie = manager.getValue(CookieEnums._VIEW.getName(), CookieEnums._VIEW_AGENT_FLAG.getName(), true);
	        if(StringUtil.isNotBlank(viewAgentFlagCookie)){
	        	link += "&agentViewFlag="+viewAgentFlagCookie;
	        }
        }
        
        Map<String,String> sign = weChatUtil.createAuth(request);
        sign.put("link", link);
        model.put("weichat", sign);
        
        return channel + "/user/forget_password_second";
    }

    
    /**
     * 微信小程序忘记密码 2
     * 
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping("weixin/user/forget_password_second")
    public String weChatForgetPasswordSecond(HttpServletRequest request,
                                       HttpServletResponse response, Map<String, Object> model) {
        model.put("verCode", request.getParameter("verCode"));
        model.put("mobile", request.getParameter("mobile"));
        return "weixin/user/forget_password_second";
    }
}
