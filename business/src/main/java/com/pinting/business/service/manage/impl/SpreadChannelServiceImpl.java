package com.pinting.business.service.manage.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pinting.business.dao.BsAgentMapper;

import com.pinting.business.model.vo.SpreadChannelVO;
import com.pinting.business.service.manage.SpreadChannelService;

@Service
public class SpreadChannelServiceImpl implements SpreadChannelService{

	@Autowired
	private BsAgentMapper bsAgentMapper;
	
	@Override
	public List<SpreadChannelVO> querySpreadChannelList(Integer start,
			Integer numPerPage,String agentName, String status) {
		// TODO Auto-generated method stub
		List<SpreadChannelVO> list = bsAgentMapper.querySpreadChannelList(start,numPerPage,agentName,status);
		return list;
	}

	@Override
	public Integer getTotalCount(String agentName, String status) {
		// TODO Auto-generated method stub
		return bsAgentMapper.getTotalCount(agentName,status);
	}

}
