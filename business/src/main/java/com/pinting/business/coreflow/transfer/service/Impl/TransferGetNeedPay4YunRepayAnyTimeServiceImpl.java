package com.pinting.business.coreflow.transfer.service.Impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pinting.business.accounting.loan.model.LoanRelation4TransferVO;
import com.pinting.business.accounting.loan.service.DepFixedLoanRelationshipService;
import com.pinting.business.model.LnFinanceRepaySchedule;
import com.pinting.core.util.DateUtil;

/**
 * 云贷随借随还债转获取承接人应付数据
 * @project business
 * @author bianyatian
 * @2018-6-27 下午4:36:07
 */
@Service("transferGetNeedPay4YunRepayAnyTimeServiceImpl")
public class TransferGetNeedPay4YunRepayAnyTimeServiceImpl extends
		AbstractTransferServiceImpl {
	@Autowired
	DepFixedLoanRelationshipService depFixedLoanRelationshipService;

	@Override
	protected LnFinanceRepaySchedule calNeedPay(LoanRelation4TransferVO record) {
		Date today = DateUtil.parseDate(DateUtil.formatYYYYMMDD(new Date()));
		LnFinanceRepaySchedule scheduleTemp = depFixedLoanRelationshipService.generateFinanceRepaySchedule(record, record.getLeftAmount(), today, null, record.getAgreementRate(), record.getLeftAmount());
		return scheduleTemp;
	}
}
