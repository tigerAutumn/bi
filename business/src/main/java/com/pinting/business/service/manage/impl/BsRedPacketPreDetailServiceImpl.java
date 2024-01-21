package com.pinting.business.service.manage.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pinting.business.dao.BsRedPacketPreDetailMapper;
import com.pinting.business.service.manage.BsRedPacketPreDetailService;

@Service
public class BsRedPacketPreDetailServiceImpl implements BsRedPacketPreDetailService {

	@Autowired
	BsRedPacketPreDetailMapper bsRedPacketPreDetailMapper;

	@Override
	public int saveRedPacketPreDetail(String sql) {
		return bsRedPacketPreDetailMapper.saveRedPacketPreDetail(sql);
	}
	
	
}
