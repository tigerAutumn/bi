/**
 * Wenzi.com Inc.
 * Copyright (c) 2015-2025 All Rights Reserved.
 */
package com.pinting.business.service.manage.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Service;
import com.pinting.business.service.manage.WechatService;
import com.pinting.business.util.WeChatUtil;
import com.pinting.core.util.StringUtil;

/**
 * 微信获取openID
 * @author HuanXiong
 * @version $Id: WechatServiceImpl.java, v 0.1 2016-3-29 下午4:16:35 HuanXiong Exp $
 */
@Service
public class WechatServiceImpl implements WechatService {
    
    /** 
     * @see com.pinting.business.service.manage.WechatService#getUserOpenIdList()
     */
    @Override
    public List<String> getUserOpenIdList() {
        String access_token = WeChatUtil.getAccessToken();
        List<String> openIds = new ArrayList<String>();
        try {
            getUserOpenIdListRecursion(access_token, "", openIds);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return openIds;
    }
    
    /**
     * 递归获取openid列表
     * @param access_token
     * @param openid
     * @return
     * @throws IOException
     */
    private static JSONObject getUserOpenIdListRecursion(String access_token, String openid, List<String> openIds) throws IOException {
        JSONObject obj = null;
        String userList = null;
        if(StringUtil.isBlank(openid)) {
            userList = WeChatUtil.sendGet("https://api.weixin.qq.com/cgi-bin/user/get?access_token=" + access_token + "&next_openid", "", true, "UTF-8");
        } else {
            userList = WeChatUtil.sendGet("https://api.weixin.qq.com/cgi-bin/user/get?access_token=" + access_token + "&next_openid=" + openid, "", true, "UTF-8");
        }
        obj = JSONObject.fromObject(userList);
        String next_openid = (String) obj.get("next_openid");
        if(null != obj.get("data")) {
            JSONObject openIdList = JSONObject.fromObject(obj.get("data"));
            @SuppressWarnings("unchecked")
            List<String> list = (List<String>) openIdList.get("openid");
            for (String openID : list) {
                openIds.add(openID);
            }
        }
        if(StringUtil.isBlank(next_openid)) {
            return obj;
        }
        return getUserOpenIdListRecursion(access_token, next_openid, openIds);
    }

}
