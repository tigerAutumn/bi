package com.pinting.business.dayend.task.process;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pinting.business.service.site.LoanRelationService;
import com.pinting.gateway.hessian.message.dafy.model.Data;
import com.pinting.gateway.hessian.message.dafy.model.LoanRelationData;

public class AddNewLoanRelativeRecordProcess implements Runnable {
	private Logger log = LoggerFactory
			.getLogger(AddNewLoanRelativeRecordProcess.class);
	private LoanRelationService loanRelationService;
	private String sendBatchId;
	private List<Data> list;

	public void setLoanRelationService(LoanRelationService loanRelationService) {
		this.loanRelationService = loanRelationService;
	}

	public void setSendBatchId(String sendBatchId) {
		this.sendBatchId = sendBatchId;
	}

	public void setList(List<Data> list) {
		this.list = list;
	}

	@Override
	public void run() {
		loanRelationService.addNewBatchLoanRelationsRecord(list, sendBatchId);
	}

}
