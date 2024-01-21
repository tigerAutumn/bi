package com.pinting.mall.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.pinting.mall.dao.MallBsSysConfigMapper;
import com.pinting.mall.model.MallBsSysConfig;
import com.pinting.mall.service.MallSysConfigService;

/**
 * 
 * @Project: mall
 * @Title: MallSysConfigServiceImpl.java
 * @author Gemma
 * @date 2018-5-10 13:45:50
 * @Copyright: 2015 BiGangWan.com Inc. All rights reserved.
 */
@Service
public class MallSysConfigServiceImpl implements MallSysConfigService{

	@Autowired
	private MallBsSysConfigMapper bsSysConfigMapper;
	
	@Override
	public MallBsSysConfig findConfigValueByKey(String key) {
		return bsSysConfigMapper.selectByPrimaryKey(key);
	}
	
}
