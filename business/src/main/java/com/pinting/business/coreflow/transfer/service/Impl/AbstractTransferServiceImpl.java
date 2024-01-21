package com.pinting.business.coreflow.transfer.service.Impl;

import com.pinting.business.accounting.loan.model.LoanRelation4TransferVO;
import com.pinting.business.coreflow.core.model.FlowContext;
import com.pinting.business.coreflow.core.service.DepFixedService;
import com.pinting.business.enums.PTMessageEnum;
import com.pinting.business.exception.PTMessageException;
import com.pinting.business.model.LnFinanceRepaySchedule;
import com.pinting.core.hessian.msg.ResMsg;

public class AbstractTransferServiceImpl implements DepFixedService {

	@Override
	public ResMsg execute(FlowContext flowContext) {
		
		LoanRelation4TransferVO record = (LoanRelation4TransferVO) flowContext.getExtendMap().get("loanRelation4TransferRecord");
		if(record == null){
			throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR,",转出债权数据校验失败");
		}
		LnFinanceRepaySchedule scheduleTemp = calNeedPay(record);
		flowContext.setRes(scheduleTemp);
		return scheduleTemp;
	}

	protected LnFinanceRepaySchedule calNeedPay(LoanRelation4TransferVO record) {
		LnFinanceRepaySchedule scheduleTemp = new LnFinanceRepaySchedule();
		return scheduleTemp;
	}

}
