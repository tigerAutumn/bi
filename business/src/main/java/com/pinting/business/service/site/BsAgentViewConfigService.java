package com.pinting.business.service.site;

import java.util.List;

public interface BsAgentViewConfigService {

	/**
	 * 获取相关的agentid列表
	 * @return
	 */
	List<Integer> getAgentIds();

	/**
	 * 获取钱报相关的agentid列表
	 * @return
	 */
	List<Integer> getQianbaoAgentIds();
	
	/**
	 * 根据agentId判断是否是agentViewConfig表中的agentId
	 * @param agentId
	 * @return
	 */
	boolean isQianbao(Integer agentId);
}
