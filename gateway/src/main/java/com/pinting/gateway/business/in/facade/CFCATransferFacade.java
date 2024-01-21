package com.pinting.gateway.business.in.facade;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pinting.gateway.cfca.out.model.SealAutoPdfReqModel;
import com.pinting.gateway.cfca.out.model.SealAutoPdfResModel;
import com.pinting.gateway.cfca.out.model.UserCertReqModel;
import com.pinting.gateway.cfca.out.model.UserCertResModel;
import com.pinting.gateway.cfca.out.model.UserSealReqModel;
import com.pinting.gateway.cfca.out.model.UserSealResModel;
import com.pinting.gateway.cfca.out.service.SendDafyCFCATransferService;
import com.pinting.gateway.cfca.out.util.CFCAOutConstant;
import com.pinting.gateway.enums.PTMessageEnum;
import com.pinting.gateway.exception.PTMessageException;
import com.pinting.gateway.hessian.message.dafy.B2GReqMsg_CFCATransfer_SealAutoPdf;
import com.pinting.gateway.hessian.message.dafy.B2GReqMsg_CFCATransfer_UserCert;
import com.pinting.gateway.hessian.message.dafy.B2GReqMsg_CFCATransfer_UserSeal;
import com.pinting.gateway.hessian.message.dafy.B2GResMsg_CFCATransfer_SealAutoPdf;
import com.pinting.gateway.hessian.message.dafy.B2GResMsg_CFCATransfer_UserCert;
import com.pinting.gateway.hessian.message.dafy.B2GResMsg_CFCATransfer_UserSeal;

@Component("CFCATransfer")
public class CFCATransferFacade {
	
	@Autowired
	private SendDafyCFCATransferService sendDafyCFCATransferService;
	
	public void userCert(B2GReqMsg_CFCATransfer_UserCert req, B2GResMsg_CFCATransfer_UserCert res){
		UserCertReqModel request = new UserCertReqModel();
		request.setIdentNo(req.getIdentNo());
		request.setPfxPath(req.getPfxPath());
		request.setUserName(req.getUserName());
		UserCertResModel resp = sendDafyCFCATransferService.userCert(request);
		if(CFCAOutConstant.RETURN_CODE_SUCCESS.equals(resp.getRespCode())){
			res.setDn(resp.getDn());
			res.setEncryptionCert(resp.getEncryptionCert());
			res.setEncryptionPrivateKey(resp.getEncryptionPrivateKey());
			res.setEndTime(resp.getEndTime());
			res.setIdCard(resp.getIdCard());
			res.setKeyIdentifier(resp.getKeyIdentifier());
			res.setP10(resp.getP10());
			res.setPfxData(resp.getPfxData());
			res.setPfxPassword(resp.getPfxPassword());
			res.setPfxPath(resp.getPfxPath());
			res.setSequenceNo(resp.getSequenceNo());
			res.setSerialNo(resp.getSerialNo());
			res.setSignatureCert(resp.getSignatureCert());
			res.setStartTime(resp.getStartTime());
			res.setUserId(resp.getUserId());
			res.setUserName(resp.getUserName());
		}else{
			throw new PTMessageException(PTMessageEnum.DAFY_CFCA_USER_CERT_ERROR);
		}
	}
	
	
	public void userSeal(B2GReqMsg_CFCATransfer_UserSeal req, B2GResMsg_CFCATransfer_UserSeal res){
		
		UserSealReqModel request = new UserSealReqModel();
		request.setIdCard(req.getIdCard());
		request.setImageCode(req.getImageCode());
		request.setImagePath(req.getImagePath());
		//request.setOrgCode(req.getOrgCode());
		request.setPfxPassword(req.getPfxPassword());
		request.setPfxPath(req.getPfxPath());
		request.setSealCode(req.getSealCode());
		request.setSealName(req.getSealName());
		request.setSealOrg(req.getSealOrg());
		request.setSealPassword(req.getSealPassword());
		request.setSealType(req.getSealType());
		request.setUserId(req.getUserId());
		request.setUserName(req.getUserName());
		UserSealResModel resp = sendDafyCFCATransferService.makeUserSeal(request);
		if(!CFCAOutConstant.RETURN_CODE_SUCCESS.equals(resp.getRespCode())){
			throw new PTMessageException(PTMessageEnum.DAFY_CFCA_USER_SEAL_ERROR);
		}
	}
	
	public void sealAutoPdf(B2GReqMsg_CFCATransfer_SealAutoPdf req, B2GResMsg_CFCATransfer_SealAutoPdf res){
		SealAutoPdfReqModel request = new SealAutoPdfReqModel();
		request.setCertDN(req.getCertDN());
		request.setCertificationLevel(req.getCertificationLevel());
		request.setCertSN(req.getCertSN());
		String keyword = req.getKeyword();
        if (keyword.contains("·")) {
            keyword = keyword.substring(0, keyword.indexOf("·"));
        }
		request.setKeyword(req.getKeyword());
		request.setLocationStyle(req.getLocationStyle());
		request.setlX(req.getlX());
		request.setlY(req.getlY());
		request.setOffsetX(req.getOffsetX());
		request.setOffsetY(req.getOffsetY());
		request.setPage(req.getPage());
		request.setPath(req.getPath());
		request.setSealCode(req.getSealCode());
		request.setSealedFileName(req.getSealedFileName());
		request.setSealLocation(req.getSealLocation());
		request.setSealPassword(req.getSealPassword());
		request.setSealPerson(req.getSealPerson());
		request.setSealResson(req.getSealResson());
		request.setType(req.getType());
		request.setIsNew(req.getIsNew());
		SealAutoPdfResModel resp = sendDafyCFCATransferService.autoSealPdf(request);
		if(!CFCAOutConstant.RETURN_CODE_SUCCESS.equals(resp.getRespCode())){
			throw new PTMessageException(PTMessageEnum.DAFY_CFCA_PDF_AUTO_SEAL_ERROR);
		}
	}

}
