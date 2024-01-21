package com.pinting.business.coreflow.transfer.service.Impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pinting.business.accounting.loan.model.LoanRelation4TransferVO;
import com.pinting.business.accounting.loan.service.DepFixedLoanRelationshipService;
import com.pinting.business.coreflow.core.model.FlowContext;
import com.pinting.business.model.LnFinanceRepaySchedule;
import com.pinting.core.hessian.msg.ResMsg;
import com.pinting.core.util.DateUtil;

/**
 * 默认债转获取承接人应付数据--实现七贷
 * @project business
 * @author bianyatian
 * @2018-6-27 下午4:29:01
 */
@Service("transferGetNeedPay4DefaultServiceImpl")
public class TransferGetNeedPay4DefaultServiceImpl extends
		AbstractTransferServiceImpl {

	@Autowired
	DepFixedLoanRelationshipService depFixedLoanRelationshipService;


	@Override
	protected LnFinanceRepaySchedule calNeedPay(LoanRelation4TransferVO record) {
		Date today = DateUtil.parseDate(DateUtil.formatYYYYMMDD(new Date()));
		LnFinanceRepaySchedule scheduleTemp = depFixedLoanRelationshipService.getFinanceRepaySchedule4SevenTransfer(record, today);
		return scheduleTemp;
	}
}
