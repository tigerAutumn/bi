/**
 * Wenzi.com Inc.
 * Copyright (c) 2015-2025 All Rights Reserved.
 */
package com.pinting.cfcatransfer.bigangwan.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.pinting.cfcatransfer.bigangwan.service.KTServiceClient;
import com.pinting.cfcatransfer.bigangwan.util.KTCommunicationConfig;

import cfca.kt.toolkit.ClientContext;
import cfca.kt.vo.KeyPairRequestVO;
import cfca.kt.vo.KeyPairResponseVO;
import cfca.kt.vo.PFXRequestVO;
import cfca.kt.vo.PFXResponseVO;
import cfca.kt.vo.util.XmlUtil2;


/**
 * 
 * @author Baby shark love blowing wind
 * @version $Id: KTServiceClientImpl.java, v 0.1 2015-9-14 下午4:41:17 BabyShark Exp $
 */
@Service("ktServiceClient")
public class KTServiceClientImpl implements KTServiceClient {
    private static Logger log = LoggerFactory.getLogger(KTServiceClientImpl.class);

    /** 
     * @see com.gateway.common.service.integration.cfca.service.KTServiceClient#createP10(cfca.kt.vo.KeyPairRequestVO)
     */
    @Override
    public KeyPairResponseVO createKeyPair(KeyPairRequestVO requestVO) {
        ClientContext context = KTCommunicationConfig.getCFCARAClientContext();
        KeyPairResponseVO responseVO = null;
        try {
            requestVO.setTxType("1001");
            String requestXml = XmlUtil2.vo2xml(requestVO, "Request");
            log.info("CFCA_KT_1001请求报文：" + "\n" + requestXml);
            responseVO = context.tx1001(requestVO);
            String responseXml = XmlUtil2.vo2xml(responseVO, "Response");
            log.info("CFCA_KT_1001响应报文：" + "\n" + responseXml);
            if (!"2000".equals(responseVO.getResultCode())) {
                log.info("获取P10失败：" + responseVO.getResultCode() + "|"
                         + responseVO.getResultMessage());
                return null;
            }
        } catch (Exception e) {
            log.info("获取P10失败：", e);
            return null;
        }
        return responseVO;
    }

    /** 
     * @see com.gateway.common.service.integration.cfca.service.KTServiceClient#createPFX(cfca.kt.vo.PFXRequestVO)
     */
    @Override
    public PFXResponseVO createPFX(PFXRequestVO requestVO) {
        ClientContext context = KTCommunicationConfig.getCFCARAClientContext();
        PFXResponseVO responseVO = null;
        try {
            requestVO.setTxType("1002");
            String requestXml = XmlUtil2.vo2xml(requestVO, "Request");
            log.info("CFCA_KT_1002请求报文：" + "\n" + requestXml);
            responseVO = context.tx1002(requestVO);
            String responseXml = XmlUtil2.vo2xml(responseVO, "Response");
            log.info("CFCA_KT_1002响应报文：" + "\n" + responseXml);
            if (!"2000".equals(responseVO.getResultCode())) {
                log.info("获取PFX证书失败：" + responseVO.getResultCode() + "|"
                         + responseVO.getResultMessage());
                return null;
            }
        } catch (Exception e) {
            log.info("获取PFX证书失败：", e);
            return null;
        }
        return responseVO;
    }
}
