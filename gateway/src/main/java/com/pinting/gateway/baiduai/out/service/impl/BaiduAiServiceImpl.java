package com.pinting.gateway.baiduai.out.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.pinting.core.util.GlobEnvUtil;
import com.pinting.gateway.baiduai.out.model.AuthTokenResModel;
import com.pinting.gateway.baiduai.out.service.BaiduAiService;
import com.pinting.gateway.baiduai.out.util.BaiduAiHttpUtil;
import com.pinting.gateway.enums.PTMessageEnum;
import com.pinting.gateway.exception.PTMessageException;
import com.pinting.gateway.util.HttpClientUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * 百度AI开放平台service
 */
@Service
public class BaiduAiServiceImpl implements BaiduAiService {

    private static Logger log = LoggerFactory.getLogger(BaiduAiServiceImpl.class);

    public static String BAIDU_AI_APIKEY = "pV6lCiUjzC3YBVRoecstY9Gz";
    public static String BAIDU_AI_SECRETKEY = "ROLzBmADAHHICBCiduhaBrzAIMTz2SYb";

    static {
        BAIDU_AI_APIKEY = GlobEnvUtil.get("baidu.ai.apikey"); // 官网获取的 API Key
        BAIDU_AI_SECRETKEY = GlobEnvUtil.get("baidu.ai.secretkey"); // 官网获取的 Secret Key
    }

    @Override
    public AuthTokenResModel getAuthToken() {
        // 获取token地址
        String getAccessTokenUrl = String.format(BaiduAiHttpUtil.AUTH_HOST, BAIDU_AI_APIKEY, BAIDU_AI_SECRETKEY);
        String accessTokenResult = HttpClientUtil.sendRequest(getAccessTokenUrl, null);

        if (StringUtils.isNotBlank(accessTokenResult)) {
            JSONObject jsonObject = JSONObject.parseObject(accessTokenResult);
            String error = jsonObject.getString("error");
            if (StringUtils.isNotBlank(error)) {
                log.info("百度AI获取token失败:{}", jsonObject.getString("error"), jsonObject.getString("error_description"));
                throw new PTMessageException(PTMessageEnum.TRANS_ERROR, "获取token失败");
            } else {
                log.info("百度AI获取token成功", jsonObject.toJSONString());
                return JSONObject.parseObject(accessTokenResult, AuthTokenResModel.class);
            }
        } else {
            throw new PTMessageException(PTMessageEnum.SYSTEM_ERROR);
        }
    }
}
