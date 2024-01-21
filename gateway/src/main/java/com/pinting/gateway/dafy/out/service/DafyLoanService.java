package com.pinting.gateway.dafy.out.service;

import com.pinting.gateway.dafy.out.model.LoanResultNoticeReqModel;
import com.pinting.gateway.dafy.out.model.LoanResultNoticeResModel;
import com.pinting.gateway.dafy.out.model.AgreementNoticeReqModel;
import com.pinting.gateway.dafy.out.model.AgreementNoticeResModel;
import com.pinting.gateway.dafy.out.model.RepayResultNoticeReqModel;
import com.pinting.gateway.dafy.out.model.RepayResultNoticeResModel;
import com.pinting.gateway.dafy.out.model.SignResultNoticeReqModel;
import com.pinting.gateway.dafy.out.model.SignResultNoticeResModel;
import com.pinting.gateway.dafy.out.model.WaitFillReqModel;
import com.pinting.gateway.dafy.out.model.WaitFillResModel;
import com.pinting.gateway.dafy.out.model.QueryBillReqModel;
import com.pinting.gateway.dafy.out.model.QueryBillResModel;
import com.pinting.gateway.dafy.out.model.RevenueSettleReqModel;
import com.pinting.gateway.dafy.out.model.RevenueSettleResModel;

/**
 * 
 * 自主放款相关业务，向达飞发起请求 接口服务
 * @Project: gateway
 * @Title: SendDafyLoanService.java
 * @author Dragon & cat
 * @date 2016-11-24
 * @Copyright: 2015 BiGangWan.com Inc. All rights reserved.
 */
public interface DafyLoanService {
	
	/**
	 * 账单同步
	 * @param req 请求数据 RepaySynchronizationReqModel
	 * @return RepaySynchronizationResModel
	 */
	public QueryBillResModel queryBill(QueryBillReqModel req);
	
	
	
	/**
	 * 待补账申请
	 * @param req 请求数据 PendingAccountNoticeReqModel
	 * @return PendingAccountNoticeResModel
	 */
	public WaitFillResModel waitFill(WaitFillReqModel req);
	
	
	/**
	 * 营收结算通知
	 * @param req 请求数据 RevenueSettlementNoticeReqModel
	 * @return RevenueSettlementNoticeResModel
	 */
	public RevenueSettleResModel revenueSettle(RevenueSettleReqModel req);
	
	/**
	 * 放款结果通知
	 * @param req
	 * @return
	 */
	public LoanResultNoticeResModel loanResultNotice(LoanResultNoticeReqModel req);
	
	/**
	 * 借款协议签章结果通知
	 * @param req 请求数据signResultNoticeReqModel
	 * @return  signResultNoticeResModel
	 */
	public SignResultNoticeResModel signResultNotice(SignResultNoticeReqModel req);
	
	/**
	 * 还款结果通知
	 * @param req 请求数据 RepayResultNoticeReqModel
	 * @return RepayResultNoticeResModel
	 */
	public RepayResultNoticeResModel repayResultNotice(RepayResultNoticeReqModel req);
	
	/**
	 * 协议下载通知
	 * @param req AgreementNoticeReqModel
	 * @return AgreementNoticeResModel
	 */
	public AgreementNoticeResModel agreementNotice(AgreementNoticeReqModel req);
}
