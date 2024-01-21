package com.pinting.business.accounting.service;

import com.pinting.business.model.BsCustomerReceiveMoney;

/**
 * 回款
 * @Project: business
 * @Title: CustomerReceiveMoneyService.java
 * @author dingpf
 * @date 2015-4-13 下午4:05:13
 * @Copyright: 2015 BiGangWan.com Inc. All rights reserved.
 */
public interface CustomerReceiveMoneyService {
	
	/**
	 * 新增回款通知记录
	 * @param bsCustomerReceiveMoney
	 */
	public void addCustomerReceiveMoney(BsCustomerReceiveMoney bsCustomerReceiveMoney);

}
