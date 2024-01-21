/**
 * Wenzi.com Inc.
 * Copyright (c) 2015-2025 All Rights Reserved.
 */
package com.pinting.cfcatransfer.bigangwan.service.impl.process.impl;

import javax.xml.namespace.QName;

import org.apache.axis2.addressing.EndpointReference;
import org.apache.axis2.client.Options;
import org.apache.axis2.rpc.client.RPCServiceClient;
import org.apache.axis2.transport.http.HTTPConstants;
import org.springframework.stereotype.Service;

import com.pinting.cfcatransfer.bigangwan.service.impl.process.SealCoreService;
import com.pinting.cfcatransfer.bigangwan.util.SealCommunicationConfig;
import com.pinting.cfcatransfer.bigangwan.util.SealHostCheckFactory;


/**
 * 
 * @author Baby shark love blowing wind
 * @version $Id: SealServiceClientImpl.java, v 0.1 2015-9-15 下午1:34:21 BabyShark Exp $
 */
@Service("sealCoreService")
public class SealCoreServiceImpl implements SealCoreService {

    /**
     * 普通制章
     * @param pfx
     * @param pfxPassword
     * @param image
     * @param sealInfoXML
     * @return
     * @throws Exception
     */
    public String makeSeal(byte[] pfx, String pfxPassword, byte[] image, String sealInfoXML)
                                                                                            throws Exception {
        return (String) sendAndGetResponse(SealCommunicationConfig.MAKE_SEAL_SERVICE, "makeSeal",
            new Object[] { pfx, pfxPassword, image, sealInfoXML }, String.class);
    }

    /**
     * 制人名章
     * @param pfx
     * @param pfxPassword
     * @param sealInfoXML
     * @return
     * @throws Exception
     */
    public String makeNamedSeal(byte[] pfx, String pfxPassword, String sealInfoXML)
                                                                                   throws Exception {
        return (String) sendAndGetResponse(SealCommunicationConfig.MAKE_SEAL_SERVICE,
            "makeNamedSeal", new Object[] { pfx, pfxPassword, sealInfoXML }, String.class);
    }

    /**
     * 更新普通印章
     * @param pfx
     * @param pfxPassword
     * @param image
     * @param sealInfoXML
     * @return
     * @throws Exception
     */
    public String updateSeal(byte[] pfx, String pfxPassword, byte[] image, String sealInfoXML)
                                                                                              throws Exception {
        return (String) sendAndGetResponse(SealCommunicationConfig.MAKE_SEAL_SERVICE, "updateSeal",
            new Object[] { pfx, pfxPassword, image, sealInfoXML }, String.class);
    }

    /**
     * 更新人名章
     * @param pfx
     * @param pfxPassword
     * @param sealInfoXML
     * @return
     * @throws Exception
     */
    public String updateNamedSeal(byte[] pfx, String pfxPassword, String sealInfoXML)
                                                                                     throws Exception {
        return (String) sendAndGetResponse(SealCommunicationConfig.MAKE_SEAL_SERVICE,
            "updateNamedSeal", new Object[] { pfx, pfxPassword, sealInfoXML }, String.class);
    }

    /**
     * PDF自动化签章
     * 
     * @param pdfData
     * @param sealStrategyXML
     * @return
     * @throws Exception
     */
    public byte[] sealAutoPdf(byte[] pdf, String sealStrategyXML) throws Exception {
        return (byte[]) sendAndGetResponse(SealCommunicationConfig.PDF_SEAL_SERVICE, "sealAutoPdf",
            new Object[] { pdf, sealStrategyXML }, byte[].class);
    }

    /**
     * PDF自动化骑缝章签章
     * @param pdf
     * @param crossSealStrategyXML
     * @return
     * @throws Exception
     */
    public byte[] sealAutoCrossPdf(byte[] pdf, String crossSealStrategyXML) throws Exception {
        return (byte[]) sendAndGetResponse(SealCommunicationConfig.PDF_SEAL_SERVICE,
            "sealAutoCrossPdf", new Object[] { pdf, crossSealStrategyXML }, byte[].class);
    }

    /**
     * PDF业务数据自动化合成并签章
     * @param pdf
     * @param businessXML
     * @param sealStrategyXML
     * @return
     * @throws Exception
     */
    public byte[] sealAutoSynthesizedBusinessPdf(byte[] pdf, String businessXML,
                                                 String sealStrategyXML) throws Exception {
        return (byte[]) sendAndGetResponse(SealCommunicationConfig.PDF_SEAL_SERVICE,
            "sealAutoSynthesizedBusinessPdf", new Object[] { pdf, businessXML, sealStrategyXML },
            byte[].class);
    }

    /**
     * PDF添加水印
     * @param pdf
     * @param waterMarkStrategyXML
     * @return
     * @throws Exception
     */
    public byte[] addWaterMark2Pdf(byte[] pdf, String waterMarkStrategyXML) throws Exception {
        return (byte[]) sendAndGetResponse(SealCommunicationConfig.PDF_SEAL_SERVICE,
            "addWaterMark2Pdf", new Object[] { pdf, waterMarkStrategyXML }, byte[].class);
    }

    /**
     * WEB签章
     * @param sourceBase64
     * @param sealStrategyXml
     * @return
     * @throws Exception
     */
    public byte[] signWebSeal(String sourceBase64, String sealStrategyXml) throws Exception {
        return (byte[]) sendAndGetResponse(SealCommunicationConfig.WEB_SEAL_SERVICE, "signWebSeal",
            new Object[] { sourceBase64, sealStrategyXml }, byte[].class);
    }

    /**
     * 
     * @param sealedPdf
     * @param verifyStrategyXML
     * @return
     * @throws Exception
     */
    public String verifyPdfSeal(byte[] sealedPdf, String verifyStrategyXML) throws Exception {
        return (String) sendAndGetResponse(SealCommunicationConfig.PDF_SEAL_SERVICE,
            "verifyPdfSeal", new Object[] { sealedPdf, verifyStrategyXML }, String.class);
    }

    /**
     * Web验章
     * @param webSealSource
     * @param sourceBase64
     * @param verifyStrategyXML
     * @return
     * @throws Exception
     */
    public String verifyWebSeal(String webSealSource, String sourceBase64, String verifyStrategyXML)
                                                                                                    throws Exception {
        return (String) sendAndGetResponse(SealCommunicationConfig.WEB_SEAL_SERVICE,
            "verifyWebSeal", new Object[] { webSealSource, sourceBase64, verifyStrategyXML },
            String.class);
    }

    /**
     * PDF业务数据自动化合成
     * @param pdf
     * @param businessXML
     * @return
     * @throws Exception
     */
    public byte[] synthesizeAutoBusinessPdf(byte[] pdf, String businessXML) throws Exception {
        return (byte[]) sendAndGetResponse(SealCommunicationConfig.BUSINESS_SEAL_SERVICE,
            "synthesizeAutoBusinessPdf", new Object[] { pdf, businessXML }, byte[].class);
    }

    /**
     * P1签名
     * @param source
     * @param signStrategyXML
     * @return
     * @throws Exception
     */
    public String p1Sign(byte[] source, String signStrategyXML) throws Exception {
        return (String) sendAndGetResponse(SealCommunicationConfig.ASSIST_SEAL_SERVICE, "p1Sign",
            new Object[] { source, signStrategyXML }, String.class);
    }

    /**
     * P7分离式签名
     * @param source
     * @param signStrategyXML
     * @return
     * @throws Exception
     */
    public String p7SignDetached(byte[] source, String signStrategyXML) throws Exception {
        return (String) sendAndGetResponse(SealCommunicationConfig.ASSIST_SEAL_SERVICE,
            "p7SignDetached", new Object[] { source, signStrategyXML }, String.class);
    }

    /**
     * P7分离式验签
     * @param signatureBase64
     * @param source
     * @param verifyStrategyXML
     * @return
     * @throws Exception
     */
    public String p7VerifyDetached(String signatureBase64, byte[] source, String verifyStrategyXML)
                                                                                                   throws Exception {
        return (String) sendAndGetResponse(SealCommunicationConfig.ASSIST_SEAL_SERVICE,
            "p7VerifyDetached", new Object[] { signatureBase64, source, verifyStrategyXML },
            String.class);
    }

    /**
     * 获取印章信息
     * @param sealCode
     * @param certDN
     * @param certSN
     * @return
     * @throws Exception
     */
    public String getSealInfo(String sealCode) throws Exception {
        return (String) sendAndGetResponse(SealCommunicationConfig.ASSIST_SEAL_SERVICE,
            "getSealInfo", new Object[] { sealCode }, String.class);
    }

    /**
     * 绑定印章
     * @param bindSealXML
     * @return
     * @throws Exception
     */
    public String bindSeal(String bindSealXML) throws Exception {
        return (String) sendAndGetResponse(SealCommunicationConfig.ASSIST_SEAL_SERVICE, "bindSeal",
            new Object[] { bindSealXML }, String.class);
    }

    /**
     * 自助生成图片
     * @param imageStrategyXML
     * @return
     * @throws Exception
     */
    public String autoGenerateImage(String imageStrategyXML) throws Exception {
        return (String) sendAndGetResponse(SealCommunicationConfig.ASSIST_SEAL_SERVICE,
            "autoGenerateImage", new Object[] { imageStrategyXML }, String.class);
    }

    /**
     * 
     * @param serviceName
     * @param methedName
     * @param opAddEntryArgs
     * @param returnType
     * @return
     * @throws Exception
     */
    private Object sendAndGetResponse(String serviceName, String methedName,
                                      Object[] opAddEntryArgs, Class<?> returnType)
                                                                                   throws Exception {
        String endPoint = SealHostCheckFactory.getValidHost(serviceName);
        if ("".equals(endPoint)) {
            throw new Exception("没有可用的服务器");
        }
        RPCServiceClient rpcServiceClient = new RPCServiceClient();
        Options options = rpcServiceClient.getOptions();
        EndpointReference targetEPR = new EndpointReference(endPoint);
        options.setTo(targetEPR);
        options.setProperty(HTTPConstants.REUSE_HTTP_CLIENT, Boolean.TRUE);
        if (SealCommunicationConfig.timeOut > 0) {
            options.setTimeOutInMilliSeconds(1000 * SealCommunicationConfig.timeOut);
            options.setProperty(HTTPConstants.CONNECTION_TIMEOUT,
                Integer.valueOf("" + 1000 * SealCommunicationConfig.timeOut));
            options.setProperty(HTTPConstants.SO_TIMEOUT,
                Integer.valueOf("" + 1000 * SealCommunicationConfig.timeOut));
        }
        // 指定makeSeal方法返回值的数据类型的Class对象
        Class<?>[] classes = new Class[] { returnType };
        // 指定要调用的makeSeal方法及WSDL文件的命名空间
        QName opAddEntry = new QName(SealCommunicationConfig.nameSpace, methedName);
        // 调用makeSeal方法并输出该方法的返回值
        return rpcServiceClient.invokeBlocking(opAddEntry, opAddEntryArgs, classes)[0];

    }

}
