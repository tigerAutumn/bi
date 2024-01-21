package com.pinting.gateway.baiduai.out.service;

import com.pinting.gateway.baiduai.out.model.AuthTokenResModel;

/**
 * 百度AI开放平台service
 */
public interface BaiduAiService {

    /**
     * 获取百度AI开放平台，授权access_token
     *
     * @return 返回示例：
     * {
     * "access_token": "24.460da4889caad24cccdb1fea17221975.2592000.1491995545.282335-1234567",
     * "expires_in": 2592000
     * }
     */
    AuthTokenResModel getAuthToken();
}
