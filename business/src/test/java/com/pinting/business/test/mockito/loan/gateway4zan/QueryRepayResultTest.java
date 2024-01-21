package com.pinting.business.test.mockito.loan.gateway4zan;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.util.ReflectionTestUtils;

import com.pinting.business.dao.LnRepayMapper;
import com.pinting.business.model.vo.LnRepayVO;
import com.pinting.business.service.loan.RepayQueryService;
import com.pinting.business.test.TestBase;
import com.pinting.business.test.mockito.AopTargetUtils;
import com.pinting.business.util.Constants;
import com.pinting.gateway.hessian.message.loan.G2BReqMsg_Repay_QueryRepayResult;
import com.pinting.gateway.hessian.message.loan.G2BResMsg_Repay_QueryRepayResult;

/**
 * 还款结果查询
 * @author bianyatian
 * @2016-9-20 下午3:26:52
 */
public class QueryRepayResultTest extends TestBase {

	@Autowired
	private RepayQueryService repayQueryService;
	@Mock
	private LnRepayMapper repayMapper;
	
	@Before
	public void mockBefore() {
		MockitoAnnotations.initMocks(this);
		ReflectionTestUtils.setField(AopTargetUtils.getTarget(repayQueryService), "repayMapper", repayMapper);
	}
	
	@Test
	public void selectTest() {
		//对方法设定预期返回值
		LnRepayVO lnRepayVO = new LnRepayVO();
		lnRepayVO.setPartnerLoanId("111");
		lnRepayVO.setChannel("channel");
		//lnRepayVO.setStatus(Constants.LN_REPAY_PAY_STATUS_REPAIED);
		//lnRepayVO.setReturnMsg("成功");
		//lnRepayVO.setStatus(Constants.LN_REPAY_PAY_STATUS_REPAY_FAIL);
		//lnRepayVO.setReturnMsg("xxx");
		lnRepayVO.setStatus(Constants.LN_REPAY_PAY_STATUS_REPAYING);
		lnRepayVO.setReturnMsg("sss");
		
		Mockito.doReturn(lnRepayVO).when(repayMapper).selectByPartnerOrderNo("111");
		G2BReqMsg_Repay_QueryRepayResult req = new G2BReqMsg_Repay_QueryRepayResult();
		req.setOrderNo("111");
        G2BResMsg_Repay_QueryRepayResult res = new G2BResMsg_Repay_QueryRepayResult();
	
        try {
			repayQueryService.queryRepayResult(req, res);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        System.out.println(res.getRepayResultCode()); 
        System.out.println(res.getRepayResultMsg());
        Mockito.verify(repayMapper).selectByPartnerOrderNo("111");
	}
}
