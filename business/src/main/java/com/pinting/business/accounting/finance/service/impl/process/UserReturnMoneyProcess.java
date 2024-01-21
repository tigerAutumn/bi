package com.pinting.business.accounting.finance.service.impl.process;

import com.pinting.business.accounting.finance.service.UserReturnMoneyService;
import com.pinting.business.model.BsBatchBuy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;


/**
 * 确认收到达飞款项后，批量回款给客户
 * @Project: business
 * @Title: UserReturnMoneyProcess.java
 * @author dingpf
 * @date 2015-11-18 下午2:12:19
 * @Copyright: 2015 BiGangWan.com Inc. All rights reserved.
 */
public class UserReturnMoneyProcess implements Runnable {
	private Logger log = LoggerFactory.getLogger(UserReturnMoneyProcess.class);
	//回款批次列表
	private List<BsBatchBuy> receiveBatchs; 
	
	private UserReturnMoneyService userReturnMoneyService;
	
	
	@Override
	public void run() {
		//开始回款
		userReturnMoneyService.returnBatch(receiveBatchs);
	}

	public void setReceiveBatchs(List<BsBatchBuy> receiveBatchs) {
		this.receiveBatchs = receiveBatchs;
	}

	public void setUserReturnMoneyService(UserReturnMoneyService userReturnMoneyService) {
		this.userReturnMoneyService = userReturnMoneyService;
	}
}
