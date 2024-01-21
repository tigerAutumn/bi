package com.pinting.business.accounting.service.impl.process;

import com.pinting.business.accounting.service.SignSealService;
import com.pinting.business.enums.SealBusiness;
import com.pinting.business.enums.SealStatus;
import com.pinting.business.model.LnLoan;
import com.pinting.business.model.vo.*;
import com.pinting.business.util.Constants;
import com.pinting.business.util.ITextPdfUtil;
import com.pinting.core.util.DateUtil;
import com.pinting.core.util.GlobEnvUtil;
import com.pinting.core.util.StringUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

/**
 * 借款咨询与服务协议  签章(赞分期)
 * @author bianyatian
 * @2016-11-10 下午1:31:15
 */
public class SignSeal4BorrowServicesZanProcess implements Runnable {
	private Logger log = LoggerFactory.getLogger(SignSeal4BorrowServicesZanProcess.class);
	private SignSealService     signSealService;
    private SignSealUserInfoVO    signSealUserInfo;
    private LnLoan lnLoan; //借款表信息

    public void setLnLoan(LnLoan lnLoan) {
		this.lnLoan = lnLoan;
	}

	public void setSignSealService(SignSealService signSealService) {
        this.signSealService = signSealService;
    }

	public void setSignSealUserInfo(SignSealUserInfoVO signSealUserInfo) {
        this.signSealUserInfo = signSealUserInfo;
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
        	 log.info("开始借款咨询与服务协议签章......");
        	 UserSealInfoVO userSealReq = new UserSealInfoVO();
             userSealReq.setIdCard(signSealUserInfo.getIdCard());
             userSealReq.setUserName(signSealUserInfo.getUserName());
             userSealReq.setUserId(signSealUserInfo.getUserId());
             log.info("开始借款咨询与服务协议签章{userName = "+ signSealUserInfo.getUserName() + ",idCard="+ signSealUserInfo.getIdCard()+ "}");
             UserSealResultVO userSealResult = signSealService.checkAndMakeSeal(userSealReq);
             if (userSealResult != null && userSealResult.isSucc()) {
            	 //客户签章
            	 log.info("获得制章信息成功");
            	 SignSeal4PdfInfoVO signSeal4PdfReq = new SignSeal4PdfInfoVO();
            	 signSeal4PdfReq.setCertDN(userSealResult.getCertDN());
            	 signSeal4PdfReq.setCertSN(userSealResult.getCertSN());
            	 signSeal4PdfReq.setKeyword(signSealUserInfo.getUserName());//"借款人（签字）"+ signSealUserInfo.getUserName()
            	 signSeal4PdfReq.setPdfPath(signSealUserInfo.getPdfPath());
            	 signSeal4PdfReq.setSealCode(userSealResult.getSealCode());
            	 signSeal4PdfReq.setSealPassword(userSealResult.getSealPassword());
	             signSeal4PdfReq.setSealResson("借款咨询与服务协议签章");
	             String tempPdfPath = signSealUserInfo.getPdfPath();
	             String sealedPdfFile = GlobEnvUtil.get("cfca.seal.pdfDestPath")
	            		 				+ "loan_"+signSealUserInfo.getUserId()
	            		 				+ tempPdfPath.substring(tempPdfPath.lastIndexOf("/"),
	            		 						tempPdfPath.length() - 4) + "-sign.pdf";
	             signSeal4PdfReq.setSealedFileName(sealedPdfFile);
	             boolean signFlag = signSealService.signSeal4PdfByKeyword(signSeal4PdfReq);
	             if (!signFlag) {
	            	 log.error("借款咨询与服务协议(赞分期)客户签章：执行签章失败......");
	            	 //记录异常
	            	 if (null != signSealUserInfo.getSealFileId()) {
	            		 log.info("客户签章失败更新异常，sealFileId = " + signSealUserInfo.getSealFileId());
	            		 updateUserSealInfo(signSealUserInfo.getUserId(), SealBusiness.BORROW_SERVICES.getCode(),
		            			 SealStatus.FAIL.getCode(), "借款咨询与服务协议(赞分期)客户签章重发：执行签章失败......",
		            			 signSealUserInfo.getOrderNo(), null, signSealUserInfo.getSealFileId());

	            	 }else {
	            		 log.info("客户签章失败新增异常记录");
		            	 saveUserSealInfo(signSealUserInfo.getUserId(), SealBusiness.BORROW_SERVICES.getCode(),
		            			 SealStatus.FAIL.getCode(), "借款咨询与服务协议(赞分期)客户签章：执行签章失败......",
		            			 signSealUserInfo.getOrderNo(), null);
	            	 }
	            	 

	             } else {
	            	 //赞分期签章
	            	 SignSeal4PdfInfoVO signYunDaiSeal4PdfReq = new SignSeal4PdfInfoVO();
	            	 signYunDaiSeal4PdfReq.setCertDN(GlobEnvUtil.get("cfca.zan.seal.certDN"));
	            	 signYunDaiSeal4PdfReq.setCertSN(GlobEnvUtil.get("cfca.zan.seal.certSN"));
	            	 signYunDaiSeal4PdfReq.setKeyword("盖章");
	            	 signYunDaiSeal4PdfReq.setPdfPath(sealedPdfFile);
	            	 signYunDaiSeal4PdfReq.setSealCode(GlobEnvUtil.get("cfca.zan.seal.code"));
	            	 signYunDaiSeal4PdfReq.setSealPassword(GlobEnvUtil
	            			 .get("cfca.zan.seal.password"));
	            	 signYunDaiSeal4PdfReq.setSealResson("借款咨询与服务协议签章");
	            	 signYunDaiSeal4PdfReq.setSealedFileName(sealedPdfFile);
                     boolean enterpriseSignFlag = signSealService
                    		 .signSeal4PdfByKeyword(signYunDaiSeal4PdfReq);
                     if (!enterpriseSignFlag) {
                         log.error("借款咨询与服务协议企业签章：执行赞分期签章失败......");
                         if (null != signSealUserInfo.getSealFileId()) {
                        	 log.info("公司签章失败更新异常，sealFileId = " + signSealUserInfo.getSealFileId());
                        	 updateUserSealInfo(signSealUserInfo.getUserId(), SealBusiness.BORROW_SERVICES.getCode(),
                            		 SealStatus.FAIL.getCode(), "借款咨询与服务协议企业签章重发：执行赞分期签章失败......",
                            		 signSealUserInfo.getOrderNo(), null, signSealUserInfo.getSealFileId());
                         }else {
                        	 log.info("公司签章失败新增异常记录");
                             saveUserSealInfo(signSealUserInfo.getUserId(), SealBusiness.BORROW_SERVICES.getCode(),
                            		 SealStatus.FAIL.getCode(), "借款咨询与服务协议企业签章：执行赞分期签章失败......",
                            		 signSealUserInfo.getOrderNo(), null);
						 }

                     }else{
                    	 //记录签章结果信息
                    	 
                    	 if (null != signSealUserInfo.getSealFileId()) {
                    		 log.info("借款咨询与服务协议签章成功,更新信息"+signSealUserInfo.getSealFileId() );
                    		 updateUserSealInfo(signSealUserInfo.getUserId(), SealBusiness.BORROW_SERVICES.getCode(),
                        			 SealStatus.SUCC.getCode(), "重发成功", signSealUserInfo.getOrderNo(),
                        			 sealedPdfFile, signSealUserInfo.getSealFileId());
                    	 }else {
                    		 log.info("借款咨询与服务协议签章成功,记录信息");
                        	 saveUserSealInfo(signSealUserInfo.getUserId(), SealBusiness.BORROW_SERVICES.getCode(),
                        			 SealStatus.SUCC.getCode(), null, signSealUserInfo.getOrderNo(),
                        			 sealedPdfFile);
                    	 }

                    	 log.info("借款咨询与服务协议签章成功......");
                     }
						   	
	             }
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
        userSealFile.setAgreementNo(lnLoan.getPartnerLoanId());
		signSealService.addUserSealFile(userSealFile);
	}
    
    
    private void updateUserSealInfo(Integer userId, String sealType, String sealStatus, String note,
            String relativeInfo, String fileAddress,Integer id) {
		UserSealFileVO userSealFile = new UserSealFileVO();
		userSealFile.setId(id);
		userSealFile.setNote(note);
		userSealFile.setSealStatus(sealStatus);
		userSealFile.setSealType(sealType);
		userSealFile.setUserId(userId);
		userSealFile.setFileAddress(fileAddress);
		userSealFile.setRelativeInfo(relativeInfo);
		signSealService.updateUserSealFile(userSealFile);
	}
	
	private void createSrcPdf() {
		 //生成协议
        try {
            String buyPdfPath = GlobEnvUtil.get("cfca.seal.pdfSrcPath") + "loan_"+ lnLoan.getLnUserId() +"/" + lnLoan.getLnUserId() + "-"
            					 + lnLoan.getPartnerLoanId() + "-"
                                 + DateUtil.formatDateTime(new Date(), "yyyyMMddHHmmssSSS")
                                 + "-borrowServices" + ".pdf";
            log.info("生成协议保存地址："+buyPdfPath);
            String buyHtml = GlobEnvUtil.get("cfca.seal.borrowServices.pdfSrcHtml") + "?loanId="
                              + lnLoan.getPartnerLoanId();
            log.info("生成协议文本源地址："+buyHtml);
            ITextPdfUtil.createHtm2Pdf(buyHtml, buyPdfPath, "借款咨询与服务协议", "借款咨询与服务协议",
            		lnLoan.getPartnerLoanId());
            log.info("生成协议文本成功");
            signSealUserInfo.setPdfPath(buyPdfPath);
            
        } catch (Exception e) {
        	log.info("生成协议文本失败");
            e.printStackTrace();
            signSealUserInfo.setPdfPath(null);
        }
		
	}
	
	
}
