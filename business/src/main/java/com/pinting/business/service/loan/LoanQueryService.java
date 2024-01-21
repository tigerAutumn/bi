package com.pinting.business.service.loan;


import java.util.Date;
import java.util.List;

import com.pinting.business.model.LnDailyAmount;
import com.pinting.gateway.hessian.message.loan.G2BReqMsg_BankLimit_LimitList;
import com.pinting.gateway.hessian.message.loan.G2BReqMsg_Loan_DailyAmount;
import com.pinting.gateway.hessian.message.loan.G2BResMsg_BankLimit_LimitList;
import com.pinting.gateway.hessian.message.loan.G2BResMsg_Loan_DailyAmount;
import com.pinting.gateway.hessian.message.loan.model.BankLimit;

public interface LoanQueryService {
	
	/**
	 * 查询银行卡限额
	 * @param req
	 * @param res
	 */
	List<BankLimit> getBankLimitList(G2BReqMsg_BankLimit_LimitList req,G2BResMsg_BankLimit_LimitList res);
	
	
	/**
	 * 蜂鸟查询币港湾当日可用额度
	 * @param req
	 * @param res
	 */
	void getDailyAmount(G2BReqMsg_Loan_DailyAmount req, G2BResMsg_Loan_DailyAmount res)throws Exception;

	/**
	 * 统计币港湾当日可用额度
	 * @return
	 */
	LnDailyAmount queryDailyAmount();

	/**
	 * 统计云贷自主放款当日可用额度
	 * @return
	 */
	Double queryDesFixedDailyAmount(Date queryDate);
	/**
	 * 统计赞时贷当日可用额度
	 * @return
	 */
	Double queryZsdDailyAmount(Date queryDate);

	void noticeZsdBankList();
}
