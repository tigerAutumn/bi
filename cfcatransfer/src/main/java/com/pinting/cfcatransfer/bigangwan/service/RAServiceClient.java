/**
 * Wenzi.com Inc.
 * Copyright (c) 2015-2025 All Rights Reserved.
 */
package com.pinting.cfcatransfer.bigangwan.service;

import cfca.ra.common.vo.request.CertServiceRequestVO;
import cfca.ra.common.vo.response.CertServiceResponseVO;

/**
 * 
 * @author Baby shark love blowing wind
 * @version $Id: RAServiceClient.java, v 0.1 2015-9-14 下午5:46:06 BabyShark Exp $
 */
public interface RAServiceClient {

    /**
     * 证书申请和下载
     * 
     * @param certServiceRequestVO
     * @return
     */
    public CertServiceResponseVO applyAndDownloadCert(CertServiceRequestVO certServiceRequestVO);

}
