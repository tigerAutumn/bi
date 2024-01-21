package com.pinting.gateway.test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import net.sf.json.JSONObject;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.pinting.gateway.business.in.facade.DepLoan7NoticeFacade;
import com.pinting.gateway.hessian.message.loan7.B2GReqMsg_DepLoan7Notice_QueryBill;
import com.pinting.gateway.hessian.message.loan7.B2GReqMsg_DepLoan7Notice_WaitFill;
import com.pinting.gateway.hessian.message.loan7.B2GResMsg_DepLoan7Notice_QueryBill;
import com.pinting.gateway.hessian.message.loan7.B2GResMsg_DepLoan7Notice_WaitFill;
import com.pinting.gateway.loan7.in.model.ActiveRepayPreReqModel;
import com.pinting.gateway.loan7.in.model.ActiveRepayPreResModel;
import com.pinting.gateway.loan7.in.model.Repayment;
import com.pinting.gateway.loan7.in.service.DepLoan7Service;

/**
 *
 * @author SHENGUOPING
 * @date  2017年12月13日 下午3:56:13
 */
public class TestDepLoan7 extends TestBase {

	private Logger logger = LoggerFactory.getLogger(TestDepLoan7.class);
    
    @Autowired 
    private DepLoan7Service depLoan7Service;
    @Autowired
    private DepLoan7NoticeFacade depLoan7NoticeFacade;
	
    /**
     * 主动还款预下单
     */
    public void activeRepayPre() {
    	ActiveRepayPreReqModel req = new ActiveRepayPreReqModel();
    	req.setOrderNo("8f720d49465749f9acb55e3725c4c01d");
    	req.setUserId("1081611170013574");
    	req.setName("老王");
    	req.setIdCard("520203199012161817");
    	req.setMobile("15157483266");
    	req.setBankCard("6212262201023557228");
    	req.setBankCode("ICBC");
    	req.setLoanId("10ae964aad894b679cf026bde5d6aea6");
    	req.setTotalAmount(100000L);
    	List<Repayment> repaymentList = new ArrayList<>();
		for (int i = 1; i < 3; i++) {
			Repayment repayment = new Repayment();
			repayment.setRepayId("11223" + i);
			repayment.setStatus("REPAIED");
			repayment.setRepayType("REPAYMENT");
			repayment.setRepaySerial(112233 + i);   //"52020319901216181" + i;
			repayment.setTotal((1000L)*i);
			repayment.setPrincipal((1000L)*i);
			repayment.setInterest(0L);
			repayment.setPrincipalOverdue(0L);
			repayment.setInterestOverdue(0L);
			repayment.setReservedField1("");
			repaymentList.add(repayment);
		}
		req.setRepayments(repaymentList);
    	ActiveRepayPreResModel res = depLoan7Service.activeRepayPre(req);
    	logger.info("activeRepayPre结果：{}", JSONObject.fromObject(res));
    }
    
    
	//账单同步查询
    @Test
    public void queryBill(){
    	B2GReqMsg_DepLoan7Notice_QueryBill req = new B2GReqMsg_DepLoan7Notice_QueryBill();

    	req.setUserId("3000084");
    	req.setLoanId("30000073295");
    	B2GResMsg_DepLoan7Notice_QueryBill res = new B2GResMsg_DepLoan7Notice_QueryBill();
    	try {
    		depLoan7NoticeFacade.queryBill(req, res);
    		System.out.println("aaa");
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    

    public void waitFill() {
    	B2GReqMsg_DepLoan7Notice_WaitFill req = new B2GReqMsg_DepLoan7Notice_WaitFill();
    	req.setOrderNo("1213313");
    	req.setFillDate(new Date());
    	req.setFillType("INTEREST");
    	req.setAmount(1000.0);
    	req.setFileUrl("/home");
    	B2GResMsg_DepLoan7Notice_WaitFill res = new B2GResMsg_DepLoan7Notice_WaitFill();
    	try {
    		depLoan7NoticeFacade.waitFill(req, res);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	    
	   
}
