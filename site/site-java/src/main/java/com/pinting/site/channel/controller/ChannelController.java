package com.pinting.site.channel.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.pinting.business.hessian.site.message.ReqMsg_User_AgentQuery;
import com.pinting.business.hessian.site.message.ResMsg_User_AgentQuery;
import com.pinting.core.cookie.CookieManager;
import com.pinting.core.util.StringUtil;
import com.pinting.site.common.enums.CookieEnums;
import com.pinting.site.common.utils.GlobEnv;
import com.pinting.site.communicate.CommunicateBusiness;
import com.pinting.site.weichat.util.WeChatUtil;
import com.pinting.util.Constants;
import com.pinting.util.Util;

@Controller
public class ChannelController {
	
	@Autowired
	private CommunicateBusiness siteService;
	
	@Autowired
    private WeChatUtil weChatUtil;
	
	//private final Logger logger = LoggerFactory.getLogger(UserRegistController.class);
	
	/**
	 * 注册首页
	 * @param channel
	 * @param agentId
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("channel/{agentId}")
	public String agentRegisterInit(@PathVariable String agentId,
			HttpServletRequest request, HttpServletResponse response, Map<String, Object> data) {
	    String link = GlobEnv.getWebURL("micro2.0/index");
	    // 分享
        Map<String,String> sign = weChatUtil.createAuth(request);
        sign.put("link", link);
        data.put("weichat", sign);
        
		//根据渠道编号查询渠道信息
		ReqMsg_User_AgentQuery reqMsg = new ReqMsg_User_AgentQuery();
		try {
			reqMsg.setAgentId(Integer.parseInt(agentId));
			ResMsg_User_AgentQuery resp = (ResMsg_User_AgentQuery) siteService.handleMsg(reqMsg);
			if ("9100029".equals(resp.getResCode()) ||  reqMsg.getAgentId()==Constants.AGENT_QIANBAO_ID) {
				return "/micro2.0/channel/channel_not_exisit";
			}
			data.put("agent", resp);
		} catch (Exception e) {
			e.printStackTrace();
			return "/micro2.0/channel/channel_error";
		}
		return  "/micro2.0/channel/channel_index";
	}

	
    /**
     * 注册界面
     * 
     * @param channel
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("{channel}/channel/register_second_index")
    public String registerSecondIndex(@PathVariable String channel, HttpServletRequest request,
                                      HttpServletResponse response, Map<String, Object> model) {
        model.put("mobile", request.getParameter("mobile"));
        model.put("agentId", request.getParameter("agentId"));
        String link = GlobEnv.getWebURL("/micro2.0/index");
        // 钱报的参数
        String qianbao = request.getParameter("qianbao");
        if(StringUtil.isNotBlank(qianbao) && Constants.USER_AGENT_QIANBAO.equals(qianbao)) {
            model.put("qianbao", Constants.USER_AGENT_QIANBAO);
            link += "?qianbao=qianbao";
            CookieManager manager = new CookieManager(request);
            String viewAgentFlagCookie = manager.getValue(CookieEnums._VIEW.getName(), CookieEnums._VIEW_AGENT_FLAG.getName(), true);
	        if(StringUtil.isNotBlank(viewAgentFlagCookie)){
	        	link += "&agentViewFlag="+viewAgentFlagCookie;
	        }
        }
        // 分享
        Map<String,String> sign = weChatUtil.createAuth(request);
        sign.put("link", link);
        model.put("weichat", sign);
        
        CookieManager cookieManager = new CookieManager(request);
        String recommendId = cookieManager.getValue(CookieEnums._SITE.getName(),
            CookieEnums._SITE_RECOMMEND_ID.getName(), true);
        if (recommendId != null && !"".equals(recommendId)) {
       	 	if(!recommendId.matches("[0-9]+")){//不是数字就设置null
        		model.put("recommendId", recommendId);
        	}else{
        		model.put("recommendId", Util.generateInvitationCode(Integer.parseInt(recommendId)));
        	}
        }
        return channel + "/channel/register_second_index";
    }
    
}
