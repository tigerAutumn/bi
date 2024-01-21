package com.pinting.business.mockito;

import com.pinting.business.accounting.loan.enums.LoanStatus;
import com.pinting.business.accounting.loan.service.DepFixedRepayPaymentService;
import com.pinting.business.accounting.loan.service.RepayPaymentService;
import com.pinting.business.model.LnPayOrders;
import com.pinting.gateway.hessian.message.baofoo.B2GReqMsg_BaoFooAgreementPay_DirectAgreementPay;
import com.pinting.gateway.hessian.message.baofoo.B2GReqMsg_BaoFooCutpayment_Cutpayment;
import com.pinting.gateway.hessian.message.baofoo.B2GReqMsg_BaoFooQuickPay_Pay4StatusQuery;
import com.pinting.gateway.hessian.message.baofoo.B2GResMsg_BaoFooAgreementPay_DirectAgreementPay;
import com.pinting.gateway.hessian.message.baofoo.B2GResMsg_BaoFooCutpayment_Cutpayment;
import com.pinting.gateway.hessian.message.zsd.G2BReqMsg_ZsdRepay_RepayConfirm;
import com.pinting.gateway.out.service.BaoFooTransportService;
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
import org.springframework.stereotype.Component;
import org.springframework.test.util.ReflectionTestUtils;

/**
 * @title 代扣mock
 * Created by 剑钊 on 2017/1/3.
 */
//@Aspect
//@Component
public class CutpaymentBaoFooMockAspect {

    @Autowired
    private RepayPaymentService repayPaymentService;
    @Autowired
    private DepFixedRepayPaymentService depFixedRepayPaymentService;
    @Autowired
    private BaoFooTransportService springBaoFooTransportService;
    private Logger logger = LoggerFactory.getLogger(UserWithdrawMockAspect.class);

    @Pointcut("execution(public * com.pinting.business.accounting.loan.service.impl.RepayPaymentServiceImpl.withholdingRepaySendBaoFoo(..))")
    public void withholdingRepayPointcut(){}


    @Pointcut("execution(public * com.pinting.business.accounting.loan.service.impl.DepFixedRepayPaymentServiceImpl.withholdingRepayAsync(..))")
    public void depFixedDoWithholdingRepayPointcut(){}
    
    @Pointcut("execution(public * com.pinting.business.accounting.loan.service.impl.DepFixedRepayPaymentServiceImpl.doZsdRepayConfirm(..))")
    public void zsdRepayConfirmWithholdingPointcut(){}
    //赞时贷代扣
    @Pointcut("execution(public * com.pinting.business.accounting.loan.service.impl.DepFixedRepayPaymentServiceImpl.withholdingRepayZsdAsync(..))")
    public void depFixedDoWithholdingRepayZsdPointcut(){}
    
    

    
    @Before(value = "withholdingRepayPointcut()")
    public void mockBefore(JoinPoint joinPoint) {
        try {
            LnPayOrders req = (LnPayOrders)joinPoint.getArgs()[0];
            logger.info("withholdingRepayPointcut代扣mock：{}", JSONObject.fromObject(req));
        } catch (Exception e) {
            e.printStackTrace();
        }
        BaoFooTransportService mockBaoFooTransportService = Mockito.mock(BaoFooTransportService.class);
        ReflectionTestUtils.setField(AopTargetUtils.getTarget(repayPaymentService), "baoFooTransportService", mockBaoFooTransportService);
        B2GResMsg_BaoFooCutpayment_Cutpayment res = new B2GResMsg_BaoFooCutpayment_Cutpayment();
        res.setResCode("000000");
//        res.setResCode(LoanStatus.BAOFOO_PAY_STATUS_PROCESS.getCode());
//        res.setResCode("939999");
//        res.setResMsg("代扣失败啦~~~~");
        Mockito.doReturn(res).when(mockBaoFooTransportService).withholding(Mockito.any(B2GReqMsg_BaoFooCutpayment_Cutpayment.class));
    }

    @After(value = "withholdingRepayPointcut()")
    public void mockAfter() {
        ReflectionTestUtils.setField(AopTargetUtils.getTarget(repayPaymentService), "baoFooTransportService", springBaoFooTransportService);
    }
    
    
    /****************赞时贷*************************/
    
    @Before(value = "zsdRepayConfirmWithholdingPointcut()")
    public void zsdRepayConfirmMockBefore(JoinPoint joinPoint) {
        try {
            G2BReqMsg_ZsdRepay_RepayConfirm req = (G2BReqMsg_ZsdRepay_RepayConfirm)joinPoint.getArgs()[0];
            logger.info("zsdRepayConfirmWithholdingPointcut代扣mock：{}", JSONObject.fromObject(req));
        } catch (Exception e) {
            e.printStackTrace();
        }
        BaoFooTransportService mockBaoFooTransportService = Mockito.mock(BaoFooTransportService.class);
        ReflectionTestUtils.setField(AopTargetUtils.getTarget(depFixedRepayPaymentService), "baoFooTransportService", mockBaoFooTransportService);
        B2GResMsg_BaoFooCutpayment_Cutpayment res = new B2GResMsg_BaoFooCutpayment_Cutpayment();
        res.setResCode("000000");
        res.setResMsg("成功");
        Mockito.doReturn(res).when(mockBaoFooTransportService).withholding(Mockito.any(B2GReqMsg_BaoFooCutpayment_Cutpayment.class));
    }
    
    @After(value = "zsdRepayConfirmWithholdingPointcut()")
    public void zsdRepayConfirmMockAfter() {
        ReflectionTestUtils.setField(AopTargetUtils.getTarget(depFixedRepayPaymentService), "baoFooTransportService", springBaoFooTransportService);
    }


    @Before(value = "depFixedDoWithholdingRepayPointcut()")
    public void depFixedMockBefore(JoinPoint joinPoint) {
        try {
            LnPayOrders req = (LnPayOrders)joinPoint.getArgs()[0];
            logger.info("depFixedDoWithholdingRepayPointcut代扣mock：{}", JSONObject.fromObject(req));
        } catch (Exception e) {
            e.printStackTrace();
        }
        BaoFooTransportService mockBaoFooTransportService = Mockito.mock(BaoFooTransportService.class);
        ReflectionTestUtils.setField(AopTargetUtils.getTarget(depFixedRepayPaymentService), "baoFooTransportService", mockBaoFooTransportService);
        B2GResMsg_BaoFooCutpayment_Cutpayment res = new B2GResMsg_BaoFooCutpayment_Cutpayment();
        res.setResCode("000000");
//        res.setResCode(LoanStatus.BAOFOO_PAY_STATUS_PROCESS.getCode());
        Mockito.doReturn(res).when(mockBaoFooTransportService).withholding(Mockito.any(B2GReqMsg_BaoFooCutpayment_Cutpayment.class));
        
        //协议支付
        B2GResMsg_BaoFooAgreementPay_DirectAgreementPay agreemRes = new B2GResMsg_BaoFooAgreementPay_DirectAgreementPay();
		agreemRes.setResCode("000000");
		agreemRes.setResMsg("协议支付");
		//agreemRes.setResCode(LoanStatus.BAOFOO_PAY_STATUS_PROCESS.getCode());
		Mockito.doReturn(agreemRes).when(mockBaoFooTransportService).directAgreementPay(Mockito.any(B2GReqMsg_BaoFooAgreementPay_DirectAgreementPay.class));
    }

    @After(value = "depFixedDoWithholdingRepayPointcut()")
    public void depFixedMockAfter() {
        ReflectionTestUtils.setField(AopTargetUtils.getTarget(depFixedRepayPaymentService), "baoFooTransportService", springBaoFooTransportService);
    }
    
    //================赞时贷代扣========================
    
    @Before(value = "depFixedDoWithholdingRepayZsdPointcut()")
    public void depFixedRepayCutpaymentZsdMockBefore(JoinPoint joinPoint) {
        try {
            LnPayOrders req = (LnPayOrders)joinPoint.getArgs()[0];
            logger.info("depFixedDoWithholdingRepayZsdPointcut代扣mock：{}", JSONObject.fromObject(req));
        } catch (Exception e) {
            e.printStackTrace();
        }
        BaoFooTransportService mockBaoFooTransportService = Mockito.mock(BaoFooTransportService.class);
        ReflectionTestUtils.setField(AopTargetUtils.getTarget(depFixedRepayPaymentService), "baoFooTransportService", mockBaoFooTransportService);
        B2GResMsg_BaoFooCutpayment_Cutpayment res = new B2GResMsg_BaoFooCutpayment_Cutpayment();
        res.setResCode("000000");
//        res.setResCode("100000");
//        res.setResMsg("失败");
//        res.setResCode(LoanStatus.BAOFOO_PAY_STATUS_PROCESS.getCode());
        Mockito.doReturn(res).when(mockBaoFooTransportService).withholding(Mockito.any(B2GReqMsg_BaoFooCutpayment_Cutpayment.class));
    }

    @After(value = "depFixedDoWithholdingRepayZsdPointcut()")
    public void depFixedRepayCutpaymentZsdMockAfter() {
        ReflectionTestUtils.setField(AopTargetUtils.getTarget(depFixedRepayPaymentService), "baoFooTransportService", springBaoFooTransportService);
    }
}
