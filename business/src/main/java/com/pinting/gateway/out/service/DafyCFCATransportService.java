package com.pinting.gateway.out.service;

import com.pinting.gateway.hessian.message.dafy.B2GReqMsg_CFCATransfer_SealAutoPdf;
import com.pinting.gateway.hessian.message.dafy.B2GReqMsg_CFCATransfer_UserCert;
import com.pinting.gateway.hessian.message.dafy.B2GReqMsg_CFCATransfer_UserSeal;
import com.pinting.gateway.hessian.message.dafy.B2GResMsg_CFCATransfer_SealAutoPdf;
import com.pinting.gateway.hessian.message.dafy.B2GResMsg_CFCATransfer_UserCert;
import com.pinting.gateway.hessian.message.dafy.B2GResMsg_CFCATransfer_UserSeal;

public interface DafyCFCATransportService {
	
	/**
	 * 客户证书获取
	 * @param req
	 * @return
	 */
	public B2GResMsg_CFCATransfer_UserCert userCert(B2GReqMsg_CFCATransfer_UserCert req);
	
	/**
	 * 客户签章
	 * @param req
	 * @return
	 */
	public B2GResMsg_CFCATransfer_UserSeal userSeal(B2GReqMsg_CFCATransfer_UserSeal req);
	
	/**
	 * pdf自动签章
	 * @param req
	 * @return
	 */
	public B2GResMsg_CFCATransfer_SealAutoPdf sealAutoPdf(B2GReqMsg_CFCATransfer_SealAutoPdf req);
	
}
