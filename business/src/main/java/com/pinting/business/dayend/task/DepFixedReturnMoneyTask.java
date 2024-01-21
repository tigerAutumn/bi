package com.pinting.business.dayend.task;

import com.pinting.business.accounting.finance.service.UserReturnMoneyService;
import com.pinting.business.dao.BsDepositionReturnMapper;
import com.pinting.business.model.BsDepositionReturn;
import com.pinting.business.model.BsDepositionReturnExample;
import com.pinting.business.util.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @project business
 * @title DepFixedReturnMoneyTask.java
 * @author Dragon & cat
 * @date 2017-4-8
 * @Copyright 2015 BiGangWan.com Inc. All rights reserved.
 * @Description 
 */
@Service
public class DepFixedReturnMoneyTask {
	private Logger log = LoggerFactory.getLogger(DepFixedReturnMoneyTask.class);
	@Autowired
	private UserReturnMoneyService userReturnMoneyService;
	@Autowired
	private BsDepositionReturnMapper bsDepositionReturnMapper;
	
	
	public void execute() {
		log.info("==================【存管定期产品理财人回款到余额】 开始 =================");
		try {
			List<String> statusStrings = new ArrayList<String>();
			statusStrings.add(Constants.DEP_RETURN_STATUS_INIT);
			statusStrings.add(Constants.DEP_RETURN_STATUS_FAIL);
			BsDepositionReturnExample bsDepositionReturnExample = new BsDepositionReturnExample();
			bsDepositionReturnExample.createCriteria().andStatusIn(statusStrings);
			List<BsDepositionReturn> bsDepositionReturns = bsDepositionReturnMapper.selectByExample(bsDepositionReturnExample);
			for (BsDepositionReturn bsDepositionReturn : bsDepositionReturns) {
				userReturnMoneyService.depReturn2Balance(bsDepositionReturn);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		log.info("==================【存管定期产品理财人回款到余额】 结束 =================");
	}
}
