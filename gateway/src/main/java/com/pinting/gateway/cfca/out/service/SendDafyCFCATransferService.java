package com.pinting.gateway.cfca.out.service;

import com.pinting.gateway.cfca.out.model.SealAutoPdfReqModel;
import com.pinting.gateway.cfca.out.model.SealAutoPdfResModel;
import com.pinting.gateway.cfca.out.model.UserCertReqModel;
import com.pinting.gateway.cfca.out.model.UserCertResModel;
import com.pinting.gateway.cfca.out.model.UserSealReqModel;
import com.pinting.gateway.cfca.out.model.UserSealResModel;



/**
 * CFCA签章服务器接口
 * @Project: gateway
 * @Title: SendDafyCFCATransferService.java
 * @author dingpf
 * @date 2016-7-5 下午3:59:20
 * @Copyright: 2016 BiGangWan.com Inc. All rights reserved.
 */
public interface SendDafyCFCATransferService {
	
	/**
	 * 客户证书生成
	 * @param request
	 * @return
	 */
	public UserCertResModel userCert(UserCertReqModel request);
	
	/**
	 * 客户制章
	 * @param request
	 * @return
	 */
	public UserSealResModel makeUserSeal(UserSealReqModel request);
	
	/**
	 * pdf自动签章
	 * @param request
	 * @return
	 */
	public SealAutoPdfResModel autoSealPdf(SealAutoPdfReqModel request);

}
