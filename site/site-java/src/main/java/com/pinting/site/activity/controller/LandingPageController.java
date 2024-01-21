/**
 * Wenzi.com Inc.
 * Copyright (c) 2015-2025 All Rights Reserved.
 */
package com.pinting.site.activity.controller;

import com.pinting.business.hessian.site.message.*;
import com.pinting.core.cookie.CookieManager;
import com.pinting.core.util.StringUtil;
import com.pinting.site.common.enums.CookieEnums;
import com.pinting.site.common.utils.GlobEnv;
import com.pinting.site.communicate.CommunicateBusiness;
import com.pinting.site.weichat.util.WeChatUtil;
import com.pinting.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * 落地页Controller
 * @author HuanXiong
 * @version $Id: LandingPageController.java, v 0.1 2016-3-23 上午11:13:49 HuanXiong Exp $
 */
@Controller
@RequestMapping("{channel}/landing/")
public class LandingPageController {
    
    @Autowired
    private CommunicateBusiness communicateBusiness;
    @Autowired
    private WeChatUtil weChatUtil;

    @RequestMapping("/landingPage318")
    public String landingPage318(@PathVariable String channel, ReqMsg_LandingPage_Index318 req, HttpServletRequest request, HttpServletResponse response, Map<String, Object> model){
        CookieManager cookieManager = new CookieManager(request);
        String userId = cookieManager.getValue(CookieEnums._SITE.getName(), CookieEnums._SITE_USER_ID.getName(), true);
        if(StringUtil.isBlank(userId)) {
            if("micro2.0".equals(channel)) {
                // 分享
                String link = GlobEnv.getWebURL("/micro2.0/landing/landingPage318");
                Map<String,String> sign = weChatUtil.createAuth(request);
                sign.put("link", link);
                model.put("weichat", sign);
                model.put("flag", request.getParameter("flag"));
            } else {
                ResMsg_LandingPage_Index318 res = (ResMsg_LandingPage_Index318) communicateBusiness.handleMsg(req);
                ReqMsg_Product_ListQuery reqMsg = new ReqMsg_Product_ListQuery();
                reqMsg.setUserType(Constants.USER_TYPE_NORMAL);
                ResMsg_Product_ListQuery resMsg = (ResMsg_Product_ListQuery) communicateBusiness.handleMsg(reqMsg);
                List<Map<String,Object>> dataList = resMsg.getProductLst();
                model.put("totalInterest", res.getTotalInterest());
                model.put("products", dataList);
            }
            return channel + "/landing_page/index_318";
        } else {
            return "redirect:/" + channel + "/index";
        }
    }

    /**
     *
     * @param channel
     * @param landPage  index-卓推广-35；quanmama-券妈妈-37；liuliangbao-流量包-38；tianmangyun-天芒云-39
     * @param req
     * @param request
     * @param model
     * @return
     */
    @RequestMapping("/{landPage}")
    public String landingPage318(@PathVariable String channel, @PathVariable String landPage, ReqMsg_LandingPage_Index318 req, HttpServletRequest request, Map<String, Object> model){
        CookieManager cookieManager = new CookieManager(request);
        String userId = cookieManager.getValue(CookieEnums._SITE.getName(), CookieEnums._SITE_USER_ID.getName(), true);
        if("micro2.0".equals(channel)) {
            if(StringUtil.isBlank(userId)) {
                // 分享
                String link = GlobEnv.getWebURL("/micro2.0/landing/" + landPage);
                Map<String,String> sign = weChatUtil.createAuth(request);
                sign.put("link", link);
                model.put("weichat", sign);
                model.put("flag", request.getParameter("flag"));
            } else {
                return "redirect:/" + channel + "/index";
            }
        } else {
            if("hefeiluntan".equals(landPage)) {
                if(StringUtil.isBlank(userId)) {
                    // 分享
                    String link = GlobEnv.getWebURL("/micro2.0/landing/" + landPage);

                    Map<String,String> sign = weChatUtil.createAuth(request);
                    sign.put("link", link);
                    model.put("weichat", sign);
                    model.put("flag", request.getParameter("flag"));
                    return "redirect:" + GlobEnv.get("server.web") + "/micro2.0/landing/" + landPage;
                } else {
                    return "redirect:" + GlobEnv.get("server.web") + "/micro2.0/index";
                }
            }
            ReqMsg_Shake_GetWinUserNumber req1 = new ReqMsg_Shake_GetWinUserNumber();
            req1.setActivityFlag("activity_528");
            req1.setTerminal(Constants.PRODUCT_SHOW_TERMINAL_PC);
            ResMsg_Shake_GetWinUserNumber res = (ResMsg_Shake_GetWinUserNumber) communicateBusiness.handleMsg(req1);
            model.put("product", res.getProduct());
            
            ResMsg_LandingPage_Index318 res1 = (ResMsg_LandingPage_Index318) communicateBusiness.handleMsg(req);
            ReqMsg_Product_ListQuery reqMsg = new ReqMsg_Product_ListQuery();
            reqMsg.setUserType(Constants.USER_TYPE_NORMAL);
            ResMsg_Product_ListQuery resMsg = (ResMsg_Product_ListQuery) communicateBusiness.handleMsg(reqMsg);
            List<Map<String,Object>> dataList = resMsg.getProductLst();
            model.put("totalInterest", res1.getTotalInterest());
            model.put("products", dataList);
        }
        return channel + "/landing_page/" + landPage;
    }
}
