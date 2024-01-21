package com.pinting.gateway.hessian.message.zsd;

import java.util.List;

import com.pinting.core.hessian.msg.ReqMsg;
import com.pinting.gateway.hessian.message.dafy.model.SignResultNoticeLenderModel;

/**
 * 借款协议签章结果通知
 * @author SHENGUOPING
 * @date  2017年10月30日 上午10:58:30
 */
public class B2GReqMsg_ZsdNotice_SignResultNotice extends ReqMsg {

	private static final long serialVersionUID = -7311436092201619663L;
	
	/*借款协议号*/
	private			String		agreementNo;
	/*借款编号*/
	private			String		loanId;
	/*签章结果*/
	private			String		signResult;
	/*协议下载地址*/
	private			String		agreementUrl;
	/*出借人信息*/
	private			List<SignResultNoticeLenderModel> 		lenders;
	public String getAgreementNo() {
		return agreementNo;
	}
	public void setAgreementNo(String agreementNo) {
		this.agreementNo = agreementNo;
	}
	public String getLoanId() {
		return loanId;
	}
	public void setLoanId(String loanId) {
		this.loanId = loanId;
	}
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
	public List<SignResultNoticeLenderModel> getLenders() {
		return lenders;
	}
	public void setLenders(List<SignResultNoticeLenderModel> lenders) {
		this.lenders = lenders;
	}
	
}
