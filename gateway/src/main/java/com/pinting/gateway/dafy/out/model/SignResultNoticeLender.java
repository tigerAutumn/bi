package com.pinting.gateway.dafy.out.model;
/**
 * 借款协议签章结果通知
 * @author Dragon & cat
 * @date 2016-12-15
 */
public class SignResultNoticeLender {
	/*出借人姓名*/
	private		String		lenderName;
	/*出借人身份证*/
	private		String		lenderIdcard;
	/*投标金额*/
	private		Long		investAmount;

	public String getLenderName() {
		return lenderName;
	}

	public void setLenderName(String lenderName) {
		this.lenderName = lenderName;
	}

	public String getLenderIdcard() {
		return lenderIdcard;
	}

	public void setLenderIdcard(String lenderIdcard) {
		this.lenderIdcard = lenderIdcard;
	}

	public Long getInvestAmount() {
		return investAmount;
	}

	public void setInvestAmount(Long investAmount) {
		this.investAmount = investAmount;
	}
	
}
