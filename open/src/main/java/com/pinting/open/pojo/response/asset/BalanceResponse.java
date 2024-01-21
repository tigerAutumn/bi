package com.pinting.open.pojo.response.asset;

import com.pinting.open.base.response.Response;

public class BalanceResponse extends Response {
	
	private boolean HaveFirstCard;  //是否有回款卡
	
	private boolean HavePayPassword;  //是否有交易密码

    private String availableBalance; //可用余额

	private String depAvailableBalance;	// 存管户账户余额

	public String getDepAvailableBalance() {
		return depAvailableBalance;
	}

	public void setDepAvailableBalance(String depAvailableBalance) {
		this.depAvailableBalance = depAvailableBalance;
	}

	public boolean getHaveFirstCard() {
		return HaveFirstCard;
	}

	public void setHaveFirstCard(boolean haveFirstCard) {
		HaveFirstCard = haveFirstCard;
	}

	public boolean getHavePayPassword() {
		return HavePayPassword;
	}

	public void setHavePayPassword(boolean havePayPassword) {
		HavePayPassword = havePayPassword;
	}

	public String getAvailableBalance() {
		return availableBalance;
	}

	public void setAvailableBalance(String availableBalance) {
		this.availableBalance = availableBalance;
	}

}
