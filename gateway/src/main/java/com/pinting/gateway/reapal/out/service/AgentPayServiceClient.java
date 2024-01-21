package com.pinting.gateway.reapal.out.service;

import java.util.List;

import com.pinting.gateway.reapal.out.model.req.AgentPayQueryReq;
import com.pinting.gateway.reapal.out.model.req.AgentPayReq;
import com.pinting.gateway.reapal.out.model.req.BatchContent;
import com.pinting.gateway.reapal.out.model.resp.AgentPayQueryResp;
import com.pinting.gateway.reapal.out.model.resp.AgentPayResp;

public interface AgentPayServiceClient {
	
	/**
	 * 代付提交接口
	 * @param req 代付提交请求
	 * @param batchContent 批量代付明细（此方法中，将转换成字符串后放入req请求对象）
	 * @return
	 */
	public AgentPayResp agentPaySubmit(AgentPayReq req, List<BatchContent> batchContent);
	
	/**
	 * 代付查询
	 * @param req 查询请求
	 * @return 包含批量代付明细
	 */
	public AgentPayQueryResp agentPayQuery(AgentPayQueryReq req);

}
