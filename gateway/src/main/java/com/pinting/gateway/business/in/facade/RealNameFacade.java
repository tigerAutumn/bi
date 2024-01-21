package com.pinting.gateway.business.in.facade;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pinting.core.util.DateUtil;
import com.pinting.core.util.GlobEnvUtil;
import com.pinting.core.util.StringUtil;
import com.pinting.gateway.hessian.message.pay19.B2GReqMsg_RealName_RealNameAuth;
import com.pinting.gateway.hessian.message.pay19.B2GResMsg_RealName_RealNameAuth;
import com.pinting.gateway.hessian.message.pay19.enums.RealNameTradeStatus;
import com.pinting.gateway.pay19.out.model.req.RealNameVerifyReq;
import com.pinting.gateway.pay19.out.model.resp.RealNameVerifyResp;
import com.pinting.gateway.pay19.out.service.RealNameServiceClient;
import com.pinting.gateway.pay19.out.util.Pay19HttpUtil;
import com.pinting.gateway.util.Pay19CipherUtil;
import com.pinting.gateway.util.Util;

/**
 * 实名认证
 * 
 * @Project: gateway
 * @Title: RealNameFacade.java
 * @author dingpf
 * @date 2015-11-17 下午6:17:43
 * @Copyright: 2015 BiGangWan.com Inc. All rights reserved.
 */
@Component("RealName")
public class RealNameFacade {
	@Autowired
	private RealNameServiceClient realNameServiceClient;
	
	public void realNameAuth(B2GReqMsg_RealName_RealNameAuth req,
			B2GResMsg_RealName_RealNameAuth res) {
        RealNameVerifyReq reqModel = new RealNameVerifyReq();
        reqModel.setMxUserId(req.getMxUserId());
        reqModel.setMxReqSno(req.getMxReqSno());
        reqModel.setMxReqDate(DateUtil.formatDateTime(req.getMxReqDate(), "yyyyMMddHHmmss"));
        reqModel.setCardHolder(Pay19CipherUtil.encryptData(req.getCardHolder(),
            Pay19HttpUtil.merchant_key));
        reqModel.setIdType(req.getIdType().getCode());
        reqModel.setIdNo(Pay19CipherUtil.encryptData(Util.formatIdNo2Upper(req.getIdNo()),
            Pay19HttpUtil.merchant_key));
        reqModel.setMobile(Pay19CipherUtil.encryptData(req.getMobile(), Pay19HttpUtil.merchant_key));
        reqModel.setPcId(req.getPcId());
        reqModel.setBankCardNo(Pay19CipherUtil.encryptData(req.getBankCardNo(),
            Pay19HttpUtil.merchant_key));
        reqModel.setCardType(req.getCardType().getCode());
        reqModel.setCardAttr(req.getCardAttr().getCode());
        reqModel.setCvv2(StringUtil.isEmpty(req.getCvv2()) ? null : Pay19CipherUtil.encryptData(
            req.getCvv2(), Pay19HttpUtil.merchant_key));
        reqModel.setValidDate(StringUtil.isEmpty(req.getValidDate()) ? null : Pay19CipherUtil
            .encryptData(req.getValidDate(), Pay19HttpUtil.merchant_key));
        //reqModel.setNotifyUrl(req.getNotifyUrl());
        reqModel.setNotifyUrl(GlobEnvUtil.get("19pay.notice.realname"));
        reqModel.setRemark(req.getRemark());
        reqModel.setReserved(req.getReserved());

        RealNameVerifyResp resp = realNameServiceClient.realNameAuth(reqModel);

        res.setMpServSno(resp.getMxReqSno());
        res.setMxReqDate(DateUtil.parse(resp.getMxReqDate(), "yyyyMMddHHmmss"));
        res.setMxReqSno(resp.getMxReqSno());
        res.setTradeStatus(RealNameTradeStatus.find(resp.getTradeStatus()));
        res.setReqStatus(resp.getReqStatus());
        res.setRetCode(resp.getRetCode());
	}
	

}
