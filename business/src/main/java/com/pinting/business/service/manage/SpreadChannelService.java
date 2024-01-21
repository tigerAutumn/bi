package com.pinting.business.service.manage;

import java.util.List;

import com.pinting.business.model.vo.SpreadChannelVO;

public interface SpreadChannelService {

	/**
	 * 查询推广渠道列表
	 * @param status 
	 * @param agentName 
	 * @return
	 */
	 List<SpreadChannelVO>  querySpreadChannelList(Integer start,Integer numPerPage, String agentName, String status);
	
	/**
	 * 查询推广渠道列表条数
	 * @return
	 */
	 Integer getTotalCount(String agentName, String status);
}
