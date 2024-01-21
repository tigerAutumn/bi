package com.pinting.gateway.hessian.message.dafy;

import java.util.ArrayList;
import java.util.HashMap;
import com.pinting.core.hessian.msg.ResMsg;

public class G2BResMsg_DafyLoan_QuerySignResult extends ResMsg {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3303905837558444289L;
	/*签章结果*/
	private			String		signResult;
	/*借款编号*/
	private			String		loanId;
	/*协议下载地址*/
	private			String		agreementUrl;
	/*出借人信息*/
	private			ArrayList<HashMap<String, Object>>		lenders;
	
	public String getSignResult() {
		return signResult;
	}
	public void setSignResult(String signResult) {
		this.signResult = signResult;
	}
	public String getAgreementUrl() {
		return agreementUrl;
	}
	public void setAgreementUrl(String agreementUrl) {
		this.agreementUrl = agreementUrl;
	}
	public String getLoanId() {
		return loanId;
	}
	public void setLoanId(String loanId) {
		this.loanId = loanId;
	}
	public ArrayList<HashMap<String, Object>> getLenders() {
		return lenders;
	}
	public void setLenders(ArrayList<HashMap<String, Object>> lenders) {
		this.lenders = lenders;
	}
	
	
}
