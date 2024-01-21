package com.pinting.gateway.loan7.out.model;

import java.util.List;
/**
 * 借款协议签章结果通知
 * @author Dragon & cat
 * @date 2016-12-15
 */
public class SignResultNoticeReqModel extends BaseReqModel {
	/*借款协议号*/
	private			String		agreementNo;
	/*借款编号*/
	private			String		loanId;
	/*签章结果*/
	private			String		signResult;
	/*协议下载地址*/
	private			String		agreementUrl;
	/*申请流水号*/
	private		String		requestSeq;
	/*出借人信息*/
	//private			List<SignResultNoticeLender> 		lenders;

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
	

	public String getRequestSeq() {
		return requestSeq;
	}

	public void setRequestSeq(String requestSeq) {
		this.requestSeq = requestSeq;
	}



	@Override
	public String toString() {
		return "SignResultNoticeReqModel [agreementNo=" + agreementNo
				+ ", loanId=" + loanId + ", signResult=" + signResult
				+ ", agreementUrl=" + agreementUrl + ", requestSeq="
				+ requestSeq + " ]";
	}
	
	
}
