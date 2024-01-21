package com.pinting.business.hessian.site.message;

import java.util.List;
import java.util.Map;

import com.pinting.core.hessian.msg.ResMsg;

/**
 * 查询用户绑定的银行卡出参
 * @author shencheng
 * @2015-11-16 下午7:49:54
 */
public class ResMsg_Bank_bindBankList extends ResMsg {

	private static final long serialVersionUID = 9124058631830058874L;

	private boolean isBindBank;
	/*银行列表*/
	private List<Map<String, Object>> bankList;
	/*子账户ID*/
	private Integer subAccountId;
	/*子账户可用余额*/
	private Double availableBalance;
	/*最小充值金额*/
	private String rechangeLimit;

	public Integer getSubAccountId() {
		return subAccountId;
	}

	public void setSubAccountId(Integer subAccountId) {
		this.subAccountId = subAccountId;
	}

	public Double getAvailableBalance() {
		return availableBalance;
	}

	public void setAvailableBalance(Double availableBalance) {
		this.availableBalance = availableBalance;
	}

	public boolean isBindBank() {
		return isBindBank;
	}

	public void setBindBank(boolean isBindBank) {
		this.isBindBank = isBindBank;
	}

	public List<Map<String, Object>> getBankList() {
		return bankList;
	}

	public void setBankList(List<Map<String, Object>> bankList) {
		this.bankList = bankList;
	}

	public String getRechangeLimit() {
		return rechangeLimit;
	}

	public void setRechangeLimit(String rechangeLimit) {
		this.rechangeLimit = rechangeLimit;
	}
}
