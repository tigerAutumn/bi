package com.pinting.gateway.hessian.message.pay19;

import java.util.List;

import com.pinting.core.hessian.msg.ResMsg;
import com.pinting.gateway.hessian.message.pay19.model.AccountDetail;
import com.pinting.gateway.hessian.message.pay19.model.TotalAccount;

/**
 * 
 * @Project: business
 * @Title: G2BResMsg_Pay4Another_queryCheckAccountFile.java
 * @author dingpf
 * @date 2015-11-16 下午2:07:19
 * @Copyright: 2015 BiGangWan.com Inc. All rights reserved.
 */
public class B2GResMsg_Pay4Another_QueryCheckAccountFile extends ResMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4909076787109476847L;

	private TotalAccount totalAccount;
	private List<AccountDetail> accountDetails;

	public TotalAccount getTotalAccount() {
		return totalAccount;
	}

	public void setTotalAccount(TotalAccount totalAccount) {
		this.totalAccount = totalAccount;
	}

	public List<AccountDetail> getAccountDetails() {
		return accountDetails;
	}

	public void setAccountDetails(List<AccountDetail> accountDetails) {
		this.accountDetails = accountDetails;
	}

}
