package com.pinting.business.service.manage.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pinting.business.dao.BsSysSubAccountMapper;
import com.pinting.business.service.manage.MBsSysSubAccountService;
/**
 * 系统子账户接口实现类
 * @author yanwl
 * @date 2015-12-07
 *
 */
@Service
public class MBsSysSubAccountServiceImpl implements MBsSysSubAccountService{
	
	@Autowired
	public BsSysSubAccountMapper bsSysSubAccountMapper;

	@Override
	public Map<String, Object> countSysSubAccountBalance() {
		return bsSysSubAccountMapper.countSysSubAccountBalance();
	}

	@Override
	public Map<String, Object> countThdSysSubAccountBalance() {
		return bsSysSubAccountMapper.countThdSysSubAccountBalance();
	}

	@Override
	public Map<String, Object> countDepSysSubAccountBalance() {
		return bsSysSubAccountMapper.countDepSysSubAccountBalance();
	}
}
