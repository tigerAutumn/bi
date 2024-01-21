package com.pinting.util;

import com.pinting.core.cookie.CookieManager;
import com.pinting.core.util.StringUtil;
import com.pinting.site.common.enums.CookieEnums;
import com.pinting.site.weichat.util.WeChatUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * Author:      cyb
 * Date:        2016/10/29
 * Description: 微信分享
 */
public class WeChatShareUtil {

    /**
     *
     * @param channel       访问端口。micro2.0-H5；gen2.0-PC；gen178-PC_178
     * @param link          链接
     * @param shareTitle    分享标题
     * @param shareContent  分享内容
     * @param logo          分享logo
     * @param isCustom      是否定制分享文案，图标
     * @param request
     * @param model
     * @param weChatUtil
     */
    public static void share(String channel, String link, String shareTitle, String shareContent, String logo, boolean isCustom, HttpServletRequest request, Map<String, Object> model, WeChatUtil weChatUtil) {
        if(Constants.CHANNEL_MICRO.equals(channel)){
            String qianbao = request.getParameter("qianbao");
            if (StringUtil.isNotBlank(qianbao)
                    && Constants.USER_AGENT_QIANBAO.equals(qianbao) 
                    && (!link.contains("?"))) {
                model.put("qianbao", Constants.USER_AGENT_QIANBAO);
                link += "?qianbao=qianbao";
                CookieManager manager = new CookieManager(request);
                String viewAgentFlagCookie = manager.getValue(CookieEnums._VIEW.getName(), CookieEnums._VIEW_AGENT_FLAG.getName(), true);
                if(StringUtil.isNotBlank(viewAgentFlagCookie)){
                    link += "&agentViewFlag="+viewAgentFlagCookie;
                }
            }
            // 分享
            Map<String, String> sign = weChatUtil.createAuth(request);
            sign.put("link", link);
            if (isCustom) {
                // 是否定制分享文案，图标
                sign.put("share_title", shareTitle);
                sign.put("share_content", shareContent);
                sign.put("share_logo", logo);
                model.put("source", "all");
            }
            model.put("weichat", sign);
        }
    }

}
