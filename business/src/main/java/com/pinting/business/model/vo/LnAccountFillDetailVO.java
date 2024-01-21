package com.pinting.business.model.vo;

import com.pinting.business.model.LnAccountFillDetail;

public class LnAccountFillDetailVO extends LnAccountFillDetail {

	private String partnerLoanId;
	
	private String partnerRepayId;

	public String getPartnerLoanId() {
		return partnerLoanId;
	}

	public void setPartnerLoanId(String partnerLoanId) {
		this.partnerLoanId = partnerLoanId;
	}

	public String getPartnerRepayId() {
		return partnerRepayId;
	}

	public void setPartnerRepayId(String partnerRepayId) {
		this.partnerRepayId = partnerRepayId;
	}
	
	
}
