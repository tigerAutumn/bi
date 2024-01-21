package com.pinting.site.common.interceptor;

import com.pinting.business.hessian.site.message.ReqMsg_AgentViewConfig_GetAgentIds;
import com.pinting.business.hessian.site.message.ResMsg_AgentViewConfig_GetAgentIds;
import com.pinting.core.cookie.CookieManager;
import com.pinting.core.util.SpringBeanUtil;
import com.pinting.core.util.StringUtil;
import com.pinting.site.common.enums.AgentViewIdEnum;
import com.pinting.site.common.enums.CookieEnums;
import com.pinting.site.common.utils.GlobEnv;
import com.pinting.site.communicate.CommunicateBusiness;
import com.pinting.util.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Author:      cyb
 * Date:        2016/10/9
 * Description:
 */
public class PreURLInterceptor extends HandlerInterceptorAdapter {

    private Logger logger = LoggerFactory.getLogger(URLInterceptor.class);

    public static List<Integer> agentIdList = new ArrayList<>();

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (request.getHeader("x-requested-with") != null && request.getHeader("x-requested-with").equalsIgnoreCase("XMLHttpRequest")){ //如果是ajax请求响应头会有x-requested-with
            return true;
        }
        String qianbao = request.getParameter("qianbao");
        if (request.getServletPath().contains("/gen178/") || "gen178".equals(request.getParameter("channel"))) {
            // 渠道配置图片文案展示
            agentViewSaveCookiePC178(request, response);
        } else if((request.getServletPath().contains("/micro2.0/") || request.getServletPath().contains("/mall/")) && StringUtil.isNotBlank(qianbao)) {
            // 渠道配置图片文案展示
            agentViewSaveCookieH5178(request, response);
        }
        return true;
    }


    /**
     * H5_178:添加针对原钱报url的拦截,并添加cookie
     * @param request
     * @param response
     */
    private void agentViewSaveCookieH5178(HttpServletRequest request, HttpServletResponse response) {
//        1. 用户未登录
//        1.1 参数存在柯桥或者钱报标识，进入柯桥或者钱报页面
//        1.2 参数不存在柯桥和钱报标识，保持原有cookie对应页面，没有，则默认进入钱报页面
//
//        2. 用户已登录
//        2.1 钱报或者柯桥用户，根据用户agentId进入对应页面
//        2.2 非钱报或者柯桥用户：
//        2.2.1 参数存在柯桥或者钱报标识，进入柯桥或者钱报页面
//        2.2.2 参数不存在柯桥和钱报标识，保持原有cookie对应页面，没有，则默认进入钱报页面

        CookieManager cookie = new CookieManager(request);
        String userId = cookie.getValue(CookieEnums._SITE.getName(), CookieEnums._SITE_USER_ID.getName(), true);
        String agentViewFlag = request.getParameter("agentViewFlag");
        String viewAgentFlagCookie = cookie.getValue(CookieEnums._VIEW.getName(), CookieEnums._VIEW_AGENT_FLAG.getName(), true);
        String userAgentId = cookie.getValue(CookieEnums._SITE.getName(), CookieEnums._SITE_REAL_AGENT_ID.getName(), true);

        if(CollectionUtils.isEmpty(agentIdList)) {
            CommunicateBusiness communicateBusiness = (CommunicateBusiness) SpringBeanUtil.getBean("communicateBusiness");
            ReqMsg_AgentViewConfig_GetAgentIds req = new ReqMsg_AgentViewConfig_GetAgentIds();
            ResMsg_AgentViewConfig_GetAgentIds res = (ResMsg_AgentViewConfig_GetAgentIds) communicateBusiness.handleMsg(req);
            agentIdList = res.getAgentIds();
        }

        if(StringUtil.isBlank(viewAgentFlagCookie)) {
            // 1. cookie不存在钱报||绍兴日报等标识
            if(StringUtil.isNotBlank(agentViewFlag)) {
                // 1.1 参数存在柯桥|钱报，刷新cookie
                agentViewFlag = StringUtil.replace(agentViewFlag, "undefined", "");
                if(agentIdList.contains(Integer.parseInt(agentViewFlag))) {
                    cookie.setValue(CookieEnums._VIEW.getName(), CookieEnums._VIEW_AGENT_FLAG.getName(), agentViewFlag, true);
                } else {
                    // 1.1 参数不存在柯桥|钱报，刷新cookie为钱报
                    agentViewFlag = Constants.AGENT_VIEW_ID_QB;
                    cookie.setValue(CookieEnums._VIEW.getName(), CookieEnums._VIEW_AGENT_FLAG.getName(), Constants.AGENT_VIEW_ID_QB, true);
                }
            } else {
                // 1.2 不存在flag：默认178
                agentViewFlag = Constants.AGENT_VIEW_ID_QB;
                cookie.setValue(CookieEnums._VIEW.getName(), CookieEnums._VIEW_AGENT_FLAG.getName(), Constants.AGENT_VIEW_ID_QB, true);
            }
            cookie.save(response, CookieEnums._VIEW.getName(), null, "/", -1, true);
        } else {
            // 2. 存在cookie
            if(StringUtil.isBlank(userId)) {
                // 2.1 参数存在flag
                agentViewFlag = saveCookie(agentViewFlag, viewAgentFlagCookie, cookie, response);
            } else {
                if(StringUtil.isNotBlank(userAgentId)) {
                    userAgentId = StringUtil.replace(userAgentId, "undefined", "");
                    if(agentIdList.contains(Integer.parseInt(userAgentId))) {
                        cookie.setValue(CookieEnums._VIEW.getName(), CookieEnums._VIEW_AGENT_FLAG.getName(), userAgentId, true);
                        cookie.save(response, CookieEnums._VIEW.getName(), null, "/", -1, true);
                        agentViewFlag = userAgentId;
                    } else {
                        // 渠道用户
                        agentViewFlag = saveCookie(agentViewFlag, viewAgentFlagCookie, cookie, response);
                    }
                } else {
                    // 普通用户
                    agentViewFlag = saveCookie(agentViewFlag, viewAgentFlagCookie, cookie, response);
                }
            }
        }
        request.setAttribute("agentViewFlag", agentViewFlag);
    }

    /**
     * PC_178:添加针对原钱报url的拦截,并添加cookie
     * @param request
     * @param response
     */
    private void agentViewSaveCookiePC178(HttpServletRequest request, HttpServletResponse response) {
//        1. 用户未登录
//        1.1 参数存在柯桥或者钱报标识，进入柯桥或者钱报页面
//        1.2 参数不存在柯桥和钱报标识，保持原有cookie对应页面，没有，则默认进入钱报页面
//
//        2. 用户已登录
//        2.1 钱报或者柯桥用户，根据用户agentId进入对应页面
//        2.2 非钱报或者柯桥用户：
//        2.2.1 参数存在柯桥或者钱报标识，进入柯桥或者钱报页面
//        2.2.2 参数不存在柯桥和钱报标识，保持原有cookie对应页面，没有，则默认进入钱报页面

        CookieManager cookie = new CookieManager(request);
        String userId = cookie.getValue(CookieEnums._SITE.getName(), CookieEnums._SITE_USER_ID.getName(), true);
        String agentViewFlag = request.getParameter("agentViewFlag");
        String viewAgentFlagCookie = cookie.getValue(CookieEnums._VIEW.getName(), CookieEnums._VIEW_AGENT_FLAG.getName(), true);
        String userAgentId = cookie.getValue(CookieEnums._SITE.getName(), CookieEnums._SITE_REAL_AGENT_ID.getName(), true);

        for (AgentViewIdEnum enums : AgentViewIdEnum.values()) {
            // 错误页
            if(enums.getCode().equals(agentViewFlag) || enums.getCode().equals(viewAgentFlagCookie)) {
                try {
                    response.sendRedirect(GlobEnv.get("gen.server.web") + "/error/404.html");
                    return;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        if(CollectionUtils.isEmpty(agentIdList)) {
            CommunicateBusiness communicateBusiness = (CommunicateBusiness) SpringBeanUtil.getBean("communicateBusiness");
            ReqMsg_AgentViewConfig_GetAgentIds req = new ReqMsg_AgentViewConfig_GetAgentIds();
            ResMsg_AgentViewConfig_GetAgentIds res = (ResMsg_AgentViewConfig_GetAgentIds) communicateBusiness.handleMsg(req);
            agentIdList = res.getAgentIds();
        }

        if(StringUtil.isBlank(viewAgentFlagCookie)) {
            // 1. cookie不存在钱报||柯桥日报等标识
            if(StringUtil.isNotBlank(agentViewFlag)) {
                // 1.1 参数存在柯桥|钱报，刷新cookie
                agentViewFlag = StringUtil.replace(agentViewFlag, "undefined", "");
                if(agentIdList.contains(Integer.parseInt(agentViewFlag))) {
                    cookie.setValue(CookieEnums._VIEW.getName(), CookieEnums._VIEW_AGENT_FLAG.getName(), agentViewFlag, true);
                } else {
                    // 1.1 参数不存在柯桥|钱报，刷新cookie为钱报
                    agentViewFlag = Constants.AGENT_VIEW_ID_QB;
                    cookie.setValue(CookieEnums._VIEW.getName(), CookieEnums._VIEW_AGENT_FLAG.getName(), Constants.AGENT_VIEW_ID_QB, true);
                }
            } else {
                // 1.2 不存在flag：默认178
                agentViewFlag = Constants.AGENT_VIEW_ID_QB;
                cookie.setValue(CookieEnums._VIEW.getName(), CookieEnums._VIEW_AGENT_FLAG.getName(), Constants.AGENT_VIEW_ID_QB, true);
            }
            cookie.save(response, CookieEnums._VIEW.getName(), null, "/", -1, true);
        } else {
            // 2. 存在cookie
            if(StringUtil.isBlank(userId)) {
                // 2.1 参数存在flag
                agentViewFlag = saveCookie(agentViewFlag,viewAgentFlagCookie,cookie,response);
            } else {
                if(StringUtil.isNotBlank(userAgentId)) {
                    userAgentId = StringUtil.replace(userAgentId, "undefined", "");
                    if(agentIdList.contains(Integer.parseInt(userAgentId))) {
                        // 当前用户是钱报|柯桥用户，则刷新cookie值
                        cookie.setValue(CookieEnums._VIEW.getName(), CookieEnums._VIEW_AGENT_FLAG.getName(), userAgentId, true);
                        cookie.save(response, CookieEnums._VIEW.getName(), null, "/", -1, true);
                        agentViewFlag = userAgentId;
                    } else {
                        // 其他渠道用户
                        agentViewFlag = saveCookie(agentViewFlag,viewAgentFlagCookie,cookie,response);
                    }
                } else {
                    // 普通用户
                    agentViewFlag =  saveCookie(agentViewFlag,viewAgentFlagCookie,cookie,response);
                }
            }
        }
        request.setAttribute("agentViewFlag", agentViewFlag);
    }

    /**
     *
     * @param agentViewFlag
     * @param viewAgentFlagCookie
     * @param cookie
     * @param response
     * @return
     */
    private String saveCookie(String agentViewFlag, String viewAgentFlagCookie, CookieManager cookie, HttpServletResponse response) {
        try {
            if(StringUtil.isNotBlank(agentViewFlag)) {
                // 存在钱报柯桥参数，则直接刷新cookie值
                agentViewFlag = StringUtil.replace(agentViewFlag, "undefined", "");
                if(agentIdList.contains(Integer.parseInt(agentViewFlag))) {
                    cookie.setValue(CookieEnums._VIEW.getName(), CookieEnums._VIEW_AGENT_FLAG.getName(), agentViewFlag, true);
                    cookie.save(response, CookieEnums._VIEW.getName(), null, "/", -1, true);
                } else {
                    // 不存在钱报柯桥参数，直接取cookie值
                    agentViewFlag = viewAgentFlagCookie;
                }
            } else {
                // 不存在钱报柯桥参数，直接取cookie值
                agentViewFlag = viewAgentFlagCookie;
            }
        } catch (Exception e) {
            agentViewFlag = viewAgentFlagCookie;
            e.printStackTrace();
        }
        return agentViewFlag;
    }

}
