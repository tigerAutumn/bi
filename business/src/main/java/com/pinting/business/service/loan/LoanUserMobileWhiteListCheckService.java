package com.pinting.business.service.loan;

public interface LoanUserMobileWhiteListCheckService {
	
	
	/**
	 * 借款人手机号白名单校验
	 * @param mobile 借款人手机号
	 * @return true/false
	 */
	boolean lnMobileWhiteListCheck(String mobile);

}
