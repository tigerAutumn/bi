package com.pinting.business.accounting.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.pinting.gateway.hessian.message.dafy.model.InvestmentAmounts;

/**
 * 对账接口
 * @Project: business
 * @Title: ReconciliationService.java
 * @author dingpf
 * @date 2015-3-3 下午2:35:04
 * @Copyright: 2015 BiGangWan.com Inc. All rights reserved.
 */
public interface ReconciliationService {
	/**
	 * 向达飞对账
	 * @param queryDate 对账日期
	 * @return 成功返回对账列表，否则返回null
	 */
	public List<InvestmentAmounts> checkAccount2Dafy(Date queryDate);
	
}
