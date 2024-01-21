package com.pinting.business.accounting.service;

import java.util.List;

import com.pinting.business.model.BsSysReceiveMoney;

/**
 * 营销费用、服务费用打款记录处理
 * @Project: business
 * @Title: SysReceiveMoneyService.java
 * @author dingpf
 * @date 2015-4-13 下午4:05:13
 * @Copyright: 2015 BiGangWan.com Inc. All rights reserved.
 */
public interface SysReceiveMoneyService {
	
	/**
	 * 批量新增打款记录
	 * @param bsSysReceiveMoneys  新增记录列表
	 * @param trsType 网新账户交易类型
	 */
	public void batchAddSysReceiveMoneys(List<BsSysReceiveMoney> bsSysReceiveMoneys, String trsType);

}
