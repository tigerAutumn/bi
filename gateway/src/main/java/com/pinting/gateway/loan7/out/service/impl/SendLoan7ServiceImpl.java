package com.pinting.gateway.loan7.out.service.impl;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.pinting.gateway.enums.PTMessageEnum;
import com.pinting.gateway.exception.PTMessageException;
import com.pinting.gateway.loan7.out.model.QueryRepayJnlReqModel;
import com.pinting.gateway.loan7.out.model.QueryRepayJnlResModel;
import com.pinting.gateway.loan7.out.model.SysBatchBuyProductReqModel;
import com.pinting.gateway.loan7.out.model.SysBatchBuyProductResModel;
import com.pinting.gateway.loan7.out.service.SendLoan7Service;
import com.pinting.gateway.loan7.out.util.Loan7OutConstant;
import com.pinting.gateway.util.CommunicateUtil;

/**
 * 向7贷发起请求 接口实现类
 * 
 * @Project: gateway
 * @Title: DafySendServiceImpl.java
 * @author dingpf
 * @date 2015-2-10 下午6:25:26
 * @Copyright: 2015 BiGangWan.com Inc. All rights reserved.
 */
@Service
public class SendLoan7ServiceImpl implements SendLoan7Service {
	
	private static Logger logger = LoggerFactory.getLogger(SendLoan7ServiceImpl.class);

	@Override
	public SysBatchBuyProductResModel sysBatchBuyProduct(
			SysBatchBuyProductReqModel req) {
		req.setTransCode(Loan7OutConstant.SYS_BATCH_BUY_PRODUCT);
		req.setRequestTime(new Date());
		req.setClientKey(CommunicateUtil.clientLoan7Key);
		SysBatchBuyProductResModel resModel = null;
		try {
			//去掉资产合作方标识
			resModel = (SysBatchBuyProductResModel) CommunicateUtil.doCommunicate2Loan7(req);
		} catch (Exception e) {
			//其他失败，包含通讯检查失败（如登录失败）
			throw new PTMessageException(PTMessageEnum.DAFY_BUYPRODUCT_FAIL, resModel.getRespMsg());
		}
				
		if(!Loan7OutConstant.RETURN_CODE_SUCCESS.equals(resModel.getRespCode())){
			//购买产品失败
			if(Loan7OutConstant.RETURN_CODE_FAIL.equals(resModel.getRespCode())){
				throw new PTMessageException(PTMessageEnum.DAFY_BUYPRODUCT_FAIL, resModel.getRespMsg());
			//其他失败，包含购买前置检查失败（如打款确认失败）
			}else{
				throw new PTMessageException(PTMessageEnum.DAFY_BUYPRODUCT_REFU, resModel.getRespMsg());
			}
		}
		return resModel;
	}

	/**
	 * 借款人某笔借款的还款流水查询
	 * @param reqModel
	 * @return
     */
	@Override
	public QueryRepayJnlResModel queryRepayJnl(QueryRepayJnlReqModel reqModel) {
		reqModel.setTransCode(Loan7OutConstant.QUERY_REPAY_JNL);
		reqModel.setRequestTime(new Date());
		reqModel.setClientKey(CommunicateUtil.clientKey);
		QueryRepayJnlResModel resModel = (QueryRepayJnlResModel) CommunicateUtil.doCommunicate2Loan7(reqModel);
		return resModel;
	}

}
