package com.pinting.gateway.xicai.out.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.pinting.gateway.xicai.out.config.Config;
import com.pinting.gateway.xicai.out.enums.XicaiUrl;
import com.pinting.gateway.xicai.out.helper.HttpRequest;
import com.pinting.gateway.xicai.out.helper.ParameterHelper;
import com.pinting.gateway.xicai.out.model.BindUserCallBackReq;
import com.pinting.gateway.xicai.out.model.InvestCallBackReq;
import com.pinting.gateway.xicai.out.model.PushP2PReq;
import com.pinting.gateway.xicai.out.service.XicaiServiceClient;

import net.sf.json.JSONObject;

@Service("xicaiServiceClient")
public class XicaiServiceClientImpl implements XicaiServiceClient {

	private static Logger logger = LoggerFactory.getLogger(XicaiServiceClientImpl.class);
	
	@Override
	public JSONObject pushP2P(PushP2PReq req) {
		String result = HttpRequest.sendGet(Config.url+XicaiUrl.SEND_PRODUCT_ID.getCode(), ParameterHelper.push_p2p(req.getPid()));
		logger.info("推送产品ID接口，希财返回结果：【"+result+"】");
		return JSONObject.fromObject(result);
	}

	@Override
	public JSONObject investCallBack(InvestCallBackReq req) {
		///投资回调
    	String result = HttpRequest.sendPost(Config.url+XicaiUrl.INVEST_CALL_BACK.getCode(), ParameterHelper.InvestCallBack(req));
    	logger.info("投资回调接口，希财返回结果：【"+result+"】");
		return JSONObject.fromObject(result);
	}

	@Override
	public JSONObject bindUserCallBack(BindUserCallBackReq req) {
		///绑定回调
    	String result = HttpRequest.sendPost(Config.url+XicaiUrl.BIND_USER_CALL_BACK.getCode(), ParameterHelper.BindUserCallBack(req));
    	logger.info("绑定回调接口，希财返回结果：【"+result+"】");
		return JSONObject.fromObject(result);
	}

}
