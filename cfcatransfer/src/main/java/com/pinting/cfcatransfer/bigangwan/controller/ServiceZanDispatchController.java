package com.pinting.cfcatransfer.bigangwan.controller;

import cfca.kt.vo.KeyPairRequestVO;
import cfca.kt.vo.KeyPairResponseVO;
import cfca.kt.vo.PFXRequestVO;
import cfca.kt.vo.PFXResponseVO;
import cfca.ra.common.vo.request.CertServiceRequestVO;
import cfca.ra.common.vo.response.CertServiceResponseVO;
import com.pinting.cfcatransfer.bigangwan.enums.SealPDFType;
import com.pinting.cfcatransfer.bigangwan.enums.SealType;
import com.pinting.cfcatransfer.bigangwan.model.*;
import com.pinting.cfcatransfer.bigangwan.model.cfca.MakeNamedSealReq;
import com.pinting.cfcatransfer.bigangwan.model.cfca.SealAutoPdfReq;
import com.pinting.cfcatransfer.bigangwan.service.KTServiceClient;
import com.pinting.cfcatransfer.bigangwan.service.RAServiceClient;
import com.pinting.cfcatransfer.bigangwan.service.SealServiceClient;
import com.pinting.cfcatransfer.bigangwan.util.BGWInConstant;
import com.pinting.cfcatransfer.bigangwan.util.CABase64;
import com.pinting.cfcatransfer.bigangwan.util.CAFileUtil;
import com.pinting.cfcatransfer.bigangwan.util.ZANInMsgUtil;
import com.pinting.cfcatransfer.enums.PTMessageEnum;
import com.pinting.cfcatransfer.exception.PTMessageException;
import com.pinting.core.util.DateUtil;
import com.pinting.core.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;

@Controller
public class ServiceZanDispatchController {

	private Logger log = LoggerFactory
			.getLogger(ServiceZanDispatchController.class);
	@Autowired
	private KTServiceClient ktServiceClient;
	@Autowired
	private RAServiceClient raServiceClient;
	@Autowired
	private SealServiceClient sealServiceClient;

	@RequestMapping("/cfcatransfer/zan")
	public @ResponseBody
	String serviceDispatch(HttpServletRequest request,
			HttpServletResponse response) {
		// 待返回报文密文
		String resp = null;
		try {
			BaseReqModel reqModel = (BaseReqModel) request
					.getAttribute("reqModel");

			switch (reqModel.getTransCode()) {
			case BGWInConstant.ZAN_MAKE_SEAL://生成证书并制章
				resp = ZANInMsgUtil.packageSuccMsg(makeSeal((MakeSealReqModel) reqModel));
				break;
			case BGWInConstant.ZAN_AUTO_SEAL://签章
				resp = ZANInMsgUtil.packageSuccMsg(autoSeal((AutoSealReqModel) reqModel));
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
			resp = ZANInMsgUtil.packageMsg(resModel);
		}
		// 返回响应报文（密文）
		return resp;
	}

	private BaseResModel makeSeal(MakeSealReqModel request) {

		MakeSealResModel result = new MakeSealResModel();
		UserCertResModel userCertResult = new UserCertResModel();
		// 1、获取p10
		KeyPairRequestVO vo = new KeyPairRequestVO();
		log.info("发起KT接口，获取p10......");
		KeyPairResponseVO keyPairResVo = ktServiceClient.createKeyPair(vo);
		if (keyPairResVo != null) {
			userCertResult.setP10(keyPairResVo.getCsr());
			userCertResult.setKeyIdentifier(keyPairResVo.getKeyIdentifier());
			// 2、申请证书及下载
			CertServiceRequestVO certServiceRequestVO = new CertServiceRequestVO();
			certServiceRequestVO.setP10(keyPairResVo.getCsr());
			certServiceRequestVO.setCertType(request.getCertType());
			certServiceRequestVO.setCustomerType(request.getCustomerType());
			certServiceRequestVO.setUserName(request.getUserName());
			certServiceRequestVO.setIdentType(request.getIdentType());
			certServiceRequestVO.setIdentNo(request.getIdCard());
			certServiceRequestVO.setBranchCode(request.getBranchCode());
			log.info("发起RA接口，申请证书及下载......");
			CertServiceResponseVO certResVo = raServiceClient
					.applyAndDownloadCert(certServiceRequestVO);
			if (certResVo != null) {
				userCertResult.setDn(certResVo.getDn());
				userCertResult.setEncryptionCert(certResVo.getEncryptionCert());
				userCertResult.setEncryptionPrivateKey(certResVo
						.getEncryptionPrivateKey());
				userCertResult.setEndTime(certResVo.getEndTime());
				userCertResult.setSequenceNo(certResVo.getSequenceNo());
				userCertResult.setSerialNo(certResVo.getSerialNo());
				userCertResult.setSignatureCert(certResVo.getSignatureCert());
				userCertResult.setStartTime(certResVo.getStartTime());
				userCertResult.setUserName(request.getUserName());
				userCertResult.setIdCard(request.getIdCard());
				// 3、获取pfx私钥证书
				PFXRequestVO pfxRequestVO = new PFXRequestVO();
				pfxRequestVO.setKeyIdentifier(userCertResult.getKeyIdentifier());
				pfxRequestVO.setSignatureCert(userCertResult.getSignatureCert());
				log.info("发起KT接口，获取pfx证书......");
				PFXResponseVO pfxResVo = ktServiceClient
						.createPFX(pfxRequestVO);
				if (pfxResVo != null) {
					userCertResult.setPfxData(pfxResVo.getPfxData());
					try {
						// 4、保存pfx证书到本地

						String tmpPfxPath = "/home/pinting/server/zan_pfx/"+
								DateUtil.formatDateTime(new Date(), "yyyyMM") +
								"/" + request.getIdCard()+".pfx";
						CAFileUtil.wirteDataToFile(tmpPfxPath,
								CABase64.decode(pfxResVo.getPfxData()));
						userCertResult.setPfxPath(tmpPfxPath);
						userCertResult.setPfxPassword("cfca1234");// 默认的pfx证书密码
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

		UserSealReqModel makeSealRequest = new UserSealReqModel();
		makeSealRequest.setIdCard(userCertResult.getIdCard());
		makeSealRequest.setImageCode(null);
		makeSealRequest.setImagePath(null);
		makeSealRequest.setPfxPassword(userCertResult.getPfxPassword());
		makeSealRequest.setPfxPath(userCertResult.getPfxPath());
		String sealCode = "BGW" + "_" + ZANInMsgUtil.generateAssignLengthNo(6);
		String sealPass = ZANInMsgUtil.generateAssignLengthNo(6);
		makeSealRequest.setSealCode(sealCode);
		makeSealRequest.setSealName(userCertResult.getUserName());
		makeSealRequest.setSealOrg("杭州赞分期商务信息咨询服务有限公司");
		makeSealRequest.setSealPassword(sealPass);
		makeSealRequest.setSealType(SealType.PERSON.getCode());
		makeSealRequest.setSealPerson("杭州赞分期商务信息咨询服务有限公司");
		UserSealResModel makeSealRes = makeUserSeal(makeSealRequest);
		if(makeSealRes == null){
			throw new PTMessageException(PTMessageEnum.MAKE_USER_SEAL_FAIL);
		}
		result.setSealCode(makeSealRequest.getSealCode());
		result.setSealPassword(makeSealRequest.getPfxPassword());
		return result;
	}

	
	public UserSealResModel makeUserSeal(UserSealReqModel request) {
		UserSealResModel result = new UserSealResModel();
        String sealType = request.getSealType();
        //制个人章
        if (SealType.PERSON.getCode().equals(sealType)) {
            MakeNamedSealReq makeSealReq = new MakeNamedSealReq();
            makeSealReq.setPfxPassword(request.getPfxPassword());
            makeSealReq.setPfxPath(request.getPfxPath());
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
		return null;
    }

	private BaseResModel autoSeal(AutoSealReqModel request) {
		//下载文件
		String pdfSrcPath = "/home/pinting/server/zan_pdf/"+
				DateUtil.formatDateTime(new Date(), "yyyyMM") +
				"/" + request.getAreementNo() +".pdf";
		File file = downLoadHttp(request.getAgreementUrl(),
				request.getAreementNo(), pdfSrcPath.substring(0, pdfSrcPath.lastIndexOf("/")));
		if(file == null){
			throw new PTMessageException(PTMessageEnum.FTP_DOWNLOAD_FAIL);
		}
		//用户签章
		SealAutoPdfReq req = new SealAutoPdfReq();
		String keyword = request.getPersonKeyword();
		if (keyword.contains("·")) {
			keyword = keyword.substring(0, keyword.indexOf("·"));
		}
		req.setKeyword(keyword);
		req.setSealCode(request.getSealCode());
		req.setSealLocation("杭州");
		req.setSealPassword(request.getSealPassword());
		req.setSealPerson("杭州赞分期商务信息咨询服务有限公司");
		req.setSealResson(request.getAgreementType());
		req.setType(SealPDFType.KEYWORD_SEAL.getCode());
		req.setPath(pdfSrcPath);
		String sealedPdfDestPath = "/home/pinting/server/zan_pdf/"+
				DateUtil.formatDateTime(new Date(), "yyyyMM") +
				"/" + request.getAreementNo() +"-sign.pdf";
		req.setSealedFileName(sealedPdfDestPath);//签章后文件路径+名称
		log.info("发起Seal接口，pdf自动签章......");
		boolean flag = sealServiceClient.sealAutoPdf(req);
		if(!flag){
			throw new PTMessageException(PTMessageEnum.AUTO_SEAL_PDF_FAIL);
		}
		//公司签章
		SealAutoPdfReq companyReq = new SealAutoPdfReq();
		String companyKeyword = request.getPersonKeyword();
		if (companyKeyword.contains("·")) {
			companyKeyword = companyKeyword.substring(0, companyKeyword.indexOf("·"));
		}
		companyReq.setKeyword(companyKeyword);
		companyReq.setSealCode(request.getCompanyCode());
		companyReq.setSealLocation("杭州");
		companyReq.setSealPassword("zan123");
		companyReq.setSealPerson("杭州赞分期商务信息咨询服务有限公司");
		companyReq.setSealResson(request.getAgreementType());
		companyReq.setType(SealPDFType.KEYWORD_SEAL.getCode());
		companyReq.setPath(sealedPdfDestPath);
		companyReq.setSealedFileName(sealedPdfDestPath);//签章后文件路径+名称
		log.info("发起Seal接口，pdf自动签章......");
		boolean companyFlag = sealServiceClient.sealAutoPdf(companyReq);
		if(!companyFlag){
			throw new PTMessageException(PTMessageEnum.AUTO_SEAL_PDF_FAIL);
		}
		AutoSealResModel res = new AutoSealResModel();
		res.setAgreementPath(sealedPdfDestPath.substring(sealedPdfDestPath.lastIndexOf("/")+1,sealedPdfDestPath.length()));
		return res;
	}

	private  File downLoadHttp(String agreementUrl, String agreementNo, String saveFile) {

		try {
			log.info("协议下载地址："+agreementUrl);
			URL url;
			url = new URL(agreementUrl);
			HttpURLConnection conn = (HttpURLConnection)url.openConnection();
			//设置超时间为3秒
			conn.setConnectTimeout(3*1000);
			//防止屏蔽程序抓取而返回403错误
			conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
			//得到输入流
			InputStream inputStream = conn.getInputStream();
			//获取自己数组
			byte[] getData = readInputStream(inputStream);
			//文件保存位置
			log.info("文件保存地址>>>>>>>>>>>>" + saveFile);
			File saveDir = new File(saveFile);
			if(!saveDir.exists()){
				saveDir.mkdir();
			}
			File file = new File(saveDir+"/"+agreementNo+".pdf");
			FileOutputStream fos = new FileOutputStream(file);
			fos.write(getData);
			if(fos!=null){
				fos.close();
			}
			if(inputStream!=null){
				inputStream.close();
			}

			return file;
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}


	/**
	 * 从输入流中获取字节数组
	 * @param inputStream
	 * @return
	 * @throws IOException
	 */
	public static  byte[] readInputStream(InputStream inputStream) throws IOException {
		byte[] buffer = new byte[1024];
		int len = 0;
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		while((len = inputStream.read(buffer)) != -1) {
			bos.write(buffer, 0, len);
		}
		bos.close();
		return bos.toByteArray();
	}

	public static void main(String[] args) {
		String pdfSrcPath = "/home/pinting/server/cfcatest/q0001/file.pdf";
		pdfSrcPath = pdfSrcPath.substring(0,pdfSrcPath.indexOf("server")+6) + "/ftp"
				+ pdfSrcPath.substring(pdfSrcPath.indexOf("server")+6, pdfSrcPath.length()) ;
		System.out.println(pdfSrcPath);
	}

}
