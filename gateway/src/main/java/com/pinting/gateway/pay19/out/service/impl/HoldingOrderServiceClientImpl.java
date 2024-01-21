package com.pinting.gateway.pay19.out.service.impl;

import org.springframework.stereotype.Service;

import com.pinting.gateway.enums.PTMessageEnum;
import com.pinting.gateway.exception.PTMessageException;
import com.pinting.gateway.pay19.out.enums.HoldingOrderRespCode;
import com.pinting.gateway.pay19.out.enums.HoldingOrderRespStatus;
import com.pinting.gateway.pay19.out.enums.HoldingOrderRespTradeStatus;
import com.pinting.gateway.pay19.out.enums.HoldingOrderUrl;
import com.pinting.gateway.pay19.out.model.req.HoldingOrderQueryReq;
import com.pinting.gateway.pay19.out.model.req.HoldingOrderReq;
import com.pinting.gateway.pay19.out.model.resp.QueryWithHoldingNewResp;
import com.pinting.gateway.pay19.out.model.resp.WithholdingOrderResp;
import com.pinting.gateway.pay19.out.service.HoldingOrderServiceClient;
import com.pinting.gateway.pay19.out.util.Pay19HttpUtil;

@Service("holdingOrderServiceClient")
public class HoldingOrderServiceClientImpl implements HoldingOrderServiceClient {

	@Override
	public WithholdingOrderResp holdingOrder(HoldingOrderReq req) {
		WithholdingOrderResp resp = (WithholdingOrderResp)Pay19HttpUtil.holdingOrder(HoldingOrderUrl.HOILDINGORDER, req);
		if(!HoldingOrderRespStatus.SUCCESS.getCode().equals(resp.getStatus())) {
			HoldingOrderRespStatus code = HoldingOrderRespStatus.find(resp.getStatus());
			String errCode = resp.getRetCode();
            String errMsg = code != null ? code.getDescription() : resp.getRetCode();
            throw new PTMessageException(PTMessageEnum.RPC_EXCEPTION, errMsg + "," + errCode);
		}
		else {
			if(HoldingOrderRespTradeStatus.FAIL.getCode().equals(resp.getTradeStatus()) || HoldingOrderRespTradeStatus.EXCEPTION.getCode().equals(resp.getTradeStatus())) {
				HoldingOrderRespTradeStatus code = HoldingOrderRespTradeStatus.find(resp.getTradeStatus());
				String errCode = resp.getRetCode();
	            String errMsg = code != null ? code.getDescription() : resp.getRetCode();
	            throw new PTMessageException(PTMessageEnum.RPC_EXCEPTION, errMsg + "," + errCode);
			}
		}
		return resp;
	}

	@Override
	public QueryWithHoldingNewResp holdingOrderQuery(HoldingOrderQueryReq req) {
		QueryWithHoldingNewResp resp = (QueryWithHoldingNewResp)Pay19HttpUtil.holdingOrderQuery(HoldingOrderUrl.HOILDINGORDERQUERY, req);
		if(!HoldingOrderRespStatus.SUCCESS.getCode().equals(resp.getStatus())) {
			HoldingOrderRespStatus code = HoldingOrderRespStatus.find(resp.getStatus());
			String errCode = resp.getRetCode();
            String errMsg = code != null ? code.getDescription() : resp.getRetCode();
            throw new PTMessageException(PTMessageEnum.RPC_EXCEPTION, errMsg + "," + errCode);
		}
		else {
			if(!HoldingOrderRespCode.SUCCESS_CODE_00000.getCode().equals(resp.getRetCode())) {
				HoldingOrderRespCode code = HoldingOrderRespCode.find(resp.getRetCode());
				String errCode = resp.getRetCode();
	            String errMsg = code != null ? code.getDescription() : resp.getRetCode();
	            throw new PTMessageException(PTMessageEnum.RPC_EXCEPTION, errMsg + "," + errCode);
			}
		}
		return resp;
	}

}
