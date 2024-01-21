package com.pinting.gateway.qidian.out.service.impl;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.pinting.gateway.enums.PTMessageEnum;
import com.pinting.gateway.exception.PTMessageException;
import com.pinting.gateway.loan7.out.util.DepLoan7OutConstant;
import com.pinting.gateway.qidian.out.model.CustomerInfoSyncReqModel;
import com.pinting.gateway.qidian.out.model.CustomerInfoSyncResModel;
import com.pinting.gateway.qidian.out.model.OrderInfoSyncReqModel;
import com.pinting.gateway.qidian.out.model.OrderInfoSyncResModel;
import com.pinting.gateway.qidian.out.service.SendQiDianService;
import com.pinting.gateway.qidian.out.util.QiDianCommunicateUtil;
import com.pinting.gateway.qidian.out.util.QiDianOutConstant;

@Service
public class SendQiDianServiceImpl implements SendQiDianService {
	private static Logger logger = LoggerFactory.getLogger(SendQiDianServiceImpl.class);
	
	@Override
	public CustomerInfoSyncResModel customerInfoSync(
			CustomerInfoSyncReqModel reqModel) {
		
		String requestSeq = String.valueOf(System.currentTimeMillis());   
		reqModel.setRequestSeq(requestSeq);
		reqModel.setTransCode(QiDianOutConstant.CUSTOMER_INFO_SYNC);
		reqModel.setRequestTime(new Date());
		logger.info("七店用户信息推送>>>"+JSON.toJSONString(reqModel));
		CustomerInfoSyncResModel resModel = (CustomerInfoSyncResModel) QiDianCommunicateUtil
				.doCommunicate2QiDian(reqModel);
		if(!DepLoan7OutConstant.RETURN_CODE_SUCCESS.equals(resModel.getRespCode())){
			throw new PTMessageException(PTMessageEnum.QIDIAN_CUSTOMER_INFO_SYNC_ERROR, resModel.getRespMsg());
		}
		return resModel;
	}

	@Override
	public OrderInfoSyncResModel orderInfoSync(OrderInfoSyncReqModel reqModel) {
		String requestSeq = String.valueOf(System.currentTimeMillis());   
		reqModel.setRequestSeq(requestSeq);
		reqModel.setTransCode(QiDianOutConstant.ORDER_INFO_SYNC);
		reqModel.setRequestTime(new Date());
		logger.info("七店订单信息推送>>>"+JSON.toJSONString(reqModel));
		OrderInfoSyncResModel resModel = (OrderInfoSyncResModel) QiDianCommunicateUtil
				.doCommunicate2QiDian(reqModel);
		if(!DepLoan7OutConstant.RETURN_CODE_SUCCESS.equals(resModel.getRespCode())){
			throw new PTMessageException(PTMessageEnum.QIDIAN_ORDER_INFO_SYNC_ERROR, resModel.getRespMsg());
		}
		return resModel;
	}

}
