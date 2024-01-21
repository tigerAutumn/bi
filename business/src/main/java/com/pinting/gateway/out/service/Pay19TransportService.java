package com.pinting.gateway.out.service;

import com.pinting.gateway.hessian.message.pay19.B2GReqMsg_AcctTrans_AcctTrans;
import com.pinting.gateway.hessian.message.pay19.B2GReqMsg_AcctTrans_QueryRecvAcctTrans;
import com.pinting.gateway.hessian.message.pay19.B2GReqMsg_NetBank_EBank;
import com.pinting.gateway.hessian.message.pay19.B2GReqMsg_Pay4Another_MerchantDfQuery;
import com.pinting.gateway.hessian.message.pay19.B2GReqMsg_Pay4Another_NewMerchantDf;
import com.pinting.gateway.hessian.message.pay19.B2GReqMsg_Pay4Another_QueryCheckAccountFile;
import com.pinting.gateway.hessian.message.pay19.B2GReqMsg_QuickPay_ConfirmOrder;
import com.pinting.gateway.hessian.message.pay19.B2GReqMsg_QuickPay_PreOrder;
import com.pinting.gateway.hessian.message.pay19.B2GReqMsg_QuickPay_QueryMOrder;
import com.pinting.gateway.hessian.message.pay19.B2GReqMsg_QuickPay_RSendSms;
import com.pinting.gateway.hessian.message.pay19.B2GReqMsg_RealName_RealNameAuth;
import com.pinting.gateway.hessian.message.pay19.B2GResMsg_AcctTrans_AcctTrans;
import com.pinting.gateway.hessian.message.pay19.B2GResMsg_AcctTrans_QueryRecvAcctTrans;
import com.pinting.gateway.hessian.message.pay19.B2GResMsg_NetBank_EBank;
import com.pinting.gateway.hessian.message.pay19.B2GResMsg_Pay4Another_MerchantDfQuery;
import com.pinting.gateway.hessian.message.pay19.B2GResMsg_Pay4Another_NewMerchantDf;
import com.pinting.gateway.hessian.message.pay19.B2GResMsg_Pay4Another_QueryCheckAccountFile;
import com.pinting.gateway.hessian.message.pay19.B2GResMsg_QuickPay_ConfirmOrder;
import com.pinting.gateway.hessian.message.pay19.B2GResMsg_QuickPay_PreOrder;
import com.pinting.gateway.hessian.message.pay19.B2GResMsg_QuickPay_QueryMOrder;
import com.pinting.gateway.hessian.message.pay19.B2GResMsg_QuickPay_RSendSms;
import com.pinting.gateway.hessian.message.pay19.B2GResMsg_RealName_RealNameAuth;

public interface Pay19TransportService {
	
	/**
	 * 快捷预下单
	 * @param req
	 * @return
	 */
	public B2GResMsg_QuickPay_PreOrder preOrder(B2GReqMsg_QuickPay_PreOrder req);
	
	/**
	 * 快捷确认下单
	 * @param req
	 * @return
	 */
	public B2GResMsg_QuickPay_ConfirmOrder confirmOrder(B2GReqMsg_QuickPay_ConfirmOrder req);
	
	/**
	 * 快捷短信重发
	 * @param req
	 * @return
	 */
	public B2GResMsg_QuickPay_RSendSms rSendSms(B2GReqMsg_QuickPay_RSendSms req);
	
	/**
	 * 快捷订单查询（暂不实现）
	 * @param req
	 * @return
	 */
	@Deprecated
	public B2GResMsg_QuickPay_QueryMOrder queryMOrder(B2GReqMsg_QuickPay_QueryMOrder req);
	
	/**
	 * 代付下单
	 * @param req
	 * @return
	 */
	public B2GResMsg_Pay4Another_NewMerchantDf newMerchantDf(B2GReqMsg_Pay4Another_NewMerchantDf req);
	
	/**
	 * 代付订单查询（暂不实现）
	 * @param req
	 * @return
	 */
	@Deprecated
	public B2GResMsg_Pay4Another_MerchantDfQuery merchantDfQuery (B2GReqMsg_Pay4Another_MerchantDfQuery req);

	/**
	 * 19付对账文件下载并解析
	 * @param req
	 * @return
	 */
	public B2GResMsg_Pay4Another_QueryCheckAccountFile queryCheckAccountFile(B2GReqMsg_Pay4Another_QueryCheckAccountFile req);
	
	/**
	 * 实名认证
	 * @param req
	 * @return
	 */
	public B2GResMsg_RealName_RealNameAuth realNameAuth(B2GReqMsg_RealName_RealNameAuth req);
	
	/**
	 * 钱包转账
	 * @param req
	 * @return
	 */
	public B2GResMsg_AcctTrans_AcctTrans acctTrans(B2GReqMsg_AcctTrans_AcctTrans req);
	
	/**
	 * 收款方转账订单查询
	 * @param req
	 * @return
	 */
	public B2GResMsg_AcctTrans_QueryRecvAcctTrans queryRecvAcctTrans(B2GReqMsg_AcctTrans_QueryRecvAcctTrans req);
	
	/**
	 * 网银请求，返回字符串形式html页面
	 * @param req
	 * @return
	 */
	public B2GResMsg_NetBank_EBank eBank(B2GReqMsg_NetBank_EBank req);
}
