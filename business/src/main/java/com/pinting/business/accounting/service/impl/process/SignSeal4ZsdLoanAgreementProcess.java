package com.pinting.business.accounting.service.impl.process;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pinting.business.accounting.service.SignSealService;
import com.pinting.business.enums.SealBusiness;
import com.pinting.business.enums.SealStatus;
import com.pinting.business.model.LnLoan;
import com.pinting.business.model.vo.BsUser4LoanVO;
import com.pinting.business.model.vo.SignSeal4PdfInfoVO;
import com.pinting.business.model.vo.SignSealUserInfoVO;
import com.pinting.business.model.vo.UserSealFileVO;
import com.pinting.business.model.vo.UserSealInfoVO;
import com.pinting.business.model.vo.UserSealResultVO;
import com.pinting.business.util.Constants;
import com.pinting.business.util.ITextPdfUtil;
import com.pinting.core.util.DateUtil;
import com.pinting.core.util.GlobEnvUtil;
import com.pinting.core.util.StringUtil;

/**
 * 赞时贷借款协议  签章
 * @author SHENGUOPING
 * @date  2017年10月31日 上午11:45:08
 */
public class SignSeal4ZsdLoanAgreementProcess implements Runnable {
	
	private Logger log = LoggerFactory.getLogger(SignSeal4ZsdLoanAgreementProcess.class);
    private SignSealService signSealService;
    private SignSealUserInfoVO signSealUserInfo;
    private LnLoan lnLoan; //借款表信息
    private List<BsUser4LoanVO> bsUserList;


    public void setLnLoan(LnLoan lnLoan) {
        this.lnLoan = lnLoan;
    }

    public void setSignSealService(SignSealService signSealService) {
        this.signSealService = signSealService;
    }

    public void setSignSealUserInfo(SignSealUserInfoVO signSealUserInfo) {
        this.signSealUserInfo = signSealUserInfo;
    }

    public void setBsUserList(List<BsUser4LoanVO> bsUserList) {
        this.bsUserList = bsUserList;
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
            log.info("SignSeal4ZsdLoanAgreementProcess 开始借款协议签章......");
            UserSealInfoVO userSealReq = new UserSealInfoVO();
            userSealReq.setIdCard(signSealUserInfo.getIdCard());
            userSealReq.setUserName(signSealUserInfo.getUserName());
            userSealReq.setUserId(signSealUserInfo.getUserId());
            UserSealResultVO userSealResult = signSealService.checkAndMakeSeal(userSealReq);

            if (userSealResult != null && userSealResult.isSucc()) {
                //借款用户签章
                SignSeal4PdfInfoVO signSeal4PdfReq = new SignSeal4PdfInfoVO();
                signSeal4PdfReq.setCertDN(userSealResult.getCertDN());
                signSeal4PdfReq.setCertSN(userSealResult.getCertSN());
                signSeal4PdfReq.setKeyword("乙方（借款人）： " + signSealUserInfo.getUserName());
                signSeal4PdfReq.setPdfPath(signSealUserInfo.getPdfPath());
                signSeal4PdfReq.setSealCode(userSealResult.getSealCode());
                signSeal4PdfReq.setSealPassword(userSealResult.getSealPassword());
                signSeal4PdfReq.setSealResson("借款协议签章");
                String tempPdfPath = signSealUserInfo.getPdfPath();
                String sealedPdfFile = GlobEnvUtil.get("cfca.seal.pdfDestPath")
                        + "loan_" + signSealUserInfo.getUserId()
                        + tempPdfPath.substring(tempPdfPath.lastIndexOf("/"),
                        tempPdfPath.length() - 4) + "-zsd" +"-sign.pdf";
                signSeal4PdfReq.setSealedFileName(sealedPdfFile);
                boolean signFlag = signSealService.signSeal4PdfByKeyword(signSeal4PdfReq);
                if (!signFlag) {
                    log.error("SignSeal4ZsdLoanAgreementProcess 借款协议客户签章：执行签章失败......");
                    //记录异常
                    saveUserSealInfo(signSealUserInfo.getUserId(), SealBusiness.ZSD_LOAN_AGREEMENT.getCode(),
                            SealStatus.FAIL.getCode(), "借款协议客户签章：执行签章失败......",
                            signSealUserInfo.getOrderNo(), null);
                } else {
                    //赞时贷签章
                    SignSeal4PdfInfoVO signYunDaiSeal4PdfReq = new SignSeal4PdfInfoVO();
                    signYunDaiSeal4PdfReq.setCertDN(GlobEnvUtil.get("cfca.zsd.seal.certDN"));
                    signYunDaiSeal4PdfReq.setCertSN(GlobEnvUtil.get("cfca.zsd.seal.certSN"));
                    signYunDaiSeal4PdfReq.setKeyword("丁方（借款人推荐方）： 杭州赞分期商务信息咨询服务有限公司");  // 
                    signYunDaiSeal4PdfReq.setPdfPath(sealedPdfFile);
                    signYunDaiSeal4PdfReq.setSealCode(GlobEnvUtil.get("cfca.zsd.seal.code"));
                    signYunDaiSeal4PdfReq.setSealPassword(GlobEnvUtil
                            .get("cfca.zsd.seal.password"));
                    signYunDaiSeal4PdfReq.setSealResson("借款协议签章");
                    signYunDaiSeal4PdfReq.setSealedFileName(sealedPdfFile);
                    boolean enterpriseSignFlag = signSealService
                            .signSeal4PdfByKeyword(signYunDaiSeal4PdfReq);
                    if (!enterpriseSignFlag) {
                        log.error("SignSeal4ZsdLoanAgreementProcess 借款协议企业签章：执行赞时贷签章失败......");
                        saveUserSealInfo(signSealUserInfo.getUserId(), SealBusiness.ZSD_LOAN_AGREEMENT.getCode(),
                                SealStatus.FAIL.getCode(), "借款协议企业签章：执行赞时贷签章失败......",
                                signSealUserInfo.getOrderNo(), null);
                    } else {

                        //公司签章
                        SignSeal4PdfInfoVO signCampanySeal4PdfReq = new SignSeal4PdfInfoVO();
                        signCampanySeal4PdfReq.setCertDN(GlobEnvUtil.get("cfca.bigangwan.seal.certDN"));
                        signCampanySeal4PdfReq.setCertSN(GlobEnvUtil.get("cfca.bigangwan.seal.certSN"));
                        signCampanySeal4PdfReq.setKeyword("丙方（出借人推荐方）： 杭州币港湾科技有限公司");
                        signCampanySeal4PdfReq.setPdfPath(sealedPdfFile);
                        signCampanySeal4PdfReq.setSealCode(GlobEnvUtil.get("cfca.bigangwan.seal.code"));
                        signCampanySeal4PdfReq.setSealPassword(GlobEnvUtil
                                .get("cfca.bigangwan.seal.password"));
                        signCampanySeal4PdfReq.setSealResson("出借协议签章");
                        signCampanySeal4PdfReq.setSealedFileName(sealedPdfFile);
                        boolean companySignFlag = signSealService
                                .signSeal4PdfByKeyword(signCampanySeal4PdfReq);

                        if (!companySignFlag) {
                            log.error("SignSeal4ZsdLoanAgreementProcess 出借协议单位签章：执行签章失败......");
                            saveUserSealInfo(signSealUserInfo.getUserId(), SealBusiness.ZSD_LOAN_AGREEMENT.getCode(),
                                    SealStatus.FAIL.getCode(), "出借协议单位签章：执行签章失败......",
                                    signSealUserInfo.getOrderNo(), null);
                        } else {

                            if (CollectionUtils.isNotEmpty(bsUserList) && bsUserList.size() > 0) {

                                boolean succ = true;
                                List<String> lnUserList=new ArrayList<>();
                                //出借人签章
                                for (BsUser4LoanVO bsUser : bsUserList) {

                                    if(!lnUserList.contains(bsUser.getUserName() + "（" + bsUser.getUserId() + "）")) {
                                        UserSealInfoVO userSeal = new UserSealInfoVO();
                                        userSeal.setIdCard(bsUser.getUserIdCardNo());
                                        userSeal.setUserName(bsUser.getUserName());
                                        userSeal.setUserId(bsUser.getUserId());
                                        UserSealResultVO sealResult = signSealService.checkAndMakeSeal(userSeal);

                                        if (sealResult != null && sealResult.isSucc()) {
                                            SignSeal4PdfInfoVO signSeal4Pdf = new SignSeal4PdfInfoVO();
                                            signSeal4Pdf.setCertDN(sealResult.getCertDN());
                                            signSeal4Pdf.setCertSN(sealResult.getCertSN());
                                            signSeal4Pdf.setKeyword(bsUser.getUserName() + "（" + bsUser.getUserId() + "）");
                                            signSeal4Pdf.setPdfPath(sealedPdfFile);
                                            signSeal4Pdf.setSealCode(sealResult.getSealCode());
                                            signSeal4Pdf.setSealPassword(sealResult.getSealPassword());
                                            signSeal4Pdf.setSealResson("借款协议签章");
                                            signSeal4Pdf.setSealedFileName(sealedPdfFile);
                                            boolean sign = signSealService.signSeal4PdfByKeyword(signSeal4Pdf);
                                            succ = sign;
                                            if (!sign) {
                                                log.error("SignSeal4ZsdLoanAgreementProcess 借款协议出借人签章：执行出借人" + bsUser.getUserName() + "签章失败......");
                                                saveUserSealInfo(bsUser.getUserId(), SealBusiness.ZSD_LOAN_AGREEMENT.getCode(),
                                                        SealStatus.FAIL.getCode(), "借款协议出借人签章：执行出借人" + bsUser.getUserName() + "签章失败......",
                                                        signSealUserInfo.getOrderNo(), null);
                                                break;
                                            }

                                            lnUserList.add(signSeal4Pdf.getKeyword());
                                        }
                                    }
                                }

                                if (succ) {
                                    //记录签章结果信息
                                    saveUserSealInfo(signSealUserInfo.getUserId(), SealBusiness.ZSD_LOAN_AGREEMENT.getCode(),
                                            SealStatus.SUCC.getCode(), null, signSealUserInfo.getOrderNo(), sealedPdfFile);
                                    log.info("SignSeal4ZsdLoanAgreementProcess 借款协议签章成功......");
                                }
                            }
                        }
                    }

                }
            } else {
                log.error("SignSeal4ZsdLoanAgreementProcess 出借协议借款人签章：执行借款人制章失败......");
                saveUserSealInfo(signSealUserInfo.getUserId(), SealBusiness.ZSD_LOAN_AGREEMENT.getCode(),
                        SealStatus.FAIL.getCode(), "出借协议借款人签章：执行借款人制章失败......",
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
        userSealFile.setUserSrc(Constants.PROPERTY_SYMBOL_ZSD);
        userSealFile.setAgreementNo("17200000"+String.valueOf(lnLoan.getId()));
        signSealService.addUserSealFile(userSealFile);
    }

    private void createSrcPdf() {
        //生成协议
        try {
            String buyPdfPath = GlobEnvUtil.get("cfca.seal.pdfSrcPath") + "/loan_" + lnLoan.getLnUserId() + "/" + lnLoan.getLnUserId() + "-"
                    + lnLoan.getPartnerLoanId() + "-"
                    + DateUtil.formatDateTime(new Date(), "yyyyMMddHHmmssSSS")
                    + "-loanAgreement" + "-zsd" + ".pdf";
            System.out.println(buyPdfPath);
            String buyHtml = GlobEnvUtil.get("cfca.seal.loanAgreementZsd.pdfSrcHtml") + "?partnerLoanId="
                    + lnLoan.getPartnerLoanId();
            ITextPdfUtil.createHtm2Pdf(buyHtml, buyPdfPath, "借款协议", "借款协议",
            		"17200000"+String.valueOf(lnLoan.getId()));
            signSealUserInfo.setPdfPath(buyPdfPath);

        } catch (Exception e) {
        	log.info("SignSeal4ZsdLoanAgreementProcess method createSrcPdf 生成协议文本失败,异常{}", e);
            e.printStackTrace();
            signSealUserInfo.setPdfPath(null);
        }

    }
    
}	
