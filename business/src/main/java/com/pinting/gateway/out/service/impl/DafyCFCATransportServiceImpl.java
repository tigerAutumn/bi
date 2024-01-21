package com.pinting.gateway.out.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.pinting.core.hessian.service.HessianService;
import com.pinting.gateway.hessian.message.dafy.B2GReqMsg_CFCATransfer_SealAutoPdf;
import com.pinting.gateway.hessian.message.dafy.B2GReqMsg_CFCATransfer_UserCert;
import com.pinting.gateway.hessian.message.dafy.B2GReqMsg_CFCATransfer_UserSeal;
import com.pinting.gateway.hessian.message.dafy.B2GResMsg_CFCATransfer_SealAutoPdf;
import com.pinting.gateway.hessian.message.dafy.B2GResMsg_CFCATransfer_UserCert;
import com.pinting.gateway.hessian.message.dafy.B2GResMsg_CFCATransfer_UserSeal;
import com.pinting.gateway.out.service.DafyCFCATransportService;

@Service
public class DafyCFCATransportServiceImpl implements DafyCFCATransportService {
	@Autowired
	@Qualifier("dafyGatewayService")
	private HessianService dafyGatewayService;
	
	@Override
	public B2GResMsg_CFCATransfer_UserCert userCert(
			B2GReqMsg_CFCATransfer_UserCert req) {
		B2GResMsg_CFCATransfer_UserCert res = (B2GResMsg_CFCATransfer_UserCert) dafyGatewayService.handleMsg(req);
		return res;
	}

	@Override
	public B2GResMsg_CFCATransfer_UserSeal userSeal(
			B2GReqMsg_CFCATransfer_UserSeal req) {
		B2GResMsg_CFCATransfer_UserSeal res = (B2GResMsg_CFCATransfer_UserSeal) dafyGatewayService.handleMsg(req);
		return res;
	}

	@Override
	public B2GResMsg_CFCATransfer_SealAutoPdf sealAutoPdf(
			B2GReqMsg_CFCATransfer_SealAutoPdf req) {
		B2GResMsg_CFCATransfer_SealAutoPdf res = (B2GResMsg_CFCATransfer_SealAutoPdf) dafyGatewayService.handleMsg(req);
		return res;
	}

}
