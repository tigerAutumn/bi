package com.pinting.business.accounting.service.impl.process;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pinting.business.accounting.service.SignSealService;
import com.pinting.business.enums.SealBusiness;
import com.pinting.business.enums.SealStatus;
import com.pinting.business.model.vo.BsUser4LoanVO;
import com.pinting.business.model.vo.SignSeal4PdfInfoVO;
import com.pinting.business.model.vo.SignSealUserInfoVO;
import com.pinting.business.model.vo.UserSealFileVO;
import com.pinting.business.model.vo.UserSealInfoVO;
import com.pinting.business.model.vo.UserSealResultVO;
import com.pinting.business.service.loan.BsUserSealFileService;
import com.pinting.business.service.site.UserService;
import com.pinting.core.util.GlobEnvUtil;
import com.pinting.core.util.StringUtil;
import com.pinting.gateway.hessian.message.loan7.B2GReqMsg_DepLoan7Notice_SignResultNotice;
import com.pinting.gateway.out.service.loan7.DepLoan7NoticeService;

/**
 * 7贷自主放款四方协议签章
 * @author SHENGUOPING
 * @date  2017年12月13日 下午6:55:43
 */
public class SignSeal4DepLoan7Process implements Runnable {

	private Logger log = LoggerFactory.getLogger(SignSeal4DafyLoanProcess.class);
	
    private SignSealService     signSealService;
    private UserService	userService;
    private SignSealUserInfoVO    signSealUserInfo;
	private List<BsUser4LoanVO>   bsUserList;
	private DepLoan7NoticeService	depLoan7NoticeService;
	private BsUserSealFileService bsUserSealFileService;

    public void setSignSealService(SignSealService signSealService) {
        this.signSealService = signSealService;
    }
    
    public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public void setSignSealUserInfo(SignSealUserInfoVO signSealUserInfo) {
        this.signSealUserInfo = signSealUserInfo;
    }
	
    public void setBsUserList(List<BsUser4LoanVO> bsUserList) {
        this.bsUserList = bsUserList;
    }

	public void setDepLoan7NoticeService(DepLoan7NoticeService depLoan7NoticeService) {
		this.depLoan7NoticeService = depLoan7NoticeService;
	}

	public void setBsUserSealFileService(BsUserSealFileService bsUserSealFileService) {
        this.bsUserSealFileService = bsUserSealFileService;
    }

    /** 
     * 借款人和借款公司（资金合作方）协议上已经签章，先下载对应的协议，然后进行币港湾和理财人的签章，
     * 最后发送通知给资金合作方
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
            signCampanySeal4PdfReq.setKeyword("丙方（签章）");
            //signCampanySeal4PdfReq.setKeyword("\u76d6\u7ae0");
            //signCampanySeal4PdfReq.setKeyword("盖章");
            
            String tempPdfPath = signSealUserInfo.getPdfPath();
            String sealedPdfFile = tempPdfPath.substring(0,tempPdfPath.length() - 4) + "-sign.pdf";
            log.info("借款协议单位签章,签章后文件保存地址："+sealedPdfFile);
            String tempString  = sealedPdfFile.replaceAll("\\\\", "/");
            String sealYundaiFile = tempString.substring(tempString.indexOf("/ftp", 0)+4);
            signCampanySeal4PdfReq.setPdfPath(tempPdfPath);
            signCampanySeal4PdfReq.setSealCode(GlobEnvUtil.get("cfca.bigangwan.seal.code"));
            signCampanySeal4PdfReq.setSealPassword(GlobEnvUtil.get("cfca.bigangwan.seal.password"));
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
                                            SealStatus.FAIL.getCode(), "借款协议出借人签章：执行出借人" + bsUser.getUserId() + "_" + bsUser.getUserName() + "签章失败......",
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
                        
                        //通知7贷借款四方协议签章成功
                        B2GReqMsg_DepLoan7Notice_SignResultNotice signResultNoticeReq = new B2GReqMsg_DepLoan7Notice_SignResultNotice();
                        signResultNoticeReq.setAgreementNo(signSealUserInfo.getAgreementNo());
                        signResultNoticeReq.setLoanId(signSealUserInfo.getLoanId());
                        signResultNoticeReq.setSignResult("SUCCESS");
                        signResultNoticeReq.setAgreementUrl(sealYundaiFile);
                        
                        depLoan7NoticeService.signResultNotice(signResultNoticeReq);
                        
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
    
    private void downloadPdf() {
        //下载协议
        try {
        	String url = bsUserSealFileService.downloadPartnerSealFile(signSealUserInfo.getPdfPath(), signSealUserInfo.getSealFileId(),
        			signSealUserInfo.getPartnerCode());
            signSealUserInfo.setPdfPath(url);
        } catch (Exception e) {
        	log.error("SignSeal4DepLoan7Process downloadPdf exception:{}", e);
            signSealUserInfo.setPdfPath(null);
        }
    }
}
