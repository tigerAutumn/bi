package com.pinting.business.accounting.service.impl.process;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pinting.business.accounting.service.SignSealService;
import com.pinting.business.enums.SealBusiness;
import com.pinting.business.enums.SealStatus;
import com.pinting.business.model.BsUser;
import com.pinting.business.model.vo.SignSeal4PdfInfoVO;
import com.pinting.business.model.vo.SignSealUserInfoVO;
import com.pinting.business.model.vo.UserSealFileVO;
import com.pinting.business.model.vo.UserSealInfoVO;
import com.pinting.business.model.vo.UserSealResultVO;
import com.pinting.business.service.site.UserService;
import com.pinting.business.util.Constants;
import com.pinting.business.util.ITextPdfUtil;
import com.pinting.core.util.DateUtil;
import com.pinting.core.util.GlobEnvUtil;
import com.pinting.core.util.StringUtil;

/**
 * 计划产品签章
 * @Project: business
 * @author Dragon & cat
 * @date 2016-9-12
 */
public class SignSeal4BuyProcessAuth implements Runnable {

	private Logger log = LoggerFactory.getLogger(SignSeal4BuyProcessAuth.class);
    private SignSealService     signSealService;
    private UserService	userService;
    private SignSealUserInfoVO    signSealUserInfo;

    public void setSignSealService(SignSealService signSealService) {
        this.signSealService = signSealService;
    }

    public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public void setSignSealUserInfo(SignSealUserInfoVO signSealUserInfo) {
        this.signSealUserInfo = signSealUserInfo;
    }

	
    /** 
     * @see java.lang.Runnable#run()
     */
	
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
        	//自动出借计划协议签章
            log.info("自动出借计划协议签章......");
            UserSealInfoVO userSealReq = new UserSealInfoVO();
            userSealReq.setIdCard(signSealUserInfo.getIdCard());
            userSealReq.setUserName(signSealUserInfo.getUserName());
            userSealReq.setUserId(signSealUserInfo.getUserId());
            log.info("自动出借计划协议签章查询是否需要制章：IdCard："+signSealUserInfo.getIdCard()+";UserName:"+signSealUserInfo.getUserName()+";UserId:"+signSealUserInfo.getUserId());
            UserSealResultVO userSealResult = signSealService.checkAndMakeSeal(userSealReq);
            String propertySymbol = signSealService.checkPropertySymbol(Integer.parseInt(signSealUserInfo.getOrderNo()));
            log.info("自动出借计划协议签章返回签章信息：CertDN："+userSealResult.getCertDN()+";CertSN:"+userSealResult.getCertSN()+";Code:"+userSealResult.getSealCode());
            if (userSealResult != null && userSealResult.isSucc()) {
                    //客户签章
                    SignSeal4PdfInfoVO signSeal4PdfReq = new SignSeal4PdfInfoVO();
                    signSeal4PdfReq.setCertDN(userSealResult.getCertDN());
                    signSeal4PdfReq.setCertSN(userSealResult.getCertSN());
                    signSeal4PdfReq.setKeyword("乙方：" + signSealUserInfo.getUserName());
                    signSeal4PdfReq.setPdfPath(signSealUserInfo.getPdfPath());
                    signSeal4PdfReq.setSealCode(userSealResult.getSealCode());
                    signSeal4PdfReq.setSealPassword(userSealResult.getSealPassword());
                    signSeal4PdfReq.setSealResson("自动出借计划协议签章");
                    String tempPdfPath = signSealUserInfo.getPdfPath();
                    String sealedPdfFile = GlobEnvUtil.get("cfca.seal.pdfDestPath")
                                           + signSealUserInfo.getUserId()
                                           + tempPdfPath.substring(tempPdfPath.lastIndexOf("/"),
                                               tempPdfPath.length() - 4) + "-sign.pdf";
                    signSeal4PdfReq.setSealedFileName(sealedPdfFile);
                    boolean signFlag = signSealService.signSeal4PdfByKeyword(signSeal4PdfReq);
                    if (!signFlag) {
                        log.error("自动出借计划协议客户签章：执行签章失败......");
                        //记录异常
                        saveUserSealInfo(signSealUserInfo.getUserId(), SealBusiness.BUY.getCode(),
                            SealStatus.FAIL.getCode(), "自动出借计划协议客户签章：执行签章失败......",
                            signSealUserInfo.getOrderNo(), null);
                    } else {
                        //公司签章
                        SignSeal4PdfInfoVO signCampanySeal4PdfReq = new SignSeal4PdfInfoVO();
                        signCampanySeal4PdfReq.setCertDN(GlobEnvUtil.get("cfca.bigangwan.seal.certDN"));
                        signCampanySeal4PdfReq.setCertSN(GlobEnvUtil.get("cfca.bigangwan.seal.certSN"));
                        signCampanySeal4PdfReq.setKeyword("甲方：杭州币港湾科技有限公司");
                        signCampanySeal4PdfReq.setPdfPath(sealedPdfFile);
                        signCampanySeal4PdfReq.setSealCode(GlobEnvUtil.get("cfca.bigangwan.seal.code"));
                        signCampanySeal4PdfReq.setSealPassword(GlobEnvUtil
                            .get("cfca.bigangwan.seal.password"));
                        signCampanySeal4PdfReq.setSealResson("自动出借计划协议签章");
                        signCampanySeal4PdfReq.setSealedFileName(sealedPdfFile);
                        boolean companySignFlag = signSealService
                            .signSeal4PdfByKeyword(signCampanySeal4PdfReq);
                        if (!companySignFlag) {
                            log.error("自动出借计划协议单位签章：执行签章失败......");
                            saveUserSealInfo(signSealUserInfo.getUserId(), SealBusiness.BUY.getCode(),
                                SealStatus.FAIL.getCode(), "自动出借计划协议单位签章：执行签章失败......",
                                signSealUserInfo.getOrderNo(), null);
                        } else {
	                        //记录签章结果信息
                        	log.error("自动出借计划协议单位签章：执行签章成功......");
	                        saveUserSealInfo(signSealUserInfo.getUserId(), SealBusiness.BUY.getCode(),
	                            SealStatus.SUCC.getCode(), null, signSealUserInfo.getOrderNo(),
	                            sealedPdfFile);
	                        log.info("自动出借计划协议签章成功......");
                        }
                    }

            } else {
                log.error("自动出借计划协议客户签章：执行证书申请或制章失败......");
                //记录异常
                saveUserSealInfo(signSealUserInfo.getUserId(), SealBusiness.BUY.getCode(),
                    SealStatus.FAIL.getCode(), "自动出借计划协议客户签章：执行证书申请或制章失败......",
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
		signSealService.addUserSealFile(userSealFile);
	}
		
	private void createSrcPdf() {
		//生成协议
		try {
			BsUser userBase = userService.findUserById(signSealUserInfo.getUserId());
			String buyPdfPath = GlobEnvUtil.get("cfca.seal.pdfSrcPath") + "/"+ userBase.getId() +"/" + userBase.getId() + "-"
						 + signSealUserInfo.getOrderNo() + "-"
			           + DateUtil.formatDateTime(new Date(), "yyyyMMddHHmmssSSS")
			           + "-buy" + ".pdf";
			log.info("自动出借计划协议生成路径：========"+buyPdfPath+"==============");
			String buyHtml = GlobEnvUtil.get("cfca.seal.buy.pdfSrcHtml") + "?investId="
			        + signSealUserInfo.getOrderNo();
			ITextPdfUtil.createHtm2Pdf(buyHtml, buyPdfPath, "自动出借计划协议", "自动出借计划协议",
			signSealUserInfo.getOrderNo());
			signSealUserInfo.setPdfPath(buyPdfPath);
			
			} catch (Exception e) {
			e.printStackTrace();
			//throw new MobwebParamException("出借服务协议生成有问题，快联系程序猿哥哥~~~");
			signSealUserInfo.setPdfPath(null);
		}
	}

}
