package com.pinting.business.mockito;

import java.util.Date;

import com.pinting.gateway.hessian.message.loan.G2BReqMsg_Loan_Loan;
import net.sf.json.JSONObject;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.test.util.ReflectionTestUtils;

import com.pinting.core.hessian.service.HessianService;
import com.pinting.core.util.DateUtil;
import com.pinting.gateway.hessian.message.baofoo.B2GReqMsg_BaoFooCutpayment_CutpaymentStatusQuery;
import com.pinting.gateway.hessian.message.baofoo.B2GReqMsg_BaoFooQuickPay_Pay4StatusQuery;
import com.pinting.gateway.hessian.message.baofoo.B2GReqMsg_BaoFooQuickPay_QuickPayStatusQuery;
import com.pinting.gateway.hessian.message.baofoo.B2GResMsg_BaoFooCutpayment_CutpaymentStatusQuery;
import com.pinting.gateway.hessian.message.baofoo.B2GResMsg_BaoFooQuickPay_Pay4StatusQuery;
import com.pinting.gateway.hessian.message.baofoo.B2GResMsg_BaoFooQuickPay_QuickPayStatusQuery;
import com.pinting.gateway.out.service.BaoFooTransportService;

/**
 * CheckBaoFooPayBirdOrderTask宝付相关mock
 * @author bianyatian
 * @2017-10-23 下午4:26:25
 */
//@Aspect
//@Component
public class CheckBaoFooPayBirdOrderMockAspect {

    private Logger logger = LoggerFactory.getLogger(UserWithdrawMockAspect.class);

    @Autowired
	private BaoFooTransportService baoFooTransportService;
	
	@Autowired
	@Qualifier("baoFooGatewayService")
	private HessianService springBaoFooGatewayService;
	
	
	@Pointcut("execution(public * com.pinting.gateway.out.service.impl.BaoFooTransportServiceImpl.syncPay4Status(..))")
    public void syncPay4Status(){}


    @Before(value = "syncPay4Status()")
    public void syncPay4StatusBefore(JoinPoint joinPoint) {
        try {
            B2GReqMsg_BaoFooQuickPay_Pay4StatusQuery req = (B2GReqMsg_BaoFooQuickPay_Pay4StatusQuery)joinPoint.getArgs()[0];
            logger.info("syncPay4Status CheckBaoFooPayBirdOrderTask宝付相关mock：{}", JSONObject.fromObject(req));
        } catch (Exception e) {
            e.printStackTrace();
        }
    	HessianService mockHessianService = Mockito.mock(HessianService.class);
        ReflectionTestUtils.setField(AopTargetUtils.getTarget(baoFooTransportService), "baoFooGatewayService", mockHessianService);
        B2GResMsg_BaoFooQuickPay_Pay4StatusQuery res = new B2GResMsg_BaoFooQuickPay_Pay4StatusQuery();
        res.setResCode("000000");
        res.setTrans_money("1000");
        res.setTrans_no("000");
        res.setTrans_orderid("111");
        res.setTrans_endtime(DateUtil.format(new Date()));
        res.setResMsg("mock成功");
        
        ((HessianService) Mockito.doReturn(res).when(mockHessianService)).handleMsg(Mockito.any(B2GReqMsg_BaoFooQuickPay_Pay4StatusQuery.class));
    }

    @After(value = "syncPay4Status()")
    public void syncPay4StatusAfter() {
        ReflectionTestUtils.setField(AopTargetUtils.getTarget(baoFooTransportService), "baoFooGatewayService", springBaoFooGatewayService);
    }
    
    
    
    @Pointcut("execution(public * com.pinting.gateway.out.service.impl.BaoFooTransportServiceImpl.syncQuickPayStauts(..))")
    public void syncQuickPayStauts(){}


    @Before(value = "syncQuickPayStauts()")
    public void syncQuickPayStautsBefore(JoinPoint joinPoint) {
        try {
            B2GReqMsg_BaoFooQuickPay_QuickPayStatusQuery req = (B2GReqMsg_BaoFooQuickPay_QuickPayStatusQuery)joinPoint.getArgs()[0];
            logger.info("syncQuickPayStauts CheckBaoFooPayBirdOrderTask宝付相关mock：{}", JSONObject.fromObject(req));
        } catch (Exception e) {
            e.printStackTrace();
        }
    	HessianService mockHessianService = Mockito.mock(HessianService.class);
        ReflectionTestUtils.setField(AopTargetUtils.getTarget(baoFooTransportService), "baoFooGatewayService", mockHessianService);
        B2GResMsg_BaoFooQuickPay_QuickPayStatusQuery res = new B2GResMsg_BaoFooQuickPay_QuickPayStatusQuery();
        res.setResCode("000000");
        res.setSucc_amt("1000");
        res.setTrans_no("111");
        res.setTrans_id("111");
        res.setResMsg("mock成功");
        
        ((HessianService) Mockito.doReturn(res).when(mockHessianService)).handleMsg(Mockito.any(B2GReqMsg_BaoFooQuickPay_QuickPayStatusQuery.class));
    }

    @After(value = "syncQuickPayStauts()")
    public void syncQuickPayStautsAfter() {
        ReflectionTestUtils.setField(AopTargetUtils.getTarget(baoFooTransportService), "baoFooGatewayService", springBaoFooGatewayService);
    }

    
    
//    @Pointcut("execution(public * com.pinting.gateway.out.service.impl.BaoFooTransportServiceImpl.queryWithholdingStatus(..))")
//    public void queryWithholdingStatus(){}
//
//
//    @Before(value = "queryWithholdingStatus()")
//    public void queryWithholdingStatusBefore(JoinPoint joinPoint) {
//        try {
//            B2GReqMsg_BaoFooCutpayment_CutpaymentStatusQuery req = (B2GReqMsg_BaoFooCutpayment_CutpaymentStatusQuery)joinPoint.getArgs()[0];
//            logger.info("queryWithholdingStatus CheckBaoFooPayBirdOrderTask宝付相关mock：{}", JSONObject.fromObject(req));
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    	HessianService mockHessianService = Mockito.mock(HessianService.class);
//        ReflectionTestUtils.setField(AopTargetUtils.getTarget(baoFooTransportService), "baoFooGatewayService", mockHessianService);
//        B2GResMsg_BaoFooCutpayment_CutpaymentStatusQuery res = new B2GResMsg_BaoFooCutpayment_CutpaymentStatusQuery();
//        res.setResCode("000000");
//        res.setSucc_amt("1000");
//        res.setTrans_no("111");
//        res.setTrans_id("111");
//        res.setResMsg("mock成功");
//
//        ((HessianService) Mockito.doReturn(res).when(mockHessianService)).handleMsg(Mockito.any(B2GReqMsg_BaoFooCutpayment_CutpaymentStatusQuery.class));
//    }
//
//    @After(value = "queryWithholdingStatus()")
//    public void queryWithholdingStatusAfter() {
//        ReflectionTestUtils.setField(AopTargetUtils.getTarget(baoFooTransportService), "baoFooGatewayService", springBaoFooGatewayService);
//    }


}
