package com.pinting.business.accounting.service.impl.process;

import com.pinting.business.accounting.service.SignSealService;
import com.pinting.business.enums.SealBusiness;
import com.pinting.business.enums.SealStatus;
import com.pinting.business.model.vo.*;
import com.pinting.business.service.loan.BsUserSealFileService;
import com.pinting.business.service.site.UserService;
import com.pinting.core.util.GlobEnvUtil;
import com.pinting.core.util.StringUtil;
import com.pinting.gateway.hessian.message.dafy.B2GReqMsg_DafyLoanNotice_SignResultNotice;
import com.pinting.gateway.out.service.loan.DafyNoticeService;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 达飞云贷自主放款四方协议签章
 * @Project: business
 * @Title: SignSeal4DafyLoanProcess.java
 * @author Dragon & cat
 * @date 2017-2-6
 * @Copyright: 2016 BiGangWan.com Inc. All rights reserved.
 */
public class SignSeal4DafyLoanProcess implements Runnable{
	private Logger log = LoggerFactory.getLogger(SignSeal4DafyLoanProcess.class);
	
    private SignSealService     signSealService;
    private SignSealUserInfoVO    signSealUserInfo;
	private List<BsUser4LoanVO>   bsUserList;
	private DafyNoticeService	dafyNoticeService;
	private BsUserSealFileService bsUserSealFileService;

    public void setSignSealService(SignSealService signSealService) {
        this.signSealService = signSealService;
    }

    public void setUserService(UserService userService) {
	}

	public void setSignSealUserInfo(SignSealUserInfoVO signSealUserInfo) {
        this.signSealUserInfo = signSealUserInfo;
    }
	
    public void setBsUserList(List<BsUser4LoanVO> bsUserList) {
        this.bsUserList = bsUserList;
    }
    
    public void setDafyNoticeService(DafyNoticeService dafyNoticeService) {
        this.dafyNoticeService = dafyNoticeService;
    }
    
    public void setBsUserSealFileService(BsUserSealFileService bsUserSealFileService) {
        this.bsUserSealFileService = bsUserSealFileService;
    }

    /** 
     * @see java.lang.Runnable#run()
     */
    @Override
    public void run() {
    	//下载协议源文件，获得文件地址
        downloadPdf();
        
        try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
        
        
        if (!StringUtil.isEmpty(signSealUserInfo.getPdfPath())) {
        	log.info("开始借款协议签章......");
        	log.info("借款协议签章,源文件地址："+signSealUserInfo.getPdfPath());
            //公司签章
            SignSeal4PdfInfoVO signCampanySeal4PdfReq = new SignSeal4PdfInfoVO();
            signCampanySeal4PdfReq.setCertDN(GlobEnvUtil.get("cfca.bigangwan.seal.certDN"));
            signCampanySeal4PdfReq.setCertSN(GlobEnvUtil.get("cfca.bigangwan.seal.certSN"));
            signCampanySeal4PdfReq.setKeyword("丙方(盖章)");
            //signCampanySeal4PdfReq.setKeyword("\u76d6\u7ae0");
            //signCampanySeal4PdfReq.setKeyword("盖章");
            
            String tempPdfPath = signSealUserInfo.getPdfPath();
            String sealedPdfFile = tempPdfPath.substring(0,tempPdfPath.length() - 4) + "-sign.pdf";
            log.info("借款协议单位签章,签章后文件保存地址："+sealedPdfFile);
            String tempString  = sealedPdfFile.replaceAll("\\\\", "/");
            String sealYundaiFile = tempString.substring(tempString.indexOf("/ftp", 0)+4);
            signCampanySeal4PdfReq.setPdfPath(tempPdfPath);
            signCampanySeal4PdfReq.setSealCode(GlobEnvUtil.get("cfca.bigangwan.seal.code"));
            signCampanySeal4PdfReq.setSealPassword(GlobEnvUtil
                    .get("cfca.bigangwan.seal.password"));
            signCampanySeal4PdfReq.setSealResson("借款协议签章");
            signCampanySeal4PdfReq.setSealedFileName(sealedPdfFile);
            boolean companySignFlag = signSealService.signSeal4PdfByKeyword(signCampanySeal4PdfReq);
            
            //测试时用户模拟签章（把文件copy一份）
            //boolean companySignFlag = fileCopy(tempPdfPath,sealedPdfFile);
            if (!companySignFlag) {
            	
                log.error("借款协议单位签章：执行借款协议签章失败......");
                updateUserSealInfo(signSealUserInfo.getSealFileId(),signSealUserInfo.getUserId(), SealBusiness.LOAN_AGREEMENT.getCode(),
                        SealStatus.FAIL.getCode(), "借款协议单位签章：执行借款协议签章失败......",
                        signSealUserInfo.getOrderNo(), null);
            } else {
            	log.error("借款协议单位签章：执行借款协议签章成功，继续执行出借人签章");
                if (CollectionUtils.isNotEmpty(bsUserList) && bsUserList.size() > 0) {
                	log.info("借款协议出借人签章开始......");
                    boolean succ = true;
                    List<String> lnUserList=new ArrayList<>();
                    String  noteMakeSealFail = "";
                    //出借人签章
                    for (BsUser4LoanVO bsUser : bsUserList) {

                        if(!lnUserList.contains(bsUser.getUserIdCardNo())) {
                            UserSealInfoVO userSeal = new UserSealInfoVO();
                            userSeal.setIdCard(bsUser.getUserIdCardNo());
                            userSeal.setUserName(bsUser.getUserName());
                            userSeal.setUserId(bsUser.getUserId());
                            log.info("借款协议出借人签章出借人信息>>>>>idCard:"+bsUser.getUserIdCardNo()+";userName:"+bsUser.getUserName());
                            UserSealResultVO sealResult = signSealService.checkAndMakeSeal(userSeal);
                            if (sealResult != null && sealResult.isSucc()) {
                            	log.info("借款协议出借人签章获取制章信息成功>>>>>idCard:"+bsUser.getUserIdCardNo()+";userName:"+bsUser.getUserName());
                                SignSeal4PdfInfoVO signSeal4Pdf = new SignSeal4PdfInfoVO();
                                signSeal4Pdf.setCertDN(sealResult.getCertDN());
                                signSeal4Pdf.setCertSN(sealResult.getCertSN());
                                signSeal4Pdf.setKeyword(bsUser.getUserIdCardNo());
                                signSeal4Pdf.setPdfPath(sealedPdfFile);
                                signSeal4Pdf.setSealCode(sealResult.getSealCode());
                                signSeal4Pdf.setSealPassword(sealResult.getSealPassword());
                                signSeal4Pdf.setSealResson("借款协议签章");
                                signSeal4Pdf.setSealedFileName(sealedPdfFile);
                                boolean sign = signSealService.signSeal4PdfByKeyword(signSeal4Pdf);
                                log.info("借款协议出借人签章结果："+sign);
                                succ = sign;
                                if (!sign) {
                                    log.error("借款协议出借人签章：执行出借人" + bsUser.getUserName() + "签章失败......");
                                    updateUserSealInfo(signSealUserInfo.getSealFileId(),signSealUserInfo.getUserId(), SealBusiness.LOAN_AGREEMENT.getCode(),
                                            SealStatus.FAIL.getCode(), "借款协议出借人签章：执行出借人" + bsUser.getUserId() + "_" +bsUser.getUserName() + "签章失败......",
                                            signSealUserInfo.getOrderNo(), null);
                                    break;
                                }

                                lnUserList.add(bsUser.getUserIdCardNo());
                            }else {
                            	noteMakeSealFail = noteMakeSealFail + bsUser.getUserIdCardNo() + ";";
                            	
                                UserSealFileVO userSealFile = new UserSealFileVO();
                                userSealFile.setId(signSealUserInfo.getSealFileId());
                                userSealFile.setUpdateTime(new Date());
                                userSealFile.setNote("获取出借人制章信息失败："+noteMakeSealFail);
                                signSealService.updateUserSealFile(userSealFile);
                            	
                            	log.info("借款协议出借人签章获取制章信息失败>>>>>idCard:"+bsUser.getUserIdCardNo()+";userName:"+bsUser.getUserName());
							}
                        }
                    }

                    if (succ) {
                        //记录签章结果信息
                        updateUserSealInfo(signSealUserInfo.getSealFileId(),signSealUserInfo.getUserId(), SealBusiness.LOAN_AGREEMENT.getCode(),
                                SealStatus.SUCC.getCode(), null, signSealUserInfo.getOrderNo(),
                                sealYundaiFile);
                        log.info("借款协议签章成功......");
                        
                        //通知云贷借款四方协议签章成功
                        B2GReqMsg_DafyLoanNotice_SignResultNotice signResultNoticeReq = new B2GReqMsg_DafyLoanNotice_SignResultNotice();
                        signResultNoticeReq.setAgreementNo(signSealUserInfo.getAgreementNo());
                        signResultNoticeReq.setLoanId(signSealUserInfo.getLoanId());
                        signResultNoticeReq.setSignResult("SUCCESS");
                        signResultNoticeReq.setAgreementUrl(sealYundaiFile);
                        
                       /* List<SignResultNoticeLenderModel> 	lenders = new  ArrayList<SignResultNoticeLenderModel>();
                        for (BsUser4LoanVO bsUser : bsUserList) {
                        	SignResultNoticeLenderModel signResultNoticeLenderModel = new SignResultNoticeLenderModel();
                        	signResultNoticeLenderModel.setLenderName(bsUser.getUserName());
                        	signResultNoticeLenderModel.setLenderIdcard(bsUser.getUserIdCardNo());
                        	signResultNoticeLenderModel.setInvestAmount(bsUser.getOutAmount());
                        	lenders.add(signResultNoticeLenderModel);
                        }*/
                        
                        dafyNoticeService.signResultNotice(signResultNoticeReq);
                        
                    }else {
                    	log.info("借款协议签章失败......");
					}
                }
            }
        }
    }

    private void updateUserSealInfo(Integer id,Integer userId, String sealType, String sealStatus, String note,
                                  String relativeInfo, String fileAddress) {
        UserSealFileVO userSealFile = new UserSealFileVO();
        userSealFile.setId(id);
        userSealFile.setSealStatus(sealStatus);
        userSealFile.setUpdateTime(new Date());
        userSealFile.setFileAddress(fileAddress);
        signSealService.updateUserSealFile(userSealFile);
    }
    
    
    boolean  fileCopy(String srcUrl,String descUrl){
    		 File srcFile = new File(srcUrl);  
	    	  
	         // 判断源文件是否存在  
	         if (!srcFile.exists()) {  
	             return false;  
	         } else if (!srcFile.isFile()) {  
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
    
    private void downloadPdf() {
        //下载协议
        try {
        	String  url = bsUserSealFileService.downloadSealFile(signSealUserInfo.getPdfPath(), signSealUserInfo.getSealFileId());
            signSealUserInfo.setPdfPath(url);
        } catch (Exception e) {
            e.printStackTrace();
            signSealUserInfo.setPdfPath(null);
        }
    }
}
