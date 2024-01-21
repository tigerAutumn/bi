package com.pinting.business.service.site;

import com.pinting.business.model.BsSysSubAccount;

/**
 * 
 * @Project: business
 * @Title: SysSubAccountService.java
 * @author Zhou Changzai
 * @date 2015-11-25下午5:31:40
 * @Copyright: 2015 BiGangWan.com Inc. All rights reserved.
 */
public interface SysSubAccountService {
	/**
	 * 查询系统子账户表中的结算户
	 * @return BsSysSubAccount
	 */
	public BsSysSubAccount findSysJSH();
	
	/**
	 * 查询系统子账户表中的用户余额户
	 * @return BsSysSubAccount
	 */
	public BsSysSubAccount findSysUSER();
	
	
}


