package com.pinting.business.service.site.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pinting.business.dao.BsSysSubAccountMapper;
import com.pinting.business.model.BsSysSubAccount;
import com.pinting.business.model.BsSysSubAccountExample;
import com.pinting.business.service.site.SysSubAccountService;
import com.pinting.business.util.Constants;

/**
 * 
 * @Project: business
 * @Title: SysSubAccountServiceImpl.java
 * @author Zhou Changzai
 * @date 2015-11-25下午5:35:34
 * @Copyright: 2015 BiGangWan.com Inc. All rights reserved.
 */
@Service
public class SysSubAccountServiceImpl implements SysSubAccountService {

	@Autowired
	private BsSysSubAccountMapper bsSysSubAccountMapper;
	
	@Override
	public BsSysSubAccount findSysJSH() {
		return findSubAccountByCode(Constants.SYS_ACCOUNT_JSH);
	}

	@Override
	public BsSysSubAccount findSysUSER() {
		return findSubAccountByCode(Constants.SYS_ACCOUNT_USER);
	}

	//根据子账户编码查询子账户信息
	private BsSysSubAccount findSubAccountByCode(String code){
		BsSysSubAccountExample example = new BsSysSubAccountExample();
		example.createCriteria().andCodeEqualTo(code);
		return bsSysSubAccountMapper.selectByExample(example).get(0);
		
	}
}


