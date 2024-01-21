package com.pinting.business.dayend.task;

import com.pinting.business.accounting.loan.service.LoanRelationshipService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 赞分期超级理财用户债权转让
 * @author bianyatian
 * @2016-8-29 下午7:25:14
 */
@Service
public class LnCreditTransferTask {
	private Logger log = LoggerFactory.getLogger(LnCreditTransferTask.class);
	
	@Autowired
	private LoanRelationshipService loanRelationshipService;
	
	public void execute() {
		log.info("==================超级理财用户债权转让 开始 =================");
		try {
			loanRelationshipService.superTransferNormal();
		} catch (Exception e) {
			e.printStackTrace();
		}
		log.info("==================超级理财用户债权转让 结束 =================");
	}
}
