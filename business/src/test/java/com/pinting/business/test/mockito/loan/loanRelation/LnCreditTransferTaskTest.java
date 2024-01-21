package com.pinting.business.test.mockito.loan.loanRelation;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.pinting.business.accounting.loan.service.LoanRelationshipService;
import com.pinting.business.dayend.task.LnCreditTransferTask;
import com.pinting.business.test.TestBase;

/**
 * 超级理财人债权转让定时任务
 * @author bianyatian
 * @2016-9-12 下午7:46:56
 */
public class LnCreditTransferTaskTest extends TestBase{
	
	@InjectMocks
	private LnCreditTransferTask lnCreditTransferTaskTest;
	
	@Mock
	private LoanRelationshipService loanRelationshipService;

	@Before
	public void mockBefore() {
		lnCreditTransferTaskTest = new LnCreditTransferTask();
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void mockTest() {
		Mockito.doReturn(true).when(loanRelationshipService).superTransferNormal();
		lnCreditTransferTaskTest.execute();
		Mockito.verify(loanRelationshipService).superTransferNormal();
	}
}
