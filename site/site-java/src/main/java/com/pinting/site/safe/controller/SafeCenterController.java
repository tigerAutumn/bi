/**
 * Wenzi.com Inc.
 * Copyright (c) 2015-2025 All Rights Reserved.
 */
package com.pinting.site.safe.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.pinting.business.hessian.site.message.ReqMsg_DepGuide_FindDepGuideInfo;
import com.pinting.business.hessian.site.message.ReqMsg_User_BankCardInfo;
import com.pinting.business.hessian.site.message.ReqMsg_User_InfoQuery;
import com.pinting.business.hessian.site.message.ResMsg_DepGuide_FindDepGuideInfo;
import com.pinting.business.hessian.site.message.ResMsg_User_BankCardInfo;
import com.pinting.business.hessian.site.message.ResMsg_User_InfoQuery;
import com.pinting.core.base.BaseController;
import com.pinting.core.cookie.CookieManager;
import com.pinting.site.common.enums.CookieEnums;
import com.pinting.site.communicate.CommunicateBusiness;
import com.pinting.util.Constants;

/**
 * 安全中心
 * @author yanwl
 * @version $Id: SafeCenterController.java, v 0.1 2015-11-24 上午10:02:40 yanwl Exp $
 */
@Controller
@RequestMapping("{channel}/safe")
public class SafeCenterController extends BaseController {
    
    @Autowired
    private CommunicateBusiness siteService;

    
    /**
     * 安全中心首页
     * 
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping("/safe_center")
    public String safe_center(@PathVariable String channel,HttpServletRequest request,ReqMsg_User_InfoQuery req, 
    		Map<String, Object> model) {
    	//组织请求报文
        CookieManager manager = new CookieManager(request);
        String userId = manager.getValue(CookieEnums._SITE.getName(),
            CookieEnums._SITE_USER_ID.getName(), true);
        if (userId != null && !"".equals(userId.trim())) {
            req.setUserId(Integer.parseInt(userId));
        }
        //发起Hessian通讯（资产信息查询）
        ResMsg_User_InfoQuery resp = (ResMsg_User_InfoQuery) siteService.handleMsg(req);
        //判断是否存在支付密码 1:有交易密码；2：无交易密码
        if (resp.getExcitPaypassword() == 2) {
            model.put("isBindPayPassword", false);
        }
        // 存管引导信息
 		ReqMsg_DepGuide_FindDepGuideInfo depGuideReq = new ReqMsg_DepGuide_FindDepGuideInfo();
 		depGuideReq.setUserId(Integer.parseInt(userId));
 		ResMsg_DepGuide_FindDepGuideInfo depGuideRes =  (ResMsg_DepGuide_FindDepGuideInfo) siteService.handleMsg(depGuideReq);
 		request.setAttribute("hfDepGuideInfo", depGuideRes);
 		// 是否实名认证(绑卡和曾经绑卡)
 		if (Constants.YES.equals(depGuideRes.getIsBindName())) {
 			ReqMsg_User_BankCardInfo bankCardReq = new ReqMsg_User_BankCardInfo();
 			bankCardReq.setUserId(Integer.parseInt(userId));
 			ResMsg_User_BankCardInfo bankCardResp = (ResMsg_User_BankCardInfo) siteService.handleMsg(bankCardReq);
 			model.put("bankCardInfo", bankCardResp);
 		}
 		model.put("userInfo", resp);
    	return channel +"/safe/safe_center";
    }
    
    /**
     * 忘记交易密码界面
     * 
     * @param channel
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/forget_pay_password")
    public String forgetPayPasswordIndex(@PathVariable String channel,HttpServletRequest request,
                                         HttpServletResponse response, Map<String, Object> model) {
        CookieManager manager = new CookieManager(request);
        String userId = manager.getValue(CookieEnums._SITE.getName(),
            CookieEnums._SITE_USER_ID.getName(), true);
        ReqMsg_User_InfoQuery req = new ReqMsg_User_InfoQuery();
        if (userId != null && !"".equals(userId.trim())) {
            req.setUserId(Integer.parseInt(userId));
        }
        //发起Hessian通讯（资产信息查询）
        ResMsg_User_InfoQuery resp = (ResMsg_User_InfoQuery) siteService.handleMsg(req);
        model.put("mobile", resp.getMobile());
        return channel +"/safe/safe_forget_trader_password";
    }
}
