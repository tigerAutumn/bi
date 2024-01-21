package com.pinting.gateway.out.service;

import com.pinting.gateway.hessian.message.xicai.B2GReqMsg_Xicai_BindUserCallBack;
import com.pinting.gateway.hessian.message.xicai.B2GReqMsg_Xicai_InvestCallBack;
import com.pinting.gateway.hessian.message.xicai.B2GReqMsg_Xicai_PushP2P;
import com.pinting.gateway.hessian.message.xicai.B2GResMsg_Xicai_BindUserCallBack;
import com.pinting.gateway.hessian.message.xicai.B2GResMsg_Xicai_InvestCallBack;
import com.pinting.gateway.hessian.message.xicai.B2GResMsg_Xicai_PushP2P;

/**
 * 希财接口调用
 * @Project: business
 * @Title: ReapalTransportService.java
 * @author dingpf
 * @date 2016-1-7 下午1:48:27
 * @Copyright: 2016 BiGangWan.com Inc. All rights reserved.
 */
public interface XicaiTransportService {
	
	/**
	 * 
	 * @Title: memoryCardSign 
	 * @Description: 推送产品ID接口
	 * @param req
	 * @return
	 * @throws
	 */
	public B2GResMsg_Xicai_PushP2P pushP2P(B2GReqMsg_Xicai_PushP2P req);
	
	/**
	 * 
	 * @Title: investCallBack 
	 * @Description: 投资回调接口
	 * @param req
	 * @return
	 * @throws
	 */
	public B2GResMsg_Xicai_InvestCallBack investCallBack(B2GReqMsg_Xicai_InvestCallBack req);
	
	/**
	 * 
	 * @Title: bindUserCallBack 
	 * @Description: 绑定回调接口
	 * @param req
	 * @return
	 * @throws
	 */
	public B2GResMsg_Xicai_BindUserCallBack bindUserCallBack(B2GReqMsg_Xicai_BindUserCallBack req);
	
}
