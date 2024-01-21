package com.pinting.gateway.out.service.zsd;

import com.pinting.gateway.hessian.message.dafy.B2GReqMsg_DafyLoanNotice_SignResultNotice;
import com.pinting.gateway.hessian.message.dafy.B2GResMsg_DafyLoanNotice_SignResultNotice;
import com.pinting.gateway.hessian.message.zsd.*;

/**
 * 
 * @project business
 * @title ZsdNoticeService.java
 * @author Dragon & cat
 * @date 2017-9-1
 * @Copyright 2015 BiGangWan.com Inc. All rights reserved.
 * @Description
 */
public interface ZsdNoticeService {

    /**
     * 借款通知
     * @param req
     * @return
     */
    B2GResMsg_ZsdNotice_NoticeLoan noticeLoan(B2GReqMsg_ZsdNotice_NoticeLoan req);
    
    /**
     * 还款通知
     * @param req
     * @return
     */
    B2GResMsg_ZsdNotice_NoticeRepay noticeRepay(B2GReqMsg_ZsdNotice_NoticeRepay req);

    /**
     * 推送银行卡限额
     * @param req
     * @return
     */
    B2GResMsg_ZsdNotice_NoticeBankLimit noticeBankLimit(B2GReqMsg_ZsdNotice_NoticeBankLimit req);
    
    /**
	 * 赞时贷自主放款 - 借款四方协议签章
	 * @param req
	 * @return
	 */
	B2GResMsg_ZsdNotice_SignResultNotice signResultNotice(B2GReqMsg_ZsdNotice_SignResultNotice req);
	
}
