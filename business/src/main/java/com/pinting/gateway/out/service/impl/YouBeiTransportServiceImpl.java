package com.pinting.gateway.out.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.pinting.core.hessian.service.HessianService;
import com.pinting.gateway.hessian.message.youbei.B2GReqMsg_YouBei_CheckRealName;
import com.pinting.gateway.hessian.message.youbei.B2GResMsg_YouBei_CheckRealName;
import com.pinting.gateway.out.service.YouBeiTransportService;

@Service
public class YouBeiTransportServiceImpl implements YouBeiTransportService {

	@Autowired
	@Qualifier("youbeiGatewayService")
	private HessianService youbeiGatewayService;
	
	@Override
	public B2GResMsg_YouBei_CheckRealName checkRealName(B2GReqMsg_YouBei_CheckRealName req) {
		B2GResMsg_YouBei_CheckRealName res = (B2GResMsg_YouBei_CheckRealName) youbeiGatewayService.handleMsg(req);
		return res;
	}

}
