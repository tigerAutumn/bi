package com.pinting.mall.controller;

import com.pinting.core.base.BaseController;
import com.pinting.core.cookie.CookieManager;
import com.pinting.core.util.StringUtil;
import com.pinting.mall.enums.AgentViewFlagEnum;
import com.pinting.site.common.enums.CookieEnums;
import com.pinting.util.Constants;
import com.pinting.util.DESUtil;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

/**
 * 页面公共内容处理
 *
 * @author zousheng
 * @date 2018年8月14日 上午9:48:05
 */
public class MallBaseController extends BaseController {

	/**
     * 读取渠道标识，获取渠道展示页
     *
     * @param request
     * @return
     */
    public AgentViewFlagEnum getAgentViewFlagEnum(HttpServletRequest request, String channel,  Map<String,Object> model) {
    	String agentViewFlag = request.getParameter("agentViewFlag");
    	if (Constants.REQUEST_TERMINAL_PC_AGENT.equals(channel)) {
    		if (StringUtil.isEmpty(agentViewFlag)) {
    			agentViewFlag = Constants.AGENT_VIEW_ID_QB;
    			model.put("agentViewFlag", agentViewFlag);
    		} else {
    			model.put("agentViewFlag", agentViewFlag);
    		}
    	}
        return AgentViewFlagEnum.find(channel,agentViewFlag);
    }
	
    /**
     * 获取用户userId
     * @param channel
     * @param request
     * @return
     */
    public Integer getUserId(String channel, HttpServletRequest request) {
        // 用户信息
        Integer userId = null;
        if (Constants.REQUEST_TERMINAL_H5.equals(channel) || Constants.REQUEST_TERMINAL_PC.equals(channel)
        		|| Constants.REQUEST_TERMINAL_PC_AGENT.equals(channel)) {
            CookieManager manager = new CookieManager(request);
            //查询userId,如不为空，查询手机号并返回，若为空，则返回空
            String userIdStr = manager.getValue(CookieEnums._SITE.getName(),
                    CookieEnums._SITE_USER_ID.getName(), true);
            if (StringUtil.isNotEmpty(userIdStr)) {
                userId = Integer.valueOf(userIdStr);
            }
        } else if (Constants.REQUEST_TERMINAL_ANDROID.equals(channel) || Constants.REQUEST_TERMINAL_IOS.equals(channel)) {
            String userIdStr = request.getParameter("userId");
            if (userId == null && StringUtil.isNotBlank(userIdStr)) {
                userIdStr = new DESUtil("cfgubijn").decryptStr(userIdStr);
                userId = Integer.valueOf(userIdStr);
            }
            userIdStr = request.getParameter("appUserId");
            if (userId == null && StringUtil.isNotBlank(userIdStr)) {
                userIdStr = new DESUtil("cfgubijn").decryptStr(userIdStr);
                userId = Integer.valueOf(userIdStr);
            }
        }

        return userId;
    }
}
