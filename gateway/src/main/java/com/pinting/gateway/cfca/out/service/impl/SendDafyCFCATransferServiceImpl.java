package com.pinting.gateway.cfca.out.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.pinting.core.util.GlobEnvUtil;
import com.pinting.gateway.cfca.out.model.SealAutoPdfReqModel;
import com.pinting.gateway.cfca.out.model.UserCertReqModel;
import com.pinting.gateway.cfca.out.model.UserSealReqModel;
import com.pinting.gateway.cfca.out.model.SealAutoPdfResModel;
import com.pinting.gateway.cfca.out.model.UserCertResModel;
import com.pinting.gateway.cfca.out.model.UserSealResModel;
import com.pinting.gateway.cfca.out.service.SendDafyCFCATransferService;
import com.pinting.gateway.cfca.out.util.CFCAOutConstant;
import com.pinting.gateway.cfca.out.util.CFCAOutMsgUtil;
import com.pinting.gateway.util.HttpClientUtil;

@Service
public class SendDafyCFCATransferServiceImpl implements
		SendDafyCFCATransferService {
	private static String CFCA_BIZ_URL = "http://qa.51wenzi.com/remote/cfcatransfer/bigangwan";
	static{
		try {
			CFCA_BIZ_URL = GlobEnvUtil.get("cfcatransfer.biz.url");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public UserCertResModel userCert(UserCertReqModel request) {
		request.setTransCode(CFCAOutConstant.USER_CERT);
		request.setRequestTime(new Date());
		String msg = CFCAOutMsgUtil.packageMsg(request);
		Map<String, String> params = new HashMap<String, String>();
		params.put("DATA", msg);
		String retStr = HttpClientUtil.sendRequest(CFCA_BIZ_URL, params);
		UserCertResModel resp = (UserCertResModel) CFCAOutMsgUtil.parseMsg(retStr, CFCAOutConstant.USER_CERT);
		return resp;
	}

	@Override
	public UserSealResModel makeUserSeal(UserSealReqModel request) {
		request.setTransCode(CFCAOutConstant.USER_SEAL);
		request.setRequestTime(new Date());
		String msg = CFCAOutMsgUtil.packageMsg(request);
		Map<String, String> params = new HashMap<String, String>();
		params.put("DATA", msg);
		String retStr = HttpClientUtil.sendRequest(CFCA_BIZ_URL, params);
		UserSealResModel resp = (UserSealResModel) CFCAOutMsgUtil.parseMsg(retStr, CFCAOutConstant.USER_SEAL);
		return resp;
	}

	@Override
	public SealAutoPdfResModel autoSealPdf(SealAutoPdfReqModel request) {
		request.setTransCode(CFCAOutConstant.SEAL_AUTO_PDF);
		request.setRequestTime(new Date());
		String msg = CFCAOutMsgUtil.packageMsg(request);
		Map<String, String> params = new HashMap<String, String>();
		params.put("DATA", msg);
		String retStr = HttpClientUtil.sendRequest(CFCA_BIZ_URL, params);
		SealAutoPdfResModel resp = (SealAutoPdfResModel) CFCAOutMsgUtil.parseMsg(retStr, CFCAOutConstant.SEAL_AUTO_PDF);
		return resp;
	}

}
