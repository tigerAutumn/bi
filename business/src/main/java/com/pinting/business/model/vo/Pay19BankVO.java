package com.pinting.business.model.vo;

import com.pinting.business.model.Bs19payBank;

public class Pay19BankVO extends Bs19payBank{

	private static final long serialVersionUID = 492446276671464236L;

	private String bankName;

	private String smallLogo;

	private String largeLogo;

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getSmallLogo() {
		return smallLogo;
	}

	public void setSmallLogo(String smallLogo) {
		this.smallLogo = smallLogo;
	}

	public String getLargeLogo() {
		return largeLogo;
	}

	public void setLargeLogo(String largeLogo) {
		this.largeLogo = largeLogo;
	}
}
