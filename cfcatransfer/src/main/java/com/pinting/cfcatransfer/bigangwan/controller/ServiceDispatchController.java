package com.pinting.cfcatransfer.bigangwan.controller;

import cfca.kt.vo.KeyPairRequestVO;
import cfca.kt.vo.KeyPairResponseVO;
import cfca.kt.vo.PFXRequestVO;
import cfca.kt.vo.PFXResponseVO;
import cfca.ra.common.vo.request.CertServiceRequestVO;
import cfca.ra.common.vo.response.CertServiceResponseVO;
import com.pinting.cfcatransfer.bigangwan.enums.SealType;
import com.pinting.cfcatransfer.bigangwan.model.*;
import com.pinting.cfcatransfer.bigangwan.model.cfca.MakeNamedSealReq;
import com.pinting.cfcatransfer.bigangwan.model.cfca.MakeSealReq;
import com.pinting.cfcatransfer.bigangwan.model.cfca.SealAutoPdfReq;
import com.pinting.cfcatransfer.bigangwan.service.KTServiceClient;
import com.pinting.cfcatransfer.bigangwan.service.RAServiceClient;
import com.pinting.cfcatransfer.bigangwan.service.SealServiceClient;
import com.pinting.cfcatransfer.bigangwan.util.BGWInConstant;
import com.pinting.cfcatransfer.bigangwan.util.BGWInMsgUtil;
import com.pinting.cfcatransfer.bigangwan.util.CABase64;
import com.pinting.cfcatransfer.bigangwan.util.CAFileUtil;
import com.pinting.cfcatransfer.enums.PTMessageEnum;
import com.pinting.cfcatransfer.exception.PTMessageException;
import com.pinting.core.util.GlobEnvUtil;
import com.pinting.core.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Date;

/**
 * 根据报文交易码，进行服务分发
 * 
 * @Project: gateway
 * @Title: ServiceDispatchController.java
 * @author dingpf
 * @date 2015-2-10 下午6:45:28
 * @Copyright: 2015 BiGangWan.com Inc. All rights reserved.
 */
@Controller
public class ServiceDispatchController {

	private Logger log = LoggerFactory
			.getLogger(ServiceDispatchController.class);
	@Autowired
	private KTServiceClient ktServiceClient;
	@Autowired
	private RAServiceClient raServiceClient;
	@Autowired
	private SealServiceClient sealServiceClient;

	@RequestMapping("/cfcatransfer/bigangwan")
	public @ResponseBody
	String serviceDispatch(HttpServletRequest request,
			HttpServletResponse response) {
		// 待返回报文密文
		String resp = null;
		try {
			BaseReqModel reqModel = (BaseReqModel) request
					.getAttribute("reqModel");

			switch (reqModel.getTransCode()) {
			case BGWInConstant.USER_CERT://生成证书
				resp = BGWInMsgUtil.packageSuccMsg(userCert((UserCertReqModel) reqModel));
				break;
			case BGWInConstant.USER_SEAL://制章
				resp = BGWInMsgUtil.packageSuccMsg(makeUserSeal((UserSealReqModel) reqModel));
				break;
			case BGWInConstant.SEAL_AUTO_PDF://签章
				resp = BGWInMsgUtil.packageSuccMsg(autoSealPdf((SealAutoPdfReqModel) reqModel));
				break;
			default:
				break;
			}
		} catch (Exception e) {
			e.printStackTrace();
			// 组装报错报文返回
			String respMsg = StringUtil.left(e.getMessage(), 20);
			if(e instanceof PTMessageException){
				PTMessageException pte = (PTMessageException)e;
				respMsg = pte.getErrMessage();
			}
			BaseResModel resModel = new BaseResModel();
			resModel.setRespCode(BGWInConstant.RETURN_CODE_FAIL);
			resModel.setRespMsg(StringUtil.isEmpty(respMsg)?"系统异常":respMsg);
			resModel.setResponseTime(new Date());
			resp = BGWInMsgUtil.packageMsg(resModel);
		}
		// 返回响应报文（密文）
		return resp;
	}
	
	@RequestMapping("/cfcatransfer/upload")
	public  @ResponseBody String upload(@RequestParam("file") CommonsMultipartFile[] files, HttpServletRequest request){
		for(int i = 0;i<files.length;i++){
			log.info("获得文件名：" + files[i].getOriginalFilename());
			if(!files[i].isEmpty()){
				int pre = (int) System.currentTimeMillis();
				try {
					//拿到输出流，同时重命名上传的文件
					FileOutputStream os = new FileOutputStream(GlobEnvUtil.get("cfca.seal.pdfDestPath")
							+ request.getParameter("userId") + "/" + files[i].getOriginalFilename());
					//拿到上传文件的输入流
					InputStream in = files[i].getInputStream();
					//写文件
					byte[] b = new byte[512];
					int len = -1;
					while((len = in.read(b)) != -1){
						os.write(b, 0, len);
					}
					os.flush();
					os.close();
					in.close();
					int finaltime = (int) System.currentTimeMillis();
					log.info("上传文件耗时：" + (finaltime - pre) + "ms");
					
				} catch (Exception e) {
					log.error("上传文件错误", e);
					return "N";
				}
			}
		}
		
		return "Y";
	}
	

	public UserCertResModel userCert(UserCertReqModel request) {
		UserCertResModel result = new UserCertResModel();
		// 1、获取p10
		KeyPairRequestVO vo = new KeyPairRequestVO();
		log.info("发起KT接口，获取p10......");
		KeyPairResponseVO keyPairResVo = ktServiceClient.createKeyPair(vo);
		if (keyPairResVo != null) {
			result.setP10(keyPairResVo.getCsr());
			result.setKeyIdentifier(keyPairResVo.getKeyIdentifier());
			// 2、申请证书及下载
			CertServiceRequestVO certServiceRequestVO = new CertServiceRequestVO();
			certServiceRequestVO.setP10(keyPairResVo.getCsr());
			certServiceRequestVO.setCertType(request.getCertType());
			certServiceRequestVO.setCustomerType(request.getCustomerType());
			certServiceRequestVO.setUserName(request.getUserName());
			certServiceRequestVO.setIdentType(request.getIdentType());
			certServiceRequestVO.setIdentNo(request.getIdentNo());
			certServiceRequestVO.setBranchCode(request.getBranchCode());
			log.info("发起RA接口，申请证书及下载......");
			CertServiceResponseVO certResVo = raServiceClient
					.applyAndDownloadCert(certServiceRequestVO);
			if (certResVo != null) {
				result.setDn(certResVo.getDn());
				result.setEncryptionCert(certResVo.getEncryptionCert());
				result.setEncryptionPrivateKey(certResVo
						.getEncryptionPrivateKey());
				result.setEndTime(certResVo.getEndTime());
				result.setSequenceNo(certResVo.getSequenceNo());
				result.setSerialNo(certResVo.getSerialNo());
				result.setSignatureCert(certResVo.getSignatureCert());
				result.setStartTime(certResVo.getStartTime());
				result.setUserName(request.getUserName());
				result.setIdCard(request.getIdentNo());
				// 3、获取pfx私钥证书
				PFXRequestVO pfxRequestVO = new PFXRequestVO();
				pfxRequestVO.setKeyIdentifier(result.getKeyIdentifier());
				pfxRequestVO.setSignatureCert(result.getSignatureCert());
				log.info("发起KT接口，获取pfx证书......");
				PFXResponseVO pfxResVo = ktServiceClient
						.createPFX(pfxRequestVO);
				if (pfxResVo != null) {
					result.setPfxData(pfxResVo.getPfxData());
					try {
						// 4、保存pfx证书到本地
						
						String tmpPfxPath = request.getPfxPath();
				        String path = tmpPfxPath.substring(0,tmpPfxPath.lastIndexOf("/"));
				        String userIdString  = path.substring(path.lastIndexOf("/")+1,path.length());
			            String pfxPath = GlobEnvUtil.get("cfca.seal.pfxSavePath")
			            		+ userIdString
			                    + tmpPfxPath.substring(tmpPfxPath.lastIndexOf("/"),
			                    		tmpPfxPath.length());
						
						CAFileUtil.wirteDataToFile(pfxPath,
								CABase64.decode(pfxResVo.getPfxData()));
						result.setPfxPath(pfxPath);
						result.setPfxPassword("cfca1234");// 默认的pfx证书密码
					} catch (Exception e) {
						e.printStackTrace();
						throw new PTMessageException(PTMessageEnum.PFX_SAVE_FAIL);
					}
				} else {
					throw new PTMessageException(PTMessageEnum.PFX_SAVE_FAIL);
				}
			} else {
				throw new PTMessageException(PTMessageEnum.APPLY_AND_DOWNLOAD_CERT_FAIL);
			}
		} else {
			throw new PTMessageException(PTMessageEnum.APPLY_P10_FAIL);
		}
		
		return result;
	}
	
	
	public UserSealResModel makeUserSeal(UserSealReqModel request) {
		UserSealResModel result = new UserSealResModel();
        String sealType = request.getSealType();
        //制个人章
        if (SealType.PERSON.getCode().equals(sealType)) {
            MakeNamedSealReq makeSealReq = new MakeNamedSealReq();
            makeSealReq.setPfxPassword(request.getPfxPassword());
            String tmpPfxPath = request.getPfxPath();
            String path = tmpPfxPath.substring(0,tmpPfxPath.lastIndexOf("/"));
            String userIdString  = path.substring(path.lastIndexOf("/")+1,path.length());
            String pfxPath = GlobEnvUtil.get("cfca.seal.pfxSavePath")
            		+ userIdString
                    + tmpPfxPath.substring(tmpPfxPath.lastIndexOf("/"),
                    		tmpPfxPath.length());
            makeSealReq.setPfxPath(pfxPath);
            makeSealReq.setSealCode(request.getSealCode());
            makeSealReq.setSealName(request.getSealName());
            makeSealReq.setSealOrg(request.getSealOrg());
            makeSealReq.setSealPassword(request.getSealPassword());
            makeSealReq.setSealPerson(request.getSealPerson());
            log.info("发起Seal接口，制个人章......");
            boolean flag = sealServiceClient.makeNamedSeal(makeSealReq);
            if(!flag){
            	throw new PTMessageException(PTMessageEnum.MAKE_USER_SEAL_FAIL);
            }
            return result;
        }
        //普通制章
        else {
            MakeSealReq makeSealReq = new MakeSealReq();
            makeSealReq.setImageCode(request.getImageCode());
            makeSealReq.setImagePath(request.getImagePath());
            makeSealReq.setOrgCode(request.getOrgCode());
            makeSealReq.setPfxPassword(request.getPfxPassword());
            makeSealReq.setSealCode(request.getSealCode());
            makeSealReq.setSealName(request.getSealName());
            makeSealReq.setSealOrg(request.getSealOrg());
            makeSealReq.setSealPassword(request.getSealPassword());
            makeSealReq.setSealPerson(request.getSealPerson());
            makeSealReq.setSealType(request.getSealType());
            log.info("发起Seal接口，普通制章......");
            boolean flag = sealServiceClient.makeSeal(makeSealReq);
            if(!flag){
            	throw new PTMessageException(PTMessageEnum.MAKE_USER_SEAL_FAIL);
            }
            return result;
        }
    }
	
	public SealAutoPdfResModel autoSealPdf(SealAutoPdfReqModel request) {
        SealAutoPdfReq req = new SealAutoPdfReq();
        req.setCertDN(request.getCertDN());
        req.setCertificationLevel(request.getCertificationLevel());
        req.setCertSN(request.getCertSN());
        String keyword = request.getKeyword();
        if (keyword.contains("·")) {
            keyword = keyword.substring(0, keyword.indexOf("·"));
        }
        req.setKeyword(keyword);
        req.setLocationStyle(request.getLocationStyle());
        req.setlX(request.getlX());
        req.setlY(request.getlY());
        req.setOffsetX(request.getOffsetX());
        req.setOffsetY(request.getOffsetY());
        req.setPage(request.getPage());
        
        String tempPdfPath = request.getPath();
        String pdfSrcPath = "";
        
        if ("Y".equals(request.getIsNew())) {
        	pdfSrcPath = GlobEnvUtil.get("cfca.seal.new.pdfSrcPath")+tempPdfPath;
		}else {
	        String path = tempPdfPath.substring(0,tempPdfPath.lastIndexOf("/"));
	        String userIdString  = path.substring(path.lastIndexOf("/")+1,path.length());
	        
	        if (tempPdfPath.contains("cfca_7dai")) {
	        	if (tempPdfPath.contains("cfca_7dai_anyrepay")) {
	        		pdfSrcPath = GlobEnvUtil.get("cfca.seal.7dai.anyrepay.pdfSrcPath")+userIdString
	        				+ tempPdfPath.substring(tempPdfPath.lastIndexOf("/"),
	        						tempPdfPath.length());
	        	} else {        		
	        		pdfSrcPath = GlobEnvUtil.get("cfca.seal.7dai.pdfSrcPath")+userIdString
	        				+ tempPdfPath.substring(tempPdfPath.lastIndexOf("/"),
	        						tempPdfPath.length());
	        	}
			}else {
				pdfSrcPath = GlobEnvUtil.get("cfca.seal.pdfSrcPath")+userIdString
	            + tempPdfPath.substring(tempPdfPath.lastIndexOf("/"),
	                tempPdfPath.length());
			}
	        if(tempPdfPath.contains("ftp")){
				pdfSrcPath = pdfSrcPath.substring(0,pdfSrcPath.indexOf("server")+6) + "/ftp"
						+ pdfSrcPath.substring(pdfSrcPath.indexOf("server")+6, pdfSrcPath.length());
			}
		}


        req.setPath(pdfSrcPath);
        req.setSealCode(request.getSealCode());
        req.setSealLocation(request.getSealLocation());
        req.setSealPassword(request.getSealPassword());
        req.setSealPerson(request.getSealPerson());
        req.setSealResson(request.getSealResson());
        req.setType(request.getType());
        
        String tempSealedPdfPath = request.getSealedFileName();
        String sealedPdfDestPath ="";
        if ("Y".equals(request.getIsNew())) {
        	sealedPdfDestPath = GlobEnvUtil.get("cfca.seal.new.pdfDestPath")+tempSealedPdfPath;
		}else {
	        String pathSealed = tempSealedPdfPath.substring(0,tempSealedPdfPath.lastIndexOf("/"));
	        String userIdSealed  = pathSealed.substring(pathSealed.lastIndexOf("/")+1,pathSealed.length());
	        
	        if (tempPdfPath.contains("cfca_7dai")) {
	        	if (tempPdfPath.contains("cfca_7dai_anyrepay")) {
	        		sealedPdfDestPath = GlobEnvUtil.get("cfca.seal.7dai.anyrepay.pdfDestPath")
	  					   + userIdSealed + tempSealedPdfPath.substring(tempSealedPdfPath.lastIndexOf("/"),
	                		   tempSealedPdfPath.length());
	        	} else {        		
	        		sealedPdfDestPath = GlobEnvUtil.get("cfca.seal.7dai.pdfDestPath")
	 					   + userIdSealed + tempSealedPdfPath.substring(tempSealedPdfPath.lastIndexOf("/"),
	               		   tempSealedPdfPath.length());
	        	}
	        }else {
	            sealedPdfDestPath = GlobEnvUtil.get("cfca.seal.pdfDestPath")
						   + userIdSealed 
	                    + tempSealedPdfPath.substring(tempSealedPdfPath.lastIndexOf("/"),
	                 		   tempSealedPdfPath.length());
			}

			if(tempSealedPdfPath.contains("ftp")){
				sealedPdfDestPath = sealedPdfDestPath.substring(0,sealedPdfDestPath.indexOf("server")+6) + "/ftp"
						+ sealedPdfDestPath.substring(sealedPdfDestPath.indexOf("server")+6, sealedPdfDestPath.length());
			}
		}
        

        req.setSealedFileName(sealedPdfDestPath);//签章后文件路径+名称
        log.info("发起Seal接口，pdf自动签章......");
        boolean flag = sealServiceClient.sealAutoPdf(req);
        if(!flag){
        	throw new PTMessageException(PTMessageEnum.AUTO_SEAL_PDF_FAIL);
        }
        return new SealAutoPdfResModel();
    }

	public static void main(String[] args) {
		String tempPdfPath = "D:/ftp/bigangwan/BUY/201804/10000561/1000561-141451-20170920175015353-buy.pdf";
        String path = tempPdfPath.substring(0,tempPdfPath.lastIndexOf("/"));
        String userIdString  = path.substring(path.lastIndexOf("/")+1,path.length());
        String pdfSrcPath = "";
        if (tempPdfPath.contains("cfca_7dai")) {
        	if (tempPdfPath.contains("cfca_7dai_anyrepay")) {
        		pdfSrcPath = GlobEnvUtil.get("cfca.seal.7dai.anyrepay.pdfSrcPath")+userIdString
        				+ tempPdfPath.substring(tempPdfPath.lastIndexOf("/"),
        						tempPdfPath.length());
        	} else {        		
        		pdfSrcPath = GlobEnvUtil.get("cfca.seal.7dai.pdfSrcPath")+userIdString
        				+ tempPdfPath.substring(tempPdfPath.lastIndexOf("/"),
        						tempPdfPath.length());
        	}
		}else {
			pdfSrcPath = GlobEnvUtil.get("cfca.seal.pdfSrcPath")+userIdString
            + tempPdfPath.substring(tempPdfPath.lastIndexOf("/"),
                tempPdfPath.length());
		}
        if(tempPdfPath.contains("ftp")){
			pdfSrcPath = pdfSrcPath.substring(0,pdfSrcPath.indexOf("server")+6) + "/ftp"
					+ pdfSrcPath.substring(pdfSrcPath.indexOf("server")+6, pdfSrcPath.length());
		}

        System.out.println(pdfSrcPath);
	}

}
