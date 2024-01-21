/**
 * Wenzi.com Inc.
 * Copyright (c) 2015-2025 All Rights Reserved.
 */
package com.pinting.cfcatransfer.bigangwan.service.impl.process;

/**
 * 
 * @author Baby shark love blowing wind
 * @version $Id: SealCoreService.java, v 0.1 2015-9-15 下午3:09:04 BabyShark Exp $
 */
public interface SealCoreService {
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
                                                                                            throws Exception;

    /**
     * 制人名章
     * @param pfx
     * @param pfxPassword
     * @param sealInfoXML
     * @return
     * @throws Exception
     */
    public String makeNamedSeal(byte[] pfx, String pfxPassword, String sealInfoXML)
                                                                                   throws Exception;

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
                                                                                              throws Exception;

    /**
     * 更新人名章
     * @param pfx
     * @param pfxPassword
     * @param sealInfoXML
     * @return
     * @throws Exception
     */
    public String updateNamedSeal(byte[] pfx, String pfxPassword, String sealInfoXML)
                                                                                     throws Exception;

    /**
     * PDF自动化签章
     * 
     * @param pdfData
     * @param sealStrategyXML
     * @return
     * @throws Exception
     */
    public byte[] sealAutoPdf(byte[] pdf, String sealStrategyXML) throws Exception;

    /**
     * PDF自动化骑缝章签章
     * @param pdf
     * @param crossSealStrategyXML
     * @return
     * @throws Exception
     */
    public byte[] sealAutoCrossPdf(byte[] pdf, String crossSealStrategyXML) throws Exception;

    /**
     * PDF业务数据自动化合成并签章
     * @param pdf
     * @param businessXML
     * @param sealStrategyXML
     * @return
     * @throws Exception
     */
    public byte[] sealAutoSynthesizedBusinessPdf(byte[] pdf, String businessXML,
                                                 String sealStrategyXML) throws Exception;

    /**
     * PDF添加水印
     * @param pdf
     * @param waterMarkStrategyXML
     * @return
     * @throws Exception
     */
    public byte[] addWaterMark2Pdf(byte[] pdf, String waterMarkStrategyXML) throws Exception;

    /**
     * WEB签章
     * @param sourceBase64
     * @param sealStrategyXml
     * @return
     * @throws Exception
     */
    public byte[] signWebSeal(String sourceBase64, String sealStrategyXml) throws Exception;

    /**
     * 
     * @param sealedPdf
     * @param verifyStrategyXML
     * @return
     * @throws Exception
     */
    public String verifyPdfSeal(byte[] sealedPdf, String verifyStrategyXML) throws Exception;

    /**
     * Web验章
     * @param webSealSource
     * @param sourceBase64
     * @param verifyStrategyXML
     * @return
     * @throws Exception
     */
    public String verifyWebSeal(String webSealSource, String sourceBase64, String verifyStrategyXML)
                                                                                                    throws Exception;

    /**
     * PDF业务数据自动化合成
     * @param pdf
     * @param businessXML
     * @return
     * @throws Exception
     */
    public byte[] synthesizeAutoBusinessPdf(byte[] pdf, String businessXML) throws Exception;

    /**
     * P1签名
     * @param source
     * @param signStrategyXML
     * @return
     * @throws Exception
     */
    public String p1Sign(byte[] source, String signStrategyXML) throws Exception;

    /**
     * P7分离式签名
     * @param source
     * @param signStrategyXML
     * @return
     * @throws Exception
     */
    public String p7SignDetached(byte[] source, String signStrategyXML) throws Exception;

    /**
     * P7分离式验签
     * @param signatureBase64
     * @param source
     * @param verifyStrategyXML
     * @return
     * @throws Exception
     */
    public String p7VerifyDetached(String signatureBase64, byte[] source, String verifyStrategyXML)
                                                                                                   throws Exception;

    /**
     * 获取印章信息
     * @param sealCode
     * @param certDN
     * @param certSN
     * @return
     * @throws Exception
     */
    public String getSealInfo(String sealCode) throws Exception;

    /**
     * 绑定印章
     * @param bindSealXML
     * @return
     * @throws Exception
     */
    public String bindSeal(String bindSealXML) throws Exception;

    /**
     * 自助生成图片
     * @param imageStrategyXML
     * @return
     * @throws Exception
     */
    public String autoGenerateImage(String imageStrategyXML) throws Exception;
}
