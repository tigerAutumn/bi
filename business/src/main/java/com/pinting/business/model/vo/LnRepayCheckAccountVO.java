package com.pinting.business.model.vo;

import com.pinting.business.model.LnRepay;

/**
 * 
 * 宝付对账 - 线下还款信息
 * @author SHENGUOPING
 * @date  2018年1月23日 下午3:44:19
 */
public class LnRepayCheckAccountVO extends LnRepay {

	private String partnerCode;

	public String getPartnerCode() {
		return partnerCode;
	}

	public void setPartnerCode(String partnerCode) {
		this.partnerCode = partnerCode;
	}
	
}
