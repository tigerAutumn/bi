package com.pinting.gateway.zsd.out.service;

import com.pinting.gateway.zsd.out.model.LoanReq;
import com.pinting.gateway.zsd.out.model.LoanRes;
import com.pinting.gateway.zsd.out.model.RepayReq;
import com.pinting.gateway.zsd.out.model.RepayRes;
import com.pinting.gateway.zsd.out.model.SignResultNoticeResModel;
import com.pinting.gateway.zsd.out.model.ZsdBankLimitReq;
import com.pinting.gateway.zsd.out.model.ZsdBankLimitRes;
import com.pinting.gateway.zsd.out.model.SignResultNoticeReqModel;

/**
 * 
 * @project gateway
 * @title NoticeService.java
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
    LoanRes noticeLoan (LoanReq req) throws Exception;
    
    /**
     * 还款通知
     * @param req
     * @return
     */
    RepayRes noticeRepay(RepayReq req) throws Exception;
    
    /**
     * 推送银行卡限额
     * @param req
     * @return
     * @throws Exception
     */
    ZsdBankLimitRes noticeBankLimit(ZsdBankLimitReq req) throws  Exception;
    
	/**
	 * 借款协议签章结果通知
	 * @param req 请求数据signResultNoticeReqModel
	 * @return  signResultNoticeResModel
	 */
	public SignResultNoticeResModel signResultNotice(SignResultNoticeReqModel req) throws Exception;
	
}
