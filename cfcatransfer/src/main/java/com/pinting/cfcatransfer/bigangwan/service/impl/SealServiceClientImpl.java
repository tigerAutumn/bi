/**
 * Wenzi.com Inc.
 * Copyright (c) 2015-2025 All Rights Reserved.
 */
package com.pinting.cfcatransfer.bigangwan.service.impl;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pinting.cfcatransfer.bigangwan.model.cfca.MakeNamedSealReq;
import com.pinting.cfcatransfer.bigangwan.model.cfca.MakeSealReq;
import com.pinting.cfcatransfer.bigangwan.model.cfca.SealAutoPdfReq;
import com.pinting.cfcatransfer.bigangwan.service.SealServiceClient;
import com.pinting.cfcatransfer.bigangwan.service.impl.process.SealCoreService;
import com.pinting.cfcatransfer.bigangwan.util.CAFileUtil;
import com.pinting.cfcatransfer.bigangwan.util.CAStringUtil;
import com.pinting.core.util.StringUtil;


/**
 * 
 * @author Baby shark love blowing wind
 * @version $Id: SealServiceClientImpl.java, v 0.1 2015-9-15 下午1:34:21 BabyShark Exp $
 */
@Service("sealServiceClient")
public class SealServiceClientImpl implements SealServiceClient {
    private static Logger   log = LoggerFactory.getLogger(SealServiceClientImpl.class);
    @Autowired
    private SealCoreService sealCoreService;

    /** 
     * @see com.gateway.common.service.integration.cfca.service.SealServiceClient#sealAutoPdf(com.gateway.common.service.integration.cfca.model.req.SealAutoPdfReq)
     */
    @Override
    public boolean sealAutoPdf(SealAutoPdfReq req) {
        try {
            byte[] pdf = CAFileUtil.getBytesFromFile(new File(req.getPath()));

            String sealStrategyXML = "<Request>" + "<Type>" + req.getType() + "</Type>"
                                     + "<SealCode>" + req.getSealCode()
                                     + "</SealCode><SealPassword>" + req.getSealPassword()
                                     + "</SealPassword><CertDN>" + req.getCertDN() + "</CertDN>"
                                     + "<CertSN>" + req.getCertSN() + "</CertSN><Page>"
                                     + req.getPage() + "</Page><SealPerson>" + req.getSealPerson()
                                     + "</SealPerson><SealLocation>" + req.getSealLocation()
                                     + "</SealLocation>" + "<SealResson>" + req.getSealResson()
                                     + "</SealResson><LX>" + req.getlX() + "</LX><LY>"
                                     + req.getlY() + "</LY><Keyword>" + req.getKeyword()
                                     + "</Keyword>" + "<LocationStyle>" + req.getLocationStyle()
                                     + "</LocationStyle><OffsetX>" + req.getOffsetX()
                                     + "</OffsetX><OffsetY>" + req.getOffsetY()
                                     + "</OffsetY><CertificationLevel>"
                                     + req.getCertificationLevel() + "</CertificationLevel>"
                                     + "</Request>";

            log.info("pdf自动签章请求报文：" + sealStrategyXML);
            byte[] resultData = sealCoreService.sealAutoPdf(pdf, sealStrategyXML);

            if (CAStringUtil.checkResultDataValid(resultData)) {
                String savePath = req.getSealedFileName();
                CAFileUtil.wirteDataToFile(savePath, resultData);
            } else {
                log.info("pdf自动签章失败：" + new String(resultData));
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /** 
     * @see com.gateway.common.service.integration.cfca.service.SealServiceClient#makeSeal(com.gateway.common.service.integration.cfca.model.req.MakeSealReq)
     */
    @Override
    public boolean makeSeal(MakeSealReq req) {

        try {
            byte[] pfx = CAFileUtil.getBytesFromFile(new File(req.getPfxPath()));
            byte[] image = "".getBytes();
            if (!StringUtil.isEmpty(req.getImagePath())) {
                image = CAFileUtil.getBytesFromFile(new File(req.getImagePath()));
            }

            String sealInfoXML = "<Request>" + "<SealPerson>" + req.getSealPerson()
                                 + "</SealPerson>" + "<SealOrg>" + req.getSealOrg() + "</SealOrg>"
                                 + "<SealName>" + req.getSealName() + "</SealName>" + "<SealCode>"
                                 + req.getSealCode() + "</SealCode>" + "<SealType>"
                                 + req.getSealType() + "</SealType>" + "<SealPassword>"
                                 + req.getSealPassword() + "</SealPassword>" + "<ImageCode>"
                                 + req.getImageCode() + "</ImageCode>" + "<OrgCode>"
                                 + req.getOrgCode() + "</OrgCode>" + "</Request>";
            log.info("xml:" + sealInfoXML);
            String result = sealCoreService.makeSeal(pfx, req.getPfxPassword(), image, sealInfoXML);

            if (!"200".equals(CAStringUtil.getNodeText(result, "Code"))) {
                log.error("制章失败：" + CAStringUtil.getNodeText(result, "Message"));
                return false;
            }

        } catch (Exception e) {
            log.error("制章失败......");
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /** 
     * @see com.gateway.common.service.integration.cfca.service.SealServiceClient#makeNamedSeal(com.gateway.common.service.integration.cfca.model.req.MakeNamedSealReq)
     */
    @Override
    public boolean makeNamedSeal(MakeNamedSealReq req) {
        try {
            byte[] pfx = CAFileUtil.getBytesFromFile(new File(req.getPfxPath()));

            String namedSealInfoXML = "<Request>" + "<SealPerson>" + req.getSealPerson()
                                      + "</SealPerson>" + "<SealOrg>" + req.getSealOrg()
                                      + "</SealOrg>" + "<SealName>" + req.getSealName()
                                      + "</SealName>" + "<SealCode>" + req.getSealCode()
                                      + "</SealCode>" + "<SealPassword>" + req.getSealPassword()
                                      + "</SealPassword>" + "<ImageShape>" + req.getImageShape()
                                      + "</ImageShape>" + "<ImageWidth>" + req.getImageWidth()
                                      + "</ImageWidth>" + "<ImageHeight>" + req.getImageHeight()
                                      + "</ImageHeight>" + "<OrgCode>" + req.getOrgCode()
                                      + "</OrgCode>" + "</Request>";
            log.info("xml：" + namedSealInfoXML);
            
            String result = sealCoreService.makeNamedSeal(pfx, req.getPfxPassword(),
                namedSealInfoXML);

            if (!"200".equals(CAStringUtil.getNodeText(result, "Code"))) {
                log.error("制章失败：" + CAStringUtil.getNodeText(result, "Message"));
                return false;
            }

        } catch (Exception e) {
            log.error("制章失败......");
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
