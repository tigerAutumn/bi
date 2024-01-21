/**
 * Wenzi.com Inc.
 * Copyright (c) 2015-2025 All Rights Reserved.
 */
package com.pinting.gateway.pay19.out.service;

import com.pinting.gateway.pay19.out.model.req.CheckAccountFileReq;
import com.pinting.gateway.pay19.out.model.resp.CheckAccountFileResp;

/**
 * 19付ftp下载
 * @author Baby shark love blowing wind
 * @version $Id: FTP4AccountsServiceClient.java, v 0.1 2015-8-31 下午8:06:51 BabyShark Exp $
 */
public interface FTP4AccountsServiceClient {

    /**
     * 对账文件下载及解析获取
     * 
     * @param req 对账日期
     * @return
     */
    CheckAccountFileResp downloadCheckAccountFile(CheckAccountFileReq req);

}
