/**
 * Wenzi.com Inc.
 * Copyright (c) 2015-2025 All Rights Reserved.
 */
package com.pinting.cfcatransfer.bigangwan.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.pinting.cfcatransfer.bigangwan.service.RAServiceClient;
import com.pinting.cfcatransfer.bigangwan.util.RACommunicationConfig;

import cfca.kt.vo.util.XmlUtil2;
import cfca.ra.common.vo.request.CertServiceRequestVO;
import cfca.ra.common.vo.response.CertServiceResponseVO;
import cfca.ra.toolkit.CFCARAClient;

/**
 * 
 * @author Baby shark love blowing wind
 * @version $Id: RAServiceClientImpl.java, v 0.1 2015-9-15 上午10:51:00 BabyShark Exp $
 */
@Service("raServiceClient")
public class RAServiceClientImpl implements RAServiceClient {
    private static Logger log = LoggerFactory.getLogger(RAServiceClientImpl.class);

    /** 
     * @see com.gateway.common.service.integration.cfca.service.RAServiceClient#applyAndDownloadCert(cfca.ra.common.vo.request.CertServiceRequestVO)
     */
    @Override
    public CertServiceResponseVO applyAndDownloadCert(CertServiceRequestVO certServiceRequestVO) {
        CertServiceResponseVO certServiceResponseVO = null;
        try {
            CFCARAClient client = RACommunicationConfig
                .getCFCARAClient(RACommunicationConfig.DEFAULT_CLIENT_TYPE);
            certServiceRequestVO.setTxCode("1101");
            String requestXml = XmlUtil2.vo2xml(certServiceRequestVO, "Request");
            log.info("CFCA_RA_1101请求报文：" + "\n" + requestXml);
            certServiceResponseVO = (CertServiceResponseVO) client.process(certServiceRequestVO);
            String responseXml = XmlUtil2.vo2xml(certServiceResponseVO, "Response");
            log.info("CFCA_RA_1101响应报文：" + "\n" + responseXml);
            if (!"0000".equals(certServiceResponseVO.getResultCode())) {
                log.info("证书申请及下载执行失败：" + certServiceResponseVO.getResultCode() + "|"
                         + certServiceResponseVO.getResultMessage());
                return null;
            }
        } catch (Exception e) {
            log.info("证书申请及下载执行失败：", e);
            return null;
        }
        return certServiceResponseVO;
    }

}
