package com.pinting.mall.controller.points;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.pinting.core.cookie.CookieManager;
import com.pinting.core.util.StringUtil;
import com.pinting.mall.controller.MallBaseController;
import com.pinting.mall.enums.AgentViewFlagEnum;
import com.pinting.mall.hessian.site.message.ReqMsg_MallPoints_PointsRecord;
import com.pinting.mall.hessian.site.message.ReqMsg_MallPoints_UserPoints;
import com.pinting.mall.hessian.site.message.ResMsg_MallPoints_PointsRecord;
import com.pinting.mall.hessian.site.message.ResMsg_MallPoints_UserPoints;
import com.pinting.site.common.enums.CookieEnums;
import com.pinting.site.common.utils.GlobEnv;
import com.pinting.site.communicate.CommunicateBusiness;
import com.pinting.site.weichat.util.WeChatUtil;
import com.pinting.util.Constants;
import com.pinting.util.DESUtil;

/**
 * 积分商城-积分相关Controller
 *
 * @author shh
 * @date 2018/5/12 21:56
 * @Copyright: 2018 BiGangWan.com Inc. All rights reserved.
 */
@Controller
public class MallPointsController  extends MallBaseController {

    @Autowired
    private CommunicateBusiness mallPointsService;
    
    @Autowired
	private WeChatUtil weChatUtil;

    /**
     * 积分规则页面
     * @param channel
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping("{channel}/mallPoints/pointsRuleIndex")
    public String toPointsRuleIndex(@PathVariable String channel, HttpServletRequest request, HttpServletResponse response, Map<String,Object> model){
		String userId = null;
		String url = "";
		if(Constants.REQUEST_TERMINAL_H5.equals(channel)){
			url = "mall/points/mall_rule";
		}else if(Constants.REQUEST_TERMINAL_ANDROID.equals(channel) || Constants.REQUEST_TERMINAL_IOS.equals(channel)) {
			userId = StringUtil.isNotBlank(request.getParameter("appUserId")) ? request.getParameter("appUserId") : null;
			String userIdStr = request.getParameter("userId");
			if(StringUtil.isBlank(userId)){
				userId = userIdStr;
			}
			// 加密后的userId回传给app页面
			model.put("userId", userId);

			if(userId == null && StringUtil.isNotBlank(userIdStr)){
				userIdStr = new DESUtil("cfgubijn").decryptStr(userIdStr);
				userId = userIdStr;
			}
			String client = request.getParameter("client");
			model.put("client", client);
			url = "mall/points/mall_rule_app";
		}
        return url;
    }
    
    /**
     * 积分记录页面
     * @author bianyatian
     * @param channel
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping("{channel}/mallPoints/pointsRecord")
    public String pointsRecord(@PathVariable String channel, HttpServletRequest request, HttpServletResponse response, Map<String,Object> model){
    	Integer userId = null;
    	String show_html = "";
    	if (Constants.REQUEST_TERMINAL_ANDROID.equals(channel) || Constants.REQUEST_TERMINAL_IOS.equals(channel)){
    		String client = request.getParameter("client");
    		model.put("client", client);
    		String userIdStr = request.getParameter("userId");
    		model.put("userId", userIdStr);
    		if(userId == null && StringUtil.isNotBlank(userIdStr)){
    			userIdStr = new DESUtil("cfgubijn").decryptStr(userIdStr);
    			userId = Integer.valueOf(userIdStr);
    		}
    		show_html = "mall/points/mall_points_record_app";
    	} else {
    		CookieManager manager = new CookieManager(request);
    		//查询userId,如不为空，查询手机号并返回，若为空，则返回空
    		String userIdStr = manager.getValue(CookieEnums._SITE.getName(),
    				CookieEnums._SITE_USER_ID.getName(), true);
    		if (StringUtil.isNotEmpty(userIdStr)) {
    			userId = Integer.valueOf(userIdStr);
    		}
    		
    		if(Constants.REQUEST_TERMINAL_H5.equals(channel)){
                
                //分享相关
                String link = GlobEnv.getWebURL("/micro2.0/more/more");
                String qianbao = request.getParameter("qianbao");//钱报标识
                if(StringUtil.isNotBlank(qianbao) && Constants.USER_AGENT_QIANBAO.equals(qianbao)){
                	model.put("qianbao", Constants.USER_AGENT_QIANBAO);
                    Map<String,String> sign = weChatUtil.createAuth(request);
                    link += "?qianbao=qianbao";
                    String viewAgentFlagCookie = manager.getValue(CookieEnums._VIEW.getName(), CookieEnums._VIEW_AGENT_FLAG.getName(), true);
        	        if(StringUtil.isNotBlank(viewAgentFlagCookie)){
        	        	link += "&agentViewFlag="+viewAgentFlagCookie;
        	        }
                    sign.put("link", link);
                    model.put("weichat", sign);
                }else{
                	Map<String,String> sign = weChatUtil.createAuth(request);
                    sign.put("link", link);
                    model.put("weichat", sign);
                }
                
                show_html = "mall/points/mall_points_record";
        	} else {
        		//pc端
            	AgentViewFlagEnum agentViewFlagEnum = super.getAgentViewFlagEnum(request, channel, model);
	            show_html = agentViewFlagEnum.getPathPrefix() + "/exchange/record";
            }
    	}
    	
    	if(userId != null){
    		//获取用户积分
    		ReqMsg_MallPoints_UserPoints userPointsReq = new ReqMsg_MallPoints_UserPoints();
    		userPointsReq.setUserId(userId);
    		ResMsg_MallPoints_UserPoints userPointsRes = (ResMsg_MallPoints_UserPoints)mallPointsService.handleMallMsg(userPointsReq);
    		model.put("points", userPointsRes.getPoints());
    		//获取用户积分记录
    		ReqMsg_MallPoints_PointsRecord reqMsg = new ReqMsg_MallPoints_PointsRecord();
    		reqMsg.setUserId(userId);
    		String page = request.getParameter("page");
    		reqMsg.setPageNum(StringUtil.isBlank(page)? 1 : Integer.parseInt(page));
    		reqMsg.setNumPerPage(10);
    		
    		ResMsg_MallPoints_PointsRecord resMsg = (ResMsg_MallPoints_PointsRecord)mallPointsService.handleMallMsg(reqMsg);
    		model.put("count", resMsg.getCount());
    		model.put("totalPage", (int)Math.ceil((double)resMsg.getCount()/10));
    		model.put("recordList", resMsg.getPointsRecordList());
    		
    	}
    	
        return show_html;
    }
    
    /**
     * 积分记录页面 - 分页
     * @param channel
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping("{channel}/mallPoints/pointsRecord/page")
    public String pointsRecordPage(@PathVariable String channel, HttpServletRequest request,
                              HttpServletResponse response, Map<String, Object> model) {
    	Integer userId = null;
    	if(Constants.REQUEST_TERMINAL_H5.equals(channel)){
    		CookieManager manager = new CookieManager(request);
            //查询userId,如不为空，查询手机号并返回，若为空，则返回空
            String userIdStr = manager.getValue(CookieEnums._SITE.getName(),
                CookieEnums._SITE_USER_ID.getName(), true);
            if (StringUtil.isNotEmpty(userIdStr)) {
            	userId = Integer.valueOf(userIdStr);
            }
    	}else if (Constants.REQUEST_TERMINAL_ANDROID.equals(channel) || Constants.REQUEST_TERMINAL_IOS.equals(channel)){
    		String client = request.getParameter("client");
    		model.put("client", client);
    		String userIdStr = request.getParameter("userId");
    		model.put("userId", userIdStr);
    		if(userId == null && StringUtil.isNotBlank(userIdStr)){
    			userIdStr = new DESUtil("cfgubijn").decryptStr(userIdStr);
    			userId = Integer.valueOf(userIdStr);
    		}
    	}
    	
    	if(userId != null){
    		//获取用户积分记录
    		ReqMsg_MallPoints_PointsRecord reqMsg = new ReqMsg_MallPoints_PointsRecord();
    		reqMsg.setUserId(userId);
    		String page = request.getParameter("page");
    		reqMsg.setPageNum(StringUtil.isBlank(page)? 1 : Integer.parseInt(page));
    		reqMsg.setNumPerPage(10);
    		
    		ResMsg_MallPoints_PointsRecord resMsg = (ResMsg_MallPoints_PointsRecord)mallPointsService.handleMallMsg(reqMsg);
    		model.put("count", resMsg.getCount());
    		model.put("recordList", resMsg.getPointsRecordList());
    		
    	}
    	
        return "mall/points/mall_points_record_page";
    }

    /**
     * pc端积分记录页面 - 分页
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping("{channel}/mallPoints/pointsRecordPagePc")
    public String pointsRecordPagePc(@PathVariable String channel, HttpServletRequest request,
                              HttpServletResponse response, Map<String, Object> model) {
    	Integer userId = null;
    	String show_html = "";
    	CookieManager manager = new CookieManager(request);
        //查询userId,如不为空，查询手机号并返回，若为空，则返回空
        String userIdStr = manager.getValue(CookieEnums._SITE.getName(),
            CookieEnums._SITE_USER_ID.getName(), true);
        if (StringUtil.isNotEmpty(userIdStr)) {
        	userId = Integer.valueOf(userIdStr);
        }
        
        AgentViewFlagEnum agentViewFlagEnum = super.getAgentViewFlagEnum(request, channel, model);
        show_html = agentViewFlagEnum.getPathPrefix() + "/exchange/record_content";
        
        if(userId != null){
    		//获取用户积分记录
    		ReqMsg_MallPoints_PointsRecord reqMsg = new ReqMsg_MallPoints_PointsRecord();
    		reqMsg.setUserId(userId);
    		String page = request.getParameter("page");
    		reqMsg.setPageNum(StringUtil.isBlank(page)? 1 : Integer.parseInt(page));
    		reqMsg.setNumPerPage(10);
    		
    		ResMsg_MallPoints_PointsRecord resMsg = (ResMsg_MallPoints_PointsRecord)mallPointsService.handleMallMsg(reqMsg);
    		model.put("totalPage", (int)Math.ceil((double)resMsg.getCount()/10));
    		model.put("recordList", resMsg.getPointsRecordList());
    	}
        return show_html;
    }
}
