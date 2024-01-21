package com.pinting.business.mockito;

import com.pinting.business.accounting.finance.service.DepUserBalanceWithdrawService;
import com.pinting.business.accounting.finance.service.UserBalanceWithdrawService;
import com.pinting.business.service.manage.DepTargetSendAgainService;
import com.pinting.core.util.ConstantUtil;
import com.pinting.core.util.DateUtil;
import com.pinting.gateway.hessian.message.baofoo.B2GReqMsg_BaoFooQuickPay_Pay4Trans;
import com.pinting.gateway.hessian.message.baofoo.B2GResMsg_BaoFooQuickPay_Pay4Trans;
import com.pinting.gateway.hessian.message.hfbank.B2GReqMsg_HFBank_UserBatchWithdraw;
import com.pinting.gateway.hessian.message.hfbank.B2GResMsg_HFBank_UserBatchWithdraw;
import com.pinting.gateway.hessian.message.hfbank.model.BatchWithdrawExtSuccessData;
import com.pinting.gateway.out.service.BaoFooTransportService;
import com.pinting.gateway.out.service.HfbankTransportService;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Author:      cyb
 * Date:        2017/4/12
 * Description:
 */
//@Aspect
//@Component
public class DepUserWithdrawMockAspect {

    @Autowired
    private DepUserBalanceWithdrawService depUserBalanceWithdrawService;

    @Autowired
    private HfbankTransportService hfbankTransportService;
    
    @Autowired
    private DepTargetSendAgainService depTargetSendAgainService;
    
   
    @Pointcut("execution(public * com.pinting.business.accounting.finance.service.impl.DepUserBalanceWithdrawServiceImpl.apply(..))")
    public void userWithdrawApply(){}
    
    @Pointcut("execution(public * com.pinting.business.service.manage.impl.DepTargetSendAgainServiceImpl.targetChargeOffCallBack(..))")
    public void userBatchWithdraw(){}


    @Before(value = "userWithdrawApply()")
    public void mockBefore() {
        HfbankTransportService mockHfbankTransportService = Mockito.mock(HfbankTransportService.class);
        ReflectionTestUtils.setField(AopTargetUtils.getTarget(depUserBalanceWithdrawService), "hfbankTransportService", mockHfbankTransportService);
        B2GResMsg_HFBank_UserBatchWithdraw res = new B2GResMsg_HFBank_UserBatchWithdraw();
        res.setResCode(ConstantUtil.BSRESCODE_SUCCESS);
        res.setFinish_datetime(DateUtil.format(new Date()));
        res.setOrder_status("2");
        res.setOrder_info("");
        res.setTotal_num("1");
        res.setSuccess_num("1");
        List<BatchWithdrawExtSuccessData> success_data = new ArrayList<>();
        BatchWithdrawExtSuccessData data = new BatchWithdrawExtSuccessData();
        data.setDetail_no("WITHDRAW_"+DateUtil.formatDateTime(new Date(), "yyyyMMddHHmmss"));
        data.setAmt("2000");
        res.setSuccess_data(success_data);
        res.setError_data(null);
        Mockito.doReturn(res).when(mockHfbankTransportService).userBatchWithdraw(Mockito.any(B2GReqMsg_HFBank_UserBatchWithdraw.class));
    }

    @After(value = "userWithdrawApply()")
    public void mockAfter() {
		ReflectionTestUtils.setField(AopTargetUtils.getTarget(depUserBalanceWithdrawService), "hfbankTransportService", hfbankTransportService);
    }

    
    @Before(value = "userBatchWithdraw()")
    public void manageBatchMockBefore() {
    	 HfbankTransportService mockHfbankTransportService = Mockito.mock(HfbankTransportService.class);
         ReflectionTestUtils.setField(AopTargetUtils.getTarget(depTargetSendAgainService), "hfbankTransportService", mockHfbankTransportService);
         B2GResMsg_HFBank_UserBatchWithdraw res = new B2GResMsg_HFBank_UserBatchWithdraw();
         res.setResCode(ConstantUtil.BSRESCODE_FAIL);
         Mockito.doReturn(res).when(mockHfbankTransportService).userBatchWithdraw(Mockito.any(B2GReqMsg_HFBank_UserBatchWithdraw.class));
    }
    
    @After(value = "userBatchWithdraw()")
    public void manageBatchMockAfter() {
		ReflectionTestUtils.setField(AopTargetUtils.getTarget(depTargetSendAgainService), "hfbankTransportService", hfbankTransportService);
    }
    
}
