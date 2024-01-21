package com.pinting.business.service.manage.impl;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.pinting.business.dao.BsSysConfigMapper;
import com.pinting.business.model.BsSysConfig;
import com.pinting.business.service.manage.BsSysConfigService;
import com.pinting.gateway.in.util.MethodRole;

@Service
public class BsSysConfigServiceImpl implements BsSysConfigService{

	@Autowired
	private BsSysConfigMapper bsSysConfigMapper;
	
	@Override
	@MethodRole("APP")
	public BsSysConfig findKey(String confKey) {
		return bsSysConfigMapper.selectByPrimaryKey(confKey);
	}

	@Override
	public List<BsSysConfig> findList() {
		return bsSysConfigMapper.selectByExample(null);
	}

}
