package com.pinting.business.service.site.impl;

import com.pinting.business.dao.BsAgentViewConfigMapper;
import com.pinting.business.service.site.BsAgentViewConfigService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class BsAgentViewConfigServiceImpl implements BsAgentViewConfigService {
	
	@Autowired
	private BsAgentViewConfigMapper bsAgentViewConfigMapper;

	@Override
	public List<Integer> getAgentIds() {
		return bsAgentViewConfigMapper.getAgentIds();
	}

	@Override
	public List<Integer> getQianbaoAgentIds() {
		
		return bsAgentViewConfigMapper.getQianbaoAgentIds();
	}

	@Override
	public boolean isQianbao(Integer agentId) {
		// 是否在配置表里存在agentId
		if(agentId == null) return false;
    	List<Integer> agentIds= getQianbaoAgentIds();
    	if(CollectionUtils.isNotEmpty(agentIds)){
    		for (Integer integer : agentIds) {
    			if(agentId == integer){
    				return true;
    			}
			}
    	}
		return false;
	}
	
	
	
	

}
