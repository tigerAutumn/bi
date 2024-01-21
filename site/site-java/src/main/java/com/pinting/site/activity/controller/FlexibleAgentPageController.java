package com.pinting.site.activity.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.pinting.business.hessian.site.message.ReqMsg_Home_InfoQuery;
import com.pinting.business.hessian.site.message.ReqMsg_LandingPage_GetSupportTerminal;
import com.pinting.business.hessian.site.message.ReqMsg_LandingPage_Index318;
import com.pinting.business.hessian.site.message.ReqMsg_Product_ListQuery;
import com.pinting.business.hessian.site.message.ResMsg_Home_InfoQuery;
import com.pinting.business.hessian.site.message.ResMsg_LandingPage_GetSupportTerminal;
import com.pinting.business.hessian.site.message.ResMsg_LandingPage_Index318;
import com.pinting.business.hessian.site.message.ResMsg_Product_ListQuery;
import com.pinting.core.cookie.CookieManager;
import com.pinting.core.util.StringUtil;
import com.pinting.site.common.enums.CookieEnums;
import com.pinting.site.common.utils.GlobEnv;
import com.pinting.site.communicate.CommunicateBusiness;
import com.pinting.site.weichat.util.WeChatUtil;
import com.pinting.util.Constants;

@Controller
@RequestMapping("{channel}/agent/")
public class FlexibleAgentPageController {

    @Autowired
    private CommunicateBusiness communicateBusiness;
    
    @Autowired
    private WeChatUtil weChatUtil;

	/**
	  * FLEXIBLE 推广型代理统一控制
	  * @param channel
	  * @param landPage
	  * @param req
	  * @param request
	  * @param model
	  * @return
	  */
	   @RequestMapping("/{id}")
	   public String commonAgent(@PathVariable String channel,ReqMsg_Home_InfoQuery regHome,@PathVariable Integer id, ReqMsg_LandingPage_Index318 req, HttpServletRequest request, Map<String, Object> model){
	       CookieManager cookieManager = new CookieManager(request);
	       String userId = cookieManager.getValue(CookieEnums._SITE.getName(), CookieEnums._SITE_USER_ID.getName(), true);
	       ReqMsg_LandingPage_GetSupportTerminal reqT= new ReqMsg_LandingPage_GetSupportTerminal();
	       reqT.setId(id);
		   ResMsg_LandingPage_GetSupportTerminal resT = (ResMsg_LandingPage_GetSupportTerminal) communicateBusiness.handleMsg(reqT);
		   List<String> terminalList = new ArrayList<String>();
		   for(String t: resT.getSupport_terminal().split(",")){
			   terminalList.add(t);
		   }
	       if("micro2.0".equals(channel)) {
	           if(StringUtil.isBlank(userId) && terminalList.contains("h5")) {
	               // 分享
	               String link = GlobEnv.getWebURL("/micro2.0/agent/" + id);
	               Map<String,String> sign = weChatUtil.createAuth(request);
	               sign.put("link", link);
	               model.put("weichat", sign);
	               model.put("flag", request.getParameter("flag"));
	           } else {
	               return "redirect:/" + channel + "/index";
	           }
	       } else {
	    	   if(StringUtil.isBlank(userId) && terminalList.contains("pc")){
	    		   ResMsg_LandingPage_Index318 res = (ResMsg_LandingPage_Index318) communicateBusiness.handleMsg(req);
	               ReqMsg_Product_ListQuery reqMsg = new ReqMsg_Product_ListQuery();
	               reqMsg.setUserType(Constants.USER_TYPE_NORMAL);
	               ResMsg_Product_ListQuery resMsg = (ResMsg_Product_ListQuery) communicateBusiness.handleMsg(reqMsg);
	               List<Map<String,Object>> dataList = resMsg.getProductLst();
	               model.put("totalInterest", res.getTotalInterest());
	               model.put("products", dataList);
	               //平台累计成交额
	               regHome.setProductShowTerminal(Constants.PRODUCT_SHOW_TERMINAL_PC);
	               regHome.setUserType("NORMAL");
	               if(!StringUtil.isBlank(userId)) {
	                   regHome.setUserId(Integer.parseInt(userId));
	               }
	               ResMsg_Home_InfoQuery resHome = (ResMsg_Home_InfoQuery) communicateBusiness.handleMsg(regHome);
	               model.put("currTotalAmount", resHome.getCurrTotalAmount());
	    	   }else{
	    		   return "redirect:/" + channel + "/index";
	    	   }
	       }
	       model.put("agentID", id);
	       return channel + "/landing_page/index_common";
	   }
}
