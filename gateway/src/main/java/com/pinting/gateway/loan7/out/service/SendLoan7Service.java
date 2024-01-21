package com.pinting.gateway.loan7.out.service;

import com.pinting.gateway.loan7.out.model.QueryRepayJnlReqModel;
import com.pinting.gateway.loan7.out.model.QueryRepayJnlResModel;
import com.pinting.gateway.loan7.out.model.SysBatchBuyProductReqModel;
import com.pinting.gateway.loan7.out.model.SysBatchBuyProductResModel;

/**
 * 向达飞发起请求 接口服务
 * @Project: gateway
 * @Title: DafySendService.java
 * @author dingpf
 * @date 2015-2-10 下午6:22:55
 * @Copyright: 2015 BiGangWan.com Inc. All rights reserved.
 */
public interface SendLoan7Service {
	
	//交易码
	public static final String TRANS_CODE_LOGIN = "login";//登录
	public static final String TRANS_CODE_REGISTER="registerCustomer";//用户注册
	
	/**
	 * 系统批量购买7贷理财
	 * @param req
	 * @return
	 */
	public SysBatchBuyProductResModel sysBatchBuyProduct(SysBatchBuyProductReqModel req);
	
	/**
	 * 根据借款编号、客户编号 查询该笔借款的还款流水记录
	 * @param req
	 * @return
	 */
	public QueryRepayJnlResModel queryRepayJnl(QueryRepayJnlReqModel req);

}
