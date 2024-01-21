package com.pinting.gateway.out.service.loan7;

import com.pinting.gateway.hessian.message.loan7.B2GReqMsg_DepLoan7Notice_AgreementNotice;
import com.pinting.gateway.hessian.message.loan7.B2GReqMsg_DepLoan7Notice_LoanResultNotice;
import com.pinting.gateway.hessian.message.loan7.B2GReqMsg_DepLoan7Notice_QueryBill;
import com.pinting.gateway.hessian.message.loan7.B2GReqMsg_DepLoan7Notice_RepayResultNotice;
import com.pinting.gateway.hessian.message.loan7.B2GReqMsg_DepLoan7Notice_RevenueSettle;
import com.pinting.gateway.hessian.message.loan7.B2GReqMsg_DepLoan7Notice_SignResultNotice;
import com.pinting.gateway.hessian.message.loan7.B2GReqMsg_DepLoan7Notice_WaitFill;
import com.pinting.gateway.hessian.message.loan7.B2GResMsg_DepLoan7Notice_AgreementNotice;
import com.pinting.gateway.hessian.message.loan7.B2GResMsg_DepLoan7Notice_LoanResultNotice;
import com.pinting.gateway.hessian.message.loan7.B2GResMsg_DepLoan7Notice_QueryBill;
import com.pinting.gateway.hessian.message.loan7.B2GResMsg_DepLoan7Notice_RepayResultNotice;
import com.pinting.gateway.hessian.message.loan7.B2GResMsg_DepLoan7Notice_RevenueSettle;
import com.pinting.gateway.hessian.message.loan7.B2GResMsg_DepLoan7Notice_SignResultNotice;
import com.pinting.gateway.hessian.message.loan7.B2GResMsg_DepLoan7Notice_WaitFill;

/**
 * 
 * @author SHENGUOPING
 * @date  2017年12月13日 上午10:40:30
 */
public interface DepLoan7NoticeService {

	/**
     * 7贷自主放款-借款通知
     * @param req
     * @return
     */
	B2GResMsg_DepLoan7Notice_LoanResultNotice noticeLoan(B2GReqMsg_DepLoan7Notice_LoanResultNotice req);

	/**
     * 7贷自主放款-还款通知
     * @param req
     * @return
     */
	B2GResMsg_DepLoan7Notice_RepayResultNotice noticeRepay(B2GReqMsg_DepLoan7Notice_RepayResultNotice req);

	/**
	 * 7贷自主放款-营收结算通知
	 * @param req
	 * @return
	 */
	B2GResMsg_DepLoan7Notice_RevenueSettle noticeRevenueSettle(B2GReqMsg_DepLoan7Notice_RevenueSettle req);

	/**
	 * 7贷自主放款 - 待补账通知
	 * @param req
	 * @return
	 */
	B2GResMsg_DepLoan7Notice_WaitFill noticeWaitFill(B2GReqMsg_DepLoan7Notice_WaitFill req);
	
	/**
	 * 7贷自主放款 - 借款四方协议签章
	 * @param req
	 * @return
	 */
	B2GResMsg_DepLoan7Notice_SignResultNotice signResultNotice(B2GReqMsg_DepLoan7Notice_SignResultNotice req);
	
	/**
	 * 7贷自主放款 - 账单同步查询
	 * @param req
	 * @return
	 */
	B2GResMsg_DepLoan7Notice_QueryBill queryBill(B2GReqMsg_DepLoan7Notice_QueryBill req);

	/**
	 * 7贷自主放款 - 代偿协议下载地址通知
	 * @param req
	 * @return
	 */
	B2GResMsg_DepLoan7Notice_AgreementNotice agreementNotice(B2GReqMsg_DepLoan7Notice_AgreementNotice req);
	
}
