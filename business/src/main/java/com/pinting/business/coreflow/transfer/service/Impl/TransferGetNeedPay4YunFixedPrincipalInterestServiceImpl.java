package com.pinting.business.coreflow.transfer.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pinting.business.accounting.loan.model.LoanRelation4TransferVO;
import com.pinting.business.accounting.loan.service.DepFixedLoanRelationshipService;
import com.pinting.business.model.LnFinanceRepaySchedule;
/**
 * 云贷等本等息债转获取承接人应付数据
 * @project business
 * @author Gemma
 * @date 2018.6.28
 */
@Service("transferGetNeedPay4YunFixedPrincipalInterestServiceImpl")
public class TransferGetNeedPay4YunFixedPrincipalInterestServiceImpl extends
		AbstractTransferServiceImpl {
	@Autowired
	DepFixedLoanRelationshipService depFixedLoanRelationshipService;

	@Override
	protected LnFinanceRepaySchedule calNeedPay(LoanRelation4TransferVO record) {
		LnFinanceRepaySchedule scheduleTemp = depFixedLoanRelationshipService.getFinanceRepaySchedule4FixedPrincipalInterestTransfer(record);
		return scheduleTemp;
	}

}
