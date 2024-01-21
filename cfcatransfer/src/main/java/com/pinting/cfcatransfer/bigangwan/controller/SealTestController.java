/**
 * Funball.com Inc.
 * Copyright (c) 2015-2025 All Rights Reserved.
 */
package com.pinting.cfcatransfer.bigangwan.controller;

import cfca.kt.vo.KeyPairRequestVO;
import cfca.kt.vo.KeyPairResponseVO;
import cfca.kt.vo.PFXRequestVO;
import cfca.kt.vo.PFXResponseVO;
import cfca.ra.common.vo.request.CertServiceRequestVO;
import cfca.ra.common.vo.response.CertServiceResponseVO;
import com.pinting.cfcatransfer.bigangwan.model.cfca.MakeNamedSealReq;
import com.pinting.cfcatransfer.bigangwan.model.cfca.MakeSealReq;
import com.pinting.cfcatransfer.bigangwan.model.cfca.SealAutoPdfReq;
import com.pinting.cfcatransfer.bigangwan.service.KTServiceClient;
import com.pinting.cfcatransfer.bigangwan.service.RAServiceClient;
import com.pinting.cfcatransfer.bigangwan.service.SealServiceClient;
import com.pinting.cfcatransfer.bigangwan.util.CABase64;
import com.pinting.cfcatransfer.bigangwan.util.CAFileUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Baby shark love blowing wind
 * @version $Id: SealTestController.java, v 0.1 2015-6-16 上午10:32:18 BabyShark Exp $
 */
@Controller
@RequestMapping("/test")
public class SealTestController {
    private static Logger     log = LoggerFactory.getLogger(SealTestController.class);
    @Autowired
    private KTServiceClient   ktServiceClient;
    @Autowired
    private RAServiceClient   raServiceClient;
    @Autowired
    private SealServiceClient sealServiceClient;

    @RequestMapping("/sealTest/index")
    public String sealTestIndex(HttpServletRequest request, HttpServletResponse response) {
        return "/test/cfcaSealTestIndex";
    }

    @RequestMapping("kt4p10")
    public @ResponseBody
    Map<String, Object> kt4p10(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> resMap = new HashMap<String, Object>();
        KeyPairRequestVO vo = new KeyPairRequestVO();
        log.info("发起KT接口......");
        KeyPairResponseVO resVo = ktServiceClient.createKeyPair(vo);
        if (resVo != null) {
            resMap.put("TxType", resVo.getTxType());
            resMap.put("TxTime", resVo.getTxTime());
            resMap.put("ResultCode", resVo.getResultCode());
            resMap.put("ResultMessage", resVo.getResultMessage());
            resMap.put("Csr", resVo.getCsr());
            resMap.put("KeyIdentifier", resVo.getKeyIdentifier());
        }
        return resMap;
    }

    @RequestMapping("ra4cert")
    public @ResponseBody
    Map<String, Object> ra4cert(HttpServletRequest request, HttpServletResponse response,
                                String p10, String userName, String identNo) {
        Map<String, Object> resMap = new HashMap<String, Object>();
        CertServiceRequestVO vo = new CertServiceRequestVO();
        String certType = "1";
        // 客户类型
        // 个人：1 企业：2
        String customerType = "1";
        // 用户名
        //String userName = "丁一";
        // 证书主题用户名(如果这个属性有值，那组成的DN里面“用户名”那个项就是这个值)
        // String userNameInDn = "testName";
        // 用户标识 (如果这个属性有值，那组成的DN里面“证件类型+证件号码”那个项就是这个值)
        // String userIdent = "Z1234567890";
        // 证件类型
        String identType = "Z";
        // 证件号码
        //String identNo = "330683198611048888";
        // 密钥算法： 默认RSA
        // String keyAlg = "RSA";
        // 密钥长度：默认2048
        // String keyLength = "2048";
        // 证书所属机构编码
        String branchCode = "678";

        vo.setP10(p10);
        vo.setCertType(certType);
        vo.setCustomerType(customerType);
        vo.setUserName(userName);
        vo.setIdentType(identType);
        vo.setIdentNo(identNo);
        vo.setBranchCode(branchCode);

        log.info("发起RA接口......");
        CertServiceResponseVO resVo = raServiceClient.applyAndDownloadCert(vo);
        if (resVo != null) {
            resMap.put("TxCode", resVo.getTxCode());
            resMap.put("TxTime", resVo.getTxTime());
            resMap.put("ResultCode", resVo.getResultCode());
            resMap.put("ResultMessage", resVo.getResultMessage());
            resMap.put("CertStatus", resVo.getCertStatus());
            resMap.put("DN", resVo.getDn());
            resMap.put("EncryptionCert", resVo.getEncryptionCert());
            resMap.put("EncryptionPrivateKey", resVo.getEncryptionPrivateKey());
            resMap.put("EndTime", resVo.getEndTime());
            resMap.put("SequenceNo", resVo.getSequenceNo());
            resMap.put("SerialNo", resVo.getSerialNo());
            resMap.put("SignatureCert", resVo.getSignatureCert());
            resMap.put("StartTime", resVo.getStartTime());
        }
        return resMap;
    }

    @RequestMapping("kt4pfx")
    public @ResponseBody
    Map<String, Object> kt4pfx(HttpServletRequest request, HttpServletResponse response,
                               String cert, String keyid, String path, String pfxName) {
        Map<String, Object> resMap = new HashMap<String, Object>();
        PFXRequestVO vo = new PFXRequestVO();
        vo.setKeyIdentifier(keyid);
        vo.setSignatureCert(cert);
        log.info("发起KT接口......");
        PFXResponseVO resVo = ktServiceClient.createPFX(vo);
        if (resVo != null) {
            resMap.put("TxType", resVo.getTxType());
            resMap.put("TxTime", resVo.getTxTime());
            resMap.put("ResultCode", resVo.getResultCode());
            resMap.put("ResultMessage", resVo.getResultMessage());
            resMap.put("pfx", resVo.getPfxData());

            try {
                path = path + pfxName + "-" + new Date().getTime() + ".pfx";
                CAFileUtil.wirteDataToFile(path, CABase64.decode(resVo.getPfxData()));
                resMap.put("pfxPath", path);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return resMap;
    }

    @RequestMapping("makeseal")
    public @ResponseBody
    Map<String, Object> makeseal(HttpServletRequest request, HttpServletResponse response,
                                 String path, String pass, String imagePath, String hongchunCode,
                                 String hongchunPass) {
        Map<String, Object> resMap = new HashMap<String, Object>();
        MakeSealReq req = new MakeSealReq();

        String sealPerson = "杭州赞分期商务信息咨询服务有限公司";
        String sealOrg = "杭州赞分期商务信息咨询服务有限公司";
        String sealName = "杭州赞分期商务信息咨询服务有限公司";
        String sealCode = hongchunCode;
        String sealPassword = hongchunPass;
        String sealType = "0";// 印章类型0：企业 1：个人
        String imageCode = "";

        req.setImageCode(imageCode);
        req.setImagePath(imagePath);
        req.setPfxPassword(pass);
        req.setPfxPath(path);
        req.setSealCode(sealCode);
        req.setSealName(sealName);
        req.setSealOrg(sealOrg);
        req.setSealPassword(sealPassword);
        req.setSealPerson(sealPerson);
        req.setSealType(sealType);
        log.info("发起Seal接口......");
        boolean flag = sealServiceClient.makeSeal(req);
        if (flag) {
            resMap.put("isSucc", "制章成功！");
        } else {
            resMap.put("isSucc", "制章失败！");
        }
        return resMap;
    }

    @RequestMapping("makenamedseal")
    public @ResponseBody
    Map<String, Object> makenamedseal(HttpServletRequest request, HttpServletResponse response,
                                      String path, String pass, String perCode, String perPass,
                                      String sealName) {
        Map<String, Object> resMap = new HashMap<String, Object>();
        MakeNamedSealReq req = new MakeNamedSealReq();

        String sealPerson = "杭州币港湾科技有限公司";
        String sealOrg = "杭州币港湾科技有限公司";
        String sealCode = perCode;
        String sealPassword = perPass;

        req.setPfxPassword(pass);
        req.setPfxPath(path);
        req.setSealCode(sealCode);
        req.setSealName(sealName);
        req.setSealOrg(sealOrg);
        req.setSealPassword(sealPassword);
        req.setSealPerson(sealPerson);

        log.info("发起Seal接口......");
        boolean flag = sealServiceClient.makeNamedSeal(req);
        if (flag) {
            resMap.put("isSucc", "制章成功！");
        } else {
            resMap.put("isSucc", "制章失败！");
        }
        return resMap;
    }

    @RequestMapping("autoseal")
    public @ResponseBody
    Map<String, Object> autoseal(HttpServletRequest request, HttpServletResponse response,
                                 String certDN, String certSN, String path, String pfxData,
                                 String sealCode, String sealPass, String sealResson, String keyword) {
        Map<String, Object> resMap = new HashMap<String, Object>();
        SealAutoPdfReq req = new SealAutoPdfReq();

        String type = "3";
        // String sealCode = "wenzi_1";
        //String sealPassword = "wenzi1234";
        String page = "1";
        String sealPerson = "杭州币港湾科技有限公司";
        String sealLocation = "杭州";
        //String sealResson = "借款签章";
        String lX = "10";
        String lY = "10";
        // String keyword = "乙方（盖章）";
        String locationStyle = "R";// 上:U；下:D；左:L；右:R；中:C；默认：C
        String offsetX = "10";
        String offsetY = "10";
        String certificationLevel = "0";// 0:Approval signature(NOT_CERTIFIED)2:Author signature, form filling allowed
        //String path = "E:\\dingpf\\dafywallet\\CADemo\\src\\cfca\\testData\\达飞移动支付投资协议（CFCA test）.pdf";

        req.setCertDN(certDN);
        req.setCertificationLevel(certificationLevel);
        req.setCertSN(certSN);
        req.setKeyword(keyword);
        req.setLocationStyle(locationStyle);
        req.setlX(lX);
        req.setlY(lY);
        req.setOffsetX(offsetX);
        req.setOffsetY(offsetY);
        req.setPage(page);
        req.setPath(path);
        req.setSealCode(sealCode);
        req.setSealLocation(sealLocation);
        req.setSealPassword(sealPass);
        req.setSealPerson(sealPerson);
        req.setSealResson(sealResson);
        req.setType(type);
        req.setSealedFileName(path.substring(0, path.length() - 4) + "-sign.pdf");

        log.info("发起Seal接口......");
        boolean flag = sealServiceClient.sealAutoPdf(req);
        if (flag) {
            resMap.put("isSucc", "签章成功！");
            resMap.put("sealedFilePath", path.substring(0, path.length() - 4) + "-sign.pdf");
        } else {
            resMap.put("isSucc", "签章失败！");
        }
        return resMap;
    }
}
