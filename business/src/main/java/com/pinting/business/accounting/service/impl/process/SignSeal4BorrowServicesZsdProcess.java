package com.pinting.business.accounting.service.impl.process;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pinting.business.accounting.service.SignSealService;
import com.pinting.business.enums.SealBusiness;
import com.pinting.business.enums.SealStatus;
import com.pinting.business.model.LnLoan;
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
 * 借款咨询与服务协议签章(赞时贷)
 * @author SHENGUOPING
 * @date  2017年10月31日 上午11:28:29
 */
public class SignSeal4BorrowServicesZsdProcess implements Runnable {
	
	private Logger log = LoggerFactory.getLogger(SignSeal4BorrowServicesZsdProcess.class);
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
        	 log.info("SignSeal4BorrowServicesZsdProcess 开始借款咨询与服务协议签章......");
        	 UserSealInfoVO userSealReq = new UserSealInfoVO();
             userSealReq.setIdCard(signSealUserInfo.getIdCard());
             userSealReq.setUserName(signSealUserInfo.getUserName());
             userSealReq.setUserId(signSealUserInfo.getUserId());
             log.info("SignSeal4BorrowServicesZsdProcess 开始借款咨询与服务协议签章{userName = "+ signSealUserInfo.getUserName() + ",idCard="+ signSealUserInfo.getIdCard()+ "}");
             UserSealResultVO userSealResult = signSealService.checkAndMakeSeal(userSealReq);
             if (userSealResult != null && userSealResult.isSucc()) {
            	 //客户签章
            	 log.info("SignSeal4BorrowServicesZsdProcess 获得制章信息成功");
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
	            		 						tempPdfPath.length() - 4) + "-zsd" + "-sign.pdf";
	             signSeal4PdfReq.setSealedFileName(sealedPdfFile);
	             boolean signFlag = signSealService.signSeal4PdfByKeyword(signSeal4PdfReq);
	             //boolean signFlag = fileCopy(tempPdfPath, sealedPdfFile);
	             if (!signFlag) {
	            	 log.error("SignSeal4BorrowServicesZsdProcess 借款咨询与服务协议(赞时贷)客户签章：执行签章失败......");
	            	 //记录异常
	            	 if (null != signSealUserInfo.getSealFileId()) {
	            		 log.info("SignSeal4BorrowServicesZsdProcess 客户签章失败更新异常，sealFileId = " + signSealUserInfo.getSealFileId());
	            		 updateUserSealInfo(signSealUserInfo.getUserId(), SealBusiness.ZSD_BORROW_SERVICES.getCode(),
		            			 SealStatus.FAIL.getCode(), "借款咨询与服务协议(赞时贷)客户签章重发：执行签章失败......",
		            			 signSealUserInfo.getOrderNo(), null, signSealUserInfo.getSealFileId());

	            	 }else {
	            		 log.info("SignSeal4BorrowServicesZsdProcess 客户签章失败新增异常记录");
		            	 saveUserSealInfo(signSealUserInfo.getUserId(), SealBusiness.ZSD_BORROW_SERVICES.getCode(),
		            			 SealStatus.FAIL.getCode(), "借款咨询与服务协议(赞时贷)客户签章：执行签章失败......",
		            			 signSealUserInfo.getOrderNo(), null);
	            	 }
	            	 

	             } else {
	            	 //赞时贷签章
	            	 SignSeal4PdfInfoVO signZsdDaiSeal4PdfReq = new SignSeal4PdfInfoVO();
	            	 signZsdDaiSeal4PdfReq.setCertDN(GlobEnvUtil.get("cfca.zsd.seal.certDN"));
	            	 signZsdDaiSeal4PdfReq.setCertSN(GlobEnvUtil.get("cfca.zsd.seal.certSN"));
	            	 signZsdDaiSeal4PdfReq.setKeyword("盖章");
	            	 signZsdDaiSeal4PdfReq.setPdfPath(sealedPdfFile);
	            	 signZsdDaiSeal4PdfReq.setSealCode(GlobEnvUtil.get("cfca.zsd.seal.code"));
	            	 signZsdDaiSeal4PdfReq.setSealPassword(GlobEnvUtil.get("cfca.zsd.seal.password"));
	            	 signZsdDaiSeal4PdfReq.setSealResson("借款咨询与服务协议签章");
	            	 signZsdDaiSeal4PdfReq.setSealedFileName(sealedPdfFile);
                     boolean enterpriseSignFlag = signSealService
                    		 .signSeal4PdfByKeyword(signZsdDaiSeal4PdfReq);
                     if (!enterpriseSignFlag) {
                         log.error("SignSeal4BorrowServicesZsdProcess 借款咨询与服务协议企业签章：执行赞时贷签章失败......");
                         if (null != signSealUserInfo.getSealFileId()) {
                        	 log.info("公司签章失败更新异常，sealFileId = " + signSealUserInfo.getSealFileId());
                        	 updateUserSealInfo(signSealUserInfo.getUserId(), SealBusiness.ZSD_BORROW_SERVICES.getCode(),
                            		 SealStatus.FAIL.getCode(), "借款咨询与服务协议企业签章重发：执行赞时贷签章失败......",
                            		 signSealUserInfo.getOrderNo(), null, signSealUserInfo.getSealFileId());
                         }else {
                        	 log.info("SignSeal4BorrowServicesZsdProcess 公司签章失败新增异常记录");
                             saveUserSealInfo(signSealUserInfo.getUserId(), SealBusiness.ZSD_BORROW_SERVICES.getCode(),
                            		 SealStatus.FAIL.getCode(), "借款咨询与服务协议企业签章：执行赞时贷签章失败......",
                            		 signSealUserInfo.getOrderNo(), null);
						 }

                     }else{
                    	 //记录签章结果信息
                    	 
                    	 if (null != signSealUserInfo.getSealFileId()) {
                    		 log.info("SignSeal4BorrowServicesZsdProcess 借款咨询与服务协议签章成功,更新信息"+signSealUserInfo.getSealFileId() );
                    		 updateUserSealInfo(signSealUserInfo.getUserId(), SealBusiness.ZSD_BORROW_SERVICES.getCode(),
                        			 SealStatus.SUCC.getCode(), "重发成功", signSealUserInfo.getOrderNo(),
                        			 sealedPdfFile, signSealUserInfo.getSealFileId());
                    	 }else {
                    		 log.info("SignSeal4BorrowServicesZsdProcess 借款咨询与服务协议签章成功,记录信息");
                        	 saveUserSealInfo(signSealUserInfo.getUserId(), SealBusiness.ZSD_BORROW_SERVICES.getCode(),
                        			 SealStatus.SUCC.getCode(), null, signSealUserInfo.getOrderNo(),
                        			 sealedPdfFile);
                    	 }

                    	 log.info("SignSeal4BorrowServicesZsdProcess 借款咨询与服务协议签章成功......");
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
		userSealFile.setUserSrc(Constants.PROPERTY_SYMBOL_ZSD);
        userSealFile.setAgreementNo("17300000"+String.valueOf(lnLoan.getId()));
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
    boolean  fileCopy(String srcUrl,String descUrl){
		 String  MESSAGE = "";
		 File srcFile = new File(srcUrl);  
   	  
        // 判断源文件是否存在  
        if (!srcFile.exists()) {  
            MESSAGE = "源文件：" + srcUrl + "不存在！";  
         
            return false;  
        } else if (!srcFile.isFile()) {  
            MESSAGE = "复制文件失败，源文件：" + srcUrl + "不是一个文件！";  
            
            return false;  
        }  
  
        // 判断目标文件是否存在  
        File destFile = new File(descUrl);  
        if (destFile.exists()) {  
            // 删除已经存在的目标文件，无论目标文件是目录还是单个文件  
            new File(descUrl).delete();  
            
        } else {  
            // 如果目标文件所在目录不存在，则创建目录  
            if (!destFile.getParentFile().exists()) {  
                // 目标文件所在目录不存在  
                if (!destFile.getParentFile().mkdirs()) {  
                    // 复制文件失败：创建目标文件所在目录失败  
                    return false;  
                }  
            }  
        }  
  
        // 复制文件  
        int byteread = 0; // 读取的字节数  
        InputStream in = null;  
        OutputStream out = null;  
  
        try {  
            in = new FileInputStream(srcFile);  
            out = new FileOutputStream(destFile);  
            byte[] buffer = new byte[1024];  
  
            while ((byteread = in.read(buffer)) != -1) {  
                out.write(buffer, 0, byteread);  
            }  
            return true;  
        } catch (FileNotFoundException e) {  
            return false;  
        } catch (IOException e) {  
            return false;  
        } finally {  
            try {  
                if (out != null)  
                    out.close();  
                if (in != null)  
                    in.close();  
            } catch (IOException e) {  
                e.printStackTrace();  
            }  
        }  
    }
    
	private void createSrcPdf() {
		 //生成协议
        try {
            String buyPdfPath = GlobEnvUtil.get("cfca.seal.pdfSrcPath") + "loan_"+ lnLoan.getLnUserId() +"/" + lnLoan.getLnUserId() + "-"
            					 + lnLoan.getPartnerLoanId() + "-"
                                 + DateUtil.formatDateTime(new Date(), "yyyyMMddHHmmssSSS")
                                 + "-borrowServices" + "-zsd" + ".pdf";
            log.info("生成协议保存地址："+buyPdfPath);
            String buyHtml = GlobEnvUtil.get("cfca.seal.borrowServicesZsd.pdfSrcHtml") + "?loanId="
                              + lnLoan.getPartnerLoanId();
            log.info("生成协议文本源地址："+buyHtml);
            ITextPdfUtil.createHtm2Pdf(buyHtml, buyPdfPath, "借款咨询与服务协议", "借款咨询与服务协议",
            		"17300000"+String.valueOf(lnLoan.getId()));
            log.info("生成协议文本成功");
            signSealUserInfo.setPdfPath(buyPdfPath);
            
        } catch (Exception e) {
        	log.info("SignSeal4BorrowServicesZsdProcess method createSrcPdf 生成协议文本失败,异常{}", e);
            e.printStackTrace();
            signSealUserInfo.setPdfPath(null);
        }
	}
	
}
