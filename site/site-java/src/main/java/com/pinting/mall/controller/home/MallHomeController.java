package com.pinting.mall.controller.home;

import com.pinting.core.cookie.CookieManager;
import com.pinting.core.util.StringUtil;
import com.pinting.mall.controller.MallBaseController;
import com.pinting.mall.enums.AgentViewFlagEnum;
import com.pinting.mall.hessian.site.message.ReqMsg_MallCommodity_IndexList;
import com.pinting.mall.hessian.site.message.ReqMsg_MallIndex_UserIndex;
import com.pinting.mall.hessian.site.message.ReqMsg_MallSign_UserSign;
import com.pinting.mall.hessian.site.message.ResMsg_MallCommodity_IndexList;
import com.pinting.mall.hessian.site.message.ResMsg_MallIndex_UserIndex;
import com.pinting.mall.hessian.site.message.ResMsg_MallSign_UserSign;
import com.pinting.site.common.enums.CookieEnums;
import com.pinting.site.common.utils.GlobEnv;
import com.pinting.site.communicate.CommunicateBusiness;
import com.pinting.site.weichat.util.WeChatUtil;
import com.pinting.util.Constants;
import com.pinting.util.DESUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.HashMap;
import java.util.Map;

/**
 * 积分商城首页
 *
 * @author shh
 * @date 2018/5/10 13:55
 * @Copyright: 2018 BiGangWan.com Inc. All rights reserved.
 */
@Controller
public class MallHomeController extends MallBaseController {

    @Autowired
    private CommunicateBusiness siteMallService;
    
    @Autowired
	private WeChatUtil weChatUtil;
    

    /**
     * 积分商城首页
     * @author bianyatian
     * @param channel
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping("{channel}/mall/homeIndex")
    public String homeIndex(@PathVariable String channel, HttpServletRequest request, 
    		HttpServletResponse response, Map<String,Object> model){
    	
    	Integer userId = null;
    	String show_html = "";
    	String mobile = "";
    	
    	if (Constants.REQUEST_TERMINAL_ANDROID.equals(channel) || Constants.REQUEST_TERMINAL_IOS.equals(channel)){
    		String userIdStr = request.getParameter("userId");
    		mobile = request.getParameter("mobile");
    		model.put("client", channel);
    		model.put("userId", userIdStr);
    		if(userId == null && StringUtil.isNotBlank(userIdStr)){
    			userIdStr = new DESUtil("cfgubijn").decryptStr(userIdStr);
    			userId = Integer.valueOf(userIdStr);
    		}
    		show_html = "mall/home/mall_index_app";
    	} else {
    		CookieManager manager = new CookieManager(request);
    		//查询userId,如不为空，查询手机号并返回，若为空，则返回空
    		String userIdStr = manager.getValue(CookieEnums._SITE.getName(),
    				CookieEnums._SITE_USER_ID.getName(), true);
    		if (StringUtil.isNotEmpty(userIdStr)) {
    			userId = Integer.valueOf(userIdStr);
    		}
    		mobile = manager.getValue(CookieEnums._SITE.getName(),
    				CookieEnums._SITE_MOBILE_NUM.getName(), true);
    		if (StringUtil.isNotEmpty(mobile) && mobile.length() == 11) {
    			mobile = mobile.substring(0, 3) + "****" + mobile.substring(7);
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
	            
	            show_html = "mall/home/mall_index";
	    	} else  {
	    		// 获取渠道展示页
	            if(userId != null){
	            	model.put("isLogin", "YES");
	            } else {
	            	model.put("isLogin", "NO");
	            }
	            AgentViewFlagEnum agentViewFlagEnum = super.getAgentViewFlagEnum(request, channel, model);
	            show_html = agentViewFlagEnum.getPathPrefix() + "/home/mall_index";
	    	} 
    	}
    	

        if(userId != null){
                //获取用户积分和签到标识
                ReqMsg_MallIndex_UserIndex userReq = new ReqMsg_MallIndex_UserIndex();
                userReq.setUserId(userId);
                ResMsg_MallIndex_UserIndex userRes = (ResMsg_MallIndex_UserIndex)siteMallService.handleMallMsg(userReq);
                model.put("isSign", userRes.getIsSign());
                model.put("points", userRes.getPoints());
        }
       
        model.put("mobile", mobile);
        
        //获取商品列表
        ReqMsg_MallCommodity_IndexList commReq = new ReqMsg_MallCommodity_IndexList();
        
        ResMsg_MallCommodity_IndexList commRes = (ResMsg_MallCommodity_IndexList)siteMallService.handleMallMsg(commReq);
        model.put("commList", commRes.getCommodityList());
        
        return show_html;
    }
    
    @ResponseBody
   	@RequestMapping("{channel}/mall/userSign")
   	public Map<String, Object> userSign(@PathVariable String channel,HttpServletRequest request,
   			HttpServletResponse response) {
   		Map<String, Object> result = new HashMap<String, Object>();
   		Integer userId = getUserId(channel, request);
   		if(userId == null){
   			result.put("isLogin", "NO");
   			return result;
   		}else{
   			result.put("isLogin", "YES");
   			//进行签到
   			ReqMsg_MallSign_UserSign userSignReq = new ReqMsg_MallSign_UserSign();
   			userSignReq.setUserId(userId);
			userSignReq.setChannel(channel);
   			ResMsg_MallSign_UserSign userSignRes = (ResMsg_MallSign_UserSign)siteMallService.handleMallMsg(userSignReq);
   			result.put("isSign", userSignRes.getIsSign());
   			result.put("signSucc", userSignRes.getSignSucc());
   			result.put("signPoints", userSignRes.getSignPoints());
   			result.put("signDays", userSignRes.getSignDays());
   			
   		}
   		
   		return result;
   	}
}
