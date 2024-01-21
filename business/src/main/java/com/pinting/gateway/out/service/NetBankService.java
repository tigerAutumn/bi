package com.pinting.gateway.out.service;

import com.pinting.gateway.hessian.message.baofoo.B2GReqMsg_BaoFooQuickPay_EBank;
import com.pinting.gateway.hessian.message.baofoo.B2GResMsg_BaoFooQuickPay_EBank;
import com.pinting.gateway.hessian.message.pay19.B2GReqMsg_NetBank_EBank;
import com.pinting.gateway.hessian.message.pay19.B2GResMsg_NetBank_EBank;

public interface NetBankService {
	/**
	 * 19付网银--用户网银购买产品
	 * @param req B2GReqMsg_NetBank_EBank
	 * @return B2GResMsg_NetBank_EBank 对象
	 */
	public B2GResMsg_NetBank_EBank netBankBuyProduct(B2GReqMsg_NetBank_EBank req);
	
	/**
	 * 宝付网银--用户网银充值
	 * @param req B2GReqMsg_NetBank_EBank
	 * @return B2GResMsg_NetBank_EBank 对象
	 */
	public B2GResMsg_BaoFooQuickPay_EBank netBankBuyProductBaofoo(B2GReqMsg_BaoFooQuickPay_EBank req);
}
