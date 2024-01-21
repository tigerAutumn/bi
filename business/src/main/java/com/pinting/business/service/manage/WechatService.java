/**
 * Wenzi.com Inc.
 * Copyright (c) 2015-2025 All Rights Reserved.
 */
package com.pinting.business.service.manage;

import java.util.List;

/**
 * 
 * @author HuanXiong
 * @version $Id: WechatService.java, v 0.1 2016-3-29 下午4:16:11 HuanXiong Exp $
 */
public interface WechatService {
    
    /**
     * 批量获取用户信息（openID）
     */
    public List<String> getUserOpenIdList();
    
}
