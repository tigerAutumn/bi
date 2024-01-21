package com.pinting.gateway.out.service.qidian.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.pinting.core.hessian.service.HessianService;
import com.pinting.gateway.hessian.message.loan7.B2GResMsg_DepLoan7Notice_LoanResultNotice;
import com.pinting.gateway.hessian.message.qidian.B2GReqMsg_QiDianNotice_CustomerInfoSync;
import com.pinting.gateway.hessian.message.qidian.B2GReqMsg_QiDianNotice_OrderInfoSync;
import com.pinting.gateway.hessian.message.qidian.B2GResMsg_QiDianNotice_CustomerInfoSync;
import com.pinting.gateway.hessian.message.qidian.B2GResMsg_QiDianNotice_OrderInfoSync;
import com.pinting.gateway.out.service.qidian.QiDianNoticeService;
/**
 * 
 * @project business
 * @title QiDianNoticeServiceImpl.java
 * @author Dragon & cat
 * @date 2018-3-22
 * @Copyright 2015 BiGangWan.com Inc. All rights reserved.
 * @Description
 */
@Service
public class QiDianNoticeServiceImpl implements QiDianNoticeService {
	@Autowired
    @Qualifier("qiDianNoticeGatewayService")
    private HessianService noticeGatewayService;
	
	@Override
	public B2GResMsg_QiDianNotice_CustomerInfoSync customerInfoSync(
			B2GReqMsg_QiDianNotice_CustomerInfoSync req) {
		return (B2GResMsg_QiDianNotice_CustomerInfoSync) noticeGatewayService.handleMsg(req);
	}

	@Override
	public B2GResMsg_QiDianNotice_OrderInfoSync orderInfoSync(
			B2GReqMsg_QiDianNotice_OrderInfoSync req) {
		return (B2GResMsg_QiDianNotice_OrderInfoSync) noticeGatewayService.handleMsg(req);
	}

}
