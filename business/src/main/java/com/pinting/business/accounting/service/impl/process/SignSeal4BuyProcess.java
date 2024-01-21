package com.pinting.business.accounting.service.impl.process;

import com.pinting.business.accounting.loan.enums.PartnerEnum;
import com.pinting.business.accounting.service.SignSealService;
import com.pinting.business.enums.SealBusiness;
import com.pinting.business.enums.SealStatus;
import com.pinting.business.model.BsUser;
import com.pinting.business.model.vo.*;
import com.pinting.business.service.site.ProductService;
import com.pinting.business.service.site.UserService;
import com.pinting.business.util.Constants;
import com.pinting.business.util.ITextPdfUtil;
import com.pinting.business.util.SignAlarmUtil;
import com.pinting.core.util.DateUtil;
import com.pinting.core.util.GlobEnvUtil;
import com.pinting.core.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;


/**
 * 普通产品签章
 * @Project: business
 * @Title: SignSeal4BuyProcess.java
 * @author dingpf
 * @date 2016-7-6 下午8:25:37
 * @Copyright: 2016 BiGangWan.com Inc. All rights reserved.
 */
public class SignSeal4BuyProcess implements Runnable {
	private Logger log = LoggerFactory.getLogger(SignSeal4BuyProcess.class);
    private SignSealService     signSealService;
    private UserService	userService;
    private SignSealUserInfoVO    signSealUserInfo;
	private ProductService productService;

    public void setSignSealService(SignSealService signSealService) {
        this.signSealService = signSealService;
    }

    public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public void setSignSealUserInfo(SignSealUserInfoVO signSealUserInfo) {
        this.signSealUserInfo = signSealUserInfo;
    }
	
    public void setProductService(ProductService productService) {
		this.productService = productService;
	}

    /** 
     * @see java.lang.Runnable#run()
     */
    @Override
    public void run() {
    	//生成协议源文件，文件会同步到CFCATransfer服务器
    	boolean resultFlag = false;
    	
        createSrcPdf();
        try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
        if (!StringUtil.isEmpty(signSealUserInfo.getPdfPath())) {
        	//借服务协议签章
            log.info("开始出借服务协议签章......");
            UserSealInfoVO userSealReq = new UserSealInfoVO();
            userSealReq.setIdCard(signSealUserInfo.getIdCard());
            userSealReq.setUserName(signSealUserInfo.getUserName());
            userSealReq.setUserId(signSealUserInfo.getUserId());
            UserSealResultVO userSealResult = signSealService.checkAndMakeSeal(userSealReq);
            String propertySymbol = signSealService.checkPropertySymbol(Integer.parseInt(signSealUserInfo.getOrderNo()));
            
            if (userSealResult != null && userSealResult.isSucc()) {
	                //客户签章
	                SignSeal4PdfInfoVO signSeal4PdfReq = new SignSeal4PdfInfoVO();
	                signSeal4PdfReq.setCertDN(userSealResult.getCertDN());
	                signSeal4PdfReq.setCertSN(userSealResult.getCertSN());
					if(Constants.PROPERTY_SYMBOL_YUN_DAI_SELF.equals(propertySymbol) ||
							Constants.PROPERTY_SYMBOL_7_DAI_SELF.equals(propertySymbol)) {
						signSeal4PdfReq.setKeyword("甲方（委托方）：" + signSealUserInfo.getUserName());
					}else {
						signSeal4PdfReq.setKeyword("甲方：" + signSealUserInfo.getUserName());
					}
	                signSeal4PdfReq.setPdfPath(signSealUserInfo.getPdfPath());
	                signSeal4PdfReq.setSealCode(userSealResult.getSealCode());
	                signSeal4PdfReq.setSealPassword(userSealResult.getSealPassword());

					if(Constants.PROPERTY_SYMBOL_YUN_DAI_SELF.equals(propertySymbol) ||
							Constants.PROPERTY_SYMBOL_7_DAI_SELF.equals(propertySymbol)) {
						signSeal4PdfReq.setSealResson("授权委托书签章");
					}else {
						signSeal4PdfReq.setSealResson("出借服务协议签章");
					}

	                String tempPdfPath = signSealUserInfo.getPdfPath();
	                String sealedPdfFile = GlobEnvUtil.get("cfca.seal.pdfDestPath")
	                                       + signSealUserInfo.getUserId()
	                                       + tempPdfPath.substring(tempPdfPath.lastIndexOf("/"),
	                                           tempPdfPath.length() - 4) + "-sign.pdf";
	                signSeal4PdfReq.setSealedFileName(sealedPdfFile);
	                boolean signFlag = signSealService.signSeal4PdfByKeyword(signSeal4PdfReq);
	                if (!signFlag) {

						if(Constants.PROPERTY_SYMBOL_YUN_DAI_SELF.equals(propertySymbol) ||
								Constants.PROPERTY_SYMBOL_7_DAI_SELF.equals(propertySymbol)) {
							log.error("授权委托书客户签章：执行签章失败......");
							//记录异常
							saveUserSealInfo(signSealUserInfo.getUserId(), SealBusiness.BUY.getCode(),
									SealStatus.FAIL.getCode(), "授权委托书客户签章：执行签章失败......",
									signSealUserInfo.getOrderNo(), null);
						}else {
							log.error("出借服务协议客户签章：执行签章失败......");
							//记录异常
							saveUserSealInfo(signSealUserInfo.getUserId(), SealBusiness.BUY.getCode(),
									SealStatus.FAIL.getCode(), "出借服务协议客户签章：执行签章失败......",
									signSealUserInfo.getOrderNo(), null);
						}

	                } else {
	                    //公司签章
	                    SignSeal4PdfInfoVO signCampanySeal4PdfReq = new SignSeal4PdfInfoVO();
	                    signCampanySeal4PdfReq.setCertDN(GlobEnvUtil.get("cfca.bigangwan.seal.certDN"));
	                    signCampanySeal4PdfReq.setCertSN(GlobEnvUtil.get("cfca.bigangwan.seal.certSN"));

	                    if (Constants.PROPERTY_SYMBOL_YUN_DAI_SELF.equals(propertySymbol)) {
	                    	signCampanySeal4PdfReq.setKeyword("乙方（受托方）：杭州币港湾科技有限公司");
	                    } else if (Constants.PROPERTY_SYMBOL_ZSD.equals(propertySymbol)) {
							signCampanySeal4PdfReq.setKeyword("乙方：杭州币港湾科技有限公司");
						} else if(PartnerEnum.SEVEN_DAI_SELF.getCode().equals(propertySymbol)) {
							signCampanySeal4PdfReq.setKeyword("乙方（受托方）：杭州币港湾科技有限公司");
						} else {
	                    	signCampanySeal4PdfReq.setKeyword("丙方：杭州币港湾科技有限公司");
						}
	                    signCampanySeal4PdfReq.setPdfPath(sealedPdfFile);
	                    signCampanySeal4PdfReq.setSealCode(GlobEnvUtil.get("cfca.bigangwan.seal.code"));
	                    signCampanySeal4PdfReq.setSealPassword(GlobEnvUtil
	                        .get("cfca.bigangwan.seal.password"));

						if (Constants.PROPERTY_SYMBOL_YUN_DAI_SELF.equals(propertySymbol) ||
								PartnerEnum.SEVEN_DAI_SELF.getCode().equals(propertySymbol)) {
							signCampanySeal4PdfReq.setSealResson("授权委托书签章");
						}else {
							signCampanySeal4PdfReq.setSealResson("出借服务协议签章");
						}

	                    signCampanySeal4PdfReq.setSealedFileName(sealedPdfFile);
	                    boolean companySignFlag = signSealService
	                        .signSeal4PdfByKeyword(signCampanySeal4PdfReq);
	                    if (!companySignFlag) {
							if (Constants.PROPERTY_SYMBOL_YUN_DAI_SELF.equals(propertySymbol) ||
									PartnerEnum.SEVEN_DAI_SELF.getCode().equals(propertySymbol)) {
								log.error("授权委托书单位签章：执行签章失败......");
								saveUserSealInfo(signSealUserInfo.getUserId(), SealBusiness.BUY.getCode(),
										SealStatus.FAIL.getCode(), "授权委托书单位签章：执行签章失败......",
										signSealUserInfo.getOrderNo(), null);
							}else {
								log.error("出借服务协议单位签章：执行签章失败......");
								saveUserSealInfo(signSealUserInfo.getUserId(), SealBusiness.BUY.getCode(),
										SealStatus.FAIL.getCode(), "出借服务协议单位签章：执行签章失败......",
										signSealUserInfo.getOrderNo(), null);
							}

	                    } else {
	                    	/**
	                    	 * 检查发生产品购买的资金接收方标识
	                    	 * 如果接收方是云贷那么企业章签云贷的签章（现在云贷的签章的秘钥未拿到暂时不进行任何操作）
	                    	 * 如果接收方是七贷那么企业章签七贷的签章
	                    	 */
	                    	
	                    	if (Constants.PROPERTY_SYMBOL_7_DAI.equals(propertySymbol)) {
								//七贷签章
	                    		SignSeal4PdfInfoVO sign7daiSeal4PdfReq = new SignSeal4PdfInfoVO();
	                    		sign7daiSeal4PdfReq.setCertDN(GlobEnvUtil.get("cfca.7dai.seal.certDN"));
	                    		sign7daiSeal4PdfReq.setCertSN(GlobEnvUtil.get("cfca.7dai.seal.certSN"));
	                    		sign7daiSeal4PdfReq.setKeyword("乙方：深圳市前海龙汇通互联网金融服务有限公司");
	                    		sign7daiSeal4PdfReq.setPdfPath(sealedPdfFile);
	                    		sign7daiSeal4PdfReq.setSealCode(GlobEnvUtil.get("cfca.7dai.seal.code"));
	                    		sign7daiSeal4PdfReq.setSealPassword(GlobEnvUtil
	                                .get("cfca.7dai.seal.password"));
	                    		sign7daiSeal4PdfReq.setSealResson("出借服务协议签章");
	                    		sign7daiSeal4PdfReq.setSealedFileName(sealedPdfFile);
	                            boolean enterpriseSignFlag = signSealService
	                                .signSeal4PdfByKeyword(sign7daiSeal4PdfReq);
	                    		
	                            if (!enterpriseSignFlag) {
	                                log.error("出借服务协议企业签章：执行七贷签章失败......");
	                                saveUserSealInfo(signSealUserInfo.getUserId(), SealBusiness.BUY.getCode(),
	                                    SealStatus.FAIL.getCode(), "出借服务协议企业签章：执行七贷签章失败......",
	                                    signSealUserInfo.getOrderNo(), null);
	                            }else{
	                        		
	    	                        //记录签章结果信息
	    	                        saveUserSealInfo(signSealUserInfo.getUserId(), SealBusiness.BUY.getCode(),
	    	                            SealStatus.SUCC.getCode(), null, signSealUserInfo.getOrderNo(),
	    	                            sealedPdfFile);
	    	                        resultFlag = true;
	    	                        log.info("出借服务协议签章成功......");
	                            }

							}else if (Constants.PROPERTY_SYMBOL_YUN_DAI.equals(propertySymbol)) {
								//云贷签章
								SignSeal4PdfInfoVO signYunDaiSeal4PdfReq = new SignSeal4PdfInfoVO();
								Date date = productService.queryYunDaiChangeNameDate();
								if (new Date().compareTo(date) <= 0) {
									log.info("出借服务协议企业签章：执行云贷老签章：达飞普惠财富投资管理（北京）有限公司");
		                    		signYunDaiSeal4PdfReq.setCertDN(GlobEnvUtil.get("cfca.yundai.seal.certDN"));
		                    		signYunDaiSeal4PdfReq.setCertSN(GlobEnvUtil.get("cfca.yundai.seal.certSN"));
		                    		signYunDaiSeal4PdfReq.setKeyword("乙方：达飞普惠财富投资管理（北京）有限公司");
		                    		signYunDaiSeal4PdfReq.setPdfPath(sealedPdfFile);
		                    		signYunDaiSeal4PdfReq.setSealCode(GlobEnvUtil.get("cfca.yundai.seal.code"));
		                    		signYunDaiSeal4PdfReq.setSealPassword(GlobEnvUtil.get("cfca.yundai.seal.password"));
								}else {
									log.info("出借服务协议企业签章：执行云贷新签章：达飞云贷科技（北京）有限公司");
		                    		signYunDaiSeal4PdfReq.setCertDN(GlobEnvUtil.get("cfca.yundai.new.seal.certDN"));
		                    		signYunDaiSeal4PdfReq.setCertSN(GlobEnvUtil.get("cfca.yundai.new.seal.certSN"));
		                    		signYunDaiSeal4PdfReq.setKeyword("乙方：达飞云贷科技（北京）有限公司");
		                    		signYunDaiSeal4PdfReq.setPdfPath(sealedPdfFile);
		                    		signYunDaiSeal4PdfReq.setSealCode(GlobEnvUtil.get("cfca.yundai.new.seal.code"));
		                    		signYunDaiSeal4PdfReq.setSealPassword(GlobEnvUtil.get("cfca.yundai.new.seal.password"));
								}
								
	                    		

	                    		signYunDaiSeal4PdfReq.setSealResson("出借服务协议签章");
	                    		signYunDaiSeal4PdfReq.setSealedFileName(sealedPdfFile);
	                            boolean enterpriseSignFlag = signSealService
	                                .signSeal4PdfByKeyword(signYunDaiSeal4PdfReq);
	                    		
	                            if (!enterpriseSignFlag) {
	                                log.error("出借服务协议企业签章：执行云贷签章失败......");
	                                saveUserSealInfo(signSealUserInfo.getUserId(), SealBusiness.BUY.getCode(),
	                                    SealStatus.FAIL.getCode(), "出借服务协议企业签章：执行云贷签章失败......",
	                                    signSealUserInfo.getOrderNo(), null);
	                            }else{
	                        		
	    	                        //记录签章结果信息
	    	                        saveUserSealInfo(signSealUserInfo.getUserId(), SealBusiness.BUY.getCode(),
	    	                            SealStatus.SUCC.getCode(), null, signSealUserInfo.getOrderNo(),
	    	                            sealedPdfFile);
	    	                        resultFlag = true;
	    	                        log.info("出借服务协议签章成功......");
	                            }
								
							}else if (Constants.PROPERTY_SYMBOL_YUN_DAI_SELF.equals(propertySymbol) ||
									Constants.PROPERTY_SYMBOL_ZSD.equals(propertySymbol) ||
									PartnerEnum.SEVEN_DAI_SELF.getCode().equals(propertySymbol)) {
								//云贷自主||赞时贷||7贷放款签章
                                log.error(PartnerEnum.getEnumByCode(propertySymbol).getName() + "不需要进行" + PartnerEnum.getEnumByCode(propertySymbol).getName() + "签章........");
    	                        saveUserSealInfo(signSealUserInfo.getUserId(), SealBusiness.BUY.getCode(),
	    	                            SealStatus.SUCC.getCode(), null, signSealUserInfo.getOrderNo(),
	    	                            sealedPdfFile);
	    	                        resultFlag = true;

								if(Constants.PROPERTY_SYMBOL_ZSD.equals(propertySymbol)) {
									log.info("出借服务协议签章成功......");
								}else {
									log.info("授权委托书签章成功......");
								}

							}else {

								if (Constants.PROPERTY_SYMBOL_YUN_DAI_SELF.equals(propertySymbol) ||
										PartnerEnum.SEVEN_DAI_SELF.getCode().equals(propertySymbol)) {
									log.error("授权委托书企业签章：查询不到对应的资金接收方信息......");
									saveUserSealInfo(signSealUserInfo.getUserId(), SealBusiness.BUY.getCode(),
											SealStatus.FAIL.getCode(), "授权委托书企业签章：查询不到对应的资金接收方信息......",
											signSealUserInfo.getOrderNo(), null);
								}else {
									log.error("出借服务协议企业签章：查询不到对应的资金接收方信息......");
									saveUserSealInfo(signSealUserInfo.getUserId(), SealBusiness.BUY.getCode(),
											SealStatus.FAIL.getCode(), "出借服务协议企业签章：查询不到对应的资金接收方信息......",
											signSealUserInfo.getOrderNo(), null);
								}

							} 

	                    }
	                }
					
				

            } else {
                log.error("出借服务协议客户签章：执行证书申请或制章失败......");
                //记录异常
                saveUserSealInfo(signSealUserInfo.getUserId(), SealBusiness.BUY.getCode(),
                    SealStatus.FAIL.getCode(), "出借服务协议客户签章：执行证书申请或制章失败......",
                    signSealUserInfo.getOrderNo(), null);
            }
        }

        if (!resultFlag) {
        	SignAlarmUtil.alarm(false);
		}else {
			SignAlarmUtil.alarm(true);
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
            String buyHtml = GlobEnvUtil.get("cfca.seal.buy.pdfSrcHtml") + "?investId="
                              + signSealUserInfo.getOrderNo();
			String propertySymbol = signSealService.checkPropertySymbol(Integer.parseInt(signSealUserInfo.getOrderNo()));
			if(Constants.PROPERTY_SYMBOL_YUN_DAI_SELF.equals(propertySymbol) ||
					PartnerEnum.SEVEN_DAI_SELF.getCode().equals(propertySymbol)) {
				ITextPdfUtil.createHtm2Pdf(buyHtml, buyPdfPath, "授权委托书", "授权委托书",
						signSealUserInfo.getOrderNo());
			}else {
				ITextPdfUtil.createHtm2Pdf(buyHtml, buyPdfPath, "出借服务协议", "出借服务协议",
						signSealUserInfo.getOrderNo());
			}
            signSealUserInfo.setPdfPath(buyPdfPath);
            
        } catch (Exception e) {
            e.printStackTrace();
            //throw new MobwebParamException("出借服务协议生成有问题，快联系程序猿哥哥~~~");
            signSealUserInfo.setPdfPath(null);
        }
    }
}
