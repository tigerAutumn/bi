package com.pinting.business.coreflow.transfer.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pinting.business.accounting.loan.model.LoanRelation4TransferVO;
import com.pinting.business.accounting.loan.service.DepFixedLoanRelationshipService;
import com.pinting.business.coreflow.core.model.FlowContext;
import com.pinting.business.model.LnFinanceRepaySchedule;
import com.pinting.core.hessian.msg.ResMsg;

/**
 * 云贷等额本息债转获取承接人应付数据
 * @project business
 * @author bianyatian
 * @2018-6-27 下午2:56:10
 */
@Service("transferGetNeedPay4YunFixedInstallmentServiceImpl")
public class TransferGetNeedPay4YunFixedInstallmentServiceImpl extends
		AbstractTransferServiceImpl {
	@Autowired
	DepFixedLoanRelationshipService depFixedLoanRelationshipService;

	@Override
	protected LnFinanceRepaySchedule calNeedPay(LoanRelation4TransferVO record) {
		LnFinanceRepaySchedule scheduleTemp = depFixedLoanRelationshipService.getFinanceRepaySchedule4FixedInstallmentTransfer(record);
		return scheduleTemp;
	}

}
