package com.pinting.gateway.test;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.pinting.core.util.DateUtil;
import com.pinting.gateway.business.in.facade.DafyLoanNoticeFacade;
import com.pinting.gateway.dafy.out.service.DafyLoanService;
import com.pinting.gateway.hessian.message.dafy.B2GReqMsg_DafyLoanNotice_LoanResultNotice;
import com.pinting.gateway.hessian.message.dafy.B2GReqMsg_DafyLoanNotice_QueryBill;
import com.pinting.gateway.hessian.message.dafy.B2GReqMsg_DafyLoanNotice_RevenueSettle;
import com.pinting.gateway.hessian.message.dafy.B2GReqMsg_DafyLoanNotice_SignResultNotice;
import com.pinting.gateway.hessian.message.dafy.B2GReqMsg_DafyLoanNotice_WaitFill;
import com.pinting.gateway.hessian.message.dafy.B2GResMsg_DafyLoanNotice_LoanResultNotice;
import com.pinting.gateway.hessian.message.dafy.B2GResMsg_DafyLoanNotice_QueryBill;
import com.pinting.gateway.hessian.message.dafy.B2GResMsg_DafyLoanNotice_RevenueSettle;
import com.pinting.gateway.hessian.message.dafy.B2GResMsg_DafyLoanNotice_SignResultNotice;
import com.pinting.gateway.hessian.message.dafy.B2GResMsg_DafyLoanNotice_WaitFill;
import com.pinting.gateway.hessian.message.dafy.model.AgreementDebtTransfers;
import com.pinting.gateway.hessian.message.dafy.model.Agreements;
import com.pinting.gateway.hessian.message.dafy.model.DebtTransferInfo;
import com.pinting.gateway.hessian.message.dafy.model.DebtTransfers;
import com.pinting.gateway.hessian.message.dafy.model.SignResultNoticeLenderModel;
import com.pinting.gateway.hessian.message.dafy.B2GReqMsg_DafyLoanNotice_AgreementNotice;
import com.pinting.gateway.hessian.message.dafy.B2GReqMsg_DafyLoanNotice_RepayResultNotice;
import com.pinting.gateway.hessian.message.dafy.B2GResMsg_DafyLoanNotice_AgreementNotice;
import com.pinting.gateway.hessian.message.dafy.B2GResMsg_DafyLoanNotice_RepayResultNotice;

/**
 * 达飞自主放款
 * @author Dragon & cat
 * @date 2016-12-20
 */
public class TestDafyLoan extends TestBase  {
	
	@Autowired
	private  DafyLoanService dafyLoanService ;
	@Autowired
	private	 DafyLoanNoticeFacade dafyLoanNoticeFacade ;
	
	
	
	//账单同步查询
	 @Test
    public void queryBill(){
    	B2GReqMsg_DafyLoanNotice_QueryBill req = new B2GReqMsg_DafyLoanNotice_QueryBill();

    	req.setUserId("3000084");
    	req.setLoanId("30000073295");
    	B2GResMsg_DafyLoanNotice_QueryBill res = new B2GResMsg_DafyLoanNotice_QueryBill();
    	try {
			dafyLoanNoticeFacade.queryBill(req, res);
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    //待补账通知

    public void waitFill() {
    	B2GReqMsg_DafyLoanNotice_WaitFill req = new B2GReqMsg_DafyLoanNotice_WaitFill();
    	req.setOrderNo("1213313");
    	req.setFillDate(new Date());
    	req.setFillType("INTEREST");
    	req.setAmount(1000.0);
    	req.setFileUrl("/home");
    	B2GResMsg_DafyLoanNotice_WaitFill res = new B2GResMsg_DafyLoanNotice_WaitFill();
    	try {
			dafyLoanNoticeFacade.waitFill(req, res);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
    
    //营收结算通知

    public void revenueSettle() {
    	B2GReqMsg_DafyLoanNotice_RevenueSettle req = new B2GReqMsg_DafyLoanNotice_RevenueSettle();
    	req.setOrderNo("123456");
    	req.setApplyTime(new Date());
    	req.setFinishTime(new Date());
    	req.setSettleType("LOAN_FEE");
    	req.setAmount(1000.0);
    	req.setFileUrl("/home");
    	B2GResMsg_DafyLoanNotice_RevenueSettle res = new B2GResMsg_DafyLoanNotice_RevenueSettle();
    	try {
			dafyLoanNoticeFacade.revenueSettle(req, res);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
    
   //放款结果通知
   public void loanResultNotice() {
    	B2GReqMsg_DafyLoanNotice_LoanResultNotice req = new B2GReqMsg_DafyLoanNotice_LoanResultNotice();
    	req.setOrderNo("1233232");
    	req.setBgwOrderNo("bgw12132323");
    	req.setLoanId("001");
    	req.setChannel("BAOFOO");
    	req.setResultCode("SUCCESS");
    	req.setResultMsg("成功");
    	req.setFinishTime(new	Date());
    	
    	ArrayList<HashMap<String, Object>> lenderList = new ArrayList<>();
    	for (int i = 0; i < 2; i++) {
    		HashMap<String, Object> lenders = new HashMap<String, Object>();
    		lenders.put("lenderName", "龙猫");
    		lenders.put("lenderIdcard", "520203199012161817");
    		lenders.put("investAmount", 1000.2);
    		
        	lenderList.add(lenders);
		}
    	req.setLenders(lenderList);
    	
    	
    	B2GResMsg_DafyLoanNotice_LoanResultNotice res = new B2GResMsg_DafyLoanNotice_LoanResultNotice();
    	try {
			dafyLoanNoticeFacade.loanResultNotice(req, res);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
    
    //借款协议签章结果通知
  
    public void signResultNotice() {
    	B2GReqMsg_DafyLoanNotice_SignResultNotice req = new B2GReqMsg_DafyLoanNotice_SignResultNotice();
    	req.setAgreementNo("001");
    	req.setLoanId("11111");
    	req.setSignResult("SUCC");
    	req.setAgreementUrl("C:/");
    	
    	List<SignResultNoticeLenderModel> 	lenderList = new ArrayList<>();
    	for (int i = 0; i < 2; i++) {
        	SignResultNoticeLenderModel lenders = new SignResultNoticeLenderModel();
        	lenders.setLenderName("龙猫");
        	lenders.setLenderIdcard("520203199012161817");
        	lenders.setInvestAmount(1000.2);
        	lenderList.add(lenders);
		}
    	req.setLenders(lenderList);
     	
    	B2GResMsg_DafyLoanNotice_SignResultNotice res = new B2GResMsg_DafyLoanNotice_SignResultNotice();
    	try {
			dafyLoanNoticeFacade.signResultNotice(req, res);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
    
    
    
    //还款结果通知
 	
    public void repayResultNotice() {
    	B2GReqMsg_DafyLoanNotice_RepayResultNotice req = new B2GReqMsg_DafyLoanNotice_RepayResultNotice();
    	req.setOrderNo("P2P0_15a8e363668009900125ac3ca4a");
    	req.setBgwOrderNo("BQP20170302164740277065171160");
    	req.setChannel("BAOFOO");
    	req.setResultCode("SUCCESS");
    	req.setResultMsg("成功");
    	req.setFinishTime(new Date());
    	
    	B2GResMsg_DafyLoanNotice_RepayResultNotice res = new B2GResMsg_DafyLoanNotice_RepayResultNotice();
    	try {
			dafyLoanNoticeFacade.repayResultNotice(req, res);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
    
    
    //协议下载通知

    public void agreementNotice() {
    	B2GReqMsg_DafyLoanNotice_AgreementNotice req = new B2GReqMsg_DafyLoanNotice_AgreementNotice();
    	req.setOrderNo("1234567");
    	
    	List<Agreements> agreements = new ArrayList<Agreements>();
    	
    	for (int i = 0; i < 2; i++) {
        	Agreements agreement = new Agreements();
        	agreement.setLoanId("12121");
        	agreement.setDebtTransNoticesUrl("home/"+i);
        	agreement.setServiceFeeConfirmUrl("home/"+i);
        	agreement.setDebtTransConfirmUrl("home/"+i);
        	agreement.setBorrowerName("龙猫");
        	agreement.setBorrowerIdCard("52020319901216");
        	
        	List<DebtTransferInfo> debtTransferInfoList = new ArrayList<DebtTransferInfo>();
        	
        	for (int j = 0; j <3; j++) {
        		DebtTransferInfo debtTransferInfo = new DebtTransferInfo();
        		debtTransferInfo.setOutUserName("longmao");
        		debtTransferInfo.setOutIdCard("520203");
        		debtTransferInfo.setTransAmount((long)1000);
        		debtTransferInfo.setPeroid(1);
        		debtTransferInfo.setInUserName("longm");
        		debtTransferInfo.setInIdCard("5222323");
        		debtTransferInfo.setTransTime(new Date());
        		debtTransferInfo.setNote("备注");
        		debtTransferInfoList.add(debtTransferInfo);
			}
        	List<DebtTransfers>  debtTransfersList = new ArrayList<DebtTransfers>();
        	for (int j = 0; j < 2; j++) {
        		
        		DebtTransfers debtTransfers = new DebtTransfers();
        		debtTransfers.setDebtTransferUrl("/hhhas");
        		debtTransfersList.add(debtTransfers);
			}
        	agreement.setDebtTransferInfo(debtTransferInfoList);
        	agreement.setDebtTransfers(debtTransfersList);
        	agreements.add(agreement);
		}

    	req.setAgreements(agreements);
    	B2GResMsg_DafyLoanNotice_AgreementNotice res = new B2GResMsg_DafyLoanNotice_AgreementNotice();
    	try {
			dafyLoanNoticeFacade.agreementNotice(req, res);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
    
    

}
