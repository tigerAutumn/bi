package com.pinting.business.accounting.service.impl.process;

import com.pinting.business.accounting.service.SignSealService;
import com.pinting.business.enums.SealBusiness;
import com.pinting.business.enums.SealStatus;
import com.pinting.business.model.LnLoanRelation;
import com.pinting.business.model.vo.*;
import com.pinting.business.service.site.RegularSiteService;
import com.pinting.business.util.Constants;
import com.pinting.business.util.ITextPdfUtil;
import com.pinting.core.util.DateUtil;
import com.pinting.core.util.GlobEnvUtil;
import com.pinting.core.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

/**
 * Author:      shh
 * Date:        2018/1/30
 * Description: 赞分期-债权转让协议签章
 */
public class SignSeal4ZanClaimsAgreementProcess implements Runnable {

    private Logger log = LoggerFactory.getLogger(SignSeal4ZanClaimsAgreementProcess.class);
    private SignSealService signSealService;
    private SignSealUserInfoVO signSealUserInfo;
    private RegularSiteService regularSiteService;
    private LnLoanRelation lnLoanRelation;

    public void setSignSealService(SignSealService signSealService) {
        this.signSealService = signSealService;
    }

    public void setSignSealUserInfo(SignSealUserInfoVO signSealUserInfo) {
        this.signSealUserInfo = signSealUserInfo;
    }

    public void setRegularSiteService(RegularSiteService regularSiteService) {
        this.regularSiteService = regularSiteService;
    }

    public void setLnLoanRelation(LnLoanRelation lnLoanRelation) {
        this.lnLoanRelation = lnLoanRelation;
    }

    @Override
    public void run() {
        //生成协议源文件，文件会同步到CFCATransfer服务器
        createSrcPdf();
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (!StringUtil.isEmpty(signSealUserInfo.getPdfPath())) {
            log.info("SignSeal4ZanClaimsAgreementProcess 开始赞分期债权转让协议签章......");

            String tempPdfPath = signSealUserInfo.getPdfPath();
            String sealedPdfFile = GlobEnvUtil.get("cfca.seal.zanClaims.pdfDestPath")
                    + "claims_" + signSealUserInfo.getUserId()
                    + tempPdfPath.substring(tempPdfPath.lastIndexOf("/"),
                    tempPdfPath.length() - 4) + "-zan" + "-sign.pdf"; //签章文件
            log.info("赞分期债权转让协议签章原文件tempPdfPath: "+tempPdfPath);
            log.info("sealedPdfFile: "+sealedPdfFile);

            //公司签章
            SignSeal4PdfInfoVO signCampanySeal4PdfReq = new SignSeal4PdfInfoVO();
            signCampanySeal4PdfReq.setCertDN(GlobEnvUtil.get("cfca.bigangwan.seal.certDN"));
            signCampanySeal4PdfReq.setCertSN(GlobEnvUtil.get("cfca.bigangwan.seal.certSN"));
            signCampanySeal4PdfReq.setKeyword("签章：杭州币港湾科技有限公司");
            signCampanySeal4PdfReq.setPdfPath(tempPdfPath); //原文件的路径
            signCampanySeal4PdfReq.setSealCode(GlobEnvUtil.get("cfca.bigangwan.seal.code"));
            signCampanySeal4PdfReq.setSealPassword(GlobEnvUtil
                    .get("cfca.bigangwan.seal.password"));
            signCampanySeal4PdfReq.setSealResson("债权转让协议签章");
            signCampanySeal4PdfReq.setSealedFileName(sealedPdfFile);
            boolean companySignFlag = signSealService
                    .signSeal4PdfByKeyword(signCampanySeal4PdfReq);
            if (!companySignFlag) {
                log.error("SignSeal4ZsdLoanAgreementProcess 赞分期债权转让签章：执行签章失败......");
                saveUserSealInfo(signSealUserInfo.getUserId(), SealBusiness.ZAN_CLAIMS_AGREEMENT.getCode(),
                        SealStatus.FAIL.getCode(), "赞分期债权转让签章：执行签章失败......",
                        signSealUserInfo.getOrderNo(), null);
            }

        }

    }

    private void saveUserSealInfo(Integer userId, String sealType, String sealStatus, String note,
                                  String relativeInfo, String fileAddress) {
        UserSealFileVO userSealFile = new UserSealFileVO();
        userSealFile.setNote(note);
        userSealFile.setSealStatus(sealStatus);
        userSealFile.setSealType(sealType);
        userSealFile.setUserId(userId);
        userSealFile.setFileAddress(fileAddress);
        userSealFile.setRelativeInfo(relativeInfo);
        userSealFile.setUserSrc(Constants.PROPERTY_SYMBOL_ZAN);
        userSealFile.setAgreementNo(lnLoanRelation.getId().toString());
        signSealService.addUserSealFile(userSealFile);
    }

    private void createSrcPdf() {
        //生成协议
        try {
            regularSiteService.queryLoanRelationById(lnLoanRelation.getId());
            // 生成的pdf文件路径
            String claimsPdfPath = GlobEnvUtil.get("cfca.seal.zanClaims.pdfSrcPath") + "claims_" + lnLoanRelation.getLnUserId() + "/" + lnLoanRelation.getLnUserId() + "-"
                    + lnLoanRelation.getId() + "-"
                    + DateUtil.formatDateTime(new Date(), "yyyyMMddHHmmssSSS")
                    + "-claimsAgreement" + ".pdf";
            log.info(claimsPdfPath);
            String claimsHtml = GlobEnvUtil.get("cfca.seal.zanAgreementClaims.pdfSrcHtml") + "?loanRelationId="
                    + lnLoanRelation.getId();
            ITextPdfUtil.createHtm2Pdf(claimsHtml, claimsPdfPath, "债转协议", "债转协议",
                    lnLoanRelation.getId().toString());
            signSealUserInfo.setPdfPath(claimsPdfPath);
        } catch (Exception e) {
            e.printStackTrace();
            signSealUserInfo.setPdfPath(null);
        }

    }

}
