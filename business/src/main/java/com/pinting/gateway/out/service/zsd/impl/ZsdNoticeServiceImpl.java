package com.pinting.gateway.out.service.zsd.impl;

import com.pinting.core.hessian.service.HessianService;
import com.pinting.gateway.hessian.message.zsd.*;
import com.pinting.gateway.out.service.zsd.ZsdNoticeService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * 
 * @project business
 * @title ZsdNoticeServiceImpl.java
 * @author Dragon & cat
 * @date 2017-9-1
 * @Copyright 2015 BiGangWan.com Inc. All rights reserved.
 * @Description
 */
@Service
public class ZsdNoticeServiceImpl implements ZsdNoticeService {

    @Autowired
    @Qualifier("zsdNoticeGatewayService")
    private HessianService noticeGatewayService;

    @Override
    public B2GResMsg_ZsdNotice_NoticeLoan noticeLoan(B2GReqMsg_ZsdNotice_NoticeLoan req) {
        return (B2GResMsg_ZsdNotice_NoticeLoan) noticeGatewayService.handleMsg(req);
    }

	@Override
	public B2GResMsg_ZsdNotice_NoticeRepay noticeRepay(B2GReqMsg_ZsdNotice_NoticeRepay req) {
        return (B2GResMsg_ZsdNotice_NoticeRepay) noticeGatewayService.handleMsg(req);
	}

    @Override
    public B2GResMsg_ZsdNotice_NoticeBankLimit noticeBankLimit(B2GReqMsg_ZsdNotice_NoticeBankLimit req) {
        return (B2GResMsg_ZsdNotice_NoticeBankLimit) noticeGatewayService.handleMsg(req);
    }

	@Override
	public B2GResMsg_ZsdNotice_SignResultNotice signResultNotice(B2GReqMsg_ZsdNotice_SignResultNotice req) {
        return (B2GResMsg_ZsdNotice_SignResultNotice) noticeGatewayService.handleMsg(req);
	}
	
}
