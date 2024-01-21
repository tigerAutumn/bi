package com.pinting.gateway.out.service;

import com.pinting.gateway.hessian.message.youbei.B2GReqMsg_YouBei_CheckRealName;
import com.pinting.gateway.hessian.message.youbei.B2GResMsg_YouBei_CheckRealName;

public interface YouBeiTransportService {

	/**
	 * 实名认证
	 * @param req
	 * @return
	 */
	public B2GResMsg_YouBei_CheckRealName checkRealName(B2GReqMsg_YouBei_CheckRealName req);
}
