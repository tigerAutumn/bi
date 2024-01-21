package com.pinting.site.activity.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pinting.business.hessian.site.message.ReqMsg_Activity_WeChatAwardListInfo;
import com.pinting.business.hessian.site.message.ReqMsg_Activity_WeChatLuckyTurningDataInfo;
import com.pinting.business.hessian.site.message.ReqMsg_Activity_WeChatLuckyTurningInfo;
import com.pinting.business.hessian.site.message.ReqMsg_Activity_WeChatStartTheLottery;
import com.pinting.business.hessian.site.message.ReqMsg_Activity_WechatShareChanceToDraw;
import com.pinting.business.hessian.site.message.ResMsg_Activity_WeChatAwardListInfo;
import com.pinting.business.hessian.site.message.ResMsg_Activity_WeChatLuckyTurningDataInfo;
import com.pinting.business.hessian.site.message.ResMsg_Activity_WeChatLuckyTurningInfo;
import com.pinting.business.hessian.site.message.ResMsg_Activity_WeChatStartTheLottery;
import com.pinting.business.hessian.site.message.ResMsg_Activity_WechatShareChanceToDraw;
import com.pinting.core.cookie.CookieManager;
import com.pinting.core.util.ConstantUtil;
import com.pinting.core.util.StringUtil;
import com.pinting.site.common.enums.CookieEnums;
import com.pinting.site.communicate.CommunicateBusiness;

/**
 * 微信小程序活动
 * @author zhangpeng
 * @2018-05-26
 */
@Controller
public class WeChatMiniProgramController {

	@Autowired
    private CommunicateBusiness communicateBusiness;
	@Autowired
    private CommunicateBusiness siteService;
	
    /**
     * 微信小程序抽奖首页
     * 
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping("weixin/activity/weChatLuckyTurning")
    public String weChatLuckyTurning(HttpServletRequest request, HttpServletResponse response, Map<String, Object> model) {
    	Integer userId = isLogin(request);
    	if (userId != null) {
			model.put("userId", userId);
		}
    	ReqMsg_Activity_WeChatLuckyTurningInfo req = new ReqMsg_Activity_WeChatLuckyTurningInfo();
    	req.setUserId(userId);
    	try {
    		ResMsg_Activity_WeChatLuckyTurningInfo res = (ResMsg_Activity_WeChatLuckyTurningInfo) communicateBusiness.handleMsg(req);
    		if (ConstantUtil.BSRESCODE_SUCCESS.equals(res.getResCode())) {
    			model.put("result", res);
    			model.put("resCode", "000");
    		} else {
    			model.put("resCode", res.getResCode());
    			model.put("resMsg", res.getResMsg());
    		}
		} catch (Exception e) {
			e.printStackTrace();
			model.put("resCode", "999");
			model.put("resMsg", "币港湾堵塞，请稍后再试");
		}
        return "weixin/activity/we_chat_lucky_turning";
    }

    /**
     * 微信小程序抽奖首页异步数据（抽奖机会，是否分享）
     * 
     * @param request
     * @param response
     * @param model
     * @return
     */
    @ResponseBody
    @RequestMapping("weixin/activity/weChatLuckyTurningData")
    public Map<String, Object> weChatLuckyTurningData(HttpServletRequest request) {
    	Integer userId = isLogin(request);
    	Map<String, Object> model = new HashMap<>();
    	ReqMsg_Activity_WeChatLuckyTurningDataInfo req = new ReqMsg_Activity_WeChatLuckyTurningDataInfo();
    	req.setUserId(userId);
    	try {
    		ResMsg_Activity_WeChatLuckyTurningDataInfo res = (ResMsg_Activity_WeChatLuckyTurningDataInfo) communicateBusiness.handleMsg(req);
    		if (ConstantUtil.BSRESCODE_SUCCESS.equals(res.getResCode())) {
    			model.put("result", res);
    			model.put("resCode", "000");
    		} else {
    			model.put("resCode", res.getResCode());
    			model.put("resMsg", res.getResMsg());
    		}
		} catch (Exception e) {
			e.printStackTrace();
			model.put("resCode", "999");
			model.put("resMsg", "币港湾堵塞，请稍后再试");
		}
        return model;
    }
    
    /**
     * 微信小程序我的奖品列表
     * 
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping("weixin/activity/getAwardList")
    public String getAwardList(HttpServletRequest request, HttpServletResponse response, Map<String, Object> model) {
    	Integer userId = isLogin(request);
    	
    	ReqMsg_Activity_WeChatAwardListInfo req = new ReqMsg_Activity_WeChatAwardListInfo();
        req.setUserId(userId);
        try {
    		ResMsg_Activity_WeChatAwardListInfo res = (ResMsg_Activity_WeChatAwardListInfo) communicateBusiness.handleMsg(req);
    		if (ConstantUtil.BSRESCODE_SUCCESS.equals(res.getResCode())) {
    			model.put("awardListResult", res);
    			model.put("resCode", "000");
    		} else {
    			model.put("resCode", res.getResCode());
    			model.put("resMsg", res.getResMsg());
    		}
		} catch (Exception e) {
			e.printStackTrace();
			model.put("resCode", "999");
			model.put("resMsg", "币港湾堵塞，请稍后再试");
		}
        return "weixin/activity/get_award_list";
    }
    
    /**
     * 微信小程序开始抽奖
     * 
     * @param request
     * @param response
     * @param model
     * @return
     */
    @ResponseBody
    @RequestMapping("weixin/activity/startTheLottery")
    public Map<String, Object> startTheLottery(HttpServletRequest request) {
    	Integer userId = isLogin(request); 
    	
    	Map<String, Object> model = new HashMap<>();
        ReqMsg_Activity_WeChatStartTheLottery req = new ReqMsg_Activity_WeChatStartTheLottery();
        req.setUserId(userId);
        ReqMsg_Activity_WeChatLuckyTurningDataInfo turningReq = new ReqMsg_Activity_WeChatLuckyTurningDataInfo();
        turningReq.setUserId(userId);
        try {
        	ResMsg_Activity_WeChatStartTheLottery res = (ResMsg_Activity_WeChatStartTheLottery) communicateBusiness.handleMsg(req);
        	ResMsg_Activity_WeChatLuckyTurningDataInfo turningRes = (ResMsg_Activity_WeChatLuckyTurningDataInfo) communicateBusiness.handleMsg(turningReq);
    		if (ConstantUtil.BSRESCODE_SUCCESS.equals(res.getResCode()) && ConstantUtil.BSRESCODE_SUCCESS.equals(turningRes.getResCode())) {
    			model.put("lotteryResult", res);
    			model.put("result", turningRes);
    			model.put("resCode", "000000");
    		} else {
    			model.put("resCode", res.getResCode());
    			model.put("resMsg", res.getResMsg());
    		}
		} catch (Exception e) {
			e.printStackTrace();
			model.put("resCode", "999999");
			model.put("resMsg", "币港湾堵塞，请稍后再试");
		}
        return model;
    }
    
    /**
     * 微信小程序分享
     * 
     * @param request
     * @param response
     * @param model
     * @return
     */
    @ResponseBody
    @RequestMapping("weixin/activity/shareWeChatMiniProgram")
    public Map<String, Object> shareWeChatMiniProgram(HttpServletRequest request, String userId) {
    	Integer userIdInt = null;
    	if(StringUtil.isNotBlank(userId)) {
            userIdInt = Integer.parseInt(userId);
        }
	    
    	Map<String, Object> model = new HashMap<>();
        ReqMsg_Activity_WechatShareChanceToDraw req = new ReqMsg_Activity_WechatShareChanceToDraw();
        req.setUserId(userIdInt);
        try {
        	ResMsg_Activity_WechatShareChanceToDraw res = (ResMsg_Activity_WechatShareChanceToDraw) communicateBusiness.handleMsg(req);
    		if (ConstantUtil.BSRESCODE_SUCCESS.equals(res.getResCode())) {
    			if ("yes".equals(res.getGetChance())) {
    				model.put("resCode", "000");
        			model.put("resMsg", "当日第一次分享成功，获得一次抽奖机会");
				} else {
					model.put("resCode", "111");
        			model.put("resMsg", "分享成功");
				}
    		} else {
    			model.put("resCode", res.getResCode());
    			model.put("resMsg", res.getResMsg());
    		}
		} catch (Exception e) {
			e.printStackTrace();
			model.put("resCode", "999");
			model.put("resMsg", "分享失败");
		}
        return model;
    }
    
    //获取用户id
  	private Integer isLogin(HttpServletRequest request) {
      	String userIdStr = "";
	    Integer userId = null;
	    CookieManager cookieManager = new CookieManager(request);
	    userIdStr = cookieManager.getValue(CookieEnums._SITE.getName(), CookieEnums._SITE_USER_ID.getName(), true);
	    userId = StringUtil.isNotBlank(userIdStr) ? Integer.parseInt(userIdStr) : null;
  		return userId;
  	}
  	
  	/**
     * 关于币港湾静态页
     * 
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping("weixin/activity/aboutBGW")
    public String aboutBGW(HttpServletRequest request, HttpServletResponse response, Map<String, Object> model) {
        return "weixin/activity/about_BGW";
    }
}
