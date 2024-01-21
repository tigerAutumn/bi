package com.pinting.open.pojo.response.asset;

import java.util.List;

import com.pinting.open.base.response.Response;
import com.pinting.open.pojo.model.asset.BalanceRechargeBankCard;

public class BalanceRechargeResponse extends Response {
	
	private boolean isBindBank;   //是否绑卡
	
	private Integer subAccountId; //子账户ID
	
	private Double rechangeLimit; //最小充值金额
	
	private List<BalanceRechargeBankCard> bankList;

	public boolean isBindBank() {
		return isBindBank;
	}

	public void setBindBank(boolean isBindBank) {
		this.isBindBank = isBindBank;
	}

	public Integer getSubAccountId() {
		return subAccountId;
	}

	public void setSubAccountId(Integer subAccountId) {
		this.subAccountId = subAccountId;
	}


	public Double getRechangeLimit() {
		return rechangeLimit;
	}

	public void setRechangeLimit(Double rechangeLimit) {
		this.rechangeLimit = rechangeLimit;
	}

	public List<BalanceRechargeBankCard> getBankList() {
		return bankList;
	}

	public void setBankList(List<BalanceRechargeBankCard> bankList) {
		this.bankList = bankList;
	}
	
	
}
