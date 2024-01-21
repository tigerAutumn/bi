package com.pinting.gateway.xicai.out.service;

import com.pinting.gateway.xicai.out.model.BindUserCallBackReq;
import com.pinting.gateway.xicai.out.model.InvestCallBackReq;
import com.pinting.gateway.xicai.out.model.PushP2PReq;

import net.sf.json.JSONObject;

public interface XicaiServiceClient {

	/**
	 * 
	 * @Title: pushP2P 
	 * @Description: 把所有需要添加/更新到希财的产品的产品id进行推送
	 * @param req
	 * @return
	 * @throws
	 */
	public JSONObject pushP2P(PushP2PReq req);
	
	/**
	 * 
	 * @Title: investCallBack 
	 * @Description: 希财投资回调接口
	 * @return
	 * @throws
	 */
	public JSONObject investCallBack(InvestCallBackReq req);
	
	/**
	 * 
	 * @Title: investCallBack 
	 * @Description: 希财绑定回调接口
	 * @return
	 * @throws
	 */
	public JSONObject bindUserCallBack(BindUserCallBackReq req);
}
