package com.pinting.business.accounting.service.impl.process;

import com.pinting.business.accounting.loan.enums.PartnerEnum;
import com.pinting.business.accounting.service.SignSealService;
import com.pinting.business.enums.SealBusiness;
import com.pinting.business.enums.SealStatus;
import com.pinting.business.model.BsUserSealFile;
import com.pinting.business.model.LnLoan;
import com.pinting.business.model.vo.*;
import com.pinting.business.util.Constants;
import com.pinting.business.util.ITextPdfUtil;
import com.pinting.core.util.DateUtil;
import com.pinting.core.util.GlobEnvUtil;
import com.pinting.core.util.StringUtil;
import com.pinting.gateway.hessian.message.dafy.B2GReqMsg_DafyLoanNotice_SignResultNotice;
import com.pinting.gateway.hessian.message.loan7.B2GReqMsg_DepLoan7Notice_SignResultNotice;
import com.pinting.gateway.out.service.loan.DafyNoticeService;
import com.pinting.gateway.out.service.loan7.DepLoan7NoticeService;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 云贷||7贷借款协议签章线程
 * Created by cyb on 2018/3/2.
 */
public class LoanAgreementSignSealProcess implements Runnable {

    private Logger log = LoggerFactory.getLogger(LoanAgreementSignSealProcess.class);

    /* 签章具体服务 */
    private SignSealService signSealService;
    /* 借款签章用户信息 */
    private SignSealUserInfoVO signSealUserInfo;
    /* 借款信息 */
    private LnLoan lnLoan;
    /* 理财人信息 */
    private List<BsUser4LoanVO> bsUserList;
    /* 合作方 */
    private PartnerEnum partner;
    /* 通知7贷的服务 */
    private DepLoan7NoticeService depLoan7NoticeService;
    /* 通知云贷的服务 */
    private DafyNoticeService dafyNoticeService;

    @Override
    public void run() {
        // 1. 生成协议源文件，并同步至cfca服务器，设置借款协议PDF路径
        // 2. 借款用户签章
        // 3. 币港湾签章
        // 4. 出借人签章
        seal();
    }

    /**
     * 进行签章
     */
    private void seal() {
        // 1. 生成协议源文件，并同步至cfca服务器，设置借款协议PDF路径
        createSrcPdf();

        if(StringUtil.isNotBlank(signSealUserInfo.getPdfPath())) {
        	
        	saveUserSealInfo(null, null,
        			null, "出借协议借款人签章：生成协议文本",
        			null,signSealUserInfo.getPdfPath(), null);
        	
            UserSealInfoVO userSealReq = new UserSealInfoVO();
            userSealReq.setIdCard(signSealUserInfo.getIdCard());
            userSealReq.setUserName(signSealUserInfo.getUserName());
            userSealReq.setUserId(signSealUserInfo.getUserId());
            // 借款用户印章信息
            UserSealResultVO userSealResult = signSealService.checkAndMakeSeal(userSealReq);
            if (userSealResult != null && userSealResult.isSucc()) {
            	String tempPdfPath = signSealUserInfo.getPdfPath();
                String sealedPdfFile =  tempPdfPath.substring(0,tempPdfPath.length() - 4) + "-sign.pdf";
                // 2. 借款用户签章
                if(lnUserSeal(userSealResult, sealedPdfFile)) {
                    // 3. 币港湾签章
                    if(bgwSeal(sealedPdfFile)) {
                        // 4. 出借人签章
                        fnUserSeal(sealedPdfFile);
                    }
                }
            } else {
                log.error("{}出借协议借款人签章：执行借款人制章失败", partner.getName());
                //记录异常
                saveUserSealInfo(signSealUserInfo.getUserId(), SealBusiness.LOAN_AGREEMENT.getCode(),
                        SealStatus.FAIL.getCode(), "出借协议借款人签章：执行借款人制章失败......",
                        String.valueOf(lnLoan.getId()),null, null);
            }

        }
    }

    /**
     * 4. 出借人签章
     * @return
     */
    private void fnUserSeal(String sealedPdfFile) {
        if (CollectionUtils.isNotEmpty(bsUserList)) {
            boolean succ = true;
            List<String> lnUserList = new ArrayList<>();
            //出借人签章
            for (BsUser4LoanVO bsUser : bsUserList) {
                if(!lnUserList.contains("（" + bsUser.getUserIdCardNo() + "）")) {
                    UserSealInfoVO userSeal = new UserSealInfoVO();
                    userSeal.setIdCard(bsUser.getUserIdCardNo());
                    userSeal.setUserName(bsUser.getUserName());
                    userSeal.setUserId(bsUser.getUserId());
                    UserSealResultVO sealResult = signSealService.checkAndMakeSeal(userSeal);

                    if (sealResult != null && sealResult.isSucc()) {
                        SignSeal4PdfInfoVO signSeal4Pdf = new SignSeal4PdfInfoVO();
                        signSeal4Pdf.setCertDN(sealResult.getCertDN());
                        signSeal4Pdf.setCertSN(sealResult.getCertSN());
                        signSeal4Pdf.setKeyword("（" + bsUser.getUserIdCardNo() + "）");
                        signSeal4Pdf.setPdfPath(sealedPdfFile);
                        signSeal4Pdf.setSealCode(sealResult.getSealCode());
                        signSeal4Pdf.setSealPassword(sealResult.getSealPassword());
                        signSeal4Pdf.setSealResson("借款协议签章");
                        signSeal4Pdf.setSealedFileName(sealedPdfFile);
                        boolean sign = signSealService.signSeal4PdfByKeyword(signSeal4Pdf);
                        succ = sign;
                        if (!sign) {
                            log.error("{}借款协议出借人签章：执行出借人 {} 签章失败", partner.getName(), bsUser.getUserName());
                            saveUserSealInfo(signSealUserInfo.getUserId(),SealBusiness.LOAN_AGREEMENT.getCode(),
                                    SealStatus.FAIL.getCode(), "借款协议出借人签章：执行出借人" + bsUser.getUserId() + "_" + bsUser.getUserName() + "签章失败......",
                                    String.valueOf(lnLoan.getId()), null , null);
                            break;
                        }
                        lnUserList.add(signSeal4Pdf.getKeyword());
                    }
                }
            }

            if (succ) {
                String tempString  = sealedPdfFile.replaceAll("\\\\", "/");
                String sealFile = tempString.substring(tempString.indexOf("/ftp", 0)+4);
            	
                //记录签章结果信息
                saveUserSealInfo(signSealUserInfo.getUserId(), SealBusiness.LOAN_AGREEMENT.getCode(),
                        SealStatus.SUCC.getCode(), null, String.valueOf(lnLoan.getId()),null, sealFile);
                log.info("{}借款协议签章成功", partner.getName());

                //通知7贷||云贷借款协议签章成功

                log.info("{}借款协议签章成功，通知{}", partner.getName(), partner.getName());
                switch (partner) {
                    case SEVEN_DAI_SELF:
                        B2GReqMsg_DepLoan7Notice_SignResultNotice sevenReq = new B2GReqMsg_DepLoan7Notice_SignResultNotice();
                        sevenReq.setAgreementNo(signSealUserInfo.getAgreementNo());
                        sevenReq.setLoanId(signSealUserInfo.getLoanId());
                        sevenReq.setSignResult("SUCCESS");
                        sevenReq.setAgreementUrl(sealFile);
                        
                        depLoan7NoticeService.signResultNotice(sevenReq);
                        break;
                    case YUN_DAI_SELF:
                        B2GReqMsg_DafyLoanNotice_SignResultNotice yunReq = new B2GReqMsg_DafyLoanNotice_SignResultNotice();
                        yunReq.setAgreementNo(signSealUserInfo.getAgreementNo());
                        yunReq.setLoanId(signSealUserInfo.getLoanId());
                        yunReq.setSignResult("SUCCESS");
                        yunReq.setAgreementUrl(sealFile);
                        dafyNoticeService.signResultNotice(yunReq);
                        break;
                    default:break;
                }
            }
        }
    }

    /**
     * 3. 币港湾公司签章
     * @return
     */
    private boolean bgwSeal(String sealedPdfFile) {
        //公司签章
        SignSeal4PdfInfoVO signCampanySeal4PdfReq = new SignSeal4PdfInfoVO();
        signCampanySeal4PdfReq.setCertDN(GlobEnvUtil.get("cfca.bigangwan.seal.certDN"));
        signCampanySeal4PdfReq.setCertSN(GlobEnvUtil.get("cfca.bigangwan.seal.certSN"));
        signCampanySeal4PdfReq.setKeyword("丙方（签章）");
        signCampanySeal4PdfReq.setPdfPath(sealedPdfFile);
        signCampanySeal4PdfReq.setSealCode(GlobEnvUtil.get("cfca.bigangwan.seal.code"));
        signCampanySeal4PdfReq.setSealPassword(GlobEnvUtil
                .get("cfca.bigangwan.seal.password"));
        signCampanySeal4PdfReq.setSealResson("出借协议签章");
        signCampanySeal4PdfReq.setSealedFileName(sealedPdfFile);
        boolean companySignFlag = signSealService
                .signSeal4PdfByKeyword(signCampanySeal4PdfReq);

        if (!companySignFlag) {
            log.error("{}出借协议单位签章：执行签章失败", partner.getName());
            saveUserSealInfo(signSealUserInfo.getUserId(),  SealBusiness.LOAN_AGREEMENT.getCode(),
                    SealStatus.FAIL.getCode(), "出借协议单位签章：执行签章失败......",
                    String.valueOf(lnLoan.getId()), null,null);
            return false;
        }
        return true;
    }

    /**
     * 2. 借款用户签章
     */
    private boolean lnUserSeal(UserSealResultVO userSealResult, String sealedPdfFile) {
        log.info("开始{}用户借款协议签章", partner.getName());
        //借款用户签章
        SignSeal4PdfInfoVO signSeal4PdfReq = new SignSeal4PdfInfoVO();
        signSeal4PdfReq.setCertDN(userSealResult.getCertDN());
        signSeal4PdfReq.setCertSN(userSealResult.getCertSN());
        signSeal4PdfReq.setKeyword("乙方（签章）" );
        signSeal4PdfReq.setPdfPath(signSealUserInfo.getPdfPath());
        signSeal4PdfReq.setSealCode(userSealResult.getSealCode());
        signSeal4PdfReq.setSealPassword(userSealResult.getSealPassword());
        signSeal4PdfReq.setSealResson("借款协议签章");
        signSeal4PdfReq.setSealedFileName(sealedPdfFile);
        boolean signFlag = signSealService.signSeal4PdfByKeyword(signSeal4PdfReq);
        if (!signFlag) {
            log.error("{}借款协议客户签章：执行签章失败", partner.getName());
            //记录异常
            saveUserSealInfo(signSealUserInfo.getUserId(), SealBusiness.LOAN_AGREEMENT.getCode(),
                    SealStatus.FAIL.getCode(), "借款协议客户签章：执行签章失败......",
                    String.valueOf(lnLoan.getId()), null,null);
            return false;
        }
        return true;
    }

    /**
     * 1. 生成协议源文件，并同步至cfca服务器，设置借款协议PDF路径
     */
    private void createSrcPdf() {
        //生成协议
        try {   
            String buyHtml = "";
            String buyPdfPath = "";
            String  dateString = DateUtil.formatDateTime(new Date(), "yyyyMM");
            if (PartnerEnum.YUN_DAI_SELF.equals(partner)) {
                buyPdfPath = GlobEnvUtil.get("self.loan.yundai.seal.path")+"/quartetAgree_"+dateString+"/"+signSealUserInfo.getAgreementNo()+".pdf";
                log.info("==============生成借款协议源文件的借款产品标识："+lnLoan.getPartnerBusinessFlag()+" ==============");
                if(Constants.LN_LOAN_PARTNER_BUSINESS_FLAG_REPAY_ANY_TIME.equals(lnLoan.getPartnerBusinessFlag())) {
                    // 消费循环贷
                    buyHtml = GlobEnvUtil.get("cfca.seal.loanAgreementYun.pdfSrcHtml") + "?partnerLoanId="
                            + lnLoan.getPartnerLoanId() + "&loanTime=" + DateUtil.formatDateTime(lnLoan.getLoanTime(), DateUtil.DATE_FORMAT + DateUtil.HHMMSS_FORMAT);;
                }else if(Constants.LN_LOAN_PARTNER_BUSINESS_FLAG_FIXED_INSTALLMENT.equals(lnLoan.getPartnerBusinessFlag())) {
                    // 等额本息
                    buyHtml = GlobEnvUtil.get("cfca.seal.loanAgreementYunInstallmentPdf.pdfSrcHtml") + "?partnerLoanId="
                            + lnLoan.getPartnerLoanId() + "&loanTime=" + DateUtil.formatDateTime(lnLoan.getLoanTime(), DateUtil.DATE_FORMAT + DateUtil.HHMMSS_FORMAT);;
                }else if(Constants.LN_LOAN_PARTNER_BUSINESS_FLAG_FIXED_PRINCIPAL_INTEREST.equals(lnLoan.getPartnerBusinessFlag())) {
                    // 等本等息
                    buyHtml = GlobEnvUtil.get("cfca.seal.loanAgreementYunPrincipalInterestPdf.pdfSrcHtml") + "?partnerLoanId="
                            + lnLoan.getPartnerLoanId() + "&loanTime=" + DateUtil.formatDateTime(lnLoan.getLoanTime(), DateUtil.DATE_FORMAT + DateUtil.HHMMSS_FORMAT);;
                }

            } else if (PartnerEnum.SEVEN_DAI_SELF.equals(partner) && !com.pinting.business.util.Constants.PARTNER_BUSINESS_FLAG_REPAY_ANY_TIME.equals(lnLoan.getPartnerBusinessFlag())) {
            	
            	buyPdfPath = GlobEnvUtil.get("self.loan.7dai.seal.path") +"/quartetAgree_"+dateString+"/"+signSealUserInfo.getAgreementNo()+".pdf";
                buyHtml = GlobEnvUtil.get("cfca.seal.loanAgreement7.pdfSrcHtml") + "?partnerLoanId="
                            + lnLoan.getPartnerLoanId() + "&loanTime=" + DateUtil.formatDateTime(lnLoan.getLoanTime(), DateUtil.DATE_FORMAT + DateUtil.HHMMSS_FORMAT);;
            } else if (PartnerEnum.SEVEN_DAI_SELF.equals(partner) && com.pinting.business.util.Constants.PARTNER_BUSINESS_FLAG_REPAY_ANY_TIME.equals(lnLoan.getPartnerBusinessFlag())) {
            	
            	buyPdfPath = GlobEnvUtil.get("self.loan.7dai.anyrepay.seal.path") +"/quartetAgree_"+dateString+"/"+signSealUserInfo.getAgreementNo()+".pdf";
            	buyHtml = GlobEnvUtil.get("cfca.seal.loanAgreement7AnyRepay.pdfSrcHtml") + "?partnerLoanId="
                        + lnLoan.getPartnerLoanId() + "&loanTime=" + DateUtil.formatDateTime(lnLoan.getLoanTime(), DateUtil.DATE_FORMAT + DateUtil.HHMMSS_FORMAT);
            }
            
            log.info("生成借款协议目标地址："+buyPdfPath);
            log.info("生成借款协议HTML源地址："+buyHtml);
            ITextPdfUtil.createHtm2PdfYunAndSeven(buyHtml, buyPdfPath, "借款协议", "借款协议");
            signSealUserInfo.setPdfPath(buyPdfPath);
        } catch (Exception e) {
            log.info("生成协议文本失败，异常：{}", e.getMessage());
            e.printStackTrace();
            signSealUserInfo.setPdfPath(null);
        }
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void saveUserSealInfo(Integer userId, String sealType, String sealStatus, String note,
                                  String relativeInfo,String srcAddress, String fileAddress) {
        BsUserSealFile file = signSealService.queryInitByAgreementNo(signSealUserInfo.getAgreementNo());
        UserSealFileVO userSealFile = new UserSealFileVO();
        userSealFile.setNote(note);
        userSealFile.setSealStatus(sealStatus);
        userSealFile.setSealType(sealType);
        userSealFile.setUserId(userId);
        userSealFile.setSrcAddress(srcAddress);
        userSealFile.setFileAddress(fileAddress);
        userSealFile.setRelativeInfo(relativeInfo);
        userSealFile.setUserSrc(partner.getCode());
        userSealFile.setId(file.getId());
        signSealService.updateUserSealFile(userSealFile);
    }

    public LoanAgreementSignSealProcess(SignSealService signSealService, SignSealUserInfoVO signSealUserInfo, LnLoan lnLoan, List<BsUser4LoanVO> bsUserList, PartnerEnum partner, DepLoan7NoticeService depLoan7NoticeService, DafyNoticeService dafyNoticeService) {
        this.signSealService = signSealService;
        this.signSealUserInfo = signSealUserInfo;
        this.lnLoan = lnLoan;
        this.bsUserList = bsUserList;
        this.partner = partner;
        this.depLoan7NoticeService = depLoan7NoticeService;
        this.dafyNoticeService = dafyNoticeService;
    }

    public SignSealService getSignSealService() {
        return signSealService;
    }

    public void setSignSealService(SignSealService signSealService) {
        this.signSealService = signSealService;
    }

    public SignSealUserInfoVO getSignSealUserInfo() {
        return signSealUserInfo;
    }

    public void setSignSealUserInfo(SignSealUserInfoVO signSealUserInfo) {
        this.signSealUserInfo = signSealUserInfo;
    }

    public LnLoan getLnLoan() {
        return lnLoan;
    }

    public void setLnLoan(LnLoan lnLoan) {
        this.lnLoan = lnLoan;
    }

    public List<BsUser4LoanVO> getBsUserList() {
        return bsUserList;
    }

    public void setBsUserList(List<BsUser4LoanVO> bsUserList) {
        this.bsUserList = bsUserList;
    }
}
